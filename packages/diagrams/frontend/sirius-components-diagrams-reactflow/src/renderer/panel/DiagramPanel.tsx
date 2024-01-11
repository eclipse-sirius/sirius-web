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

import IconButton from '@material-ui/core/IconButton';
import Paper from '@material-ui/core/Paper';
import AccountTreeIcon from '@material-ui/icons/AccountTree';
import AspectRatioIcon from '@material-ui/icons/AspectRatio';
import FullscreenIcon from '@material-ui/icons/Fullscreen';
import FullscreenExitIcon from '@material-ui/icons/FullscreenExit';
import GridOffIcon from '@material-ui/icons/GridOff';
import GridOnIcon from '@material-ui/icons/GridOn';
import ImageIcon from '@material-ui/icons/Image';
import ShareIcon from '@material-ui/icons/Share';
import TonalityIcon from '@material-ui/icons/Tonality';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import { memo, useState } from 'react';
import { Panel, useReactFlow } from 'reactflow';
import { UnpinIcon } from '../../icons/UnpinIcon';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ShareDiagramDialog } from '../ShareDiagramDialog';
import { useFadeDiagramElements } from '../fade/useFadeDiagramElements';
import { useFullscreen } from '../fullscreen/useFullscreen';
import { useHideDiagramElements } from '../hide/useHideDiagramElements';
import { usePinDiagramElements } from '../pin/usePinDiagramElements';
import { DiagramPanelProps, DiagramPanelState } from './DiagramPanel.types';
import { useExportToImage } from './useExportToImage';
import { useArrangeAll } from '../layout/useArrangeAll';

export const DiagramPanel = memo(({ snapToGrid, onSnapToGrid }: DiagramPanelProps) => {
  const [state, setState] = useState<DiagramPanelState>({
    dialogOpen: null,
  });

  const reactFlow = useReactFlow<NodeData, EdgeData>();
  const { getEdges, getNodes } = reactFlow;

  const getAllElementsIds = () => [...getNodes().map((elem) => elem.id), ...getEdges().map((elem) => elem.id)];
  const getSelectedNodes = () => getNodes().filter((node) => node.selected);

  const { fullscreen, onFullscreen } = useFullscreen();
  const { arrangeAll } = useArrangeAll();

  const handleFitToScreen = () => reactFlow.fitView({ duration: 200, nodes: getSelectedNodes() });
  const handleZoomIn = () => reactFlow.zoomIn({ duration: 200 });
  const handleZoomOut = () => reactFlow.zoomOut({ duration: 200 });
  const handleShare = () => setState((prevState) => ({ ...prevState, dialogOpen: 'Share' }));
  const handleCloseDialog = () => setState((prevState) => ({ ...prevState, dialogOpen: null }));

  const { fadeDiagramElements } = useFadeDiagramElements();
  const { hideDiagramElements } = useHideDiagramElements();
  const { pinDiagramElements } = usePinDiagramElements();

  const onUnfadeAll = () => fadeDiagramElements([...getAllElementsIds()], false);
  const onUnhideAll = () => hideDiagramElements([...getAllElementsIds()], false);
  const onUnpinAll = () => pinDiagramElements([...getAllElementsIds()], false);

  const { exportToImage } = useExportToImage();

  return (
    <>
      <Panel position="top-left">
        <Paper>
          {fullscreen ? (
            <IconButton
              size="small"
              aria-label="exit full screen mode"
              title="Exit full screen mode"
              onClick={() => onFullscreen(false)}>
              <FullscreenExitIcon />
            </IconButton>
          ) : (
            <IconButton
              size="small"
              aria-label="toggle full screen mode"
              title="Toggle full screen mode"
              onClick={() => onFullscreen(true)}>
              <FullscreenIcon />
            </IconButton>
          )}
          <IconButton
            size="small"
            aria-label="fit to screen"
            title="Fit to screen"
            onClick={handleFitToScreen}
            data-testid="fit-to-screen">
            <AspectRatioIcon />
          </IconButton>
          <IconButton size="small" aria-label="zoom in" title="Zoom in" onClick={handleZoomIn}>
            <ZoomInIcon />
          </IconButton>
          <IconButton size="small" aria-label="zoom out" title="Zoom out" onClick={handleZoomOut}>
            <ZoomOutIcon />
          </IconButton>
          <IconButton
            size="small"
            aria-label="share diagram"
            title="Share diagram"
            onClick={handleShare}
            data-testid="share">
            <ShareIcon />
          </IconButton>
          <IconButton
            size="small"
            aria-label="export to svg"
            title="Export to SVG"
            onClick={exportToImage}
            data-testid="export-diagram-to-svg">
            <ImageIcon />
          </IconButton>
          {snapToGrid ? (
            <IconButton
              size="small"
              aria-label="exit snap to grid mode"
              title="Exit snap to grid mode"
              onClick={() => onSnapToGrid(false)}>
              <GridOffIcon />
            </IconButton>
          ) : (
            <IconButton
              size="small"
              aria-label="toggle snap to grid mode"
              title="Toggle snap to grid mode"
              onClick={() => onSnapToGrid(true)}>
              <GridOnIcon />
            </IconButton>
          )}
          <IconButton size="small" onClick={() => arrangeAll()}>
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
          <IconButton
            size="small"
            aria-label="unpin all elements"
            title="Unpin all elements"
            onClick={onUnpinAll}
            data-testid="unpin-all-elements">
            <UnpinIcon />
          </IconButton>
        </Paper>
      </Panel>
      {state.dialogOpen === 'Share' ? <ShareDiagramDialog onClose={handleCloseDialog} /> : null}
    </>
  );
});
