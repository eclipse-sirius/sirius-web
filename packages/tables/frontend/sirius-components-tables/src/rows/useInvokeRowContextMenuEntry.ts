/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
  GQLInvokeRowContextMenuEntryData,
  GQLInvokeRowContextMenuEntryInput,
  GQLInvokeRowContextMenuEntryVariables,
  UseInvokeRowContextMenuEntryMutationValue,
} from './useInvokeRowContextMenuEntry.types';

export const invokeRowContextMenuEntryMutation = gql`
  mutation invokeRowContextMenuEntry($input: InvokeRowContextMenuEntryInput!) {
    invokeRowContextMenuEntry(input: $input) {
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

export const useInvokeRowContextMenuEntry = (
  editingContextId: string,
  representationId: string,
  tableId: string,
  rowId: string
): UseInvokeRowContextMenuEntryMutationValue => {
  const [mutationInvokeRowContextMenuEntry, mutationInvokeRowContextMenuEntryResult] = useMutation<
    GQLInvokeRowContextMenuEntryData,
    GQLInvokeRowContextMenuEntryVariables
  >(invokeRowContextMenuEntryMutation);
  useReporting(
    mutationInvokeRowContextMenuEntryResult,
    (data: GQLInvokeRowContextMenuEntryData) => data.invokeRowContextMenuEntry
  );

  const invokeRowContextMenuEntry = (menuEntryId: string) => {
    const input: GQLInvokeRowContextMenuEntryInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId,
      rowId,
      menuEntryId,
    };

    mutationInvokeRowContextMenuEntry({ variables: { input } });
  };

  return {
    invokeRowContextMenuEntry,
  };
};
