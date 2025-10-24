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

import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import LineStyleIcon from '@mui/icons-material/LineStyle';
import LineWeightIcon from '@mui/icons-material/LineWeight';
import TurnSharpRightIcon from '@mui/icons-material/TurnSharpRight';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import Typography from '@mui/material/Typography';
import { useContext } from 'react';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { useDiagramElementPalette } from '../../useDiagramElementPalette';
import { AppearanceColorPicker } from '../widget/AppearanceColorPicker';
import { AppearanceNumberTextfield } from '../widget/AppearanceNumberTextfield ';
import { AppearanceSelect } from '../widget/AppearanceSelect';
import { EdgeAppearancePartProps } from './EdgeAppearancePart.types';
import { useEditEdgeAppearance } from './useEditEdgeAppearance';
import { GQLEdgeAppearanceInput } from './useEditEdgeAppearance.types';
import { useResetEdgeAppearance } from './useResetEdgeAppearance';

const LINE_STYLE_OPTIONS = [
  { value: 'Solid', label: 'Solid' },
  { value: 'Dash', label: 'Dash' },
  { value: 'Dot', label: 'Dot' },
  { value: 'Dash_Dot', label: 'Dash Dot' },
];

const ARROW_OPTIONS = [
  { value: 'Diamond', label: 'Diamond' },
  { value: 'FillDiamond', label: 'Fill Diamond' },
  { value: 'InputArrow', label: 'Input Arrow' },
  { value: 'InputArrowWithDiamond', label: 'Input Arrow Diamond' },
  { value: 'InputArrowWithFillDiamond', label: 'Input Arrow Fill Diamond' },
  { value: 'InputClosedArrow', label: 'Input Closed Arrow' },
  { value: 'InputFillClosedArrow', label: 'Input Fill Closed Arrow' },
  { value: 'None', label: 'None' },
  { value: 'OutputArrow', label: 'Output Arrow' },
  { value: 'OutputClosedArrow', label: 'Output Closed Arrow' },
  { value: 'OutputFillClosedArrow', label: 'Output Fill Closed Arrow' },
  { value: 'Circle', label: 'Circle' },
  { value: 'FillCircle', label: 'Fill Circle' },
  { value: 'CrossedCircle', label: 'Crossed Circle' },
  { value: 'ClosedArrowWithVerticalBar', label: 'Closed Arrow Vertical Bar' },
  { value: 'ClosedArrowWithDots', label: 'Closed Arrow Dots' },
];

const EDGE_TYPE_OPTIONS = [
  { value: 'Manhattan', label: 'Manhattan' },
  { value: 'SmartManhattan', label: 'SmartManhattan' },
  { value: 'Oblique', label: 'Oblique' },
];

export const EdgeAppearancePart = ({ edgeId, style, customizedStyleProperties }: EdgeAppearancePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const { updateEdgeAppearance } = useEditEdgeAppearance();
  const { resetEdgeStyleProperties } = useResetEdgeAppearance();
  const { hideDiagramElementPalette } = useDiagramElementPalette();

  const handleResetProperty = (customizedStyleProperty: string) => {
    resetEdgeStyleProperties(editingContextId, diagramId, edgeId, [customizedStyleProperty]);
  };

  const handleEditProperty = (newValue: Partial<GQLEdgeAppearanceInput>) => {
    updateEdgeAppearance(editingContextId, diagramId, edgeId, newValue);
  };

  const isDisabled = (property: string) => !customizedStyleProperties.includes(property);

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
        <Typography variant="subtitle2">{'Style'}</Typography>

        <AppearanceColorPicker
          label={'Color'}
          initialValue={style.color}
          disabled={isDisabled('COLOR')}
          onEdit={(newValue) => handleEditProperty({ color: newValue })}
          onReset={() => handleResetProperty('COLOR')}></AppearanceColorPicker>

        <AppearanceNumberTextfield
          icon={<LineWeightIcon />}
          label={'Size'}
          initialValue={style.size}
          disabled={isDisabled('SIZE')}
          onEdit={(newValue) => handleEditProperty({ size: newValue })}
          onReset={() => handleResetProperty('SIZE')}></AppearanceNumberTextfield>

        <AppearanceSelect
          icon={<LineStyleIcon />}
          label={'Line Style'}
          options={LINE_STYLE_OPTIONS}
          initialValue={style.lineStyle}
          disabled={isDisabled('LINESTYLE')}
          onEdit={(newValue) => handleEditProperty({ lineStyle: newValue })}
          onReset={() => handleResetProperty('LINESTYLE')}></AppearanceSelect>

        <AppearanceSelect
          icon={<ArrowBackIcon />}
          options={ARROW_OPTIONS}
          label={'Source arrow'}
          initialValue={style.sourceArrow}
          disabled={isDisabled('SOURCE_ARROW')}
          onEdit={(newValue) => handleEditProperty({ sourceArrowStyle: newValue })}
          onReset={() => handleResetProperty('SOURCE_ARROW')}></AppearanceSelect>

        <AppearanceSelect
          icon={<ArrowForwardIcon />}
          options={ARROW_OPTIONS}
          label={'Target arrow'}
          initialValue={style.targetArrow}
          disabled={isDisabled('TARGET_ARROW')}
          onEdit={(newValue) => handleEditProperty({ targetArrowStyle: newValue })}
          onReset={() => handleResetProperty('TARGET_ARROW')}></AppearanceSelect>

        <AppearanceSelect
          icon={<TurnSharpRightIcon />}
          options={EDGE_TYPE_OPTIONS}
          label={'Edge Type'}
          initialValue={style.edgeType}
          disabled={isDisabled('EDGE_TYPE')}
          onEdit={(newValue) => {
            handleEditProperty({ edgeType: newValue });
            hideDiagramElementPalette(); //Changing the edge type creates a new edge, so we explicitly close the palette to avoid a glitch.
          }}
          onReset={() => {
            handleResetProperty('EDGE_TYPE');
            hideDiagramElementPalette(); //Changing the edge type creates a new edge, so we explicitly close the palette to avoid a glitch.
          }}></AppearanceSelect>
      </Box>
    </ListItem>
  );
};
