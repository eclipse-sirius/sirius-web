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
import { gql, useLazyQuery } from '@apollo/client';
import { useMultiToast } from '../toast/MultiToast';
import { RepresentationMetadata } from '../workbench/Workbench.types';
import {
  GQLRepresentationMetadata,
  GQLRepresentationMetadataQueryData,
  GQLRepresentationMetadataQueryVariables,
  UseRepresentationMetadataValue,
} from './useRepresentationMetadata.types';

const getRepresentationMetadata = gql`
  query getRepresentationMetadata($editingContextId: ID!, $representationId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          id
          label
          kind
        }
      }
    }
  }
`;

const notNullNorUndefined = (
  metadata: GQLRepresentationMetadata | null | undefined
): metadata is GQLRepresentationMetadata => !!metadata;

export const useRepresentationMetadata = (): UseRepresentationMetadataValue => {
  const { addErrorMessage } = useMultiToast();
  const [fetch] = useLazyQuery<GQLRepresentationMetadataQueryData, GQLRepresentationMetadataQueryVariables>(
    getRepresentationMetadata
  );

  const representationMetadata = (
    editingContextId: string,
    representationIds: string[],
    onRetrieveRepresentationMetadataSuccess: (representationMetadata: RepresentationMetadata[]) => void
  ): void => {
    Promise.all(
      representationIds.map((representationId) => {
        const variables: GQLRepresentationMetadataQueryVariables = {
          editingContextId,
          representationId,
        };
        return fetch({ variables });
      })
    ).then(
      (result) => {
        const representationMetadata: RepresentationMetadata[] = result
          .map((queryResult) => queryResult.data?.viewer.editingContext.representation)
          .filter(notNullNorUndefined);
        onRetrieveRepresentationMetadataSuccess(representationMetadata);
      },
      () => {
        addErrorMessage('An error occurred while fetching representation metadata, please refresh.');
      }
    );
  };

  return { representationMetadata };
};
