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
import { useTranslation } from 'react-i18next';
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
import { PaletteSearchField } from './search/PaletteSearchField';
import { PaletteSearchResult } from './search/PaletteSearchResult';
import { PaletteToolSection } from './tool-section/PaletteToolSection';
import { usePalette } from './usePalette';

export const isSingleClickOnDiagramElementTool = (tool: GQLPaletteEntry): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

export const isToolSection = (entry: GQLPaletteEntry): entry is GQLToolSection => entry.__typename === 'ToolSection';

export const isPaletteDivider = (entry: GQLPaletteDivider): entry is GQLToolSection =>
  entry.__typename === 'PaletteDivider';

export const isTool = (entry: GQLPaletteEntry): entry is GQLTool => !isPaletteDivider(entry) && !isToolSection(entry);

const paletteWidth = 250;

/**
 * The palette component forward both the ref and props
 * This is mandatory for wrapping the palette in a Draggable component from react-draggable
 * Props like className, onMouse{Down|Up}, onTouchEnd, style and transform are used by the Draggable component
 */
export const Palette = React.forwardRef<HTMLDivElement, PaletteProps & React.HTMLAttributes<HTMLDivElement>>(
  (
    {
      representationKind,
      representationElementIds,
      onClose,
      onToolClick,
      palette,
      paletteToolListExtensions,
      ...remainingProps
    },
    ref
  ) => {
    const nodeRef = React.useRef<HTMLDivElement>(null);
    const theme: Theme = useTheme();

    const { t } = useTranslation('sirius-components-palette', { keyPrefix: 'palette' });

    const [state, setState] = useState<PaletteState>({
      searchToolValue: '',
    });

    const { setLastToolInvokedId, getLastToolInvokedId } = usePalette();

    const lastToolInvokedId = palette ? getLastToolInvokedId(palette.id) : null;

    const handleToolClick = (tool: GQLTool) => {
      onClose();
      onToolClick(tool);
      if (palette) {
        setLastToolInvokedId(palette.id, tool.id);
      }
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
      <Paper
        {...remainingProps}
        ref={ref}
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
              representationKind={representationKind}
              representationElementIds={representationElementIds}
              onToolClick={handleToolClick}
              quickAccessTools={palette.quickAccessTools}
            />
            <PaletteSearchField onValueChanged={onSearchFieldValueChanged} />
            {state.searchToolValue.length > 0 ? (
              <PaletteSearchResult
                searchToolValue={state.searchToolValue}
                palette={palette}
                onToolClick={handleToolClick}
              />
            ) : (
              <PaletteToolSection
                representationKind={representationKind}
                representationElementIds={representationElementIds}
                palette={palette}
                onToolClick={handleToolClick}
                onBackToMainList={handleBackToMainList}
                onClose={onClose}
                lastToolInvokedId={lastToolInvokedId}
                extensionSections={paletteToolListExtensions}
              />
            )}
          </Box>
        </ClickAwayListener>
      </Paper>
    );
  }
);
