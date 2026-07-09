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
  GQLDeleteProjectMutationData,
  GQLDeleteProjectMutationVariables,
  GQLErrorPayload,
  GQLDeleteProjectPayload,
  UseDeleteProjectValue,
} from './useDeleteProject.types';

const deleteProjectMutation = gql`
  mutation deleteProject($input: DeleteProjectInput!) {
    deleteProject(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDeleteProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useDeleteProject = (): UseDeleteProjectValue => {
  const [performProjectDeletion, mutationResult] = useMutation<
    GQLDeleteProjectMutationData,
    GQLDeleteProjectMutationVariables
  >(deleteProjectMutation);
  const { loading, data } = mutationResult;
  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (data && isErrorPayload(data.deleteProject)) {
      addMessages(data.deleteProject.messages);
    }
    if (mutationResult.error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [data, mutationResult.error, addErrorMessage, addMessages]);

  const deleteProject = (projectId: string) => {
    const variables: GQLDeleteProjectMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
      },
    };
    performProjectDeletion({ variables });
  };

  const projectDeleted: boolean = data?.deleteProject.__typename === 'SuccessPayload';

  return {
    deleteProject,
    loading,
    projectDeleted,
  };
};
