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
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { useDelete } from './useDelete';

const useStyle = makeStyles()((theme) => ({
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  listItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  listItemIcon: {
    minWidth: 0,
    marginRight: theme.spacing(2),
  },
}));

export const DeleteToolContribution = ({ onInvoked }: PaletteToolContributionComponentProps) => {
  const { handleDelete } = useDelete();
  const { editingContextId, item, treeId, readOnly } = useContext<TreePaletteContextValue>(TreePaletteContext);

  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'deleteMenuItem' });
  const { classes } = useStyle();

  const deleteTreeItem = () => handleDelete(editingContextId, treeId, item);

  if (!item.deletable) {
    return null;
  }

  const handleClick = () => {
    onInvoked();
    deleteTreeItem();
  };

  return (
    <Tooltip title={t('delete')} placement="right">
      <ListItemButton
        className={classes.listItemButton}
        disabled={readOnly}
        onClick={handleClick}
        data-testid={`tool-delete`}>
        <ListItemIcon className={classes.listItemIcon}>
          <DeleteIcon />
        </ListItemIcon>
        <ListItemText primary={t('delete')} className={classes.listItemText} />
      </ListItemButton>
    </Tooltip>
  );
};
