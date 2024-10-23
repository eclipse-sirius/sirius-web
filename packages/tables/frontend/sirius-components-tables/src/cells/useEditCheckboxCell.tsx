/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
  GQLEditCheckboxCellMutationData,
  GQLEditCheckboxCellMutationVariables,
  UseEditCheckboxCellValue,
} from './useEditCheckboxCell.types';

const editCheckboxCellMutation = gql`
  mutation editCheckboxCell($input: EditCheckboxCellInput!) {
    editCheckboxCell(input: $input) {
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

export const useEditCheckboxCell = (
  editingContextId: string,
  representationId: string,
  tableId: string,
  cellId: string
): UseEditCheckboxCellValue => {
  const [mutationEditCheckboxCell, mutationEditCheckboxCellResult] = useMutation<
    GQLEditCheckboxCellMutationData,
    GQLEditCheckboxCellMutationVariables
  >(editCheckboxCellMutation);

  useReporting(mutationEditCheckboxCellResult, (data: GQLEditCheckboxCellMutationData) => data?.editCheckboxCell);

  const editCheckboxCell = (newValue: boolean) => {
    const variables: GQLEditCheckboxCellMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        tableId,
        cellId,
        newValue,
      },
    };
    mutationEditCheckboxCell({ variables });
  };

  return {
    editCheckboxCell,
    loading: mutationEditCheckboxCellResult.loading,
  };
};
