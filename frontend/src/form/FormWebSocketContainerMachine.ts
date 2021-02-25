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
  GQLFormEventPayload,
  GQLFormEventSubscription,
  GQLFormRefreshedEventPayload,
  GQLSubscribersUpdatedEventPayload,
  GQLWidgetSubscriptionsUpdatedEventPayload,
} from 'form/FormEventFragments.types';
import { v4 as uuid } from 'uuid';
import { assign, Machine } from 'xstate';

export interface FormWebSocketContainerStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    formWebSocketContainer: {
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
  formWebSocketContainer: 'idle' | 'ready' | 'complete';
};

export interface FormWebSocketContainerContext {
  id: string;
  formId: string;
  form: Form | null;
  subscribers: Subscriber[];
  widgetSubscriptions: WidgetSubscription[];
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type SwitchFormEvent = { type: 'SWITCH_FORM'; formId: string };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLFormEventSubscription>;
};
export type HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
export type FormWebSocketContainerEvent =
  | SwitchFormEvent
  | HandleSubscriptionResultEvent
  | HandleCompleteEvent
  | ShowToastEvent
  | HideToastEvent;

const isFormRefreshedEventPayload = (payload: GQLFormEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload.__typename === 'FormRefreshedEventPayload';
const isSubscribersUpdatedEventPayload = (payload: GQLFormEventPayload): payload is GQLSubscribersUpdatedEventPayload =>
  payload.__typename === 'SubscribersUpdatedEventPayload';
const isWidgetSubscriptionsUpdatedEventPayload = (
  payload: GQLFormEventPayload
): payload is GQLWidgetSubscriptionsUpdatedEventPayload =>
  payload.__typename == 'WidgetSubscriptionsUpdatedEventPayload';

export const formWebSocketContainerMachine = Machine<
  FormWebSocketContainerContext,
  FormWebSocketContainerStateSchema,
  FormWebSocketContainerEvent
>(
  {
    type: 'parallel',
    context: {
      id: uuid(),
      formId: null,
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
      formWebSocketContainer: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              SWITCH_FORM: {
                target: 'idle',
                actions: 'switchForm',
              },
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
              SWITCH_FORM: {
                target: 'idle',
                actions: 'switchForm',
              },
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
              SWITCH_FORM: {
                target: 'idle',
                actions: 'switchForm',
              },
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
        return isFormRefreshedEventPayload(data.formEvent);
      },
    },
    actions: {
      switchForm: assign((_, event) => {
        const { formId } = event as SwitchFormEvent;
        return { id: uuid(), formId };
      }),
      handleSubscriptionResult: assign((_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (isFormRefreshedEventPayload(data.formEvent)) {
          const { form } = data.formEvent;
          return { form };
        } else if (isSubscribersUpdatedEventPayload(data.formEvent)) {
          const { subscribers } = data.formEvent;
          return { subscribers };
        } else if (isWidgetSubscriptionsUpdatedEventPayload(data.formEvent)) {
          const { widgetSubscriptions } = data.formEvent;
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
