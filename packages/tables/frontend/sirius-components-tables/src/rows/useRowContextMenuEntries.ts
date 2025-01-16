/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetAllRowContextMenuEntriesData,
  GQLGetAllRowContextMenuEntriesVariables,
  UseRowContextMenuEntriesValue,
} from './useRowContextMenuEntries.types';

const getRowContextMenuEntriesQuery = gql`
  query getAllRowContextMenuEntries($editingContextId: ID!, $representationId: ID!, $tableId: ID!, $rowId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TableDescription {
              contextMenuEntries(tableId: $tableId, rowId: $rowId) {
                __typename
                id
                label
                iconURLs
              }
            }
          }
        }
      }
    }
  }
`;

export const useRowContextMenuEntries = (): UseRowContextMenuEntriesValue => {
  const [getRowContextMenuEntries, { loading, data, error }] = useLazyQuery<
    GQLGetAllRowContextMenuEntriesData,
    GQLGetAllRowContextMenuEntriesVariables
  >(getRowContextMenuEntriesQuery);

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  return {
    getRowContextMenuEntries,
    loading,
    contextMenuEntriesData: data ?? null,
  };
};
