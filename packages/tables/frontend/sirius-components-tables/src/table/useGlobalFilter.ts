/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import { useEffect, useState } from 'react';
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
export const useGlobalFilter = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  onGlobalFilterChange: (globalFilter: string) => void,
  enableGlobalFilter: boolean
): UseGlobalFilterValue => {
  const [globalFilter, setGlobalFilter] = useState<string>(table.globalFilter ?? '');

  useEffect(() => {
    if (enableGlobalFilter) {
      changeGlobalFilterValue(globalFilter ?? '');
      onGlobalFilterChange(globalFilter ?? '');
    }
  }, [globalFilter]);

  useEffect(() => {
    setGlobalFilter(table.globalFilter ?? '');
  }, [table.globalFilter]);

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
