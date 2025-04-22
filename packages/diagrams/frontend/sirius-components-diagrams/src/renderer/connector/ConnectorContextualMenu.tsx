/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { IconOverlay, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { Edge, Node, useReactFlow, XYPosition } from '@xyflow/react';
import { memo, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramDialogVariable } from '../../dialog/DialogContextExtensionPoints.types';
import { useDialog } from '../../dialog/useDialog';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  ConnectorContextualMenuProps,
  GetConnectorToolsData,
  GetConnectorToolsVariables,
  GQLDiagramDescription,
  GQLErrorPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolData,
  GQLInvokeSingleClickOnTwoDiagramElementsToolInput,
  GQLInvokeSingleClickOnTwoDiagramElementsToolPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolVariables,
  GQLRepresentationDescription,
  GQLTool,
  GQLToolVariable,
} from './ConnectorContextualMenu.types';
import { useConnector } from './useConnector';
import { GQLSingleClickOnTwoDiagramElementsTool } from './useConnector.types';

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

export const invokeSingleClickOnTwoDiagramElementsToolMutation = gql`
  mutation invokeSingleClickOnTwoDiagramElementsTool($input: InvokeSingleClickOnTwoDiagramElementsToolInput!) {
    invokeSingleClickOnTwoDiagramElementsTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload {
        newSelection {
          entries {
            id
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
const isSuccessPayload = (
  payload: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload
): payload is GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload';

const isSingleClickOnTwoDiagramElementsTool = (tool: GQLTool): tool is GQLSingleClickOnTwoDiagramElementsTool =>
  tool.__typename === 'SingleClickOnTwoDiagramElementsTool';

const ConnectorContextualMenuComponent = memo(({}: ConnectorContextualMenuProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { connection, position, onConnectorContextualMenuClose, addTempConnectionLine, removeTempConnectionLine } =
    useConnector();
  const { addMessages, addErrorMessage } = useMultiToast();
  const { setSelection } = useSelection();

  const { showDialog, isOpened } = useDialog();

  const { getNodes, screenToFlowPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

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

  const invokeOpenSelectionDialog = (tool: GQLSingleClickOnTwoDiagramElementsTool) => {
    const onConfirm = (variables: GQLToolVariable[]) => {
      invokeToolMutation(tool, variables);
    };

    const onClose = () => {
      onShouldConnectorContextualMenuClose();
    };

    const sourceNode = getNodes().find((node) => node.id === sourceDiagramElementId);
    const targetNode = getNodes().find((node) => node.id === targetDiagramElementId);
    if (sourceNode && targetNode) {
      const variables: DiagramDialogVariable[] = [
        { name: 'targetObjectId', value: sourceNode.data.targetObjectId },
        { name: 'sourceDiagramElementTargetObjectId', value: sourceNode.data.targetObjectId },
        { name: 'targetDiagramElementTargetObjectId', value: targetNode.data.targetObjectId },
      ];
      showDialog(tool.dialogDescriptionId, variables, onConfirm, onClose);
    }
  };

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
    if (isSingleClickOnTwoDiagramElementsTool(tool)) {
      if (tool.dialogDescriptionId) {
        if (!isOpened) {
          invokeOpenSelectionDialog(tool);
        }
      } else {
        invokeToolMutation(tool, []);
      }
    }
  };

  const invokeToolMutation = (tool: GQLTool, variables: GQLToolVariable[]) => {
    let targetHandlePosition: XYPosition = { x: 0, y: 0 };
    if (position) {
      targetHandlePosition = screenToFlowPosition({ x: position.x, y: position.y });
    }

    const input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      diagramSourceElementId: sourceDiagramElementId,
      diagramTargetElementId: targetDiagramElementId,
      toolId: tool.id,
      sourcePositionX: 0,
      sourcePositionY: 0,
      targetPositionX: targetHandlePosition.x,
      targetPositionY: targetHandlePosition.y,
      variables,
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
        const { newSelection } = payload;
        if (newSelection?.entries.length ?? 0 > 0) {
          setSelection(newSelection);
        }
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
