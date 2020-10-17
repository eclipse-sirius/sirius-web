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
export const CONTEXTUAL_MENU_DISPLAYED__STATE = 'CONTEXTUAL_MENU_DISPLAYED__STATE';
export const MODAL_DISPLAYED__STATE = 'MODAL_DISPLAYED__STATE';
export const REDIRECT__STATE = 'REDIRECT__STATE';

export const HANDLE_SHOW_CONTEXT_MENU__ACTION = 'HANDLE_SHOW_CONTEXT_MENU__ACTION';
export const HANDLE_CLOSE_CONTEXT_MENU__ACTION = 'HANDLE_CLOSE_CONTEXT_MENU__ACTION';
export const HANDLE_SHOW_MODAL__ACTION = 'HANDLE_SHOW_MODAL__ACTION';
export const HANDLE_CLOSE_MODAL__ACTION = 'HANDLE_CLOSE_MODAL__ACTION';
export const HANDLE_REDIRECTING__ACTION = 'HANDLE_REDIRECTING__ACTION';

export const machine = {
  EMPTY__STATE: {
    HANDLE_SHOW_CONTEXT_MENU__ACTION: [CONTEXTUAL_MENU_DISPLAYED__STATE],
  },
  CONTEXTUAL_MENU_DISPLAYED__STATE: {
    HANDLE_CLOSE_CONTEXT_MENU__ACTION: [EMPTY__STATE],
    HANDLE_SHOW_MODAL__ACTION: [MODAL_DISPLAYED__STATE],
  },
  MODAL_DISPLAYED__STATE: {
    HANDLE_CLOSE_MODAL__ACTION: [EMPTY__STATE],
    HANDLE_REDIRECTING__ACTION: [REDIRECT__STATE],
  },
};
