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
import { gql, useQuery } from '@apollo/client';
import { useEffect } from 'react';
import { Selection } from '../selection/SelectionContext.types';
import { useMultiToast } from '../toast/MultiToast';
import {
  GQLRepresentationMetadataQueryData,
  GQLRepresentationMetadataQueryVariables,
  UseRepresentationMetadataValue,
} from './useRepresentationMetadata.types';

const getRepresentationMetadata = gql`
  query getRepresentationMetadata($editingContextId: ID!, $representationIds: [ID!]!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representations(representationIds: $representationIds) {
          edges {
            node {
              id
              label
              kind
              iconURLs
            }
          }
        }
      }
    }
  }
`;

export const useRepresentationMetadata = (
  editingContextId: string,
  selection: Selection
): UseRepresentationMetadataValue => {
  const { addErrorMessage } = useMultiToast();
  const representationIds: string[] = selection.entries.map((entry) => entry.id);
  const variables: GQLRepresentationMetadataQueryVariables = {
    editingContextId,
    representationIds,
  };
  const { data, error } = useQuery<GQLRepresentationMetadataQueryData, GQLRepresentationMetadataQueryVariables>(
    getRepresentationMetadata,
    { variables }
  );

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return { data: data ?? null };
};
