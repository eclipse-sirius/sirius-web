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
import { CompletionRequest, GQLCompletionProposal } from './TextfieldPropertySection.types';

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
    completion: {
      states: {
        idle: {};
        requested: {};
        received: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  textfieldPropertySection: 'pristine' | 'edited';
  completion: 'idle' | 'requested' | 'received';
};

export interface TextfieldPropertySectionContext {
  value: string;
  completionRequest: CompletionRequest | null;
  proposals: GQLCompletionProposal[] | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type InitializeEvent = { type: 'INITIALIZE'; value: string };
export type ChangeValueEvent = { type: 'CHANGE_VALUE'; value: string };
export type RequestCompletionEvent = { type: 'COMPLETION_REQUESTED'; currentText: string; cursorPosition: number };
export type CompletionReceivedEvent = { type: 'COMPLETION_RECEIVED'; proposals: GQLCompletionProposal[] };
export type CompletionDismissedEvent = { type: 'COMPLETION_DISMISSED' };

export type TextfieldPropertySectionEvent =
  | InitializeEvent
  | ChangeValueEvent
  | ShowToastEvent
  | HideToastEvent
  | RequestCompletionEvent
  | CompletionReceivedEvent
  | CompletionDismissedEvent;

export const textfieldPropertySectionMachine = Machine<
  TextfieldPropertySectionContext,
  TextfieldPropertySectionStateSchema,
  TextfieldPropertySectionEvent
>(
  {
    type: 'parallel',
    context: {
      value: '',
      completionRequest: null,
      proposals: null,
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
      completion: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              COMPLETION_REQUESTED: {
                target: 'requested',
                actions: 'setCompletionRequest',
              },
            },
          },
          requested: {
            on: {
              COMPLETION_RECEIVED: [
                {
                  cond: 'noProposals',
                  target: 'idle',
                },
                {
                  target: 'received',
                  actions: 'setReceivedProposals',
                },
              ],
            },
          },
          received: {
            on: {
              COMPLETION_DISMISSED: {
                target: 'idle',
                actions: 'resetCompletion',
              },
              COMPLETION_REQUESTED: {
                target: 'requested',
                actions: ['resetCompletion', 'setCompletionRequest'],
              },
            },
          },
        },
      },
    },
  },
  {
    guards: {
      noProposals: (_, event) => {
        const { proposals } = event as CompletionReceivedEvent;
        return proposals.length === 0;
      },
    },
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
      setCompletionRequest: assign((_, event) => {
        const { currentText, cursorPosition } = event as RequestCompletionEvent;
        return { completionRequest: { currentText, cursorPosition } };
      }),
      setReceivedProposals: assign((_, event) => {
        const { proposals } = event as CompletionReceivedEvent;
        return { proposals };
      }),
      resetCompletion: assign((_) => {
        return { completionRequest: null, proposals: null };
      }),
    },
  }
);
