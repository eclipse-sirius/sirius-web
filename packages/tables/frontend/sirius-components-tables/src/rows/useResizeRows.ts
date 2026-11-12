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
import { gql, useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { MeasuredRowHeight } from '../table/measureTableContent';
import { GQLResizeRowsData, GQLResizeRowsInput, GQLResizeRowsVariables } from './useResizeRows.types';

const resizeRowsMutation = gql`
  mutation resizeTableRows($input: ResizeTableRowsInput!) {
    resizeTableRows(input: $input) {
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

export const useResizeRows = (editingContextId: string, representationId: string, tableId: string) => {
  const [mutationResizeRows, mutationResizeRowsResult] = useMutation<GQLResizeRowsData, GQLResizeRowsVariables>(
    resizeRowsMutation
  );
  useReporting(mutationResizeRowsResult, (data: GQLResizeRowsData) => data.resizeTableRows);

  const resizeRows = (rows: MeasuredRowHeight[]) => {
    if (rows.length > 0) {
      const input: GQLResizeRowsInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        tableId,
        rows,
      };
      mutationResizeRows({ variables: { input } });
    }
  };

  return { resizeRows };
};
