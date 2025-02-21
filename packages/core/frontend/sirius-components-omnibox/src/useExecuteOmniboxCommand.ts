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
  GQLExecuteOmniboxCommandData,
  GQLExecuteOmniboxCommandPayload,
  GQLExecuteOmniboxCommandSuccessPayload,
  GQLExecuteOmniboxCommandVariables,
  UseExecuteOmniboxCommandValue,
} from './useExecuteOmniboxCommand.types';

const executeOmniboxCommandMutation = gql`
  mutation executeOmniboxCommand($input: ExecuteOmniboxCommandInput!) {
    executeOmniboxCommand(input: $input) {
      __typename
      ... on ExecuteOmniboxCommandSuccessPayload {
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

const isErrorPayload = (payload: GQLExecuteOmniboxCommandPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const isExecuteOmniboxCommandSuccessPayload = (
  payload: GQLExecuteOmniboxCommandPayload
): payload is GQLExecuteOmniboxCommandSuccessPayload => payload.__typename === 'ExecuteOmniboxCommandSuccessPayload';

export const useExecuteOmniboxCommand = (): UseExecuteOmniboxCommandValue => {
  const { setSelection } = useSelection();
  const { addErrorMessage, addMessages } = useMultiToast();

  const [performExecuteOmniboxCommand, { loading, data, error }] = useMutation<
    GQLExecuteOmniboxCommandData,
    GQLExecuteOmniboxCommandVariables
  >(executeOmniboxCommandMutation);

  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { executeOmniboxCommand } = data;
      if (isErrorPayload(executeOmniboxCommand)) {
        addMessages(executeOmniboxCommand.messages);
      }
      if (isExecuteOmniboxCommandSuccessPayload(executeOmniboxCommand)) {
        addMessages(executeOmniboxCommand.messages);
        if (isExecuteOmniboxCommandSuccessPayload(executeOmniboxCommand) && executeOmniboxCommand.newSelection) {
          const selection: Selection = {
            entries: executeOmniboxCommand.newSelection.entries.map((entry) => ({ id: entry.id, kind: entry.kind })),
          };
          setSelection(selection);
        }
      }
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
