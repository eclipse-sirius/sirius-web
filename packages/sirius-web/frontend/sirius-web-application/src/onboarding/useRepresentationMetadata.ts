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

import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetRepresentationMetadataData,
  GQLGetRepresentationMetadataVariables,
  UseRepresentationMetadataValue,
} from './useRepresentationMetadata.types';

const getRepresentationMetadataQuery = gql`
  query getRepresentationMetadata($editingContextId: ID!, $after: String, $before: String, $first: Int, $last: Int) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representations(after: $after, before: $before, first: $first, last: $last) {
          edges {
            node {
              id
              label
              kind
              iconURLs
            }
            cursor
          }
          pageInfo {
            count
            hasPreviousPage
            hasNextPage
            startCursor
            endCursor
          }
        }
      }
    }
  }
`;

export const useRepresentationMetadata = (
  editingContextId: string,
  startCursor: string | null,
  endCursor: string | null,
  pageSize: number
): UseRepresentationMetadataValue => {
  const variables: GQLGetRepresentationMetadataVariables = {
    editingContextId,
    after: startCursor,
    before: endCursor,
    first: startCursor ? pageSize : endCursor ? null : pageSize,
    last: endCursor ? pageSize : null,
  };
  const { data, loading, error } = useQuery<GQLGetRepresentationMetadataData, GQLGetRepresentationMetadataVariables>(
    getRepresentationMetadataQuery,
    { variables }
  );

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return { data: data ?? null, loading };
};
