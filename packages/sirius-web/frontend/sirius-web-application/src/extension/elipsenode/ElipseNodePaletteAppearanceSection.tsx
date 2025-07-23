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

import {
  Color,
  DiagramContext,
  DiagramContextValue,
  PaletteAppearanceSectionContributionComponentProps,
  SelectAppearanceProperty,
  TextFieldAppearanceProperty,
  useResetNodeAppearance,
} from '@eclipse-sirius/sirius-components-diagrams';
import Crop32Icon from '@mui/icons-material/Crop32';
import LineStyleIcon from '@mui/icons-material/LineStyle';
import LineWeightIcon from '@mui/icons-material/LineWeight';
import Box from '@mui/material/Box';
import ListItem from '@mui/material/ListItem';
import { SelectChangeEvent } from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import {
  ElipseNodePaletteAppearanceSectionState,
  GQLEllipseNodeStyle,
} from './ElipseNodePaletteAppearanceSection.types';
import { useUpdateElipseNodeAppearance } from './useUpdateElipseNodeAppearance';

export const ElipseNodeAppearanceSection = ({ element }: PaletteAppearanceSectionContributionComponentProps) => {
  const style = element.data.nodeAppearanceData.gqlStyle as GQLEllipseNodeStyle;
  const customizedStyleProperties = element.data.nodeAppearanceData.customizedStyleProperties;

  const [state, setState] = useState<ElipseNodePaletteAppearanceSectionState>({
    background: style.background,
    borderColor: style.borderColor,
    borderRadius: style.borderRadius,
    borderSize: style.borderSize,
    borderStyle: style.borderStyle,
  });

  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { updateElipseNodeAppearance } = useUpdateElipseNodeAppearance();
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
    updateElipseNodeAppearance(editingContextId, diagramId, element.id, { borderStyle: event.target.value });
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
              updateElipseNodeAppearance(editingContextId, diagramId, element.id, {
                background: state.background,
              });
            }
          }}
          onBlur={() =>
            updateElipseNodeAppearance(editingContextId, diagramId, element.id, {
              background: state.background,
            })
          }
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, element.id, ['BACKGROUND'])}
          isDisabled={!customizedStyleProperties.includes('BACKGROUND')}
        />
        <TextFieldAppearanceProperty
          icon={<Color value={state.borderColor} />}
          label="Border Color"
          value={state.borderColor}
          onChange={onBorderColorChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateElipseNodeAppearance(editingContextId, diagramId, element.id, {
                borderColor: state.borderColor,
              });
            }
          }}
          onBlur={() =>
            updateElipseNodeAppearance(editingContextId, diagramId, element.id, {
              borderColor: state.borderColor,
            })
          }
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, element.id, ['BORDER_COLOR'])}
          isDisabled={!customizedStyleProperties.includes('BORDER_COLOR')}
        />
        <TextFieldAppearanceProperty
          icon={<Crop32Icon />}
          label="Border Radius"
          value={state.borderRadius}
          onChange={onBorderRadiusChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateElipseNodeAppearance(editingContextId, diagramId, element.id, {
                borderRadius: state.borderRadius,
              });
            }
          }}
          onBlur={() =>
            updateElipseNodeAppearance(editingContextId, diagramId, element.id, {
              borderRadius: state.borderRadius,
            })
          }
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, element.id, ['BORDER_RADIUS'])}
          isDisabled={!customizedStyleProperties.includes('BORDER_RADIUS')}
        />
        <TextFieldAppearanceProperty
          icon={<LineWeightIcon />}
          label="Border Size"
          value={state.borderSize}
          onChange={onBorderSizeChange}
          onKeyDown={(event) => {
            if (event.code === 'Enter') {
              updateElipseNodeAppearance(editingContextId, diagramId, element.id, {
                borderSize: state.borderSize,
              });
            }
          }}
          onBlur={() =>
            updateElipseNodeAppearance(editingContextId, diagramId, element.id, {
              borderSize: state.borderSize,
            })
          }
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, element.id, ['BORDER_SIZE'])}
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
          onReset={() => resetNodeStyleProperties(editingContextId, diagramId, element.id, ['BORDER_STYLE'])}
          isDisabled={!customizedStyleProperties.includes('BORDER_STYLE')}
        />
      </Box>
    </ListItem>
  );
};
