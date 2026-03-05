/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
import SyncLockOutlinedIcon from '@mui/icons-material/SyncLockOutlined';
import SyncOutlinedIcon from '@mui/icons-material/SyncOutlined';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { useTranslation } from 'react-i18next';
import { SynchronizationButtonProps } from './SynchronizationButton.types';

export const SynchronizationButton = ({ pinned, onClick }: SynchronizationButtonProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'synchronizationButton' });
  const tooltipTitle = pinned ? t('tooltipPinned') : t('tooltipUnpinned');
  return (
    <Tooltip
      title={tooltipTitle}
      placement="left" // Needed to prevent a (temporary) overflow of the whole page
    >
      <IconButton
        size="small"
        aria-label={tooltipTitle}
        color="inherit"
        onClick={onClick}
        data-testid="details-toggle-pin">
        {pinned ? <SyncLockOutlinedIcon /> : <SyncOutlinedIcon />}
      </IconButton>
    </Tooltip>
  );
};
