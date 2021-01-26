/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
  COMPLETE__STATE,
  EMPTY__STATE,
  HANDLE_COMPLETE__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_ERROR__ACTION,
  INITIALIZE__ACTION,
  LOADING__STATE,
  machine,
  READY__STATE,
  SELECTED_ELEMENT__ACTION,
  SELECTION__ACTION,
  SELECT_ZOOM_LEVEL__ACTION,
  SET_ACTIVE_TOOL__ACTION,
  SET_CONTEXTUAL_PALETTE__ACTION,
  SET_DEFAULT_TOOL__ACTION,
  SET_TOOL_SECTIONS__ACTION,
  SWITCH_REPRESENTATION__ACTION,
} from 'diagram/machine';
import { createDependencyInjectionContainer } from 'diagram/sprotty/DependencyInjection';
import { SiriusWebWebSocketDiagramServer } from 'diagram/sprotty/WebSocketDiagramServer';
import { MousePositionTracker, TYPES } from 'sprotty';

export const initialState = {
  viewState: EMPTY__STATE,
  displayedRepresentationId: undefined,
  diagramServer: undefined,
  diagram: undefined,
  toolSections: [],
  activeTool: undefined,
  contextualPalette: undefined,
  latestSelection: undefined,
  newSelection: undefined,
  zoomLevel: undefined,
  subscribers: [],
  message: undefined,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }

  let state = prevState;
  switch (action.type) {
    case SWITCH_REPRESENTATION__ACTION:
      state = switchRepresentationAction(action);
      break;
    case INITIALIZE__ACTION:
      state = initializeAction(prevState, action);
      break;
    case HANDLE_CONNECTION_ERROR__ACTION:
      state = handleConnectionErrorAction(prevState);
      break;
    case HANDLE_ERROR__ACTION:
      state = handleErrorAction(prevState, action);
      break;
    case HANDLE_COMPLETE__ACTION:
      state = handleCompleteAction(prevState);
      break;
    case HANDLE_DATA__ACTION:
      state = handleDataAction(prevState, action);
      break;
    case SET_ACTIVE_TOOL__ACTION:
      state = setActiveToolAction(prevState, action);
      break;
    case SET_TOOL_SECTIONS__ACTION:
      state = setToolSectionsAction(prevState, action);
      break;
    case SET_CONTEXTUAL_PALETTE__ACTION:
      state = setContextualPaletteAction(prevState, action);
      break;
    case SET_DEFAULT_TOOL__ACTION:
      state = setDefaultToolAction(prevState, action);
      break;
    case SELECTION__ACTION:
      state = selectionAction(prevState, action);
      break;
    case SELECTED_ELEMENT__ACTION:
      state = selectedElementAction(prevState, action);
      break;
    case SELECT_ZOOM_LEVEL__ACTION:
      state = selectZoomLevelAction(prevState, action);
      break;
    default:
      state = prevState;
      break;
  }

  const newSupportedStates = supportedActions[action.type];
  if (!newSupportedStates || newSupportedStates.indexOf(state.viewState) === -1) {
    console.error(`The state ${state.viewState} should not be accessible with the action ${action.type}`);
  }

  return state;
};

const switchRepresentationAction = (action) => {
  return {
    viewState: LOADING__STATE,
    displayedRepresentationId: action.representationId,
    diagramServer: undefined,
    diagram: undefined,
    toolSections: [],
    activeTool: undefined,
    contextualPalette: undefined,
    latestSelection: undefined,
    newSelection: undefined,
    zoomLevel: '1',
    subscribers: [],
    message: undefined,
  };
};

const initializeAction = (prevState, action) => {
  const { displayedRepresentationId, subscribers } = prevState;
  const {
    diagramDomElement,
    deleteElements,
    invokeTool,
    editLabel,
    onSelectElement,
    getCursorOn,
    setActiveTool,
    toolSections,
    setContextualPalette,
  } = action;

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
  diagramServer.setDeleteElementsListener(deleteElements);
  diagramServer.setInvokeToolListener(invokeTool);
  diagramServer.setContextualPaletteListener(setContextualPalette);

  return {
    viewState: READY__STATE,
    displayedRepresentationId,
    diagramServer,
    toolSections,
    contextualPalette: undefined,
    diagram: undefined,
    activeTool: undefined,
    latestSelection: undefined,
    newSelection: undefined,
    zoomLevel: '1',
    subscribers,
    message: undefined,
  };
};

const switchToCompleteState = (prevState, message) => {
  const { displayedRepresentationId, subscribers } = prevState;
  return {
    viewState: COMPLETE__STATE,
    displayedRepresentationId,
    diagramServer: undefined,
    diagram: undefined,
    toolSections: [],
    contextualPalette: undefined,
    activeTool: undefined,
    latestSelection: undefined,
    newSelection: undefined,
    zoomLevel: undefined,
    subscribers,
    message,
  };
};

const handleConnectionErrorAction = (prevState) => {
  return switchToCompleteState(prevState, 'An error has occured while retrieving the content from the server');
};

const handleErrorAction = (prevState, action) => {
  const message = action.message;
  return switchToCompleteState(prevState, message);
};

const handleCompleteAction = (prevState) => {
  return switchToCompleteState(prevState, 'The diagram does not exist');
};

const handleDataAction = (prevState, action) => {
  const { message } = action;

  let state = prevState;
  if (message.data && message.data.diagramEvent) {
    const { diagramEvent } = message.data;
    if (diagramEvent.__typename === 'DiagramRefreshedEventPayload') {
      const {
        displayedRepresentationId,
        diagramServer,
        toolSections,
        contextualPalette,
        activeTool,
        latestSelection,
        newSelection,
        zoomLevel,
        subscribers,
      } = prevState;
      const { diagram } = diagramEvent;
      state = {
        viewState: READY__STATE,
        displayedRepresentationId,
        diagramServer,
        diagram,
        toolSections,
        contextualPalette,
        activeTool,
        latestSelection,
        newSelection,
        zoomLevel,
        subscribers,
        message: undefined,
      };
    } else if (diagramEvent.__typename === 'SubscribersUpdatedEventPayload') {
      const {
        displayedRepresentationId,
        diagramServer,
        diagram,
        toolSections,
        contextualPalette,
        activeTool,
        latestSelection,
        newSelection,
        zoomLevel,
      } = prevState;
      const { subscribers } = diagramEvent;
      state = {
        viewState: READY__STATE,
        displayedRepresentationId,
        diagramServer,
        diagram,
        toolSections,
        contextualPalette,
        activeTool,
        latestSelection,
        newSelection,
        zoomLevel,
        subscribers,
        message: undefined,
      };
    }
  }

  return state;
};

const setActiveToolAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
  } = prevState;
  const { activeTool } = action;

  return {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    activeTool,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message: undefined,
  };
};
const setContextualPaletteAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    diagramServer,
    activeTool,
    diagram,
    toolSections,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message,
  } = prevState;
  const { contextualPalette } = action;

  return {
    viewState,
    displayedRepresentationId,
    diagramServer,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message,
  };
};

const setDefaultToolAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    diagramServer,
    activeTool,
    diagram,
    contextualPalette,
    toolSections,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message,
  } = prevState;
  const { defaultTool } = action;
  let newToolSections;
  if (toolSections) {
    newToolSections = [];
    toolSections.forEach((toolSection) => {
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

  return {
    viewState,
    displayedRepresentationId,
    diagramServer,
    activeTool,
    diagram,
    toolSections: newToolSections,
    contextualPalette,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message,
  };
};

const setToolSectionsAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    diagramServer,
    activeTool,
    diagram,
    contextualPalette,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message,
  } = prevState;
  const { toolSections } = action;

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

  return {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    contextualPalette,
    toolSections: toolSectionsWithDefaults,
    activeTool,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message,
  };
};

const selectedElementAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    activeTool,
    newSelection,
    zoomLevel,
    subscribers,
  } = prevState;
  const { selection } = action;

  return {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    activeTool,
    latestSelection: selection,
    newSelection,
    zoomLevel,
    subscribers,
    message: undefined,
  };
};

const selectionAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    activeTool,
    latestSelection,
    newSelection,
    subscribers,
  } = prevState;
  const { selection } = action;
  let newSelectionValue;
  if ((!latestSelection && !selection) || latestSelection?.id === selection?.id) {
    newSelectionValue = newSelection;
  } else {
    newSelectionValue = selection;
  }
  return {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    activeTool,
    latestSelection: newSelectionValue,
    newSelection: newSelectionValue,
    subscribers,
    zoomLevel: '1',
    message: undefined,
  };
};

const selectZoomLevelAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    activeTool,
    latestSelection,
    newSelection,
    subscribers,
  } = prevState;
  const { level } = action;

  return {
    viewState,
    displayedRepresentationId,
    diagramServer,
    diagram,
    toolSections,
    contextualPalette,
    activeTool,
    latestSelection,
    newSelection,
    zoomLevel: level,
    subscribers,
    message: undefined,
  };
};
