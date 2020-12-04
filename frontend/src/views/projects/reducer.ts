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
  machine,
  LOADING__STATE,
  EMPTY__STATE,
  ERROR__STATE,
  LOADED__STATE,
  HANDLE_ERROR_FETCHING_PROJECTS__ACTION,
  HANDLE_FETCHED_PROJECTS__ACTION,
  HANDLE_PROJECTS_UPDATED__ACTION,
} from './machine';

export const initialState = {
  viewState: LOADING__STATE,
  projects: [],
  message: undefined,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }

  let state = prevState;
  switch (action.type) {
    case HANDLE_ERROR_FETCHING_PROJECTS__ACTION:
      state = handleErrorFetchingProjectsAction(prevState, action);
      break;
    case HANDLE_FETCHED_PROJECTS__ACTION:
      state = handleFetchedProjectsAction(prevState, action);
      break;
    case HANDLE_PROJECTS_UPDATED__ACTION:
      state = handleProjectsUpdatedAction(prevState, action);
      break;
    default:
      state = prevState;
  }

  const newSupportedStates = supportedActions[action.type];
  if (newSupportedStates.indexOf(state.viewState) === -1) {
    console.error(`The state ${state.viewState} should not be accessible with the action ${action.type}`);
  }
  return state;
};

const handleErrorFetchingProjectsAction = (prevState, action) => {
  const message = 'An unexpected error has occured while retrieving the project, please contact your administrator';
  return { viewState: ERROR__STATE, message };
};

const handleFetchedProjectsAction = (prevState, action) => {
  const { response } = action;

  let state = prevState;
  const { projects } = response.viewer;
  if (projects.length === 0) {
    state = { viewState: EMPTY__STATE };
  } else {
    state = { viewState: LOADED__STATE, projects };
  }
  return state;
};

const handleProjectsUpdatedAction = (prevState, action) => {
  const { response } = action;

  let state = prevState;
  const { projects } = response.viewer;
  if (projects.length === 0) {
    state = { viewState: EMPTY__STATE };
  } else {
    state = { viewState: LOADED__STATE, projects };
  }
  return state;
};
