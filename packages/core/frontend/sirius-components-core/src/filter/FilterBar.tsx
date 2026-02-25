/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import ClearIcon from '@mui/icons-material/Clear';
import SearchIcon from '@mui/icons-material/Search';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import TextField from '@mui/material/TextField';
import { useTranslation } from 'react-i18next';
import { FilterBarProps } from './FilterBar.types';

export const FilterBar = ({ onTextChange, onTextClear, text }: FilterBarProps) => {
  const { t } = useTranslation('sirius-components-core', { keyPrefix: 'filterBar' });

  return (
    <TextField
      variant="outlined"
      data-testid="filterbar-textfield"
      name="filterbar-textfield"
      placeholder={t('typeToFilter')}
      spellCheck={false}
      size="small"
      margin="none"
      autoFocus={true}
      multiline={false}
      fullWidth
      value={text}
      onChange={onTextChange}
      slotProps={{
        input: {
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon fontSize="small" sx={(theme) => ({ color: theme.palette.text.disabled })} />
            </InputAdornment>
          ),
          endAdornment: (
            <InputAdornment position="end">
              <IconButton data-testid="filterbar-clear-button" aria-label="clear" size="small" onClick={onTextClear}>
                <ClearIcon fontSize="small" />
              </IconButton>
            </InputAdornment>
          ),
        },
      }}
    />
  );
};
