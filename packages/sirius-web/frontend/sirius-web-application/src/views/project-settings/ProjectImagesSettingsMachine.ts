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
import { Machine, assign } from 'xstate';
import { GQLCustomImage, GQLGetImagesQueryData } from './ProjectImagesSettings.types';

export interface ImagesViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    imagesSettings: {
      states: {
        loading: {};
        loaded: {};
        empty: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  imagesSettings: 'loading' | 'loaded' | 'empty';
};

export type ImagesSettingsModal = 'Upload' | 'Rename' | 'Delete';

export interface ImagesSettingsContext {
  images: GQLCustomImage[];
  modalToDisplay: ImagesSettingsModal | null;
  message: string | null;
  currentImage: GQLCustomImage | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedImagesEvent = { type: 'HANDLE_FETCHED_IMAGES'; data: GQLGetImagesQueryData };
export type OpenModalEvent = { type: 'OPEN_MODAL'; modalToDisplay: ImagesSettingsModal };
export type CloseModalEvent = { type: 'CLOSE_MODAL' };
export type SelectImageEvent = { type: 'SELECT_IMAGE'; image: GQLCustomImage };
export type ImagesSettingsEvent =
  | FetchedImagesEvent
  | ShowToastEvent
  | HideToastEvent
  | OpenModalEvent
  | CloseModalEvent
  | SelectImageEvent;

export const imagesViewMachine = Machine<ImagesSettingsContext, ImagesViewStateSchema, ImagesSettingsEvent>(
  {
    type: 'parallel',
    context: {
      images: [],
      modalToDisplay: null,
      message: null,
      currentImage: null,
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
      imagesSettings: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_IMAGES: [
                {
                  cond: 'isEmpty',
                  target: 'empty',
                  actions: 'updateImages',
                },
                {
                  target: 'loaded',
                  actions: 'updateImages',
                },
              ],
            },
          },
          loaded: {
            on: {
              HANDLE_FETCHED_IMAGES: [
                {
                  cond: 'isEmpty',
                  target: 'empty',
                  actions: 'updateImages',
                },
                {
                  target: 'loaded',
                  actions: 'updateImages',
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
              SELECT_IMAGE: [
                {
                  actions: 'selectImage',
                },
              ],
            },
          },
          empty: {
            type: 'final',
            on: {
              HANDLE_FETCHED_IMAGES: [
                {
                  cond: 'isEmpty',
                  target: 'empty',
                  actions: 'updateImages',
                },
                {
                  target: 'loaded',
                  actions: 'updateImages',
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
        },
      },
    },
  },
  {
    guards: {
      isEmpty: (_, event) => {
        const {
          data: {
            viewer: {
              project: { customImages },
            },
          },
        } = event as FetchedImagesEvent;
        return !customImages || customImages.length === 0;
      },
    },
    actions: {
      updateImages: assign((_, event) => {
        const {
          data: {
            viewer: {
              project: { customImages },
            },
          },
        } = event as FetchedImagesEvent;
        return { images: customImages };
      }),
      openModal: assign((_, event) => {
        const { modalToDisplay } = event as OpenModalEvent;
        return { modalToDisplay };
      }),
      closeModal: assign((_) => {
        return { modalToDisplay: null };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_) => {
        return { message: null };
      }),
      selectImage: assign((_, event) => {
        const { image } = event as SelectImageEvent;
        return { currentImage: image };
      }),
    },
  }
);
