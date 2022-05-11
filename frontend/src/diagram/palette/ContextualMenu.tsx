/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
import { useQuery } from '@apollo/client';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { Tool } from 'diagram/palette/tool/Tool';
import gql from 'graphql-tag';
import React, { useCallback, useEffect } from 'react';
import styles from './ContextualMenu.module.css';
import { ContextualMenuProps, GQLGetConnectorToolsData, GQLGetConnectorToolsVariables } from './ContextualMenu.types';
import {
  ContextualMenuContext,
  ContextualMenuEvent,
  contextualMenuMachine,
  HandleConnectorToolsResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
} from './ContextualMenuMachine';
import toolSectionStyles from './tool-section/ToolSection.module.css';

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
                imageURL
              }
            }
          }
        }
      }
    }
  }
`;

/**
 * The component used to display a Contextual Menu.
 *
 * @hmarchadour
 */
export const ContextualMenu = ({
  editingContextId,
  representationId,
  sourceDiagramElement,
  targetDiagramElement,
  invokeTool,
  invokeClose,
}: ContextualMenuProps) => {
  const [{ value, context }, dispatch] = useMachine<ContextualMenuContext, ContextualMenuEvent>(contextualMenuMachine);

  const { toast, contextualMenu } = value as SchemaValue;
  const { connectorTools, message } = context;

  const {
    loading: connectorToolsLoading,
    data: connectorToolsData,
    error: connectorToolsError,
  } = useQuery<GQLGetConnectorToolsData, GQLGetConnectorToolsVariables>(getConnectorToolsQuery, {
    variables: {
      editingContextId,
      representationId,
      sourceDiagramElementId: sourceDiagramElement.id,
      targetDiagramElementId: targetDiagramElement.id,
    },
  });

  useEffect(() => {
    if (!connectorToolsLoading) {
      const event: HandleConnectorToolsResultEvent = {
        type: 'HANDLE_CONNECTOR_TOOLS_RESULT',
        result: connectorToolsData,
      };
      dispatch(event);
    }
    if (connectorToolsError) {
      const { message } = connectorToolsError;
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  });

  const onToolClick = useCallback(
    (tool) => {
      invokeTool(tool);
    },
    [invokeTool]
  );

  const menuContent =
    contextualMenu === 'loaded' && connectorTools.length > 0 ? (
      <>
        <div className={styles.menu} attrs-data-testid={`PopupMenu`}>
          <div className={styles.menuEntries}>
            {connectorTools.map((tool) => (
              <Tool tool={tool} thumbnail={false} onClick={onToolClick} key={tool.id} />
            ))}
          </div>
        </div>
        <div className={toolSectionStyles.modal} onClick={() => invokeClose()}></div>
      </>
    ) : null;
  return (
    <>
      {menuContent}
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
