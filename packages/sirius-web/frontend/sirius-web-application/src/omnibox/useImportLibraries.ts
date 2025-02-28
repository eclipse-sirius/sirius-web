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
  GQLErrorPayload,
  GQLImportLibrariesMutationData,
  GQLImportLibrariesMutationVariables,
  GQLImportLibrariesPayload,
  GQLSuccessPayload,
  UseImportLibrariesValue,
} from './useImportLibraries.types';

const importLibrariesMutation = gql`
  mutation importLibraries($input: ImportLibrariesInput!) {
    importLibraries(input: $input) {
      __typename
      ... on SuccessPayload {
        messages {
          level
          body
        }
      }
      ... on ErrorPayload {
        message
        messages {
          level
          body
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLImportLibrariesPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLImportLibrariesPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useImportLibraries = (): UseImportLibrariesValue => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [performImportLibraries, { loading, data, error }] = useMutation<
    GQLImportLibrariesMutationData,
    GQLImportLibrariesMutationVariables
  >(importLibrariesMutation);

  useEffect(() => {
    if (data) {
      const { importLibraries } = data;
      if (isErrorPayload(importLibraries)) {
        addMessages(importLibraries.messages);
      }
      if (isSuccessPayload(importLibraries)) {
        addMessages(importLibraries.messages);
      }
    }
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [error, data]);

  const importLibraries = (editingContextId: string, type: string, libraryIds: string[]) => {
    const variables: GQLImportLibrariesMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        type,
        libraryIds,
      },
    };
    performImportLibraries({ variables });
  };

  return {
    importLibraries,
    loading,
    data: data?.importLibraries ?? null,
  };
};
