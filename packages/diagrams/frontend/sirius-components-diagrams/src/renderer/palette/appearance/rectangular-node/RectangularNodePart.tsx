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
import LineStyleIcon from '@mui/icons-material/LineStyle';
import LineWeightIcon from '@mui/icons-material/LineWeight';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import Typography from '@mui/material/Typography';
import { TFunction } from 'i18next/typescript/t';
import { useContext, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { useResetNodeAppearance } from '../useResetNodeAppearance';
import { AppearanceColorPicker } from '../widget/AppearanceColorPicker';
import { AppearanceNumberTextfield } from '../widget/AppearanceNumberTextfield ';
import { AppearanceSelect } from '../widget/AppearanceSelect';
import { RectangularNodePartProps } from './RectangularNodePart.types';
import { useUpdateRectangularNodeAppearance } from './useUpdateRectangularNodeAppearance';
import { GQLRectangularNodeAppearanceInput } from './useUpdateRectangularNodeAppearance.types';

const getLineStyleOptions = (t: TFunction) => [
  { value: 'Solid', label: t('solid') },
  { value: 'Dash', label: t('dash') },
  { value: 'Dot', label: t('dot') },
  { value: 'Dash_Dot', label: t('dashDot') },
];

export const RectangularNodePart = ({ nodeIds, style, customizedStyleProperties }: RectangularNodePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { updateRectangularNodeAppearance } = useUpdateRectangularNodeAppearance();
  const { resetNodeStyleProperties } = useResetNodeAppearance();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'rectangularNodePart' });
  const handleResetProperty = (customizedStyleProperty: string) =>
    resetNodeStyleProperties(editingContextId, diagramId, nodeIds, [customizedStyleProperty]);

  const handleEditProperty = (newValue: Partial<GQLRectangularNodeAppearanceInput>) =>
    updateRectangularNodeAppearance(editingContextId, diagramId, nodeIds, newValue);

  const isDisabled = (property: string) => !customizedStyleProperties.includes(property);

  const lineStyleOptions = useMemo(() => getLineStyleOptions(t), [t]);

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1), paddingBottom: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column' }} data-testid={'rectangular-node-part'}>
        <Typography variant="subtitle2">Style</Typography>

        <AppearanceColorPicker
          label={t('background')}
          initialValue={style.background}
          disabled={isDisabled('BACKGROUND')}
          onEdit={(newValue) => handleEditProperty({ background: newValue })}
          onReset={() => handleResetProperty('BACKGROUND')}></AppearanceColorPicker>

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
          icon={<LineWeightIcon />}
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
      </Box>
    </ListItem>
  );
};
