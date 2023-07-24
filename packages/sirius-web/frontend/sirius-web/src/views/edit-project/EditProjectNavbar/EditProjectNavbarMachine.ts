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

import { SubscriptionResult } from '@apollo/client';
import { Machine, assign } from 'xstate';
import {
  GQLProjectEventPayload,
  GQLProjectEventSubscription,
  GQLProjectRenamedEventPayload,
} from './EditProjectNavbar.types';

export interface EditProjectNavbarStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    navbar: {
      states: {
        empty: {};
        contextualMenuDisplayedState: {};
        modalDisplayedState: {};
        redirectState: {};
        complete: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  navbar: 'empty' | 'contextualMenuDisplayedState' | 'modalDisplayedState' | 'redirectState' | 'complete';
};

export interface EditProjectNavbarContext {
  id: string;
  to: string | null;
  modalDisplayed: string | null;
  projectMenuAnchor: (EventTarget & HTMLElement) | null;
  projectName: string;
  message: string | null;
}

export type HandleShowContextMenuEvent = {
  type: 'HANDLE_SHOW_CONTEXT_MENU_EVENT';
  projectMenuAnchor: EventTarget & HTMLElement;
};
export type HandleCloseContextMenuEvent = { type: 'HANDLE_CLOSE_CONTEXT_MENU_EVENT' };
export type HandleShowModalEvent = { type: 'HANDLE_SHOW_MODAL_EVENT'; modalName: string };
export type HandleCloseModalEvent = { type: 'HANDLE_CLOSE_MODAL_EVENT' };
export type HandleRedirectingEvent = { type: 'HANDLE_REDIRECTING_EVENT'; to: string };

export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLProjectEventSubscription>;
};
export type HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };

export type EditProjectNavbarEvent =
  | HandleShowContextMenuEvent
  | HandleCloseContextMenuEvent
  | HandleShowModalEvent
  | HandleCloseModalEvent
  | HandleRedirectingEvent
  | HandleSubscriptionResultEvent
  | HandleCompleteEvent
  | ShowToastEvent
  | HideToastEvent;

const isProjectRenamedEventPayload = (payload: GQLProjectEventPayload): payload is GQLProjectRenamedEventPayload =>
  payload.__typename === 'ProjectRenamedEventPayload';

export const editProjectNavbarMachine = Machine<
  EditProjectNavbarContext,
  EditProjectNavbarStateSchema,
  EditProjectNavbarEvent
>(
  {
    type: 'parallel',
    context: {
      id: crypto.randomUUID(),
      to: null,
      modalDisplayed: null,
      projectMenuAnchor: null,
      projectName: null,
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
      navbar: {
        initial: 'empty',
        states: {
          empty: {
            on: {
              HANDLE_SHOW_CONTEXT_MENU_EVENT: {
                target: 'contextualMenuDisplayedState',
                actions: 'showContextMenu',
              },
              HANDLE_SUBSCRIPTION_RESULT: {
                actions: 'handleSubscriptionResult',
              },
              HANDLE_COMPLETE: {
                target: 'complete',
              },
            },
          },
          contextualMenuDisplayedState: {
            on: {
              HANDLE_CLOSE_CONTEXT_MENU_EVENT: {
                target: 'empty',
                actions: 'closeContextMenu',
              },
              HANDLE_SHOW_MODAL_EVENT: {
                target: 'modalDisplayedState',
                actions: 'showModal',
              },
              HANDLE_SUBSCRIPTION_RESULT: {
                actions: 'handleSubscriptionResult',
              },
            },
          },
          modalDisplayedState: {
            on: {
              HANDLE_CLOSE_MODAL_EVENT: {
                target: 'empty',
                actions: 'closeModal',
              },
              HANDLE_REDIRECTING_EVENT: {
                target: 'redirectState',
                actions: 'redirecting',
              },
              HANDLE_SUBSCRIPTION_RESULT: {
                actions: 'handleSubscriptionResult',
              },
            },
          },
          redirectState: {
            type: 'final',
          },
          complete: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    actions: {
      showContextMenu: assign((_, event) => {
        const { projectMenuAnchor } = event as HandleShowContextMenuEvent;
        return { projectMenuAnchor };
      }),
      hideContextMenu: assign((_) => {
        return { projectMenuAnchor: null };
      }),
      showModal: assign((_, event) => {
        const { modalName } = event as HandleShowModalEvent;
        return { projectMenuAnchor: null, modalDisplayed: modalName };
      }),
      closeModal: assign((_) => {
        return { modalDisplayed: null };
      }),
      redirecting: assign((_, event) => {
        const { to } = event as HandleRedirectingEvent;
        return { to, projectMenuAnchor: null, modalDisplayed: null };
      }),
      handleSubscriptionResult: assign((_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (isProjectRenamedEventPayload(data.projectEvent)) {
          const { newName } = data.projectEvent;
          return {
            projectName: newName,
          };
        }
        return {};
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
