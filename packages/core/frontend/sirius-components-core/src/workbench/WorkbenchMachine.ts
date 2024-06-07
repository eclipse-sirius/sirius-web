/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { SubscriptionResult } from '@apollo/client';
import { assign, Machine } from 'xstate';
import {
  GQLEditingContextEventPayload,
  GQLEditingContextEventSubscription,
  GQLRepresentationRenamedEventPayload,
  RepresentationMetadata,
} from './Workbench.types';

export interface WorkbenchStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    workbench: {
      states: {
        initial: {};
        complete: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  workbench: 'initial' | 'complete';
};

export interface WorkbenchContext {
  id: string;
  representations: RepresentationMetadata[];
  displayedRepresentation: RepresentationMetadata | null;
  message: string | null;
}

export type HideRepresentationEvent = { type: 'HIDE_REPRESENTATION'; representation: RepresentationMetadata };
export type UpdateSelectedRepresentationEvent = {
  type: 'UPDATE_SELECTED_REPRESENTATION';
  representations: RepresentationMetadata[];
};

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleSubscriptionResultEvent = {
  type: 'HANDLE_SUBSCRIPTION_RESULT';
  result: SubscriptionResult<GQLEditingContextEventSubscription>;
};
export type HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
export type WorkbenchEvent =
  | UpdateSelectedRepresentationEvent
  | HideRepresentationEvent
  | HandleSubscriptionResultEvent
  | HandleCompleteEvent
  | ShowToastEvent
  | HideToastEvent;

const isRepresentationRenamedEventPayload = (
  payload: GQLEditingContextEventPayload
): payload is GQLRepresentationRenamedEventPayload => payload.__typename === 'RepresentationRenamedEventPayload';

export const workbenchMachine = Machine<WorkbenchContext, WorkbenchStateSchema, WorkbenchEvent>(
  {
    type: 'parallel',
    context: {
      id: crypto.randomUUID(),
      representations: [],
      displayedRepresentation: null,
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
      workbench: {
        initial: 'initial',
        states: {
          initial: {
            on: {
              UPDATE_SELECTED_REPRESENTATION: {
                target: 'initial',
                actions: 'updateSelectedRepresentation',
              },
              HIDE_REPRESENTATION: {
                target: 'initial',
                actions: 'hideRepresentation',
              },
              HANDLE_SUBSCRIPTION_RESULT: {
                target: 'initial',
                actions: 'handleSubscriptionResult',
              },
              HANDLE_COMPLETE: {
                target: 'complete',
              },
            },
          },
          complete: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    actions: {
      updateSelectedRepresentation: assign((context, event) => {
        const { representations: selectedRepresentations } = event as UpdateSelectedRepresentationEvent;
        if (selectedRepresentations.length > 0) {
          const displayedRepresentation = selectedRepresentations[0];

          const representations = [...context.representations];
          const newRepresentations = selectedRepresentations.filter(
            (selectedRepresentation) =>
              !representations.find((representation) => selectedRepresentation.id === representation.id)
          );

          const newSelectedRepresentations = [...representations, ...newRepresentations];

          return { displayedRepresentation, representations: newSelectedRepresentations };
        }
        return {};
      }),
      hideRepresentation: assign((context, event) => {
        const { representation: representationToHide } = event as HideRepresentationEvent;

        const previousIndex = context.representations.findIndex(
          (representation) =>
            context.displayedRepresentation && representation.id === context.displayedRepresentation.id
        );
        const newRepresentations = context.representations.filter(
          (representation) => representation.id !== representationToHide.id
        );

        if (newRepresentations.length === 0) {
          // There are no representations anymore
          return { displayedRepresentation: null, representations: [] };
        } else {
          const newIndex = newRepresentations.findIndex(
            (representation) =>
              context.displayedRepresentation && representation.id === context.displayedRepresentation.id
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
      handleSubscriptionResult: assign((context, event) => {
        const { result } = event as HandleSubscriptionResultEvent;
        const { data } = result;
        if (data && isRepresentationRenamedEventPayload(data.editingContextEvent)) {
          const { representationId, newLabel } = data.editingContextEvent;
          const representations = context.representations;

          for (var i = 0; i < representations.length; i++) {
            const representation = representations[i];
            if (!!representation && representation.id === representationId) {
              representation.label = newLabel;
            }
          }

          return {
            representations,
          };
        }
        return {};
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
