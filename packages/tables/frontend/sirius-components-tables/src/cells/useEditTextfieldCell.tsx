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
import {
  GQLEditTextfieldCellMutationData,
  GQLEditTextfieldCellMutationVariables,
  UseEditTextfieldCellValue,
} from './useEditTextfieldCell.types';

import { gql } from '@apollo/client/core';

const editTextfieldCellMutation = gql`
  mutation editTextfieldCell($input: EditTextfieldCellInput!) {
    editTextfieldCell(input: $input) {
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

export const useEditTextfieldCell = (
  editingContextId: string,
  representationId: string,
  tableId: string,
  cellId: string
): UseEditTextfieldCellValue => {
  const [mutationEditTextfieldCell, mutationEditTextfieldCellResult] = useMutation<
    GQLEditTextfieldCellMutationData,
    GQLEditTextfieldCellMutationVariables
  >(editTextfieldCellMutation);

  useReporting(mutationEditTextfieldCellResult, (data: GQLEditTextfieldCellMutationData) => data?.editTextfieldCell);

  const editTextfieldCell = (newValue: string) => {
    const variables: GQLEditTextfieldCellMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        tableId,
        cellId,
        newValue,
      },
    };
    mutationEditTextfieldCell({ variables });
  };

  return {
    editTextfieldCell,
    loading: mutationEditTextfieldCellResult.loading,
  };
};
