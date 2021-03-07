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
  ERROR__STATE,
  HANDLE_COMPLETE__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_EXPANDED__ACTION,
  LOADING__STATE,
  machine,
  TREE_LOADED__STATE,
} from './machine';

export const initialState = {
  viewState: LOADING__STATE,
  tree: undefined,
  expanded: [],
  maxDepth: 1,
  message: '',
  modal: undefined,
};

export const reducer = (prevState, action) => {
  const supportedActions = machine[prevState.viewState];
  if (!supportedActions[action.type]) {
    console.error(`The state ${prevState.viewState} does not support the action ${action.type}`);
  }

  let state = prevState;
  switch (action.type) {
    case HANDLE_CONNECTION_ERROR__ACTION:
      state = handleConnectionErrorAction();
      break;
    case HANDLE_DATA__ACTION:
      state = handleDataAction(prevState, action);
      break;
    case HANDLE_ERROR__ACTION:
      state = handleErrorAction(prevState, action);
      break;
    case HANDLE_COMPLETE__ACTION:
      state = handleCompleteAction();
      break;
    case HANDLE_EXPANDED__ACTION:
      state = handleExpandedAction(prevState, action);
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

const handleConnectionErrorAction = () => {
  return {
    viewState: ERROR__STATE,
    tree: undefined,
    expanded: [],
    maxDepth: 1,
    message: 'An error has occured while retrieving the content from the server',
    modal: undefined,
  };
};

const handleDataAction = (prevState, action) => {
  const { expanded, maxDepth, modal } = prevState;
  const { message } = action;

  if (message?.data?.treeEvent) {
    const { treeEvent } = message.data;
    if (treeEvent.__typename === 'TreeRefreshedEventPayload') {
      const { tree } = treeEvent;
      return { viewState: TREE_LOADED__STATE, tree, expanded, maxDepth, message: '', modal };
    }
  }

  return prevState;
};

const handleErrorAction = (prevState, action) => {
  const { viewState, tree, expanded, maxDepth, modal } = prevState;
  const { message } = action;
  if (viewState === TREE_LOADED__STATE) {
    return { viewState: TREE_LOADED__STATE, tree, expanded, maxDepth, message, modal };
  }
  return { viewState: ERROR__STATE, tree, expanded, maxDepth, message, modal };
};

const handleCompleteAction = () => {
  return {
    viewState: COMPLETE__STATE,
    tree: undefined,
    expanded: [],
    maxDepth: 1,
    message: '',
    modal: undefined,
  };
};

const handleExpandedAction = (prevState, action) => {
  const { viewState, tree, expanded, maxDepth, message, modal } = prevState;
  const { id, depth } = action;
  let newExpanded;
  if (expanded.includes(id)) {
    newExpanded = [...expanded];
    newExpanded.splice(newExpanded.indexOf(id), 1);
  } else {
    newExpanded = [...expanded, id];
  }

  return { viewState, tree, expanded: newExpanded, maxDepth: Math.max(maxDepth, depth), message, modal };
};
