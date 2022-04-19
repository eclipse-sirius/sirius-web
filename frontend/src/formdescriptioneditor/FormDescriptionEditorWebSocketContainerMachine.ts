/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { Widget } from 'formdescriptioneditor/FormDescriptionEditorWebSocketContainer.types';
import { assign, Machine } from 'xstate';

export interface FormDescriptionEditorWebSocketContainerStateSchema {
  states: {
    formDescriptionEditorWebSocketContainer: {
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
  formDescriptionEditorWebSocketContainer: 'loading' | 'ready' | 'empty' | 'complete';
};

export interface FormDescriptionEditorWebSocketContainerContext {
  widgets: Widget[];
  message: string | null;
}

export type UpdateWidgetsEvent = { type: 'UPDATE_WIDGETS'; widgets: Widget[] };
export type CompleteEvent = { type: 'HANDLE_COMPLETE' };

export type InitializeRepresentationEvent = {
  type: 'INITIALIZE';
  widgets: Widget[];
};

export type FormDescriptionEditorWebSocketContainerEvent =
  | CompleteEvent
  | InitializeRepresentationEvent
  | UpdateWidgetsEvent;

export const formDescriptionEditorWebSocketContainerMachine = Machine<
  FormDescriptionEditorWebSocketContainerContext,
  FormDescriptionEditorWebSocketContainerStateSchema,
  FormDescriptionEditorWebSocketContainerEvent
>(
  {
    type: 'parallel',
    context: {
      widgets: [],
      message: null,
    },
    states: {
      formDescriptionEditorWebSocketContainer: {
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
              UPDATE_WIDGETS: [
                {
                  actions: 'updateWidgets',
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
      initialize: assign((_, event) => {
        const { widgets } = event as InitializeRepresentationEvent;
        return {
          widgets,
          message: undefined,
        };
      }),
      handleComplete: assign((_) => {
        return {
          widgets: [],
        };
      }),
      updateWidgets: assign((_, event) => {
        const { widgets } = event as UpdateWidgetsEvent;
        return { widgets: widgets };
      }),
    },
  }
);
