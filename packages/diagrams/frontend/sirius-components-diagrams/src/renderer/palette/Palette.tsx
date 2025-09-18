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

import { PaletteSearchField, PaletteSearchResult, PaletteToolList } from '@eclipse-sirius/sirius-components-palette';
import CloseIcon from '@mui/icons-material/Close';
import DragIndicatorIcon from '@mui/icons-material/DragIndicator';
import Box from '@mui/material/Box';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import { Theme, useTheme } from '@mui/material/styles';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, useStoreApi, useViewport } from '@xyflow/react';
import React, { useContext, useEffect, useState } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useGetUpdatedModalPosition } from '../hooks/useGetUpdatedModalPosition';
import { DiagramToolExecutorContext } from '../tools/DiagramToolExecutorContext';
import { DiagramToolExecutorContextValue } from '../tools/DiagramToolExecutorContext.types';
import {
  GQLPalette,
  GQLPaletteDivider,
  GQLPaletteEntry,
  GQLSingleClickOnDiagramElementTool,
  GQLTool,
  GQLToolSection,
  PaletteProps,
  PaletteState,
} from './Palette.types';
import { PaletteQuickAccessToolBar } from './quick-access-tool/PaletteQuickAccessToolBar';
import { useDiagramPalette } from './useDiagramPalette';
import { usePaletteContents } from './usePaletteContents';
import { UsePaletteContentValue } from './usePaletteContents.types';

export const isSingleClickOnDiagramElementTool = (tool: GQLPaletteEntry): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

export const isToolSection = (entry: GQLPaletteEntry): entry is GQLToolSection => entry.__typename === 'ToolSection';

export const isPaletteDivider = (entry: GQLPaletteDivider): entry is GQLToolSection =>
  entry.__typename === 'PaletteDivider';

export const isTool = (entry: GQLPaletteEntry): entry is GQLTool => !isPaletteDivider(entry) && !isToolSection(entry);

const paletteWidth = 200;

const getPaletteToolCount = (palette: GQLPalette): number => {
  return (
    palette.paletteEntries.filter(isSingleClickOnDiagramElementTool).length +
    palette.quickAccessTools.filter(isSingleClickOnDiagramElementTool).length +
    palette.paletteEntries
      .filter(isToolSection)
      .filter((toolSection) => toolSection.tools.filter(isSingleClickOnDiagramElementTool).length > 0).length
  );
};

export const Palette = ({
  x: paletteX,
  y: paletteY,
  diagramElementId,
  targetObjectId,
  onDirectEditClick,
  onClose,
  children,
}: PaletteProps) => {
  const { domNode, nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { getUpdatedModalPosition, getUpdatedBounds } = useGetUpdatedModalPosition();
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  const nodeRef = React.useRef<HTMLDivElement>(null);
  const theme: Theme = useTheme();

  const [state, setState] = useState<PaletteState>({
    searchToolValue: '',
    controlledPosition: { x: paletteX, y: paletteY },
  });

  const diagramElement = nodeLookup.get(diagramElementId) || edgeLookup.get(diagramElementId);

  const { palette }: UsePaletteContentValue = usePaletteContents(diagramElementId);
  const { setLastToolInvoked, getLastToolInvoked } = useDiagramPalette();
  const { executeTool } = useContext<DiagramToolExecutorContextValue>(DiagramToolExecutorContext);

  const lastToolInvoked = palette ? getLastToolInvoked(palette.id) : null;

  const handleToolClick = (tool: GQLTool) => {
    onClose();
    domNode?.focus();
    let x: number = 0;
    let y: number = 0;
    if (viewportZoom !== 0 && paletteX && paletteY) {
      x = (paletteX - viewportX) / viewportZoom;
      y = (paletteY - viewportY) / viewportZoom;
    }
    executeTool(x, y, diagramElementId, targetObjectId, onDirectEditClick, tool);
    if (palette) {
      setLastToolInvoked(palette.id, tool);
    }
    const position = getUpdatedModalPosition({ x: state.controlledPosition.x, y: state.controlledPosition.y }, nodeRef);
    setState((prevState) => {
      return { ...prevState, controlledPosition: position };
    });
  };

  useEffect(() => {}, []);

  const shouldRender = palette && (getPaletteToolCount(palette) > 0 || !!diagramElement);

  useEffect(() => {
    if (!nodeRef.current) return;
    // If the palette size changes when opening a section then update the position of the modal
    const resizeObserver = new ResizeObserver(() => {
      setState((prevState) => {
        return {
          ...prevState,
          controlledPosition: getUpdatedModalPosition(
            { x: prevState.controlledPosition.x, y: prevState.controlledPosition.y },
            nodeRef
          ),
        };
      });
    });
    resizeObserver.observe(nodeRef.current);

    return () => resizeObserver.disconnect();
  }, [shouldRender]);

  const draggableBounds = getUpdatedBounds(nodeRef);

  if (!shouldRender) {
    return null;
  }

  const onPaletteDragStop = (_event, data: DraggableData) => {
    setState((prevState) => ({ ...prevState, controlledPosition: data }));
  };

  const onSearchFieldValueChanged = (newValue: string): void => {
    setState((prevState) => ({ ...prevState, searchToolValue: newValue }));
  };

  const handleBackToMainList = () => {
    if (nodeRef.current) {
      nodeRef.current.focus();
    }
  };

  return (
    <Draggable
      nodeRef={nodeRef}
      handle="#tool-palette-header"
      bounds={draggableBounds}
      position={state.controlledPosition}
      onStop={onPaletteDragStop}>
      <Paper
        ref={nodeRef}
        data-testid="Palette"
        elevation={3}
        onClick={(event) => event.stopPropagation()}
        tabIndex={0}
        sx={{
          border: `1px solid ${theme.palette.divider}`,
          borderRadius: '10px',
          zIndex: 5,
          position: 'fixed',
          width: paletteWidth,
        }}>
        <ClickAwayListener mouseEvent="onPointerDown" onClickAway={onClose}>
          <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'stretch', maxHeight: 280 }}>
            <Box
              id="tool-palette-header"
              sx={{
                cursor: 'move',
                width: '100%',
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'space-between',
                alignItems: 'center',
                backgroundColor: `${theme.palette.secondary.main}08`,
              }}>
              <DragIndicatorIcon />
              <Tooltip title="Close">
                <IconButton
                  size="small"
                  aria-label="close"
                  color="inherit"
                  data-testid="Close-palette"
                  onClick={onClose}>
                  <CloseIcon fontSize="small" />
                </IconButton>
              </Tooltip>
            </Box>
            <Divider />
            <PaletteQuickAccessToolBar
              diagramElementId={diagramElementId}
              onToolClick={handleToolClick}
              quickAccessTools={palette.quickAccessTools}
              x={paletteX}
              y={paletteY}
            />
            <PaletteSearchField onValueChanged={onSearchFieldValueChanged} />
            {state.searchToolValue.length > 0 ? (
              <PaletteSearchResult
                searchToolValue={state.searchToolValue}
                palette={palette}
                onToolClick={handleToolClick}
              />
            ) : (
              <PaletteToolList
                palette={palette}
                onToolClick={handleToolClick}
                onBackToMainList={handleBackToMainList}
                lastToolInvoked={lastToolInvoked}
                diagramElementId={diagramElementId}>
                {children}
              </PaletteToolList>
            )}
          </Box>
        </ClickAwayListener>
      </Paper>
    </Draggable>
  );
};
