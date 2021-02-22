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

export interface PublishModelerModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    publishModelerModal: {
      states: {
        unconfirmed: {};
        confirmed: {};
        publishingModeler: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  publishModelerModal: 'unconfirmed' | 'confirmed' | 'publishingModeler' | 'success';
  toast: 'visible' | 'hidden';
};

export interface PublishModelerModalContext {
  publicationError: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type ConfirmPublicationEvent = { type: 'CONFIRM' };
export type UnconfirmPublicationEvent = { type: 'UNCONFIRM' };
export type PublishModelerEvent = { type: 'PUBLISH' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: any };
export type PublishModelerModalEvent =
  | ConfirmPublicationEvent
  | UnconfirmPublicationEvent
  | PublishModelerEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

export const publishModelerModalMachine = Machine<
  PublishModelerModalContext,
  PublishModelerModalStateSchema,
  PublishModelerModalEvent
>(
  {
    id: 'PublishModelerModal',
    type: 'parallel',
    context: {
      publicationError: null,
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
      publishModelerModal: {
        initial: 'unconfirmed',
        states: {
          unconfirmed: {
            on: {
              CONFIRM: {
                target: 'confirmed',
              },
            },
          },
          confirmed: {
            on: {
              UNCONFIRM: {
                target: 'unconfirmed',
              },
              PUBLISH: {
                target: 'publishingModeler',
              },
            },
          },
          publishingModeler: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                },
                {
                  target: 'confirmed',
                  actions: 'setMessage',
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
        return data.__typename === 'PublishModelerSuccessPayload';
      },
    },
    actions: {
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { publicationError: message };
      }),
      clearMessage: assign((_) => {
        return { publicationError: null };
      }),
    },
  }
);
