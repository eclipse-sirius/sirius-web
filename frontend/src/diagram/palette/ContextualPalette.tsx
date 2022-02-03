/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { GQLDeletionPolicy } from 'diagram/DiagramWebSocketContainer.types';
import {
  ContextualPaletteProps,
  ContextualPaletteStyleProps,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLTool,
} from 'diagram/palette/ContextualPalette.types';
import {
  ContextualPaletteContext,
  ContextualPaletteEvent,
  contextualPaletteMachine,
  HandleToolSectionsResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
} from 'diagram/palette/ContextualPaletteMachine';
import { ToolSection } from 'diagram/palette/tool-section/ToolSection';
import { Tool } from 'diagram/palette/tool/Tool';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import closeImagePath from './icons/close.svg';
import connectorImagePath from './icons/connector.svg';

export const getToolSectionsQuery = gql`
  query getToolSections($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              toolSections(diagramElementId: $diagramElementId) {
                id
                label
                imageURL
                tools {
                  __typename
                  id
                  label
                  imageURL
                  ... on CreateNodeTool {
                    targetDescriptions {
                      id
                    }
                    appliesToDiagramRoot
                    selectionDescriptionId
                  }
                  ... on CreateEdgeTool {
                    edgeCandidates {
                      sources {
                        id
                      }
                      targets {
                        id
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
`;

const connectorTool = {
  id: 'connector',
  type: 'connector',
  imageURL: connectorImagePath,
  label: 'Connector',
};
const closeTool = {
  id: 'close',
  type: 'close',
  imageURL: closeImagePath,
  label: 'Close',
};

const useContextualPaletteStyle = makeStyles((theme) => ({
  toolbar: {
    background: theme.palette.background.paper,
    border: '1px solid #d1dadb',
    boxShadow: '0px 2px 5px #002b3c40',
    borderRadius: '2px',
    zIndex: 2,
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
    width: ({ toolSectionsCount }: ContextualPaletteStyleProps) => `${Math.min(toolSectionsCount, 10) * 36 + 2 * 1}px`,
  },
  toolEntries: {
    display: 'grid',
    gridTemplateColumns: ({ toolSectionsCount }: ContextualPaletteStyleProps) =>
      `repeat(${Math.min(toolSectionsCount, 10)}, 36px)`,
    gridTemplateRows: '28px',
    gridAutoRows: '28px',
    placeItems: 'center',
  },
  connectorTool: {},
  toolSection: {},
  close: {
    gridRowStart: '1',
    gridRowEnd: '2',
    gridColumnStart: '-2',
    gridColumnEnd: '-1',
  },
}));

/**
 * The component used to display a Contextual Palette.
 *
 * @hmarchadour
 */
export const ContextualPalette = ({
  editingContextId,
  representationId,
  diagramElement,
  invokeTool,
  invokeConnectorTool,
  invokeLabelEdit,
  invokeDelete,
  invokeClose,
}: ContextualPaletteProps) => {
  const [{ value, context }, dispatch] = useMachine<ContextualPaletteContext, ContextualPaletteEvent>(
    contextualPaletteMachine
  );
  const { toast, contextualPalette } = value as SchemaValue;
  const { toolSections, message } = context;

  const {
    loading: toolSectionsLoading,
    data: toolSectionsData,
    error: toolSectionsError,
  } = useQuery<GQLGetToolSectionsData, GQLGetToolSectionsVariables>(getToolSectionsQuery, {
    variables: {
      editingContextId,
      diagramId: representationId,
      diagramElementId: diagramElement.id,
    },
  });

  useEffect(() => {
    if (!toolSectionsLoading) {
      if (toolSectionsData) {
        const event: HandleToolSectionsResultEvent = { type: 'HANDLE_TOOL_SECTIONS_RESULT', result: toolSectionsData };
        dispatch(event);
      }
      if (toolSectionsError) {
        const { message } = toolSectionsError;
        const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
        dispatch(showToastEvent);
      }
    }
  }, [toolSectionsLoading, toolSectionsData, toolSectionsError, dispatch]);

  const atLeastOneEdgeTool = toolSections.some((toolSection) =>
    toolSection.tools.some((tool) => tool.__typename === 'CreateEdgeTool')
  );

  let toolSectionsCount = toolSections.length + 1;
  if (atLeastOneEdgeTool) {
    toolSectionsCount = toolSectionsCount + 1;
  }

  const props: ContextualPaletteStyleProps = { toolSectionsCount };
  const classes = useContextualPaletteStyle(props);

  const toolSectionElements: JSX.Element[] = toolSections.map((toolSection) => {
    const handleToolClick = (tool: GQLTool) => {
      if (tool.id === 'edit') {
        invokeLabelEdit();
      } else if (tool.id === 'semantic-delete') {
        invokeDelete(GQLDeletionPolicy.SEMANTIC);
      } else if (tool.id === 'graphical-delete') {
        invokeDelete(GQLDeletionPolicy.GRAPHICAL);
      } else {
        invokeTool(tool);
      }
    };
    return (
      <div className={classes.toolSection} key={diagramElement.id + toolSection.id}>
        <ToolSection toolSection={toolSection} onToolClick={handleToolClick} />
      </div>
    );
  });

  const paletteContent =
    contextualPalette === 'loaded' ? (
      <div className={classes.toolbar} data-testid="PopupToolbar" key="PopupToolbar">
        <div className={classes.toolEntries}>
          {atLeastOneEdgeTool ? (
            <div className={classes.connectorTool} key="connectorTool">
              <Tool tool={connectorTool} thumbnail={true} onClick={() => invokeConnectorTool(toolSections)} />
            </div>
          ) : null}
          {toolSectionElements}
          <div className={classes.close}>
            <Tool tool={closeTool} thumbnail={true} onClick={() => invokeClose()} />
          </div>
        </div>
      </div>
    ) : null;

  return (
    <>
      {paletteContent}
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
