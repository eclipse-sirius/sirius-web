/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { Machine, assign } from 'xstate';
import { GQLGantt, Palette } from './GanttRepresentation.types';

export interface GanttRepresentationStateSchema {
  states: {
    ganttRepresentation: {
      states: {
        idle: {};
        ready: {};
        complete: {};
      };
    };
  };
}

export type SchemaValue = {
  ganttRepresentation: 'idle' | 'ready' | 'complete';
};

export interface GanttRepresentationContext {
  id: string;
  displayedGanttId: string | null;
  gantt: GQLGantt | null;
  contextualPalette: Palette;
  latestSelection: Selection;
  newSelection: Selection;
  selectedObjectId: string | null;
}

export type SwitchRepresentationEvent = { type: 'SWITCH_REPRESENTATION'; representationId: string };
export type GanttRefreshedEvent = { type: 'HANDLE_GANTT_REFRESHED'; gantt: GQLGantt };
export type SetContextualPaletteEvent = { type: 'SET_CONTEXTUAL_PALETTE'; contextualPalette: Palette | null };
export type SelectionEvent = { type: 'SELECTION'; selection: Selection };
export type SelectedElementEvent = { type: 'SELECTED_ELEMENT'; selection: Selection };
export type SelectZoomLevelEvent = { type: 'SELECT_ZOOM_LEVEL'; level: string };
export type CompleteEvent = { type: 'HANDLE_COMPLETE' };

export type GanttRepresentationEvent =
  | SwitchRepresentationEvent
  | GanttRefreshedEvent
  | SetContextualPaletteEvent
  | SelectionEvent
  | SelectedElementEvent
  | SelectZoomLevelEvent
  | CompleteEvent;

export const ganttRepresentationMachine = Machine<
  GanttRepresentationContext,
  GanttRepresentationStateSchema,
  GanttRepresentationEvent
>(
  {
    type: 'parallel',
    context: {
      id: crypto.randomUUID(),
      displayedGanttId: null,
      gantt: null,
      contextualPalette: null,
      latestSelection: { entries: [] },
      newSelection: { entries: [] },
      selectedObjectId: null,
    },
    states: {
      ganttRepresentation: {
        initial: 'idle',
        states: {
          idle: {
            on: {
              SWITCH_REPRESENTATION: [
                {
                  target: 'idle',
                  actions: 'switchRepresentation',
                },
              ],
              HANDLE_GANTT_REFRESHED: [
                {
                  target: 'ready',
                  actions: 'handleGanttRefreshed',
                },
              ],
            },
          },
          ready: {
            on: {
              SWITCH_REPRESENTATION: [
                {
                  target: 'idle',
                  actions: 'switchRepresentation',
                },
              ],
              HANDLE_GANTT_REFRESHED: [
                {
                  actions: 'handleGanttRefreshed',
                },
              ],
              SET_CONTEXTUAL_PALETTE: [
                {
                  actions: 'setContextualPalette',
                },
              ],
              SELECTION: [
                {
                  actions: 'setSelection',
                },
              ],
              SELECTED_ELEMENT: [
                {
                  actions: 'setSelectedElement',
                },
              ],
              SELECT_ZOOM_LEVEL: [
                {
                  actions: 'selectZoomLevel',
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
          complete: {
            on: {
              SWITCH_REPRESENTATION: [
                {
                  target: 'idle',
                  actions: 'switchRepresentation',
                },
              ],
            },
          },
        },
      },
    },
  },
  {
    actions: {
      switchRepresentation: assign((_, event) => {
        const { representationId } = event as SwitchRepresentationEvent;
        return {
          id: crypto.randomUUID(),
          displayedGanttId: representationId,
        };
      }),

      handleGanttRefreshed: assign((_, event) => {
        const { gantt } = event as GanttRefreshedEvent;
        return { gantt };
      }),

      setContextualPalette: assign((_, event) => {
        const { contextualPalette } = event as SetContextualPaletteEvent;
        return { contextualPalette };
      }),

      setSelection: assign((context, event) => {
        const { selection } = event as SelectionEvent;
        let newSelectionValue: Selection;
        if (
          context.latestSelection.entries.length === selection.entries.length &&
          context.latestSelection.entries.every(
            (entry) => selection.entries.find((e) => e.id === entry.id) !== undefined
          )
        ) {
          newSelectionValue = context.newSelection;
        } else {
          newSelectionValue = selection;
        }
        return {
          latestSelection: newSelectionValue,
          newSelection: newSelectionValue,
          zoomLevel: '1',
        };
      }),
      setSelectedElement: assign((_, event) => {
        const { selection } = event as SelectedElementEvent;
        return { latestSelection: selection };
      }),
      handleComplete: assign((_) => {
        return {
          gantt: undefined,
          ganttDescription: null,
          contextualPalette: undefined,
          latestSelection: { entries: [] },
          newSelection: { entries: [] },
          selectedObjectId: undefined,
        };
      }),
    },
  }
);
