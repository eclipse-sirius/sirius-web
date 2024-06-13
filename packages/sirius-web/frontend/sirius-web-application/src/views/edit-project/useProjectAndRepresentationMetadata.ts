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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetProjectAndRepresentationMetadataQueryData,
  GQLGetProjectAndRepresentationMetadataQueryVariables,
  UseProjectAndRepresentationMetadataValue,
} from './useProjectAndRepresentationMetadata.types';

const getProjectQuery = gql`
  query getRepresentation($projectId: ID!, $representationId: ID!, $includeRepresentation: Boolean!) {
    viewer {
      project(projectId: $projectId) {
        id
        name
        natures {
          name
        }
        currentEditingContext {
          id
          representation(representationId: $representationId) @include(if: $includeRepresentation) {
            id
            label
            kind
          }
        }
      }
    }
  }
`;

export const useProjectAndRepresentationMetadata = (
  projectId: string,
  representationId: string | null
): UseProjectAndRepresentationMetadataValue => {
  const { loading, data, error } = useQuery<
    GQLGetProjectAndRepresentationMetadataQueryData,
    GQLGetProjectAndRepresentationMetadataQueryVariables
  >(getProjectQuery, {
    variables: {
      projectId,
      representationId: representationId ?? '',
      includeRepresentation: !!representationId,
    },
  });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    data: data ?? null,
    loading,
  };
};
