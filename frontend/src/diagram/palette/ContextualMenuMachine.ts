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
import {
  GQLDiagramDescription,
  GQLGetConnectorToolsData,
  GQLRepresentationDescription,
  GQLTool,
} from 'diagram/palette/ContextualMenu.types';
import { assign, Machine } from 'xstate';

export interface ContextualMenuStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    contextualMenu: {
      states: {
        loading: {};
        loaded: {};
        error: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  contextualMenu: 'loading' | 'loaded' | 'error';
};

export interface ContextualMenuContext {
  connectorTools: GQLTool[];
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleConnectorToolsResultEvent = {
  type: 'HANDLE_CONNECTOR_TOOLS_RESULT';
  result: GQLGetConnectorToolsData;
};
export type ContextualMenuEvent = ShowToastEvent | HideToastEvent | HandleConnectorToolsResultEvent;

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const contextualMenuMachine = Machine<ContextualMenuContext, ContextualMenuStateSchema, ContextualMenuEvent>(
  {
    type: 'parallel',
    context: {
      connectorTools: [],
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
      contextualMenu: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_CONNECTOR_TOOLS_RESULT: [
                { cond: 'isValidResult', target: 'loaded', actions: 'setConnectorTools' },
                { target: 'error' },
              ],
            },
          },
          loaded: {
            type: 'final',
          },
          error: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isValidResult: (_, event) => {
        const { result } = event as HandleConnectorToolsResultEvent;
        const description = result?.viewer?.editingContext?.representation?.description;
        return description && isDiagramDescription(description);
      },
    },
    actions: {
      setConnectorTools: assign((_, event) => {
        const { result } = event as HandleConnectorToolsResultEvent;
        const description = result.viewer.editingContext.representation.description;
        const connectorTools = isDiagramDescription(description) ? description.connectorTools : [];
        return { connectorTools };
      }),
      setMessage: assign((_, event) => {
        const { message } = event as ShowToastEvent;
        return { message };
      }),
      clearMessage: assign((_, event) => {
        return { message: null };
      }),
    },
  }
);
