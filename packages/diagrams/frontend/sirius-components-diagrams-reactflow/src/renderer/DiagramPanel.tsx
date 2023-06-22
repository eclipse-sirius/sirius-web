/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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

import IconButton from '@material-ui/core/IconButton';
import Paper from '@material-ui/core/Paper';
import AspectRatioIcon from '@material-ui/icons/AspectRatio';
import FullscreenIcon from '@material-ui/icons/Fullscreen';
import FullscreenExitIcon from '@material-ui/icons/FullscreenExit';
import GridOffIcon from '@material-ui/icons/GridOff';
import GridOnIcon from '@material-ui/icons/GridOn';
import ShareIcon from '@material-ui/icons/Share';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import { useState } from 'react';
import { Panel } from 'reactflow';
import { DiagramPanelProps, DiagramPanelState } from './DiagramPanel.types';
import { ShareDiagramDialog } from './ShareDiagramDialog';

export const DiagramPanel = ({
  fullscreen,
  onFullscreen,
  onFitToScreen,
  onZoomIn,
  onZoomOut,
  snapToGrid,
  onSnapToGrid,
}: DiagramPanelProps) => {
  const [state, setState] = useState<DiagramPanelState>({
    dialogOpen: null,
  });

  const onShare = () => setState((prevState) => ({ ...prevState, dialogOpen: 'Share' }));
  const onCloseDialog = () => setState((prevState) => ({ ...prevState, dialogOpen: null }));

  return (
    <>
      <Panel position="top-left">
        <Paper>
          {fullscreen ? (
            <IconButton size="small" onClick={() => onFullscreen(false)}>
              <FullscreenExitIcon />
            </IconButton>
          ) : (
            <IconButton size="small" onClick={() => onFullscreen(true)}>
              <FullscreenIcon />
            </IconButton>
          )}
          <IconButton size="small" onClick={() => onFitToScreen()}>
            <AspectRatioIcon />
          </IconButton>
          <IconButton size="small" onClick={() => onZoomIn()}>
            <ZoomInIcon />
          </IconButton>
          <IconButton size="small" onClick={() => onZoomOut()}>
            <ZoomOutIcon />
          </IconButton>
          <IconButton size="small" onClick={() => onShare()}>
            <ShareIcon />
          </IconButton>
          {snapToGrid ? (
            <IconButton size="small" onClick={() => onSnapToGrid(false)}>
              <GridOffIcon />
            </IconButton>
          ) : (
            <IconButton size="small" onClick={() => onSnapToGrid(true)}>
              <GridOnIcon />
            </IconButton>
          )}
        </Paper>
      </Panel>
      {state.dialogOpen === 'Share' ? <ShareDiagramDialog onClose={onCloseDialog} /> : null}
    </>
  );
};
