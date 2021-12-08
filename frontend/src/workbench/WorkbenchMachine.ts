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
import { Representation, Selection } from 'workbench/Workbench.types';
import { assign, Machine } from 'xstate';

export interface WorkbenchStateSchema {
  states: { initial: {} };
}

export type SchemaValue = 'initial';

export interface WorkbenchContext {
  selection: Selection;
  representations: Representation[];
  displayedRepresentation: Representation | null;
}

export type ShowRepresentationEvent = { type: 'SHOW_REPRESENTATION'; representation: Representation };
export type HideRepresentationEvent = { type: 'HIDE_REPRESENTATION'; representation: Representation };
export type UpdateSelectionEvent = {
  type: 'UPDATE_SELECTION';
  selection: Selection;
  representations: Representation[];
};
export type WorkbenchEvent = UpdateSelectionEvent | ShowRepresentationEvent | HideRepresentationEvent;

export const workbenchMachine = Machine<WorkbenchContext, WorkbenchStateSchema, WorkbenchEvent>(
  {
    initial: 'initial',
    context: {
      selection: { entries: [] },
      representations: [],
      displayedRepresentation: null,
    },
    states: {
      initial: {
        on: {
          UPDATE_SELECTION: {
            target: 'initial',
            actions: 'updateSelection',
          },
          SHOW_REPRESENTATION: {
            target: 'initial',
            actions: 'showRepresentation',
          },
          HIDE_REPRESENTATION: {
            target: 'initial',
            actions: 'hideRepresentation',
          },
        },
      },
    },
  },
  {
    actions: {
      updateSelection: assign((context, event) => {
        const { selection, representations: selectedRepresentations } = event as UpdateSelectionEvent;
        if (selectedRepresentations.length > 0) {
          const displayedRepresentation = selectedRepresentations[0];

          const representations = [...context.representations];
          const newRepresentations = selectedRepresentations.filter(
            (selectedRepresentation) =>
              !representations.find((representation) => selectedRepresentation.id === representation.id)
          );

          const newSelectedRepresentations = [...representations, ...newRepresentations];

          return { selection, displayedRepresentation, representations: newSelectedRepresentations };
        }
        return { selection };
      }),
      showRepresentation: assign((_, event) => {
        const { representation } = event as ShowRepresentationEvent;
        return { displayedRepresentation: representation };
      }),
      hideRepresentation: assign((context, event) => {
        const { representation: representationToHide } = event as HideRepresentationEvent;

        const previousIndex = context.representations.findIndex(
          (representation) => representation.id === context.displayedRepresentation.id
        );
        const newRepresentations = context.representations.filter(
          (representation) => representation.id !== representationToHide.id
        );

        if (newRepresentations.length === 0) {
          // There are no representations anymore
          return { displayedRepresentation: null, representations: [] };
        } else {
          const newIndex = newRepresentations.findIndex(
            (representation) => representation.id === context.displayedRepresentation.id
          );

          if (newIndex !== -1) {
            // The previously displayed representation has not been closed
            return { representations: newRepresentations };
          } else if (newRepresentations.length === previousIndex) {
            // The previous representation has been closed and it was the last one
            const displayedRepresentation = newRepresentations[previousIndex - 1];
            return { displayedRepresentation, representations: newRepresentations };
          } else {
            const displayedRepresentation = newRepresentations[previousIndex];
            return { displayedRepresentation, representations: newRepresentations };
          }
        }
      }),
    },
  }
);
