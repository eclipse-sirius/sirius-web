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
import { assign, Machine } from 'xstate';

export interface ModelersViewStateSchema {
  states: {
    loading: {};
    loaded: {};
    empty: {};
    error: {};
  };
}

export interface ModelersViewContext {
  modelers: any;
  message: string;
}

export type FetchedModelersEvent = { type: 'FETCHED_MODELERS'; modelers: any };
export type ErrorFetchingEvent = { type: 'ERROR_FETCHING'; message: string };
export type ModelersUpdatedEvent = { type: 'MODELERS_UPDATED'; modelers: any };
export type ModelersViewEvent = FetchedModelersEvent | ErrorFetchingEvent | ModelersUpdatedEvent;

export const modelersViewMachine = Machine<ModelersViewContext, ModelersViewStateSchema, ModelersViewEvent>(
  {
    initial: 'loading',
    context: {
      modelers: [],
      message: '',
    },
    states: {
      loading: {
        on: {
          FETCHED_MODELERS: [
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
          ERROR_FETCHING: [
            {
              target: 'error',
              actions: 'setMessage',
            },
          ],
        },
      },
      loaded: {
        on: {
          MODELERS_UPDATED: [
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

          ERROR_FETCHING: [
            {
              target: 'error',
              actions: 'setMessage',
            },
          ],
        },
      },
      empty: {},
      error: {},
    },
  },
  {
    guards: {
      isEmpty: (_, event) => {
        const { modelers } = event as FetchedModelersEvent;
        return modelers.length === 0;
      },
    },
    actions: {
      updateModelers: assign((_, event) => {
        const { modelers } = event as ModelersUpdatedEvent;
        return { modelers, message: '' };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ErrorFetchingEvent;
        return {
          message:
            'An unexpected error has occured while retrieving the modeler, please contact your administrator. ' +
            message,
        };
      }),
    },
  }
);
