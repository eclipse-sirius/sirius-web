/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
  GQLGetTreeItemTooltipData,
  GQLGetTreeItemTooltipVariables,
  UseTreeItemTooltipValue,
} from './useTreeItemTooltip.types';

const getTreeItemTooltipQuery = gql`
  query getTreeItemTooltip($editingContextId: ID!, $representationId: ID!, $treeItemId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TreeDescription {
              treeItemTooltip(treeItemId: $treeItemId)
            }
          }
        }
      }
    }
  }
`;

export const useTreeItemTooltip = (
  editingContextId: string,
  treeId: string,
  treeItemId: string,
  skip: boolean
): UseTreeItemTooltipValue => {
  const { loading, data, error } = useQuery<GQLGetTreeItemTooltipData, GQLGetTreeItemTooltipVariables>(
    getTreeItemTooltipQuery,
    {
      variables: {
        editingContextId,
        representationId: treeId,
        treeItemId,
      },
      skip,
    }
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const tooltip: string = data?.viewer.editingContext.representation?.description.treeItemTooltip || '';

  return {
    tooltip,
    loading,
  };
};
