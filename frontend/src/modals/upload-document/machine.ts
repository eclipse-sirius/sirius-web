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
export const EMPTY_STATE = 'EMPTY_STATE';
export const SELECTED_STATE = 'SELECTED_STATE';
export const SUBMIT_ERROR_STATE = 'SUBMIT_ERROR_STATE';
export const SUBMIT_SUCCESS_STATE = 'SUBMIT_SUCCESS_STATE';

export const HANDLE_SELECTED_ACTION = 'HANDLE_SELECTED_ACTION';
export const HANDLE_SUBMIT_ACTION = 'HANDLE_SUBMIT_ACTION';

export const machine = {
  EMPTY_STATE: {
    HANDLE_SELECTED_ACTION: [SELECTED_STATE],
  },
  CANCEL_STATE: {},
  SELECTED_STATE: {
    HANDLE_SUBMIT_ACTION: [SUBMIT_ERROR_STATE, SUBMIT_SUCCESS_STATE],
    HANDLE_SELECTED_ACTION: [SELECTED_STATE],
  },
  SUBMIT_ERROR_STATE: {
    HANDLE_SELECTED_ACTION: [SELECTED_STATE],
  },
  SUBMIT_SUCCESS_STATE: {},
};
