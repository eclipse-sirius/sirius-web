/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { gql, useLazyQuery } from '@apollo/client';
import { useEffect } from 'react';
import { useMultiToast } from '../toast/MultiToast';
import {
  GQLRepresentationMetadataQueryData,
  GQLRepresentationMetadataQueryVariables,
  UseRepresentationMetadataValue,
} from './useRepresentationMetadata.types';

const getRepresentationMetadataQuery = gql`
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
              description {
                id
              }
            }
          }
        }
      }
    }
  }
`;

export const useRepresentationMetadata = (): UseRepresentationMetadataValue => {
  const { addErrorMessage } = useMultiToast();

  const [retrieveRepresentationMetadata, { data, error }] = useLazyQuery<
    GQLRepresentationMetadataQueryData,
    GQLRepresentationMetadataQueryVariables
  >(getRepresentationMetadataQuery);

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  const getRepresentationMetadata = (editingContextId: string, representationIds: string[]) => {
    if (representationIds.length > 0) {
      retrieveRepresentationMetadata({
        variables: {
          editingContextId,
          representationIds,
        },
      });
    }
  };

  return { data: data ?? null, getRepresentationMetadata };
};
