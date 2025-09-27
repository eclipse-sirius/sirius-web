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

import { ComponentExtension, ShareRepresentationModal, useComponents } from '@eclipse-sirius/sirius-components-core';
import AspectRatioIcon from '@mui/icons-material/AspectRatio';
import FullscreenIcon from '@mui/icons-material/Fullscreen';
import FullscreenExitIcon from '@mui/icons-material/FullscreenExit';
import GridOffIcon from '@mui/icons-material/GridOff';
import GridOnIcon from '@mui/icons-material/GridOn';
import ShareIcon from '@mui/icons-material/Share';
import TonalityIcon from '@mui/icons-material/Tonality';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import ZoomInIcon from '@mui/icons-material/ZoomIn';
import ZoomOutIcon from '@mui/icons-material/ZoomOut';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, Panel, useNodesInitialized, useReactFlow } from '@xyflow/react';
import { memo, useContext, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { HelperLinesIcon } from '../../icons/HelperLinesIcon';
import { HelperLinesIconOff } from '../../icons/HelperLinesIconOff';
import { UnpinIcon } from '../../icons/UnpinIcon';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useFadeDiagramElements } from '../fade/useFadeDiagramElements';
import { useFitView } from '../fit-to-screen/useFitView';
import { useFullscreen } from '../fullscreen/useFullscreen';
import { useHideDiagramElements } from '../hide/useHideDiagramElements';
import { usePinDiagramElements } from '../pin/usePinDiagramElements';
import { ArrangeAllButton } from './ArrangeAllButton';
import { DiagramPanelActionProps, DiagramPanelProps, DiagramPanelState } from './DiagramPanel.types';
import { diagramPanelActionExtensionPoint } from './DiagramPanelExtensionPoints';
import { ExportImageButton } from './ExportImageButton';
import { RevealSelectionInDiagramButton } from './RevealSelectionInDiagramButton';

export const DiagramPanel = memo(
  ({ snapToGrid, onSnapToGrid, helperLines, onHelperLines, reactFlowWrapper }: DiagramPanelProps) => {
    const [state, setState] = useState<DiagramPanelState>({
      dialogOpen: null,
      arrangeAllDone: false,
      arrangeAllInProgress: false,
    });
    const { t } = useTranslation('siriusComponentsDiagrams', { keyPrefix: 'panel' });

    const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
    const diagramPanelActionComponents: ComponentExtension<DiagramPanelActionProps>[] = useComponents(
      diagramPanelActionExtensionPoint
    );

    const { getNodes, getEdges, zoomIn, zoomOut } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
    const { fitView } = useFitView();

    const getAllElementsIds = () => [...getNodes().map((elem) => elem.id), ...getEdges().map((elem) => elem.id)];
    const getSelectedNodes = () => getNodes().filter((node) => node.selected);

    const { fullscreen, onFullscreen } = useFullscreen();
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

    const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

    return (
      <>
        <Panel position="top-left">
          <Paper>
            {fullscreen ? (
              <Tooltip title={t('exitFullScreen')}>
                <IconButton size="small" aria-label={t('exitFullScreen')} onClick={() => onFullscreen(false)}>
                  <FullscreenExitIcon />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title={t('toggleFullScreen')}>
                <IconButton size="small" aria-label={t('toggleFullScreen')} onClick={() => onFullscreen(true)}>
                  <FullscreenIcon />
                </IconButton>
              </Tooltip>
            )}
            <Tooltip title={t('fitToScreen')}>
              <IconButton
                size="small"
                aria-label={t('fitToScreen')}
                onClick={handleFitToScreen}
                data-testid="fit-to-screen">
                <AspectRatioIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={t('zoomIn')}>
              <IconButton size="small" aria-label={t('zoomIn')} onClick={handleZoomIn}>
                <ZoomInIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={t('zoomOut')}>
              <IconButton size="small" aria-label={t('zoomOut')} onClick={handleZoomOut} data-testid="zoom-out">
                <ZoomOutIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={t('shareDiagram')}>
              <IconButton size="small" aria-label={t('shareDiagram')} onClick={handleShare} data-testid="share">
                <ShareIcon />
              </IconButton>
            </Tooltip>
            <ExportImageButton />
            {snapToGrid ? (
              <Tooltip title={t('exitSnapToGrid')}>
                <IconButton size="small" aria-label={t('exitSnapToGrid')} onClick={() => onSnapToGrid(false)}>
                  <GridOffIcon />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title={t('toggleSnapToGrid')}>
                <IconButton size="small" aria-label={t('toggleSnapToGrid')} onClick={() => onSnapToGrid(true)}>
                  <GridOnIcon />
                </IconButton>
              </Tooltip>
            )}
            {helperLines ? (
              <Tooltip title={t('hideHelperLines')}>
                <span>
                  <IconButton
                    size="small"
                    aria-label={t('hideHelperLines')}
                    onClick={() => onHelperLines(false)}
                    data-testid="hide-helper-lines"
                    disabled={readOnly}>
                    <HelperLinesIconOff />
                  </IconButton>
                </span>
              </Tooltip>
            ) : (
              <Tooltip title={t('showHelperLines')}>
                <span>
                  <IconButton
                    size="small"
                    aria-label={t('showHelperLines')}
                    onClick={() => onHelperLines(true)}
                    data-testid="show-helper-lines"
                    disabled={readOnly}>
                    <HelperLinesIcon />
                  </IconButton>
                </span>
              </Tooltip>
            )}
            <ArrangeAllButton reactFlowWrapper={reactFlowWrapper} disabled={readOnly} />
            <Tooltip title={t('revealHidden')}>
              <span>
                <IconButton
                  size="small"
                  aria-label={t('revealHidden')}
                  onClick={onUnhideAll}
                  data-testid="reveal-hidden-elements"
                  disabled={readOnly}>
                  <VisibilityOffIcon />
                </IconButton>
              </span>
            </Tooltip>
            <Tooltip title={t('revealFaded')}>
              <span>
                <IconButton
                  size="small"
                  aria-label={t('revealFaded')}
                  onClick={onUnfadeAll}
                  data-testid="reveal-faded-elements"
                  disabled={readOnly}>
                  <TonalityIcon />
                </IconButton>
              </span>
            </Tooltip>
            <Tooltip title={t('unpinAll')}>
              <span>
                <IconButton
                  size="small"
                  aria-label={t('unpinAll')}
                  onClick={onUnpinAll}
                  data-testid="unpin-all-elements"
                  disabled={readOnly}>
                  <UnpinIcon />
                </IconButton>
              </span>
            </Tooltip>
            <RevealSelectionInDiagramButton editingContextId={editingContextId} />
            {diagramPanelActionComponents.map(({ Component: DiagramPanelActionComponent }, index) => (
              <DiagramPanelActionComponent editingContextId={editingContextId} diagramId={diagramId} key={index} />
            ))}
          </Paper>
        </Panel>
        {state.dialogOpen === 'Share' ? (
          <ShareRepresentationModal representationId={diagramId} onClose={handleCloseDialog} />
        ) : null}
      </>
    );
  }
);
