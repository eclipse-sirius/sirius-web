/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

export interface ToolbarStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    toolbar: {
      states: {
        idle: {};
        error: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  toolbar: 'idle' | 'error';
};

export interface ToolbarContext {
  modal: string | null;
  currentZoomLevel: string | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type SetZoomLevelEvent = { type: 'SET_ZOOM_LEVEL'; currentZoomLevel: string };
export type ShareDiagramModalEvent = { type: 'SHARE_DIAGRAM_MODAL'; modal: string };
export type CloseModalEvent = { type: 'CLOSE_MODAL' };
export type ToolbarEvent =
  | ShowToastEvent
  | HideToastEvent
  | SetZoomLevelEvent
  | ShareDiagramModalEvent
  | CloseModalEvent;

export const toolbarMachine = Machine<ToolbarContext, ToolbarStateSchema, ToolbarEvent>(
  {
    type: 'parallel',
    context: {
      modal: null,
      currentZoomLevel: '1',
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
      toolbar: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              SET_ZOOM_LEVEL: {
                target: 'idle',
                actions: 'setZoomLevel',
              },
              SHARE_DIAGRAM_MODAL: {
                target: 'idle',
                actions: 'shareDiagramModal',
              },
              CLOSE_MODAL: {
                target: 'idle',
                actions: 'closeModal',
              },
            },
          },
          error: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    actions: {
      setZoomLevel: assign((_, event) => {
        const { currentZoomLevel } = event as SetZoomLevelEvent;
        return { currentZoomLevel };
      }),
      shareDiagramModal: assign((_, event) => {
        const { modal } = event as ShareDiagramModalEvent;
        return { modal };
      }),
      closeModal: assign((_, event) => {
        return { modal: null };
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
