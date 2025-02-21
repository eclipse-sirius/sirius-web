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

import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLExecuteOmniboxCommandData,
  GQLExecuteOmniboxCommandPayload,
  GQLExecuteOmniboxCommandVariables,
  GQLSuccessPayload,
  UseExecuteOmniboxCommandValue,
} from './useExecuteOmniboxCommand.types';

const executeOmniboxCommandMutation = gql`
  mutation executeOmniboxCommand($input: ExecuteOmniboxCommandInput!) {
    executeOmniboxCommand(input: $input) {
      __typename
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLExecuteOmniboxCommandPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (payload: GQLExecuteOmniboxCommandPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useExecuteOmniboxCommand = (): UseExecuteOmniboxCommandValue => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [performExecuteOmniboxCommand, { loading, data, error }] = useMutation<
    GQLExecuteOmniboxCommandData,
    GQLExecuteOmniboxCommandVariables
  >(executeOmniboxCommandMutation);

  useEffect(() => {
    if (data) {
      const { executeOmniboxCommand } = data;
      if (isErrorPayload(executeOmniboxCommand)) {
        addMessages(executeOmniboxCommand.messages);
      }
      if (isSuccessPayload(executeOmniboxCommand)) {
        addMessages(executeOmniboxCommand.messages);
      }
    }
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [data, error]);

  const executeOmniboxCommand = (editingContextId: string, selectedObjectIds: string[], commandId: string) => {
    const variables: GQLExecuteOmniboxCommandVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        selectedObjectIds,
        commandId,
      },
    };
    performExecuteOmniboxCommand({ variables });
  };

  return {
    executeOmniboxCommand,
    loading,
    data: data ?? null,
  };
};
