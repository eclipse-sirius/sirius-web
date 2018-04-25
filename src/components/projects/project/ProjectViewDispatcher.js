/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import { dispatcherCreator } from '../../../common/dispatcherCreator';
import { UNKNOWN_ERROR } from '../../../common/errors';

import {
  FSM,
  ERROR__STATE,
  INITIAL__STATE,
  LOADING__STATE,
  PROJECT_LOADED__STATE,
  HANDLE_ERROR__ACTION,
  HANDLE_FETCHED_PAGE__ACTION,
  HANDLE_FETCHED_PROJECT__ACTION,
  INITIALIZE__ACTION
} from './ProjectViewFiniteStateMachine';

/**
 * The reducer of the project view.
 *
 * It will be used to execute the transitions in the finite state machine of
 * the project view.
 *
 * @param {*} state The current state
 * @param {*} props The properties of the component
 * @param {*} action The action to perform
 */
const reducer = (state, props, action) => {
  switch (action.kind) {
    case INITIALIZE__ACTION:
      return { stateId: LOADING__STATE, project: null, pageIdentifier: null, error: null };
    case HANDLE_FETCHED_PROJECT__ACTION:
      let pageIdentifier = null;
      if (action.project.pages.length > 0) {
        pageIdentifier = action.project.pages[0].identifier;
      }
      return {
        stateId: PROJECT_LOADED__STATE,
        project: action.project,
        pageIdentifier,
        error: null
      };
    case HANDLE_FETCHED_PAGE__ACTION:
      const newState = {
        stateId: PROJECT_LOADED__STATE,
        project: state.project,
        pageIdentifier: action.page.identifier,
        error: null
      };
      newState.project.currentPageSections = action.page.sections;
      return newState;
    case HANDLE_ERROR__ACTION:
      return {
        stateId: ERROR__STATE,
        project: state.project,
        pageIdentifier: state.pageIdentifier,
        error: action.error
      };
    default:
      return state;
  }
};

/**
 * Returns an initialize action used to go from the initial state to the
 * loading state.
 */
const newInitializeAction = () => ({
  kind: INITIALIZE__ACTION
});

/**
 * Returns an handle project fetched action used to go from the loading state
 * to the project loaded state.
 *
 * @param {*} response The HTTP response of the server
 */
const newHandleProjectFetchedAction = response => ({
  kind: HANDLE_FETCHED_PROJECT__ACTION,
  project: response
});

const newHandlePageFetchedAction = response => ({
  kind: HANDLE_FETCHED_PAGE__ACTION,
  page: response
});

/**
 * Returns an invalid response action used to indicate that the server has
 * returned a response with an error status 4xx or 5xx.
 *
 * @param {*} message The message from the server
 * @param {*} code The HTTP status code
 */
const newInvalidResponseAction = (message, code) => ({
  kind: HANDLE_ERROR__ACTION,
  error: {
    title: 'An error has occurred while retrieving the project',
    message,
    code
  }
});

/**
 * Returns an unexpected error action used to indicate that an issue has appeared
 * during the processing of the server response.
 *
 * @param {*} message The error message
 */
const newUnexpectedErrorAction = message => ({
  kind: HANDLE_ERROR__ACTION,
  error: {
    title: 'Unexpected content retrieved for the project',
    message,
    code: UNKNOWN_ERROR
  }
});

export const actionCreator = {
  newInitializeAction,
  newHandleProjectFetchedAction,
  newHandlePageFetchedAction,
  newInvalidResponseAction,
  newUnexpectedErrorAction
};

export const dispatcher = dispatcherCreator(FSM, reducer, INITIAL__STATE);
