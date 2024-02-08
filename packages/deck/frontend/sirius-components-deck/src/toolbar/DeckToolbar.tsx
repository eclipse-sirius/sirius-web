/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import AspectRatioIcon from '@material-ui/icons/AspectRatio';
import FullscreenIcon from '@material-ui/icons/Fullscreen';
import FullscreenExitIcon from '@material-ui/icons/FullscreenExit';
import ShareIcon from '@material-ui/icons/Share';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import { useState } from 'react';
import { useFullscreen } from '../hooks/useFullScreen';
import { ToolbarProps, ToolbarState } from './Toolbar.types';

const useToolbarStyles = makeStyles((theme) => ({
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
  editingContextId,
  representationId,
  onZoomIn,
  onZoomOut,
  onFitToScreen,
  fullscreenNode,
}: ToolbarProps) => {
  const classes = useToolbarStyles();
  const [state, setState] = useState<ToolbarState>({ modal: null });
  const { fullscreen, setFullscreen } = useFullscreen(fullscreenNode);

  const onShare = () => setState((prevState) => ({ ...prevState, modal: 'share' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modal: null }));

  let modalElement: React.ReactElement | null = null;
  if (state.modal === 'share') {
    modalElement = (
      <ShareRepresentationModal
        editingContextId={editingContextId}
        representationId={representationId}
        onClose={closeModal}
      />
    );
  }

  return (
    <>
      <div className={classes.toolbar}>
        {fullscreen ? (
          <IconButton
            size="small"
            color="inherit"
            aria-label="exit full screen mode"
            title="Exit full screen mode"
            onClick={() => setFullscreen(false)}>
            <FullscreenExitIcon />
          </IconButton>
        ) : (
          <IconButton
            size="small"
            color="inherit"
            aria-label="toggle full screen mode"
            title="Toggle full screen mode"
            onClick={() => setFullscreen(true)}>
            <FullscreenIcon />
          </IconButton>
        )}
        {!fullscreen ? (
          //We disable the Fit to Screen but in Full screen mode because of issues to compute the parent container size.
          <IconButton
            size="small"
            color="inherit"
            aria-label="fit to screen"
            title="Fit to Screen"
            onClick={onFitToScreen}
            data-testid="fit-to-screen">
            <AspectRatioIcon />
          </IconButton>
        ) : null}
        <IconButton
          size="small"
          color="inherit"
          aria-label="zoom in"
          title="Zoom In"
          onClick={onZoomIn}
          data-testid="share">
          <ZoomInIcon />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="zoom out"
          title="Zoom Out"
          onClick={onZoomOut}
          data-testid="share">
          <ZoomOutIcon />
        </IconButton>
        <IconButton size="small" color="inherit" aria-label="share" title="Share" onClick={onShare} data-testid="share">
          <ShareIcon />
        </IconButton>
      </div>
      {modalElement}
    </>
  );
};
