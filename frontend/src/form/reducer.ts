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
  EMPTY__STATE,
  LOADING__STATE,
  READY__STATE,
  COMPLETE__STATE,
  HANDLE_DATA__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_COMPLETE__ACTION,
  INITIALIZE__ACTION,
  SWITCH_FORM__ACTION,
} from './machine';

const EMPTY_SELECTION_MESSAGE = 'Please select an object to display its properties';

export const initialState = {
  viewState: EMPTY__STATE,
  form: undefined,
  displayedFormId: undefined,
  subscribers: [],
  widgetSubscriptions: [],
  message: EMPTY_SELECTION_MESSAGE,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }

  let state = prevState;
  switch (action.type) {
    case INITIALIZE__ACTION:
      state = handleInitializeAction(prevState);
      break;
    case SWITCH_FORM__ACTION:
      state = handleSwitchFormAction(prevState, action);
      break;
    case HANDLE_CONNECTION_ERROR__ACTION:
      state = handleConnectionErrorAction(prevState);
      break;
    case HANDLE_DATA__ACTION:
      state = handleDataAction(prevState, action);
      break;
    case HANDLE_ERROR__ACTION:
      state = handleErrorAction(prevState, action);
      break;
    case HANDLE_COMPLETE__ACTION:
      state = handleCompleteAction(prevState);
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

const handleSwitchFormAction = (prevState, action) => {
  const { formId } = action;
  let state = {
    ...prevState,
    displayedFormId: formId,
  };
  if (formId) {
    return {
      ...state,
      viewState: LOADING__STATE,
    };
  }
  return handleCompleteAction(state);
};

const handleInitializeAction = (prevState) => {
  return {
    ...prevState,
    viewState: READY__STATE,
  };
};

const handleDataAction = (prevState, action) => {
  const { message } = action;

  let state = prevState;
  if (message.payload && message.payload.data && message.payload.data.formEvent) {
    const { formEvent } = message.payload.data;
    if (formEvent.__typename === 'FormRefreshedEventPayload') {
      const { form } = formEvent;
      state = { ...prevState, viewState: READY__STATE, form, message: '' };
    } else if (formEvent.__typename === 'SubscribersUpdatedEventPayload') {
      const { subscribers } = formEvent;
      state = { ...prevState, subscribers };
    } else if (formEvent.__typename === 'WidgetSubscriptionsUpdatedEventPayload') {
      const { widgetSubscriptions } = formEvent;
      state = { ...prevState, widgetSubscriptions };
    }
  }

  return state;
};

const handleConnectionErrorAction = (prevState) => {
  return switchToCompleteState(prevState, 'An error has occured while retrieving the content from the server');
};

const handleErrorAction = (prevState, action) => {
  const { viewState } = prevState;
  const { payload: message } = action.message;
  if (viewState === READY__STATE) {
    return { ...prevState, message };
  }
  return switchToCompleteState(prevState, message);
};

const handleCompleteAction = (prevState) => {
  return switchToCompleteState(prevState, EMPTY_SELECTION_MESSAGE);
};

const switchToCompleteState = (prevState, message) => {
  return {
    ...prevState,
    viewState: COMPLETE__STATE,
    form: undefined,
    message,
  };
};
