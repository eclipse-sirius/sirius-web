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
export const TREE_LOADED__STATE = 'TREE_LOADED__STATE';
export const COMPLETE__STATE = 'COMPLETE__STATE';
export const ERROR__STATE = 'ERROR__STATE';

export const HANDLE_DATA__ACTION = 'HANDLE_DATA__ACTION';
export const HANDLE_CONNECTION_ERROR__ACTION = 'HANDLE_CONNECTION_ERROR__ACTION';
export const HANDLE_ERROR__ACTION = 'HANDLE_ERROR__ACTION';
export const HANDLE_COMPLETE__ACTION = 'HANDLE_COMPLETE__ACTION';
export const HANDLE_EXPANDED__ACTION = 'HANDLE_EXPANDED__ACTION';
export const HANDLE_SYNCHRONIZE__ACTION = 'HANDLE_SYNCHRONIZE__ACTION';
export const HANDLE_TREE_PATH__ACTION = 'HANDLE_TREE_PATH__ACTION';

export const machine = {
  LOADING__STATE: {
    HANDLE_CONNECTION_ERROR__ACTION: [ERROR__STATE],
    HANDLE_ERROR__ACTION: [ERROR__STATE],
    HANDLE_DATA__ACTION: [ERROR__STATE, TREE_LOADED__STATE],
    HANDLE_COMPLETE__ACTION: [COMPLETE__STATE],
    HANDLE_SYNCHRONIZE__ACTION: [LOADING__STATE],
    HANDLE_TREE_PATH__ACTION: [LOADING__STATE],
  },
  TREE_LOADED__STATE: {
    HANDLE_ERROR__ACTION: [TREE_LOADED__STATE],
    HANDLE_DATA__ACTION: [TREE_LOADED__STATE],
    HANDLE_COMPLETE__ACTION: [COMPLETE__STATE],
    HANDLE_EXPANDED__ACTION: [TREE_LOADED__STATE],
    HANDLE_SYNCHRONIZE__ACTION: [TREE_LOADED__STATE],
    HANDLE_TREE_PATH__ACTION: [TREE_LOADED__STATE],
  },
  ERROR__STATE: {
    HANDLE_ERROR__ACTION: [ERROR__STATE],
    HANDLE_DATA__ACTION: [TREE_LOADED__STATE],
    HANDLE_COMPLETE__ACTION: [COMPLETE__STATE],
  },
  COMPLETE__STATE: {},
};
