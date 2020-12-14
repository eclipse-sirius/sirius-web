/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

export interface UploadProjectViewStateSchema {
  states: {
    toast: {
      states: {
        hidden: {};
        visible: {};
      };
    };
    uploadProjectView: {
      states: {
        idle: {};
        fileSelected: {};
        uploading: {};
        success: {};
      };
    };
  };
}
export type SchemaValue = {
  uploadProjectView: 'idle' | 'fileSelected' | 'uploading' | 'success';
  toast: 'visible' | 'hidden';
};

export interface UploadProjectViewContext {
  file: File;
  message: string;
  newProjectId: string;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleSelectedFileEvent = { type: 'HANDLE_SELECTED_FILE'; file: File };
export type HandleUploadEvent = { type: 'HANDLE_UPLOAD' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: any };
export type UploadProjectEvent =
  | ShowToastEvent
  | HideToastEvent
  | HandleSelectedFileEvent
  | HandleUploadEvent
  | HandleResponseEvent;

export const uploadProjectMachine = Machine<UploadProjectViewContext, UploadProjectViewStateSchema, UploadProjectEvent>(
  {
    id: 'uploadProjectView',
    type: 'parallel',
    context: {
      file: null,
      message: null,
      newProjectId: null,
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
      uploadProjectView: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              HANDLE_SELECTED_FILE: {
                target: 'fileSelected',
                actions: 'setFile',
              },
            },
          },
          fileSelected: {
            on: {
              HANDLE_UPLOAD: {
                target: 'uploading',
              },
              HANDLE_SELECTED_FILE: {
                target: 'fileSelected',
                actions: 'setFile',
              },
            },
          },
          uploading: {
            on: {
              HANDLE_RESPONSE: {
                target: 'success',
                actions: 'setNewProjectId',
              },
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
    actions: {
      setFile: assign((_, event) => {
        const { file } = event as HandleSelectedFileEvent;
        return { file };
      }),
      setNewProjectId: assign((_, event) => {
        const { data } = event as HandleResponseEvent;
        return { newProjectId: data.uploadProject.project.id };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_, event) => {
        return { message: null };
      }),
    },
  }
);
