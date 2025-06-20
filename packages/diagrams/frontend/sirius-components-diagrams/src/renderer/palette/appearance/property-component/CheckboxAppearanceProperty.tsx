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

import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';
import IconButton from '@mui/material/IconButton';
import FormatColorResetIcon from '@mui/icons-material/FormatColorReset';
import Box from '@mui/material/Box';
import { CheckboxAppearancePropertyProps } from './CheckboxAppearanceProperty.types';

export const CheckboxAppearanceProperty = ({
  icon,
  label,
  checked,
  onChange,
  onReset,
  isDisabled,
}: CheckboxAppearancePropertyProps) => {
  return (
    <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
      <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
        {icon}
        <FormGroup aria-label="position" row>
          <FormControlLabel
            value="bottom"
            control={<Checkbox checked={checked} onChange={(_, checked) => onChange(checked)} />}
            label={label}
            labelPlacement="end"
            sx={{ marginLeft: 0, marginRight: 0 }}
          />
        </FormGroup>
      </Box>
      <IconButton
        aria-label="reset"
        size="small"
        onClick={onReset}
        disabled={isDisabled}
        sx={{
          alignSelf: 'center',
          justifySelf: 'center',
        }}>
        <FormatColorResetIcon fontSize="small" />
      </IconButton>
    </Box>
  );
};
