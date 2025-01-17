/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
  GQLEditTextareaCellMutationData,
  GQLEditTextareaCellMutationVariables,
  UseEditTextareaCellValue,
} from './useEditTextareaCell.types';

import { gql } from '@apollo/client/core';

const editTextareaCellMutation = gql`
  mutation editTextareaCell($input: EditTextareaCellInput!) {
    editTextareaCell(input: $input) {
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

export const useEditTextareaCell = (
  editingContextId: string,
  representationId: string,
  tableId: string,
  cellId: string
): UseEditTextareaCellValue => {
  const [mutationEditTextareaCell, mutationEditTextareaCellResult] = useMutation<
    GQLEditTextareaCellMutationData,
    GQLEditTextareaCellMutationVariables
  >(editTextareaCellMutation);

  useReporting(mutationEditTextareaCellResult, (data: GQLEditTextareaCellMutationData) => data?.editTextareaCell);

  const editTextareaCell = (newValue: string) => {
    const variables: GQLEditTextareaCellMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        tableId,
        cellId,
        newValue,
      },
    };
    mutationEditTextareaCell({ variables });
  };

  return {
    editTextareaCell,
    loading: mutationEditTextareaCellResult.loading,
  };
};
