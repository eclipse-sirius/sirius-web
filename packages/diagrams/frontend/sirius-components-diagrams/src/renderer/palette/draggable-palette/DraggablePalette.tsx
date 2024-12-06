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
import { useStoreApi, XYPosition } from '@xyflow/react';
import React, { useEffect, useState } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { makeStyles } from 'tss-react/mui';
import { PaletteSearchField } from './../search/PaletteSearchField';
import { PaletteSearchResult } from './../search/PaletteSearchResult';
import { PaletteToolList } from './../tool-list/PaletteToolList';
import {
  DraggablePaletteProps,
  PaletteDivider,
  PaletteEntry,
  PaletteStyleProps,
  Tool,
  ToolSection,
} from './DraggablePalette.types';

const usePaletteStyle = makeStyles<PaletteStyleProps>()((theme, props) => ({
  quickAccessTools: {
    display: 'flex',
    flexWrap: 'nowrap',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    overflowX: 'auto',
  },
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

const computeDraggableBounds = (bounds?: DOMRect): XYPosition => {
  return {
    x: bounds?.width ?? 0,
    y: bounds?.height ?? 0,
  };
};

const paletteWidth = 200;
const paletteHeight = 275;

export const isTool = (tool: PaletteEntry): tool is Tool =>
  tool.__typename === 'SingleClickOnDiagramElementTool' || tool.__typename === 'SingleClickOnTwoDiagramElementsTool';

export const isToolSection = (entry: PaletteEntry): entry is ToolSection => entry.__typename === 'ToolSection';

export const isPaletteDivider = (entry: PaletteEntry): entry is PaletteDivider => entry.__typename === 'PaletteDivider';

export const DraggablePalette = ({
  x: paletteX,
  y: paletteY,
  onEscape,
  onToolClick,
  paletteEntries,
  lastToolInvoked,
  quickAccessToolComponents,
}: DraggablePaletteProps) => {
  const [searchToolValue, setSearchToolValue] = useState<string>('');
  const { domNode } = useStoreApi().getState();
  const { x: viewportWidth, y: viewportHeight } = computeDraggableBounds(domNode?.getBoundingClientRect());

  const [controlledPosition, setControlledPosition] = useState<XYPosition | null>();

  const { classes } = usePaletteStyle({ paletteWidth: `${paletteWidth}px`, paletteHeight: `${paletteHeight}px` });

  useEffect(() => {
    const paletteLocation: XYPosition = computePaletteLocation(paletteX, paletteY, viewportWidth, viewportHeight);
    setControlledPosition(paletteLocation);
  }, [paletteX, paletteY, viewportWidth, viewportHeight]);

  if (!controlledPosition) {
    return;
  }

  const onPaletteDragStop = (_event, data: DraggableData) => {
    setControlledPosition(data);
  };

  const onSearchFieldValueChanged = (newValue: string): void => {
    setSearchToolValue(newValue);
  };

  const nodeRef = React.createRef<HTMLDivElement>();
  const draggableBounds = {
    left: 0,
    top: 0,
    bottom: viewportHeight - paletteHeight,
    right: viewportWidth - paletteWidth,
  };

  let paletteQuickAccessToolBar: JSX.Element | null = null;
  if (quickAccessToolComponents.length > 0) {
    paletteQuickAccessToolBar = (
      <>
        <Box className={classes.quickAccessTools}>{quickAccessToolComponents}</Box>
        <Divider />
      </>
    );
  }

  return (
    <Draggable
      nodeRef={nodeRef}
      bounds={draggableBounds}
      handle="#tool-palette-header"
      position={controlledPosition}
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
        {paletteQuickAccessToolBar}
        <PaletteSearchField onValueChanged={onSearchFieldValueChanged} onEscape={onEscape} />
        <Divider />
        {searchToolValue.length > 0 ? (
          <PaletteSearchResult
            searchToolValue={searchToolValue}
            paletteEntries={paletteEntries}
            onToolClick={onToolClick}
          />
        ) : (
          <PaletteToolList
            paletteEntries={paletteEntries}
            onToolClick={onToolClick}
            lastToolInvoked={lastToolInvoked}
          />
        )}
      </Paper>
    </Draggable>
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
