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
import { ColumnHeaderProps } from './ColumnHeader.types';

export const ColumnHeader = ({ column }: ColumnHeaderProps) => {
  const theme = useTheme();
  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="space-between"
      paddingX={theme.spacing(2)}>
      <Box>
        <Typography noWrap>{column.headerIndexLabel}</Typography>
      </Box>
      <Box display="flex" alignItems="center" gap={theme.spacing(1)}>
        <IconOverlay iconURLs={column.headerIconURLs} alt={column.headerLabel} />
        <Typography noWrap>{column.headerLabel}</Typography>
      </Box>
    </Box>
  );
};
