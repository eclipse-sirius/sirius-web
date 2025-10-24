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
  GQLDuplicateProjectMutationData,
  GQLDuplicateProjectMutationVariables,
  GQLDuplicateProjectPayload,
  GQLDuplicateSuccessPayload,
  GQLErrorPayload,
  UseDuplicateProjectValue,
} from './useDuplicateProject.types';

const duplicateProjectMutation = gql`
  mutation duplicateProject($input: DuplicateProjectInput!) {
    duplicateProject(input: $input) {
      __typename
      ... on DuplicateProjectSuccessPayload {
        project {
          id
        }
      }
    }
  }
`;

const isDuplicateSuccessPayload = (payload: GQLDuplicateProjectPayload): payload is GQLDuplicateSuccessPayload => {
  return typeof payload === 'object' && payload !== null && payload.__typename === 'DuplicateProjectSuccessPayload';
};

const isErrorPayload = (payload: GQLDuplicateProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useDuplicateProject = (): UseDuplicateProjectValue => {
  const [performProjectDuplication, { loading, data, error }] = useMutation<
    GQLDuplicateProjectMutationData,
    GQLDuplicateProjectMutationVariables
  >(duplicateProjectMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { duplicateProject } = data;
      if (isErrorPayload(duplicateProject)) {
        addMessages(duplicateProject.messages);
      }
    }
  }, [data, error]);

  const duplicateProject = (projectId: string) => {
    const variables: GQLDuplicateProjectMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
      },
    };
    performProjectDuplication({ variables });
  };

  const newProjectId: string | null = isDuplicateSuccessPayload(data?.duplicateProject)
    ? data.duplicateProject.project.id
    : null;

  return {
    duplicateProject,
    loading,
    newProjectId,
  };
};
