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
import { TFunction } from 'i18next/typescript/t';
import { useContext, useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { useDiagramPalette } from '../../useDiagramPalette';
import { AppearanceColorPicker } from '../widget/AppearanceColorPicker';
import { AppearanceNumberTextfield } from '../widget/AppearanceNumberTextfield ';
import { AppearanceSelect } from '../widget/AppearanceSelect';
import { EdgeAppearancePartProps } from './EdgeAppearancePart.types';
import { useEditEdgeAppearance } from './useEditEdgeAppearance';
import { GQLEdgeAppearanceInput } from './useEditEdgeAppearance.types';
import { useResetEdgeAppearance } from './useResetEdgeAppearance';

const getLineStyleOptions = (t: TFunction) => [
  { value: 'Solid', label: t('solid') },
  { value: 'Dash', label: t('dash') },
  { value: 'Dot', label: t('dot') },
  { value: 'Dash_Dot', label: t('dashDot') },
];

const getArrowOption = (t: TFunction) => [
  { value: 'Diamond', label: t('diamond') },
  { value: 'FillDiamond', label: t('fillDiamond') },
  { value: 'InputArrow', label: t('inputArrow') },
  { value: 'InputArrowWithDiamond', label: t('inputArrowWithDiamond') },
  { value: 'InputArrowWithFillDiamond', label: t('inputArrowWithFillDiamond') },
  { value: 'InputClosedArrow', label: t('inputClosedArrow') },
  { value: 'InputFillClosedArrow', label: t('inputFillClosedArrow') },
  { value: 'None', label: t('none') },
  { value: 'OutputArrow', label: t('outputArrow') },
  { value: 'OutputClosedArrow', label: t('outputClosedArrow') },
  { value: 'OutputFillClosedArrow', label: t('outputFillClosedArrow') },
  { value: 'Circle', label: t('circle') },
  { value: 'FillCircle', label: t('fillCircle') },
  { value: 'CrossedCircle', label: t('crossedCircle') },
  { value: 'ClosedArrowWithVerticalBar', label: t('closedArrowWithVerticalBar') },
  { value: 'ClosedArrowWithDots', label: t('closedArrowWithDots') },
];

const getEdgeTypeOptions = (t: TFunction) => [
  { value: 'Manhattan', label: t('manhattan') },
  { value: 'SmartManhattan', label: t('smartManhattan') },
  { value: 'Oblique', label: t('oblique') },
];

export const EdgeAppearancePart = ({ edgeIds, style, customizedStyleProperties }: EdgeAppearancePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const { updateEdgeAppearance } = useEditEdgeAppearance();
  const { resetEdgeStyleProperties } = useResetEdgeAppearance();
  const { hideDiagramPalette } = useDiagramPalette();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'edgeAppearancePart' });
  const lineStyleOptions = useMemo(() => getLineStyleOptions(t), [t]);
  const arrowOptions = useMemo(() => getArrowOption(t), [t]);
  const edgeTypeOptions = useMemo(() => getEdgeTypeOptions(t), [t]);

  const handleResetProperty = (customizedStyleProperty: string) => {
    resetEdgeStyleProperties(editingContextId, diagramId, edgeIds, [customizedStyleProperty]);
  };

  const handleEditProperty = (newValue: Partial<GQLEdgeAppearanceInput>) => {
    updateEdgeAppearance(editingContextId, diagramId, edgeIds, newValue);
  };

  const isDisabled = (property: string) => !customizedStyleProperties.includes(property);

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
        <Typography variant="subtitle2">{'Style'}</Typography>

        <AppearanceColorPicker
          label={t('color')}
          initialValue={style.color}
          disabled={isDisabled('COLOR')}
          onEdit={(newValue) => handleEditProperty({ color: newValue })}
          onReset={() => handleResetProperty('COLOR')}></AppearanceColorPicker>

        <AppearanceNumberTextfield
          icon={<LineWeightIcon />}
          label={t('size')}
          initialValue={style.size}
          disabled={isDisabled('SIZE')}
          onEdit={(newValue) => handleEditProperty({ size: newValue })}
          onReset={() => handleResetProperty('SIZE')}></AppearanceNumberTextfield>

        <AppearanceSelect
          icon={<LineStyleIcon />}
          label={t('lineStyle')}
          options={lineStyleOptions}
          initialValue={style.lineStyle}
          disabled={isDisabled('LINESTYLE')}
          onEdit={(newValue) => handleEditProperty({ lineStyle: newValue })}
          onReset={() => handleResetProperty('LINESTYLE')}></AppearanceSelect>

        <AppearanceSelect
          icon={<ArrowBackIcon />}
          options={arrowOptions}
          label={t('sourceArrow')}
          initialValue={style.sourceArrow}
          disabled={isDisabled('SOURCE_ARROW')}
          onEdit={(newValue) => handleEditProperty({ sourceArrowStyle: newValue })}
          onReset={() => handleResetProperty('SOURCE_ARROW')}></AppearanceSelect>

        <AppearanceSelect
          icon={<ArrowForwardIcon />}
          options={arrowOptions}
          label={t('targetArrow')}
          initialValue={style.targetArrow}
          disabled={isDisabled('TARGET_ARROW')}
          onEdit={(newValue) => handleEditProperty({ targetArrowStyle: newValue })}
          onReset={() => handleResetProperty('TARGET_ARROW')}></AppearanceSelect>

        <AppearanceSelect
          icon={<TurnSharpRightIcon />}
          options={edgeTypeOptions}
          label={t('edgeType')}
          initialValue={style.edgeType}
          disabled={isDisabled('EDGE_TYPE')}
          onEdit={(newValue) => {
            handleEditProperty({ edgeType: newValue });
            hideDiagramPalette(); //Changing the edge type creates a new edge, so we explicitly close the palette to avoid a glitch.
          }}
          onReset={() => {
            handleResetProperty('EDGE_TYPE');
            hideDiagramPalette(); //Changing the edge type creates a new edge, so we explicitly close the palette to avoid a glitch.
          }}></AppearanceSelect>
      </Box>
    </ListItem>
  );
};
