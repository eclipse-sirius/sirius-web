/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { assign, Machine } from 'xstate';
import {
  GQLSelection,
  GQLSelectionEventPayload,
  GQLSelectionEventSubscription,
  GQLSelectionRefreshedEventPayload,
} from './SelectionEvent.types';

export interface SelectionDialogStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    selectionDialog: {
      states: {
        idle: {};
        ready: {};
        complete: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  selectionDialog: 'idle' | 'ready' | 'complete';
};

export interface SelectionDialogContext {
  id: string;
  selection: GQLSelection | null;
  message: string | null;
  selectedObjects: Selection;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleSelectionUpdatedEvent = { type: 'HANDLE_SELECTION_UPDATED'; selectedObjects: Selection };
export type HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLSelectionEventSubscription>;
};

export type SelectionDialogEvent =
  | HandleCompleteEvent
  | HandleSelectionUpdatedEvent
  | HandleSubscriptionResultEvent
  | ShowToastEvent
  | HideToastEvent;

const isSelectionRefreshedEventPayload = (
  payload: GQLSelectionEventPayload
): payload is GQLSelectionRefreshedEventPayload => payload.__typename === 'SelectionRefreshedEventPayload';

export const selectionDialogMachine = Machine<SelectionDialogContext, SelectionDialogStateSchema, SelectionDialogEvent>(
  {
    type: 'parallel',
    context: {
      id: crypto.randomUUID(),
      selection: null,
      message: null,
      selectedObjects: { entries: [] },
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
      selectionDialog: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              HANDLE_SUBSCRIPTION_RESULT: [
                {
                  cond: 'isSelectionRefreshedEventPayload',
                  target: 'ready',
                  actions: 'handleSubscriptionResult',
                },
              ],
            },
          },
          ready: {
            on: {
              HANDLE_SELECTION_UPDATED: [
                {
                  target: 'ready',
                  actions: 'handleSelectionUpdated',
                },
              ],
              HANDLE_SUBSCRIPTION_RESULT: [
                {
                  cond: 'isSelectionRefreshedEventPayload',
                  target: 'ready',
                  actions: 'handleSubscriptionResult',
                },
              ],
              HANDLE_COMPLETE: {
                target: 'complete',
              },
            },
          },
          complete: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isSelectionRefreshedEventPayload: (_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        return !!data && isSelectionRefreshedEventPayload(data.selectionEvent);
      },
    },
    actions: {
      handleSelectionUpdated: assign((_, event) => {
        const { selectedObjects } = event as HandleSelectionUpdatedEvent;
        return { selectedObjects };
      }),
      handleSubscriptionResult: assign((_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (data && isSelectionRefreshedEventPayload(data.selectionEvent)) {
          const { selection } = data.selectionEvent;
          return { selection };
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
