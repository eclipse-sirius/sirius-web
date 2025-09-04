/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import React, { useState } from 'react';
import { DEFAULT_TOOL_LIST_ITEMS } from './toolListItems/DefaultToolListItems';
import { GQLPalette, GQLTool, PaletteProps, PaletteState } from './TreeItemPalette.types';
import { useInvokePaletteTool } from './useInvokePaletteTool';
const paletteWidth = 200;

const palette: GQLPalette = {
  id: '',
  quickAccessTools: [],
  paletteEntries: DEFAULT_TOOL_LIST_ITEMS,
};

export const TreeItemPalette = ({
  editingContextId,
  treeId,
  treeItem,
  onDirectEditClick,
  onClose,
  children,
}: PaletteProps) => {
  const nodeRef = React.useRef<HTMLDivElement>(null);
  const theme: Theme = useTheme();

  const [state, setState] = useState<PaletteState>({
    searchToolValue: '',
  });

  const { invokeTool } = useInvokePaletteTool();
  const lastToolInvoked = null;

  const onSearchFieldValueChanged = (newValue: string): void => {
    setState((prevState) => ({ ...prevState, searchToolValue: newValue }));
  };

  const handleBackToMainList = () => {
    if (nodeRef.current) {
      nodeRef.current.focus();
    }
  };

  const handleToolClick = (tool: GQLTool) => {
    invokeTool(editingContextId, treeId, treeItem, onDirectEditClick, tool);
  };

  return (
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
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'stretch' }}>
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
              <IconButton size="small" aria-label="close" color="inherit" data-testid="Close-palette" onClick={onClose}>
                <CloseIcon fontSize="small" />
              </IconButton>
            </Tooltip>
          </Box>
          <Divider />
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
              diagramElementId={treeId}>
              {children}
            </PaletteToolList>
          )}
        </Box>
      </ClickAwayListener>
    </Paper>
  );
};
