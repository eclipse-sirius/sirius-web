/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import { useComponents } from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { CreateProjectAreaProps } from './CreateProjectArea.types';
import { createProjectAreaCardExtensionPoint } from './CreateProjectAreaExtensionPoints';
import { NewProjectCard } from './NewProjectCard';
import { ShowAllProjectTemplatesCard } from './ShowAllProjectTemplatesCard';
import { UploadProjectCard } from './UploadProjectCard';
import { useProjectTemplates } from './useProjectTemplates';
import { GQLProjectTemplate, ProjectTemplateContext } from './useProjectTemplates.types';

const useCreateProjectAreaStyles = makeStyles()((theme) => ({
  createProjectArea: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(5),
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  content: {
    display: 'grid',
    gap: theme.spacing(1),
    gridTemplateColumns: 'repeat(6, 1fr)',
  },
}));

const MAX_CARDS = 6;

export const CreateProjectArea = ({}: CreateProjectAreaProps) => {
  const { classes } = useCreateProjectAreaStyles();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'createProjectArea' });

  const createProjectAreaCards = useComponents(createProjectAreaCardExtensionPoint);
  const { data } = useProjectTemplates(
    0,
    Math.max(0, MAX_CARDS - createProjectAreaCards.length),
    ProjectTemplateContext.PROJECT_BROWSER
  );
  const projectTemplates: GQLProjectTemplate[] = data?.viewer.projectTemplates.edges.map((edge) => edge.node) ?? [];

  return (
    <div className={classes.createProjectArea}>
      <div className={classes.header}>
        <Typography variant="h4">{t('createNewProject')}</Typography>
      </div>
      <div className={classes.content}>
        {projectTemplates.map((template) =>
          template.id === 'upload-project' ? (
            <UploadProjectCard key={template.id} />
          ) : template.id === 'browse-all-project-templates' ? (
            <ShowAllProjectTemplatesCard key={template.id} />
          ) : (
            <NewProjectCard key={template.id} url={`/new/project?templateId=${template.id}`} template={template} />
          )
        )}
        {createProjectAreaCards.map(({ Component: Card }, index) => (
          <Card key={index} />
        ))}
      </div>
    </div>
  );
};
