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
  ChildCreationDescription,
  GQLCreateChildMutationData,
  GQLCreateChildPayload,
  GQLCreateChildSuccessPayload,
  GQLGetChildCreationDescriptionsQueryData,
} from 'modals/new-object/NewObjectModal.types';
import { SelectionEntry } from 'workbench/Workbench.types';
import { assign, Machine } from 'xstate';

export interface NewObjectModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    newObjectModal: {
      states: {
        loading: {};
        valid: {};
        creatingChild: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  newObjectModal: 'loading' | 'valid' | 'creatingChild' | 'success';
  toast: 'visible' | 'hidden';
};

export interface NewObjectModalContext {
  selectedChildCreationDescriptionId: string;
  childCreationDescriptions: ChildCreationDescription[];
  objectToSelect: SelectionEntry | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedChildCreationDescriptionsEvent = {
  type: 'HANDLE_FETCHED_CHILD_CREATION_DESCRIPTIONS';
  data: GQLGetChildCreationDescriptionsQueryData;
};
export type ChangeChildCreationDescriptionEvent = {
  type: 'CHANGE_CHILD_CREATION_DESCRIPTION';
  childCreationDescriptionId: string;
};
export type CreateChildEvent = { type: 'CREATE_CHILD' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLCreateChildMutationData };
export type NewObjectModalEvent =
  | FetchedChildCreationDescriptionsEvent
  | ChangeChildCreationDescriptionEvent
  | CreateChildEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

const isCreateChildSuccessPayload = (payload: GQLCreateChildPayload): payload is GQLCreateChildSuccessPayload => {
  return payload.__typename === 'CreateChildSuccessPayload';
};
export const newObjectModalMachine = Machine<NewObjectModalContext, NewObjectModalStateSchema, NewObjectModalEvent>(
  {
    id: 'NewObjectModal',
    type: 'parallel',
    context: {
      selectedChildCreationDescriptionId: '',
      childCreationDescriptions: [],
      objectToSelect: null,
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
      newObjectModal: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_CHILD_CREATION_DESCRIPTIONS: [
                {
                  target: 'valid',
                  actions: 'updateChildCreationDescriptions',
                },
              ],
            },
          },
          valid: {
            on: {
              CHANGE_CHILD_CREATION_DESCRIPTION: [
                {
                  actions: 'updateChildCreationDescription',
                },
              ],
              CREATE_CHILD: [
                {
                  target: 'creatingChild',
                },
              ],
            },
          },
          creatingChild: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                  actions: 'updateObjectToSelect',
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
      isResponseSuccessful: (_, event) => {
        const { data } = event as HandleResponseEvent;
        return data.createChild.__typename === 'CreateChildSuccessPayload';
      },
    },
    actions: {
      updateChildCreationDescriptions: assign((_, event) => {
        const { data } = event as FetchedChildCreationDescriptionsEvent;
        const { childCreationDescriptions } = data.viewer.editingContext;
        const selectedChildCreationDescriptionId =
          childCreationDescriptions.length > 0 ? childCreationDescriptions[0].id : '';
        return { childCreationDescriptions, selectedChildCreationDescriptionId };
      }),
      updateChildCreationDescription: assign((_, event) => {
        const { childCreationDescriptionId } = event as ChangeChildCreationDescriptionEvent;
        return { selectedChildCreationDescriptionId: childCreationDescriptionId };
      }),
      updateObjectToSelect: assign((_, event) => {
        const { data } = event as HandleResponseEvent;
        if (isCreateChildSuccessPayload(data.createChild)) {
          const { object } = data.createChild;
          return { objectToSelect: object };
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
