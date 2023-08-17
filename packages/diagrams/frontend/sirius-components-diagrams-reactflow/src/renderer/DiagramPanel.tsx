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
import AccountTreeIcon from '@material-ui/icons/AccountTree';
import AspectRatioIcon from '@material-ui/icons/AspectRatio';
import FullscreenIcon from '@material-ui/icons/Fullscreen';
import FullscreenExitIcon from '@material-ui/icons/FullscreenExit';
import GridOffIcon from '@material-ui/icons/GridOff';
import GridOnIcon from '@material-ui/icons/GridOn';
import ShareIcon from '@material-ui/icons/Share';
import TonalityIcon from '@material-ui/icons/Tonality';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';

import { useState } from 'react';
import { Panel, useReactFlow } from 'reactflow';
import { DiagramPanelProps, DiagramPanelState } from './DiagramPanel.types';
import { ShareDiagramDialog } from './ShareDiagramDialog';
import { useFadeDiagramElements } from './fade/useFadeDiagramElements';
import { useHideDiagramElements } from './hide/useHideDiagramElements';

export const DiagramPanel = ({
  fullscreen,
  onFullscreen,
  snapToGrid,
  onSnapToGrid,
  onArrangeAll,
}: DiagramPanelProps) => {
  const [state, setState] = useState<DiagramPanelState>({
    dialogOpen: null,
  });

  const reactFlow = useReactFlow();
  const handleFitToScreen = () => reactFlow.fitView({ duration: 200 });
  const handleZoomIn = () => reactFlow.zoomIn({ duration: 200 });
  const handleZoomOut = () => reactFlow.zoomOut({ duration: 200 });
  const handleShare = () => setState((prevState) => ({ ...prevState, dialogOpen: 'Share' }));
  const handleCloseDialog = () => setState((prevState) => ({ ...prevState, dialogOpen: null }));

  const reactFlowInstance = useReactFlow();
  const { fadeDiagramElements } = useFadeDiagramElements();
  const { hideDiagramElements } = useHideDiagramElements();

  const onUnfadeAll = () => fadeDiagramElements([...getAllElementsIds()], false);
  const onUnhideAll = () => hideDiagramElements([...getAllElementsIds()], false);

  const getAllElementsIds = () => {
    return [
      ...reactFlowInstance.getNodes().map((elem) => elem.id),
      ...reactFlowInstance.getEdges().map((elem) => elem.id),
    ];
  };

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
          <IconButton size="small" onClick={handleFitToScreen}>
            <AspectRatioIcon />
          </IconButton>
          <IconButton size="small" onClick={handleZoomIn}>
            <ZoomInIcon />
          </IconButton>
          <IconButton size="small" onClick={handleZoomOut}>
            <ZoomOutIcon />
          </IconButton>
          <IconButton size="small" onClick={handleShare}>
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
          <IconButton size="small" onClick={() => onArrangeAll()}>
            <AccountTreeIcon />
          </IconButton>
          <IconButton
            size="small"
            aria-label="reveal hidden elements"
            title="Reveal hidden elements"
            onClick={onUnhideAll}
            data-testid="reveal-hidden-elements">
            <VisibilityOffIcon />
          </IconButton>
          <IconButton
            size="small"
            aria-label="reveal faded elements"
            title="Reveal faded elements"
            onClick={onUnfadeAll}
            data-testid="reveal-faded-elements">
            <TonalityIcon />
          </IconButton>
        </Paper>
      </Panel>
      {state.dialogOpen === 'Share' ? <ShareDiagramDialog onClose={handleCloseDialog} /> : null}
    </>
  );
};
