/*******************************************************************************
 * Copyright (c) 2020 Obeo.
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
import { assign, Machine } from 'xstate';

export interface ProjectsViewStateSchema {
  states: {
    loading: {};
    loaded: {};
    empty: {};
    error: {};
  };
}

export interface ProjectsViewContext {
  projects: any;
  message: string;
}

export type FetchedProjectsEvent = { type: 'FETCHED_PROJECTS'; projects: any };
export type ErrorFetchingEvent = { type: 'ERROR_FETCHING'; message: string };
export type ProjectstUpdatedEvent = { type: 'PROJECTS_UPDATED'; projects: any };
export type ProjectsViewEvent = FetchedProjectsEvent | ErrorFetchingEvent | ProjectstUpdatedEvent;

export const projectsViewMachine = Machine<ProjectsViewContext, ProjectsViewStateSchema, ProjectsViewEvent>(
  {
    initial: 'loading',
    context: {
      projects: [],
      message: '',
    },
    states: {
      loading: {
        on: {
          FETCHED_PROJECTS: [
            {
              cond: 'isEmpty',
              target: 'empty',
              actions: 'updateProjects',
            },
            {
              target: 'loaded',
              actions: 'updateProjects',
            },
          ],
          ERROR_FETCHING: [
            {
              target: 'error',
              actions: 'setMessage',
            },
          ],
        },
      },
      loaded: {
        on: {
          PROJECTS_UPDATED: [
            {
              cond: 'isEmpty',
              target: 'empty',
              actions: 'updateProjects',
            },
            {
              target: 'loaded',
              actions: 'updateProjects',
            },
          ],

          ERROR_FETCHING: [
            {
              target: 'error',
              actions: 'setMessage',
            },
          ],
        },
      },
      empty: {},
      error: {},
    },
  },
  {
    guards: {
      isEmpty: (_, event) => {
        const { projects } = event as FetchedProjectsEvent;
        return projects.length === 0;
      },
    },
    actions: {
      updateProjects: assign((_, event) => {
        const { projects } = event as ProjectstUpdatedEvent;
        return { projects, message: '' };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ErrorFetchingEvent;
        return {
          message:
            'An unexpected error has occured while retrieving the project, please contact your administrator. ' +
            message,
        };
      }),
    },
  }
);
