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

import KeyboardDoubleArrowDownIcon from '@mui/icons-material/KeyboardDoubleArrowDown';
import KeyboardDoubleArrowRightIcon from '@mui/icons-material/KeyboardDoubleArrowRight';
import IconButton from '@mui/material/IconButton';
import { ExpandAllColumnHeaderProps } from './ExpandAllColumnHeader.types';

export const ExpandAllColumnHeader = ({ expandAll, onExpandAllChange }: ExpandAllColumnHeaderProps) => {
  return (
    <IconButton disableRipple={true} onClick={() => onExpandAllChange()}>
      {expandAll ? <KeyboardDoubleArrowDownIcon /> : <KeyboardDoubleArrowRightIcon />}
    </IconButton>
  );
};
