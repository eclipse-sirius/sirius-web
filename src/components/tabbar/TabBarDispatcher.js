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
  INITIALIZE__ACTION,
  HANDLE_NEXT__ACTION,
  HANDLE_PREVIOUS__ACTION,
  HANDLE_TAB_SELECTED__ACTION,
  MOVED__STATE,
  PRISTINE__STATE
} from './TabBarFiniteStateMachine';

/**
 * The reducer of the tab bar.
 *
 * It will be used to execute the transitions in the finite state machine of
 * the tab bar.
 *
 * @param {*} state The current state
 * @param {*} props The properties of the component
 * @param {*} action The action to perform
 */
const reducer = (state, props, action) => {
  switch (action.kind) {
    case INITIALIZE__ACTION:
      const initializedState = computeState(
        props.tabs,
        0,
        props.selectedTabIndex,
        props.numberOfTabsDisplayed
      );
      initializedState.stateId = PRISTINE__STATE;
      return initializedState;
    case HANDLE_NEXT__ACTION:
      const handleNextState = computeState(
        props.tabs,
        state.index + 1,
        state.selectedTabIndex,
        props.numberOfTabsDisplayed
      );
      handleNextState.stateId = MOVED__STATE;
      return handleNextState;
    case HANDLE_PREVIOUS__ACTION:
      const handlePreviousState = computeState(
        props.tabs,
        state.index - 1,
        state.selectedTabIndex,
        props.numberOfTabsDisplayed
      );
      handlePreviousState.stateId = MOVED__STATE;
      return handlePreviousState;
    case HANDLE_TAB_SELECTED__ACTION:
      const handleTabSelectedState = computeState(
        props.tabs,
        state.index,
        action.selectedTabIndex,
        props.numberOfTabsDisplayed
      );
      handleTabSelectedState.stateId = MOVED__STATE;
      return handleTabSelectedState;
    default:
      return state;
  }
};

const computeState = (tabs, index, selectedTabIndex, numberOfTabsDisplayed) => {
  const isPreviousAvailable = index > 0;
  const isNextAvailable = tabs.length > index + numberOfTabsDisplayed;
  return {
    index,
    isPreviousAvailable,
    isNextAvailable,
    selectedTabIndex
  };
};

/**
 * Returns an initialize action used to go from the initial state to the
 * loading state.
 */
const newInitializeAction = () => ({
  kind: INITIALIZE__ACTION
});

/**
 * Returns an handle next action used to navigate to the next tabs in the tab bar.
 */
const newHandleNextAction = () => ({
  kind: HANDLE_NEXT__ACTION
});

/**
 * Returns an handle previous action used to navigate to the next tabs in the tab bar.
 */
const newHandlePreviousAction = () => ({
  kind: HANDLE_PREVIOUS__ACTION
});

/**
 * Returns an handle tab selected action used to select a specific tab in the tab bar.
 *
 * @param {*} selectedTabIndex The index of the selected tab
 */
const newHandleTabSelectedAction = selectedTabIndex => ({
  kind: HANDLE_TAB_SELECTED__ACTION,
  selectedTabIndex
});

export const actionCreator = {
  newInitializeAction,
  newHandleNextAction,
  newHandlePreviousAction,
  newHandleTabSelectedAction
};

export const dispatcher = dispatcherCreator(FSM, reducer, INITIAL__STATE);
