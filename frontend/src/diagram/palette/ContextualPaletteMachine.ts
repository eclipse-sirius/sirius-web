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
  GQLGetToolSectionsData,
  GQLRepresentationDescription,
  GQLToolSection,
} from 'diagram/palette/ContextualPalette.types';
import { assign, Machine } from 'xstate';

export interface ContextualPaletteStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    contextualPalette: {
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
  contextualPalette: 'loading' | 'loaded' | 'error';
};

export interface ContextualPaletteContext {
  toolSections: GQLToolSection[];
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleToolSectionsResultEvent = { type: 'HANDLE_TOOL_SECTIONS_RESULT'; result: GQLGetToolSectionsData };
export type ContextualPaletteEvent = ShowToastEvent | HideToastEvent | HandleToolSectionsResultEvent;

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const contextualPaletteMachine = Machine<
  ContextualPaletteContext,
  ContextualPaletteStateSchema,
  ContextualPaletteEvent
>(
  {
    type: 'parallel',
    context: {
      toolSections: [],
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
      contextualPalette: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_TOOL_SECTIONS_RESULT: [
                { cond: 'isValidResult', target: 'loaded', actions: 'setToolSections' },
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
        const { result } = event as HandleToolSectionsResultEvent;
        const description = result?.viewer?.editingContext?.representation?.description;
        return description && isDiagramDescription(description);
      },
    },
    actions: {
      setToolSections: assign((_, event) => {
        const { result } = event as HandleToolSectionsResultEvent;
        const description = result.viewer.editingContext.representation.description;
        const toolSections = isDiagramDescription(description) ? description.toolSections : [];
        return { toolSections };
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
