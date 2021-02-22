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
import { GQLRenameProjectMutationData } from 'modals/rename-project/RenameProjectModal.types';
import { assign, Machine } from 'xstate';

export interface RenameProjectModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    renameProjectModal: {
      states: {
        pristine: {};
        valid: {};
        invalid: {};
        renamingProject: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  renameProjectModal: 'pristine' | 'valid' | 'invalid' | 'renamingProject' | 'success';
};

export interface RenameProjectModalContext {
  name: string;
  nameMessage: string;
  nameIsInvalid: boolean;
  initialName: string;
  message: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type ChangeNameEvent = { type: 'CHANGE_NAME'; name: string };
export type RequestProjectRenamingEvent = { type: 'REQUEST_PROJECT_RENAMING' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLRenameProjectMutationData };
export type RenameProjectModalEvent =
  | ShowToastEvent
  | HideToastEvent
  | ChangeNameEvent
  | RequestProjectRenamingEvent
  | HandleResponseEvent;

const isNameInvalid = (name: string) => name.trim().length < 3 || name.trim().length > 20;
export const renameProjectModalMachine = Machine<
  RenameProjectModalContext,
  RenameProjectModalStateSchema,
  RenameProjectModalEvent
>(
  {
    type: 'parallel',
    context: {
      name: null,
      nameMessage: 'The name must contain between 3 and 20 characters',
      nameIsInvalid: false,
      initialName: null,
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
      renameProjectModal: {
        initial: 'pristine',
        states: {
          pristine: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'isPristine',
                  target: 'pristine',
                  actions: 'updateName',
                },
                {
                  cond: 'isInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
            },
          },
          valid: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'isPristine',
                  target: 'pristine',
                  actions: 'updateName',
                },
                {
                  cond: 'isInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
              REQUEST_PROJECT_RENAMING: {
                target: 'renamingProject',
              },
            },
          },
          invalid: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'isPristine',
                  target: 'pristine',
                  actions: 'updateName',
                },
                {
                  cond: 'isInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
            },
          },
          renamingProject: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                },
                {
                  target: 'valid',
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
      isInvalid: (_, event) => {
        const { name } = event as ChangeNameEvent;
        return isNameInvalid(name);
      },
      isPristine: (context, event) => {
        const { name } = event as ChangeNameEvent;
        const { initialName } = context;
        return name === initialName;
      },
      isResponseSuccessful: (_, event) => {
        const { data } = event as HandleResponseEvent;
        return data.renameProject.__typename === 'RenameProjectSuccessPayload';
      },
    },
    actions: {
      updateName: assign((_, event) => {
        const { name } = event as ChangeNameEvent;
        return { name, nameIsInvalid: isNameInvalid(name) };
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
