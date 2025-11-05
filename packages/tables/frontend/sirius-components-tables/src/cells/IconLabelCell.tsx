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

import { IconLabelCellProps } from './IconLabelCell.types';

export const IconLabelCell = ({ cell }: IconLabelCellProps) => {
  const theme = useTheme();

  return (
    <Box display="flex" alignItems="center" gap={theme.spacing(1)}>
      <IconOverlay iconURLs={cell.iconURLs} alt={cell.label} />
      {cell.label}
    </Box>
  );
};
