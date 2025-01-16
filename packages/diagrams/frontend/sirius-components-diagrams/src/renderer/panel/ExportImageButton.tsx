/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import ImageIcon from '@mui/icons-material/Image';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import IconButton from '@mui/material/IconButton';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Tooltip from '@mui/material/Tooltip';
import { useRef, useState } from 'react';
import { useExportToImage } from './useExportToImage';

export const ExportImageButton = () => {
  const [exportImageMenuOpen, setExportImageMenuOpen] = useState<boolean>(false);
  const anchorExportImageMenuRef = useRef<HTMLButtonElement | null>(null);

  const handleExportImageMenuToggle = () => setExportImageMenuOpen((prevState) => !prevState);
  const onCloseExportImageMenu = () => setExportImageMenuOpen(false);

  const { exportToSVG, exportToPNG } = useExportToImage();

  return (
    <>
      <Tooltip title="Export the diagram as an image">
        <IconButton
          size="small"
          aria-label="export to image"
          onClick={handleExportImageMenuToggle}
          data-testid="export-diagram-to-image"
          ref={anchorExportImageMenuRef}>
          <ImageIcon />
          <KeyboardArrowDownIcon />
        </IconButton>
      </Tooltip>
      {exportImageMenuOpen ? (
        <Menu
          open={exportImageMenuOpen}
          anchorEl={anchorExportImageMenuRef.current}
          data-testid="export-diagram-to-image-menu"
          onClick={onCloseExportImageMenu}
          onClose={onCloseExportImageMenu}>
          <MenuItem onClick={exportToSVG} data-testid="export-diagram-to-svg">
            <ListItemText primary="SVG" />
          </MenuItem>
          <MenuItem onClick={exportToPNG} data-testid="export-diagram-to-png">
            <ListItemText primary="PNG" />
          </MenuItem>
        </Menu>
      ) : null}
    </>
  );
};
