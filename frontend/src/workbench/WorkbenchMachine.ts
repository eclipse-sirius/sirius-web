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
  selection: Selection | null;
  representations: Representation[];
  displayedRepresentation: Representation | null;
}

export type ShowRepresentationEvent = { type: 'SHOW_REPRESENTATION'; representation: Representation };
export type UpdateSelectionEvent = { type: 'UPDATE_SELECTION'; selection: Selection };
export type WorkbenchEvent = UpdateSelectionEvent | ShowRepresentationEvent;

export const workbenchMachine = Machine<WorkbenchContext, WorkbenchStateSchema, WorkbenchEvent>(
  {
    initial: 'initial',
    context: {
      selection: null,
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
        },
      },
    },
  },
  {
    actions: {
      updateSelection: assign((context, event) => {
        const { selection } = event as UpdateSelectionEvent;

        if (selection.kind === 'Diagram' || selection.kind === 'Form') {
          const { id, label, kind } = selection;
          const representation: Representation = { id, label, kind };

          let newRepresentations = [...context.representations];
          const selectedRepresentation = newRepresentations.find(
            (representation) => selection.id === representation.id
          );
          if (!selectedRepresentation) {
            newRepresentations = [...newRepresentations, representation];
          }

          return { selection, displayedRepresentation: representation, representations: newRepresentations };
        }

        return { selection };
      }),
      showRepresentation: assign((_, event) => {
        const { representation } = event as ShowRepresentationEvent;
        return { displayedRepresentation: representation };
      }),
    },
  }
);
