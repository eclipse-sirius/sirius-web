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

import DragIndicatorIcon from '@mui/icons-material/DragIndicator';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';
import { Edge, Node, useStoreApi, useViewport, XYPosition } from '@xyflow/react';
import React, { useEffect, useState } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  GQLPalette,
  GQLPaletteDivider,
  GQLPaletteEntry,
  GQLSingleClickOnDiagramElementTool,
  GQLToolSection,
  PaletteProps,
  PaletteState,
  PaletteStyleProps,
} from './Palette.types';
import { PaletteQuickAccessToolBar } from './quick-access-tool/PaletteQuickAccessToolBar';
import { PaletteSearchField } from './search/PaletteSearchField';
import { PaletteSearchResult } from './search/PaletteSearchResult';
import { PaletteToolList } from './tool-list/PaletteToolList';
import { usePalette } from './usePalette';

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
    justifyContent: 'flex-start',
    alignItems: 'center',
    color: theme.palette.grey[400],
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
  onEscape,
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
  const { handleToolClick, palette } = usePalette({ x, y, diagramElementId, onDirectEditClick, targetObjectId });

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
        onClick={(event) => event.stopPropagation()}>
        <Box id="tool-palette-header" className={classes.paletteHeader}>
          <DragIndicatorIcon />
        </Box>
        <Divider />
        <PaletteSearchField onValueChanged={onSearchFieldValueChanged} onEscape={onEscape} />
        <PaletteQuickAccessToolBar
          diagramElementId={diagramElementId}
          onToolClick={handleToolClick}
          quickAccessTools={palette.quickAccessTools}
          x={x}
          y={y}
        />
        {state.searchToolValue.length > 0 ? (
          <PaletteSearchResult
            searchToolValue={state.searchToolValue}
            palette={palette}
            onToolClick={handleToolClick}
          />
        ) : (
          <PaletteToolList palette={palette} onToolClick={handleToolClick} />
        )}
      </Paper>
    </Draggable>
  );
};
