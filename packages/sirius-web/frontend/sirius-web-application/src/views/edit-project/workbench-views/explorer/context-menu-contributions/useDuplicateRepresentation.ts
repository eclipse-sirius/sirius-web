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
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import {
  GQLDuplicateRepresentationData,
  GQLDuplicateRepresentationInput,
  GQLDuplicateRepresentationVariables,
  GQLRepresentationMetadata,
  UseDuplicateRepresentationValue,
} from './useDuplicateRepresentation.types';

const duplicateRepresentationMutation = gql`
  mutation duplicateRepresentation($input: DuplicateRepresentationInput!) {
    duplicateRepresentation(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on DuplicateRepresentationSuccessPayload {
        representationMetadata {
          id
        }
        messages {
          body
          level
        }
      }
    }
  }
`;

export const useDuplicateRepresentation = (): UseDuplicateRepresentationValue => {
  const [mutationDuplicateRepresentation, mutationDuplicateRepresentationResult] = useMutation<
    GQLDuplicateRepresentationData,
    GQLDuplicateRepresentationVariables
  >(duplicateRepresentationMutation);
  useReporting(mutationDuplicateRepresentationResult, (data: GQLDuplicateRepresentationData) => {
    const payload = data.duplicateRepresentation;
    if (payload.__typename === 'DuplicateRepresentationSuccessPayload') {
      return { __typename: 'SuccessPayload', id: payload.id, messages: payload.messages };
    } else {
      return { __typename: 'ErrorPayload', id: payload.id, messages: payload.messages };
    }
  });

  const duplicateRepresentation = (editingContextId: string, representationId: string) => {
    const input: GQLDuplicateRepresentationInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
    };
    mutationDuplicateRepresentation({ variables: { input } });
  };

  let duplicatedRepresentationMetadata: GQLRepresentationMetadata = null;
  if (
    mutationDuplicateRepresentationResult?.data?.duplicateRepresentation?.__typename ===
    'DuplicateRepresentationSuccessPayload'
  ) {
    duplicatedRepresentationMetadata =
      mutationDuplicateRepresentationResult.data.duplicateRepresentation.representationMetadata;
  }

  return { duplicateRepresentation, duplicatedRepresentationMetadata };
};
