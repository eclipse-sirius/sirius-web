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
import { GQLGetModelersQueryData, Modeler } from 'views/modelers/ModelersView.types';
import { assign, Machine } from 'xstate';

export interface ModelersViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    modelersView: {
      states: {
        loading: {};
        loaded: {};
        empty: {};
        missing: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  modelersView: 'loading' | 'loaded' | 'empty' | 'missing';
};

export type ModelersViewModal = 'Rename' | 'Publish';

export interface ModelersViewContext {
  modelers: Modeler[];
  selectedModeler: Modeler | null;
  menuAnchor: HTMLElement | null;
  modalToDisplay: ModelersViewModal | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedModelersEvent = { type: 'HANDLE_FETCHED_MODELERS'; data: GQLGetModelersQueryData };
export type OpenMenuEvent = { type: 'OPEN_MENU'; menuAnchor: HTMLElement; modeler: Modeler };
export type CloseMenuEvent = { type: 'CLOSE_MENU' };
export type OpenModalEvent = { type: 'OPEN_MODAL'; modalToDisplay: ModelersViewModal };
export type CloseModalEvent = { type: 'CLOSE_MODAL' };
export type ModelersViewEvent =
  | FetchedModelersEvent
  | ShowToastEvent
  | HideToastEvent
  | OpenMenuEvent
  | CloseMenuEvent
  | OpenModalEvent
  | CloseModalEvent;

export const modelersViewMachine = Machine<ModelersViewContext, ModelersViewStateSchema, ModelersViewEvent>(
  {
    type: 'parallel',
    context: {
      modelers: [],
      selectedModeler: null,
      menuAnchor: null,
      modalToDisplay: null,
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
      modelersView: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_MODELERS: [
                {
                  cond: 'isMissing',
                  target: 'missing',
                },
                {
                  cond: 'isEmpty',
                  target: 'empty',
                  actions: 'updateModelers',
                },
                {
                  target: 'loaded',
                  actions: 'updateModelers',
                },
              ],
            },
          },
          loaded: {
            on: {
              HANDLE_FETCHED_MODELERS: [
                {
                  cond: 'isMissing',
                  target: 'missing',
                },
                {
                  cond: 'isEmpty',
                  target: 'empty',
                  actions: 'updateModelers',
                },
                {
                  target: 'loaded',
                  actions: 'updateModelers',
                },
              ],
              OPEN_MENU: [
                {
                  actions: 'openMenu',
                },
              ],
              CLOSE_MENU: [
                {
                  actions: 'closeMenu',
                },
              ],
              OPEN_MODAL: [
                {
                  actions: 'openModal',
                },
              ],
              CLOSE_MODAL: [
                {
                  actions: 'closeModal',
                },
              ],
            },
          },
          empty: {
            type: 'final',
          },
          missing: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isMissing: (_, event) => {
        const { data } = event as FetchedModelersEvent;
        return !data.viewer.project;
      },
      isEmpty: (_, event) => {
        const { data } = event as FetchedModelersEvent;
        return data.viewer.project.modelers.length === 0;
      },
    },
    actions: {
      updateModelers: assign((_, event) => {
        const { data } = event as FetchedModelersEvent;
        return { modelers: data.viewer.project.modelers };
      }),
      openMenu: assign((_, event) => {
        const { menuAnchor, modeler } = event as OpenMenuEvent;
        return { menuAnchor, selectedModeler: modeler };
      }),
      closeMenu: assign((_, event) => {
        return { menuAnchor: null, selectedModeler: null };
      }),
      openModal: assign((_, event) => {
        const { modalToDisplay } = event as OpenModalEvent;
        return { menuAnchor: null, modalToDisplay };
      }),
      closeModal: assign((_, event) => {
        return { modalToDisplay: null };
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
