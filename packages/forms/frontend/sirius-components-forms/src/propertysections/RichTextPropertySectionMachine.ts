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
import { assign, Machine } from 'xstate';

export interface RichTextPropertySectionStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
};

export interface RichTextPropertySectionContext {
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };

export type RichTextPropertySectionEvent = ShowToastEvent | HideToastEvent;

export const RichTextPropertySectionMachine = Machine<
  RichTextPropertySectionContext,
  RichTextPropertySectionStateSchema,
  RichTextPropertySectionEvent
>(
  {
    type: 'parallel',
    context: {
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
    },
  },
  {
    actions: {
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
