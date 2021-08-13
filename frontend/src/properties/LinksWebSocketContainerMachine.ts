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
import { SubscriptionResult } from '@apollo/client';
import { Form, Subscriber, WidgetSubscription } from 'form/Form.types';
import {
  GQLFormRefreshedEventPayload,
  GQLLinksEventPayload,
  GQLLinksEventSubscription,
  GQLSubscribersUpdatedEventPayload,
  GQLWidgetSubscriptionsUpdatedEventPayload,
} from 'form/FormEventFragments.types';
import { v4 as uuid } from 'uuid';
import { Selection } from 'workbench/Workbench.types';
import { assign, Machine } from 'xstate';

export interface LinksWebSocketContainerStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    linksWebSocketContainer: {
      states: {
        empty: {};
        unsupportedSelection: {};
        idle: {};
        ready: {};
        complete: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  linksWebSocketContainer: 'empty' | 'unsupportedSelection' | 'idle' | 'ready' | 'complete';
};

export interface LinksWebSocketContainerContext {
  id: string;
  currentSelection: Selection | null;
  form: Form | null;
  subscribers: Subscriber[];
  widgetSubscriptions: WidgetSubscription[];
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type SwitchSelectionEvent = { type: 'SWITCH_SELECTION'; selection: Selection; isRepresentation: boolean };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLLinksEventSubscription>;
};
export type HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
export type LinksWebSocketContainerEvent =
  | SwitchSelectionEvent
  | HandleSubscriptionResultEvent
  | HandleCompleteEvent
  | ShowToastEvent
  | HideToastEvent;

const isFormRefreshedEventPayload = (payload: GQLLinksEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload.__typename === 'FormRefreshedEventPayload';
const isSubscribersUpdatedEventPayload = (
  payload: GQLLinksEventPayload
): payload is GQLSubscribersUpdatedEventPayload => payload.__typename === 'SubscribersUpdatedEventPayload';
const isWidgetSubscriptionsUpdatedEventPayload = (
  payload: GQLLinksEventPayload
): payload is GQLWidgetSubscriptionsUpdatedEventPayload =>
  payload.__typename == 'WidgetSubscriptionsUpdatedEventPayload';

export const linksWebSocketContainerMachine = Machine<
  LinksWebSocketContainerContext,
  LinksWebSocketContainerStateSchema,
  LinksWebSocketContainerEvent
>(
  {
    type: 'parallel',
    context: {
      id: uuid(),
      currentSelection: null,
      form: null,
      subscribers: [],
      widgetSubscriptions: [],
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
      linksWebSocketContainer: {
        initial: 'empty',
        states: {
          empty: {
            on: {
              SWITCH_SELECTION: [
                {
                  cond: 'isSelectionUnsupported',
                  target: 'unsupportedSelection',
                  actions: ['switchSelection', 'clearForm'],
                },
                {
                  target: 'idle',
                  actions: 'switchSelection',
                },
              ],
            },
          },
          unsupportedSelection: {
            on: {
              SWITCH_SELECTION: [
                {
                  cond: 'isSelectionUnsupported',
                  target: 'unsupportedSelection',
                  actions: ['switchSelection', 'clearForm'],
                },
                {
                  target: 'idle',
                  actions: 'switchSelection',
                },
              ],
            },
          },
          idle: {
            on: {
              SWITCH_SELECTION: [
                {
                  cond: 'isSelectionUnsupported',
                  target: 'unsupportedSelection',
                  actions: ['switchSelection', 'clearForm'],
                },
                {
                  target: 'idle',
                  actions: 'switchSelection',
                },
              ],
              HANDLE_SUBSCRIPTION_RESULT: [
                {
                  cond: 'isFormRefreshedEventPayload',
                  target: 'ready',
                  actions: 'handleSubscriptionResult',
                },
                {
                  target: 'idle',
                  actions: 'handleSubscriptionResult',
                },
              ],
            },
          },
          ready: {
            on: {
              SWITCH_SELECTION: [
                {
                  cond: 'isSelectionUnsupported',
                  target: 'unsupportedSelection',
                  actions: ['switchSelection', 'clearForm'],
                },
                {
                  target: 'idle',
                  actions: 'switchSelection',
                },
              ],
              HANDLE_SUBSCRIPTION_RESULT: {
                target: 'ready',
                actions: 'handleSubscriptionResult',
              },
              HANDLE_COMPLETE: {
                target: 'complete',
              },
            },
          },
          complete: {
            on: {
              SWITCH_SELECTION: [
                {
                  cond: 'isSelectionUnsupported',
                  target: 'unsupportedSelection',
                  actions: ['switchSelection', 'clearForm'],
                },
                {
                  target: 'idle',
                  actions: 'switchSelection',
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
      isFormRefreshedEventPayload: (_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        return isFormRefreshedEventPayload(data.linksEvent);
      },
      isSelectionUnsupported: (_, event) => {
        const { selection, isRepresentation } = event as SwitchSelectionEvent;
        return !selection || isRepresentation || selection.kind === 'Unknown' || selection.kind === 'Document';
      },
    },
    actions: {
      switchSelection: assign((_, event) => {
        const { selection } = event as SwitchSelectionEvent;
        return { id: uuid(), currentSelection: selection };
      }),
      clearForm: assign((_, event) => {
        return { form: null };
      }),
      handleSubscriptionResult: assign((_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (isFormRefreshedEventPayload(data.linksEvent)) {
          const { form } = data.linksEvent;
          return { form };
        } else if (isSubscribersUpdatedEventPayload(data.linksEvent)) {
          const { subscribers } = data.linksEvent;
          return { subscribers };
        } else if (isWidgetSubscriptionsUpdatedEventPayload(data.linksEvent)) {
          const { widgetSubscriptions } = data.linksEvent;
          return { widgetSubscriptions };
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
