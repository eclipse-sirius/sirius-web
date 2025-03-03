/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import IconButton from '@mui/material/IconButton';
import { useTheme } from '@mui/material/styles';
import { RowChevronButtonProps } from './RowChevronButton.types';

export const RowChevronButton = ({ row, isExpanded, onExpandCollapse, hasChildren }: RowChevronButtonProps) => {
  const theme = useTheme();
  return (
    <IconButton
      onClick={(_) => onExpandCollapse(row.id)}
      // the contrast between disabled and default text color is not enough
      sx={{ marginLeft: theme.spacing(row.depthLevel), marginRight: theme.spacing(-2), color: 'black' }}
      disabled={!hasChildren}>
      {isExpanded ? <ExpandMoreIcon /> : <ChevronRightIcon />}
    </IconButton>
  );
};
