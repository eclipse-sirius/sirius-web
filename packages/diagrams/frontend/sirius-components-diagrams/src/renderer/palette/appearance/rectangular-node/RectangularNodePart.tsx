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
import FormatColorResetIcon from '@mui/icons-material/FormatColorReset';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import ListItem from '@mui/material/ListItem';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { Color } from '../Color';
import { RectangularNodePartProps, RectangularNodePartState } from './RectangularNodePart.types';
import { useResetNodeAppearance } from './useResetNodeAppearance';
import { useUpdateRectangularNodeAppearance } from './useUpdateRectangularNodeAppearance';

export const RectangularNodePart = ({ nodeId, style, customizedStyleProperties }: RectangularNodePartProps) => {
  const [state, setState] = useState<RectangularNodePartState>({
    background: style.background,
    borderColor: style.borderColor,
    borderRadius: style.borderRadius,
    borderSize: style.borderSize,
    borderStyle: style.borderStyle,
  });
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { updateBackground, updateBorderColor, updateBorderRadius, updateBorderSize, updateBorderStyle } =
    useUpdateRectangularNodeAppearance();
  const { resetNodeStyleProperties } = useResetNodeAppearance();

  const onBackgroundChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setState((prevState) => ({
      ...prevState,
      background: event.target.value,
    }));
  };

  const onBorderColorChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setState((prevState) => ({
      ...prevState,
      borderColor: event.target.value,
    }));
  };

  const onBordeRadiusChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newBorderRadius = Number(event.target.value);
    if (!isNaN(newBorderRadius) && isFinite(newBorderRadius)) {
      setState((prevState) => ({
        ...prevState,
        borderRadius: newBorderRadius,
      }));
    }
  };

  const onBordeSizeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newBorderSize = Number(event.target.value);
    if (!isNaN(newBorderSize) && isFinite(newBorderSize)) {
      setState((prevState) => ({
        ...prevState,
        borderSize: newBorderSize,
      }));
    }
  };

  const onBordeStyleChange = (event: SelectChangeEvent<string>) => {
    updateBorderStyle(editingContextId, diagramId, nodeId, event.target.value);
    setState((prevState) => ({
      ...prevState,
      borderStyle: event.target.value,
    }));
  };

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1), paddingBottom: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column' }}>
        <Typography variant="subtitle2">Style</Typography>

        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography variant="caption">Background</Typography>

          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
            <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
              <Color value={state.background} />
              <TextField
                variant="standard"
                value={state.background}
                onChange={onBackgroundChange}
                onKeyDown={(event) => {
                  if (event.code === 'Enter') {
                    updateBackground(editingContextId, diagramId, nodeId, state.background);
                  }
                }}
                onBlur={() => updateBackground(editingContextId, diagramId, nodeId, state.background)}
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BACKGROUND'])}
              disabled={!customizedStyleProperties.includes('BACKGROUND')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>

        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography variant="caption">Border Color</Typography>

          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
            <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
              <Color value={state.borderColor} />
              <TextField
                variant="standard"
                value={state.borderColor}
                onChange={onBorderColorChange}
                onKeyDown={(event) => {
                  if (event.code === 'Enter') {
                    updateBorderColor(editingContextId, diagramId, nodeId, state.borderColor);
                  }
                }}
                onBlur={() => updateBorderColor(editingContextId, diagramId, nodeId, state.borderColor)}
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BORDER_COLOR'])}
              disabled={!customizedStyleProperties.includes('BORDER_COLOR')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>

        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography variant="caption">Border Radius</Typography>

          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
            <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
              <TextField
                variant="standard"
                value={state.borderRadius}
                onChange={onBordeRadiusChange}
                onKeyDown={(event) => {
                  if (event.code === 'Enter') {
                    updateBorderRadius(editingContextId, diagramId, nodeId, state.borderRadius);
                  }
                }}
                onBlur={() => updateBorderRadius(editingContextId, diagramId, nodeId, state.borderRadius)}
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BORDER_RADIUS'])}
              disabled={!customizedStyleProperties.includes('BORDER_RADIUS')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>

        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography variant="caption">Border Size</Typography>

          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
            <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
              <TextField
                variant="standard"
                value={state.borderSize}
                onChange={onBordeSizeChange}
                onKeyDown={(event) => {
                  if (event.code === 'Enter') {
                    updateBorderSize(editingContextId, diagramId, nodeId, state.borderSize);
                  }
                }}
                onBlur={() => updateBorderSize(editingContextId, diagramId, nodeId, state.borderSize)}
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BORDER_SIZE'])}
              disabled={!customizedStyleProperties.includes('BORDER_SIZE')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>

        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography variant="caption">Border Line Style</Typography>

          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
            <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
              <Select
                id="border-style-select"
                value={state.borderStyle}
                onChange={onBordeStyleChange}
                sx={{ width: '100%' }}>
                <MenuItem value="Solid">Solid</MenuItem>
                <MenuItem value="Dash">Dash</MenuItem>
                <MenuItem value="Dot">Dot</MenuItem>
                <MenuItem value="Dash_Dot">Dash Dot</MenuItem>
              </Select>
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BORDER_STYLE'])}
              disabled={!customizedStyleProperties.includes('BORDER_STYLE')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>
      </Box>
    </ListItem>
  );
};
