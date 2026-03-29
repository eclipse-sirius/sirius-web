/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

import { gql, useQuery } from '@apollo/client';
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { memo, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  ConnectorContextualMenuProps,
  GetConnectorToolsData,
  GetConnectorToolsVariables,
  GQLDiagramDescription,
  GQLRepresentationDescription,
  GQLTool,
} from './ConnectorContextualMenu.types';
import { useConnector } from './useConnector';
import { useSingleClickOnTwoDiagramElementTool } from './useSingleClickOnTwoDiagramElementTool';

export const getConnectorToolsQuery = gql`
  query getConnectorTools(
    $editingContextId: ID!
    $representationId: ID!
    $sourceDiagramElementId: ID!
    $targetDiagramElementId: ID!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on DiagramDescription {
              connectorTools(
                sourceDiagramElementId: $sourceDiagramElementId
                targetDiagramElementId: $targetDiagramElementId
              ) {
                id
                label
                iconURL
                ... on SingleClickOnTwoDiagramElementsTool {
                  dialogDescriptionId
                }
              }
            }
          }
        }
      }
    }
  }
`;

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

const ConnectorContextualMenuComponent = memo(({}: ConnectorContextualMenuProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { connection, position, onConnectorContextualMenuClose, addTempConnectionLine, removeTempConnectionLine } =
    useConnector();
  const { addMessages, addErrorMessage } = useMultiToast();
  const { screenToFlowPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { invokeConnectorTool, data: invokeSingleClickOnTwoDiagramElementToolCalled } =
    useSingleClickOnTwoDiagramElementTool();

  const connectionSource: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.source}"]`)
    : null;

  const connectionTarget: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.target}"]`)
    : null;

  const sourceDiagramElementId = connectionSource?.dataset.id ?? '';
  const targetDiagramElementId = connectionTarget?.dataset.id ?? '';

  const variables: GetConnectorToolsVariables = {
    editingContextId,
    representationId: diagramId,
    sourceDiagramElementId,
    targetDiagramElementId,
  };
  const { loading, data, error } = useQuery<GetConnectorToolsData, GetConnectorToolsVariables>(getConnectorToolsQuery, {
    variables,
    skip: !connectionSource || !connectionTarget,
  });

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  const connectorTools: GQLTool[] = [];
  const representationDescription: GQLRepresentationDescription | null | undefined =
    data?.viewer.editingContext?.representation?.description;
  if (representationDescription && isDiagramDescription(representationDescription)) {
    representationDescription.connectorTools.forEach((tool) => connectorTools.push(tool));
  }

  useEffect(() => {
    if (connectorTools.length > 1) {
      addTempConnectionLine();
    }
  }, [connection, connectorTools.length]);

  useEffect(() => {
    if (!loading && connection && data && connectorTools.length === 0) {
      onConnectorContextualMenuClose();
      addMessages([{ body: 'No edge found between source and target selected', level: 'WARNING' }]);
    }
  }, [loading, data, connection, connectorTools.length]);

  useEffect(() => {
    return () => removeTempConnectionLine();
  }, []);

  useEffect(() => {
    if (!invokeSingleClickOnTwoDiagramElementToolCalled && connectorTools.length === 1 && connectorTools[0]) {
      invokeTool(connectorTools[0]);
    }
  }, [connectorTools.length]);

  const invokeTool = (tool: GQLTool) => {
    const { x: cursorPositionX, y: cursorPositionY } = screenToFlowPosition({ x: position.x, y: position.y });
    invokeConnectorTool(tool, sourceDiagramElementId, targetDiagramElementId, cursorPositionX, cursorPositionY);
  };

  if (!data || connectorTools.length <= 1) {
    return null;
  }

  return (
    <Menu
      open={!!connection}
      onClose={onConnectorContextualMenuClose}
      anchorEl={connectionTarget}
      anchorReference="anchorPosition"
      data-testid="connectorContextualMenu"
      anchorPosition={{ left: position?.x || 0, top: position?.y || 0 }}>
      {connectorTools.map((tool) => (
        <MenuItem key={tool.id} onClick={() => invokeTool(tool)} data-testid={`connectorContextualMenu-${tool.label}`}>
          <ListItemIcon>
            <IconOverlay iconURLs={tool.iconURL} alt={tool.label} title={tool.label} />
          </ListItemIcon>
          <Typography>{tool.label}</Typography>
        </MenuItem>
      ))}
    </Menu>
  );
});

export const ConnectorContextualMenu = memo(({}: ConnectorContextualMenuProps) => {
  const { isConnectionInProgress } = useConnector();
  return isConnectionInProgress() ? <ConnectorContextualMenuComponent /> : null;
});
