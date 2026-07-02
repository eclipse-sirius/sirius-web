/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { PaletteToolContributionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import DeleteIcon from '@mui/icons-material/Delete';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { useDelete } from './useDelete';

export const DeleteToolContribution = ({ onInvoked }: PaletteToolContributionComponentProps) => {
  const { handleDelete } = useDelete();
  const { editingContextId, item, treeId, readOnly } = useContext<TreePaletteContextValue>(TreePaletteContext);

  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'deleteMenuItem' });

  const deleteTreeItem = () => handleDelete(editingContextId, treeId, item);

  if (!item.deletable) {
    return null;
  }

  const handleClick = () => {
    onInvoked();
    deleteTreeItem();
  };

  return (
    <MenuItem key="delete-tree-item" onClick={handleClick} data-testid="delete" disabled={readOnly} aria-disabled>
      <ListItemIcon>
        <DeleteIcon fontSize="small" />
      </ListItemIcon>
      <ListItemText primary={t('delete')} />
    </MenuItem>
  );
};
