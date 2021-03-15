/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
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
  GQLCreateProjectMutationData,
  GQLCreateProjectPayload,
  GQLCreateProjectSuccessPayload,
} from 'views/new-project/NewProjectView.types';
import { assign, Machine } from 'xstate';

export interface NewProjectViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    newProjectView: {
      states: {
        pristine: {};
        invalid: {};
        valid: {};
        creatingProject: {};
        success: {};
      };
    };
  };
}

export type SchemaValue = {
  newProjectView: 'pristine' | 'invalid' | 'valid' | 'creatingProject' | 'success';
  toast: 'visible' | 'hidden';
};

export interface NewProjectViewContext {
  name: string;
  nameMessage: string;
  nameIsInvalid: boolean;
  message: string | null;
  newProjectId: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type ChangeNameEvent = { type: 'CHANGE_NAME'; name: string };
export type HandleResponseEvent = { type: 'HANDLE_RESPONSE'; data: GQLCreateProjectMutationData };
export type RequestProjectCreationEvent = { type: 'REQUEST_PROJECT_CREATION' };
export type NewProjectEvent =
  | ChangeNameEvent
  | RequestProjectCreationEvent
  | HandleResponseEvent
  | ShowToastEvent
  | HideToastEvent;

const isChangeNameEvent = (event: NewProjectEvent): event is ChangeNameEvent => !!(event as ChangeNameEvent).name;
const isNameInvalid = (name: string) => name.trim().length < 3 || name.trim().length > 20;
const isCreateProjectSuccessPayload = (payload: GQLCreateProjectPayload): payload is GQLCreateProjectSuccessPayload =>
  payload.__typename === 'CreateProjectSuccessPayload';
export const newProjectViewMachine = Machine<NewProjectViewContext, NewProjectViewStateSchema, NewProjectEvent>(
  {
    id: 'NewProjectView',
    type: 'parallel',
    context: {
      name: '',
      nameMessage: 'The name must contain between 3 and 20 characters',
      nameIsInvalid: false,
      message: null,
      newProjectId: null,
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
      newProjectView: {
        initial: 'pristine',
        states: {
          pristine: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'isFormInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
            },
          },
          invalid: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'isFormInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
            },
          },
          valid: {
            on: {
              CHANGE_NAME: [
                {
                  cond: 'isFormInvalid',
                  target: 'invalid',
                  actions: 'updateName',
                },
                {
                  target: 'valid',
                  actions: 'updateName',
                },
              ],
              REQUEST_PROJECT_CREATION: [
                {
                  target: 'creatingProject',
                },
              ],
            },
          },
          creatingProject: {
            on: {
              HANDLE_RESPONSE: [
                {
                  cond: 'isResponseSuccessful',
                  target: 'success',
                  actions: 'updateProjectId',
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
      isFormInvalid: (_, event) => {
        if (isChangeNameEvent(event)) {
          const { name } = event;
          return isNameInvalid(name);
        }
        return true;
      },
      isResponseSuccessful: (_, event) => {
        const { data } = event as HandleResponseEvent;
        return data.createProject.__typename === 'CreateProjectSuccessPayload';
      },
    },
    actions: {
      updateName: assign((_, event) => {
        const { name } = event as ChangeNameEvent;
        return { name, nameIsInvalid: isNameInvalid(name) };
      }),
      updateProjectId: assign((_, event) => {
        const { data } = event as HandleResponseEvent;
        const { createProject } = data;
        if (isCreateProjectSuccessPayload(createProject)) {
          return { newProjectId: createProject.project.id };
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
