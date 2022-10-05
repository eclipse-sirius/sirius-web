/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { GQLDeleteProjectMutationData } from 'modals/delete-project/DeleteProjectModal.types';
import { Machine } from 'xstate';

export interface DeleteProjectModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    deleteProjectModal: {
      states: {
        idle: {};
        deletingProject: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  deleteProjectModal: 'idle' | 'deletingProject' | 'success';
};

export interface DeleteProjectModalContext {
  message: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type RequestProjectDeletionEvent = { type: 'REQUEST_PROJECT_DELETION' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLDeleteProjectMutationData };
export type DeleteProjectModalEvent =
  | ShowToastEvent
  | HideToastEvent
  | RequestProjectDeletionEvent
  | HandleResponseEvent;

export const deleteProjectModalMachine = Machine<
  DeleteProjectModalContext,
  DeleteProjectModalStateSchema,
  DeleteProjectModalEvent
>(
  {
    type: 'parallel',
    context: {
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
      deleteProjectModal: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              REQUEST_PROJECT_DELETION: {
                target: 'deletingProject',
              },
            },
          },
          deletingProject: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                },
                {
                  target: 'idle',
                },
              ],
            },
          },
          success: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isResponseSuccessful: (_, event) => {
        const { data } = event as HandleResponseEvent;
        return data.deleteProject.__typename === 'DeleteProjectSuccessPayload';
      },
    },
  }
);
