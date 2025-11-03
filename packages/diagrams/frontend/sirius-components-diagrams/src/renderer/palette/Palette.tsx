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
import { Edge, Node, useStoreApi } from '@xyflow/react';
import React, { useEffect, useState } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { useTranslation } from 'react-i18next';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useGetUpdatedModalPosition } from '../hooks/useGetUpdatedModalPosition';
import {
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

export const isSingleClickOnDiagramElementTool = (tool: GQLPaletteEntry): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

export const isToolSection = (entry: GQLPaletteEntry): entry is GQLToolSection => entry.__typename === 'ToolSection';

export const isPaletteDivider = (entry: GQLPaletteDivider): entry is GQLToolSection =>
  entry.__typename === 'PaletteDivider';

export const isTool = (entry: GQLPaletteEntry): entry is GQLTool => !isPaletteDivider(entry) && !isToolSection(entry);

const paletteWidth = 200;

export const Palette = ({
  x: paletteX,
  y: paletteY,
  diagramElementIds,
  palette,
  onToolClick,
  onClose,
  paletteToolListExtensions,
}: PaletteProps) => {
  const { domNode } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { getUpdatedModalPosition, getUpdatedBounds } = useGetUpdatedModalPosition();
  const nodeRef = React.useRef<HTMLDivElement>(null);
  const theme: Theme = useTheme();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'palette' });
  const [state, setState] = useState<PaletteState>({
    searchToolValue: '',
    controlledPosition: { x: paletteX, y: paletteY },
  });

  useEffect(() => {
    setState((prevState) => {
      return { ...prevState, controlledPosition: { x: paletteX, y: paletteY } };
    });
  }, [paletteX, paletteY]);

  const { setLastToolInvoked, getLastToolInvoked } = useDiagramPalette();

  const lastToolInvoked = palette ? getLastToolInvoked(palette.id) : null;

  const handleToolClick = (tool: GQLTool) => {
    onClose();
    domNode?.focus();
    onToolClick(tool);
    if (palette) {
      setLastToolInvoked(palette.id, tool);
    }
    const position = getUpdatedModalPosition({ x: state.controlledPosition.x, y: state.controlledPosition.y }, nodeRef);
    setState((prevState) => {
      return { ...prevState, controlledPosition: position };
    });
  };

  const updatedModalPosition = () => {
    setState((prevState) => {
      return {
        ...prevState,
        controlledPosition: getUpdatedModalPosition(
          { x: prevState.controlledPosition.x, y: prevState.controlledPosition.y },
          nodeRef
        ),
      };
    });
  };

  useEffect(() => {
    if (!nodeRef.current) return;
    // If the palette size changes when opening a section then update the position of the modal
    const resizeObserver = new ResizeObserver(() => {
      updatedModalPosition();
    });
    resizeObserver.observe(nodeRef.current);
    updatedModalPosition();
    return () => resizeObserver.disconnect();
  }, [paletteX, paletteY]);

  const draggableBounds = getUpdatedBounds(nodeRef);

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

              <Tooltip title={t('close')}>
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
              diagramElementIds={diagramElementIds}
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
                onClose={onClose}
                lastToolInvoked={lastToolInvoked}
                diagramElementIds={diagramElementIds}>
                {paletteToolListExtensions}
              </PaletteToolList>
            )}
          </Box>
        </ClickAwayListener>
      </Paper>
    </Draggable>
  );
};
