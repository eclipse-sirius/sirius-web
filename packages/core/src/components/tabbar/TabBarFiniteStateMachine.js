/*******************************************************************************
 * Copyright (c) 2018 Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2
 * which accompanies this distribution and is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *******************************************************************************/

export const INITIAL__STATE = 'INITIAL__STATE';
export const PRISTINE__STATE = 'PRISTINE__STATE';
export const MOVED__STATE = 'MOVED__STATE';

export const INITIALIZE__ACTION = 'INITIALIZE__ACTION';
export const HANDLE_NEXT__ACTION = 'HANDLE_NEXT__ACTION';
export const HANDLE_PREVIOUS__ACTION = 'HANDLE_PREVIOUS__ACTION';
export const HANDLE_TAB_SELECTED__ACTION = 'HANDLE_TAB_SELECTED__ACTION';

export const FSM = {
  INITIAL__STATE: { INITIALIZE__ACTION: [PRISTINE__STATE] },
  PRISTINE__STATE: {
    HANDLE_NEXT__ACTION: [MOVED__STATE],
    HANDLE_PREVIOUS__ACTION: [MOVED__STATE],
    HANDLE_TAB_SELECTED__ACTION: [MOVED__STATE]
  },
  MOVED__STATE: {
    HANDLE_NEXT__ACTION: [MOVED__STATE],
    HANDLE_PREVIOUS__ACTION: [MOVED__STATE],
    HANDLE_TAB_SELECTED__ACTION: [MOVED__STATE]
  }
};
