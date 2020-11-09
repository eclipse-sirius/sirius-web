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

/**
 * Perform the transition between each state of the UploadProjectView when an action is received.
 *
 * @author gcoutable
 */
import {
  machine,
  PRISTINE__STATE,
  HANDLE_SELECTED__ACTION,
  HANDLE_SUBMIT__ACTION,
  SELECTED__STATE,
  SUBMIT_ERROR__STATE,
  SUBMIT_SUCCESS__STATE,
} from './machine';

export const initialState = {
  viewState: PRISTINE__STATE,
  file: null,
  message: null,
  newProjectId: undefined,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }

  let state = prevState;
  switch (action.type) {
    case HANDLE_SELECTED__ACTION:
      state = handleSelectedAction(action);
      break;
    case HANDLE_SUBMIT__ACTION:
      state = handleSubmitAction(prevState, action);
      break;
    default:
      state = prevState;
      break;
  }

  const newSupportedStates = supportedActions[action.type];
  if (newSupportedStates.indexOf(state.viewState) === -1) {
    console.error(`The state ${state.viewState} should not be accessible with the action ${action.type}`);
  }
  return state;
};

const handleSelectedAction = (action) => {
  const { file } = action;
  return { viewState: SELECTED__STATE, file };
};

const handleSubmitAction = (prevState, action) => {
  const { file } = prevState;
  const { response } = action;
  let state = prevState;
  if (response.errors) {
    const message = 'An unexpected error has occured while creating the project, please contact your administrator';
    state = { viewState: SUBMIT_ERROR__STATE, message };
  } else {
    if (response.data.uploadProject.__typename === 'UploadProjectSuccessPayload') {
      const newProjectId = response.data.uploadProject.project.id;
      state = { viewState: SUBMIT_SUCCESS__STATE, newProjectId };
    } else if (response.data.uploadProject.__typename === 'ErrorPayload') {
      state = { viewState: SUBMIT_ERROR__STATE, file, message: response.data.uploadProject.message };
    }
  }

  return state;
};
