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

import CloseIcon from '@mui/icons-material/Close';
import DragIndicatorIcon from '@mui/icons-material/DragIndicator';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Tooltip from '@mui/material/Tooltip';
import { Edge, Node, useStoreApi, useViewport, XYPosition } from '@xyflow/react';
import React, { useEffect, useState } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useInvokePaletteTool } from '../tools/useInvokePaletteTool';
import {
  GQLPalette,
  GQLPaletteDivider,
  GQLPaletteEntry,
  GQLSingleClickOnDiagramElementTool,
  GQLTool,
  GQLToolSection,
  PaletteProps,
  PaletteState,
  PaletteStyleProps,
} from './Palette.types';
import { PaletteQuickAccessToolBar } from './quick-access-tool/PaletteQuickAccessToolBar';
import { PaletteSearchField } from './search/PaletteSearchField';
import { PaletteSearchResult } from './search/PaletteSearchResult';
import { PaletteToolList } from './tool-list/PaletteToolList';
import { useDiagramPalette } from './useDiagramPalette';
import { usePaletteContents } from './usePaletteContents';
import { UsePaletteContentValue } from './usePaletteContents.types';

const usePaletteStyle = makeStyles<PaletteStyleProps>()((theme, props) => ({
  palette: {
    display: 'grid',
    gridAutoRows: `fit-content(100%)`,
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '10px',
    zIndex: 5,
    position: 'fixed',
    width: props.paletteWidth,
    height: props.paletteHeight,
  },
  paletteHeader: {
    cursor: 'move',
    width: '100%',
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    backgroundColor: `${theme.palette.secondary.main}08`,
  },
}));

export const isSingleClickOnDiagramElementTool = (tool: GQLPaletteEntry): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

export const isToolSection = (entry: GQLPaletteEntry): entry is GQLToolSection => entry.__typename === 'ToolSection';

export const isPaletteDivider = (entry: GQLPaletteDivider): entry is GQLToolSection =>
  entry.__typename === 'PaletteDivider';

const computeDraggableBounds = (bounds?: DOMRect): XYPosition => {
  return {
    x: bounds?.width ?? 0,
    y: bounds?.height ?? 0,
  };
};

const paletteWidth = 200;
const paletteHeight = 275;

const getPaletteToolCount = (palette: GQLPalette): number => {
  return (
    palette.paletteEntries.filter(isSingleClickOnDiagramElementTool).length +
    palette.quickAccessTools.filter(isSingleClickOnDiagramElementTool).length +
    palette.paletteEntries
      .filter(isToolSection)
      .filter((toolSection) => toolSection.tools.filter(isSingleClickOnDiagramElementTool).length > 0).length
  );
};

const computePaletteLocation = (
  paletteX: number,
  paletteY: number,
  viewportWidth: number,
  viewportHeight: number
): XYPosition => {
  return {
    x: paletteX + paletteWidth < viewportWidth ? paletteX : viewportWidth - paletteWidth,
    y: paletteY + paletteHeight < viewportHeight ? paletteY : viewportHeight - paletteHeight,
  };
};

export const Palette = ({
  x: paletteX,
  y: paletteY,
  diagramElementId,
  targetObjectId,
  onDirectEditClick,
  onClose,
}: PaletteProps) => {
  const { domNode, nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { x: viewportWidth, y: viewportHeight } = computeDraggableBounds(domNode?.getBoundingClientRect());

  const [state, setState] = useState<PaletteState>({
    searchToolValue: '',
    controlledPosition: { x: 0, y: 0 },
  });

  const diagramElement = nodeLookup.get(diagramElementId) || edgeLookup.get(diagramElementId);

  let x: number = 0;
  let y: number = 0;
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  if (viewportZoom !== 0 && paletteX && paletteY) {
    x = (paletteX - viewportX) / viewportZoom;
    y = (paletteY - viewportY) / viewportZoom;
  }

  const { palette }: UsePaletteContentValue = usePaletteContents(diagramElementId);
  const { setLastToolInvoked } = useDiagramPalette();
  const { invokeTool } = useInvokePaletteTool({ x, y, diagramElementId, onDirectEditClick, targetObjectId });

  const handleToolClick = (tool: GQLTool) => {
    onClose();
    domNode?.focus();
    invokeTool(tool);
    if (palette) {
      setLastToolInvoked(palette.id, tool);
    }
  };

  useEffect(() => {
    const paletteLocation: XYPosition = computePaletteLocation(paletteX, paletteY, viewportWidth, viewportHeight);
    setState((prevState) => ({ ...prevState, controlledPosition: paletteLocation }));
  }, [paletteX, paletteY, viewportWidth, viewportHeight]);

  const { classes } = usePaletteStyle({ paletteWidth: `${paletteWidth}px`, paletteHeight: `${paletteHeight}px` });

  const shouldRender = palette && (getPaletteToolCount(palette) > 0 || !!diagramElement);

  if (!shouldRender) {
    return null;
  }

  const onPaletteDragStop = (_event, data: DraggableData) => {
    setState((prevState) => ({ ...prevState, controlledPosition: data }));
  };

  const nodeRef = React.createRef<HTMLDivElement>();
  const draggableBounds = {
    left: 0,
    top: 0,
    bottom: viewportHeight - paletteHeight,
    right: viewportWidth - paletteWidth,
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
      bounds={draggableBounds}
      handle="#tool-palette-header"
      position={state.controlledPosition}
      onStop={onPaletteDragStop}>
      <Paper
        ref={nodeRef}
        className={classes.palette}
        data-testid="Palette"
        elevation={3}
        onClick={(event) => event.stopPropagation()}
        tabIndex={0}>
        <Box id="tool-palette-header" className={classes.paletteHeader}>
          <DragIndicatorIcon />
          <Tooltip title="Close">
            <IconButton size="small" aria-label="close" color="inherit" data-testid="Close-palette" onClick={onClose}>
              <CloseIcon fontSize="small" />
            </IconButton>
          </Tooltip>
        </Box>
        <Divider />
        <PaletteQuickAccessToolBar
          diagramElementId={diagramElementId}
          onToolClick={handleToolClick}
          quickAccessTools={palette.quickAccessTools}
          x={x}
          y={y}
        />
        <PaletteSearchField onValueChanged={onSearchFieldValueChanged} />
        {state.searchToolValue.length > 0 ? (
          <PaletteSearchResult
            searchToolValue={state.searchToolValue}
            palette={palette}
            onToolClick={handleToolClick}
          />
        ) : (
          <PaletteToolList palette={palette} onToolClick={handleToolClick} onBackToMainList={handleBackToMainList} />
        )}
      </Paper>
    </Draggable>
  );
};
