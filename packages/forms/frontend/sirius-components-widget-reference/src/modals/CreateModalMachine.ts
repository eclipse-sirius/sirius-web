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
import { assign, Machine } from 'xstate';
import {
  ChildCreationDescription,
  Domain,
  GQLCreateElementMutationData,
  GQLCreateElementPayload,
  GQLCreateElementSuccessPayload,
  GQLGetChildCreationDescriptionsQueryData,
  GQLGetDomainsQueryData,
  GQLGetRootObjectCreationDescriptionsQueryData,
} from './CreateModal.types';

export interface CreateModalStateSchema {
  states: {
    createModal: {
      states: {
        selectContainmentMode: {};
        selectContainer: {};
        loadingChildCreationDescription: {};
        loadingDomains: {};
        loadingRootObjectCreationDescriptions: {};
        validForChild: {};
        validForRoot: {};
        creatingChild: {};
        creatingRoot: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  createModal:
    | 'selectContainmentMode'
    | 'selectContainer'
    | 'loadingDomains'
    | 'loadingChildCreationDescription'
    | 'loadingRootObjectCreationDescriptions'
    | 'validForChild'
    | 'validForRoot'
    | 'creatingChild'
    | 'creatingRoot'
    | 'success';
};

export interface CreateModalContext {
  domains: Domain[];
  selectedDomainId: string;
  selectedChildCreationDescriptionId: string;
  creationDescriptions: ChildCreationDescription[];
  newObjectId: string | null;
  containerSelected: Selection;
  containerId: string | null;
  containerKind: string | null;
}

export type FetchedDomainsEvent = { type: 'HANDLE_FETCHED_DOMAINS'; data: GQLGetDomainsQueryData };
export type FetchedChildCreationDescriptionsEvent = {
  type: 'HANDLE_FETCHED_CHILD_CREATION_DESCRIPTIONS';
  data: GQLGetChildCreationDescriptionsQueryData;
};
export type ChangeChildCreationDescriptionEvent = {
  type: 'CHANGE_CHILD_CREATION_DESCRIPTION';
  childCreationDescriptionId: string;
};
export type ChangeContainerSelectionEvent = {
  type: 'CHANGE_CONTAINER_SELECTION';
  container: Selection;
};
export type ChangeContainmentModeEvent = {
  type: 'CHANGE_CONTAINMENT_MODE';
  containment: boolean;
  containerId: string | null;
  containerKind: string | null;
};
export type FetchedRootObjectCreationDescriptionsEvent = {
  type: 'HANDLE_FETCHED_ROOT_OBJECT_CREATION_DESCRIPTIONS';
  data: GQLGetRootObjectCreationDescriptionsQueryData;
};
export type ChangeDomainEvent = {
  type: 'CHANGE_DOMAIN';
  domainId: string;
};

export type CreateChildEvent = { type: 'CREATE_CHILD' };
export type CreateRootEvent = { type: 'CREATE_ROOT' };
export type HandleCreateElementResponseEvent = {
  type: 'HANDLE_CREATE_ELEMENT_RESPONSE';
  data: GQLCreateElementMutationData;
};

export type CreateModalEvent =
  | FetchedDomainsEvent
  | FetchedRootObjectCreationDescriptionsEvent
  | FetchedChildCreationDescriptionsEvent
  | ChangeContainmentModeEvent
  | ChangeDomainEvent
  | ChangeChildCreationDescriptionEvent
  | CreateChildEvent
  | CreateRootEvent
  | HandleCreateElementResponseEvent
  | ChangeContainerSelectionEvent;

const isCreateElementSuccessPayload = (payload: GQLCreateElementPayload): payload is GQLCreateElementSuccessPayload => {
  return payload.__typename === 'CreateElementSuccessPayload';
};

export const createModalMachine = Machine<CreateModalContext, CreateModalStateSchema, CreateModalEvent>(
  {
    id: 'CreateModal',
    type: 'parallel',
    context: {
      domains: [],
      selectedDomainId: '',
      selectedChildCreationDescriptionId: '',
      creationDescriptions: [],
      newObjectId: null,
      containerSelected: { entries: [] },
      containerId: null,
      containerKind: null,
    },
    states: {
      createModal: {
        initial: 'selectContainmentMode',
        states: {
          selectContainmentMode: {
            on: {
              CHANGE_CONTAINMENT_MODE: [
                {
                  cond: 'isContainmentReference',
                  target: 'loadingChildCreationDescription',
                  actions: 'updateContainer',
                },
                {
                  target: 'selectContainer',
                },
              ],
            },
          },
          selectContainer: {
            on: {
              CHANGE_CONTAINER_SELECTION: [
                {
                  actions: 'updateContainerSelection',
                  cond: 'isRootContainer',
                  target: 'loadingDomains',
                },
                {
                  actions: 'updateContainerSelection',
                  target: 'loadingChildCreationDescription',
                },
              ],
            },
          },
          loadingChildCreationDescription: {
            on: {
              HANDLE_FETCHED_CHILD_CREATION_DESCRIPTIONS: [
                {
                  target: 'validForChild',
                  actions: 'updateChildCreationDescriptions',
                },
              ],
              CHANGE_CONTAINER_SELECTION: [
                {
                  actions: 'updateContainerSelection',
                  cond: 'isRootContainer',
                  target: 'loadingDomains',
                },
                {
                  actions: 'updateContainerSelection',
                  target: 'loadingChildCreationDescription',
                },
              ],
            },
          },
          loadingDomains: {
            on: {
              HANDLE_FETCHED_DOMAINS: [
                {
                  actions: 'updateDomains',
                  target: 'loadingRootObjectCreationDescriptions',
                },
              ],
              CHANGE_CONTAINER_SELECTION: [
                {
                  actions: 'updateContainerSelection',
                  cond: 'isRootContainer',
                  target: 'loadingDomains',
                },
                {
                  actions: 'updateContainerSelection',
                  target: 'loadingChildCreationDescription',
                },
              ],
            },
          },
          loadingRootObjectCreationDescriptions: {
            on: {
              CHANGE_DOMAIN: [
                {
                  actions: 'updateDomain',
                },
              ],
              HANDLE_FETCHED_ROOT_OBJECT_CREATION_DESCRIPTIONS: [
                {
                  target: 'validForRoot',
                  actions: 'updateRootChildCreationDescriptions',
                },
              ],
            },
          },
          validForChild: {
            on: {
              CHANGE_CONTAINER_SELECTION: [
                {
                  actions: 'updateContainerSelection',
                  cond: 'isRootContainer',
                  target: 'loadingDomains',
                },
                {
                  actions: 'updateContainerSelection',
                  target: 'loadingChildCreationDescription',
                },
              ],
              CHANGE_CHILD_CREATION_DESCRIPTION: [
                {
                  actions: 'updateChildCreationDescription',
                },
              ],
              CHANGE_DOMAIN: [
                {
                  actions: 'updateDomain',
                  target: 'loadingRootObjectCreationDescriptions',
                },
              ],
              CREATE_CHILD: [
                {
                  target: 'creatingChild',
                },
              ],
            },
          },
          validForRoot: {
            on: {
              CHANGE_CONTAINER_SELECTION: [
                {
                  actions: 'updateContainerSelection',
                  cond: 'isRootContainer',
                  target: 'loadingDomains',
                },
                {
                  actions: 'updateContainerSelection',
                  target: 'loadingChildCreationDescription',
                },
              ],
              CHANGE_CHILD_CREATION_DESCRIPTION: [
                {
                  actions: 'updateChildCreationDescription',
                },
              ],
              CHANGE_DOMAIN: [
                {
                  actions: 'updateDomain',
                  target: 'loadingRootObjectCreationDescriptions',
                },
              ],
              CREATE_ROOT: [
                {
                  target: 'creatingRoot',
                },
              ],
            },
          },
          creatingChild: {
            on: {
              HANDLE_CREATE_ELEMENT_RESPONSE: [
                {
                  cond: 'isResponseCreateElementSuccessful',
                  target: 'success',
                  actions: 'updateNewElementId',
                },
                {
                  target: 'validForChild',
                },
              ],
            },
          },
          creatingRoot: {
            on: {
              HANDLE_CREATE_ELEMENT_RESPONSE: [
                {
                  cond: 'isResponseCreateElementSuccessful',
                  target: 'success',
                  actions: 'updateNewElementId',
                },
                {
                  target: 'validForRoot',
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
      isResponseCreateElementSuccessful: (_, event) => {
        const { data } = event as HandleCreateElementResponseEvent;
        return data.createElement.__typename === 'CreateElementSuccessPayload';
      },
      isContainmentReference: (_, event) => {
        const { containment } = event as ChangeContainmentModeEvent;
        return containment;
      },
      isRootContainer: (_, event) => {
        const { container } = event as ChangeContainerSelectionEvent;
        return container.entries[0]?.kind === 'siriusWeb://document';
      },
    },
    actions: {
      updateDomains: assign((_, event) => {
        const { data } = event as FetchedDomainsEvent;
        const { domains } = data.viewer.editingContext;
        const selectedDomainId = domains.length > 0 ? domains[0].id : '';
        return { domains, selectedDomainId };
      }),
      updateDomain: assign((_, event) => {
        const { domainId } = event as ChangeDomainEvent;
        return { selectedDomainId: domainId };
      }),
      updateChildCreationDescriptions: assign((_, event) => {
        const { data } = event as FetchedChildCreationDescriptionsEvent;
        const { childCreationDescriptions } = data.viewer.editingContext;
        const selectedChildCreationDescriptionId =
          childCreationDescriptions.length > 0 ? childCreationDescriptions[0].id : '';
        return { creationDescriptions: childCreationDescriptions, selectedChildCreationDescriptionId };
      }),
      updateRootChildCreationDescriptions: assign((_, event) => {
        const { data } = event as FetchedRootObjectCreationDescriptionsEvent;
        const { rootObjectCreationDescriptions } = data.viewer.editingContext;
        const selectedChildCreationDescriptionId =
          rootObjectCreationDescriptions.length > 0 ? rootObjectCreationDescriptions[0].id : '';
        return { creationDescriptions: rootObjectCreationDescriptions, selectedChildCreationDescriptionId };
      }),
      updateChildCreationDescription: assign((_, event) => {
        const { childCreationDescriptionId } = event as ChangeChildCreationDescriptionEvent;
        return { selectedChildCreationDescriptionId: childCreationDescriptionId };
      }),
      updateContainer: assign((_, event) => {
        const { containerKind, containerId } = event as ChangeContainmentModeEvent;
        return {
          containerId: containerId,
          containerKind: containerKind,
        };
      }),
      updateContainerSelection: assign((_, event) => {
        const { container } = event as ChangeContainerSelectionEvent;
        return {
          containerSelected: container,
          containerId: container.entries[0]?.id,
          containerKind: container.entries[0]?.kind,
        };
      }),
      updateNewElementId: assign((_, event) => {
        const { data } = event as HandleCreateElementResponseEvent;
        if (isCreateElementSuccessPayload(data.createElement)) {
          const { object } = data.createElement;
          return { newObjectId: object.id };
        }
        return {};
      }),
    },
  }
);
