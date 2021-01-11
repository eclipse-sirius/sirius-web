/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
import { createDependencyInjectionContainer } from 'diagram/sprotty/DependencyInjection';
import { SiriusWebWebSocketDiagramServer } from 'diagram/sprotty/WebSocketDiagramServer';
import { MutableRefObject } from 'react';
import { MousePositionTracker, TYPES } from 'sprotty';
import { assign, Machine } from 'xstate';

export interface DiagramWebSocketContainerStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    diagramWebSocketContainer: {
      states: {
        empty: {};
        loading: {};
        ready: {};
        complete: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  diagramWebSocketContainer: 'loading' | 'ready' | 'empty' | 'complete';
};

export interface DiagramWebSocketContainerContext {
  displayedRepresentationId: string;
  diagramServer: any;
  diagram: any;
  toolSections: any[];
  activeTool: any;
  contextualPalette: any;
  latestSelection: any;
  newSelection: any;
  zoomLevel: string;
  subscribers: any[];
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type SwithRepresentationEvent = { type: 'SWITCH_REPRESENTATION'; representationId: string };
export type SetToolSectionsEvent = { type: 'SET_TOOL_SECTIONS'; toolSections: any[] };
export type SetDefaultToolEvent = { type: 'SET_DEFAULT_TOOL'; defaultTool: any };
export type DiagramRefreshedEvent = { type: 'HANDLE_DIAGRAM_REFRESHED'; diagram: any };
export type SubscribersUpdatedEvent = { type: 'HANDLE_SUBSCRIBERS_UPDATED'; subscribers: any };
export type SetActiveToolEvent = { type: 'SET_ACTIVE_TOOL'; activeTool: any };
export type SetContextualPaletteEvent = { type: 'SET_CONTEXTUAL_PALETTE'; contextualPalette: any };
export type SelectionEvent = { type: 'SELECTION'; selection: any };
export type SelectedElementEvent = { type: 'SELECTED_ELEMENT'; selection: any };
export type SelectZoomLevelEvent = { type: 'SELECT_ZOOM_LEVEL'; level: string };
export type CompleteEvent = { type: 'HANDLE_COMPLETE' };

export type InitializeRepresentationEvent = {
  type: 'INITIALIZE';
  diagramDomElement: MutableRefObject<any>;
  deleteElements: any;
  invokeTool: any;
  moveElement: any;
  editLabel: any;
  onSelectElement: any;
  getCursorOn: any;
  setActiveTool: any;
  toolSections: any;
  setContextualPalette: any;
};

export type DiagramWebSocketContainerEvent =
  | ShowToastEvent
  | HideToastEvent
  | SwithRepresentationEvent
  | InitializeRepresentationEvent
  | SetToolSectionsEvent
  | SetDefaultToolEvent
  | DiagramRefreshedEvent
  | SubscribersUpdatedEvent
  | SetActiveToolEvent
  | SetContextualPaletteEvent
  | SelectionEvent
  | SelectedElementEvent
  | SelectZoomLevelEvent
  | CompleteEvent;

export const diagramWebSocketContainerMachine = Machine<
  DiagramWebSocketContainerContext,
  DiagramWebSocketContainerStateSchema,
  DiagramWebSocketContainerEvent
>(
  {
    type: 'parallel',
    context: {
      displayedRepresentationId: null,
      diagramServer: null,
      diagram: null,
      toolSections: [],
      activeTool: null,
      contextualPalette: null,
      latestSelection: null,
      newSelection: null,
      zoomLevel: null,
      subscribers: [],
      message: null,
    },
    states: {
      toast: {
        initial: 'hidden',
        states: {
          hidden: {
            on: {
              SHOW_TOAST: {
                target: 'visible',
                actions: 'setMessage',
              },
            },
          },
          visible: {
            on: {
              HIDE_TOAST: {
                target: 'hidden',
                actions: 'clearMessage',
              },
            },
          },
        },
      },
      diagramWebSocketContainer: {
        initial: 'empty',
        states: {
          empty: {
            on: {
              SWITCH_REPRESENTATION: [
                {
                  target: 'loading',
                },
              ],
            },
          },
          loading: {
            on: {
              INITIALIZE: [
                {
                  target: 'ready',
                  actions: 'initialize',
                },
              ],
            },
          },
          ready: {
            on: {
              SWITCH_REPRESENTATION: [
                {
                  target: 'loading',
                  actions: 'switchRepresentation',
                },
              ],
              SET_TOOL_SECTIONS: [
                {
                  actions: 'setToolSections',
                },
              ],
              SET_DEFAULT_TOOL: [
                {
                  actions: 'setDefaultTool',
                },
              ],
              HANDLE_DIAGRAM_REFRESHED: [
                {
                  actions: 'handleDiagramRefreshed',
                },
              ],
              HANDLE_SUBSCRIBERS_UPDATED: [
                {
                  actions: 'handleSubscribersUpdated',
                },
              ],
              SET_ACTIVE_TOOL: [
                {
                  actions: 'setActiveTool',
                },
              ],
              SET_CONTEXTUAL_PALETTE: [
                {
                  actions: 'setContextualPalette',
                },
              ],
              SELECTION: [
                {
                  actions: 'setSelection',
                },
              ],
              SELECTED_ELEMENT: [
                {
                  actions: 'setSelectedElement',
                },
              ],
              SELECT_ZOOM_LEVEL: [
                {
                  actions: 'selectZoomLevel',
                },
              ],
              HANDLE_COMPLETE: [
                {
                  target: 'complete',
                  actions: 'handleComplete',
                },
              ],
            },
          },
          complete: {
            on: {
              SWITCH_REPRESENTATION: [
                {
                  target: 'loading',
                  actions: 'switchRepresentation',
                },
              ],
            },
          },
        },
      },
    },
  },
  {
    actions: {
      switchRepresentation: assign((_, event) => {
        const { representationId } = event as SwithRepresentationEvent;
        return { displayedRepresentationId: representationId };
      }),
      initialize: assign((_, event) => {
        const {
          diagramDomElement,
          deleteElements,
          invokeTool,
          moveElement,
          editLabel,
          onSelectElement,
          getCursorOn,
          setActiveTool,
          toolSections,
          setContextualPalette,
        } = event as InitializeRepresentationEvent;

        const container = createDependencyInjectionContainer(
          diagramDomElement.current.id,
          onSelectElement,
          getCursorOn,
          setActiveTool
        );
        const diagramServer = <SiriusWebWebSocketDiagramServer>container.get(TYPES.ModelSource);
        /**
         * workaround to inject objects in diagramServer from the injector.
         * We cannot use inversify annotation for now. (and perhaps never)
         */
        diagramServer.setMousePositionTracker(container.get(MousePositionTracker));
        diagramServer.setModelFactory(container.get(TYPES.IModelFactory));
        diagramServer.setLogger(container.get(TYPES.ILogger));

        diagramServer.setEditLabelListener(editLabel);
        diagramServer.setMoveElementListener(moveElement);
        diagramServer.setDeleteElementsListener(deleteElements);
        diagramServer.setInvokeToolListener(invokeTool);
        diagramServer.setContextualPaletteListener(setContextualPalette);

        return {
          diagramServer,
          toolSections,
          contextualPalette: undefined,
          diagram: undefined,
          activeTool: undefined,
          latestSelection: undefined,
          newSelection: undefined,
          zoomLevel: '1',
          message: undefined,
        };
      }),

      setToolSections: assign((_, event) => {
        const { toolSections } = event as SetToolSectionsEvent;

        let toolSectionsWithDefaults = toolSections;
        if (toolSections) {
          toolSectionsWithDefaults = toolSections.map((toolSection) => {
            if (toolSection.tools && toolSection.tools.length > 0) {
              return { ...toolSection, defaultTool: toolSection.tools[0] };
            } else {
              return toolSection;
            }
          });
        }

        return { toolSections: toolSectionsWithDefaults };
      }),

      handleDiagramRefreshed: assign((_, event) => {
        const { diagram } = event as DiagramRefreshedEvent;
        return { diagram };
      }),

      handleSubscribersUpdated: assign((_, event) => {
        const { subscribers } = event as SubscribersUpdatedEvent;
        return { subscribers };
      }),

      setActiveTool: assign((_, event) => {
        const { activeTool } = event as SetActiveToolEvent;
        return { activeTool };
      }),
      setContextualPalette: assign((_, event) => {
        const { contextualPalette } = event as SetContextualPaletteEvent;
        return { contextualPalette };
      }),

      setSelection: assign((context, event) => {
        const { selection } = event as SelectionEvent;
        let newSelectionValue;
        if ((!context.latestSelection && !selection) || context.latestSelection?.id === selection?.id) {
          newSelectionValue = context.newSelection;
        } else {
          newSelectionValue = selection;
        }
        return {
          latestSelection: newSelectionValue,
          newSelection: newSelectionValue,
          zoomLevel: '1',
        };
      }),
      setSelectedElement: assign((_, event) => {
        const { selection } = event as SelectedElementEvent;
        return { latestSelection: selection };
      }),
      selectZoomLevel: assign((_, event) => {
        const { level } = event as SelectZoomLevelEvent;
        return { zoomLevel: level };
      }),

      handleComplete: assign((_) => {
        return {
          diagramServer: undefined,
          diagram: undefined,
          toolSections: [],
          contextualPalette: undefined,
          activeTool: undefined,
          latestSelection: undefined,
          newSelection: undefined,
          zoomLevel: undefined,
        };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_) => {
        return { message: null };
      }),
    },
  }
);
