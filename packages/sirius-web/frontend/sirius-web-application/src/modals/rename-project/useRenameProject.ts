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
  GQLErrorPayload,
  GQLRenameProjectMutationData,
  GQLRenameProjectMutationVariables,
  GQLRenameProjectPayload,
  UseRenameProjectValue,
} from './useRenameProject.types';

const renameProjectMutation = gql`
  mutation renameProject($input: RenameProjectInput!) {
    renameProject(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLRenameProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useRenameProject = (): UseRenameProjectValue => {
  const [performProjectRename, { loading, data, error }] = useMutation<
    GQLRenameProjectMutationData,
    GQLRenameProjectMutationVariables
  >(renameProjectMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { renameProject } = data;
      if (isErrorPayload(renameProject)) {
        addMessages(renameProject.messages);
      }
    }
  }, [data, error]);

  const renameProject = (projectId: string, newName: string) => {
    const variables: GQLRenameProjectMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
        newName,
      },
    };
    performProjectRename({ variables });
  };

  const projectRenamed: boolean = data?.renameProject.__typename === 'RenameProjectSuccessPayload';

  return {
    renameProject,
    loading,
    projectRenamed,
  };
};
