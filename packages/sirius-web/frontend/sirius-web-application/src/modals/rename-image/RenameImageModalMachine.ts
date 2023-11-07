/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { GQLRenameImageMutationData } from './RenameImageModal.types';

export interface RenameImageModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    renameImageModal: {
      states: {
        pristine: {};
        valid: {};
        invalid: {};
        renamingImage: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  renameImageModal: 'pristine' | 'valid' | 'invalid' | 'renamingImage' | 'success';
};

export interface RenameImageModalContext {
  name: string;
  nameMessage: string;
  nameIsInvalid: boolean;
  initialName: string;
  message: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type ChangeNameEvent = { type: 'CHANGE_NAME'; name: string };
export type RequestImageRenamingEvent = { type: 'REQUEST_IMAGE_RENAMING' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLRenameImageMutationData };
export type RenameImageModalEvent =
  | ShowToastEvent
  | HideToastEvent
  | ChangeNameEvent
  | RequestImageRenamingEvent
  | HandleResponseEvent;

const isNameInvalid = (name: string) => name.trim().length < 3;
export const renameImageModalMachine = Machine<
  RenameImageModalContext,
  RenameImageModalStateSchema,
  RenameImageModalEvent
>(
  {
    type: 'parallel',
    context: {
      name: null,
      nameMessage: 'The name must contain at least 3 characters',
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
      renameImageModal: {
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
              REQUEST_IMAGE_RENAMING: {
                target: 'renamingImage',
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
          renamingImage: {
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
        return data.renameImage.__typename === 'SuccessPayload';
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
