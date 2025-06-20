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
import { SelectAppearanceProperty } from '../property-component/SelectAppearanceProperty';
import { TextFieldAppearanceProperty } from '../property-component/TextFieldAppearanceProperty';
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
  const { updateRectangularNodeAppearance } = useUpdateRectangularNodeAppearance();
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
    updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, { borderStyle: event.target.value });
    setState((prevState) => ({
      ...prevState,
      borderStyle: event.target.value,
    }));
  };

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1), paddingBottom: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column' }}>
        <Typography variant="subtitle2">Style</Typography>

        <TextFieldAppearanceProperty
          icon={<Color value={state.background} />}
          label="Background"
          value={state.background}
          onChange={onBackgroundChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, {
                background: state.background,
              });
            }
          }}
          onBlur={() =>
            updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, {
              background: state.background,
            })
          }
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BACKGROUND'])}
          isDisabled={!customizedStyleProperties.includes('BACKGROUND')}
        />
        <TextFieldAppearanceProperty
          icon={<Color value={state.borderColor} />}
          label="Border Color"
          value={state.borderColor}
          onChange={onBorderColorChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, {
                borderColor: state.borderColor,
              });
            }
          }}
          onBlur={() =>
            updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, {
              borderColor: state.borderColor,
            })
          }
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BORDER_COLOR'])}
          isDisabled={!customizedStyleProperties.includes('BORDER_COLOR')}
        />
        <TextFieldAppearanceProperty
          icon={<Crop32Icon />}
          label="Border Radius"
          value={state.borderRadius}
          onChange={onBorderRadiusChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, {
                borderRadius: state.borderRadius,
              });
            }
          }}
          onBlur={() =>
            updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, {
              borderRadius: state.borderRadius,
            })
          }
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BORDER_RADIUS'])}
          isDisabled={!customizedStyleProperties.includes('BORDER_RADIUS')}
        />
        <TextFieldAppearanceProperty
          icon={<LineWeightIcon />}
          label="Border Size"
          value={state.borderSize}
          onChange={onBorderSizeChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, {
                borderSize: state.borderSize,
              });
            }
          }}
          onBlur={() =>
            updateRectangularNodeAppearance(editingContextId, diagramId, nodeId, {
              borderSize: state.borderSize,
            })
          }
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BORDER_SIZE'])}
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
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BORDER_STYLE'])}
          isDisabled={!customizedStyleProperties.includes('BORDER_STYLE')}
        />
      </Box>
    </ListItem>
  );
};
