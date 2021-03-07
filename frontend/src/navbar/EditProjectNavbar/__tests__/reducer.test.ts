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
import {
  EMPTY__STATE,
  CONTEXTUAL_MENU_DISPLAYED__STATE,
  MODAL_DISPLAYED__STATE,
  REDIRECT__STATE,
  HANDLE_SHOW_CONTEXT_MENU__ACTION,
  HANDLE_SHOW_MODAL__ACTION,
  HANDLE_REDIRECTING__ACTION,
  HANDLE_CLOSE_CONTEXT_MENU__ACTION,
  HANDLE_CLOSE_MODAL__ACTION,
} from '../machine';
import { initialState, reducer } from '../reducer';

const contextualMenuDisplayedState = {
  viewState: CONTEXTUAL_MENU_DISPLAYED__STATE,
  to: null,
  modalDisplayed: null,
  x: 126,
  y: 250,
};

const modalDisplayedState = {
  viewState: MODAL_DISPLAYED__STATE,
  to: null,
  modalDisplayed: 'DeleteProject',
  x: 0,
  y: 0,
};

describe('EditProjectNavbar - reducer', () => {
  it('test the initial state', () => {
    const state = initialState;

    expect(state.viewState).toBe(EMPTY__STATE);
    expect(state.to).toBeNull();
    expect(state.modalDisplayed).toBeNull();
    expect(state.x).toBe(0);
    expect(state.y).toBe(0);
  });

  it('navigates to the context menu displayed state from empty state, when required', () => {
    const prevState = initialState;

    const action = { type: HANDLE_SHOW_CONTEXT_MENU__ACTION, x: 126, y: 250 };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(CONTEXTUAL_MENU_DISPLAYED__STATE);
    expect(state.to).toBeNull();
    expect(state.modalDisplayed).toBeNull();
    expect(state.x).toBe(126);
    expect(state.y).toBe(250);
  });

  it('navigates to the empty state when the contextual menu is closed', () => {
    const prevState = contextualMenuDisplayedState;
    const action = { type: HANDLE_CLOSE_CONTEXT_MENU__ACTION };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(EMPTY__STATE);
    expect(state.to).toBeNull();
    expect(state.modalDisplayed).toBeNull();
    expect(state.x).toBe(0);
    expect(state.y).toBe(0);
  });

  it('navigates to the modal displayed state when an action requiering a modal is choosed in the contextual menu', () => {
    const prevState = contextualMenuDisplayedState;
    const action = { type: HANDLE_SHOW_MODAL__ACTION, modalDisplayed: 'DeleteProject' };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(MODAL_DISPLAYED__STATE);
    expect(state.to).toBeNull();
    expect(state.modalDisplayed).toBe('DeleteProject');
    expect(state.x).toBe(0);
    expect(state.y).toBe(0);
  });

  it('navigates to the empty state when the modal is closed', () => {
    const prevState = modalDisplayedState;
    const action = { type: HANDLE_CLOSE_MODAL__ACTION };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(EMPTY__STATE);
    expect(state.to).toBeNull();
    expect(state.modalDisplayed).toBeNull();
    expect(state.x).toBe(0);
    expect(state.y).toBe(0);
  });

  it('navigates to the redirect state when the project is deleted using the delete project modal', () => {
    const prevState = modalDisplayedState;
    const action = { type: HANDLE_REDIRECTING__ACTION, to: '/projects' };
    const state = reducer(prevState, action);

    expect(state.viewState).toBe(REDIRECT__STATE);
    expect(state.to).toBe('/projects');
    expect(state.modalDisplayed).toBeNull();
    expect(state.x).toBe(0);
    expect(state.y).toBe(0);
  });
});
