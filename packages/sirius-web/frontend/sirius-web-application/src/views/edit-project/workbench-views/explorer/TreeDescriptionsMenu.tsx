/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import AccountTreeIcon from '@mui/icons-material/AccountTree';
import CheckIcon from '@mui/icons-material/Check';
import IconButton from '@mui/material/IconButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { TreeDescriptionMetadata, TreeDescriptionsMenuProps } from './TreeDescriptionsMenu.types';

const useTreeDescriptionsMenuStyles = makeStyles()((_) => ({
  checkedTreeDescription: {
    opacity: '1',
  },
  uncheckedTreeDescription: {
    opacity: '0',
  },
}));

export const TreeDescriptionsMenu = ({
  treeDescriptions,
  activeTreeDescriptionId,
  onTreeDescriptionChange,
}: TreeDescriptionsMenuProps) => {
  const { classes } = useTreeDescriptionsMenuStyles();
  const [open, setOpen] = useState<boolean>(false);
  const anchorRef = useRef<HTMLButtonElement | null>(null);

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event: MouseEvent | TouchEvent) => {
    if (anchorRef.current && anchorRef.current.contains(event.target as HTMLElement)) {
      return;
    }
    setOpen(false);
  };

  const prevOpen = useRef<boolean>(open);
  useEffect(() => {
    if (prevOpen.current === true && open === false && anchorRef.current) {
      anchorRef.current.focus();
    }
    prevOpen.current = open;
  }, [open]);

  const updateActiveTreeDescription = (treeDescription: TreeDescriptionMetadata) => {
    onTreeDescriptionChange(treeDescription);
    setOpen(false);
  };

  return (
    <div>
      <IconButton
        data-testid={`tree-descriptions-menu-icon`}
        title="Explorers"
        aria-label="Explorers"
        aria-controls="tree-descriptions-menu"
        aria-haspopup="true"
        ref={anchorRef}
        color="inherit"
        size="small"
        onClick={handleToggle}>
        <AccountTreeIcon color={open ? 'disabled' : 'inherit'} />
      </IconButton>
      <Menu id="tree-descriptions-menu" anchorEl={anchorRef.current} keepMounted open={open} onClose={handleClose}>
        {treeDescriptions.map((td) => (
          <MenuItem
            key={td.id}
            data-testid={`tree-descriptions-menu-item-${td.label}`}
            selected={td.id === activeTreeDescriptionId}
            onClick={() => updateActiveTreeDescription(td)}>
            <ListItemIcon
              className={
                td.id === activeTreeDescriptionId ? classes.checkedTreeDescription : classes.uncheckedTreeDescription
              }>
              <CheckIcon />
            </ListItemIcon>
            <Typography variant="inherit" noWrap>
              {td.label}
            </Typography>
          </MenuItem>
        ))}
      </Menu>
    </div>
  );
};
