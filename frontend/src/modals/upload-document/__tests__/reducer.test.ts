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
  EMPTY_STATE,
  SELECTED_STATE,
  SUBMIT_ERROR_STATE,
  SUBMIT_SUCCESS_STATE,
  HANDLE_SELECTED_ACTION,
  HANDLE_SUBMIT_ACTION,
} from 'modals/upload-document/machine';
import { initialState, reducer } from 'modals/upload-document/reducer';

const fileSelectedState = {
  viewState: SELECTED_STATE,
  file: {},
};

const unexpectedErrorResponse = {
  errors: [
    {
      message: 'Unexpected error',
      locations: [
        {
          line: 3,
          column: 5,
        },
      ],
      extensions: {
        classification: 'DataFetchingError',
      },
    },
  ],
};

describe('UploadDocumentModal - reducer', () => {
  it('test the initial state', () => {
    const state = initialState;

    expect(state.viewState).toBe(EMPTY_STATE);
    expect(state.file).toBeUndefined();
    expect(state.uploadedDocumentId).toBeUndefined();
    expect(state.message).toBeUndefined();
  });

  it('navigates to the selected state after a file selection', () => {
    const prevState = initialState;
    const file = {};
    const action = { type: HANDLE_SELECTED_ACTION, file };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SELECTED_STATE);
    expect(state.file).not.toBeUndefined();
    expect(state.file).not.toBeNull();
    expect(state.message).toBeUndefined();
  });

  it('navigates to the selected state after the file selected state', () => {
    const prevState = fileSelectedState;
    const file = {};
    const action = { type: HANDLE_SELECTED_ACTION, file };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SELECTED_STATE);
    expect(state.file).not.toBeUndefined();
    expect(state.file).not.toBeNull();
    expect(state.message).toBeUndefined();
  });

  it('navigates to the submit error state after the file selected state', () => {
    const prevState = fileSelectedState;
    const response = unexpectedErrorResponse;
    const action = { type: HANDLE_SUBMIT_ACTION, response };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SUBMIT_ERROR_STATE);
    expect(state.file).toBeUndefined();
    expect(state.message).not.toBeUndefined();
  });

  it('navigates to the submit success state after the file selected state', () => {
    const prevState = fileSelectedState;
    const uploadedDocumentId = 'azerty';

    const response = {
      data: {
        uploadDocument: {
          document: {
            id: uploadedDocumentId,
          },
        },
      },
    };
    const action = { type: HANDLE_SUBMIT_ACTION, response };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(SUBMIT_SUCCESS_STATE);
    expect(state.file).toBeUndefined();
    expect(state.message).toBeUndefined();
  });
});
