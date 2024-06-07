/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { gql, useMutation, useQuery } from '@apollo/client';
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Typography from '@material-ui/core/Typography';
import { memo, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  ConnectorContextualMenuProps,
  GQLDiagramDescription,
  GQLErrorPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolData,
  GQLInvokeSingleClickOnTwoDiagramElementsToolInput,
  GQLInvokeSingleClickOnTwoDiagramElementsToolPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolVariables,
  GQLRepresentationDescription,
  GQLSuccessPayload,
  GQLTool,
  GetConnectorToolsData,
  GetConnectorToolsVariables,
} from './ConnectorContextualMenu.types';
import { useConnector } from './useConnector';

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
              }
            }
          }
        }
      }
    }
  }
`;

export const invokeSingleClickOnTwoDiagramElementsToolMutation = gql`
  mutation invokeSingleClickOnTwoDiagramElementsTool($input: InvokeSingleClickOnTwoDiagramElementsToolInput!) {
    invokeSingleClickOnTwoDiagramElementsTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload {
        newSelection {
          entries {
            id
            label
            kind
          }
        }
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

const isErrorPayload = (payload: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload';

const ConnectorContextualMenuComponent = memo(({}: ConnectorContextualMenuProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { connection, position, onConnectorContextualMenuClose, addTempConnectionLine, removeTempConnectionLine } =
    useConnector();
  const { addMessages, addErrorMessage } = useMultiToast();

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

  const [
    invokeSingleClickOnTwoDiagramElementsTool,
    {
      data: invokeSingleClickOnTwoDiagramElementToolData,
      error: invokeSingleClickOnTwoDiagramElementToolError,
      called: invokeSingleClickOnTwoDiagramElementToolCalled,
      reset,
    },
  ] = useMutation<
    GQLInvokeSingleClickOnTwoDiagramElementsToolData,
    GQLInvokeSingleClickOnTwoDiagramElementsToolVariables
  >(invokeSingleClickOnTwoDiagramElementsToolMutation);

  const invokeTool = (tool: GQLTool) => {
    const input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      diagramSourceElementId: sourceDiagramElementId,
      diagramTargetElementId: targetDiagramElementId,
      toolId: tool.id,
      sourcePositionX: 0,
      sourcePositionY: 0,
      targetPositionX: 0,
      targetPositionY: 0,
    };
    invokeSingleClickOnTwoDiagramElementsTool({ variables: { input } });
  };

  const onShouldConnectorContextualMenuClose = () => {
    onConnectorContextualMenuClose();
    reset();
  };

  useEffect(() => {
    if (invokeSingleClickOnTwoDiagramElementToolData) {
      const payload = invokeSingleClickOnTwoDiagramElementToolData.invokeSingleClickOnTwoDiagramElementsTool;
      if (isErrorPayload(payload)) {
        addMessages(payload.messages);
      }
      if (isSuccessPayload(payload)) {
        addMessages(payload.messages);
        onShouldConnectorContextualMenuClose();
      }
    }
    if (invokeSingleClickOnTwoDiagramElementToolError?.message) {
      addErrorMessage(invokeSingleClickOnTwoDiagramElementToolError.message);
    }
  }, [invokeSingleClickOnTwoDiagramElementToolData, invokeSingleClickOnTwoDiagramElementToolError]);

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
  }, [connectorTools]);

  if (!data || connectorTools.length <= 1) {
    return null;
  }

  return (
    <Menu
      open={!!connection}
      onClose={onShouldConnectorContextualMenuClose}
      anchorEl={connectionTarget}
      anchorReference="anchorPosition"
      anchorPosition={{ left: position?.x || 0, top: position?.y || 0 }}>
      {connectorTools.map((tool) => (
        <MenuItem key={tool.id} onClick={() => invokeTool(tool)}>
          <ListItemIcon>
            <IconOverlay iconURL={tool.iconURL} alt={tool.label} title={tool.label} />
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
