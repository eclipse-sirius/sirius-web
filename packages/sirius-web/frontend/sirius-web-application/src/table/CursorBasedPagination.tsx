/*******************************************************************************
 * Copyright (c) 2025 CEA LIST and others.
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
  hasPreviousPage,
  hasNextPage,
  onPreviousPage,
  onNextPage,
  pageSize,
  onPageSizeChange,
}: CursorBasedPaginationProps) => {
  return (
    <Box
      sx={(theme) => ({
        display: 'flex',
        flexDirection: 'row',
        gap: theme.spacing(1),
        justifyContent: 'flex-end',
        alignItems: 'center',
        paddingTop: theme.spacing(0.25),
        paddingBottom: theme.spacing(0.25),
      })}>
      <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
        <Typography variant="caption">Rows per page:</Typography>
        <Select
          variant="outlined"
          size="small"
          value={pageSize}
          onChange={(event) => onPageSizeChange(Number(event.target.value))}
          inputProps={{
            'aria-label': 'Rows per page',
            sx: (theme) => ({ paddingTop: theme.spacing(0.5), paddingBottom: theme.spacing(0.5) }),
          }}
          data-testid="cursor-based-pagination-size">
          {[5, 10, 20, 50].map((option) => (
            <MenuItem key={option} value={option}>
              {option}
            </MenuItem>
          ))}
        </Select>
      </Box>
      <Box sx={{ displat: 'flex', flexDirection: 'row', alignItems: 'center' }}>
        <IconButton onClick={onPreviousPage} disabled={!hasPreviousPage} data-testid="pagination-prev">
          <ArrowBack />
        </IconButton>
        <IconButton onClick={onNextPage} disabled={!hasNextPage} data-testid="pagination-next">
          <ArrowForward />
        </IconButton>
      </Box>
    </Box>
  );
};
