/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import { GQLGetProjectsQueryData, Project, ProjectTemplate } from './ProjectsView.types';

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

export type ProjectsViewModal = 'Rename' | 'Delete' | 'ProjectTemplates';

export interface ProjectsViewContext {
  projects: Project[];
  projectTemplates: ProjectTemplate[];
  runningTemplate: ProjectTemplate | null;
  redirectUrl: string | null;
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
export type InvokeTemplateEvent = { type: 'INVOKE_TEMPLATE'; template: ProjectTemplate };
export type RedirectEvent = { type: 'REDIRECT'; projectId: string; representationId: string | null };
export type ProjectsViewEvent =
  | FetchedProjectsEvent
  | ShowToastEvent
  | HideToastEvent
  | OpenMenuEvent
  | CloseMenuEvent
  | OpenModalEvent
  | CloseModalEvent
  | InvokeTemplateEvent
  | RedirectEvent;

export const projectsViewMachine = Machine<ProjectsViewContext, ProjectsViewStateSchema, ProjectsViewEvent>(
  {
    type: 'parallel',
    context: {
      projects: [],
      projectTemplates: [],
      runningTemplate: null,
      redirectUrl: null,
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
              INVOKE_TEMPLATE: [
                {
                  actions: 'invokeTemplate',
                },
              ],
              REDIRECT: [{ actions: 'redirect' }],
            },
          },
          empty: {
            on: {
              INVOKE_TEMPLATE: [
                {
                  actions: 'invokeTemplate',
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
              REDIRECT: [{ actions: 'redirect' }],
            },
          },
        },
      },
    },
  },
  {
    guards: {
      isEmpty: (_, event) => {
        const {
          data: {
            viewer: { projects },
          },
        } = event as FetchedProjectsEvent;
        return projects.edges.length === 0 && !projects.pageInfo.hasPreviousPage;
      },
    },
    actions: {
      updateProjects: assign((_, event) => {
        const {
          data: {
            viewer: { projects, projectTemplates },
          },
        } = event as FetchedProjectsEvent;
        return {
          projects: projects.edges.map((edge) => edge.node),
          pageInfo: projects.pageInfo,
          projectTemplates: projectTemplates.edges.map((edge) => edge.node),
        };
      }),
      openMenu: assign((_, event) => {
        const { menuAnchor, project } = event as OpenMenuEvent;
        return { menuAnchor, selectedProject: project };
      }),
      closeMenu: assign((_) => {
        return { menuAnchor: null, selectedProject: null };
      }),
      openModal: assign((_, event) => {
        const { modalToDisplay } = event as OpenModalEvent;
        return { menuAnchor: null, modalToDisplay };
      }),
      closeModal: assign((_) => {
        return { modalToDisplay: null };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_) => {
        return { message: null };
      }),
      invokeTemplate: assign((_, event) => {
        const { template } = event as InvokeTemplateEvent;
        return { runningTemplate: template };
      }),
      redirect: assign((_, event) => {
        const { projectId, representationId } = event as RedirectEvent;
        if (representationId) {
          return { redirectUrl: `/projects/${projectId}/edit/${representationId}` };
        } else {
          return { redirectUrl: `/projects/${projectId}/edit` };
        }
      }),
    },
  }
);
