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
 * Contain the finite state machine of the NewProjectView.
 *
 * @author lfasani
 */
export const PRISTINE__STATE = 'PRISTINE__STATE';
export const INVALID_NAME__STATE = 'INVALID_NAME__STATE';
export const VALID__STATE = 'VALID__STATE';
export const SUBMIT_ERROR__STATE = 'SUBMIT_ERROR__STATE';
export const SUBMIT_SUCCESS__STATE = 'SUBMIT_SUCCESS__STATE';
export const CANCEL_CREATION_STATE = 'CANCEL_CREATION_STATE';

export const HANDLE_CHANGE_NAME__ACTION = 'HANDLE_CHANGE_NAME__ACTION';
export const HANDLE_SUBMIT__ACTION = 'HANDLE_SUBMIT__ACTION';
export const HANDLE_CANCEL_CREATION__ACTION = 'HANDLE_CANCEL_CREATION__ACTION';

export const machine = {
  PRISTINE__STATE: {
    HANDLE_CHANGE_NAME__ACTION: [INVALID_NAME__STATE, VALID__STATE],
    HANDLE_CANCEL_CREATION__ACTION: [CANCEL_CREATION_STATE],
  },
  CANCEL_CREATION_STATE: {},
  INVALID_NAME__STATE: {
    HANDLE_CHANGE_NAME__ACTION: [INVALID_NAME__STATE, VALID__STATE],
    HANDLE_CANCEL_CREATION__ACTION: [CANCEL_CREATION_STATE],
  },
  VALID__STATE: {
    HANDLE_CHANGE_NAME__ACTION: [INVALID_NAME__STATE, VALID__STATE],
    HANDLE_SUBMIT__ACTION: [SUBMIT_ERROR__STATE, SUBMIT_SUCCESS__STATE],
    HANDLE_CANCEL_CREATION__ACTION: [CANCEL_CREATION_STATE],
  },
  SUBMIT_ERROR__STATE: {
    HANDLE_CHANGE_NAME__ACTION: [INVALID_NAME__STATE, VALID__STATE],
    HANDLE_CANCEL_CREATION__ACTION: [CANCEL_CREATION_STATE],
  },
  SUBMIT_SUCCESS__STATE: {},
};
