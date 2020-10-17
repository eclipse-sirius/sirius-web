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
 * Perform the transition between each state of the NewProjectView when an action is received.
 *
 * @author lfasani
 * @author sbegaudeau
 */
import {
  machine,
  PRISTINE__STATE,
  INVALID_NAME__STATE,
  VALID__STATE,
  SUBMIT_ERROR__STATE,
  CANCEL_CREATION_STATE,
  SUBMIT_SUCCESS__STATE,
  HANDLE_CHANGE_NAME__ACTION,
  HANDLE_SUBMIT__ACTION,
  HANDLE_CANCEL_CREATION__ACTION,
} from './machine';

import { SEVERITY_DEFAULT, SEVERITY_SUCCESS, SEVERITY_WARNING } from 'core/message/Message';

export const NAME_HINT_MESSAGE = 'The name must contain between 3 and 20 characters.';
export const NAME_VALID_MESSAGE = 'The name is valid.';

export const initialState = {
  viewState: PRISTINE__STATE,
  name: '',
  nameMessage: NAME_HINT_MESSAGE,
  nameMessageSeverity: SEVERITY_DEFAULT,
  message: null,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }

  let state = prevState;
  switch (action.type) {
    case HANDLE_CHANGE_NAME__ACTION:
      state = handleChangeNameAction(prevState, action);
      break;
    case HANDLE_SUBMIT__ACTION:
      state = handleSubmitAction(prevState, action);
      break;
    case HANDLE_CANCEL_CREATION__ACTION:
      state = handleCancelCreationAction();
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

const isNameValid = (name) => name.trim().length >= 3 && name.trim().length <= 20;

const handleChangeNameAction = (prevState, action) => {
  const { name } = action;

  let viewState = null;
  let nameMessageSeverity = SEVERITY_DEFAULT;
  let nameMessage = NAME_HINT_MESSAGE;

  if (isNameValid(name)) {
    viewState = VALID__STATE;
    nameMessage = NAME_VALID_MESSAGE;
    nameMessageSeverity = SEVERITY_SUCCESS;
  } else {
    viewState = INVALID_NAME__STATE;
    nameMessageSeverity = SEVERITY_WARNING;
  }
  return { ...prevState, viewState, name, nameMessage: nameMessage, nameMessageSeverity, message: null };
};

const handleSubmitAction = (prevState, action) => {
  const { name } = prevState;
  const { response } = action;
  let state = prevState;
  if (response.error) {
    state = { ...prevState, viewState: SUBMIT_ERROR__STATE, message: response.error.message };
  } else if (response.data && response.data.data) {
    const { createProject } = response.data.data;
    if (createProject.__typename === 'CreateProjectSuccessPayload') {
      const newProjectId = createProject.project.id;
      state = { viewState: SUBMIT_SUCCESS__STATE, newProjectId };
    } else if (createProject.__typename === 'ErrorPayload') {
      state = { ...prevState, viewState: SUBMIT_ERROR__STATE, name, message: createProject.message };
    }
  }
  return state;
};

const handleCancelCreationAction = () => {
  return { viewState: CANCEL_CREATION_STATE, name: '', message: '' };
};
