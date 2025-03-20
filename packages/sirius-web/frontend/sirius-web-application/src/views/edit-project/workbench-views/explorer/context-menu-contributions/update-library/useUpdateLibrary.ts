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
  GQLSuccessPayload,
  GQLUpdateLibraryMutationData,
  GQLUpdateLibraryMutationVariables,
  GQLUpdateLibraryPayload,
  UseUpdateLibraryValue,
} from './useUpdateLibrary.types';

const updateLibraryMutation = gql`
  mutation updateLibrary($input: UpdateLibraryInput!) {
    updateLibrary(input: $input) {
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

const isErrorPayload = (payload: GQLUpdateLibraryPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLUpdateLibraryPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useUpdateLibrary = (): UseUpdateLibraryValue => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [performUpdateLibrary, { loading, data, error }] = useMutation<
    GQLUpdateLibraryMutationData,
    GQLUpdateLibraryMutationVariables
  >(updateLibraryMutation);

  useEffect(() => {
    if (data) {
      const { updateLibrary } = data;
      if (isErrorPayload(updateLibrary)) {
        addMessages(updateLibrary.messages);
      }
      if (isSuccessPayload(updateLibrary)) {
        addMessages(updateLibrary.messages);
      }
    }
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [error, data]);

  const updateLibrary = (editingContextId: string, libraryId: string) => {
    const variables: GQLUpdateLibraryMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        libraryId,
      },
    };
    performUpdateLibrary({ variables });
  };

  return {
    updateLibrary,
    loading,
    data: data ?? null,
  };
};
