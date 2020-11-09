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
 * Test the transition between each state of the UploadProjectView when an action is received.
 
 * @author gcoutable
 */
import {
  PRISTINE__STATE,
  SELECTED__STATE,
  SUBMIT_ERROR__STATE,
  SUBMIT_SUCCESS__STATE,
  HANDLE_SELECTED__ACTION,
  HANDLE_SUBMIT__ACTION,
} from '../machine';
import { initialState, reducer } from '../reducer';

const NEW_PROJECT_ID = 'newProjectId';
const FILE = 'should be a real file, but for those tests it is just a string';

const selectedState = {
  viewState: SELECTED__STATE,
  file: FILE,
};

const submitErrorState = {
  viewState: SUBMIT_ERROR__STATE,
  file: FILE,
  message: 'An error has occurred',
};

describe('UploadProjectView - reducer', () => {
  it('tests the pristine state', () => {
    const state = initialState;
    expect(state.viewState).toBe(PRISTINE__STATE);
    expect(state.file).toBeNull();
    expect(state.newProjectId).toBeUndefined();
    expect(state.message).toBeNull();
  });

  it('can handle a selected file in the pristine state', () => {
    const prevState = initialState;
    const action = { type: HANDLE_SELECTED__ACTION, file: FILE };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SELECTED__STATE);
    expect(state.file).toBe(FILE);
    expect(state.message).toBeUndefined();
  });

  it('can handle a selected file in the selected state', () => {
    const prevState = selectedState;
    const action = { type: HANDLE_SELECTED__ACTION, file: FILE };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SELECTED__STATE);
    expect(state.file).toBe(FILE);
    expect(state.message).toBeUndefined();
  });

  it('can handle a submit success in the selected state', () => {
    const prevState = selectedState;
    const response = {
      data: {
        uploadProject: {
          __typename: 'UploadProjectSuccessPayload',
          project: {
            id: NEW_PROJECT_ID,
          },
        },
      },
    };

    const action = { type: HANDLE_SUBMIT__ACTION, response };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SUBMIT_SUCCESS__STATE);
    expect(state.file).toBeUndefined();
    expect(state.message).toBeUndefined();
    expect(state.newProjectId).toBe(NEW_PROJECT_ID);
  });

  it('can handle a submit error in the selected state', () => {
    const prevState = selectedState;
    const response = {
      data: {
        uploadProject: {
          __typename: 'ErrorPayload',
          message: 'error message',
        },
      },
    };
    const action = { type: HANDLE_SUBMIT__ACTION, response };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SUBMIT_ERROR__STATE);
    expect(state.file).toBe(FILE);
    expect(state.message).toBeDefined();
  });

  it('can handle a selected file in the submit error state', () => {
    const prevState = submitErrorState;
    const action = { type: HANDLE_SELECTED__ACTION, file: FILE };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SELECTED__STATE);
    expect(state.file).toBe(FILE);
    expect(state.message).toBeUndefined();
  });
});
