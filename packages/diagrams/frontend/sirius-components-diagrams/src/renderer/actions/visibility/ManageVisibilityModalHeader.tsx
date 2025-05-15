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

import CloseIcon from '@mui/icons-material/Close';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import IconButton from '@mui/material/IconButton';
import { Theme, useTheme } from '@mui/material/styles';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import { ManageVisibilityHeaderProps } from './ManageVisibilityModalHeader.types';

export const ManageVisibilityModalHeader = ({ closeDialog }: ManageVisibilityHeaderProps) => {
  const theme: Theme = useTheme();

  return (
    <Grid container alignItems={'center'} sx={{ backgroundColor: `${theme.palette.secondary.main}08` }}>
      <Grid size={10}>
        <Box display="flex" justifyContent="center">
          <Typography variant="body1">Manage Visibility</Typography>
        </Box>
      </Grid>

      <Grid size={2}>
        <Tooltip title="Close">
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            data-testid={'manage_visibility_close'}
            onClick={closeDialog}>
            <CloseIcon fontSize="small" />
          </IconButton>
        </Tooltip>
      </Grid>
    </Grid>
  );
};
