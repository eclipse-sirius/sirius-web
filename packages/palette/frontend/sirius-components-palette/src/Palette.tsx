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

import { GQLPaletteDivider, GQLPaletteEntry, GQLTool, GQLToolSection, PaletteState } from './Palette.types';

import CloseIcon from '@mui/icons-material/Close';
import DragIndicatorIcon from '@mui/icons-material/DragIndicator';
import Box from '@mui/material/Box';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import { Theme, useTheme } from '@mui/material/styles';
import Tooltip from '@mui/material/Tooltip';
import { forwardRef, useState } from 'react';
import { PaletteProps } from './Palette.types';
import { PaletteQuickAccessToolBar } from './quick-access-tool/PaletteQuickAccessToolBar';
import { PaletteSearchField } from './search/PaletteSearchField';
import { PaletteSearchResult } from './search/PaletteSearchResult';
import { PaletteToolList } from './tool-list/PaletteToolList';

export const isToolSection = (entry: GQLPaletteEntry): entry is GQLToolSection => entry.__typename === 'ToolSection';

export const isPaletteDivider = (entry: GQLPaletteDivider): entry is GQLToolSection =>
  entry.__typename === 'PaletteDivider';

export const isTool = (entry: GQLPaletteEntry): entry is GQLTool => !isPaletteDivider(entry) && !isToolSection(entry);

const paletteWidth = 200;

export const Palette = forwardRef<HTMLDivElement, PaletteProps>(
  (
    {
      palette,
      paletteXYPosition,
      representationElementId,
      lastToolInvoked,
      onToolClick,
      onDirectEditClick,
      onClose,
      extensions,
      className,
      style,
      onMouseDown,
      onMouseUp,
    },
    nodeRef
  ) => {
    const theme: Theme = useTheme();

    const [state, setState] = useState<PaletteState>({
      searchToolValue: '',
    });

    const handleToolClick = (tool: GQLTool) => {
      onToolClick(paletteXYPosition, representationElementId, onDirectEditClick, tool);
    };

    const onSearchFieldValueChanged = (newValue: string): void => {
      setState((prevState) => ({ ...prevState, searchToolValue: newValue }));
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
        }}
        className={className}
        style={style}
        onMouseDown={onMouseDown}
        onMouseUp={onMouseUp}>
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
              representationElementId={representationElementId}
              onToolClick={handleToolClick}
              quickAccessTools={palette.quickAccessTools}
              x={paletteXYPosition.x}
              y={paletteXYPosition.y}
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
                lastToolInvoked={lastToolInvoked}
                representationElementId={representationElementId}>
                {extensions}
              </PaletteToolList>
            )}
          </Box>
        </ClickAwayListener>
      </Paper>
    );
  }
);
