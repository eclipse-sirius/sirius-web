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

import { ComponentExtension, ShareRepresentationModal, useComponents } from '@eclipse-sirius/sirius-components-core';
import AspectRatioIcon from '@mui/icons-material/AspectRatio';
import ChevronLeft from '@mui/icons-material/ChevronLeft';
import ChevronRight from '@mui/icons-material/ChevronRight';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import CreditCardOffIcon from '@mui/icons-material/CreditCardOff';
import FullscreenIcon from '@mui/icons-material/Fullscreen';
import FullscreenExitIcon from '@mui/icons-material/FullscreenExit';
import GridOffIcon from '@mui/icons-material/GridOff';
import GridOnIcon from '@mui/icons-material/GridOn';
import ShareIcon from '@mui/icons-material/Share';
import TonalityIcon from '@mui/icons-material/Tonality';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import ZoomInIcon from '@mui/icons-material/ZoomIn';
import ZoomOutIcon from '@mui/icons-material/ZoomOut';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, Panel, useNodesInitialized, useReactFlow } from '@xyflow/react';
import { memo, useContext, useEffect, useRef, useState } from 'react';
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
import { HelperLinesContext } from '../helper-lines/HelperLinesContext';
import { HelperLinesContextValue } from '../helper-lines/HelperLinesContext.types';
import { useHideDiagramElements } from '../hide/useHideDiagramElements';
import { MiniMapContext } from '../mini-map/MiniMapContext';
import { MiniMapContextValue } from '../mini-map/MiniMapContext.types';
import { usePinDiagramElements } from '../pin/usePinDiagramElements';
import { SnapToGridContext } from '../snap-to-grid/SnapToGridContext';
import { SnapToGridContextValue } from '../snap-to-grid/SnapToGridContext.types';
import { ArrangeAllButton } from './ArrangeAllButton';
import { DiagramToolbarActionProps, DiagramToolbarProps, DiagramToolbarState } from './DiagramToolbar.types';
import { diagramToolbarActionExtensionPoint } from './DiagramToolbarExtensionPoints';
import { ExportImageButton } from './ExportImageButton';
import { RevealSelectionInDiagramButton } from './RevealSelectionInDiagramButton';

export const DiagramToolbar = memo(({ reactFlowWrapper, diagramToolbar }: DiagramToolbarProps) => {
  const [state, setState] = useState<DiagramToolbarState>({
    dialogOpen: null,
    arrangeAllDone: false,
    arrangeAllInProgress: false,
    expanded: diagramToolbar.expandedByDefault,
    contentWidth: null,
  });
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramToolbar' });

  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { isMiniMapVisible, setMiniMapVisibility } = useContext<MiniMapContextValue>(MiniMapContext);
  const { isHelperLineEnabled, setHelperLineEnabled } = useContext<HelperLinesContextValue>(HelperLinesContext);
  const { isSnapToGridEnabled, setSnapToGridEnabled } = useContext<SnapToGridContextValue>(SnapToGridContext);
  const diagramToolbarActionComponents: ComponentExtension<DiagramToolbarActionProps>[] = useComponents(
    diagramToolbarActionExtensionPoint
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

  const collapsibleContentRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (collapsibleContentRef.current) {
      setState((state) => ({ ...state, contentWidth: collapsibleContentRef.current?.scrollWidth ?? null }));
    }
  }, [collapsibleContentRef.current?.scrollWidth]);

  const onCollapse = (collapse: boolean) => {
    setState((prevState) => ({ ...prevState, expanded: !collapse }));
  };

  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  return (
    <>
      <Panel position="top-left">
        <Paper style={{ display: 'flex', alignItems: 'center', overflow: 'hidden' }}>
          <Box
            ref={collapsibleContentRef}
            style={{
              display: 'flex',
              overflow: 'hidden',
              width: state.expanded ? state.contentWidth ?? 'auto' : 0,
              visibility: state.expanded ? 'visible' : 'hidden',
              transition: 'width 0.5s ease, visibility 0.5s ease',
            }}>
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
            {isMiniMapVisible ? (
              <Tooltip title={t('hideMiniMap')}>
                <IconButton
                  size="small"
                  data-testid="hide-mini-map"
                  aria-label={t('hideMiniMap')}
                  onClick={() => setMiniMapVisibility(false)}>
                  <CreditCardOffIcon />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title={t('showMiniMap')}>
                <IconButton
                  size="small"
                  data-testid="show-mini-map"
                  aria-label={t('showMiniMap')}
                  onClick={() => setMiniMapVisibility(true)}>
                  <CreditCardIcon />
                </IconButton>
              </Tooltip>
            )}
            <Tooltip title={t('zoomIn')}>
              <IconButton size="small" aria-label={t('zoomIn')} onClick={handleZoomIn} data-testid="zoom-in">
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
            {isSnapToGridEnabled ? (
              <Tooltip title={t('exitSnapToGrid')}>
                <IconButton size="small" aria-label={t('exitSnapToGrid')} onClick={() => setSnapToGridEnabled(false)}>
                  <GridOffIcon />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title={t('toggleSnapToGrid')}>
                <IconButton size="small" aria-label={t('toggleSnapToGrid')} onClick={() => setSnapToGridEnabled(true)}>
                  <GridOnIcon />
                </IconButton>
              </Tooltip>
            )}
            {isHelperLineEnabled ? (
              <Tooltip title={t('hideHelperLines')}>
                <span>
                  <IconButton
                    size="small"
                    aria-label={t('hideHelperLines')}
                    onClick={() => setHelperLineEnabled(false)}
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
                    onClick={() => setHelperLineEnabled(true)}
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
            {diagramToolbarActionComponents.map(({ Component: DiagramToolbarActionComponent }, index) => (
              <DiagramToolbarActionComponent editingContextId={editingContextId} diagramId={diagramId} key={index} />
            ))}
          </Box>
          {state.expanded ? (
            <Tooltip title={t('collapse')}>
              <IconButton
                size="small"
                aria-label={t('collapse')}
                onClick={() => onCollapse(true)}
                data-testid="toolbar-collapse">
                <ChevronLeft />
              </IconButton>
            </Tooltip>
          ) : (
            <Tooltip title={t('expand')}>
              <IconButton
                size="small"
                aria-label={t('expand')}
                onClick={() => onCollapse(false)}
                data-testid="toolbar-expand">
                <ChevronRight />
              </IconButton>
            </Tooltip>
          )}
        </Paper>
      </Panel>
      {state.dialogOpen === 'Share' ? (
        <ShareRepresentationModal representationId={diagramId} onClose={handleCloseDialog} />
      ) : null}
    </>
  );
});
