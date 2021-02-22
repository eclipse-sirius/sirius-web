/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { GQLGetProjectsQueryData, Project } from 'views/projects/ProjectsView.types';
import { assign, Machine } from 'xstate';

export interface ProjectsViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    projectsView: {
      states: {
        loading: {};
        loaded: {};
        empty: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  projectsView: 'loading' | 'loaded' | 'empty';
};

export type ProjectsViewModal = 'Rename' | 'Delete';

export interface ProjectsViewContext {
  projects: Project[];
  selectedProject: Project | null;
  menuAnchor: HTMLElement | null;
  modalToDisplay: ProjectsViewModal | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedProjectsEvent = { type: 'HANDLE_FETCHED_PROJECTS'; data: GQLGetProjectsQueryData };
export type OpenMenuEvent = { type: 'OPEN_MENU'; menuAnchor: HTMLElement; project: Project };
export type CloseMenuEvent = { type: 'CLOSE_MENU' };
export type OpenModalEvent = { type: 'OPEN_MODAL'; modalToDisplay: ProjectsViewModal };
export type CloseModalEvent = { type: 'CLOSE_MODAL' };
export type ProjectsViewEvent =
  | FetchedProjectsEvent
  | ShowToastEvent
  | HideToastEvent
  | OpenMenuEvent
  | CloseMenuEvent
  | OpenModalEvent
  | CloseModalEvent;

export const projectsViewMachine = Machine<ProjectsViewContext, ProjectsViewStateSchema, ProjectsViewEvent>(
  {
    type: 'parallel',
    context: {
      projects: [],
      selectedProject: null,
      menuAnchor: null,
      modalToDisplay: null,
      message: null,
    },
    states: {
      toast: {
        initial: 'hidden',
        states: {
          hidden: {
            on: {
              SHOW_TOAST: {
                target: 'visible',
                actions: 'setMessage',
              },
            },
          },
          visible: {
            on: {
              HIDE_TOAST: {
                target: 'hidden',
                actions: 'clearMessage',
              },
            },
          },
        },
      },
      projectsView: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_PROJECTS: [
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
            },
          },
          loaded: {
            on: {
              HANDLE_FETCHED_PROJECTS: [
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
              OPEN_MENU: [
                {
                  actions: 'openMenu',
                },
              ],
              CLOSE_MENU: [
                {
                  actions: 'closeMenu',
                },
              ],
              OPEN_MODAL: [
                {
                  actions: 'openModal',
                },
              ],
              CLOSE_MODAL: [
                {
                  actions: 'closeModal',
                },
              ],
            },
          },
          empty: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isEmpty: (_, event) => {
        const { data } = event as FetchedProjectsEvent;
        return data.viewer.projects.length === 0;
      },
    },
    actions: {
      updateProjects: assign((_, event) => {
        const { data } = event as FetchedProjectsEvent;
        return { projects: data.viewer.projects };
      }),
      openMenu: assign((_, event) => {
        const { menuAnchor, project } = event as OpenMenuEvent;
        return { menuAnchor, selectedProject: project };
      }),
      closeMenu: assign((_, event) => {
        return { menuAnchor: null, selectedProject: null };
      }),
      openModal: assign((_, event) => {
        const { modalToDisplay } = event as OpenModalEvent;
        return { menuAnchor: null, modalToDisplay };
      }),
      closeModal: assign((_, event) => {
        return { modalToDisplay: null };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_) => {
        return { message: null };
      }),
    },
  }
);
