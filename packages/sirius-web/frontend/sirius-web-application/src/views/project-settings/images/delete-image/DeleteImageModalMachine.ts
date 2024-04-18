/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { Machine } from 'xstate';
import { GQLDeleteImageMutationData } from './DeleteImageModal.types';

export interface DeleteImageModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    deleteImageModal: {
      states: {
        idle: {};
        deletingImage: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  deleteImageModal: 'idle' | 'deletingImage' | 'success';
};

export interface DeleteImageModalContext {
  message: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type RequestImageDeletionEvent = { type: 'REQUEST_IMAGE_DELETION' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLDeleteImageMutationData };
export type DeleteImageModalEvent = ShowToastEvent | HideToastEvent | RequestImageDeletionEvent | HandleResponseEvent;

export const deleteImageModalMachine = Machine<
  DeleteImageModalContext,
  DeleteImageModalStateSchema,
  DeleteImageModalEvent
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
      deleteImageModal: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              REQUEST_IMAGE_DELETION: {
                target: 'deletingImage',
              },
            },
          },
          deletingImage: {
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
        return data.deleteImage.__typename === 'SuccessPayload';
      },
    },
  }
);
