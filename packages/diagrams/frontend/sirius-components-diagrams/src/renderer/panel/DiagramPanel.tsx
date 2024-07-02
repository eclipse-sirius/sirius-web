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
import CircularProgress from '@material-ui/core/CircularProgress';
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
import { useTranslation } from 'react-i18next';
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
import { DiagramPanelActionProps, DiagramPanelProps, DiagramPanelState } from './DiagramPanel.types';
import { diagramPanelActionExtensionPoint } from './DiagramPanelExtensionPoints';
import { useExportToImage } from './useExportToImage';

export const DiagramPanel = memo(
  ({ snapToGrid, onSnapToGrid, helperLines, onHelperLines, reactFlowWrapper }: DiagramPanelProps) => {
    const { t } = useTranslation('siriusComponentsDiagrams', { keyPrefix: 'panel' });

    const [state, setState] = useState<DiagramPanelState>({
      dialogOpen: null,
      arrangeAllDone: false,
      arrangeAllInProgress: false,
    });

    const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
    const diagramPanelActionComponents: ComponentExtension<DiagramPanelActionProps>[] = useComponents(
      diagramPanelActionExtensionPoint
    );

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
              <IconButton size="small" aria-label={t('zoomOut')} onClick={handleZoomOut}>
                <ZoomOutIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={t('shareDiagram')}>
              <IconButton size="small" aria-label={t('shareDiagram')} onClick={handleShare} data-testid="share">
                <ShareIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={t('exportToSvg')}>
              <IconButton
                size="small"
                aria-label={t('exportToSvg')}
                onClick={exportToImage}
                data-testid="export-diagram-to-svg">
                <ImageIcon />
              </IconButton>
            </Tooltip>
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
                <IconButton
                  size="small"
                  aria-label={t('hideHelperLines')}
                  onClick={() => onHelperLines(false)}
                  data-testid="hide-helper-lines">
                  <HelperLinesIconOff />
                </IconButton>
              </Tooltip>
            ) : (
              <Tooltip title={t('showHelperLines')}>
                <IconButton
                  size="small"
                  aria-label={t('showHelperLines')}
                  onClick={() => onHelperLines(true)}
                  data-testid="show-helper-lines">
                  <HelperLinesIcon />
                </IconButton>
              </Tooltip>
            )}
            <Tooltip title={t('arrangeAll')}>
              <IconButton
                size="small"
                aria-label={t('arrangeAll')}
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
            </Tooltip>
            <Tooltip title={t('revealHidden')}>
              <IconButton
                size="small"
                aria-label={t('revealHidden')}
                onClick={onUnhideAll}
                data-testid="reveal-hidden-elements"
                disabled={readOnly}>
                <VisibilityOffIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={t('revealFaded')}>
              <IconButton
                size="small"
                aria-label={t('revealFaded')}
                onClick={onUnfadeAll}
                data-testid="reveal-faded-elements"
                disabled={readOnly}>
                <TonalityIcon />
              </IconButton>
            </Tooltip>
            <Tooltip title={t('unpinAll')}>
              <IconButton
                size="small"
                aria-label={t('unpinAll')}
                onClick={onUnpinAll}
                data-testid="unpin-all-elements"
                disabled={readOnly}>
                <UnpinIcon />
              </IconButton>
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
