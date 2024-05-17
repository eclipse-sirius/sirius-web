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
import Paper from '@material-ui/core/Paper';
import Tooltip from '@material-ui/core/Tooltip';
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
import { memo, useContext, useEffect, useState } from 'react';
import { Panel, useNodesInitialized, useReactFlow } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { HelperLinesIcon } from '../../icons/HelperLinesIcon';
import { HelperLinesIconOff } from '../../icons/HelperLinesIconOff';
import { UnpinIcon } from '../../icons/UnpinIcon';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useFadeDiagramElements } from '../fade/useFadeDiagramElements';
import { useFullscreen } from '../fullscreen/useFullscreen';
import { useHideDiagramElements } from '../hide/useHideDiagramElements';
import { useArrangeAll } from '../layout/useArrangeAll';
import { usePinDiagramElements } from '../pin/usePinDiagramElements';
import { DiagramPanelProps, DiagramPanelState } from './DiagramPanel.types';
import { useExportToImage } from './useExportToImage';

export const DiagramPanel = memo(
  ({ snapToGrid, onSnapToGrid, helperLines, onHelperLines, reactFlowWrapper }: DiagramPanelProps) => {
    const [state, setState] = useState<DiagramPanelState>({
      dialogOpen: null,
      arrangeAllDone: false,
    });

    const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

    const { getNodes, getEdges, zoomIn, zoomOut, fitView } = useReactFlow<NodeData, EdgeData>();

    const getAllElementsIds = () => [...getNodes().map((elem) => elem.id), ...getEdges().map((elem) => elem.id)];
    const getSelectedNodes = () => getNodes().filter((node) => node.selected);

    const { fullscreen, onFullscreen } = useFullscreen();
    const { arrangeAll } = useArrangeAll(reactFlowWrapper);
    const nodesInitialized = useNodesInitialized();
    useEffect(() => {
      if (nodesInitialized && state.arrangeAllDone) {
        fitView({ duration: 400 });
        setState((prevState) => ({ ...prevState, arrangeAllDone: false }));
      }
    }, [nodesInitialized, state.arrangeAllDone]);

    const handleFitToScreen = () => fitView({ duration: 200, nodes: getSelectedNodes() });
    const handleZoomIn = () => zoomIn({ duration: 200 });
    const handleZoomOut = () => zoomOut({ duration: 200 });
    const handleShare = () => setState((prevState) => ({ ...prevState, dialogOpen: 'Share' }));
    const handleCloseDialog = () => setState((prevState) => ({ ...prevState, dialogOpen: null }));

    const { fadeDiagramElements } = useFadeDiagramElements();
    const { hideDiagramElements } = useHideDiagramElements();
    const { pinDiagramElements } = usePinDiagramElements();

    const onUnfadeAll = () => fadeDiagramElements([...getAllElementsIds()], false);
    const onUnhideAll = () => hideDiagramElements([...getAllElementsIds()], false);
    const onUnpinAll = () => pinDiagramElements([...getAllElementsIds()], false);

    const { exportToImage } = useExportToImage();
    const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

    return (
      <>
        <Panel position="top-left">
          <Paper>
            {fullscreen ? (
              <Tooltip title="Exit full screen mode">
                <IconButton size="small" aria-label="exit full screen mode" onClick={() => onFullscreen(false)}>
                  <FullscreenExitIcon />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title="Toggle full screen mode">
                <IconButton size="small" aria-label="toggle full screen mode" onClick={() => onFullscreen(true)}>
                  <FullscreenIcon />
                </IconButton>
              </Tooltip>
            )}
            <Tooltip title="Fit to screen">
              <IconButton
                size="small"
                aria-label="fit to screen"
                onClick={handleFitToScreen}
                data-testid="fit-to-screen">
                <AspectRatioIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title="Zoom in">
              <IconButton size="small" aria-label="zoom in" onClick={handleZoomIn}>
                <ZoomInIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title="Zoom out">
              <IconButton size="small" aria-label="zoom out" onClick={handleZoomOut}>
                <ZoomOutIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title="Share diagram">
              <IconButton size="small" aria-label="share diagram" onClick={handleShare} data-testid="share">
                <ShareIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title="Export to SVG">
              <IconButton
                size="small"
                aria-label="export to svg"
                onClick={exportToImage}
                data-testid="export-diagram-to-svg">
                <ImageIcon />
              </IconButton>
            </Tooltip>
            {snapToGrid ? (
              <Tooltip title="Exit snap to grid mode">
                <IconButton size="small" aria-label="exit snap to grid mode" onClick={() => onSnapToGrid(false)}>
                  <GridOffIcon />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title="Toggle snap to grid mode">
                <IconButton size="small" aria-label="toggle snap to grid mode" onClick={() => onSnapToGrid(true)}>
                  <GridOnIcon />
                </IconButton>
              </Tooltip>
            )}
            {helperLines ? (
              <Tooltip title="Hide helper lines">
                <IconButton
                  size="small"
                  aria-label="hide helper lines"
                  onClick={() => onHelperLines(false)}
                  data-testid="hide-helper-lines">
                  <HelperLinesIconOff />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title="Show helper lines">
                <IconButton
                  size="small"
                  aria-label="show helper lines"
                  onClick={() => onHelperLines(true)}
                  data-testid="show-helper-lines">
                  <HelperLinesIcon />
                </IconButton>
              </Tooltip>
            )}
            <Tooltip title="Arrange all elements">
              <IconButton
                size="small"
                aria-label="arrange all elements"
                onClick={() =>
                  arrangeAll().then(() =>
                    setState((prevState) => ({
                      ...prevState,
                      arrangeAllDone: true,
                    }))
                  )
                }
                data-testid={'arrange-all'}
                disabled={readOnly}>
                <AccountTreeIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title="Reveal hidden elements">
              <IconButton
                size="small"
                aria-label="reveal hidden elements"
                onClick={onUnhideAll}
                data-testid="reveal-hidden-elements"
                disabled={readOnly}>
                <VisibilityOffIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title="Reveal faded elements">
              <IconButton
                size="small"
                aria-label="reveal faded elements"
                onClick={onUnfadeAll}
                data-testid="reveal-faded-elements"
                disabled={readOnly}>
                <TonalityIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title="Unpin all elements">
              <IconButton
                size="small"
                aria-label="unpin all elements"
                onClick={onUnpinAll}
                data-testid="unpin-all-elements"
                disabled={readOnly}>
                <UnpinIcon />
              </IconButton>
            </Tooltip>
          </Paper>
        </Panel>
        {state.dialogOpen === 'Share' ? (
          <ShareRepresentationModal
            editingContextId={editingContextId}
            representationId={diagramId}
            onClose={handleCloseDialog}
          />
        ) : null}
      </>
    );
  }
);
