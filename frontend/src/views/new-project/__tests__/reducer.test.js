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

/**
 * Test the transition between each state of the CreateProjectView when an action is received.
 *
 * @author lfasani
 * @author sbegaudeau
 * @author gcoutable
 */
import {
  PRISTINE__STATE,
  INVALID_NAME__STATE,
  VALID__STATE,
  SUBMIT_ERROR__STATE,
  SUBMIT_SUCCESS__STATE,
  HANDLE_CHANGE_NAME__ACTION,
  HANDLE_SUBMIT__ACTION,
} from '../machine';

import { SEVERITY_DEFAULT, SEVERITY_SUCCESS, SEVERITY_WARNING } from '@obeo/sirius-components';

import { initialState, reducer, NAME_HINT_MESSAGE, NAME_VALID_MESSAGE } from '../reducer';

const INVALID_NAME = '';
const VALID_NAME = 'valid name';
const ERROR_MESSAGE = 'An error has occured.';

const pristineState = {
  viewState: PRISTINE__STATE,
  name: '',
  visibility: 'PRIVATE',
  nameMessage: NAME_HINT_MESSAGE,
  nameMessageSeverity: SEVERITY_DEFAULT,
  message: null,
};

const validState = {
  viewState: VALID__STATE,
  name: VALID_NAME,
  visibility: 'PRIVATE',
  nameMessage: NAME_VALID_MESSAGE,
  nameMessageSeverity: SEVERITY_SUCCESS,
  message: null,
};

const invalidNameState = {
  viewState: INVALID_NAME__STATE,
  name: INVALID_NAME,
  visibility: 'PRIVATE',
  nameMessage: NAME_HINT_MESSAGE,
  nameMessageSeverity: SEVERITY_WARNING,
  message: null,
};

const submitErrorState = {
  viewState: SUBMIT_ERROR__STATE,
  name: VALID_NAME,
  visibility: 'PRIVATE',
  nameMessage: NAME_VALID_MESSAGE,
  nameMessageSeverity: SEVERITY_SUCCESS,
  message: ERROR_MESSAGE,
};

describe('NewProjectView - reducer', () => {
  it('tests the pristine state', () => {
    expect(initialState).toStrictEqual(pristineState);
  });

  it('can handle an invalid name in the pristine state', () => {
    const prevState = initialState;
    const action = { type: HANDLE_CHANGE_NAME__ACTION, name: INVALID_NAME };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(invalidNameState);
  });

  it('can handle a valid name in the pristine state', () => {
    const prevState = initialState;
    const action = { type: HANDLE_CHANGE_NAME__ACTION, name: VALID_NAME };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(validState);
  });

  it('can handle an invalid name in the valid state', () => {
    const prevState = validState;
    const action = { type: HANDLE_CHANGE_NAME__ACTION, name: INVALID_NAME };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(invalidNameState);
  });

  it('can handle a valid name in the valid state', () => {
    const prevState = validState;
    const action = { type: HANDLE_CHANGE_NAME__ACTION, name: VALID_NAME };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(validState);
  });

  it('can handle an invalid name in the invalid state', () => {
    const prevState = invalidNameState;
    const action = { type: HANDLE_CHANGE_NAME__ACTION, name: INVALID_NAME };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(invalidNameState);
  });

  it('can handle a valid name in the invalid state', () => {
    const prevState = invalidNameState;
    const action = { type: HANDLE_CHANGE_NAME__ACTION, name: VALID_NAME };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(validState);
  });

  it('can handle an error in the valid state', () => {
    const prevState = validState;
    const response = {
      data: {
        data: {
          createProject: {
            __typename: 'ErrorPayload',
            message: ERROR_MESSAGE,
          },
        },
      },
    };
    const action = { type: HANDLE_SUBMIT__ACTION, response };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(submitErrorState);
  });

  it('can handle an invalid name in the error state', () => {
    const prevState = submitErrorState;
    const action = { type: HANDLE_CHANGE_NAME__ACTION, name: INVALID_NAME };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(invalidNameState);
  });

  it('can handle a valid name in the error state', () => {
    const prevState = submitErrorState;
    const action = { type: HANDLE_CHANGE_NAME__ACTION, name: VALID_NAME };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual(validState);
  });

  it('can handle a successful submission in the valid state', () => {
    const prevState = validState;
    const response = {
      data: {
        data: {
          createProject: {
            __typename: 'CreateProjectSuccessPayload',
            project: {
              id: 'abcdef',
            },
          },
        },
      },
    };
    const action = { type: HANDLE_SUBMIT__ACTION, response };
    const state = reducer(prevState, action);

    expect(state).toStrictEqual({
      viewState: SUBMIT_SUCCESS__STATE,
      newProjectId: response.data.data.createProject.project.id,
    });
  });
});
