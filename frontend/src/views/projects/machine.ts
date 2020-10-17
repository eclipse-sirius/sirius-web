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
export const LOADING__STATE = 'LOADING__STATE';
export const LOADED__STATE = 'LOADED__STATE';
export const EMPTY__STATE = 'EMPTY__STATE';
export const ERROR__STATE = 'ERROR__STATE';

export const HANDLE_FETCHED_PROJECTS__ACTION = 'HANDLE_FETCHED_PROJECTS__ACTION';
export const HANDLE_PROJECT_UPDATED__ACTION = 'HANDLE_PROJECT_UPDATED__ACTION';

export const machine = {
  LOADING__STATE: {
    HANDLE_FETCHED_PROJECTS__ACTION: [LOADED__STATE, EMPTY__STATE, ERROR__STATE],
  },
  LOADED__STATE: {
    HANDLE_PROJECT_UPDATED__ACTION: [LOADED__STATE, EMPTY__STATE, ERROR__STATE],
  },
  EMPTY__STATE: {},
  ERROR_STATE: {},
};
