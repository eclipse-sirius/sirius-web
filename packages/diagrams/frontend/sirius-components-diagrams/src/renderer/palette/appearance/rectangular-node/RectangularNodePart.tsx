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
import FormatColorResetIcon from '@mui/icons-material/FormatColorReset';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import ListItem from '@mui/material/ListItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { Color } from '../Color';
import { RectangularNodePartProps, RectangularNodePartState } from './RectangularNodePart.types';
import { useResetNodeAppearance } from './useResetNodeAppearance';
import { useUpdateRectangularNodeAppearance } from './useUpdateRectangularNodeAppearance';

export const RectangularNodePart = ({ nodeId, style, customizedStyleProperties }: RectangularNodePartProps) => {
  const [state, setState] = useState<RectangularNodePartState>({
    background: style.background,
  });
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { updateBackground } = useUpdateRectangularNodeAppearance();
  const { resetNodeStyleProperties } = useResetNodeAppearance();

  const onBackgroundChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setState((prevState) => ({
      ...prevState,
      background: event.target.value,
    }));
  };

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1), paddingBottom: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column' }}>
        <Typography variant="subtitle2">Style</Typography>

        <Box sx={{ display: 'flex', flexDirection: 'column' }}>
          <Typography variant="caption">Background</Typography>

          <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
            <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
              <Color value={state.background} />
              <TextField
                variant="standard"
                value={state.background}
                onChange={onBackgroundChange}
                onKeyDown={(event) => {
                  if (event.code === 'Enter') {
                    updateBackground(editingContextId, diagramId, nodeId, state.background);
                  }
                }}
                onBlur={() => updateBackground(editingContextId, diagramId, nodeId, state.background)}
              />
            </Box>
            <IconButton
              aria-label="reset"
              size="small"
              onClick={() => resetNodeStyleProperties(editingContextId, diagramId, nodeId, ['BACKGROUND'])}
              disabled={!customizedStyleProperties.includes('BACKGROUND')}
              sx={{
                alignSelf: 'center',
                justifySelf: 'center',
              }}>
              <FormatColorResetIcon fontSize="small" />
            </IconButton>
          </Box>
        </Box>
      </Box>
    </ListItem>
  );
};
