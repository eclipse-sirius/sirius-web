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
import Box from '@mui/material/Box';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import IconButton from '@mui/material/IconButton';
import { RowExpandHeaderProps } from './RowExpandHeader.types';

export const RowExpandHeader = ({ row, isExpanded, onClick }: RowExpandHeaderProps) => {
  return (
    <Box
      sx={(theme) => ({
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        gap: theme.spacing(1),
        height: '100%',
      })}
      data-testid="table-row-expand-header">
      <IconButton
        onClick={(_) => onClick(row.targetObjectId)}
        sx={(theme) => ({
          marginLeft: theme.spacing(row.depthLevel),
        })}
        disableRipple={true}
        disabled={!row.hasChildren}>
        {isExpanded ? <ExpandMoreIcon /> : <ChevronRightIcon />}
      </IconButton>
    </Box>
  );
};
