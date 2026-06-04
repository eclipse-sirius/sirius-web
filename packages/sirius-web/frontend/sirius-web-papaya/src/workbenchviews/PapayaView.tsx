/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import { WorkbenchViewComponentProps, WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import TroubleshootIcon from '@mui/icons-material/Troubleshoot';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef } from 'react';

export const PapayaView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({}: WorkbenchViewComponentProps, _ref: ForwardedRef<WorkbenchViewHandle>) => {
    return (
      <Box sx={{ display: 'flex', flexDirection: 'column' }} data-testid="view-Papaya View">
        <Box
          sx={(theme) => ({
            display: 'flex',
            flexDirection: 'row',
            borderBottomWidth: '1px',
            borderBottomStyle: 'solid',
            borderBottomColor: theme.palette.divider,
          })}>
          <TroubleshootIcon sx={(theme) => ({ margin: theme.spacing(1) })} />
          <Typography
            sx={(theme) => ({
              marginTop: theme.spacing(1),
              marginRight: theme.spacing(1),
              marginBottom: theme.spacing(1),
            })}>
            Papaya View
          </Typography>
        </Box>
        <Box sx={{ flexGrow: 1, minHeight: 0 }}>Papaya View</Box>
      </Box>
    );
  }
);
