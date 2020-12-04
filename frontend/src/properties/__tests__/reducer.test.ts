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
import { initialState, reducer } from '../reducer';
import {
  LOADING__STATE,
  READY__STATE,
  COMPLETE__STATE,
  EMPTY__STATE,
  INITIALIZE__ACTION,
  SWITCH_FORM__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_COMPLETE__ACTION,
} from '../machine';

const formReadyState = {
  viewState: READY__STATE,
  form: {
    id: 'form',
    label: 'Existing form',
    pages: [],
  },
  displayedObjectId: 'objectId',
  subscribers: [],
  widgetSubscriptions: [],
  message: '',
};

const formLoadedWithErrorState = {
  viewState: READY__STATE,
  form: {
    id: 'form',
    label: 'Existing form',
    pages: [],
  },
  displayedObjectId: 'objectId',
  subscribers: [],
  widgetSubscriptions: [],
  message: 'An error has occured while retrieving the content from the server',
};

const firstLoadingState = {
  viewState: LOADING__STATE,
  form: undefined,
  displayedObjectId: 'objectId',
  subscribers: [],
  widgetSubscriptions: [],
  message: 'Please select an object to display its properties',
};

const errorMessage = 'An error has occured while retrieving the content from the server';

const completeMessage = {
  type: 'complete',
};

const formRefreshEventPayloadMessage = {
  type: 'data',
  id: '42',
  data: {
    formEvent: {
      __typename: 'FormRefreshedEventPayload',
      form: {
        id: 'form',
        label: 'New Label',
        pages: [],
      },
    },
  },
};

const subscribersUpdatedEventPayloadMessage = {
  type: 'data',
  id: '51',
  data: {
    formEvent: {
      __typename: 'SubscribersUpdatedEventPayload',
      subscribers: [{ username: 'jdoe' }],
    },
  },
};

const widgetSubscriptionsUpdatedEventPayloadMessage = {
  type: 'data',
  id: '54',
  data: {
    formEvent: {
      __typename: 'WidgetSubscriptionsUpdatedEventPayload',
      widgetSubscriptions: [{ widgetId: 'some widget', subscribers: [{ username: 'jdoe' }] }],
    },
  },
};

describe('PropertiesWebSocketContainer - reducer', () => {
  it('has a proper initial state', () => {
    expect(initialState).toStrictEqual({
      viewState: EMPTY__STATE,
      form: undefined,
      displayedObjectId: undefined,
      subscribers: [],
      widgetSubscriptions: [],
      message: 'Please select an object to display its properties',
    });
  });

  it('navigates to the loading state if properties for an object has been requested', () => {
    const prevState = initialState;
    const action = { type: SWITCH_FORM__ACTION, objectId: 'objectId' };

    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: LOADING__STATE,
      form: undefined,
      displayedObjectId: action.objectId,
      subscribers: [],
      widgetSubscriptions: [],
      message: 'Please select an object to display its properties',
    });
  });

  it('navigates to the ready state if the loading state has been reached', () => {
    const prevState = firstLoadingState;
    const action = { type: INITIALIZE__ACTION };

    const state = reducer(prevState, action);
    expect(state).toStrictEqual({
      viewState: READY__STATE,
      form: undefined,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: [],
      widgetSubscriptions: [],
      message: 'Please select an object to display its properties',
    });
  });

  it('navigates to the complete state if a connection error has been received', () => {
    const prevState = formReadyState;
    const message = {
      type: 'connection_error',
    };
    const action = { type: HANDLE_CONNECTION_ERROR__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: COMPLETE__STATE,
      form: undefined,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: [],
      widgetSubscriptions: [],
      message: 'An error has occured while retrieving the content from the server',
    });
  });

  it('navigates to the ready state if a proper form has been received', () => {
    const prevState = formReadyState;
    const message = formRefreshEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      form: message.data.formEvent.form,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: [],
      widgetSubscriptions: [],
      message: '',
    });
  });

  it('refreshes the form if a new form has been received', () => {
    const prevState = formReadyState;
    const message = formRefreshEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      form: message.data.formEvent.form,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: [],
      widgetSubscriptions: [],
      message: '',
    });
  });

  it('updates the list of subscribers if it should be updated', () => {
    const prevState = formReadyState;
    const message = subscribersUpdatedEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      form: prevState.form,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: message.data.formEvent.subscribers,
      widgetSubscriptions: [],
      message: '',
    });
  });

  it('updates the message if an error has been received while a form was displayed', () => {
    const prevState = formReadyState;
    const message = errorMessage;
    const action = { type: HANDLE_ERROR__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      form: prevState.form,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: prevState.subscribers,
      widgetSubscriptions: [],
      message: message,
    });
  });

  it('clears the message if a new form has been received', () => {
    const prevState = formLoadedWithErrorState;
    const message = formRefreshEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      form: message.data.formEvent.form,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: prevState.subscribers,
      widgetSubscriptions: [],
      message: '',
    });
  });

  it('navigates to the loading state if properties of a new object has been requested', () => {
    const prevState = formReadyState;
    const action = { type: SWITCH_FORM__ACTION, objectId: 'newObjectId' };

    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: LOADING__STATE,
      form: prevState.form,
      displayedObjectId: action.objectId,
      subscribers: prevState.subscribers,
      widgetSubscriptions: prevState.widgetSubscriptions,
      message: prevState.message,
    });
  });

  it('navigates to the complete state if no more object is selected', () => {
    const prevState = formReadyState;
    const action = { type: SWITCH_FORM__ACTION, objectId: undefined };

    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: COMPLETE__STATE,
      form: undefined,
      displayedObjectId: action.objectId,
      subscribers: prevState.subscribers,
      widgetSubscriptions: prevState.widgetSubscriptions,
      message: 'Please select an object to display its properties',
    });
  });

  it('navigates to the complete state if a complete event has been received', () => {
    const prevState = formReadyState;
    const message = completeMessage;
    const action = { type: HANDLE_COMPLETE__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: COMPLETE__STATE,
      form: undefined,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: prevState.subscribers,
      widgetSubscriptions: [],
      message: 'Please select an object to display its properties',
    });
  });

  it('updates the list of widget subscriptions if it should be updated', () => {
    const prevState = formReadyState;
    const message = widgetSubscriptionsUpdatedEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: READY__STATE,
      form: prevState.form,
      displayedObjectId: prevState.displayedObjectId,
      subscribers: prevState.subscribers,
      widgetSubscriptions: message.data.formEvent.widgetSubscriptions,
      message: prevState.message,
    });
  });
});
