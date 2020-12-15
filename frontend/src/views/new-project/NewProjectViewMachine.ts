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

export interface NewProjectViewStateSchema {
  states: {
    newProjectView: {
      states: {
        pristine: {};
        invalid: {};
        valid: {};
        submitted: {};
        success: {};
      };
    };
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
  };
}

export type SchemaValue = {
  newProjectView: 'pristine' | 'invalid' | 'valid' | 'submitted' | 'success';
  toast: 'visible' | 'hidden';
};

export interface NewProjectViewContext {
  name: any;
  message: string;
  nameMessage: string;
  isValidName: boolean;
  newProjectId: string;
}

export type ChangeNameEvent = { type: 'CHANGE_NAME'; name: string };
export type HandleSuccessEvent = { type: 'HANDLE_SUCCESS'; id: string };
export type HandleErrorEvent = { type: 'HANDLE_ERROR' };
export type SubmitEvent = { type: 'SUBMIT' };
export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST'; message: string };
export type NewProjectEvent =
  | ChangeNameEvent
  | SubmitEvent
  | HandleSuccessEvent
  | HandleErrorEvent
  | ShowToastEvent
  | HideToastEvent;

const NAME_MESSAGE = 'The name must contain between 3 and 20 characters';
export const newProjectViewMachine = Machine<NewProjectViewContext, NewProjectViewStateSchema, NewProjectEvent>(
  {
    id: 'NewProjectView',
    type: 'parallel',
    context: {
      name: '',
      nameMessage: NAME_MESSAGE,
      isValidName: false,
      message: null,
      newProjectId: null,
    },
    states: {
      newProjectView: {
        initial: 'pristine',
        states: {
          pristine: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'areNameInvalid',
                  target: 'invalid',
                  actions: 'updateProjectnameInvalid',
                },
                {
                  target: 'valid',
                  actions: 'updateProjectnameValid',
                },
              ],
            },
          },
          invalid: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'areNameInvalid',
                  target: 'invalid',
                  actions: 'updateProjectnameInvalid',
                },
                {
                  target: 'valid',
                  actions: 'updateProjectnameValid',
                },
              ],
            },
          },
          valid: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'areNameInvalid',
                  target: 'invalid',
                  actions: 'updateProjectnameInvalid',
                },
                {
                  target: 'valid',
                  actions: 'updateProjectnameValid',
                },
              ],

              SUBMIT: [
                {
                  target: 'submitted',
                },
              ],
            },
          },
          submitted: {
            on: {
              HANDLE_SUCCESS: [
                {
                  target: 'success',
                  actions: 'updateProjectId',
                },
              ],
              HANDLE_ERROR: [
                {
                  target: 'valid',
                },
              ],
            },
          },
          success: {},
        },
      },
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
    },
  },
  {
    guards: {
      areNameInvalid: (_, event) => {
        const { name } = event as ChangeNameEvent;
        return !(name.trim().length >= 3 && name.trim().length <= 20);
      },
    },
    actions: {
      updateProjectnameValid: assign((_, event) => {
        const { name } = event as ChangeNameEvent;
        return { name, nameMessage: '', isValidName: true };
      }),
      updateProjectnameInvalid: assign((_, event) => {
        const { name } = event as ChangeNameEvent;
        return { name, nameMessage: NAME_MESSAGE, isValidName: false };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      updateProjectId: assign((_, event) => {
        const { id } = event as HandleSuccessEvent;
        return { newProjectId: id };
      }),
      clearMessage: assign(() => {
        return { message: null };
      }),
    },
  }
);
