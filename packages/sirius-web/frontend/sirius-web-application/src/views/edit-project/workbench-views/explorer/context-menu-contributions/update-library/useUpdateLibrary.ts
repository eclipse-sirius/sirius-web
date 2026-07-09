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
  GQLUpdateLibraryMutationData,
  GQLUpdateLibraryMutationVariables,
  GQLErrorPayload,
  GQLSuccessPayload,
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
        messages {
          level
          body
        }
      }
    }
  }
`;

const isSuccessPayload = (payload: GQLUpdateLibraryPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';
const isErrorPayload = (payload: GQLUpdateLibraryPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useUpdateLibrary = (): UseUpdateLibraryValue => {
  const [performUpdateLibrary, mutationResult] = useMutation<
    GQLUpdateLibraryMutationData,
    GQLUpdateLibraryMutationVariables
  >(updateLibraryMutation);
  const { loading, data } = mutationResult;
  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (mutationResult.error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      if (isSuccessPayload(data.updateLibrary)) {
        addMessages(data.updateLibrary.messages);
      }
      if (isErrorPayload(data.updateLibrary)) {
        addMessages(data.updateLibrary.messages);
      }
    }
  }, [data, mutationResult.error, addErrorMessage, addMessages]);

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
