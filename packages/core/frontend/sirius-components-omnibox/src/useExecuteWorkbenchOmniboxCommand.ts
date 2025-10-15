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
import { Selection, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLExecuteWorkbenchOmniboxCommandData,
  GQLExecuteWorkbenchOmniboxCommandPayload,
  GQLExecuteWorkbenchOmniboxCommandSuccessPayload,
  GQLExecuteWorkbenchOmniboxCommandVariables,
  UseExecuteWorkbenchOmniboxCommandValue,
} from './useExecuteWorkbenchOmniboxCommand.types';

const executeOmniboxCommandMutation = gql`
  mutation executeWorkbenchOmniboxCommand($input: ExecuteWorkbenchOmniboxCommandInput!) {
    executeWorkbenchOmniboxCommand(input: $input) {
      __typename
      ... on ExecuteWorkbenchOmniboxCommandSuccessPayload {
        newSelection {
          entries {
            id
            kind
          }
        }
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

const isErrorPayload = (payload: GQLExecuteWorkbenchOmniboxCommandPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const isExecuteWorkbenchOmniboxCommandSuccessPayload = (
  payload: GQLExecuteWorkbenchOmniboxCommandPayload
): payload is GQLExecuteWorkbenchOmniboxCommandSuccessPayload =>
  payload.__typename === 'ExecuteWorkbenchOmniboxCommandSuccessPayload';

export const useExecuteWorkbenchOmniboxCommand = (): UseExecuteWorkbenchOmniboxCommandValue => {
  const { setSelection } = useSelection();
  const { addErrorMessage, addMessages } = useMultiToast();

  const [performExecuteWorkbenchOmniboxCommand, { loading, data, error }] = useMutation<
    GQLExecuteWorkbenchOmniboxCommandData,
    GQLExecuteWorkbenchOmniboxCommandVariables
  >(executeOmniboxCommandMutation);

  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { executeWorkbenchOmniboxCommand } = data;
      if (isErrorPayload(executeWorkbenchOmniboxCommand)) {
        addMessages(executeWorkbenchOmniboxCommand.messages);
      }
      if (isExecuteWorkbenchOmniboxCommandSuccessPayload(executeWorkbenchOmniboxCommand)) {
        addMessages(executeWorkbenchOmniboxCommand.messages);
        if (
          isExecuteWorkbenchOmniboxCommandSuccessPayload(executeWorkbenchOmniboxCommand) &&
          executeWorkbenchOmniboxCommand.newSelection
        ) {
          const selection: Selection = {
            entries: executeWorkbenchOmniboxCommand.newSelection.entries.map((entry) => ({ id: entry.id })),
          };
          setSelection(selection);
        }
      }
    }
  }, [data, error]);

  const executeWorkbenchOmniboxCommand = (editingContextId: string, selectedObjectIds: string[], commandId: string) => {
    const variables: GQLExecuteWorkbenchOmniboxCommandVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        selectedObjectIds,
        commandId,
      },
    };
    performExecuteWorkbenchOmniboxCommand({ variables });
  };

  return {
    executeWorkbenchOmniboxCommand,
    loading,
    data: data ?? null,
  };
};
