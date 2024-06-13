/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
  GQLFormDescriptionEditor,
  GQLFormDescriptionEditorEventPayload,
  GQLFormDescriptionEditorEventSubscription,
  GQLFormDescriptionEditorRefreshedEventPayload,
} from './FormDescriptionEditorEventFragment.types';

export interface FormDescriptionEditorRepresentationStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    formDescriptionEditorRepresentation: {
      states: {
        empty: {};
        loading: {};
        ready: {};
        complete: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  formDescriptionEditorRepresentation: 'loading' | 'ready' | 'empty' | 'complete';
};

export interface FormDescriptionEditorRepresentationContext {
  id: string;
  formDescriptionEditor: GQLFormDescriptionEditor;
  message: string | null;
}
export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLFormDescriptionEditorEventSubscription>;
};
export type CompleteEvent = { type: 'HANDLE_COMPLETE' };

export type InitializeRepresentationEvent = {
  type: 'INITIALIZE';
};

export type FormDescriptionEditorRepresentationEvent =
  | ShowToastEvent
  | HideToastEvent
  | CompleteEvent
  | InitializeRepresentationEvent
  | HandleSubscriptionResultEvent;

const isFormDescriptionEditorRefreshedEventPayload = (
  payload: GQLFormDescriptionEditorEventPayload
): payload is GQLFormDescriptionEditorRefreshedEventPayload =>
  payload.__typename === 'FormDescriptionEditorRefreshedEventPayload';

export const formDescriptionEditorRepresentationMachine = Machine<
  FormDescriptionEditorRepresentationContext,
  FormDescriptionEditorRepresentationStateSchema,
  FormDescriptionEditorRepresentationEvent
>(
  {
    type: 'parallel',
    context: {
      id: crypto.randomUUID(),
      formDescriptionEditor: null,
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
      formDescriptionEditorRepresentation: {
        initial: 'loading',
        states: {
          empty: {},
          loading: {
            on: {
              INITIALIZE: [
                {
                  target: 'ready',
                  actions: 'initialize',
                },
              ],
            },
          },
          ready: {
            on: {
              HANDLE_SUBSCRIPTION_RESULT: [
                {
                  actions: 'handleSubscriptionResult',
                },
              ],
              HANDLE_COMPLETE: [
                {
                  target: 'complete',
                  actions: 'handleComplete',
                },
              ],
            },
          },
          complete: {},
        },
      },
    },
  },
  {
    actions: {
      initialize: assign((_) => {
        return {
          message: undefined,
        };
      }),
      handleComplete: assign((_) => {
        return {};
      }),
      handleSubscriptionResult: assign((_, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (isFormDescriptionEditorRefreshedEventPayload(data.formDescriptionEditorEvent)) {
          const { formDescriptionEditor } = data.formDescriptionEditorEvent;
          return { formDescriptionEditor };
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
