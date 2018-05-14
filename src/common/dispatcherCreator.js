/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

/**
 * The dispatcher creator is a function which will create a new dispatcher
 * initialized thanks to the following parameters:
 *
 * First, the finite state machine defining all the states and transitions in
 * the stateful component. Second, the reducer used to define the behavior of
 * each transition, allowing the stateful component to move from one state to
 * another and finally, the initial state of the finite state machine.
 *
 * It will then return a dispatcher which will ensure that the reducer cannot
 * be called to execute an action which is not defined on the current state
 * starting with the initial state. It will also make sure that the new state
 * computed by the reducer is accessible from the current state with the action
 * that the reducer had to execute.
 *
 * Failure to comply with those requirements will result in an error logged in
 * the console and no change will be applied to the returned state.
 */
export const dispatcherCreator = (FSM, reducer, INITIAL__STATE) => (
  prevState = { stateId: INITIAL__STATE },
  props,
  action
) => {
  let transitions = FSM[prevState.stateId];
  const newPotentialState = transitions[action.kind];
  if (newPotentialState) {
    const newState = reducer(prevState, props, action);
    if (newPotentialState.includes(newState.stateId)) {
      if (FSM[newState.stateId]) {
        return newState;
      } else {
        console.error(`The state ${newState.stateId} does not exist.`);
      }
    } else {
      console.error(
        `The state '${newState.stateId}' should not be accessible from ${
          prevState.stateId
        } with the transition ${action.kind}.`
      );
    }
  } else {
    console.error(
      `The state '${prevState.stateId}' does not support the transition '${action.kind}'.`
    );
  }
  return prevState;
};
