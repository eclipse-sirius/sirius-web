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
import { assign, Machine } from 'xstate';

export interface RenameModelerModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    renameModelerModal: {
      states: {
        pristine: {};
        invalid: {};
        valid: {};
        renamingModeler: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  renameModelerModal: 'pristine' | 'invalid' | 'valid' | 'renamingModeler' | 'success';
};

export interface RenameModelerModalContext {
  name: string;
  nameIsInvalid: boolean;
  message: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleChangedNameEvent = { type: 'HANDLE_CHANGED_NAME'; name: string };
export type HandleRenameModelerEvent = { type: 'HANDLE_RENAME_MODELER' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: any };
export type RenameModelerEvent =
  | HandleChangedNameEvent
  | HandleResponseEvent
  | HandleRenameModelerEvent
  | ShowToastEvent
  | HideToastEvent;

const isNameInvalid = (name: string) => name.trim().length < 3 || name.trim().length > 20;

export const renameModelerModalMachine = Machine<
  RenameModelerModalContext,
  RenameModelerModalStateSchema,
  RenameModelerEvent
>(
  {
    id: 'RenameModelerModal',
    type: 'parallel',
    context: {
      name: '',
      nameIsInvalid: false,
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
      renameModelerModal: {
        initial: 'pristine',
        states: {
          pristine: {
            on: {
              HANDLE_CHANGED_NAME: [
                {
                  cond: 'isFormInvalid',
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
          invalid: {
            on: {
              HANDLE_CHANGED_NAME: [
                {
                  cond: 'isFormInvalid',
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
              HANDLE_CHANGED_NAME: [
                {
                  cond: 'isFormInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
              HANDLE_RENAME_MODELER: [
                {
                  target: 'renamingModeler',
                },
              ],
            },
          },
          renamingModeler: {
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
      isFormInvalid: (_, event) => {
        const { name } = event as HandleChangedNameEvent;
        return isNameInvalid(name);
      },
      isResponseSuccessful: (_, event) => {
        const { data } = event as HandleResponseEvent;
        return data.__typename === 'RenameModelerSuccessPayload';
      },
    },
    actions: {
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_) => {
        return { message: null };
      }),
      updateName: assign((_, event) => {
        const { name } = event as HandleChangedNameEvent;
        return { name, nameIsInvalid: isNameInvalid(name) };
      }),
    },
  }
);
