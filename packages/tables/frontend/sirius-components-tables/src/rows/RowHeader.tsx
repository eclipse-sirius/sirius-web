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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import { useTheme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { RowHeaderProps } from './RowHeader.types';

export const RowHeader = ({ row }: RowHeaderProps) => {
  const theme = useTheme();
  return (
    <Box
      display="flex"
      alignItems="center"
      justifyContent="space-between"
      gap={theme.spacing(2)}
      data-testid="table-row-header">
      <Box>
        <Typography noWrap>{row.headerIndexLabel}</Typography>
      </Box>
      <Box display="flex" alignItems="center" gap={theme.spacing(1)}>
        <IconOverlay iconURL={row.headerIconURLs} alt={row.headerLabel} />
        <Typography noWrap>{row.headerLabel}</Typography>
      </Box>
    </Box>
  );
};
