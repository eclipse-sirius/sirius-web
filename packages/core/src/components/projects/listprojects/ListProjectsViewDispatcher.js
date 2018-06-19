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
  NO_PROJECTS_LOADED__STATE,
  PROJECTS_LOADED__STATE,
  HANDLE_ERROR__ACTION,
  HANDLE_FETCHED_PROJECTS__ACTION,
  INITIALIZE__ACTION
} from './ListProjectsViewFiniteStateMachine';

/**
 * The reducer of the list projects view.
 *
 * It will be used to execute the transitions in the finite state machine of
 * the list projects view.
 *
 * @param {*} state The current state
 * @param {*} action The action to perform
 */
const reducer = (state, action) => {
  switch (action.kind) {
    case INITIALIZE__ACTION:
      return { stateId: LOADING__STATE, projects: [], error: undefined };
    case HANDLE_FETCHED_PROJECTS__ACTION:
      if (action.projects.length === 0) {
        return { stateId: NO_PROJECTS_LOADED__STATE, projects: [], error: undefined };
      }
      return { stateId: PROJECTS_LOADED__STATE, projects: action.projects, error: undefined };
    case HANDLE_ERROR__ACTION:
      return { stateId: ERROR__STATE, projects: state.projects, error: action.error };
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
 * Returns an handle projects fetched action used to go from the loading state
 * to the projects loaded state.
 *
 * @param {*} response The HTTP response of the server
 */
const newHandleProjectsFetchedAction = response => ({
  kind: HANDLE_FETCHED_PROJECTS__ACTION,
  projects: response.projects
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
    title: 'An error has occurred while retrieving the list of projects',
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
    title: 'Unexpected content retrieved for the projects list',
    message,
    code: UNKNOWN_ERROR
  }
});

export const actionCreator = {
  newInitializeAction,
  newHandleProjectsFetchedAction,
  newInvalidResponseAction,
  newUnexpectedErrorAction
};

export const dispatcher = dispatcherCreator(FSM, reducer, INITIAL__STATE);
