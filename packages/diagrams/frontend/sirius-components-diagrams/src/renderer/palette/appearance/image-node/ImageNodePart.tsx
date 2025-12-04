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
import { useContext } from 'react';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { useResetNodeAppearance } from '../useResetNodeAppearance';
import { AppearanceColorPicker } from '../widget/AppearanceColorPicker';
import { AppearanceNumberTextfield } from '../widget/AppearanceNumberTextfield ';
import { AppearanceSelect } from '../widget/AppearanceSelect';
import { ImageNodePartProps } from './ImageNodePart.types';
import { useUpdateImageNodeAppearance } from './useUpdateImageNodeAppearance';
import { GQLImageNodeAppearanceInput } from './useUpdateImageNodeAppearance.types';

const LINE_STYLE_OPTIONS = [
  { value: 'Solid', label: 'Solid' },
  { value: 'Dash', label: 'Dash' },
  { value: 'Dot', label: 'Dot' },
  { value: 'Dash_Dot', label: 'Dash Dot' },
];

export const ImageNodePart = ({ nodeIds, style, customizedStyleProperties }: ImageNodePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { updateImageNodeAppearance } = useUpdateImageNodeAppearance();
  const { resetNodeStyleProperties } = useResetNodeAppearance();

  const handleResetProperty = (customizedStyleProperty: string) =>
    resetNodeStyleProperties(editingContextId, diagramId, nodeIds, [customizedStyleProperty]);

  const handleEditProperty = (newValue: Partial<GQLImageNodeAppearanceInput>) =>
    updateImageNodeAppearance(editingContextId, diagramId, nodeIds, newValue);

  const isDisabled = (property: string) => !customizedStyleProperties.includes(property);

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1), paddingBottom: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column' }} data-testid={'image-node-part'}>
        <Typography variant="subtitle2">Style</Typography>

        <AppearanceColorPicker
          label={'Border Color'}
          initialValue={style.borderColor}
          disabled={isDisabled('BORDER_COLOR')}
          onEdit={(newValue) => handleEditProperty({ borderColor: newValue })}
          onReset={() => handleResetProperty('BORDER_COLOR')}></AppearanceColorPicker>

        <AppearanceNumberTextfield
          icon={<Crop32Icon />}
          label={'Border Radius'}
          initialValue={style.borderRadius}
          disabled={isDisabled('BORDER_RADIUS')}
          onEdit={(newValue) => handleEditProperty({ borderRadius: newValue })}
          onReset={() => handleResetProperty('BORDER_RADIUS')}></AppearanceNumberTextfield>

        <AppearanceNumberTextfield
          icon={<LineWeightIcon />}
          label={'Border Size'}
          initialValue={style.borderSize}
          disabled={isDisabled('BORDER_SIZE')}
          onEdit={(newValue) => handleEditProperty({ borderSize: newValue })}
          onReset={() => handleResetProperty('BORDER_SIZE')}></AppearanceNumberTextfield>

        <AppearanceSelect
          icon={<LineStyleIcon />}
          label={'Border Line Style'}
          options={LINE_STYLE_OPTIONS}
          initialValue={style.borderStyle}
          disabled={isDisabled('BORDER_STYLE')}
          onEdit={(newValue) => handleEditProperty({ borderStyle: newValue })}
          onReset={() => handleResetProperty('BORDER_STYLE')}></AppearanceSelect>
      </Box>
    </ListItem>
  );
};
