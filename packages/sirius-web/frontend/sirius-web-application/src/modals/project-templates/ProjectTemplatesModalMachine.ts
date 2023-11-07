/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { ProjectTemplate } from '../../views/projects/ProjectsView.types';
import { GQLgetProjectTemplatesQueryData } from './ProjectTemplatesModal.types';

export interface ProjectTemplatesModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    projectTemplatesModal: {
      states: {
        loading: {};
        loaded: {};
        executingTemplate: {};
        templateCreated: {};
        empty: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  projectTemplatesModal: 'loading' | 'loaded' | 'executingTemplate' | 'templateCreated' | 'empty';
};

export interface ProjectTemplatesModalContext {
  // Current page index, from 1
  page: number;
  // The templates to display on the current page. May be empty for the last page.
  templates: ProjectTemplate[];
  // Total number of templates (unknown before the first load)
  templatesCount: number;
  // The template which is currently executed on the backend; we're waiting for its result
  runningTemplate: ProjectTemplate | null;
  // The URL to redirect to once we received the success payload for the executed template.
  redirectURL: string | null;
  // The message to display in the toast.
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedProjectTemplatesEvent = { type: 'HANDLE_FETCHED_TEMPLATES'; data: GQLgetProjectTemplatesQueryData };
export type ChangePageEvent = { type: 'CHANGE_PAGE'; page: number };
export type InvokeTemplateEvent = { type: 'INVOKE_TEMPLATE'; template: ProjectTemplate };
export type RedirectEvent = { type: 'REDIRECT'; projectId: string; representationId: string | null };
export type ProjectTemplatesModalEvent =
  | FetchedProjectTemplatesEvent
  | ChangePageEvent
  | ShowToastEvent
  | InvokeTemplateEvent
  | HideToastEvent
  | RedirectEvent;

export const projectTemplatesModalMachine = Machine<
  ProjectTemplatesModalContext,
  ProjectTemplatesModalStateSchema,
  ProjectTemplatesModalEvent
>(
  {
    type: 'parallel',
    context: {
      page: 1,
      templates: [],
      templatesCount: 0,
      runningTemplate: null,
      redirectURL: null,
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
      projectTemplatesModal: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_TEMPLATES: [
                {
                  cond: 'isEmpty',
                  target: 'empty',
                  actions: 'updateTemplates',
                },
                {
                  target: 'loaded',
                  actions: 'updateTemplates',
                },
              ],
            },
          },
          loaded: {
            on: {
              HANDLE_FETCHED_TEMPLATES: [
                {
                  cond: 'isEmpty',
                  target: 'empty',
                  actions: 'updateTemplates',
                },
                {
                  target: 'loaded',
                  actions: 'updateTemplates',
                },
              ],
              CHANGE_PAGE: [
                {
                  actions: 'changePage',
                },
              ],
              INVOKE_TEMPLATE: [
                {
                  target: 'executingTemplate',
                  actions: 'invokeTemplate',
                },
              ],
            },
          },
          executingTemplate: {
            on: {
              REDIRECT: {
                target: 'templateCreated',
                actions: 'redirect',
              },
            },
          },
          templateCreated: {
            type: 'final',
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
        const {
          data: {
            viewer: { projectTemplates },
          },
        } = event as FetchedProjectTemplatesEvent;
        return projectTemplates.edges.length === 0 && !projectTemplates.pageInfo.hasPreviousPage;
      },
    },
    actions: {
      updateTemplates: assign((context, event) => {
        const {
          data: {
            viewer: { projectTemplates },
          },
        } = event as FetchedProjectTemplatesEvent;

        if (projectTemplates.edges.length === 0 && projectTemplates.pageInfo.hasPreviousPage) {
          return { page: context.page - 1 };
        }
        return {
          templatesCount: projectTemplates.pageInfo.count,
          templates: projectTemplates.edges.map((edge) => edge.node),
        };
      }),
      changePage: assign((_, event) => {
        const { page } = event as ChangePageEvent;
        return { page };
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
          return { redirectURL: `/projects/${projectId}/edit/${representationId}` };
        } else {
          return { redirectURL: `/projects/${projectId}/edit` };
        }
      }),
    },
  }
);
