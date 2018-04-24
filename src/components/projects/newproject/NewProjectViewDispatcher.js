/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

import { dispatcherCreator } from '../../../common/dispatcherCreator';

import {
  FSM,
  INITIAL__STATE,
  INITIALIZE__ACTION,
  MODIFY_NAME__ACTION,
  PRISTINE__STATE,
  MODIFIED__STATE,
  HANDLE_ERROR__ACTION,
  HANDLE_CREATED_PROJECT__ACTION,
  REDIRECT__STATE
} from './NewProjectViewFiniteStateMachine';

/**
 * The reducer of the new project view.
 *
 * It will be used to execute the transitions in the finite state machine of
 * the new project view.
 *
 * @param {*} state The current state
 * @param {*} props The properties of the component
 * @param {*} action The action to perform
 */
const reducer = (state, props, action) => {
  switch (action.kind) {
    case INITIALIZE__ACTION:
      return {
        stateId: PRISTINE__STATE,
        errors: [],
        isValid: false,
        name: '',
        nameIsValid: false,
        nameErrors: []
      };
    case MODIFY_NAME__ACTION:
      const { name } = action;
      const nameErrors = validateName(name);
      const nameIsValid = nameErrors.length === 0;
      const isValid = nameIsValid;
      return {
        stateId: MODIFIED__STATE,
        errors: [],
        isValid,
        name,
        nameIsValid,
        nameErrors
      };
    case HANDLE_ERROR__ACTION:
      const errors = [].concat(state.errors);
      errors.push(action.message);
      return {
        stateId: MODIFIED__STATE,
        errors,
        isValid: false
      };
    case HANDLE_CREATED_PROJECT__ACTION:
      return {
        stateId: REDIRECT__STATE
      };
    default:
      return state;
  }
};

/**
 * Validates the given name and returns the errors found.
 * @param {*} name
 * @returns An array of string describing the errors found
 */
const validateName = name => {
  const errors = [];

  if (name.trim().length === 0) {
    errors.push('The name is required');
  }

  const invalidCharacters = ['\\', '/', ':', '*', '?', '"', '<', '>', '|'];
  invalidCharacters.forEach(invalidCharacter => {
    if (name.indexOf(invalidCharacter) !== -1) {
      errors.push(`The character ${invalidCharacter} cannot be used in the name`);
    }
  });

  if (name.trim() === '.' || name.trim() === '..') {
    errors.push('The name cannot have the value . or ..');
  }

  if (name.length > 0 && name.charAt(name.length - 1) === '.') {
    errors.push('The name cannot end with a dot');
  }

  if (name.length > 0 && name.trim().length === 0) {
    errors.push('The name cannot be composed of whitespaces');
  } else if (name.length !== name.trim().length) {
    errors.push('Remove any whitespace at the beginning or the end');
  }

  return errors;
};

/**
 * Returns an initialize action used to go from the initial state to the
 * pristine state.
 */
const newInitializeAction = () => ({
  kind: INITIALIZE__ACTION
});

/**
 * Returns a new modify name action used to change the value of the name and
 * trigger the various validation rules.
 *
 * @param {*} name The name
 */
const newModifyNameAction = name => ({
  kind: MODIFY_NAME__ACTION,
  name
});

/**
 * Returns a new unexpected error action used to handle an unexpected error in
 * our code.
 *
 * @param {*} message The message
 */
const newUnexpectedErrorAction = message => ({
  kind: HANDLE_ERROR__ACTION,
  message: 'An error has occured during the processing of the response'
});

/**
 * Returns a new handle created project action used to redirect the user interface
 * to the new project created.
 */
const newCreatedProjectAction = () => ({
  kind: HANDLE_CREATED_PROJECT__ACTION
});

/**
 * Returns a new invalid error action used to handle an invalid response from
 * the server.
 *
 * @param {*} message The message
 */
const newInvalidResponseAction = message => ({
  kind: HANDLE_ERROR__ACTION,
  message
});

export const actionCreator = {
  newInitializeAction,
  newModifyNameAction,
  newUnexpectedErrorAction,
  newCreatedProjectAction,
  newInvalidResponseAction
};

export const dispatcher = dispatcherCreator(FSM, reducer, INITIAL__STATE);
