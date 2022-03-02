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
import { v4 as uuid } from 'uuid';
import {
  COMPLETE__STATE,
  ERROR__STATE,
  HANDLE_COMPLETE__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_ERROR__ACTION,
  LOADING__STATE,
  TREE_LOADED__STATE,
} from '../machine';
import { initialState, reducer } from '../reducer';

const treeLoadedState = {
  viewState: TREE_LOADED__STATE,
  id: uuid(),
  tree: {
    id: 'tree',
    label: 'Project',
    children: [],
  },
  expanded: [],
  maxDepth: 1,
  synchronized: true,
  message: '',
};

const treeLoadedWithErrorState = {
  viewState: TREE_LOADED__STATE,
  id: uuid(),
  tree: {
    id: 'tree',
    label: 'Project',
    children: [],
  },
  expanded: [],
  maxDepth: 1,
  synchronized: false,
  message: 'An error has occured while retrieving the content from the server',
};

const errorMessage = 'An error has occured while retrieving the content from the server';

const completeMessage = {
  type: 'complete',
};

const treeRefreshEventPayloadMessage = {
  type: 'data',
  id: '42',
  data: {
    treeEvent: {
      __typename: 'TreeRefreshedEventPayload',
      tree: {
        id: 'tree',
        label: 'Project',
        children: [],
      },
    },
  },
};

describe('ExplorerWebSocketContainer - reducer', () => {
  it('has a proper initial state', () => {
    expect(initialState).toStrictEqual({
      viewState: LOADING__STATE,
      id: initialState.id,
      tree: undefined,
      expanded: [],
      maxDepth: 1,
      synchronized: true,
      message: '',
      modal: undefined,
    });
  });

  it('navigates to the error state if a connection error has been received', () => {
    const prevState = initialState;
    const message = {
      type: 'connection_error',
    };
    const action = { type: HANDLE_CONNECTION_ERROR__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: ERROR__STATE,
      id: prevState.id,
      tree: undefined,
      expanded: [],
      maxDepth: 1,
      synchronized: false,
      message: 'An error has occured while retrieving the content from the server',
      modal: undefined,
    });
  });

  it('navigates to the error state if an error has been received', () => {
    const prevState = initialState;
    const message = errorMessage;
    const action = { type: HANDLE_ERROR__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: ERROR__STATE,
      id: prevState.id,
      tree: undefined,
      expanded: [],
      maxDepth: 1,
      synchronized: false,
      message: message,
      modal: undefined,
    });
  });

  it('navigates to the tree loaded state if a proper tree has been received', () => {
    const prevState = initialState;
    const message = treeRefreshEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: TREE_LOADED__STATE,
      id: prevState.id,
      tree: message.data.treeEvent.tree,
      expanded: [],
      maxDepth: 1,
      synchronized: true,
      message: '',
      modal: undefined,
    });
  });

  it('refreshes the tree if a new tree has been received', () => {
    const prevState = treeLoadedState;
    const message = treeRefreshEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: TREE_LOADED__STATE,
      id: prevState.id,
      tree: message.data.treeEvent.tree,
      expanded: [],
      maxDepth: 1,
      synchronized: prevState.synchronized,
      message: '',
      modal: undefined,
    });
  });

  it('updates the message if an error has been received while a tree was displayed', () => {
    const prevState = treeLoadedState;
    const message = errorMessage;
    const action = { type: HANDLE_ERROR__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: TREE_LOADED__STATE,
      id: prevState.id,
      tree: prevState.tree,
      expanded: prevState.expanded,
      maxDepth: prevState.maxDepth,
      synchronized: prevState.synchronized,
      message: message,
      modal: undefined,
    });
  });

  it('clears the message if a new tree has been received', () => {
    const prevState = treeLoadedWithErrorState;
    const message = treeRefreshEventPayloadMessage;
    const action = { type: HANDLE_DATA__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: TREE_LOADED__STATE,
      id: prevState.id,
      tree: message.data.treeEvent.tree,
      expanded: [],
      maxDepth: 1,
      synchronized: prevState.synchronized,
      message: '',
      modal: undefined,
    });
  });

  it('navigates to the complete state if a complete event has been received', () => {
    const prevState = treeLoadedState;
    const message = completeMessage;
    const action = { type: HANDLE_COMPLETE__ACTION, message };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: COMPLETE__STATE,
      id: prevState.id,
      tree: undefined,
      expanded: [],
      maxDepth: 1,
      synchronized: false,
      message: '',
      modal: undefined,
    });
  });
});
