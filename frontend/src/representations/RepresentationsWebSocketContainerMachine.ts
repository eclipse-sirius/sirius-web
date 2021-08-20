/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
import { List, Subscriber, WidgetSubscription } from 'form/Form.types';
import {
  GQLFormRefreshedEventPayload,
  GQLPropertiesEventPayload,
  GQLRepresentationsEventSubscription,
  GQLSubscribersUpdatedEventPayload,
  GQLWidget,
  GQLWidgetSubscriptionsUpdatedEventPayload,
} from 'form/FormEventFragments.types';
import { v4 as uuid } from 'uuid';
import { Selection } from 'workbench/Workbench.types';
import { assign, Machine } from 'xstate';

export interface RepresentationsWebSocketContainerStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    representationsWebSocketContainer: {
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
  representationsWebSocketContainer: 'empty' | 'unsupportedSelection' | 'idle' | 'ready' | 'complete';
};

export interface RepresentationsWebSocketContainerContext {
  id: string;
  currentSelection: Selection | null;
  formId: string | null;
  widget: List | null;
  subscribers: Subscriber[];
  widgetSubscriptions: WidgetSubscription[];
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type SwitchSelectionEvent = { type: 'SWITCH_SELECTION'; selection: Selection; isRepresentation: boolean };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLRepresentationsEventSubscription>;
};
export type HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
export type RepresentationsWebSocketContainerEvent =
  | SwitchSelectionEvent
  | HandleSubscriptionResultEvent
  | HandleCompleteEvent
  | ShowToastEvent
  | HideToastEvent;

const isFormRefreshedEventPayload = (payload: GQLPropertiesEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload.__typename === 'FormRefreshedEventPayload';
const isSubscribersUpdatedEventPayload = (
  payload: GQLPropertiesEventPayload
): payload is GQLSubscribersUpdatedEventPayload => payload.__typename === 'SubscribersUpdatedEventPayload';
const isWidgetSubscriptionsUpdatedEventPayload = (
  payload: GQLPropertiesEventPayload
): payload is GQLWidgetSubscriptionsUpdatedEventPayload =>
  payload.__typename == 'WidgetSubscriptionsUpdatedEventPayload';
const isList = (widget: GQLWidget): widget is List => widget.__typename === 'List';

export const representationsWebSocketContainerMachine = Machine<
  RepresentationsWebSocketContainerContext,
  RepresentationsWebSocketContainerStateSchema,
  RepresentationsWebSocketContainerEvent
>(
  {
    type: 'parallel',
    context: {
      id: uuid(),
      currentSelection: null,
      widget: null,
      formId: null,
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
      representationsWebSocketContainer: {
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
        return isFormRefreshedEventPayload(data.representationsEvent);
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
        return { widget: null };
      }),
      handleSubscriptionResult: assign((_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (isFormRefreshedEventPayload(data.representationsEvent)) {
          const { form } = data.representationsEvent;
          const widget = form.pages[0]?.groups[0]?.widgets[0];
          if (isList(widget)) {
            return { widget, formId: form.id };
          }
        } else if (isSubscribersUpdatedEventPayload(data.representationsEvent)) {
          const { subscribers } = data.representationsEvent;
          return { subscribers };
        } else if (isWidgetSubscriptionsUpdatedEventPayload(data.representationsEvent)) {
          const { widgetSubscriptions } = data.representationsEvent;
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
