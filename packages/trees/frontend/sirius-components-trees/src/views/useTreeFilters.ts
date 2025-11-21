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

import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetAllTreeFiltersData,
  GQLGetAllTreeFiltersVariables,
  GQLRepresentationDescription,
  GQLTreeDescription,
  GQLTreeFilter,
  UseTreeFilterValue,
} from './useTreeFilters.types';

const getAllTreeFiltersQuery = gql`
  query getAllTreeFilters($editingContextId: ID!, $representationDescriptionId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representationDescription(representationDescriptionId: $representationDescriptionId) {
          ... on TreeDescription {
            filters {
              id
              label
              defaultState
            }
          }
        }
      }
    }
  }
`;

const isTreeDescription = (
  representationDescription: GQLRepresentationDescription | null | undefined
): representationDescription is GQLTreeDescription => {
  return representationDescription?.__typename === 'TreeDescription';
};

export const useTreeFilters = (
  editingContextId: string,
  representationDescriptionId: string | null
): UseTreeFilterValue => {
  const { loading, data, error } = useQuery<GQLGetAllTreeFiltersData, GQLGetAllTreeFiltersVariables>(
    getAllTreeFiltersQuery,
    {
      variables: {
        editingContextId,
        representationDescriptionId,
      },
      skip: representationDescriptionId === null,
    }
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const representationDescription: GQLRepresentationDescription | null | undefined =
    data?.viewer.editingContext.representationDescription;
  const treeFilters: GQLTreeFilter[] = isTreeDescription(representationDescription)
    ? representationDescription.filters
    : [];

  return {
    treeFilters,
    loading,
  };
};
