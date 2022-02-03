/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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
import {
  CreateEdgeTool,
  Menu,
  Palette,
  Position,
  Subscriber,
  Tool,
  ToolSection,
} from 'diagram/DiagramWebSocketContainer.types';
import { createDependencyInjectionContainer } from 'diagram/sprotty/DependencyInjection';
import { DiagramServer } from 'diagram/sprotty/DiagramServer';
import { GQLDiagram } from 'index';
import { MutableRefObject } from 'react';
import { MousePositionTracker, SModelElement, TYPES } from 'sprotty';
import { v4 as uuid } from 'uuid';
import { Selection } from 'workbench/Workbench.types';
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
    selectionDialog: {
      states: {
        visible: {};
        hidden: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  diagramWebSocketContainer: 'loading' | 'ready' | 'empty' | 'complete';
  selectionDialog: 'visible' | 'hidden';
};

export interface DiagramWebSocketContainerContext {
  id: string;
  displayedRepresentationId: string | null;
  diagramServer: DiagramServer;
  diagram: GQLDiagram;
  toolSections: ToolSection[];
  activeTool: Tool | null;
  activeConnectorTools: CreateEdgeTool[];
  contextualPalette: Palette | null;
  contextualMenu: Menu | null;
  latestSelection: Selection;
  newSelection: Selection;
  zoomLevel: string;
  subscribers: Subscriber[];
  message: string | null;
  selectedObjectId: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type ShowSelectionDialogEvent = { type: 'SHOW_SELECTION_DIALOG'; activeTool: Tool };
export type CloseSelectionDialogEvent = { type: 'CLOSE_SELECTION_DIALOG' };
export type HandleSelectedObjectInSelectionDialogEvent = {
  type: 'HANDLE_SELECTED_OBJECT_IN_SELECTION_DIALOG';
  selectedObjectId: string;
};
export type ResetSelectedObjectInSelectionDialogEvent = { type: 'RESET_SELECTED_OBJECT_IN_SELECTION_DIALOG' };
export type SwithRepresentationEvent = { type: 'SWITCH_REPRESENTATION'; representationId: string };
export type SetDefaultToolEvent = { type: 'SET_DEFAULT_TOOL'; defaultTool: Tool };
export type DiagramRefreshedEvent = { type: 'HANDLE_DIAGRAM_REFRESHED'; diagram: GQLDiagram };
export type SubscribersUpdatedEvent = { type: 'HANDLE_SUBSCRIBERS_UPDATED'; subscribers: Subscriber[] };
export type ResetToolsEvent = { type: 'RESET_TOOLS' };
export type SetActiveToolEvent = { type: 'SET_ACTIVE_TOOL'; activeTool: Tool | null };
export type SetActiveConnectorToolsEvent = { type: 'SET_ACTIVE_CONNECTOR_TOOLS'; tools: CreateEdgeTool[] };
export type SetContextualPaletteEvent = { type: 'SET_CONTEXTUAL_PALETTE'; contextualPalette: Palette | null };
export type SetContextualMenuEvent = { type: 'SET_CONTEXTUAL_MENU'; contextualMenu: Menu | null };
export type SelectionEvent = { type: 'SELECTION'; selection: Selection };
export type SelectedElementEvent = { type: 'SELECTED_ELEMENT'; selection: Selection };
export type SelectZoomLevelEvent = { type: 'SELECT_ZOOM_LEVEL'; level: string };
export type CompleteEvent = { type: 'HANDLE_COMPLETE' };

export type InitializeRepresentationEvent = {
  type: 'INITIALIZE';
  diagramDomElement: MutableRefObject<any>;
  deleteElements: any;
  invokeTool: any;
  moveElement: any;
  resizeElement: any;
  editLabel: any;
  onSelectElement: (selectedElement: SModelElement, diagramServer: DiagramServer, position: Position) => void;
  getCursorOn: any;
  setActiveTool: (tool: Tool) => void;
  toolSections: ToolSection[];
  setContextualPalette: (contextualPalette: Palette) => void;
  setContextualMenu: (contextualMenu: Menu) => void;
  httpOrigin: string;
};

export type DiagramWebSocketContainerEvent =
  | ShowToastEvent
  | HideToastEvent
  | ShowSelectionDialogEvent
  | CloseSelectionDialogEvent
  | HandleSelectedObjectInSelectionDialogEvent
  | ResetSelectedObjectInSelectionDialogEvent
  | SwithRepresentationEvent
  | InitializeRepresentationEvent
  | SetDefaultToolEvent
  | DiagramRefreshedEvent
  | SubscribersUpdatedEvent
  | ResetToolsEvent
  | SetActiveToolEvent
  | SetActiveConnectorToolsEvent
  | SetContextualPaletteEvent
  | SetContextualMenuEvent
  | SelectionEvent
  | SelectedElementEvent
  | SelectZoomLevelEvent
  | CompleteEvent;

const isSetActiveToolEvent = (event: DiagramWebSocketContainerEvent): event is SetActiveToolEvent =>
  event.type === 'SET_ACTIVE_TOOL';
const isSetActiveConnectorToolsEvent = (event: DiagramWebSocketContainerEvent): event is SetActiveConnectorToolsEvent =>
  event.type === 'SET_ACTIVE_CONNECTOR_TOOLS';
const isShowSelectionDialogEvent = (event: DiagramWebSocketContainerEvent): event is ShowSelectionDialogEvent =>
  event.type === 'SHOW_SELECTION_DIALOG';

export const diagramWebSocketContainerMachine = Machine<
  DiagramWebSocketContainerContext,
  DiagramWebSocketContainerStateSchema,
  DiagramWebSocketContainerEvent
>(
  {
    type: 'parallel',
    context: {
      id: uuid(),
      displayedRepresentationId: null,
      diagramServer: null,
      diagram: null,
      toolSections: [],
      activeTool: null,
      activeConnectorTools: [],
      contextualPalette: null,
      contextualMenu: null,
      latestSelection: { entries: [] },
      newSelection: { entries: [] },
      zoomLevel: '1',
      subscribers: [],
      message: null,
      selectedObjectId: null,
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
                  actions: 'switchRepresentation',
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
              SWITCH_REPRESENTATION: [
                {
                  target: 'loading',
                  actions: 'switchRepresentation',
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
              RESET_TOOLS: [
                {
                  actions: 'resetTools',
                },
              ],
              SET_ACTIVE_TOOL: [
                {
                  actions: 'setActiveTool',
                },
              ],
              SET_ACTIVE_CONNECTOR_TOOLS: [
                {
                  actions: 'setActiveConnectorTools',
                },
              ],
              SET_CONTEXTUAL_PALETTE: [
                {
                  actions: 'setContextualPalette',
                },
              ],
              SET_CONTEXTUAL_MENU: [
                {
                  actions: 'setContextualMenu',
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
      selectionDialog: {
        initial: 'hidden',
        states: {
          hidden: {
            on: {
              SHOW_SELECTION_DIALOG: {
                target: 'visible',
                actions: 'setActiveTool',
              },
              RESET_SELECTED_OBJECT_IN_SELECTION_DIALOG: {
                target: 'hidden',
                actions: 'resetSelectedObjectInSelectionDialog',
              },
            },
          },
          visible: {
            on: {
              CLOSE_SELECTION_DIALOG: {
                target: 'hidden',
                actions: 'closeSelectionDialog',
              },
              HANDLE_SELECTED_OBJECT_IN_SELECTION_DIALOG: {
                target: 'hidden',
                actions: 'handleSelectedObjectInSelectionDialog',
              },
              RESET_SELECTED_OBJECT_IN_SELECTION_DIALOG: {
                target: 'visible',
                actions: 'resetSelectedObjectInSelectionDialog',
              },
              SWITCH_REPRESENTATION: {
                target: 'hidden',
                actions: 'closeSelectionDialog',
              },
              HANDLE_COMPLETE: {
                target: 'hidden',
                actions: 'closeSelectionDialog',
              },
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
        return { id: uuid(), displayedRepresentationId: representationId };
      }),
      initialize: assign((_, event) => {
        const {
          diagramDomElement,
          deleteElements,
          invokeTool,
          moveElement,
          resizeElement,
          editLabel,
          onSelectElement,
          getCursorOn,
          setActiveTool,
          toolSections,
          setContextualPalette,
          setContextualMenu,
          httpOrigin,
        } = event as InitializeRepresentationEvent;

        const container = createDependencyInjectionContainer(diagramDomElement.current.id, getCursorOn);
        const diagramServer = <DiagramServer>container.get(TYPES.ModelSource);

        /**
         * workaround to inject objects in diagramServer from the injector.
         * We cannot use inversify annotation for now. (and perhaps never)
         */
        diagramServer.setMousePositionTracker(container.get(MousePositionTracker));
        diagramServer.setModelFactory(container.get(TYPES.IModelFactory));
        diagramServer.setLogger(container.get(TYPES.ILogger));

        diagramServer.setEditLabelListener(editLabel);
        diagramServer.setMoveElementListener(moveElement);
        diagramServer.setResizeElementListener(resizeElement);
        diagramServer.setDeleteElementsListener(deleteElements);
        diagramServer.setInvokeToolListener(invokeTool);
        diagramServer.setContextualPaletteListener(setContextualPalette);
        diagramServer.setContextualMenuListener(setContextualMenu);
        diagramServer.setHttpOrigin(httpOrigin);
        diagramServer.setActiveToolListener(setActiveTool);
        diagramServer.setOnSelectElementListener(onSelectElement);

        return {
          diagramServer,
          toolSections,
          contextualPalette: undefined,
          contextualMenu: undefined,
          diagram: undefined,
          activeTool: undefined,
          activeConnectorTools: [],
          latestSelection: { entries: [] },
          newSelection: { entries: [] },
          zoomLevel: '1',
          message: undefined,
          selectedObjectId: undefined,
        };
      }),

      setDefaultTool: assign((context, event) => {
        const { defaultTool } = event as SetDefaultToolEvent;
        let newToolSections;
        if (context.toolSections) {
          newToolSections = [];
          context.toolSections.forEach((toolSection) => {
            const newToolSection = Object.assign({}, toolSection);
            newToolSection.tools.forEach((tool) => {
              if (tool.id === defaultTool.id) {
                newToolSection.defaultTool = defaultTool;
                return;
              }
            });
            newToolSections.push(newToolSection);
          });
        }
        return { toolSections: newToolSections };
      }),

      handleDiagramRefreshed: assign((_, event) => {
        const { diagram } = event as DiagramRefreshedEvent;
        return { diagram };
      }),

      handleSubscribersUpdated: assign((_, event) => {
        const { subscribers } = event as SubscribersUpdatedEvent;
        return { subscribers };
      }),

      resetTools: assign((_, event) => {
        return {
          contextualPalette: undefined,
          contextualMenu: undefined,
          activeTool: undefined,
          activeConnectorTools: [],
        };
      }),

      setActiveTool: assign((_, event) => {
        if (isSetActiveToolEvent(event) || isShowSelectionDialogEvent(event)) {
          const { activeTool } = event;
          return { activeTool };
        }
        return {};
      }),

      setActiveConnectorTools: assign((_, event) => {
        if (isSetActiveConnectorToolsEvent(event)) {
          const { tools } = event;
          return { activeConnectorTools: tools };
        }
        return {};
      }),
      setContextualPalette: assign((_, event) => {
        const { contextualPalette } = event as SetContextualPaletteEvent;
        return { contextualPalette };
      }),
      setContextualMenu: assign((_, event) => {
        const { contextualMenu } = event as SetContextualMenuEvent;
        return { contextualMenu };
      }),

      setSelection: assign((context, event) => {
        const { selection } = event as SelectionEvent;
        let newSelectionValue: Selection;
        if (
          (context.latestSelection.entries.length <= 0 && selection.entries.length <= 0) ||
          context.latestSelection.entries[0]?.id === selection.entries[0]?.id
        ) {
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
      handleSelectedObjectInSelectionDialog: assign((_, event) => {
        const { selectedObjectId } = event as HandleSelectedObjectInSelectionDialogEvent;
        return { selectedObjectId: selectedObjectId };
      }),
      resetSelectedObjectInSelectionDialog: assign((_) => {
        return { selectedObjectId: null };
      }),
      closeSelectionDialog: assign((_, event) => {
        return { activeTool: null, selectedObjectId: null };
      }),
      handleComplete: assign((_) => {
        return {
          diagramServer: undefined,
          diagram: undefined,
          toolSections: [],
          contextualPalette: undefined,
          contextualMenu: undefined,
          activeTool: undefined,
          activeConnectorTools: [],
          latestSelection: { entries: [] },
          newSelection: { entries: [] },
          zoomLevel: undefined,
          selectedObjectId: undefined,
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
