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
import { RepresentationMetadata } from '@eclipse-sirius/sirius-components-core';
import { Machine, assign } from 'xstate';
import { GQLGetProjectQueryData, Project } from './EditProjectView.types';

export interface EditProjectViewStateSchema {
  states: {
    toast: {
      states: {
        visible: {};
        hidden: {};
      };
    };
    editProjectView: {
      states: {
        loading: {};
        loaded: {};
        missing: {};
      };
    };
  };
}

export type SchemaValue = {
  toast: 'visible' | 'hidden';
  editProjectView: 'loading' | 'loaded' | 'missing';
};

export interface EditProjectViewContext {
  project: Project | null;
  representation: RepresentationMetadata | null;
  message: string | null;
}

export type ShowToastEvent = { type: 'SHOW_TOAST'; message: string };
export type HideToastEvent = { type: 'HIDE_TOAST' };
export type HandleFetchedProjectEvent = { type: 'HANDLE_FETCHED_PROJECT'; data: GQLGetProjectQueryData };
export type SelectRepresentationEvent = { type: 'SELECT_REPRESENTATION'; representation: RepresentationMetadata };
export type EditProjectViewEvent =
  | HandleFetchedProjectEvent
  | SelectRepresentationEvent
  | ShowToastEvent
  | HideToastEvent;

export const editProjectViewMachine = Machine<EditProjectViewContext, EditProjectViewStateSchema, EditProjectViewEvent>(
  {
    type: 'parallel',
    context: {
      project: null,
      representation: null,
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
      editProjectView: {
        initial: 'loading',
        states: {
          loading: {
            on: {
              HANDLE_FETCHED_PROJECT: [
                {
                  cond: 'isMissing',
                  target: 'missing',
                },
                {
                  target: 'loaded',
                  actions: 'updateProject',
                },
              ],
            },
          },
          loaded: {
            type: 'final',
            on: {
              SELECT_REPRESENTATION: {
                target: 'loaded',
                actions: 'selectRepresentation',
              },
            },
          },
          missing: {
            type: 'final',
          },
        },
      },
    },
  },
  {
    guards: {
      isMissing: (_, event) => {
        const { data } = event as HandleFetchedProjectEvent;
        return !data.viewer.project;
      },
    },
    actions: {
      updateProject: assign((_, event) => {
        const { data } = event as HandleFetchedProjectEvent;
        const { project: gQLProject } = data.viewer;
        const { id, name, currentEditingContext } = gQLProject;
        const project = { id, name, currentEditingContext: { id: currentEditingContext.id } };

        let representation: RepresentationMetadata | null = null;
        if (gQLProject.currentEditingContext.representation) {
          representation = {
            id: gQLProject.currentEditingContext.representation.id,
            label: gQLProject.currentEditingContext.representation.label,
            kind: gQLProject.currentEditingContext.representation.kind,
          };
        }

        return { project, representation };
      }),
      selectRepresentation: assign((_, event) => {
        const { representation } = event as SelectRepresentationEvent;
        return { representation };
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
