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
  ERROR__STATE,
  LOADED__STATE,
  HANDLE_ERROR_FETCHING_PROJECTS__ACTION,
  HANDLE_FETCHED_PROJECTS__ACTION,
  HANDLE_PROJECTS_UPDATED__ACTION,
} from '../machine';
import { initialState, reducer } from '../reducer';

const projectsLoadedState = {
  viewState: LOADED__STATE,
  projects: [
    {
      projectId: 'azerty',
      name: 'Azerty',
    },
    {
      projectId: 'qwerty',
      name: 'Qwerty',
    },
  ],
};

const unexpectedErrorResponse = {
  errors: [
    {
      message: 'Unexpected error',
      locations: [
        {
          line: 3,
          column: 5,
        },
      ],
      extensions: {
        classification: 'DataFetchingError',
      },
    },
  ],
};

describe('ProjectsView - reducer', () => {
  it('navigates to the empty state after loading no projects', () => {
    const prevState = initialState;
    const response = {
      viewer: {
        projects: [],
      },
    };
    const action = { type: HANDLE_FETCHED_PROJECTS__ACTION, response };
    const state = reducer(prevState, action);
    expect(state.viewState).toBe(EMPTY__STATE);
  });

  it('navigates to the projects loaded state after loading some projects', () => {
    const prevState = initialState;
    const response = {
      viewer: {
        projects: [
          {
            projectId: 'azerty',
            name: 'Azerty',
          },
        ],
      },
    };
    const action = { type: HANDLE_FETCHED_PROJECTS__ACTION, response };
    const state = reducer(prevState, action);
    expect(state.viewState).toBe(LOADED__STATE);
    expect(state.projects).toHaveLength(response.viewer.projects.length);
  });

  it('navigates to the error state after an unexpected error during the loading', () => {
    const prevState = initialState;
    const error = unexpectedErrorResponse;

    const action = { type: HANDLE_ERROR_FETCHING_PROJECTS__ACTION, error };
    const state = reducer(prevState, action);
    expect(state.viewState).toBe(ERROR__STATE);
    expect(state.message).not.toBeNull();
    expect(state.message).not.toBeUndefined();
  });

  it('navigates to the projects loaded state after deleting a project', () => {
    const prevState = projectsLoadedState;
    const response = {
      viewer: {
        projects: [
          {
            projectId: 'azerty',
            name: 'Azerty',
          },
        ],
      },
    };
    const action = { type: HANDLE_PROJECTS_UPDATED__ACTION, response };
    const state = reducer(prevState, action);
    expect(state.viewState).toBe(LOADED__STATE);
    expect(state.projects).toHaveLength(response.viewer.projects.length);
  });

  it('navigates to the empty state after the deletion of the last project', () => {
    const prevState = projectsLoadedState;
    const response = {
      viewer: {
        projects: [],
      },
    };
    const action = { type: HANDLE_PROJECTS_UPDATED__ACTION, response };
    const state = reducer(prevState, action);
    expect(state.viewState).toBe(EMPTY__STATE);
  });

  it('navigates to the error state after an unexpected error during the update', () => {
    const prevState = projectsLoadedState;
    const error = unexpectedErrorResponse;

    const action = { type: HANDLE_ERROR_FETCHING_PROJECTS__ACTION, error };
    const state = reducer(prevState, action);
    expect(state.viewState).toBe(ERROR__STATE);
    expect(state.message).not.toBeNull();
    expect(state.message).not.toBeUndefined();
  });
});
