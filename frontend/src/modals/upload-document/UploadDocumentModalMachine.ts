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
import { GQLUploadDocumentMutationData } from './UploadDocumentModal.types';

export interface UploadDocumentModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    uploadDocumentModal: {
      states: {
        pristine: {};
        documentSelected: {};
        uploadingDocument: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  uploadDocumentModal: 'pristine' | 'documentSelected' | 'uploadingDocument' | 'success';
};

export interface UploadDocumentModalContext {
  file: File;
  message: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type SelectDocumentEvent = { type: 'SELECT_DOCUMENT'; file: File };
export type RequestDocumentUploadingEvent = { type: 'REQUEST_DOCUMENT_UPLOADING' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLUploadDocumentMutationData };
export type UploadDocumentModalEvent =
  | ShowToastEvent
  | HideToastEvent
  | SelectDocumentEvent
  | HandleResponseEvent
  | RequestDocumentUploadingEvent;

export const uploadDocumentModalMachine = Machine<
  UploadDocumentModalContext,
  UploadDocumentModalStateSchema,
  UploadDocumentModalEvent
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
      uploadDocumentModal: {
        initial: 'pristine',
        states: {
          pristine: {
            on: {
              SELECT_DOCUMENT: {
                target: 'documentSelected',
                actions: 'updateSelectedFile',
              },
            },
          },
          documentSelected: {
            on: {
              REQUEST_DOCUMENT_UPLOADING: {
                target: 'uploadingDocument',
              },
              SELECT_DOCUMENT: {
                target: 'documentSelected',
                actions: 'updateSelectedFile',
              },
            },
          },
          uploadingDocument: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                },
                {
                  target: 'documentSelected',
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
        return data.uploadDocument.__typename === 'UploadDocumentSuccessPayload';
      },
    },
    actions: {
      updateSelectedFile: assign((_, event) => {
        const { file } = event as SelectDocumentEvent;
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
