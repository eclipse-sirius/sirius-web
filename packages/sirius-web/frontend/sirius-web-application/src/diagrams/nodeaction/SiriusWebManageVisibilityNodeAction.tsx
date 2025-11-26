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
import {
  ActionProps,
  ManageVisibilityContext,
  ManageVisibilityContextValue,
} from '@eclipse-sirius/sirius-components-diagrams';
import VisibilityIcon from '@mui/icons-material/Visibility';
import IconButton from '@mui/material/IconButton';
import { Theme } from '@mui/material/styles';
import { useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';

const useToolStyle = makeStyles()((theme: Theme) => ({
  actionIcon: {
    '&:hover': {
      backgroundColor: theme.palette.action.selected,
    },
  },
}));

export const SiriusWebManageVisibilityNodeAction = ({ diagramElementId }: ActionProps) => {
  const { openDialog } = useContext<ManageVisibilityContextValue>(ManageVisibilityContext);
  const { classes } = useToolStyle();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'siriusWebManageVisibilityNodeAction' });
  return (
    <IconButton
      className={classes.actionIcon}
      size="small"
      color="inherit"
      aria-label={t('manageVisibility')}
      title={t('manageVisibility')}
      onClick={(event) => openDialog(event, diagramElementId)}
      data-testid="manage-visibility">
      <VisibilityIcon sx={{ fontSize: 16 }} />
    </IconButton>
  );
};
