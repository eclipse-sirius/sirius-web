/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
  GQLCreateDocumentMutationData,
  GQLCreateDocumentPayload,
  GQLCreateDocumentSuccessPayload,
  GQLGetStereotypeDescriptionsQueryData,
  StereotypeDescription,
} from 'modals/new-document/NewDocumentModal.types';
import { assign, Machine } from 'xstate';

export interface NewDocumentModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    newDocumentModal: {
      states: {
        loading: {};
        invalid: {};
        valid: {};
        creatingDocument: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  newDocumentModal: 'loading' | 'invalid' | 'valid' | 'creatingDocument' | 'success';
  toast: 'visible' | 'hidden';
};

export interface NewDocumentModalContext {
  name: string;
  nameMessage: string;
  nameIsInvalid: boolean;
  selectedStereotypeDescriptionId: string;
  stereotypeDescriptions: StereotypeDescription[];
  message: string | null;
  newDocumentId: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedStereotypeDescriptionsEvent = {
  type: 'HANDLE_FETCHED_STEREOTYPE_DESCRIPTIONS';
  data: GQLGetStereotypeDescriptionsQueryData;
};
export type ChangeNameEvent = { type: 'CHANGE_NAME'; name: string };
export type ChangeStereotypeDescriptionEvent = {
  type: 'CHANGE_STEREOTYPE_DESCRIPTION';
  stereotypeDescriptionId: string;
};
export type CreateDocumentEvent = { type: 'CREATE_DOCUMENT' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLCreateDocumentMutationData };
export type NewDocumentModalEvent =
  | FetchedStereotypeDescriptionsEvent
  | ChangeNameEvent
  | ChangeStereotypeDescriptionEvent
  | CreateDocumentEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

const isSuccessPayload = (payload: GQLCreateDocumentPayload): payload is GQLCreateDocumentSuccessPayload =>
  payload.__typename === 'CreateDocumentSuccessPayload';

export const newDocumentModalMachine = Machine<
  NewDocumentModalContext,
  NewDocumentModalStateSchema,
  NewDocumentModalEvent
>(
  {
    id: 'NewDocumentModal',
    type: 'parallel',
    context: {
      name: '',
      nameMessage: 'The name cannot be empty',
      nameIsInvalid: false,
      selectedStereotypeDescriptionId: '',
      stereotypeDescriptions: [],
      message: null,
      newDocumentId: null,
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
      newDocumentModal: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_STEREOTYPE_DESCRIPTIONS: [
                {
                  target: 'invalid',
                  actions: 'updateStereotypeDescriptions',
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
              CHANGE_STEREOTYPE_DESCRIPTION: [
                {
                  actions: 'updateStereotypeDescription',
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
              CHANGE_STEREOTYPE_DESCRIPTION: [
                {
                  actions: 'updateStereotypeDescription',
                },
              ],
              CREATE_DOCUMENT: [
                {
                  target: 'creatingDocument',
                },
              ],
            },
          },
          creatingDocument: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  actions: 'recordNewDocumentId',
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
        return data.createDocument.__typename === 'CreateDocumentSuccessPayload';
      },
    },
    actions: {
      updateStereotypeDescriptions: assign((_, event) => {
        const { data } = event as FetchedStereotypeDescriptionsEvent;
        const { stereotypeDescriptions: stereotypeDescriptionsConnections } = data.viewer.editingContext;
        const selectedStereotypeDescriptionId =
          stereotypeDescriptionsConnections.edges.length > 0
            ? stereotypeDescriptionsConnections.edges[0].node.id
            : null;
        const stereotypeDescriptions = stereotypeDescriptionsConnections.edges.map((edge) => edge.node);
        return { stereotypeDescriptions, selectedStereotypeDescriptionId };
      }),
      updateName: assign((_, event) => {
        const { name } = event as ChangeNameEvent;
        return { name, nameIsInvalid: name.trim().length === 0 };
      }),
      updateStereotypeDescription: assign((_, event) => {
        const { stereotypeDescriptionId } = event as ChangeStereotypeDescriptionEvent;
        return { selectedStereotypeDescriptionId: stereotypeDescriptionId };
      }),
      recordNewDocumentId: assign((_, event) => {
        const { data } = event as HandleResponseEvent;
        if (isSuccessPayload(data.createDocument)) {
          return { newDocumentId: data.createDocument.documentId };
        }
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
