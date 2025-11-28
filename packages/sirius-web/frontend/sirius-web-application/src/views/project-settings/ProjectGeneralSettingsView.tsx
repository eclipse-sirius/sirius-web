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
import FormControlLabel from '@mui/material/FormControlLabel';
import Paper from '@mui/material/Paper';
import Switch from '@mui/material/Switch';
import Typography from '@mui/material/Typography';
import React, { useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { ConfirmationSettingsContext } from '../../localSettings/ConfirmationSettingsContext';
import { ProjectSettingTabProps } from './ProjectSettingsView.types';

const useProjectGeneralSettingsStyles = makeStyles()((theme) => ({
  generalSettingsContainer: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(3),
  },
  section: {
    padding: theme.spacing(3),
  },
  sectionTitle: {
    marginBottom: theme.spacing(2),
  },
  settingItem: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(1),
  },
}));

export const ProjectGeneralSettingsView = ({}: ProjectSettingTabProps) => {
  const { classes } = useProjectGeneralSettingsStyles();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'projectGeneralSettingsView' });
  const {
    isDisabled: value,
    setIsDisabled: setValue,
    canBeDisabled: allowConfirmationDisabled,
  } = useContext(ConfirmationSettingsContext);

  const handleToggle = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(!event.target.checked);
  };

  return (
    <div className={classes.generalSettingsContainer}>
      <Paper className={classes.section}>
        <Typography variant="h5">{t('title')}</Typography>
        <Typography variant="h6" className={classes.sectionTitle}>
          {t('clientSettings')}
        </Typography>
        <div className={classes.settingItem}>
          <FormControlLabel
            control={
              <Switch
                checked={!(value ?? false)}
                onChange={handleToggle}
                disabled={!allowConfirmationDisabled}
                color="primary"
                data-testid="disable-confirmation-dialogs"
              />
            }
            label={t('enableConfirmationDialogs')}
          />
        </div>
      </Paper>
    </div>
  );
};
