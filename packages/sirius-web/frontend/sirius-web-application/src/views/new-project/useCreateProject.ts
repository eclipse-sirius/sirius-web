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
  GQLErrorPayload,
  GQLCreateProjectPayload,
  GQLCreateProjectSuccessPayload,
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

const isCreateProjectSuccessPayload = (payload: GQLCreateProjectPayload): payload is GQLCreateProjectSuccessPayload =>
  payload.__typename === 'CreateProjectSuccessPayload';

const isErrorPayload = (payload: GQLCreateProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useCreateProject = (): UseCreateProjectValue => {
  const [rawCreateProject, mutationResult] = useMutation<
    GQLCreateProjectMutationData,
    GQLCreateProjectMutationVariables
  >(createProjectMutation);
  const { data, loading } = mutationResult;
  const { addErrorMessage, addMessages } = useMultiToast();

  const createProject = (name: string, templateId: string, libraryIds: string[]) => {
    const input: GQLCreateProjectMutationInput = {
      id: crypto.randomUUID(),
      name,
      templateId,
      libraryIds,
    };
    rawCreateProject({ variables: { input } });
  };

  useEffect(() => {
    if (mutationResult.error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data && isErrorPayload(data.createProject)) {
      addMessages(data.createProject.messages);
    }
  }, [data, mutationResult.error, addErrorMessage, addMessages]);

  return {
    createProject,
    loading: loading,
    newProjectId: data && isCreateProjectSuccessPayload(data.createProject) ? data.createProject.project.id : null,
  };
};
