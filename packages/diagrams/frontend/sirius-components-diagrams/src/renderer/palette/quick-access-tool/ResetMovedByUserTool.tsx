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
import NearMeDisabledIcon from '@mui/icons-material/NearMeDisabled';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { useResetMovedByUser } from '../../move/useResetMovedByUser';
import { ResetMovedByUserToolProps } from './ResetMovedByUserTool.types';

const useStyle = makeStyles()((theme) => ({
  toolIcon: {
    minWidth: theme.spacing(3),
    minHeight: theme.spacing(3),
    color: theme.palette.text.primary,
    padding: 0,
  },
}));

export const ResetMovedByUserTool = ({ diagramElementId }: ResetMovedByUserToolProps) => {
  const { classes } = useStyle();
  const { resetMovedByUser } = useResetMovedByUser();
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'resetMovedByUserTool' });
  return (
    <Tooltip title={t('resetMove')} key={'tooltip_reset_move_by_user_tool'}>
      <IconButton
        className={classes.toolIcon}
        size="small"
        aria-label={t('resetMove')}
        onClick={() => resetMovedByUser(diagramElementId)}
        data-testid="reset-move">
        <NearMeDisabledIcon sx={{ fontSize: 16 }} />
      </IconButton>
    </Tooltip>
  );
};
