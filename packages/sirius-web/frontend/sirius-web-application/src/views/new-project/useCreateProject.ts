/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLCreateProjectMutationData,
  GQLCreateProjectMutationInput,
  GQLCreateProjectMutationVariables,
  GQLCreateProjectPayload,
  GQLCreateProjectSuccessPayload,
  GQLErrorPayload,
  UseCreateProjectValue,
} from './useCreateProject.types';

const createProjectMutation = gql`
  mutation createProject($input: CreateProjectInput!) {
    createProject(input: $input) {
      __typename
      ... on CreateProjectSuccessPayload {
        project {
          id
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLCreateProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isCreateProjectSuccessPayload = (payload: GQLCreateProjectPayload): payload is GQLCreateProjectSuccessPayload =>
  payload.__typename === 'CreateProjectSuccessPayload';

export const useCreateProject = (): UseCreateProjectValue => {
  const [rawCreateProject, { data, loading, error }] = useMutation<
    GQLCreateProjectMutationData,
    GQLCreateProjectMutationVariables
  >(createProjectMutation);

  const createProject = (name: string, natures: string[], libraryIds: string[]) => {
    const input: GQLCreateProjectMutationInput = {
      id: crypto.randomUUID(),
      name,
      natures,
      libraryIds,
    };
    rawCreateProject({ variables: { input } });
  };

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { createProject } = data;
      if (isErrorPayload(createProject)) {
        addMessages(createProject.messages);
      }
    }
  }, [data, error]);

  return {
    createProject,
    loading: loading,
    newProjectId: data && isCreateProjectSuccessPayload(data.createProject) ? data.createProject.project.id : null,
  };
};
