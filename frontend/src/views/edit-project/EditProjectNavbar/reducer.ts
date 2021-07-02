/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
  machine,
  EMPTY__STATE,
  CONTEXTUAL_MENU_DISPLAYED__STATE,
  MODAL_DISPLAYED__STATE,
  REDIRECT__STATE,
  HANDLE_SHOW_CONTEXT_MENU__ACTION,
  HANDLE_CLOSE_CONTEXT_MENU__ACTION,
  HANDLE_SHOW_MODAL__ACTION,
  HANDLE_CLOSE_MODAL__ACTION,
  HANDLE_REDIRECTING__ACTION,
} from './machine';

export const initialState = {
  viewState: EMPTY__STATE,
  to: null,
  modalDisplayed: null,
  projectMenuAnchor: null,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }
  let state = prevState;
  switch (action.type) {
    case HANDLE_SHOW_CONTEXT_MENU__ACTION:
      state = handleShowContextMenuAction(prevState, action);
      break;
    case HANDLE_CLOSE_CONTEXT_MENU__ACTION:
      state = handleCloseContextMenuAction(prevState);
      break;
    case HANDLE_SHOW_MODAL__ACTION:
      state = handleShowModalAction(prevState, action);
      break;
    case HANDLE_CLOSE_MODAL__ACTION:
      state = handleCloseModalAction(prevState);
      break;
    case HANDLE_REDIRECTING__ACTION:
      state = handleRedirectingAction(prevState, action);
      break;
    default:
      console.error(`The action ${action.type} isn't supported`);
  }

  const newSupportedStates = supportedActions[action.type];
  if (newSupportedStates.indexOf(state.viewState) === -1) {
    console.error(`The state ${state.viewState} should not be accessible with the action ${action.type}`);
  }
  return state;
};

const handleShowContextMenuAction = (prevState, action) => {
  const { projectMenuAnchor } = action;
  return { ...prevState, viewState: CONTEXTUAL_MENU_DISPLAYED__STATE, projectMenuAnchor };
};

const handleCloseContextMenuAction = (prevState) => {
  return { ...prevState, viewState: EMPTY__STATE, projectMenuAnchor: null };
};

const handleShowModalAction = (prevState, action) => {
  const { modalDisplayed } = action;
  return {
    ...prevState,
    viewState: MODAL_DISPLAYED__STATE,
    projectMenuAnchor: null,
    modalDisplayed,
  };
};

const handleCloseModalAction = (prevState) => {
  return {
    ...prevState,
    viewState: EMPTY__STATE,
    modalDisplayed: null,
  };
};

const handleRedirectingAction = (prevState, action) => {
  const { to } = action;
  const state = { ...prevState, modalDisplayed: null, viewState: REDIRECT__STATE, to, projectMenuAnchor: null };
  return state;
};
