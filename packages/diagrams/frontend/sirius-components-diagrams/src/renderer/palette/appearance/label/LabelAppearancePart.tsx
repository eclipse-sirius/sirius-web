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

import FormatBoldIcon from '@mui/icons-material/FormatBold';
import FormatColorResetIcon from '@mui/icons-material/FormatColorReset';
import FormatItalicIcon from '@mui/icons-material/FormatItalic';
import FormatStrikethroughIcon from '@mui/icons-material/FormatStrikethrough';
import FormatUnderlinedIcon from '@mui/icons-material/FormatUnderlined';
import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';
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
import { LabelAppearancePartProps, LabelPartState } from './LabelAppearancePart.types';
import { useEditLabelAppearance } from './useEditLabelAppearance';
import { useResetLabelAppearance } from './useResetLabelAppearance';

export const LabelAppearancePart = ({
  diagramElementId,
  labelId,
  position,
  style,
  customizedStyleProperties,
}: LabelAppearancePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const [state, setState] = useState<LabelPartState>({
    fontSize: style.fontSize,
    borderRadius: style.borderRadius,
    borderColor: style.borderColor,
    borderSize: style.borderSize,
    borderStyle: style.borderStyle,
    color: style.color,
    background: style.background,
  });

  const {
    updateBold,
    updateItalic,
    updateFontSize,
    updateUnderline,
    updateStrikeThrough,
    updateBorderSize,
    updateBorderStyle,
    updateBorderRadius,
    updateBorderColor,
    updateColor,
    updateBackground,
  } = useEditLabelAppearance();
  const { resetLabelStyleProperties } = useResetLabelAppearance();

  const onFontSizeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newFontSize = Number(event.target.value);
    if (!isNaN(newFontSize) && isFinite(newFontSize)) {
      setState((prevState) => ({
        ...prevState,
        fontSize: newFontSize,
      }));
    }
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
    updateBorderStyle(editingContextId, diagramId, diagramElementId, labelId, event.target.value);
    setState((prevState) => ({
      ...prevState,
      borderStyle: event.target.value,
    }));
  };

  const onColorChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setState((prevState) => ({
      ...prevState,
      color: event.target.value,
    }));
  };

  const onBackgroundChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setState((prevState) => ({
      ...prevState,
      background: event.target.value,
    }));
  };

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
        <Typography variant="subtitle2">{position}</Typography>

        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography variant="caption">Font Size</Typography>

          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
            <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
              <TextField
                variant="standard"
                value={state.fontSize}
                onChange={onFontSizeChange}
                onKeyDown={(event) => {
                  if (event.code === 'Enter') {
                    updateFontSize(editingContextId, diagramId, diagramElementId, labelId, state.fontSize);
                  }
                }}
                onBlur={() => updateFontSize(editingContextId, diagramId, diagramElementId, labelId, state.fontSize)}
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() =>
                resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['FONT_SIZE'])
              }
              disabled={!customizedStyleProperties.includes('FONT_SIZE')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>

        <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
          <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
            <FormatItalicIcon />
            <FormGroup aria-label="position" row>
              <FormControlLabel
                value="bottom"
                control={
                  <Checkbox
                    checked={style.italic}
                    onChange={(_, checked) =>
                      updateItalic(editingContextId, diagramId, diagramElementId, labelId, checked)
                    }
                  />
                }
                label="Italic"
                labelPlacement="end"
                sx={{ marginLeft: 0, marginRight: 0 }}
              />
            </FormGroup>
          </Box>
          <IconButton
            aria-label="reset"
            size="small"
            onClick={() =>
              resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['ITALIC'])
            }
            disabled={!customizedStyleProperties.includes('ITALIC')}
            sx={{
              alignSelf: 'center',
              justifySelf: 'center',
            }}>
            <FormatColorResetIcon fontSize="small" />
          </IconButton>
        </Box>

        <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
          <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
            <FormatBoldIcon />
            <FormGroup aria-label="position" row>
              <FormControlLabel
                value="bottom"
                control={
                  <Checkbox
                    checked={style.bold}
                    onChange={(_, checked) =>
                      updateBold(editingContextId, diagramId, diagramElementId, labelId, checked)
                    }
                  />
                }
                label="Bold"
                labelPlacement="end"
                sx={{ marginLeft: 0, marginRight: 0 }}
              />
            </FormGroup>
          </Box>
          <IconButton
            aria-label="reset"
            size="small"
            onClick={() => resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BOLD'])}
            disabled={!customizedStyleProperties.includes('BOLD')}
            sx={{
              alignSelf: 'center',
              justifySelf: 'center',
            }}>
            <FormatColorResetIcon fontSize="small" />
          </IconButton>
        </Box>
        <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
          <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
            <FormatUnderlinedIcon />
            <FormGroup aria-label="position" row>
              <FormControlLabel
                value="bottom"
                control={
                  <Checkbox
                    checked={style.underline}
                    onChange={(_, checked) =>
                      updateUnderline(editingContextId, diagramId, diagramElementId, labelId, checked)
                    }
                  />
                }
                label="Underline"
                labelPlacement="end"
                sx={{ marginLeft: 0, marginRight: 0 }}
              />
            </FormGroup>
          </Box>
          <IconButton
            aria-label="reset"
            size="small"
            onClick={() =>
              resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['UNDERLINE'])
            }
            disabled={!customizedStyleProperties.includes('UNDERLINE')}
            sx={{
              alignSelf: 'center',
              justifySelf: 'center',
            }}>
            <FormatColorResetIcon fontSize="small" />
          </IconButton>
        </Box>

        <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
          <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
            <FormatStrikethroughIcon />
            <FormGroup aria-label="position" row>
              <FormControlLabel
                value="bottom"
                control={
                  <Checkbox
                    checked={style.strikeThrough}
                    onChange={(_, checked) =>
                      updateStrikeThrough(editingContextId, diagramId, diagramElementId, labelId, checked)
                    }
                  />
                }
                label="Strike Through"
                labelPlacement="end"
                sx={{ marginLeft: 0, marginRight: 0 }}
              />
            </FormGroup>
          </Box>
          <IconButton
            aria-label="reset"
            size="small"
            onClick={() =>
              resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['STRIKE_THROUGH'])
            }
            disabled={!customizedStyleProperties.includes('STRIKE_THROUGH')}
            sx={{
              alignSelf: 'center',
              justifySelf: 'center',
            }}>
            <FormatColorResetIcon fontSize="small" />
          </IconButton>
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
                    updateBorderColor(editingContextId, diagramId, diagramElementId, labelId, state.borderColor);
                  }
                }}
                onBlur={() =>
                  updateBorderColor(editingContextId, diagramId, diagramElementId, labelId, state.borderColor)
                }
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() =>
                resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BORDER_COLOR'])
              }
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
                    updateBorderRadius(editingContextId, diagramId, diagramElementId, labelId, state.borderRadius);
                  }
                }}
                onBlur={() =>
                  updateBorderRadius(editingContextId, diagramId, diagramElementId, labelId, state.borderRadius)
                }
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() =>
                resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BORDER_RADIUS'])
              }
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
                    updateBorderSize(editingContextId, diagramId, diagramElementId, labelId, state.borderSize);
                  }
                }}
                onBlur={() =>
                  updateBorderSize(editingContextId, diagramId, diagramElementId, labelId, state.borderSize)
                }
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() =>
                resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BORDER_SIZE'])
              }
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
              onClick={() =>
                resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BORDER_STYLE'])
              }
              disabled={!customizedStyleProperties.includes('BORDER_STYLE')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>

        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography variant="caption">Label Color</Typography>

          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
            <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
              <Color value={state.color} />
              <TextField
                variant="standard"
                value={state.color}
                onChange={onColorChange}
                onKeyDown={(event) => {
                  if (event.code === 'Enter') {
                    updateColor(editingContextId, diagramId, diagramElementId, labelId, state.color);
                  }
                }}
                onBlur={() => updateColor(editingContextId, diagramId, diagramElementId, labelId, state.color)}
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() =>
                resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['COLOR'])
              }
              disabled={!customizedStyleProperties.includes('COLOR')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>

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
                    updateBackground(editingContextId, diagramId, diagramElementId, labelId, state.background);
                  }
                }}
                onBlur={() =>
                  updateBackground(editingContextId, diagramId, diagramElementId, labelId, state.background)
                }
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() =>
                resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BACKGROUND'])
              }
              disabled={!customizedStyleProperties.includes('BACKGROUND')}
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
