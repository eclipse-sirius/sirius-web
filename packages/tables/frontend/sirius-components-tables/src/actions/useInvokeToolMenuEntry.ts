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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLInvokeToolMenuEntryData,
  GQLInvokeToolMenuEntryInput,
  GQLInvokeToolMenuEntryPayload,
  GQLInvokeToolMenuEntryVariables,
  UseInvokeToolMenuEntryValue,
} from './useInvokeToolMenuEntry.types';

const invokeToolMenuEntryMutation = gql`
  mutation invokeToolMenuEntry($input: InvokeToolMenuEntryInput!) {
    invokeToolMenuEntry(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLInvokeToolMenuEntryPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useInvokeToolMenuEntry = (): UseInvokeToolMenuEntryValue => {
  const { addErrorMessage, addMessages } = useMultiToast();

  const [invokeToolMenuEntry, { data, loading, error }] = useMutation<
    GQLInvokeToolMenuEntryData,
    GQLInvokeToolMenuEntryVariables
  >(invokeToolMenuEntryMutation);

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
    if (data) {
      const { invokeToolMenuEntry } = data;
      if (isErrorPayload(invokeToolMenuEntry)) {
        addMessages(invokeToolMenuEntry.messages);
      }
    }
  }, [error, data]);

  const invokeTool = (editingContextId: string, representationId: string, tableId: string, menuEntryId: string) => {
    const input: GQLInvokeToolMenuEntryInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId,
      menuEntryId,
    };
    invokeToolMenuEntry({ variables: { input } });
  };

  return { invokeTool, loading };
};
