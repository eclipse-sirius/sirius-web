/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { GQLUploadImageMutationData } from './UploadImageModal.types';

export interface UploadImageModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    uploadImageModal: {
      states: {
        pristine: {};
        imageSelected: {};
        uploadingImage: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  uploadImageModal: 'pristine' | 'imageSelected' | 'uploadingImage' | 'success';
};

export interface UploadImageModalContext {
  file: File;
  message: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type SelectImageEvent = { type: 'SELECT_IMAGE'; file: File };
export type RequestImageUploadingEvent = { type: 'REQUEST_IMAGE_UPLOADING' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLUploadImageMutationData };
export type UploadImageModalEvent =
  | ShowToastEvent
  | HideToastEvent
  | SelectImageEvent
  | HandleResponseEvent
  | RequestImageUploadingEvent;

export const uploadImageModalMachine = Machine<
  UploadImageModalContext,
  UploadImageModalStateSchema,
  UploadImageModalEvent
>(
  {
    type: 'parallel',
    context: {
      message: undefined,
      file: undefined,
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
      uploadImageModal: {
        initial: 'pristine',
        states: {
          pristine: {
            on: {
              SELECT_IMAGE: {
                target: 'imageSelected',
                actions: 'updateSelectedFile',
              },
            },
          },
          imageSelected: {
            on: {
              REQUEST_IMAGE_UPLOADING: {
                target: 'uploadingImage',
              },
              SELECT_IMAGE: {
                target: 'imageSelected',
                actions: 'updateSelectedFile',
              },
            },
          },
          uploadingImage: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                },
                {
                  target: 'imageSelected',
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
        return data.uploadImage.__typename === 'UploadImageSuccessPayload';
      },
    },
    actions: {
      updateSelectedFile: assign((_, event) => {
        const { file } = event as SelectImageEvent;
        return { file };
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
