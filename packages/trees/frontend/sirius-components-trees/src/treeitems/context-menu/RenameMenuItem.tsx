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
import EditIcon from '@mui/icons-material/Edit';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useTranslation } from 'react-i18next';
import { RenameMenuItemProps } from './RenameMenuItem.types';

export const RenameMenuItem = ({ item, readOnly, onClick }: RenameMenuItemProps) => {
  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'renameMenuItem' });
  if (!item.editable) {
    return null;
  }
  return (
    <MenuItem key="rename" onClick={onClick} data-testid="rename-tree-item" disabled={readOnly} aria-disabled>
      <ListItemIcon>
        <EditIcon fontSize="small" />
      </ListItemIcon>
      <ListItemText primary={t('rename')} />
    </MenuItem>
  );
};
