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
import { SelectionEntry } from '@eclipse-sirius/sirius-components-core';
import {
  ChildCreationDescription,
  Domain,
  GQLCreateRootObjectMutationData,
  GQLCreateRootObjectPayload,
  GQLCreateRootObjectSuccessPayload,
  GQLGetDomainsQueryData,
  GQLGetRootObjectCreationDescriptionsQueryData,
} from 'modals/new-root-object/NewRootObjectModal.types';
import { Machine, assign } from 'xstate';

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
        loadingDomains: {};
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
    | 'loadingDomains'
    | 'loadingRootObjectCreationDescriptions'
    | 'valid'
    | 'creatingRootObject'
    | 'success';
  toast: 'visible' | 'hidden';
};

export interface NewRootObjectModalContext {
  domains: Domain[];
  selectedDomainId: string;
  rootObjectCreationDescriptions: ChildCreationDescription[];
  selectedRootObjectCreationDescriptionId: string;
  suggestedRootObject: boolean;
  objectToSelect: SelectionEntry | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type FetchedDomainsEvent = { type: 'HANDLE_FETCHED_DOMAINS'; data: GQLGetDomainsQueryData };
export type FetchedRootObjectCreationDescriptionsEvent = {
  type: 'HANDLE_FETCHED_ROOT_OBJECT_CREATION_DESCRIPTIONS';
  data: GQLGetRootObjectCreationDescriptionsQueryData;
};
export type ChangeDomainEvent = {
  type: 'CHANGE_DOMAIN';
  domainId: string;
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
  | FetchedDomainsEvent
  | FetchedRootObjectCreationDescriptionsEvent
  | ChangeDomainEvent
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
      domains: [],
      selectedDomainId: '',
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
        initial: 'loadingDomains',
        states: {
          loadingDomains: {
            on: {
              HANDLE_FETCHED_DOMAINS: [
                {
                  actions: 'updateDomains',
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
              CHANGE_DOMAIN: [
                {
                  actions: 'updateDomain',
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
      updateDomains: assign((_, event) => {
        const { data } = event as FetchedDomainsEvent;
        const { domains } = data.viewer.editingContext;
        const selectedDomainId = domains.length > 0 ? domains[0].id : '';
        return { domains, selectedDomainId };
      }),
      updateRootObjectCreationDescriptions: assign((_, event) => {
        const { data } = event as FetchedRootObjectCreationDescriptionsEvent;
        const { rootObjectCreationDescriptions } = data.viewer.editingContext;
        const selectedRootObjectCreationDescriptionId =
          rootObjectCreationDescriptions.length > 0 ? rootObjectCreationDescriptions[0].id : '';
        return { rootObjectCreationDescriptions, selectedRootObjectCreationDescriptionId };
      }),
      updateDomain: assign((_, event) => {
        const { domainId } = event as ChangeDomainEvent;
        return { selectedDomainId: domainId };
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
