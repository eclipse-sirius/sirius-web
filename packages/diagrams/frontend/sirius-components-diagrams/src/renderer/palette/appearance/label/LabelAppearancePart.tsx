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
import DisabledVisibleIcon from '@mui/icons-material/DisabledVisible';
import FormatBoldIcon from '@mui/icons-material/FormatBold';
import FormatItalicIcon from '@mui/icons-material/FormatItalic';
import FormatSizeIcon from '@mui/icons-material/FormatSize';
import FormatStrikethroughIcon from '@mui/icons-material/FormatStrikethrough';
import FormatUnderlinedIcon from '@mui/icons-material/FormatUnderlined';
import LineStyleIcon from '@mui/icons-material/LineStyle';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import Typography from '@mui/material/Typography';
import { TFunction } from 'i18next/typescript/t';
import { useContext, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
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

const getLineStyleOptions = (t: TFunction) => [
  { value: 'Solid', label: t('solid') },
  { value: 'Dash', label: t('dash') },
  { value: 'Dot', label: t('dot') },
  { value: 'Dash_Dot', label: t('dashDot') },
];

export const LabelAppearancePart = ({
  diagramElementIds,
  labelIds,
  position,
  style,
  customizedStyleProperties,
}: LabelAppearancePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const { updateLabelAppearance } = useEditLabelAppearance();
  const { resetLabelStyleProperties } = useResetLabelAppearance();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'labelAppearancePart' });
  const lineStyleOptions = useMemo(() => getLineStyleOptions(t), [t]);

  const handleResetProperty = (customizedStyleProperty: string) =>
    resetLabelStyleProperties(editingContextId, diagramId, diagramElementIds, labelIds, [customizedStyleProperty]);

  const handleEditProperty = (newValue: Partial<GQLLabelAppearanceInput>) =>
    updateLabelAppearance(editingContextId, diagramId, diagramElementIds, labelIds, newValue);

  const isDisabled = (property: string) => !customizedStyleProperties.includes(property);

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
        <Typography variant="subtitle2">{position}</Typography>

        <CheckboxAppearanceProperty
          icon={<DisabledVisibleIcon />}
          label={t('hide')}
          checked={style.visibility === 'hidden'}
          disabled={isDisabled('VISIBILITY')}
          onChange={(checked) => handleEditProperty({ visibility: checked ? 'hidden' : 'visible' })}
          onReset={() => handleResetProperty('VISIBILITY')}
        />

        <AppearanceNumberTextfield
          icon={<FormatSizeIcon />}
          label={t('fontSize')}
          initialValue={style.fontSize}
          disabled={isDisabled('FONT_SIZE')}
          onEdit={(newValue) => handleEditProperty({ fontSize: newValue })}
          onReset={() => handleResetProperty('FONT_SIZE')}></AppearanceNumberTextfield>

        <CheckboxAppearanceProperty
          icon={<FormatItalicIcon />}
          label={t('italic')}
          checked={style.italic}
          disabled={isDisabled('ITALIC')}
          onChange={(checked) => handleEditProperty({ italic: checked })}
          onReset={() => handleResetProperty('ITALIC')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatBoldIcon />}
          label={t('bold')}
          checked={style.bold}
          disabled={isDisabled('BOLD')}
          onChange={(checked) => handleEditProperty({ bold: checked })}
          onReset={() => handleResetProperty('BOLD')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatUnderlinedIcon />}
          label={t('underline')}
          checked={style.underline}
          disabled={isDisabled('UNDERLINE')}
          onChange={(checked) => handleEditProperty({ underline: checked })}
          onReset={() => handleResetProperty('UNDERLINE')}
        />
        <CheckboxAppearanceProperty
          icon={<FormatStrikethroughIcon />}
          label={t('strikeThrough')}
          checked={style.strikeThrough}
          disabled={isDisabled('STRIKE_THROUGH')}
          onChange={(checked) => handleEditProperty({ strikeThrough: checked })}
          onReset={() => handleResetProperty('STRIKE_THROUGH')}
        />

        <AppearanceColorPicker
          label={t('borderColor')}
          initialValue={style.borderColor}
          disabled={isDisabled('BORDER_COLOR')}
          onEdit={(newValue) => handleEditProperty({ borderColor: newValue })}
          onReset={() => handleResetProperty('BORDER_COLOR')}></AppearanceColorPicker>

        <AppearanceNumberTextfield
          icon={<Crop32Icon />}
          label={t('borderRadius')}
          initialValue={style.borderRadius}
          disabled={isDisabled('BORDER_RADIUS')}
          onEdit={(newValue) => handleEditProperty({ borderRadius: newValue })}
          onReset={() => handleResetProperty('BORDER_RADIUS')}></AppearanceNumberTextfield>

        <AppearanceNumberTextfield
          icon={<Crop32Icon />}
          label={t('borderSize')}
          initialValue={style.borderSize}
          disabled={isDisabled('BORDER_SIZE')}
          onEdit={(newValue) => handleEditProperty({ borderSize: newValue })}
          onReset={() => handleResetProperty('BORDER_SIZE')}></AppearanceNumberTextfield>

        <AppearanceSelect
          icon={<LineStyleIcon />}
          label={t('borderLineStyle')}
          options={lineStyleOptions}
          initialValue={style.borderStyle}
          disabled={isDisabled('BORDER_STYLE')}
          onEdit={(newValue) => handleEditProperty({ borderStyle: newValue })}
          onReset={() => handleResetProperty('BORDER_STYLE')}></AppearanceSelect>

        <AppearanceColorPicker
          label={t('labelColor')}
          initialValue={style.color}
          disabled={isDisabled('COLOR')}
          onEdit={(newValue) => handleEditProperty({ color: newValue })}
          onReset={() => handleResetProperty('COLOR')}></AppearanceColorPicker>

        <AppearanceColorPicker
          label={t('background')}
          initialValue={style.background}
          disabled={isDisabled('BACKGROUND')}
          onEdit={(newValue) => handleEditProperty({ background: newValue })}
          onReset={() => handleResetProperty('BACKGROUND')}></AppearanceColorPicker>
      </Box>
    </ListItem>
  );
};
