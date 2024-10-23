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
import { gql } from '@apollo/client/core';
import { useMutation } from '@apollo/client/react/hooks/useMutation';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { GQLEditSelectCellMutationData, GQLEditSelectCellMutationVariables } from './useEditSelectCell.types';

const editSelectCellMutation = gql`
  mutation editSelectCell($input: EditSelectCellInput!) {
    editSelectCell(input: $input) {
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

export const useEditSelectCell = (
  editingContextId: string,
  representationId: string,
  tableId: string,
  cellId: string
) => {
  const [mutationEditSelectCell, mutationEditSelectCellResult] = useMutation<
    GQLEditSelectCellMutationData,
    GQLEditSelectCellMutationVariables
  >(editSelectCellMutation);

  useReporting(mutationEditSelectCellResult, (data: GQLEditSelectCellMutationData) => data?.editSelectCell);

  const editSelectCell = (newValue: string) => {
    const variables: GQLEditSelectCellMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        tableId,
        cellId,
        newValue,
      },
    };
    mutationEditSelectCell({ variables });
  };

  return {
    editSelectCell,
    loading: mutationEditSelectCellResult.loading,
  };
};
