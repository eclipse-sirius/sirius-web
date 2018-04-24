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
export const MODIFIED__STATE = 'MODIFIED__STATE';
export const REDIRECT__STATE = 'REDIRECT__STATE';

export const INITIALIZE__ACTION = 'INITIALIZE__ACTION';
export const MODIFY_NAME__ACTION = 'MODIFY_NAME__ACTION';
export const HANDLE_ERROR__ACTION = 'HANDLE_ERROR__ACTION';
export const HANDLE_CREATED_PROJECT__ACTION = 'HANDLE_CREATED_PROJECT__ACTION';

export const FSM = {
  INITIAL__STATE: { INITIALIZE__ACTION: [PRISTINE__STATE] },
  PRISTINE__STATE: { MODIFY_NAME__ACTION: [MODIFIED__STATE] },
  MODIFIED__STATE: {
    MODIFY_NAME__ACTION: [MODIFIED__STATE],
    HANDLE_ERROR__ACTION: [MODIFIED__STATE],
    HANDLE_CREATED_PROJECT__ACTION: [REDIRECT__STATE]
  },
  REDIRECT__STATE: {}
};
