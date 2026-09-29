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
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { ProjectSettingTabProps } from '../ProjectSettingsView.types';
import { ProjectAppearancesSettingsParams } from './ProjectAppearancesSettingsView.types';
import { ProjectStyleCustomizationsTable } from './ProjectStyleCustomizationsTable';
import { useProjectStyleCustomizations } from './useProjectStyleCustomizations';
import { GQLProjectStyleCustomization } from './useProjectStyleCustomizations.types';

export const ProjectAppearancesSettingsView = ({}: ProjectSettingTabProps) => {
  const { t } = useTranslation('sirius-web-projects-stylecustomizations-application', {
    keyPrefix: 'projectAppearancesSettings',
  });
  const { projectId } = useParams<ProjectAppearancesSettingsParams>();
  const { data, loading } = useProjectStyleCustomizations(projectId);

  const styleCustomizations: GQLProjectStyleCustomization[] = data?.viewer.project?.styleCustomizations ?? [];

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', gap: (theme) => theme.spacing(3) }}>
      <Box sx={{ display: 'flex', flexDirection: 'column', gap: (theme) => theme.spacing(1) }}>
        <Typography variant="h3">{t('title')}</Typography>
        <Typography variant="h6" sx={{ color: (theme) => theme.palette.text.secondary, fontWeight: 100 }}>
          {t('description')}
        </Typography>
      </Box>
      <ProjectStyleCustomizationsTable styleCustomizations={styleCustomizations} loading={loading} />
    </Box>
  );
};
