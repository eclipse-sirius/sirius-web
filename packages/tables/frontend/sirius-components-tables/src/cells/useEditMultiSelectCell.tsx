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
import { useMutation } from '@apollo/client/react/hooks/useMutation';
import { useReporting } from '@eclipse-sirius/sirius-components-core';

import { gql } from '@apollo/client/core';
import {
  GQLEditMultiSelectCellMutationData,
  GQLEditMultiSelectCellMutationVariables,
  UseEditMultiSelectCellValue,
} from './useEditMultiSelectCell.types';

const editMultiSelectCellMutation = gql`
  mutation editMultiSelectCell($input: EditMultiSelectCellInput!) {
    editMultiSelectCell(input: $input) {
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

export const useEditMultiSelectCell = (
  editingContextId: string,
  representationId: string,
  tableId: string,
  cellId: string
): UseEditMultiSelectCellValue => {
  const [mutationEditMultiSelectCell, mutationEditMultiSelectCellResult] = useMutation<
    GQLEditMultiSelectCellMutationData,
    GQLEditMultiSelectCellMutationVariables
  >(editMultiSelectCellMutation);

  useReporting(
    mutationEditMultiSelectCellResult,
    (data: GQLEditMultiSelectCellMutationData) => data?.editMultiSelectCell
  );

  const editMultiSelectCell = (newValues: string[]) => {
    const variables: GQLEditMultiSelectCellMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: representationId,
        tableId: tableId,
        cellId,
        newValue: newValues,
      },
    };
    mutationEditMultiSelectCell({ variables });
  };

  return {
    editMultiSelectCell,
    loading: mutationEditMultiSelectCellResult.loading,
  };
};
