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
export const PRISTINE__STATE = 'PRISTINE__STATE';
export const SELECTED__STATE = 'SELECTED__STATE';
export const SUBMIT_ERROR__STATE = 'SUBMIT_ERROR__STATE';
export const SUBMIT_SUCCESS__STATE = 'SUBMIT_SUCCESS__STATE';

export const HANDLE_SELECTED__ACTION = 'HANDLE_SELECTED__ACTION';
export const HANDLE_SUBMIT__ACTION = 'HANDLE_SUBMIT__ACTION';

export const machine = {
  PRISTINE__STATE: {
    HANDLE_SELECTED__ACTION: [SELECTED__STATE],
  },
  SELECTED__STATE: {
    HANDLE_SUBMIT__ACTION: [SUBMIT_ERROR__STATE, SUBMIT_SUCCESS__STATE],
    HANDLE_SELECTED__ACTION: [SELECTED__STATE],
  },
  SUBMIT_ERROR__STATE: {
    HANDLE_SELECTED__ACTION: [SELECTED__STATE],
  },
  SUBMIT_SUCCESS__STATE: {},
};
