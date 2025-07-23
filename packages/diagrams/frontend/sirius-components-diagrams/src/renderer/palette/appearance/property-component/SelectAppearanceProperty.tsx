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
import FormControl from '@mui/material/FormControl';
import IconButton from '@mui/material/IconButton';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { SelectAppearancePropertyProps } from './SelectAppearanceProperty.types';
//maxWidth: theme.spacing(18),
//sx={{ whiteSpace: 'normal' }}
export const SelectAppearanceProperty = ({
  icon,
  label,
  value,
  onChange,
  options,
  onReset,
  isDisabled,
}: SelectAppearancePropertyProps) => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column' }}>
      {label && <Typography variant="caption">{label}</Typography>}
      <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
        <Box
          sx={(theme) => ({
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'end',
            gap: theme.spacing(1),
            maxWidth: theme.spacing(17),
          })}>
          {icon}
          <FormControl variant="standard" sx={{ width: '100%' }}>
            <Select value={value} onChange={onChange}>
              {options.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  <Typography sx={{ textWrap: 'wrap' }}>{option.label}</Typography>
                </MenuItem>
              ))}
            </Select>
          </FormControl>
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
    </Box>
  );
};
