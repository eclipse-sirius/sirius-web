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
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { ProjectSettingTabProps } from '../ProjectSettingsView.types';
import {
  ProjectAppearancesSettingsParams,
  ProjectAppearancesSettingsViewState,
} from './ProjectAppearancesSettingsView.types';
import { ProjectStyleCustomizationsTable } from './ProjectStyleCustomizationsTable';
import { useProjectStyleCustomizations } from './useProjectStyleCustomizations';
import { GQLStyleCustomization } from './useProjectStyleCustomizations.types';

export const ProjectAppearancesSettingsView = ({}: ProjectSettingTabProps) => {
  const { t } = useTranslation('sirius-web-projects-stylecustomizations-application', {
    keyPrefix: 'projectAppearancesSettings',
  });
  const { projectId } = useParams<ProjectAppearancesSettingsParams>();

  const [state, setState] = useState<ProjectAppearancesSettingsViewState>({
    pageSize: 20,
    startCursor: null,
    endCursor: null,
  });

  const { data, loading } = useProjectStyleCustomizations(
    projectId,
    state.startCursor,
    state.endCursor,
    state.pageSize
  );

  const onPreviousPage = () => {
    setState((prevState) => ({
      ...prevState,
      startCursor: null,
      endCursor: data?.viewer.project?.styleCustomizations.pageInfo.startCursor ?? null,
    }));
  };

  const onNextPage = () => {
    setState((prevState) => ({
      ...prevState,
      startCursor: data?.viewer.project?.styleCustomizations.pageInfo.endCursor ?? null,
      endCursor: null,
    }));
  };

  const onPageSizeChange = (pageSize: number) =>
    setState((prevState) => ({
      ...prevState,
      pageSize,
      startCursor: null,
      endCursor: null,
    }));

  const hasPreviousPage = data?.viewer.project?.styleCustomizations.pageInfo.hasPreviousPage ?? false;
  const hasNextPage = data?.viewer.project?.styleCustomizations.pageInfo.hasNextPage ?? false;
  const count = data?.viewer.project?.styleCustomizations.pageInfo.count ?? 0;
  const styleCustomizations: GQLStyleCustomization[] =
    data?.viewer.project?.styleCustomizations.edges.map((edge) => edge.node) ?? [];

  return (
    <Box sx={{ display: 'flex', flexDirection: 'column', gap: (theme) => theme.spacing(3) }}>
      <Box sx={{ display: 'flex', flexDirection: 'column', gap: (theme) => theme.spacing(1) }}>
        <Typography variant="h3">{t('title')}</Typography>
        <Typography variant="h6" sx={{ color: (theme) => theme.palette.text.secondary, fontWeight: 100 }}>
          {t('description')}
        </Typography>
      </Box>
      <ProjectStyleCustomizationsTable
        styleCustomizations={styleCustomizations}
        loading={loading}
        rowCount={count}
        hasPreviousPage={hasPreviousPage}
        hasNextPage={hasNextPage}
        onPreviousPage={onPreviousPage}
        onNextPage={onNextPage}
        pageSize={state.pageSize}
        onPageSizeChange={onPageSizeChange}
      />
    </Box>
  );
};
