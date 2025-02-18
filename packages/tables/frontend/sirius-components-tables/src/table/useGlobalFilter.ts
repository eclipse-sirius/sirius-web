/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
import { useEffect, useMemo } from 'react';
import { GQLTable } from './TableContent.types';
import {
  GQLChangeGlobalFilterValueData,
  GQLChangeGlobalFilterValueInput,
  GQLChangeGlobalFilterValueVariables,
  UseGlobalFilterValue,
} from './useGlobalFilter.types';

export const changeGlobalFilterValueMutation = gql`
  mutation changeGlobalFilterValue($input: ChangeGlobalFilterValueInput!) {
    changeGlobalFilterValue(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const getGlobalFilter = (table: GQLTable): string => {
  return table.globalFilter ?? '';
};

export const useGlobalFilter = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  onGlobalFilterChange: (globalFilter: string) => void,
  enableGlobalFilter: boolean
): UseGlobalFilterValue => {
  const [mutationChangeGlobalFilterValue, mutationChangeGlobalFilterValueResult] = useMutation<
    GQLChangeGlobalFilterValueData,
    GQLChangeGlobalFilterValueVariables
  >(changeGlobalFilterValueMutation);
  useReporting(
    mutationChangeGlobalFilterValueResult,
    (data: GQLChangeGlobalFilterValueData) => data.changeGlobalFilterValue
  );

  const changeGlobalFilterValue = (globalFilterValue: string) => {
    const input: GQLChangeGlobalFilterValueInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId: table.id,
      globalFilterValue,
    };

    mutationChangeGlobalFilterValue({ variables: { input } });
  };

  const setGlobalFilter = (globalFilter: string | ((prevState: string) => string)) => {
    let newGlobalFilter: string;
    if (typeof globalFilter === 'function') {
      newGlobalFilter = globalFilter(getGlobalFilter(table)) ?? '';
    } else {
      newGlobalFilter = globalFilter ?? '';
    }
    onGlobalFilterChange(newGlobalFilter);
    changeGlobalFilterValue(newGlobalFilter);
  };

  const globalFilter = useMemo(() => getGlobalFilter(table), [table]);

  useEffect(() => {
    onGlobalFilterChange(globalFilter);
  }, [globalFilter]);

  if (!enableGlobalFilter) {
    return {
      globalFilter: undefined,
      setGlobalFilter: undefined,
    };
  }

  return {
    globalFilter,
    setGlobalFilter,
  };
};
