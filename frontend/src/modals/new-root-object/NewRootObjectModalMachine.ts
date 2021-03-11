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
  GQLCreateRootObjectMutationData,
  GQLCreateRootObjectPayload,
  GQLCreateRootObjectSuccessPayload,
  GQLGetNamespacesQueryData,
  GQLGetRootObjectCreationDescriptionsQueryData,
  Namespace,
} from 'modals/new-root-object/NewRootObjectModal.types';
import { Selection } from 'workbench/Workbench.types';
import { assign, Machine } from 'xstate';

export interface NewRootObjectModalStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    newRootObjectModal: {
      states: {
        loadingNamespaces: {};
        loadingRootObjectCreationDescriptions: {};
        valid: {};
        creatingRootObject: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  newRootObjectModal:
    | 'loadingNamespaces'
    | 'loadingRootObjectCreationDescriptions'
    | 'valid'
    | 'creatingRootObject'
    | 'success';
  toast: 'visible' | 'hidden';
};

export interface NewRootObjectModalContext {
  namespaces: Namespace[];
  selectedNamespaceId: string;
  rootObjectCreationDescriptions: ChildCreationDescription[];
  selectedRootObjectCreationDescriptionId: string;
  suggestedRootObject: boolean;
  objectToSelect: Selection | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedNamespacesEvent = { type: 'HANDLE_FETCHED_NAMESPACES'; data: GQLGetNamespacesQueryData };
export type FetchedRootObjectCreationDescriptionsEvent = {
  type: 'HANDLE_FETCHED_ROOT_OBJECT_CREATION_DESCRIPTIONS';
  data: GQLGetRootObjectCreationDescriptionsQueryData;
};
export type ChangeNamespaceEvent = {
  type: 'CHANGE_NAMESPACE';
  namespaceId: string;
};
export type ChangeRootObjectCreationDescriptionEvent = {
  type: 'CHANGE_ROOT_OBJECT_CREATION_DESCRIPTION';
  rootObjectCreationDescriptionId: string;
};
export type ChangeSuggestedEvent = {
  type: 'CHANGE_SUGGESTED';
  suggestedRootObject: boolean;
};
export type CreateRootObjectEvent = { type: 'CREATE_ROOT_OBJECT' };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLCreateRootObjectMutationData };
export type NewRootObjectModalEvent =
  | FetchedNamespacesEvent
  | FetchedRootObjectCreationDescriptionsEvent
  | ChangeNamespaceEvent
  | ChangeRootObjectCreationDescriptionEvent
  | ChangeSuggestedEvent
  | CreateRootObjectEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

const isCreateRootObjectSuccessPayload = (
  payload: GQLCreateRootObjectPayload
): payload is GQLCreateRootObjectSuccessPayload => {
  return payload.__typename === 'CreateRootObjectSuccessPayload';
};
export const newRootObjectModalMachine = Machine<
  NewRootObjectModalContext,
  NewRootObjectModalStateSchema,
  NewRootObjectModalEvent
>(
  {
    id: 'NewRootObjectModal',
    type: 'parallel',
    context: {
      namespaces: [],
      selectedNamespaceId: '',
      rootObjectCreationDescriptions: [],
      selectedRootObjectCreationDescriptionId: '',
      suggestedRootObject: true,
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
      newRootObjectModal: {
        initial: 'loadingNamespaces',
        states: {
          loadingNamespaces: {
            on: {
              HANDLE_FETCHED_NAMESPACES: [
                {
                  actions: 'updateNamespaces',
                  target: 'loadingRootObjectCreationDescriptions',
                },
              ],
            },
          },
          loadingRootObjectCreationDescriptions: {
            on: {
              HANDLE_FETCHED_ROOT_OBJECT_CREATION_DESCRIPTIONS: [
                {
                  actions: 'updateRootObjectCreationDescriptions',
                  target: 'valid',
                },
              ],
            },
          },
          valid: {
            on: {
              CHANGE_NAMESPACE: [
                {
                  actions: 'updateNamespace',
                  target: 'loadingRootObjectCreationDescriptions',
                },
              ],
              CHANGE_ROOT_OBJECT_CREATION_DESCRIPTION: [
                {
                  actions: 'updateRootObjectCreationDescription',
                },
              ],
              CHANGE_SUGGESTED: [
                {
                  actions: 'updateSuggested',
                  target: 'loadingRootObjectCreationDescriptions',
                },
              ],
              CREATE_ROOT_OBJECT: [
                {
                  target: 'creatingRootObject',
                },
              ],
            },
          },
          creatingRootObject: {
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
        return data.createRootObject.__typename === 'CreateRootObjectSuccessPayload';
      },
    },
    actions: {
      updateNamespaces: assign((_, event) => {
        const { data } = event as FetchedNamespacesEvent;
        const { namespaces } = data.viewer.editingContext;
        const selectedNamespaceId = namespaces.length > 0 ? namespaces[0].id : '';
        return { namespaces, selectedNamespaceId };
      }),
      updateRootObjectCreationDescriptions: assign((_, event) => {
        const { data } = event as FetchedRootObjectCreationDescriptionsEvent;
        const { rootObjectCreationDescriptions } = data.viewer.editingContext;
        const selectedRootObjectCreationDescriptionId =
          rootObjectCreationDescriptions.length > 0 ? rootObjectCreationDescriptions[0].id : '';
        return { rootObjectCreationDescriptions, selectedRootObjectCreationDescriptionId };
      }),
      updateNamespace: assign((_, event) => {
        const { namespaceId } = event as ChangeNamespaceEvent;
        return { selectedNamespaceId: namespaceId };
      }),
      updateRootObjectCreationDescription: assign((_, event) => {
        const { rootObjectCreationDescriptionId } = event as ChangeRootObjectCreationDescriptionEvent;
        return { selectedRootObjectCreationDescriptionId: rootObjectCreationDescriptionId };
      }),
      updateSuggested: assign((_, event) => {
        const { suggestedRootObject } = event as ChangeSuggestedEvent;
        return { suggestedRootObject };
      }),
      updateObjectToSelect: assign((_, event) => {
        const { data } = event as HandleResponseEvent;
        if (isCreateRootObjectSuccessPayload(data.createRootObject)) {
          const { object } = data.createRootObject;
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
