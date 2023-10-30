/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  ConnectorContextualMenuProps,
  GetConnectorToolsData,
  GetConnectorToolsVariables,
  GQLDiagramDescription,
  GQLErrorPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolData,
  GQLInvokeSingleClickOnTwoDiagramElementsToolInput,
  GQLInvokeSingleClickOnTwoDiagramElementsToolVariables,
  GQLRepresentationDescription,
  GQLTool,
} from './ConnectorContextualMenu.types';
import { useConnector } from './useConnector';

const useStyle = makeStyles(() => ({
  iconContainer: {
    position: 'relative',
    width: '16px',
    height: '16px',
  },
  icon: {
    position: 'absolute',
    top: 0,
    left: 0,
  },
}));

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

export const ConnectorContextualMenu = ({}: ConnectorContextualMenuProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { connection, onConnectorContextualMenuClose } = useConnector();

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const classes = useStyle();

  const { addMessages, addErrorMessage } = useMultiToast();

  const connectionSource: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.source}"]`)
    : null;

  const connectionTarget: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.target}"]`)
    : null;
  let connectionTargetHandle: HTMLElement | null = null;
  if (connection && connectionTarget) {
    connectionTargetHandle = connectionTarget.querySelector(`[data-handleid="${connection.targetHandle}"]`);
  }

  const sourceDiagramElementId = connectionSource?.dataset.id ?? '';
  const targetDiagramElementId = connectionTarget?.dataset.id ?? '';

  const variables: GetConnectorToolsVariables = {
    editingContextId,
    representationId: diagramId,
    sourceDiagramElementId,
    targetDiagramElementId,
  };
  const { data, error } = useQuery<GetConnectorToolsData, GetConnectorToolsVariables>(getConnectorToolsQuery, {
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
      const isErrorPayload = payload.__typename === 'ErrorPayload';
      if (isErrorPayload) {
        const errorPayload = payload as GQLErrorPayload;
        addMessages(errorPayload.messages);
      } else {
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
    if (!invokeSingleClickOnTwoDiagramElementToolCalled && connectorTools.length === 1 && connectorTools[0]) {
      invokeTool(connectorTools[0]);
    }
  }, [connectorTools]);

  if (!data || connectorTools.length <= 1) {
    return null;
  }
  return (
    <Menu
      open={connectionSource !== null && connectionTarget !== null && connectionTargetHandle !== null}
      onClose={onShouldConnectorContextualMenuClose}
      anchorEl={connectionTargetHandle}>
      {connectorTools.map((tool) => (
        <MenuItem key={tool.id} onClick={() => invokeTool(tool)}>
          <ListItemIcon>
            <div className={classes.iconContainer}>
              {tool.iconURL.map((icon, index) => (
                <img
                  height="16"
                  width="16"
                  key={index}
                  alt={tool.label}
                  title={tool.label}
                  src={httpOrigin + icon}
                  className={classes.icon}
                  style={{ zIndex: index }}></img>
              ))}
            </div>
          </ListItemIcon>
          <Typography>{tool.label}</Typography>
        </MenuItem>
      ))}
    </Menu>
  );
};
