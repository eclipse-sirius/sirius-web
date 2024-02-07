/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
  GQLDeleteProjectPayload,
  GQLErrorPayload,
  UseDeleteProjectValue,
} from './useDeleteProject.types';

const deleteProjectMutation = gql`
  mutation deleteProject($input: DeleteProjectInput!) {
    deleteProject(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDeleteProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useDeleteProject = (): UseDeleteProjectValue => {
  const [performProjectDeletion, { loading, data, error }] = useMutation<
    GQLDeleteProjectMutationData,
    GQLDeleteProjectMutationVariables
  >(deleteProjectMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { deleteProject } = data;
      if (isErrorPayload(deleteProject)) {
        addMessages(deleteProject.messages);
      }
    }
  }, [data, error]);

  const deleteProject = (projectId: string) => {
    const variables: GQLDeleteProjectMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
      },
    };
    performProjectDeletion({ variables });
  };

  const projectDeleted: boolean = data?.deleteProject.__typename === 'DeleteProjectSuccessPayload';

  return {
    deleteProject,
    loading,
    projectDeleted,
  };
};
