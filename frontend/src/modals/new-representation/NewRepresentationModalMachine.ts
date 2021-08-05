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
import {
  GQLCreateRepesentationSuccessPayload,
  GQLCreateRepresentationMutationData,
  GQLGetRepresentationDescriptionsQueryData,
  GQLRepresentationDescriptionNode,
} from 'modals/new-representation/NewRepresentationModal.types';
import { assign, Machine } from 'xstate';

export interface NewRepresentationModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    newRepresentationModal: {
      states: {
        loading: {};
        invalid: {};
        valid: {};
        creatingRepresentation: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  newRepresentationModal: 'loading' | 'invalid' | 'valid' | 'creatingRepresentation' | 'success';
  toast: 'visible' | 'hidden';
};

export interface NewRepresentationModalContext {
  name: string;
  nameMessage: string;
  nameIsInvalid: boolean;
  nameHasBeenModified: boolean;
  selectedRepresentationDescriptionId: string;
  representationDescriptions: GQLRepresentationDescriptionNode[];
  message: string | null;
  createdRepresentationId: string | null;
  createdRepresentationLabel: string | null;
  createdRepresentationKind: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedRepresentationDescriptionsEvent = {
  type: 'HANDLE_FETCHED_REPRESENTATION_DESCRIPTIONS';
  data: GQLGetRepresentationDescriptionsQueryData;
};
export type ChangeNameEvent = { type: 'CHANGE_NAME'; name: string };
export type ChangeRepresentationDescriptionEvent = {
  type: 'CHANGE_REPRESENTATION_DESCRIPTION';
  representationDescriptionId: string;
};

export type CreateRepresentationEvent = { type: 'CREATE_REPRESENTATION' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLCreateRepresentationMutationData };
export type NewRepresentationModalEvent =
  | FetchedRepresentationDescriptionsEvent
  | ChangeNameEvent
  | ChangeRepresentationDescriptionEvent
  | CreateRepresentationEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

export const newRepresentationModalMachine = Machine<
  NewRepresentationModalContext,
  NewRepresentationModalStateSchema,
  NewRepresentationModalEvent
>(
  {
    id: 'NewRepresentationsModal',
    type: 'parallel',
    context: {
      name: '',
      nameMessage: 'The name cannot be empty',
      nameIsInvalid: false,
      nameHasBeenModified: false,
      selectedRepresentationDescriptionId: '',
      representationDescriptions: [],
      message: null,
      createdRepresentationId: null,
      createdRepresentationLabel: null,
      createdRepresentationKind: null,
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
      newRepresentationModal: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_REPRESENTATION_DESCRIPTIONS: [
                {
                  target: 'valid',
                  actions: 'updateRepresentationDescriptions',
                },
              ],
            },
          },
          invalid: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'isNameInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
              CHANGE_REPRESENTATION_DESCRIPTION: [
                {
                  actions: 'updateRepresentationDescription',
                },
              ],
            },
          },
          valid: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'isNameInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
              CHANGE_REPRESENTATION_DESCRIPTION: [
                {
                  actions: 'updateRepresentationDescription',
                },
              ],
              CREATE_REPRESENTATION: [
                {
                  target: 'creatingRepresentation',
                },
              ],
            },
          },
          creatingRepresentation: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  actions: 'updateCreatedRepresentation',
                  target: 'success',
                },
                {
                  target: 'valid',
                },
              ],
            },
          },
          success: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isNameInvalid: (_, event) => {
        const { name } = event as ChangeNameEvent;
        return name.trim().length === 0;
      },
      isResponseSuccessful: (_, event) => {
        const { data } = event as HandleResponseEvent;
        return data.createRepresentation.__typename === 'CreateRepresentationSuccessPayload';
      },
    },
    actions: {
      updateRepresentationDescriptions: assign((_, event) => {
        const { data } = event as FetchedRepresentationDescriptionsEvent;
        const representationDescriptions = new Array<GQLRepresentationDescriptionNode>();
        data.viewer.editingContext.representationDescriptions.edges.forEach((edge) =>
          representationDescriptions.push(edge.node)
        );
        const selectedRepresentationDescriptionId =
          representationDescriptions.length > 0 ? representationDescriptions[0].id : '';
        const name = representationDescriptions.length > 0 ? representationDescriptions[0].label : '';
        return {
          representationDescriptions,
          selectedRepresentationDescriptionId,
          name,
          nameIsInvalid: name.trim().length === 0,
        };
      }),
      updateName: assign((_, event) => {
        const { name } = event as ChangeNameEvent;
        return { name, nameIsInvalid: name.trim().length === 0, nameHasBeenModified: true };
      }),
      updateRepresentationDescription: assign((context, event) => {
        const { representationDescriptionId } = event as ChangeRepresentationDescriptionEvent;
        if (!context.nameHasBeenModified) {
          const name = context.representationDescriptions.filter(
            (representationDescription) => representationDescription.id === representationDescriptionId
          )[0].label;

          return { selectedRepresentationDescriptionId: representationDescriptionId, name };
        }
        return { selectedRepresentationDescriptionId: representationDescriptionId };
      }),
      updateCreatedRepresentation: assign((_, event) => {
        const { data } = event as HandleResponseEvent;
        const createRepresentation: GQLCreateRepesentationSuccessPayload = <GQLCreateRepesentationSuccessPayload>(
          data.createRepresentation
        );
        return {
          createdRepresentationId: createRepresentation.representation.id,
          createdRepresentationLabel: createRepresentation.representation.label,
          createdRepresentationKind: createRepresentation.representation.kind,
        };
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
