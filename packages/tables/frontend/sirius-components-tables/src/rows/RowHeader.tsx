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

import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import { useTheme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { RowHeaderProps } from './RowHeader.types';

export const RowHeader = ({ row, isExpanded, onClick, enableSubRows }: RowHeaderProps) => {
  const theme = useTheme();
  return (
    <Box
      display="flex"
      alignItems="center"
      justifyContent="space-between"
      gap={theme.spacing(2)}
      data-testid="table-row-header">
      {enableSubRows && (
        <IconButton onClick={(_) => onClick(row.targetObjectId)} sx={{ marginLeft: theme.spacing(row.depthLevel) }}>
          {isExpanded ? <ExpandMoreIcon /> : <ChevronRightIcon />}
        </IconButton>
      )}
      <Typography noWrap>{row.headerIndexLabel}</Typography>
      <Box display="flex" alignItems="center" gap={theme.spacing(1)}>
        <IconOverlay iconURL={row.headerIconURLs} alt={row.headerLabel} />
        <Typography noWrap>{row.headerLabel}</Typography>
      </Box>
    </Box>
  );
};
