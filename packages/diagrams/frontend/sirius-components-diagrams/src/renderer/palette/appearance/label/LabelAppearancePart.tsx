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
import LineStyleIcon from '@mui/icons-material/LineStyle';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import Typography from '@mui/material/Typography';
import { useContext } from 'react';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { CheckboxAppearanceProperty } from '../property-component/CheckboxAppearanceProperty';
import { AppearanceColorPicker } from '../widget/AppearanceColorPicker';
import { AppearanceNumberTextfield } from '../widget/AppearanceNumberTextfield ';
import { AppearanceSelect } from '../widget/AppearanceSelect';
import { LabelAppearancePartProps } from './LabelAppearancePart.types';
import { useEditLabelAppearance } from './useEditLabelAppearance';
import { GQLLabelAppearanceInput } from './useEditLabelAppearance.types';
import { useResetLabelAppearance } from './useResetLabelAppearance';

const LINE_STYLE_OPTIONS = [
  { value: 'Solid', label: 'Solid' },
  { value: 'Dash', label: 'Dash' },
  { value: 'Dot', label: 'Dot' },
  { value: 'Dash_Dot', label: 'Dash Dot' },
];

export const LabelAppearancePart = ({
  diagramElementId,
  labelId,
  position,
  style,
  customizedStyleProperties,
}: LabelAppearancePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const { updateLabelAppearance } = useEditLabelAppearance();
  const { resetLabelStyleProperties } = useResetLabelAppearance();

  const handleResetProperty = (customizedStyleProperty: string) =>
    resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, [customizedStyleProperty]);

  const handleEditProperty = (newValue: Partial<GQLLabelAppearanceInput>) =>
    updateLabelAppearance(editingContextId, diagramId, diagramElementId, labelId, newValue);

  const isDisabled = (property: string) => !customizedStyleProperties.includes(property);

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
        <Typography variant="subtitle2">{position}</Typography>

        <AppearanceNumberTextfield
          icon={<FormatSizeIcon />}
          label={'Font Size'}
          initialValue={style.fontSize}
          isDisabled={isDisabled('FONT_SIZE')}
          onEdit={(newValue) => handleEditProperty({ fontSize: newValue })}
          onReset={() => handleResetProperty('FONT_SIZE')}></AppearanceNumberTextfield>

        <CheckboxAppearanceProperty
          icon={<FormatItalicIcon />}
          label="Italic"
          checked={style.italic}
          isDisabled={isDisabled('ITALIC')}
          onChange={(checked) => handleEditProperty({ italic: checked })}
          onReset={() => handleResetProperty('ITALIC')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatBoldIcon />}
          label="Bold"
          checked={style.bold}
          isDisabled={isDisabled('BOLD')}
          onChange={(checked) => handleEditProperty({ bold: checked })}
          onReset={() => handleResetProperty('BOLD')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatUnderlinedIcon />}
          label="Underline"
          checked={style.underline}
          isDisabled={isDisabled('UNDERLINE')}
          onChange={(checked) => handleEditProperty({ underline: checked })}
          onReset={() => handleResetProperty('UNDERLINE')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatStrikethroughIcon />}
          label="Strike Through"
          checked={style.strikeThrough}
          isDisabled={isDisabled('STRIKE_THROUGH')}
          onChange={(checked) => handleEditProperty({ strikeThrough: checked })}
          onReset={() => handleResetProperty('STRIKE_THROUGH')}
        />

        <AppearanceColorPicker
          label={'Border Color'}
          initialValue={style.borderColor}
          isDisabled={isDisabled('BORDER_COLOR')}
          onEdit={(newValue) => handleEditProperty({ borderColor: newValue })}
          onReset={() => handleResetProperty('BORDER_COLOR')}></AppearanceColorPicker>

        <AppearanceNumberTextfield
          icon={<Crop32Icon />}
          label={'Border Radius'}
          initialValue={style.borderRadius}
          isDisabled={isDisabled('BORDER_RADIUS')}
          onEdit={(newValue) => handleEditProperty({ borderRadius: newValue })}
          onReset={() => handleResetProperty('BORDER_RADIUS')}></AppearanceNumberTextfield>

        <AppearanceNumberTextfield
          icon={<Crop32Icon />}
          label={'Border Size'}
          initialValue={style.borderSize}
          isDisabled={isDisabled('BORDER_SIZE')}
          onEdit={(newValue) => handleEditProperty({ borderSize: newValue })}
          onReset={() => handleResetProperty('BORDER_SIZE')}></AppearanceNumberTextfield>

        <AppearanceSelect
          icon={<LineStyleIcon />}
          label={'Border Line Style'}
          options={LINE_STYLE_OPTIONS}
          initialValue={style.borderStyle}
          isDisabled={isDisabled('BORDER_STYLE')}
          onEdit={(newValue) => handleEditProperty({ borderStyle: newValue })}
          onReset={() => handleResetProperty('BORDER_STYLE')}></AppearanceSelect>

        <AppearanceColorPicker
          label={'Label Color'}
          initialValue={style.color}
          isDisabled={isDisabled('COLOR')}
          onEdit={(newValue) => handleEditProperty({ color: newValue })}
          onReset={() => handleResetProperty('COLOR')}></AppearanceColorPicker>

        <AppearanceColorPicker
          label={'Background'}
          initialValue={style.background}
          isDisabled={isDisabled('BACKGROUND')}
          onEdit={(newValue) => handleEditProperty({ background: newValue })}
          onReset={() => handleResetProperty('BACKGROUND')}></AppearanceColorPicker>
      </Box>
    </ListItem>
  );
};
