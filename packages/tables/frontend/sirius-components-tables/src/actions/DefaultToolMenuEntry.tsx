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
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useEffect } from 'react';
import {
  DefaultToolMenuEntryProps,
  GQLInvokeToolMenuEntryData,
  GQLInvokeToolMenuEntryVariables,
  GQLInvokeToolMenuEntryPayload,
  GQLErrorPayload,
  GQLInvokeToolMenuEntryInput,
} from './DefaultToolMenuEntry.types';

const invokeToolMenuEntryMutation = gql`
  mutation invokeToolMenuEntry($input: InvokeToolMenuEntryInput!) {
    invokeToolMenuEntry(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLInvokeToolMenuEntryPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const DefaultToolMenuEntry = ({
  editingContextId,
  representationId,
  tableId,
  entry,
  readOnly,
}: DefaultToolMenuEntryProps) => {
  const { addErrorMessage } = useMultiToast();

  const [invokeToolMenuEntry, { data, error }] = useMutation<
    GQLInvokeToolMenuEntryData,
    GQLInvokeToolMenuEntryVariables
  >(invokeToolMenuEntryMutation);

  useEffect(() => {
    if (error) {
      addErrorMessage('An error has occurred while executing this action, please contact the server administrator');
    }
    if (data) {
      const { invokeToolMenuEntry } = data;
      if (isErrorPayload(invokeToolMenuEntry)) {
        addErrorMessage(invokeToolMenuEntry.message);
      }
    }
  }, [error, data]);

  const invokeTool = (menuEntryId: string) => {
    const input: GQLInvokeToolMenuEntryInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId,
      menuEntryId,
    };
    invokeToolMenuEntry({ variables: { input } });
  };

  return (
    <MenuItem
      onClick={() => invokeTool(entry.id)}
      data-testid={`context-menu-entry-${entry.label}`}
      disabled={readOnly}
      aria-disabled>
      <ListItemIcon>
        {entry.iconURLs.length > 0 ? (
          <IconOverlay iconURLs={entry.iconURLs} alt={entry.label} title={entry.label} />
        ) : (
          <div style={{ marginRight: '16px' }} />
        )}
      </ListItemIcon>
      <ListItemText primary={entry.label} />
    </MenuItem>
  );
};
