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
  UseToolMenuEntriesValue,
  GQLGetAllToolMenuEntriesData,
  GQLGetAllToolMenuEntriesVariables,
  GQLTableToolMenuEntry,
} from './useToolMenuEntries.types';

const getAllToolMenuEntriesQuery = gql`
  query getAllToolMenuEntries($editingContextId: ID!, $representationId: ID!, $tableId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TableDescription {
              toolMenuEntries(tableId: $tableId) {
                ... on ToolMenuEntry {
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
  }
`;

export const useToolMenuEntries = (
  editingContextId: string,
  representationId: string,
  tableId: string
): UseToolMenuEntriesValue => {
  const { loading, data, error } = useQuery<GQLGetAllToolMenuEntriesData, GQLGetAllToolMenuEntriesVariables>(
    getAllToolMenuEntriesQuery,
    {
      variables: {
        editingContextId,
        representationId: representationId,
        tableId: tableId,
      },
    }
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const toolMenuEntries: GQLTableToolMenuEntry[] =
    data?.viewer.editingContext.representation?.description.toolMenuEntries || [];

  return {
    toolMenuEntries,
    loading,
  };
};
