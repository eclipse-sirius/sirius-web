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
// Required because Sprotty uses Inversify and both frameworks are written in TypeScript with experimental features.
import 'reflect-metadata';
import { TYPES } from 'sprotty';
import {
  machine,
  EMPTY__STATE,
  LOADING__STATE,
  READY__STATE,
  COMPLETE__STATE,
  INITIALIZE__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_ERROR_MESSAGE__ACTION,
  HANDLE_COMPLETE__ACTION,
  SET_ACTIVE_TOOL__ACTION,
  SET_CONTEXTUAL_PALETTE__ACTION,
  SET_SOURCE_ELEMENT__ACTION,
  SET_CURRENT_ROOT__ACTION,
  SET_CONTEXTUAL_MENU__ACTION,
  SELECTIONS__ACTION,
  SELECTED_ELEMENTS__ACTION,
  SWITCH_REPRESENTATION__ACTION,
  SELECT_ZOOM_LEVEL__ACTION,
  SET_TOOL_SECTIONS__ACTION,
  SET_DEFAULT_TOOL__ACTION,
} from 'diagram/machine';
import { createDependencyInjectionContainer } from 'diagram/sprotty/DependencyInjection';
import { SIRIUS_TYPES } from 'diagram/sprotty/Types';
import { IMutations } from 'diagram/sprotty/IMutations';
import { IState } from 'diagram/sprotty/IState';
import { ISetState } from 'diagram/sprotty/ISetState';

export const INITIAL_ROOT = {
  type: 'NONE',
  id: 'ROOT',
};

export const initialState = {
  viewState: EMPTY__STATE,
  displayedRepresentationId: undefined,
  modelSource: undefined,
  diagram: undefined,
  diagramInternalState: undefined,
  activeTool: undefined,
  toolSections: [],
  contextualPalette: undefined,
  contextualMenu: undefined,
  latestSelections: [],
  newSelections: [],
  zoomLevel: undefined,
  subscribers: [],
  message: undefined,
  errorMessage: undefined,
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
    case HANDLE_ERROR_MESSAGE__ACTION:
      state = handleErrorMessageAction(prevState, action);
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
    case SET_SOURCE_ELEMENT__ACTION:
      state = selectSourceElementAction(prevState, action);
      break;
    case SET_CURRENT_ROOT__ACTION:
      state = setCurrentRootAction(prevState, action);
      break;
    case SET_CONTEXTUAL_PALETTE__ACTION:
      state = setContextualPaletteAction(prevState, action);
      break;
    case SET_CONTEXTUAL_MENU__ACTION:
      state = setContextualMenuAction(prevState, action);
      break;
    case SET_TOOL_SECTIONS__ACTION:
      state = setToolSectionsAction(prevState, action);
      break;
    case SET_DEFAULT_TOOL__ACTION:
      state = setDefaultToolAction(prevState, action);
      break;
    case SELECTIONS__ACTION:
      state = selectionsAction(prevState, action);
      break;
    case SELECTED_ELEMENTS__ACTION:
      state = selectedElementsAction(prevState, action);
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
    modelSource: undefined,
    diagram: undefined,
    diagramInternalState: undefined,
    activeTool: undefined,
    toolSections: [],
    contextualPalette: undefined,
    contextualMenu: undefined,
    latestSelections: [],
    newSelections: [],
    zoomLevel: '1',
    subscribers: [],
    message: undefined,
    errorMessage: undefined,
  };
};

const initializeAction = (prevState, action) => {
  const { displayedRepresentationId, subscribers } = prevState;
  const {
    diagramDomElement,
    toolSections,
    deleteElements,
    invokeTool,
    editLabel,
    onSelectElements,
    setContextualPalette,
    setContextualMenu,
    setSourceElement,
    setCurrentRoot,
    setActiveTool,
  } = action;

  const container = createDependencyInjectionContainer(diagramDomElement.current.id);

  const modelSource = container.get(TYPES.ModelSource);
  /**
   * Sprotty lifecycle can trigger a graphQL mutation.
   * The MutationAPI class instance binds graphQL mutation functions.
   * Those functions will be available thanks to the inversity injection in Sprotty handlers.
   */
  const mutations = container.get<IMutations>(SIRIUS_TYPES.MUTATIONS);
  mutations.invokeEdgeTool = (tool, sourceElement, targetElement) => invokeTool(tool, sourceElement, targetElement);
  mutations.invokeNodeTool = (tool, targetElement) => invokeTool(tool, targetElement);
  mutations.deleteElements = deleteElements;
  mutations.editLabel = editLabel;
  mutations.onSelectElements = onSelectElements;
  /**
   * Sprotty lifecycle can trigger React setState.
   * The SetState class instance binds DiagramWebSocketContainer setState functions.
   * Those functions will be available thanks to the inversity injection in Sprotty handlers.
   */
  const setState = container.get<ISetState>(SIRIUS_TYPES.SET_STATE);
  setState.setContextualPalette = setContextualPalette;
  setState.setContextualMenu = setContextualMenu;
  setState.setSourceElement = setSourceElement;
  setState.setCurrentRoot = setCurrentRoot;
  setState.setActiveTool = setActiveTool;
  /**
   * Sprotty handlers/listeners can need to read some state values.
   * The State class instance values will be synchronized with the reducer.
   * Actually, we expose: currentRoot, activeTool, sourceElement.
   * Those values will be available thanks to the inversity injection in Sprotty handlers/listeners.
   */
  const diagramInternalState = container.get<IState>(SIRIUS_TYPES.STATE);
  diagramInternalState.currentRoot = INITIAL_ROOT;
  diagramInternalState.activeTool = undefined;
  diagramInternalState.sourceElement = undefined;
  return {
    viewState: READY__STATE,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    toolSections,
    contextualPalette: undefined,
    contextualMenu: undefined,
    diagram: undefined,
    activeTool: undefined,
    latestSelections: undefined,
    newSelections: undefined,
    zoomLevel: '1',
    subscribers,
    message: undefined,
    errorMessage: undefined,
  };
};

const switchToCompleteState = (prevState, message) => {
  const { displayedRepresentationId, subscribers } = prevState;
  return {
    viewState: COMPLETE__STATE,
    displayedRepresentationId,
    modelSource: undefined,
    diagramInternalState: undefined,
    diagram: undefined,
    activeTool: undefined,
    toolSections: [],
    contextualPalette: undefined,
    contextualMenu: undefined,
    latestSelections: [],
    newSelections: [],
    zoomLevel: undefined,
    subscribers,
    message,
    errorMessage: undefined,
  };
};

const handleConnectionErrorAction = (prevState) => {
  return switchToCompleteState(prevState, 'An error has occured while retrieving the content from the server');
};

const handleErrorAction = (prevState, action) => {
  const message = action.message;
  return switchToCompleteState(prevState, message);
};

const handleErrorMessageAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message,
  } = prevState;
  const { errorMessage } = action;
  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    latestSelection,
    newSelection,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  };
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
        modelSource,
        diagramInternalState,
        toolSections,
        contextualPalette,
        contextualMenu,
        activeTool,
        latestSelections,
        newSelections,
        zoomLevel,
        subscribers,
        errorMessage,
      } = prevState;
      const { diagram } = diagramEvent;
      state = {
        viewState: READY__STATE,
        displayedRepresentationId,
        modelSource,
        diagramInternalState,
        diagram,
        toolSections,
        contextualPalette,
        contextualMenu,
        activeTool,
        latestSelections,
        newSelections,
        zoomLevel,
        subscribers,
        message: undefined,
        errorMessage,
      };
    } else if (diagramEvent.__typename === 'SubscribersUpdatedEventPayload') {
      const {
        displayedRepresentationId,
        modelSource,
        diagramInternalState,
        diagram,
        toolSections,
        contextualPalette,
        contextualMenu,
        activeTool,
        latestSelections,
        newSelections,
        zoomLevel,
        errorMessage,
      } = prevState;
      const { subscribers } = diagramEvent;
      state = {
        viewState: READY__STATE,
        displayedRepresentationId,
        modelSource,
        diagramInternalState,
        diagram,
        toolSections,
        contextualPalette,
        contextualMenu,
        activeTool,
        latestSelections,
        newSelections,
        zoomLevel,
        subscribers,
        message: undefined,
        errorMessage,
      };
    }
  }

  return state;
};

const setActiveToolAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  } = prevState;
  const { activeTool } = action;
  diagramInternalState.activeTool = activeTool;
  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  };
};

const selectSourceElementAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  } = prevState;
  const { sourceElement } = action;
  diagramInternalState.sourceElement = sourceElement;
  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  };
};
const setCurrentRootAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  } = prevState;
  const { currentRoot } = action;
  diagramInternalState.currentRoot = currentRoot;
  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  };
};
const setContextualPaletteAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  } = prevState;
  const { contextualPalette } = action;

  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  };
};

const setContextualMenuAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    latestSelections,
    contextualPalette,
    newSelections,
    zoomLevel,
    subscribers,
    message,
  } = prevState;
  const { contextualMenu } = action;

  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
  };
};

const setDefaultToolAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    contextualPalette,
    contextualMenu,
    toolSections,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
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
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    toolSections: newToolSections,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  };
};

const setToolSectionsAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    activeTool,
    diagram,
    contextualPalette,
    contextualMenu,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
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
    modelSource,
    diagramInternalState,
    diagram,
    contextualPalette,
    toolSections: toolSectionsWithDefaults,
    contextualMenu,
    activeTool,
    latestSelections,
    newSelections,
    zoomLevel,
    subscribers,
    message,
    errorMessage,
  };
};

const selectedElementsAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    activeTool,
    newSelections,
    zoomLevel,
    subscribers,
    errorMessage,
  } = prevState;
  const { selections } = action;

  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    activeTool,
    latestSelections: selections,
    newSelections,
    zoomLevel,
    subscribers,
    message: undefined,
    errorMessage,
  };
};

const selectionsAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    activeTool,
    latestSelections,
    newSelections,
    subscribers,
    errorMessage,
  } = prevState;
  const { selections } = action;
  let newSelectionValues;
  if (
    (!latestSelections && !selections) ||
    (latestSelections &&
      selections &&
      latestSelections.length === selections.length &&
      selections.every((selection) => latestSelections.some((latestSelection) => latestSelection.id === selection.id)))
  ) {
    newSelectionValues = newSelections;
  } else {
    newSelectionValues = selections;
  }
  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    activeTool,
    latestSelections: newSelectionValues,
    newSelections: newSelectionValues,
    subscribers,
    zoomLevel: '1',
    message: undefined,
    errorMessage,
  };
};

const selectZoomLevelAction = (prevState, action) => {
  const {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    activeTool,
    latestSelections,
    newSelections,
    subscribers,
    errorMessage,
  } = prevState;
  const { level } = action;

  return {
    viewState,
    displayedRepresentationId,
    modelSource,
    diagramInternalState,
    diagram,
    toolSections,
    contextualPalette,
    contextualMenu,
    activeTool,
    latestSelections,
    newSelections,
    zoomLevel: level,
    subscribers,
    message: undefined,
    errorMessage,
  };
};
