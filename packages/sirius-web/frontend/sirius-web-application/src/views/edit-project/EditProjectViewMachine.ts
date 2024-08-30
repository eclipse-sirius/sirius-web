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
import {
  GQLGetProjectAndRepresentationMetadataQueryData,
  GQLProject,
} from './useProjectAndRepresentationMetadata.types';

export interface EditProjectViewStateSchema {
  states: {
    loading: {};
    loaded: {};
    missing: {};
  };
}

export type SchemaValue = 'loading' | 'loaded' | 'missing';

export interface EditProjectViewContext {
  project: GQLProject | null;
  representation: RepresentationMetadata | null;
  message: string | null;
}

export type HandleFetchedProjectEvent = {
  type: 'HANDLE_FETCHED_PROJECT';
  data: GQLGetProjectAndRepresentationMetadataQueryData;
};
export type SelectRepresentationEvent = { type: 'SELECT_REPRESENTATION'; representation: RepresentationMetadata };
export type EditProjectViewEvent = HandleFetchedProjectEvent | SelectRepresentationEvent;

export const editProjectViewMachine = Machine<EditProjectViewContext, EditProjectViewStateSchema, EditProjectViewEvent>(
  {
    context: {
      project: null,
      representation: null,
      message: null,
    },
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
        const { project } = data.viewer;

        let representation: RepresentationMetadata | null = null;
        if (project.currentEditingContext.representation) {
          representation = {
            id: project.currentEditingContext.representation.id,
            label: project.currentEditingContext.representation.label,
            kind: project.currentEditingContext.representation.kind,
          };
        }

        return { project, representation };
      }),
      selectRepresentation: assign((_, event) => {
        const { representation } = event as SelectRepresentationEvent;
        return { representation };
      }),
    },
  }
);
