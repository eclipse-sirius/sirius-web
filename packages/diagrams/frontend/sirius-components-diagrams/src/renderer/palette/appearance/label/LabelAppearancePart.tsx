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

import FormatBoldIcon from '@mui/icons-material/FormatBold';
import FormatColorResetIcon from '@mui/icons-material/FormatColorReset';
import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';
import IconButton from '@mui/material/IconButton';
import ListItem from '@mui/material/ListItem';
import Typography from '@mui/material/Typography';
import { useContext } from 'react';
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { LabelAppearancePartProps } from './LabelAppearancePart.types';
import { useEditLabelAppearance } from './useEditLabelAppearance';
import { useResetLabelAppearance } from './useResetLabelAppearance';

export const LabelAppearancePart = ({
  diagramElementId,
  labelId,
  position,
  style,
  customizedStyleProperties,
}: LabelAppearancePartProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const { updateBold } = useEditLabelAppearance();
  const { resetLabelStyleProperties } = useResetLabelAppearance();

  return (
    <ListItem disablePadding sx={(theme) => ({ paddingX: theme.spacing(1) })}>
      <Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
        <Typography variant="subtitle2">{position}</Typography>

        <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
          <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
            <FormatBoldIcon />
            <FormGroup aria-label="position" row>
              <FormControlLabel
                value="bottom"
                control={
                  <Checkbox
                    checked={style.bold}
                    onChange={(_, checked) =>
                      updateBold(editingContextId, diagramId, diagramElementId, labelId, checked)
                    }
                  />
                }
                label="Bold"
                labelPlacement="start"
                sx={{ marginLeft: 0, marginRight: 0 }}
              />
            </FormGroup>
          </Box>
          <IconButton
            aria-label="reset"
            size="small"
            onClick={() => resetLabelStyleProperties(editingContextId, diagramId, diagramElementId, labelId, ['BOLD'])}
            disabled={!customizedStyleProperties.includes('BOLD')}
            sx={{
              alignSelf: 'center',
              justifySelf: 'center',
            }}>
            <FormatColorResetIcon fontSize="small" />
          </IconButton>
        </Box>
      </Box>
    </ListItem>
  );
};
