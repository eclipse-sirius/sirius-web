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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { DefaultToolMenuEntryProps } from './DefaultToolMenuEntry.types';
import { useInvokeToolMenuEntry } from './useInvokeToolMenuEntry';

export const DefaultToolMenuEntry = ({
  editingContextId,
  representationId,
  tableId,
  entry,
  readOnly,
}: DefaultToolMenuEntryProps) => {
  const { invokeTool, loading } = useInvokeToolMenuEntry();

  return (
    <MenuItem
      onClick={() => invokeTool(editingContextId, representationId, tableId, entry.id)}
      data-testid={`context-menu-entry-${entry.label}`}
      disabled={loading || readOnly}
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
