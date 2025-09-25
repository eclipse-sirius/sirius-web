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
import { RectangularNodePartProps } from './RectangularNodePart.types';
import { useUpdateRectangularNodeAppearance } from './useUpdateRectangularNodeAppearance';
import { GQLRectangularNodeAppearanceInput } from './useUpdateRectangularNodeAppearance.types';

const LINE_STYLE_OPTIONS = [
  { value: 'Solid', label: 'Solid' },
  { value: 'Dash', label: 'Dash' },
  { value: 'Dot', label: 'Dot' },
  { value: 'Dash_Dot', label: 'Dash Dot' },
];

export const RectangularNodePart = ({ nodeId, style, customizedStyleProperties }: RectangularNodePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { updateRectangularNodeAppearance } = useUpdateRectangularNodeAppearance();
  const { resetNodeStyleProperties } = useResetNodeAppearance();

  const handleResetProperty = (customizedStyleProperty: string) =>
    resetNodeStyleProperties(editingContextId, diagramId, nodeId, [customizedStyleProperty]);

  const handleEditProperty = (newValue: Partial<GQLRectangularNodeAppearanceInput>) =>
    updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, newValue);

  const isDisabled = (property: string) => !customizedStyleProperties.includes(property);

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1), paddingBottom: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column' }} data-testid={'rectangular-node-part'}>
        <Typography variant="subtitle2">Style</Typography>

        <AppearanceColorPicker
          label={'Background'}
          initialValue={style.background}
          disabled={isDisabled('BACKGROUND')}
          onEdit={(newValue) => handleEditProperty({ background: newValue })}
          onReset={() => handleResetProperty('BACKGROUND')}></AppearanceColorPicker>

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
