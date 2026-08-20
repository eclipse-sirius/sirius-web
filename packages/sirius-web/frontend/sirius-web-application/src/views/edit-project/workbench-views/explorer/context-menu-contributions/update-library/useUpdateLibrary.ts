/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import {
  GQLUpdateLibraryMutationData,
  GQLUpdateLibraryMutationVariables,
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

export const useUpdateLibrary = (): UseUpdateLibraryValue => {
  const [performUpdateLibrary, result] = useMutation<GQLUpdateLibraryMutationData, GQLUpdateLibraryMutationVariables>(
    updateLibraryMutation
  );
  useReporting(result, (payload) => payload.updateLibrary);

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

  const { loading, data } = result;
  return {
    updateLibrary,
    loading,
    data: data ?? null,
  };
};
