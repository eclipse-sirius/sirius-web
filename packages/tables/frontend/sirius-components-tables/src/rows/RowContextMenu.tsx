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

import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import IconButton from '@mui/material/IconButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Tooltip from '@mui/material/Tooltip';
import { MouseEvent, useEffect, useState } from 'react';
import { RowContextMenuEntry, RowContextMenuProps } from './RowContextMenu.types';
import { useInvokeRowContextMenuEntry } from './useInvokeRowContextMenuEntry';
import { useRowContextMenuEntries } from './useRowContextMenuEntries';
import { GQLRowContextMenuEntry } from './useRowContextMenuEntries.types';

const commonIconButtonStyles = {
  '&:hover': {
    opacity: 1,
  },
  height: '2rem',
  ml: '10px',
  opacity: 0.5,
  transition: 'opacity 150ms',
  width: '2rem',
};

export const RowContextMenu = ({
  editingContextId,
  representationId,
  tableId,
  row,
  table,
  readOnly,
}: RowContextMenuProps) => {
  const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);
  const [contextMenuEntries, setContextMenuEntries] = useState<RowContextMenuEntry[]>([]);
  const { callRowContextMenuEntriesQuery, loading, data } = useRowContextMenuEntries();
  const { invokeRowContextMenuEntry } = useInvokeRowContextMenuEntry(
    editingContextId,
    representationId,
    tableId,
    row.original.id
  );

  const convertMetadata = (menuEntry: GQLRowContextMenuEntry): RowContextMenuEntry => {
    return {
      __typename: menuEntry.__typename,
      id: menuEntry.id,
      label: menuEntry.label,
      iconURLs: menuEntry.iconURLs,
    };
  };

  useEffect(() => {
    if (!loading && data) {
      const allContextEntries = data.viewer.editingContext.representation?.description.contextMenu.map(convertMetadata);
      setContextMenuEntries(allContextEntries);
    }
  }, [loading, data]);

  const handleOpenRowActionMenu = (event: MouseEvent<HTMLElement>) => {
    event.stopPropagation();
    event.preventDefault();
    setAnchorEl(event.currentTarget);

    const variables = {
      variables: {
        editingContextId,
        representationId: tableId,
        tableId,
        rowId: row.original.id,
      },
    };
    callRowContextMenuEntriesQuery(variables);
  };

  const handleClickContextMenuEntry = (menuEntry: RowContextMenuEntry) => {
    invokeRowContextMenuEntry(menuEntry.id);
    setAnchorEl(null);
  };

  return (
    <>
      <Tooltip disableInteractive enterDelay={1000} enterNextDelay={1000} title="Row actions">
        <IconButton aria-label="Row actions" onClick={handleOpenRowActionMenu} size="small" sx={commonIconButtonStyles}>
          <MoreHorizIcon />
        </IconButton>
      </Tooltip>
      <Menu
        MenuListProps={{
          dense: false,
          sx: {
            backgroundColor: table.options.mrtTheme.menuBackgroundColor,
          },
        }}
        anchorEl={anchorEl}
        disableScrollLock
        onClick={(event) => event.stopPropagation()}
        onClose={() => setAnchorEl(null)}
        open={!!anchorEl}
        data-testid={`row-context_menu-${row.original.headerLabel}`}>
        {contextMenuEntries.map((entry) => (
          <MenuItem
            key={entry.id}
            onClick={(_) => handleClickContextMenuEntry(entry)}
            data-testid={`context-menu-entry-${entry.label}`}
            disabled={readOnly}
            aria-disabled>
            <ListItemIcon>
              {entry.iconURLs.length > 0 ? (
                <IconOverlay iconURL={entry.iconURLs} alt={entry.label} title={entry.label} />
              ) : (
                <div style={{ marginRight: '16px' }} />
              )}
            </ListItemIcon>
            <ListItemText primary={entry.label} />
          </MenuItem>
        ))}
      </Menu>
    </>
  );
};
