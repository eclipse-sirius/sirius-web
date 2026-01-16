/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import Tooltip from '@mui/material/Tooltip';
import { DefaultMenuItemProps } from './DefaultMenuItem.types';
import { GQLTreeItemContextMenuEntry } from './useContextMenuEntries.types';
import { useInvokeContextMenuEntry } from './useInvokeContextMenuEntry';

const getEntryTooltip = (entry: GQLTreeItemContextMenuEntry) => {
  if (entry.keyBindings.length > 0) {
    return (
      ' (' +
      entry.keyBindings
        .map((keyBinding) => {
          return (
            (keyBinding.isCtrl ? 'CTRL + ' : '') +
            (keyBinding.isMeta ? 'META + ' : '') +
            (keyBinding.isAlt ? 'ALT + ' : '') +
            keyBinding.key
          );
        })
        .join(', ') +
      ')'
    );
  } else {
    return '';
  }
};

export const DefaultMenuItem = ({ editingContextId, treeId, item, entry, readOnly, onClick }: DefaultMenuItemProps) => {
  const { invokeContextMenuEntry } = useInvokeContextMenuEntry();

  return (
    <MenuItem
      onClick={() => invokeContextMenuEntry(editingContextId, treeId, item.id, entry, onClick)}
      data-testid={`context-menu-entry-${entry.label}`}
      disabled={readOnly}
      aria-disabled>
      <ListItemIcon>
        {entry.iconURL.length > 0 ? (
          <IconOverlay iconURLs={entry.iconURL} alt={entry.label} title={entry.label} />
        ) : (
          <div style={{ marginRight: '16px' }} />
        )}
      </ListItemIcon>
      <Tooltip title={getEntryTooltip(entry)} placement="right">
        <ListItemText primary={entry.label} />
      </Tooltip>
    </MenuItem>
  );
};
