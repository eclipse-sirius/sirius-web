/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

export interface TextfieldPropertySectionStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    textfieldPropertySection: {
      states: {
        pristine: {};
        edited: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  textfieldPropertySection: 'pristine' | 'edited';
};

export interface TextfieldPropertySectionContext {
  value: string;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type InitializeEvent = { type: 'INITIALIZE'; value: string };
export type ChangeValueEvent = { type: 'CHANGE_VALUE'; value: string };

export type TextfieldPropertySectionEvent = InitializeEvent | ChangeValueEvent | ShowToastEvent | HideToastEvent;

export const textfieldPropertySectionMachine = Machine<
  TextfieldPropertySectionContext,
  TextfieldPropertySectionStateSchema,
  TextfieldPropertySectionEvent
>(
  {
    type: 'parallel',
    context: {
      value: '',
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
      textfieldPropertySection: {
        initial: 'pristine',
        states: {
          pristine: {
            on: {
              INITIALIZE: {
                target: 'pristine',
                actions: 'updateValue',
              },
              CHANGE_VALUE: {
                target: 'edited',
                actions: 'updateValue',
              },
            },
          },
          edited: {
            on: {
              INITIALIZE: {
                target: 'pristine',
                actions: 'initializeValue',
              },
              CHANGE_VALUE: {
                target: 'edited',
                actions: 'updateValue',
              },
            },
          },
        },
      },
    },
  },
  {
    actions: {
      initializeValue: assign((context, event) => {
        const { value } = event as InitializeEvent;
        const { value: previousValue } = context;

        if (value !== previousValue) {
          // Similar issue as in EEFLifecycleManager, some update is coming from the server
          // while we have started to enter some content locally. We are choosing here to drop
          // the content entered locally but we will still log it.
          console.trace(`The following content "${previousValue}" has been overwritten by "${value}"`);
        }

        return { value };
      }),
      updateValue: assign((_, event) => {
        const { value } = event as ChangeValueEvent;
        return { value };
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
