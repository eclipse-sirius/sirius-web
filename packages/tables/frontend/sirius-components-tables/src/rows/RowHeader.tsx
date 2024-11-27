/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
    <Box display="flex" alignItems="center" justifyContent="space-between">
      <Box>
        <Typography>{row.headerIndexLabel}</Typography>
      </Box>
      <Box display="flex" alignItems="center">
        <IconOverlay iconURL={row.headerIconURLs} alt={row.headerLabel} />
        <Typography ml={1}>{row.headerLabel}</Typography>
      </Box>
    </Box>
  );
};
