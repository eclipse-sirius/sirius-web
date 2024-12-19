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
import {
  GQLResetRowsHeightData,
  GQLResetRowsHeightInput,
  GQLResetRowsHeightVariables,
  UseResetRowsMutationValue,
} from './useResetRows.types';

export const resetRowsHeightMutation = gql`
  mutation resetTableRowsHeight($input: ResetTableRowsHeightInput!) {
    resetTableRowsHeight(input: $input) {
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

export const useResetRowsMutation = (
  editingContextId: string,
  representationId: string,
  tableId: string,
  enableRowSizing: boolean
): UseResetRowsMutationValue => {
  const [mutationResetRowsHeight, mutationResetRowsHeightResult] = useMutation<
    GQLResetRowsHeightData,
    GQLResetRowsHeightVariables
  >(resetRowsHeightMutation);
  useReporting(mutationResetRowsHeightResult, (data: GQLResetRowsHeightData) => data.resetTableRowsHeight);

  const resetRowsHeight = () => {
    if (enableRowSizing) {
      const input: GQLResetRowsHeightInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        tableId,
      };

      mutationResetRowsHeight({ variables: { input } });
    }
  };

  return {
    resetRowsHeight,
  };
};
