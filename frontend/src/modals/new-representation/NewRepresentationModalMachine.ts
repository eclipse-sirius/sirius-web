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
import {
  GQLCreateRepesentationSuccessPayload,
  GQLCreateRepresentationMutationData,
  GQLGetRepresentationCreationDescriptionsQueryData,
  GQLRepresentationCreationDescriptionNode,
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
  representationCreationDescriptions: GQLRepresentationCreationDescriptionNode[];
  message: string | null;
  createdRepresentationId: string | null;
  createdRepresentationLabel: string | null;
  createdRepresentationKind: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedRepresentationCreationDescriptionsEvent = {
  type: 'HANDLE_FETCHED_REPRESENTATION_CREATION_DESCRIPTIONS';
  data: GQLGetRepresentationCreationDescriptionsQueryData;
};
export type ChangeNameEvent = { type: 'CHANGE_NAME'; name: string };
export type ChangeRepresentationCreationDescriptionEvent = {
  type: 'CHANGE_REPRESENTATION_CREATION_DESCRIPTION';
  representationCreationDescriptionId: string;
};

export type CreateRepresentationEvent = { type: 'CREATE_REPRESENTATION' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLCreateRepresentationMutationData };
export type NewRepresentationModalEvent =
  | FetchedRepresentationCreationDescriptionsEvent
  | ChangeNameEvent
  | ChangeRepresentationCreationDescriptionEvent
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
      representationCreationDescriptions: [],
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
              HANDLE_FETCHED_REPRESENTATION_CREATION_DESCRIPTIONS: [
                {
                  target: 'valid',
                  actions: 'updateRepresentationCreationDescriptions',
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
              CHANGE_REPRESENTATION_CREATION_DESCRIPTION: [
                {
                  actions: 'updateRepresentationCreationDescription',
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
              CHANGE_REPRESENTATION_CREATION_DESCRIPTION: [
                {
                  actions: 'updateRepresentationCreationDescription',
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
      updateRepresentationCreationDescriptions: assign((_, event) => {
        const { data } = event as FetchedRepresentationCreationDescriptionsEvent;
        const representationCreationDescriptions = new Array<GQLRepresentationCreationDescriptionNode>();
        data.viewer.editingContext.representationCreationDescriptions.edges.forEach((edge) =>
          representationCreationDescriptions.push(edge.node)
        );
        const selectedRepresentationDescriptionId =
          representationCreationDescriptions.length > 0 ? representationCreationDescriptions[0].id : '';
        const name =
          representationCreationDescriptions.length > 0 ? representationCreationDescriptions[0].defaultName : '';
        return {
          representationCreationDescriptions,
          selectedRepresentationDescriptionId,
          name,
          nameIsInvalid: name.trim().length === 0,
        };
      }),
      updateName: assign((_, event) => {
        const { name } = event as ChangeNameEvent;
        return { name, nameIsInvalid: name.trim().length === 0, nameHasBeenModified: true };
      }),
      updateRepresentationCreationDescription: assign((context, event) => {
        const { representationCreationDescriptionId } = event as ChangeRepresentationCreationDescriptionEvent;
        if (!context.nameHasBeenModified) {
          const name = context.representationCreationDescriptions.filter(
            (representationDescription) => representationDescription.id === representationCreationDescriptionId
          )[0].defaultName;

          return { selectedRepresentationDescriptionId: representationCreationDescriptionId, name };
        }
        return { selectedRepresentationDescriptionId: representationCreationDescriptionId };
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
