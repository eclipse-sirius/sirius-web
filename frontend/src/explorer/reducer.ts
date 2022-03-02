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
import { GQLGetTreePathData } from 'explorer/ExplorerWebSocketContainer.types';
import { v4 as uuid } from 'uuid';
import {
  COMPLETE__STATE,
  ERROR__STATE,
  HANDLE_COMPLETE__ACTION,
  HANDLE_CONNECTION_ERROR__ACTION,
  HANDLE_DATA__ACTION,
  HANDLE_ERROR__ACTION,
  HANDLE_EXPANDED__ACTION,
  HANDLE_SYNCHRONIZE__ACTION,
  HANDLE_TREE_PATH__ACTION,
  LOADING__STATE,
  machine,
  TREE_LOADED__STATE,
} from './machine';

export const initialState = {
  viewState: LOADING__STATE,
  id: uuid(),
  tree: undefined,
  expanded: [],
  maxDepth: 1,
  synchronized: true,
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
    case HANDLE_EXPANDED__ACTION:
      state = handleExpandedAction(prevState, action);
      break;
    case HANDLE_TREE_PATH__ACTION:
      state = handleTreePathAction(prevState, action);
      break;
    case HANDLE_SYNCHRONIZE__ACTION:
      state = handleSynchronizeAction(prevState, action);
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

const handleConnectionErrorAction = (prevState) => {
  const { id } = prevState;
  return {
    viewState: ERROR__STATE,
    id,
    tree: undefined,
    expanded: [],
    maxDepth: 1,
    synchronized: false,
    message: 'An error has occured while retrieving the content from the server',
    modal: undefined,
  };
};

const handleDataAction = (prevState, action) => {
  const { id, expanded, maxDepth, synchronized, modal } = prevState;
  const { message } = action;

  if (message?.data?.treeEvent) {
    const { treeEvent } = message.data;
    if (treeEvent.__typename === 'TreeRefreshedEventPayload') {
      const { tree } = treeEvent;
      return { viewState: TREE_LOADED__STATE, id, tree, expanded, maxDepth, synchronized, message: '', modal };
    }
  }

  return prevState;
};

const handleErrorAction = (prevState, action) => {
  const { viewState, id, tree, expanded, maxDepth, synchronized, modal } = prevState;
  const { message } = action;
  if (viewState === TREE_LOADED__STATE) {
    return { viewState: TREE_LOADED__STATE, id, tree, expanded, maxDepth, synchronized, message, modal };
  }
  return { viewState: ERROR__STATE, id, tree, expanded, maxDepth, synchronized: false, message, modal };
};

const handleCompleteAction = (prevState) => {
  const { id } = prevState;
  return {
    viewState: COMPLETE__STATE,
    id,
    tree: undefined,
    expanded: [],
    maxDepth: 1,
    synchronized: false,
    message: '',
    modal: undefined,
  };
};

const handleExpandedAction = (prevState, action) => {
  const { viewState, id, tree, expanded, maxDepth, synchronized, message, modal } = prevState;
  const { id: elementId, depth } = action;
  let newExpanded;
  let newSynchronized = synchronized;
  if (expanded.includes(elementId)) {
    newExpanded = [...expanded];
    newExpanded.splice(newExpanded.indexOf(elementId), 1);
    newSynchronized = false; // Disable synchronize mode on collapse
  } else {
    newExpanded = [...expanded, elementId];
  }

  return {
    viewState,
    id,
    tree,
    expanded: newExpanded,
    maxDepth: Math.max(maxDepth, depth),
    synchronized: newSynchronized,
    message,
    modal,
  };
};

const handleTreePathAction = (prevState, action) => {
  const { viewState, id, tree, expanded, maxDepth, synchronized, message, modal } = prevState;
  const treePathData: GQLGetTreePathData = action.treePathData;

  if (treePathData.viewer?.editingContext?.treePath) {
    const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } = treePathData.viewer.editingContext.treePath;
    const newExpanded: string[] = [...expanded];
    treeItemIdsToExpand?.forEach((itemToExpand) => {
      if (!expanded.includes(itemToExpand)) {
        newExpanded.push(itemToExpand);
      }
    });
    return {
      viewState,
      id,
      tree,
      expanded: newExpanded,
      maxDepth: Math.max(expandedMaxDepth, maxDepth),
      synchronized,
      message,
      modal,
    };
  } else {
    return prevState;
  }
};

const handleSynchronizeAction = (prevState, action) => {
  const { viewState, id, tree, expanded, maxDepth, message, modal } = prevState;
  const { synchronized } = action;
  return { viewState, id, tree, expanded, maxDepth, synchronized, message, modal };
};
