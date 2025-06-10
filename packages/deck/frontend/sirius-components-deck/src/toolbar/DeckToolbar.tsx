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
import { ShareRepresentationModal } from '@eclipse-sirius/sirius-components-core';
import AspectRatioIcon from '@mui/icons-material/AspectRatio';
import FullscreenIcon from '@mui/icons-material/Fullscreen';
import FullscreenExitIcon from '@mui/icons-material/FullscreenExit';
import SearchIcon from '@mui/icons-material/Search';
import ShareIcon from '@mui/icons-material/Share';
import ZoomInIcon from '@mui/icons-material/ZoomIn';
import ZoomOutIcon from '@mui/icons-material/ZoomOut';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { useFullscreen } from '../hooks/useFullScreen';
import { ToolbarProps, ToolbarState } from './Toolbar.types';

const useToolbarStyles = makeStyles()((theme) => ({
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    borderBottomColor: theme.palette.divider,
  },
}));

export const DeckToolbar = ({
  representationId,
  onZoomIn,
  onZoomOut,
  onFitToScreen,
  fullscreenNode,
  onResetZoom,
}: ToolbarProps) => {
  const { classes } = useToolbarStyles();
  const [state, setState] = useState<ToolbarState>({ modal: null });
  const { fullscreen, setFullscreen } = useFullscreen(fullscreenNode);

  const onShare = () => setState((prevState) => ({ ...prevState, modal: 'share' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modal: null }));

  let modalElement: React.ReactElement | null = null;
  if (state.modal === 'share') {
    modalElement = <ShareRepresentationModal representationId={representationId} onClose={closeModal} />;
  }

  return (
    <>
      <div className={classes.toolbar}>
        {fullscreen ? (
          <Tooltip title="Exit full screen mode">
            <IconButton
              size="small"
              color="inherit"
              aria-label="exit full screen mode"
              onClick={() => setFullscreen(false)}>
              <FullscreenExitIcon />
            </IconButton>
          </Tooltip>
        ) : (
          <Tooltip title="Toggle full screen mode">
            <IconButton
              size="small"
              color="inherit"
              aria-label="toggle full screen mode"
              onClick={() => setFullscreen(true)}>
              <FullscreenIcon />
            </IconButton>
          </Tooltip>
        )}
        {!fullscreen ? (
          //We disable the Fit to Screen but in Full screen mode because of issues to compute the parent container size.
          <Tooltip title="Fit to Screen">
            <IconButton
              size="small"
              color="inherit"
              aria-label="fit to screen"
              onClick={onFitToScreen}
              data-testid="fit-to-screen">
              <AspectRatioIcon />
            </IconButton>
          </Tooltip>
        ) : null}
        <Tooltip title="Zoom In">
          <IconButton size="small" color="inherit" aria-label="zoom in" onClick={onZoomIn} data-testid="zoomIn">
            <ZoomInIcon />
          </IconButton>
        </Tooltip>
        <Tooltip title="Zoom Out">
          <IconButton size="small" color="inherit" aria-label="zoom out" onClick={onZoomOut} data-testid="zoomOut">
            <ZoomOutIcon />
          </IconButton>
        </Tooltip>
        <Tooltip title="Reset zoom">
          <IconButton
            size="small"
            color="inherit"
            aria-label="reset zoom"
            onClick={onResetZoom}
            data-testid="resetZoom">
            <SearchIcon />
          </IconButton>
        </Tooltip>
        <Tooltip title="Share">
          <IconButton size="small" color="inherit" aria-label="share" onClick={onShare} data-testid="share">
            <ShareIcon />
          </IconButton>
        </Tooltip>
      </div>
      {modalElement}
    </>
  );
};
