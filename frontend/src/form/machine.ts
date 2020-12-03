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
export const EMPTY__STATE = 'EMPTY__STATE';
export const LOADING__STATE = 'LOADING__STATE';
export const READY__STATE = 'READY__STATE';
export const COMPLETE__STATE = 'COMPLETE__STATE';

export const SWITCH_FORM__ACTION = 'SWITCH_FORM__ACTION';
export const INITIALIZE__ACTION = 'INITIALIZE__ACTION';
export const HANDLE_DATA__ACTION = 'HANDLE_DATA__ACTION';
export const HANDLE_CONNECTION_ERROR__ACTION = 'HANDLE_CONNECTION_ERROR__ACTION';
export const HANDLE_ERROR__ACTION = 'HANDLE_ERROR__ACTION';
export const HANDLE_COMPLETE__ACTION = 'HANDLE_COMPLETE__ACTION';

export const machine = {
  EMPTY__STATE: {
    SWITCH_FORM__ACTION: [LOADING__STATE],
  },
  LOADING__STATE: {
    INITIALIZE__ACTION: [READY__STATE],
  },
  READY__STATE: {
    SWITCH_FORM__ACTION: [LOADING__STATE, COMPLETE__STATE],
    HANDLE_DATA__ACTION: [READY__STATE],
    HANDLE_ERROR__ACTION: [READY__STATE],
    HANDLE_CONNECTION_ERROR__ACTION: [COMPLETE__STATE],
    HANDLE_COMPLETE__ACTION: [COMPLETE__STATE],
  },
  COMPLETE__STATE: {
    SWITCH_FORM__ACTION: [LOADING__STATE, COMPLETE__STATE],
  },
};
