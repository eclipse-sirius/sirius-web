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
  GQLRepresentationMetadataQueryData,
  GQLRepresentationMetadataQueryVariables,
  UseRepresentationMetadataProps,
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
              isViewBased
            }
          }
        }
      }
    }
  }
`;

export const useRepresentationMetadata = ({
  editingContextId,
  representationIds,
}: UseRepresentationMetadataProps): UseRepresentationMetadataValue => {
  const { addErrorMessage } = useMultiToast();
  const variables: GQLRepresentationMetadataQueryVariables = {
    editingContextId,
    representationIds,
  };
  const { data, error } = useQuery<GQLRepresentationMetadataQueryData, GQLRepresentationMetadataQueryVariables>(
    getRepresentationMetadata,
    { variables, skip: representationIds.length === 0 }
  );

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return { data: data ?? null };
};
