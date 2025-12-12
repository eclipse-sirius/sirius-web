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
import HelpOutlineIcon from '@mui/icons-material/HelpOutline';
import PersonOutlineIcon from '@mui/icons-material/PersonOutline';
import Chip from '@mui/material/Chip';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';
import Switch from '@mui/material/Switch';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import React from 'react';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { useConfirmationDialogSettings } from '../../confirmationDialogSettings/useConfirmationDialogSettings';
import { ProjectSettingsParams, ProjectSettingTabProps } from './ProjectSettingsView.types';

const useProjectGeneralSettingsStyles = makeStyles()((theme) => ({
  generalSettingsContainer: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(3),
  },
  section: {
    padding: theme.spacing(3),
    borderRadius: theme.spacing(1),
    width: 'fit-content',
  },
  sectionHeader: {
    marginBottom: theme.spacing(2),
  },
  sectionTitleContainer: {
    display: 'flex',
    alignItems: 'center',
    gap: theme.spacing(1),
  },
  sectionTitle: {
    fontWeight: 600,
  },
  personalBadge: {
    backgroundColor: `${theme.palette.primary.main}14`,
    border: 'none',
    color: theme.palette.primary.main,
    '& .MuiChip-icon': {
      color: theme.palette.primary.main,
    },
  },
  sectionSubtitle: {
    color: theme.palette.text.secondary,
    marginTop: theme.spacing(0.5),
  },
  settingItem: {
    paddingTop: theme.spacing(2),
  },
  settingRow: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  settingLabelContainer: {
    display: 'flex',
    alignItems: 'center',
    gap: theme.spacing(0.5),
  },
  settingLabel: {
    fontWeight: 500,
  },
  infoIcon: {
    fontSize: '1rem',
    color: theme.palette.text.secondary,
    cursor: 'help',
  },
  settingDescription: {
    color: theme.palette.text.secondary,
    marginTop: theme.spacing(0.5),
    wordWrap: 'break-word',
    overflowWrap: 'break-word',
  },
}));

export const ProjectGeneralSettingsView = ({}: ProjectSettingTabProps) => {
  const { classes } = useProjectGeneralSettingsStyles();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'projectGeneralSettingsView' });
  const { projectId } = useParams<ProjectSettingsParams>();
  const {
    isDisabled: value,
    setIsDisabled: setValue,
    canBeDisabled: allowConfirmationDisabled,
  } = useConfirmationDialogSettings(projectId);

  const handleToggle = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue(!event.target.checked);
  };

  return (
    <div className={classes.generalSettingsContainer}>
      <Typography variant="h3">{t('pageTitle')}</Typography>
      <Paper className={classes.section}>
        <div className={classes.sectionHeader}>
          <div className={classes.sectionTitleContainer}>
            <Typography variant="h5" className={classes.sectionTitle}>
              {t('title')}
            </Typography>
            <Chip
              icon={<PersonOutlineIcon />}
              label={t('personalBadge')}
              variant="outlined"
              size="small"
              className={classes.personalBadge}
            />
          </div>
          <Typography variant="body2" className={classes.sectionSubtitle}>
            {t('sectionDescription')}
          </Typography>
        </div>
        <Divider />
        <div className={classes.settingItem}>
          <div className={classes.settingRow}>
            <div className={classes.settingLabelContainer}>
              <Typography className={classes.settingLabel}>{t('enableConfirmationDialogs')}</Typography>
              <Tooltip title={t('enableConfirmationDialogsTooltip')}>
                <HelpOutlineIcon className={classes.infoIcon} />
              </Tooltip>
            </div>
            <Switch
              checked={!(value ?? false)}
              onChange={handleToggle}
              disabled={!allowConfirmationDisabled}
              color="primary"
              data-testid="disable-confirmation-dialogs"
            />
          </div>
          <Typography variant="body2" className={classes.settingDescription}>
            {t('enableConfirmationDialogsDescription')}
          </Typography>
        </div>
      </Paper>
    </div>
  );
};
