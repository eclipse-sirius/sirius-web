/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Tooltip from '@mui/material/Tooltip';
import { useCallback, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useExperimentalSvgExport } from './experimental-svg-export/useExperimentalSvgExport';
import { useExportToImage } from './useExportToImage';

const downloadImage = (dataUrl: string, fileName: string) => {
  const a: HTMLAnchorElement = document.createElement('a');
  a.setAttribute('download', fileName);
  a.setAttribute('href', dataUrl);
  a.click();
};

export const ExportImageButton = () => {
  const [exportImageMenuOpen, setExportImageMenuOpen] = useState<boolean>(false);
  const anchorExportImageMenuRef = useRef<HTMLButtonElement | null>(null);

  const handleExportImageMenuToggle = () => setExportImageMenuOpen((prevState) => !prevState);
  const onCloseExportImageMenu = () => setExportImageMenuOpen(false);

  const { exportToSVG, exportToPNG } = useExportToImage();
  const { experimentalExportToSvg: protoExportToSvg } = useExperimentalSvgExport();

  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'exportImageButton' });

  const ref = useCallback((node: HTMLDivElement | null) => {
    if (node) {
      new Promise((resolve) => setTimeout(resolve, 5000)).then(() => {
        if (urlParams.get('mode') === 'png-viewer') {
          exportToPNG((dataUrl) => {
            var img = new Image();
            img.src = dataUrl;
            img.id = 'png-viewer-image';
            node.appendChild(img);
          });
        } else if (urlParams.get('mode') === 'svg-viewer') {
          protoExportToSvg((dataUrl) => {
            var img = new Image();
            img.src = dataUrl;
            img.id = 'svg-viewer-image';
            node.appendChild(img);
          });
        }
      });
    }
  }, []);

  const urlParams = new URLSearchParams(window.location.search);
  if (urlParams.has('mode')) {
    const title = urlParams.get('mode') === 'png-viewer' ? 'PNG Viewer' : 'SVG Viewer';
    return (
      <Dialog open fullWidth maxWidth="xl">
        <DialogTitle>{title}</DialogTitle>
        <div ref={ref}></div>
      </Dialog>
    );
  }

  return (
    <>
      <Tooltip title={t('exportDiagram')}>
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
          onClose={onCloseExportImageMenu}>
          <MenuItem
            onClick={() =>
              exportToSVG((dataUrl: string) => {
                downloadImage(dataUrl, 'diagram.svg');
                onCloseExportImageMenu();
              })
            }
            data-testid="export-diagram-to-svg">
            <ListItemText primary="SVG" />
          </MenuItem>
          <MenuItem
            onClick={() =>
              exportToPNG((dataUrl: string) => {
                downloadImage(dataUrl, 'diagram.png');
                onCloseExportImageMenu();
              })
            }
            data-testid="export-diagram-to-png">
            <ListItemText primary="PNG" />
          </MenuItem>
          <MenuItem
            data-testid="experimental-export-diagram-to-svg"
            onClick={() =>
              protoExportToSvg((dataUrl: string) => {
                downloadImage(dataUrl, 'diagram.svg');
                onCloseExportImageMenu();
              })
            }>
            <ListItemText primary={`SVG (${t('experimentalFeature')})`} />
          </MenuItem>
        </Menu>
      ) : null}
    </>
  );
};
