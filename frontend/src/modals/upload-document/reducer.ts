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
  EMPTY_STATE,
  SELECTED_STATE,
  SUBMIT_ERROR_STATE,
  SUBMIT_SUCCESS_STATE,
  HANDLE_SELECTED_ACTION,
  HANDLE_SUBMIT_ACTION,
} from 'modals/upload-document/machine';

export const initialState = {
  viewState: EMPTY_STATE,
  file: undefined,
  message: undefined,
  uploadedDocumentId: undefined,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }
  let state = prevState;
  switch (action.type) {
    case HANDLE_SELECTED_ACTION:
      state = handleSelectedAction(action);
      break;
    case HANDLE_SUBMIT_ACTION:
      state = handleSubmitAction(prevState, action);
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

const handleSelectedAction = (action) => {
  const { file } = action;
  const state = { viewState: SELECTED_STATE, file };
  return state;
};

const handleSubmitAction = (prevState, action) => {
  const { response } = action;
  let state = prevState;
  if (response.errors) {
    const message = 'An unexpected error has occured while retrieving the document, please contact your administrator';
    state = { viewState: SUBMIT_ERROR_STATE, message };
  } else if (response.data.uploadDocument.document) {
    state = { viewState: SUBMIT_SUCCESS_STATE };
  } else if (response.data.uploadDocument.message) {
    const { message } = response.data.uploadDocument;
    state = { viewState: SUBMIT_ERROR_STATE, message };
  } else {
    console.error(`The action response cannot be handled.`);
  }
  return state;
};
