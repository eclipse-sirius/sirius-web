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
import { GQLDeleteDocumentMutationData } from './DeleteDocumentModal.types';

export interface DeleteDocumentModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    deleteDocumentModal: {
      states: {
        pristine: {};
        deletingDocument: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  deleteDocumentModal: 'pristine' | 'deletingDocument' | 'success';
};

export interface DeleteDocumentModalContext {
  message: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type RequestDocumentDeletingEvent = { type: 'REQUEST_DOCUMENT_DELETING' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLDeleteDocumentMutationData };
export type DeleteDocumentModalEvent =
  | ShowToastEvent
  | HideToastEvent
  | RequestDocumentDeletingEvent
  | HandleResponseEvent;

export const deleteDocumentModalMachine = Machine<
  DeleteDocumentModalContext,
  DeleteDocumentModalStateSchema,
  DeleteDocumentModalEvent
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
      deleteDocumentModal: {
        initial: 'pristine',
        states: {
          pristine: {
            on: {
              REQUEST_DOCUMENT_DELETING: {
                target: 'deletingDocument',
              },
            },
          },
          deletingDocument: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                },
                {
                  target: 'pristine',
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
        return data.deleteDocument.__typename === 'DeleteDocumentSuccessPayload';
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
    },
  }
);
