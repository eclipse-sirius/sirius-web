/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import { dispatcherCreator } from '../../common/dispatcherCreator';

import {
  FSM,
  INITIAL__STATE,
  LOADING__STATE,
  DASHBOARD_LOADED__STATE,
  HANDLE_FETCHED_DASHBOARD__ACTION,
  INITIALIZE__ACTION
} from './DashboardViewFiniteStateMachine';

/**
 * The reducer of the dashboard view.
 *
 * It will be used to execute the transitions in the finite state machine of
 * the dashboard.
 *
 * @param {*} state The current state
 * @param {*} props The properties of the component
 * @param {*} action The action to perform
 */
const reducer = (state, props, action) => {
  switch (action.kind) {
    case INITIALIZE__ACTION:
      return { stateId: LOADING__STATE, dashboard: { projects: [] }, error: null };
    case HANDLE_FETCHED_DASHBOARD__ACTION:
      return { stateId: DASHBOARD_LOADED__STATE, dashboard: action.dashboard, error: null };
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
 * Returns an handle dashboard fetched action used to go from the loading state
 * to the dashboard loaded state.
 *
 * @param {*} response The HTTP response of the server
 */
const newHandleDashboardFetchedAction = response => ({
  kind: HANDLE_FETCHED_DASHBOARD__ACTION,
  dashboard: response
});

export const actionCreator = { newInitializeAction, newHandleDashboardFetchedAction };

export const dispatcher = dispatcherCreator(FSM, reducer, INITIAL__STATE);
