/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { ApolloError, gql, useMutation, useQuery } from '@apollo/client';
import { Toast } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useCallback, useEffect } from 'react';
import { EditLabelAction, isWithEditableLabel, SModelElement } from 'sprotty';
import { GQLDeletionPolicy } from '../representation/DiagramRepresentation.types';
import { BorderNode, Diagram, Edge, Node, ViewModifier } from '../sprotty/Diagram.types';
import { HIDE_CONTEXTUAL_TOOLBAR_ACTION } from '../sprotty/DiagramServer';
import {
  ContextualPaletteProps,
  ContextualPaletteStyleProps,
  GQLCollapsingState,
  GQLErrorPayload,
  GQLFadeDiagramElementData,
  GQLFadeDiagramElementInput,
  GQLFadeDiagramElementVariables,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLHideDiagramElementData,
  GQLHideDiagramElementInput,
  GQLHideDiagramElementVariables,
  GQLTool,
  GQLToolSection,
  GQLUpdateCollapsingStateData,
  GQLUpdateCollapsingStateInput,
  GQLUpdateCollapsingStateVariables,
} from './ContextualPalette.types';
import {
  ContextualPaletteContext,
  ContextualPaletteEvent,
  contextualPaletteMachine,
  HandleToolSectionsResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
} from './ContextualPaletteMachine';
import closeImagePath from './icons/close.svg';
import connectorImagePath from './icons/connector.svg';
import hideImagePath from './icons/hide.svg';
import fadeImagePath from './icons/tonality.svg';
import { ToolSection } from './tool-section/ToolSection';
import { Tool } from './tool/Tool';

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
                  ... on SingleClickOnDiagramElementTool {
                    targetDescriptions {
                      id
                    }
                    appliesToDiagramRoot
                    selectionDescriptionId
                  }
                  ... on SingleClickOnTwoDiagramElementsTool {
                    candidates {
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

const hideDiagramElementMutation = gql`
  mutation hideDiagramElement($input: HideDiagramElementInput!) {
    hideDiagramElement(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const fadeDiagramElementMutation = gql`
  mutation fadeDiagramElement($input: FadeDiagramElementInput!) {
    fadeDiagramElement(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const updateCollapsingStateMutation = gql`
  mutation updateCollapsingState($input: UpdateCollapsingStateInput!) {
    updateCollapsingState(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const connectorTool = {
  id: 'connector',
  type: 'connector',
  imageURL: connectorImagePath,
  label: 'Connector',
  __typename: 'Connector',
};
const closeTool = {
  id: 'close',
  type: 'close',
  imageURL: closeImagePath,
  label: 'Close',
  __typename: 'Close',
};

const visibilitySection: GQLToolSection = {
  id: 'visibility_section',
  label: 'Visibility Section',
  imageURL: hideImagePath,
  tools: [
    {
      id: 'hide',
      imageURL: hideImagePath,
      label: 'Hide',
      __typename: 'Hide',
    },
    {
      id: 'fade',
      imageURL: fadeImagePath,
      label: 'Fade',
      __typename: 'Fade',
    },
  ],
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
  visibilitySection: {},
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
  diagramServer,
  renameable,
  invokeTool,
  invokeConnectorTool,
  invokeDelete,
  invokeClose,
}: ContextualPaletteProps) => {
  const [{ value, context }, dispatch] = useMachine<ContextualPaletteContext, ContextualPaletteEvent>(
    contextualPaletteMachine
  );
  const { toast, contextualPalette } = value as SchemaValue;
  const { toolSections, message } = context;

  const diagramElementId = diagramElement.id;
  const isRoot: boolean = diagramElement instanceof Diagram;

  const {
    loading: toolSectionsLoading,
    data: toolSectionsData,
    error: toolSectionsError,
  } = useQuery<GQLGetToolSectionsData, GQLGetToolSectionsVariables>(getToolSectionsQuery, {
    variables: {
      editingContextId,
      diagramId: representationId,
      diagramElementId,
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

  const atLeastOneSingleClickOnTwoDiagramElementsTool = toolSections.some((toolSection) => {
    return toolSection.tools.some((tool) => tool.__typename === 'SingleClickOnTwoDiagramElementsTool');
  });

  let invokeLabelEdit;
  if (renameable && isWithEditableLabel(diagramElement)) {
    invokeLabelEdit = () =>
      diagramServer.actionDispatcher.dispatchAll([
        { kind: HIDE_CONTEXTUAL_TOOLBAR_ACTION },
        EditLabelAction.create(diagramElement.editableLabel.id),
      ]);
  }

  const [
    hideElementMutation,
    { loading: hideDiagramElementLoading, data: hideDiagramElementData, error: hideDiagramElementError },
  ] = useMutation<GQLHideDiagramElementData, GQLHideDiagramElementVariables>(hideDiagramElementMutation);

  const hideElements = useCallback(
    (diagramElements: SModelElement[]): void => {
      const elements: Array<Edge | Node | BorderNode> = diagramElements
        .filter((elem) => elem instanceof Edge || elem instanceof Node || elem instanceof BorderNode)
        .map((elem) => elem as Edge | Node | BorderNode);
      const elementIds = elements.map((elt) => elt.id);
      // If at least one selected element is not hidden, we hide all selected elements
      const hide = elements.some((elem) => elem.state !== ViewModifier.Hidden);

      const input: GQLHideDiagramElementInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        elementIds,
        hide,
      };
      hideElementMutation({ variables: { input } });
      invokeClose();
    },
    [editingContextId, representationId, hideElementMutation, invokeClose]
  );

  const [
    fadeElementMutation,
    { loading: fadeDiagramElementLoading, data: fadeDiagramElementData, error: fadeDiagramElementError },
  ] = useMutation<GQLFadeDiagramElementData, GQLFadeDiagramElementVariables>(fadeDiagramElementMutation);

  const fadeElements = useCallback(
    (diagramElements: SModelElement[]): void => {
      const elements: Array<Edge | Node | BorderNode> = diagramElements
        .filter((elem) => elem instanceof Edge || elem instanceof Node || elem instanceof BorderNode)
        .map((elem) => elem as Edge | Node | BorderNode);
      const elementIds = elements.map((elt) => elt.id);
      // If at least one selected element is not hidden, we fade all selected elements
      const fade = elements.some((elem) => elem.state !== ViewModifier.Hidden);

      const input: GQLFadeDiagramElementInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        elementIds,
        fade,
      };
      fadeElementMutation({ variables: { input } });
      invokeClose();
    },
    [editingContextId, representationId, fadeElementMutation, invokeClose]
  );

  const invokeHide = () => hideElements([diagramElement]);
  const invokeFade = () => fadeElements([diagramElement]);

  const [
    collapseExpandMutation,
    {
      loading: collapseExpandDiagramElementLoading,
      data: collapseExpandDiagramElementData,
      error: collapseExpandDiagramElementError,
    },
  ] = useMutation<GQLUpdateCollapsingStateData, GQLUpdateCollapsingStateVariables>(updateCollapsingStateMutation);

  const collapseExpandElement = useCallback(
    (nodeId: string, collapsingState: GQLCollapsingState) => {
      const input: GQLUpdateCollapsingStateInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        diagramElementId: nodeId,
        collapsingState,
      };
      collapseExpandMutation({ variables: { input } });
      invokeClose();
    },
    [editingContextId, representationId, collapseExpandMutation]
  );

  const updateCollapsingState = (collapsingState: GQLCollapsingState) =>
    collapseExpandElement(diagramElement.id, collapsingState);

  let toolSectionsCount = toolSections.length + 1;
  if (atLeastOneSingleClickOnTwoDiagramElementsTool) {
    toolSectionsCount = toolSectionsCount + 1;
  }
  if (!isRoot) {
    toolSectionsCount = toolSectionsCount + 1;
  }

  const isErrorPayload = (payload): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';

  const handleError = useCallback(
    (loading: boolean, data, error: ApolloError) => {
      if (!loading) {
        if (error) {
          const showToastEvent: ShowToastEvent = {
            type: 'SHOW_TOAST',
            message: 'An error has occurred while executing this action, please contact the server administrator',
          };
          dispatch(showToastEvent);
        }
        if (data) {
          const keys = Object.keys(data);
          if (keys.length > 0) {
            const firstKey = keys[0];
            const firstField = data[firstKey];
            if (isErrorPayload(firstField)) {
              const { message } = firstField;
              const showToastEvent: ShowToastEvent = {
                type: 'SHOW_TOAST',
                message,
              };
              dispatch(showToastEvent);
            }
          }
        }
      }
    },
    [dispatch]
  );

  useEffect(() => {
    handleError(hideDiagramElementLoading, hideDiagramElementData, hideDiagramElementError);
  }, [hideDiagramElementLoading, hideDiagramElementData, hideDiagramElementError, handleError]);
  useEffect(() => {
    handleError(fadeDiagramElementLoading, fadeDiagramElementData, fadeDiagramElementError);
  }, [fadeDiagramElementLoading, fadeDiagramElementData, fadeDiagramElementError, handleError]);
  useEffect(() => {
    handleError(
      collapseExpandDiagramElementLoading,
      collapseExpandDiagramElementData,
      collapseExpandDiagramElementError
    );
  }, [
    collapseExpandDiagramElementLoading,
    collapseExpandDiagramElementData,
    collapseExpandDiagramElementError,
    handleError,
  ]);

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
      } else if (tool.id === 'expand') {
        updateCollapsingState(GQLCollapsingState.EXPANDED);
      } else if (tool.id === 'collapse') {
        updateCollapsingState(GQLCollapsingState.COLLAPSED);
      } else {
        invokeTool(tool);
      }
    };

    return (
      <div className={classes.toolSection} key={diagramElementId + toolSection.id}>
        <ToolSection toolSection={toolSection} onToolClick={handleToolClick} />
      </div>
    );
  });

  const invokeVisibilityTool = (tool: GQLTool) => {
    if (tool.id === 'fade') {
      invokeFade();
    } else {
      invokeHide();
    }
  };

  const paletteContent =
    contextualPalette === 'loaded' && !toolSectionsLoading && toolSectionsData ? (
      <div className={classes.toolbar} data-testid="PopupToolbar" key="PopupToolbar">
        <div className={classes.toolEntries}>
          {atLeastOneSingleClickOnTwoDiagramElementsTool ? (
            <div className={classes.connectorTool} key="connectorTool">
              <Tool tool={connectorTool} thumbnail onClick={() => invokeConnectorTool(toolSections)} />
            </div>
          ) : null}
          {toolSectionElements}
          {!isRoot ? (
            <div className={classes.visibilitySection} data-testid="visibilitySection">
              <ToolSection toolSection={visibilitySection} onToolClick={invokeVisibilityTool} />
            </div>
          ) : null}
          <div className={classes.close}>
            <Tool tool={closeTool} thumbnail onClick={() => invokeClose()} />
          </div>
        </div>
      </div>
    ) : null;

  return (
    <>
      {paletteContent}
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
