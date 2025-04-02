/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
import ArrowBack from '@mui/icons-material/ArrowBack';
import ArrowForward from '@mui/icons-material/ArrowForward';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { CursorBasedPaginationProps } from './CursorBasedPagination.types';

export const CursorBasedPagination = ({
  hasPrev,
  hasNext,
  onPrev,
  onNext,
  pageSize,
  onPageSizeChange,
  pageSizeOptions,
}: CursorBasedPaginationProps) => {
  return (
    <Box display="flex" justifyContent="flex-end" alignItems="center" width="100%">
      <Box display="flex" alignItems="center">
        <Typography variant="body1" mr={1}>
          Rows per page:
        </Typography>
        <Select
          value={pageSize}
          onChange={(e) => onPageSizeChange(Number(e.target.value))}
          inputProps={{ 'aria-label': 'Rows per page' }}
          data-testid="cursor-based-pagination-size">
          {pageSizeOptions.map((option) => (
            <MenuItem key={option} value={option}>
              {option}
            </MenuItem>
          ))}
        </Select>
      </Box>
      <Box display="flex" alignItems="center">
        <IconButton onClick={onPrev} disabled={!hasPrev} data-testid="pagination-prev">
          <ArrowBack />
        </IconButton>
        <IconButton onClick={onNext} disabled={!hasNext} data-testid="pagination-next">
          <ArrowForward />
        </IconButton>
      </Box>
    </Box>
  );
};
