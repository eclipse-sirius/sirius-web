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

import TextField from '@mui/material/TextField';
import IconButton from '@mui/material/IconButton';
import FormatColorResetIcon from '@mui/icons-material/FormatColorReset';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { TextFieldAppearancePropertyProps } from './TextFieldAppearanceProperty.types';

export const TextFieldAppearanceProperty = ({
  icon,
  label,
  value,
  onChange,
  onKeyDown,
  onBlur,
  onReset,
  isDisabled,
}: TextFieldAppearancePropertyProps) => {
  return (
    <Box sx={{ display: 'flex', flexDirection: 'column' }}>
      {label && <Typography variant="caption">{label}</Typography>}
      <Box sx={{ display: 'grid', gridTemplateColumns: '1fr min-content' }}>
        <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'end', gap: theme.spacing(1) })}>
          {icon}
          <TextField variant="standard" value={value} onChange={onChange} onKeyDown={onKeyDown} onBlur={onBlur} />
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
