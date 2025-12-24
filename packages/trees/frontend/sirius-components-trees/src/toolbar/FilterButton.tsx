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
import SearchIcon from '@mui/icons-material/Search';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { useTranslation } from 'react-i18next';
import { FilterButtonProps } from './FilterButton.types';

export const FilterButton = ({ onClick }: FilterButtonProps) => {
  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'filterButton' });

  return (
    <Tooltip title={t('filter')}>
      <span>
        <IconButton
          size="small"
          aria-label={t('filter')}
          color="inherit"
          onClick={onClick}
          data-testid="explorer-filter-button">
          <SearchIcon />
        </IconButton>
      </span>
    </Tooltip>
  );
};
