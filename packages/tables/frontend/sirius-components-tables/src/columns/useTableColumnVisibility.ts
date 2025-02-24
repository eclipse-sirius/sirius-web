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
import { MRT_VisibilityState } from 'material-react-table';
import { useMemo } from 'react';
import { GQLTable } from '../table/TableContent.types';
import {
  GQLChangeColumnVisibilityData,
  GQLChangeColumnVisibilityInput,
  GQLChangeColumnVisibilityVariables,
  GQLColumnVisibility,
  UseTableColumnVisibilityValue,
} from './useTableColumnVisibility.types';

export const changeColumnVisibilityMutation = gql`
  mutation changeTableColumnVisibility($input: ChangeTableColumnVisibilityInput!) {
    changeTableColumnVisibility(input: $input) {
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

const getColumnsVisibility = (table: GQLTable) => {
  return table.columns.reduce((acc, obj) => {
    acc[obj.id] = !obj.hidden;
    return acc;
  }, {});
};

const convertToGQLColumnVisibility = (record: Record<string, boolean>): GQLColumnVisibility[] => {
  return Object.keys(record).reduce((acc: GQLColumnVisibility[], columnId: string) => {
    acc.push({
      columnId: columnId,
      visible: record[columnId] ?? false,
    });
    return acc;
  }, []);
};

export const useTableColumnVisibility = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  enableColumnVisibility: boolean
): UseTableColumnVisibilityValue => {
  const [mutationChangeColumnVisibility, mutationChangeColumnVisibilityResult] = useMutation<
    GQLChangeColumnVisibilityData,
    GQLChangeColumnVisibilityVariables
  >(changeColumnVisibilityMutation);
  useReporting(
    mutationChangeColumnVisibilityResult,
    (data: GQLChangeColumnVisibilityData) => data.changeTableColumnVisibility
  );

  const changeColumnVisibility = (columnsVisibility: GQLColumnVisibility[]) => {
    const input: GQLChangeColumnVisibilityInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId: table.id,
      columnsVisibility,
    };

    mutationChangeColumnVisibility({ variables: { input } });
  };

  const setColumnVisibility = (
    columnVisibility: MRT_VisibilityState | ((prevState: MRT_VisibilityState) => MRT_VisibilityState)
  ) => {
    let newColumnVisibility: MRT_VisibilityState;
    if (typeof columnVisibility === 'function') {
      newColumnVisibility = columnVisibility(getColumnsVisibility(table));
    } else {
      newColumnVisibility = columnVisibility;
    }

    changeColumnVisibility(convertToGQLColumnVisibility(newColumnVisibility));
  };

  const columnVisibility = useMemo(() => getColumnsVisibility(table), [table]);

  if (!enableColumnVisibility) {
    return {
      columnVisibility: undefined,
      setColumnVisibility: undefined,
    };
  }

  return {
    columnVisibility,
    setColumnVisibility,
  };
};
