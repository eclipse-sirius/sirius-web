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

import Crop32Icon from '@mui/icons-material/Crop32';
import FormatBoldIcon from '@mui/icons-material/FormatBold';
import FormatItalicIcon from '@mui/icons-material/FormatItalic';
import FormatSizeIcon from '@mui/icons-material/FormatSize';
import FormatStrikethroughIcon from '@mui/icons-material/FormatStrikethrough';
import FormatUnderlinedIcon from '@mui/icons-material/FormatUnderlined';
import LineWeightIcon from '@mui/icons-material/LineWeight';
import LineStyleIcon from '@mui/icons-material/LineStyle';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import { SelectChangeEvent } from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { Color } from '../Color';
import { LabelAppearancePartProps, LabelPartState } from './LabelAppearancePart.types';
import { useEditLabelAppearance } from './useEditLabelAppearance';
import { useResetLabelAppearance } from './useResetLabelAppearance';
import { TextFieldAppearanceProperty } from '../property-component/TextFieldAppearanceProperty';
import { CheckboxAppearanceProperty } from '../property-component/CheckboxAppearanceProperty';
import { SelectAppearanceProperty } from '../property-component/SelectAppearanceProperty';

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

  const { updateLabelAppearance } = useEditLabelAppearance();
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

  const onBorderRadiusChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newBorderRadius = Number(event.target.value);
    if (!isNaN(newBorderRadius) && isFinite(newBorderRadius)) {
      setState((prevState) => ({
        ...prevState,
        borderRadius: newBorderRadius,
      }));
    }
  };

  const onBorderSizeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newBorderSize = Number(event.target.value);
    if (!isNaN(newBorderSize) && isFinite(newBorderSize)) {
      setState((prevState) => ({
        ...prevState,
        borderSize: newBorderSize,
      }));
    }
  };

  const onBorderStyleChange = (event: SelectChangeEvent<string>) => {
    updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, { borderStyle: event.target.value });
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

        <TextFieldAppearanceProperty
          icon={<FormatSizeIcon />}
          label="Font Size"
          value={state.fontSize}
          onChange={onFontSizeChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
                fontSize: state.fontSize,
              });
            }
          }}
          onBlur={() =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, { fontSize: state.fontSize })
          }
          onReset={() =>
            resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['FONT_SIZE'])
          }
          isDisabled={!customizedStyleProperties.includes('FONT_SIZE')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatItalicIcon />}
          label="Italic"
          checked={style.italic}
          onChange={(checked) =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, { italic: checked })
          }
          onReset={() => resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['ITALIC'])}
          isDisabled={!customizedStyleProperties.includes('ITALIC')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatBoldIcon />}
          label="Bold"
          checked={style.bold}
          onChange={(checked) =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, { bold: checked })
          }
          onReset={() => resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BOLD'])}
          isDisabled={!customizedStyleProperties.includes('BOLD')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatUnderlinedIcon />}
          label="Underline"
          checked={style.underline}
          onChange={(checked) =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, { underline: checked })
          }
          onReset={() =>
            resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['UNDERLINE'])
          }
          isDisabled={!customizedStyleProperties.includes('UNDERLINE')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatStrikethroughIcon />}
          label="Strike Through"
          checked={style.strikeThrough}
          onChange={(checked) =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, { strikeThrough: checked })
          }
          onReset={() =>
            resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['STRIKE_THROUGH'])
          }
          isDisabled={!customizedStyleProperties.includes('STRIKE_THROUGH')}
        />
        <TextFieldAppearanceProperty
          icon={<Color value={state.borderColor} />}
          label="Border Color"
          value={state.borderColor}
          onChange={onBorderColorChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
                borderColor: state.borderColor,
              });
            }
          }}
          onBlur={() =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
              borderColor: state.borderColor,
            })
          }
          onReset={() =>
            resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BORDER_COLOR'])
          }
          isDisabled={!customizedStyleProperties.includes('BORDER_COLOR')}
        />
        <TextFieldAppearanceProperty
          icon={<Crop32Icon />}
          label="Border Radius"
          value={state.borderRadius}
          onChange={onBorderRadiusChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
                borderRadius: state.borderRadius,
              });
            }
          }}
          onBlur={() =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
              borderRadius: state.borderRadius,
            })
          }
          onReset={() =>
            resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BORDER_RADIUS'])
          }
          isDisabled={!customizedStyleProperties.includes('BORDER_RADIUS')}
        />
        <TextFieldAppearanceProperty
          icon={<LineWeightIcon />}
          label="Border Size"
          value={state.borderSize}
          onChange={onBorderSizeChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
                borderSize: state.borderSize,
              });
            }
          }}
          onBlur={() =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
              borderSize: state.borderSize,
            })
          }
          onReset={() =>
            resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BORDER_SIZE'])
          }
          isDisabled={!customizedStyleProperties.includes('BORDER_SIZE')}
        />
        <SelectAppearanceProperty
          icon={<LineStyleIcon />}
          label="Border Line Style"
          value={state.borderStyle}
          onChange={onBorderStyleChange}
          options={[
            { value: 'Solid', label: 'Solid' },
            { value: 'Dash', label: 'Dash' },
            { value: 'Dot', label: 'Dot' },
            { value: 'Dash_Dot', label: 'Dash Dot' },
          ]}
          onReset={() =>
            resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BORDER_STYLE'])
          }
          isDisabled={!customizedStyleProperties.includes('BORDER_STYLE')}
        />
        <TextFieldAppearanceProperty
          icon={<Color value={state.color} />}
          label="Label Color"
          value={state.color}
          onChange={onColorChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, { color: state.color });
            }
          }}
          onBlur={() =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, { color: state.color })
          }
          onReset={() => resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['COLOR'])}
          isDisabled={!customizedStyleProperties.includes('COLOR')}
        />
        <TextFieldAppearanceProperty
          icon={<Color value={state.background} />}
          label="Background"
          value={state.background}
          onChange={onBackgroundChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
                background: state.background,
              });
            }
          }}
          onBlur={() =>
            updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, {
              background: state.background,
            })
          }
          onReset={() =>
            resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BACKGROUND'])
          }
          isDisabled={!customizedStyleProperties.includes('BACKGROUND')}
        />
      </Box>
    </ListItem>
  );
};
