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
import Typography from '@mui/material/Typography';
import { RowHeaderProps } from './RowHeader.types';

export const RowHeader = ({ row }: RowHeaderProps) => {
  return (
    <Box
      sx={(theme) => ({
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        gap: theme.spacing(1),
        height: '100%',
      })}
      data-testid="table-row-header">
      <Typography noWrap sx={(theme) => ({ marginLeft: theme.spacing(row.depthLevel), marginRight: theme.spacing(1) })}>
        {row.headerIndexLabel}
      </Typography>
      <Box sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(1) })}>
        <IconOverlay iconURLs={row.headerIconURLs} alt={row.headerLabel} />
        <Typography noWrap>{row.headerLabel}</Typography>
      </Box>
    </Box>
  );
};
