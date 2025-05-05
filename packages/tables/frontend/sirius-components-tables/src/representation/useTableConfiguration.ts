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

import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  UseTableConfigurationValue,
  GQLGetTableConfigurationData,
  GQLGetTableConfigurationVariables,
} from './useTableConfiguration.types';
import { ColumnFilter, ColumnSort } from '../table/TableContent.types';

const getTableConfigurationQuery = gql`
  query getTableConfiguration($editingContextId: ID!, $representationId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          configuration {
            globalFilter
            columnFilters {
              id
              value
            }
            columnSort {
              id
              desc
            }
            defaultPageSize
          }
        }
      }
    }
  }
`;

export const useTableConfiguration = (
  editingContextId: string,
  representationId: string | null
): UseTableConfigurationValue => {
  const { data, error } = useQuery<GQLGetTableConfigurationData, GQLGetTableConfigurationVariables>(
    getTableConfigurationQuery,
    {
      variables: {
        editingContextId,
        representationId: representationId ?? '',
        tableId: representationId ?? '',
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

  const globalFilter: string | null = data?.viewer.editingContext.representation.configuration.globalFilter ?? null;
  const columnFilters: ColumnFilter[] | null =
    data?.viewer.editingContext.representation.configuration.columnFilters ?? null;
  const columnSort: ColumnSort[] | null = data?.viewer.editingContext.representation.configuration.columnSort ?? null;
  const defaultPageSize: number | null =
    data?.viewer.editingContext.representation.configuration.defaultPageSize ?? null;

  return {
    globalFilter,
    columnFilters,
    columnSort,
    defaultPageSize,
  };
};
