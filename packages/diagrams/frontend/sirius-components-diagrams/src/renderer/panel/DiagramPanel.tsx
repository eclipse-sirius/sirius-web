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

import { ComponentExtension, ShareRepresentationModal, useComponents } from '@eclipse-sirius/sirius-components-core';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import AspectRatioIcon from '@mui/icons-material/AspectRatio';
import FullscreenIcon from '@mui/icons-material/Fullscreen';
import FullscreenExitIcon from '@mui/icons-material/FullscreenExit';
import GridOffIcon from '@mui/icons-material/GridOff';
import GridOnIcon from '@mui/icons-material/GridOn';
import ImageIcon from '@mui/icons-material/Image';
import ShareIcon from '@mui/icons-material/Share';
import TonalityIcon from '@mui/icons-material/Tonality';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import ZoomInIcon from '@mui/icons-material/ZoomIn';
import ZoomOutIcon from '@mui/icons-material/ZoomOut';
import CircularProgress from '@mui/material/CircularProgress';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, Panel, useNodesInitialized, useReactFlow } from '@xyflow/react';
import { memo, useContext, useEffect, useState } from 'react';
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
import { DiagramPanelActionProps, DiagramPanelProps, DiagramPanelState } from './DiagramPanel.types';
import { diagramPanelActionExtensionPoint } from './DiagramPanelExtensionPoints';
import { useExportToImage } from './useExportToImage';
import { SmartEdgeIcon } from '../../icons/SmartEdgeIcon';
import { SmoothStepEdgeIcon } from '../../icons/SmoothStepEdgeIcon';

export const DiagramPanel = memo(
  ({
    snapToGrid,
    onSnapToGrid,
    helperLines,
    onHelperLines,
    reactFlowWrapper,
    edgeType,
    onEdgeType,
  }: DiagramPanelProps) => {
    const [state, setState] = useState<DiagramPanelState>({
      dialogOpen: null,
      arrangeAllDone: false,
      arrangeAllInProgress: false,
    });

    const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
    const diagramPanelActionComponents: ComponentExtension<DiagramPanelActionProps>[] = useComponents(
      diagramPanelActionExtensionPoint
    );

    const { getNodes, getEdges, zoomIn, zoomOut, fitView } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

    const getAllElementsIds = () => [...getNodes().map((elem) => elem.id), ...getEdges().map((elem) => elem.id)];
    const getSelectedNodes = () => getNodes().filter((node) => node.selected);

    const { fullscreen, onFullscreen } = useFullscreen();
    const { arrangeAll } = useArrangeAll(reactFlowWrapper);
    const nodesInitialized = useNodesInitialized();
    useEffect(() => {
      if (nodesInitialized && state.arrangeAllDone) {
        fitView({ duration: 400, nodes: getNodes() });
        setState((prevState) => ({ ...prevState, arrangeAllDone: false }));
      }
    }, [nodesInitialized, state.arrangeAllDone]);

    const handleFitToScreen = () => {
      if (getSelectedNodes().length) {
        fitView({ duration: 200, nodes: getSelectedNodes() });
      } else {
        fitView({ duration: 200, nodes: getNodes() });
      }
    };
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
              <span>
                <IconButton
                  size="small"
                  aria-label="arrange all elements"
                  onClick={() => {
                    setState((prevState) => ({
                      ...prevState,
                      arrangeAllInProgress: true,
                    }));
                    arrangeAll().then(() =>
                      setState((prevState) => ({
                        ...prevState,
                        arrangeAllDone: true,
                        arrangeAllInProgress: false,
                      }))
                    );
                  }}
                  data-testid={'arrange-all'}
                  disabled={readOnly}>
                  {state.arrangeAllInProgress ? (
                    <CircularProgress size="24px" data-testid="arrange-all-circular-loading" />
                  ) : (
                    <AccountTreeIcon />
                  )}
                </IconButton>
              </span>
            </Tooltip>
            {edgeType === 'smoothStepEdge' ? (
              <Tooltip title="Smart Step Edge">
                <IconButton
                  size="small"
                  aria-label="smart step edge"
                  onClick={() => onEdgeType('smartStepEdge')}
                  data-testid="smart-step-edge">
                  <SmoothStepEdgeIcon />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title="Smooth Step Edge">
                <IconButton
                  size="small"
                  aria-label="smooth step edge"
                  onClick={() => onEdgeType('smoothStepEdge')}
                  data-testid="smooth-step-edge">
                  <SmartEdgeIcon />
                </IconButton>
              </Tooltip>
            )}
            <Tooltip title="Reveal hidden elements">
              <span>
                <IconButton
                  size="small"
                  aria-label="reveal hidden elements"
                  onClick={onUnhideAll}
                  data-testid="reveal-hidden-elements"
                  disabled={readOnly}>
                  <VisibilityOffIcon />
                </IconButton>
              </span>
            </Tooltip>
            <Tooltip title="Reveal faded elements">
              <span>
                <IconButton
                  size="small"
                  aria-label="reveal faded elements"
                  onClick={onUnfadeAll}
                  data-testid="reveal-faded-elements"
                  disabled={readOnly}>
                  <TonalityIcon />
                </IconButton>
              </span>
            </Tooltip>
            <Tooltip title="Unpin all elements">
              <span>
                <IconButton
                  size="small"
                  aria-label="unpin all elements"
                  onClick={onUnpinAll}
                  data-testid="unpin-all-elements"
                  disabled={readOnly}>
                  <UnpinIcon />
                </IconButton>
              </span>
            </Tooltip>
            {diagramPanelActionComponents.map(({ Component: DiagramPanelActionComponent }, index) => (
              <DiagramPanelActionComponent editingContextId={editingContextId} diagramId={diagramId} key={index} />
            ))}
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
