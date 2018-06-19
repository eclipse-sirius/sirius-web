/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

export const INITIAL__STATE = 'INITIAL__STATE';
export const LOADING__STATE = 'LOADING__STATE';
export const ERROR__STATE = 'ERROR__STATE';
export const PROJECT_LOADED__STATE = 'PROJECT_LOADED__STATE';

export const INITIALIZE__ACTION = 'INITIALIZE__ACTION';
export const HANDLE_FETCHED_PROJECT__ACTION = 'HANDLE_FETCHED_PROJECT__ACTION';
export const HANDLE_FETCHED_PAGE__ACTION = 'HANDLE_FETCHED_PAGE__ACTION';
export const HANDLE_DESCRIPTION_UPDATED__ACTION = 'HANDLE_DESCRIPTION_UPDATED__ACTION';
export const HANDLE_ERROR__ACTION = 'HANDLE_ERROR__ACTION';

export const FSM = {
  INITIAL__STATE: { INITIALIZE__ACTION: [LOADING__STATE] },
  LOADING__STATE: {
    HANDLE_FETCHED_PROJECT__ACTION: [PROJECT_LOADED__STATE],
    HANDLE_ERROR__ACTION: [ERROR__STATE]
  },
  PROJECT_LOADED__STATE: {
    HANDLE_FETCHED_PAGE__ACTION: [PROJECT_LOADED__STATE],
    HANDLE_DESCRIPTION_UPDATED__ACTION: [PROJECT_LOADED__STATE]
  },
  ERROR__STATE: {}
};
