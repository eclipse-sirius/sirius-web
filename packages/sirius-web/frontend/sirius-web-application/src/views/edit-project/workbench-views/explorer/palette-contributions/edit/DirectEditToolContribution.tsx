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
import EditIcon from '@mui/icons-material/Edit';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';

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

export const DirectEditToolContribution = ({}: PaletteToolContributionComponentProps) => {
  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'renameMenuItem' });
  const { item, readOnly, onDirectEditClick } = useContext<TreePaletteContextValue>(TreePaletteContext);
  const { classes } = useStyle();

  if (!item.editable) {
    return null;
  }

  return (
    <Tooltip title={t('rename')} placement="right">
      <ListItemButton
        className={classes.listItemButton}
        disabled={readOnly}
        onClick={onDirectEditClick}
        data-testid={`rename-tree-item`}>
        <ListItemIcon className={classes.listItemIcon}>
          <EditIcon />
        </ListItemIcon>
        <ListItemText primary={t('rename')} className={classes.listItemText} />
      </ListItemButton>
    </Tooltip>
  );
};
