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

export interface NewModelerViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    newModelerView: {
      states: {
        pristine: {};
        invalid: {};
        valid: {};
        creatingModeler: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  newModelerView: 'pristine' | 'invalid' | 'valid' | 'creatingModeler' | 'success';
  toast: 'visible' | 'hidden';
};

export interface NewModelerViewContext {
  name: string;
  nameMessage: string;
  nameIsInvalid: boolean;
  message: string;
  newModelerId: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleChangedNameEvent = { type: 'HANDLE_CHANGED_NAME'; name: string };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: any };
export type HandleCreateModelerEvent = { type: 'HANDLE_CREATE_MODELER' };
export type NewModelerEvent =
  | HandleChangedNameEvent
  | HandleCreateModelerEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

const isNameInvalid = (name: string) => name.trim().length < 3 || name.trim().length > 20;
export const newModelerViewMachine = Machine<NewModelerViewContext, NewModelerViewStateSchema, NewModelerEvent>(
  {
    id: 'NewModelerView',
    type: 'parallel',
    context: {
      name: '',
      nameMessage: 'The name must contain between 3 and 20 characters',
      nameIsInvalid: false,
      message: null,
      newModelerId: null,
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
      newModelerView: {
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
              HANDLE_CREATE_MODELER: [
                {
                  target: 'creatingModeler',
                },
              ],
            },
          },
          creatingModeler: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                  actions: 'updateModelerId',
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
        return data.createModeler.__typename === 'CreateModelerSuccessPayload';
      },
    },
    actions: {
      updateName: assign((_, event) => {
        const { name } = event as HandleChangedNameEvent;
        return { name, nameIsInvalid: isNameInvalid(name) };
      }),
      updateModelerId: assign((_, event) => {
        const { data } = event as HandleResponseEvent;
        return { newModelerId: data.createModeler.modeler.id };
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
