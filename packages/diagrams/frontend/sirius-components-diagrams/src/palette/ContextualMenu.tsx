/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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
import { Toast } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useCallback, useEffect } from 'react';
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
import { Tool } from './tool/Tool';

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

const useContextualMenuStyles = makeStyles(() => ({
  contextualMenu: {
    background: '#fafafa',
    border: '1px solid #d1dadb',
    boxShadow: '0px 2px 5px #002b3c40',
    borderRadius: '2px',
    display: 'grid',
    gridTemplateRows: '8px 1fr 8px',
    gridTemplateColumns: '8px 1fr 8px',
    color: '#002d37',
    position: 'fixed',
    zIndex: 2,
  },
  menuEntries: {
    gridColumnStart: 2,
    gridRowStart: 2,
    display: 'flex',
    flexDirection: 'column',
  },
  modal: {
    position: 'fixed',
    zIndex: 1,
    left: 0,
    top: 0,
    width: '100%',
    height: '100%',
    overflow: 'auto',
  },
}));

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

  const classes = useContextualMenuStyles();

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
        <div className={classes.contextualMenu} attrs-data-testid={`PopupMenu`}>
          <div className={classes.menuEntries}>
            {connectorTools.map((tool) => (
              <Tool tool={tool} onClick={onToolClick} key={tool.id} />
            ))}
          </div>
        </div>
        <div className={classes.modal} onClick={() => invokeClose()}></div>
      </>
    ) : null;
  return (
    <>
      {menuContent}
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
