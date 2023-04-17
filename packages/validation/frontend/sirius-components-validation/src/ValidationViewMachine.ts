/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo and others.
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
import { assign, Machine } from 'xstate';
import {
  Category,
  GQLValidationEventPayload,
  GQLValidationEventSubscription,
  GQLValidationRefreshedEventPayload,
  Validation,
} from './ValidationView.types';

export interface ValidationViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    validationView: {
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
  validationView: 'idle' | 'ready' | 'complete';
};

export interface ValidationViewContext {
  id: string;
  validation: Validation | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLValidationEventSubscription>;
};
export type HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
export type ValidationViewEvent = HandleSubscriptionResultEvent | HandleCompleteEvent | ShowToastEvent | HideToastEvent;

const isValidationRefreshedEventPayload = (
  payload: GQLValidationEventPayload
): payload is GQLValidationRefreshedEventPayload => payload.__typename === 'ValidationRefreshedEventPayload';

export const validationViewMachine = Machine<ValidationViewContext, ValidationViewStateSchema, ValidationViewEvent>(
  {
    type: 'parallel',
    context: {
      id: crypto.randomUUID(),
      validation: null,
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
      validationView: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              HANDLE_SUBSCRIPTION_RESULT: [
                {
                  cond: 'isValidationRefreshedEventPayload',
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
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isValidationRefreshedEventPayload: (_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        return isValidationRefreshedEventPayload(data.validationEvent);
      },
    },
    actions: {
      handleSubscriptionResult: assign((_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (isValidationRefreshedEventPayload(data.validationEvent)) {
          const { validation } = data.validationEvent;

          const categories: Category[] = [];
          const processedValidation: Validation = { categories };

          validation.diagnostics.forEach((diagnostic) => {
            let category: Category = categories.find((category) => category.kind === diagnostic.kind);
            if (!category) {
              category = {
                kind: diagnostic.kind,
                diagnostics: [],
              };
              categories.push(category);
            }

            category.diagnostics.push({ id: diagnostic.id, message: diagnostic.message });
          });

          return { validation: processedValidation };
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
