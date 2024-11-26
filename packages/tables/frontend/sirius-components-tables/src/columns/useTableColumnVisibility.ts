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
import { MRT_VisibilityState } from 'material-react-table';
import { useEffect, useState } from 'react';
import { GQLTable } from '../table/TableContent.types';
import {
  GQLChangeColumnVisibilityData,
  GQLChangeColumnVisibilityVariables,
  GQLColumnVisibility,
  GQLChangeColumnVisibilityInput,
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

export const useTableColumnVisibility = (
  editingContextId: string,
  representationId: string,
  table: GQLTable
): UseTableColumnVisibilityValue => {
  const [columnVisibility, setColumnVisibility] = useState<MRT_VisibilityState>(
    table.columns.reduce((acc, obj) => {
      acc[obj.id] = !obj.hidden;
      return acc;
    }, {})
  );

  useEffect(() => {
    changeColumnVisibility(
      Object.entries(columnVisibility).map(([columnId, visible]) => ({
        columnId,
        visible,
      }))
    );
  }, [columnVisibility]);

  useEffect(() => {
    setColumnVisibility(
      table.columns.reduce((acc, obj) => {
        acc[obj.id] = !obj.hidden;
        return acc;
      }, {})
    );
  }, [table.columns.map((column) => column.hidden).join()]);

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

  return {
    columnVisibility,
    setColumnVisibility,
  };
};
