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

import { MockedResponse } from '@apollo/client/testing';
import { getRepresentationMetadataQuery } from '../../representationmetadata/useRepresentationMetadata';
import {
  GQLRepresentationMetadataQueryData,
  GQLRepresentationMetadataQueryVariables,
} from '../../representationmetadata/useRepresentationMetadata.types';

export const representationMetadataMock: MockedResponse<
  GQLRepresentationMetadataQueryData,
  GQLRepresentationMetadataQueryVariables
> = {
  request: {
    query: getRepresentationMetadataQuery,
  },
  maxUsageCount: Infinity,
  variableMatcher: () => true,
  result: ({ representationIds }) => {
    return {
      data: {
        viewer: {
          editingContext: {
            representations: {
              edges: representationIds.map((representationId) => ({
                node: {
                  id: representationId,
                  label: `Representation ${representationId}`,
                  kind: 'kind',
                  iconURLs: [],
                  description: {
                    id: 'representation-description-id',
                  },
                },
              })),
            },
          },
        },
      },
    };
  },
};
