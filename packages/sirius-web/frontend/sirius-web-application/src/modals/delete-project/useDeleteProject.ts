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
import { useTranslation } from 'react-i18next';
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

  const { addErrorMessage } = useMultiToast();
  const { t } = useTranslation('siriusWebApplication');
  useEffect(() => {
    if (error) {
      addErrorMessage(t('errors.unexpected'));
    }
    if (data) {
      const { deleteProject } = data;
      if (isErrorPayload(deleteProject)) {
        addErrorMessage(deleteProject.message);
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

  const projectDeleted: boolean = data?.deleteProject.__typename === 'SuccessPayload';

  return {
    deleteProject,
    loading,
    projectDeleted,
  };
};
