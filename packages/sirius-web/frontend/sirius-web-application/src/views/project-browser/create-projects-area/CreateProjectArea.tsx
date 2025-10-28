/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { CreateProjectAreaProps, CreateProjectAreaState } from './CreateProjectArea.types';
import { createProjectAreaCardExtensionPoint } from './CreateProjectAreaExtensionPoints';
import { NewProjectCard } from './NewProjectCard';
import { ProjectTemplateCard } from './ProjectTemplateCard';
import { redirectUrlFromTemplate } from './redirectUrlFromTemplate';
import { ShowAllProjectTemplatesCard } from './ShowAllProjectTemplatesCard';
import { UploadProjectCard } from './UploadProjectCard';
import { useCreateProjectFromTemplate } from './useCreateProjectFromTemplate';
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

export const CreateProjectArea = ({}: CreateProjectAreaProps) => {
  const { classes } = useCreateProjectAreaStyles();
  const navigate = useNavigate();

  const createProjectAreaCards = useComponents(createProjectAreaCardExtensionPoint);

  const limit = Math.max(0, 6 - createProjectAreaCards.length);
  const [state, setState] = useState<CreateProjectAreaState>({
    page: 0,
    limit,
    runningTemplate: null,
  });

  const { data } = useProjectTemplates(state.page, state.limit, ProjectTemplateContext.PROJECT_BROWSER);
  const projectTemplates: GQLProjectTemplate[] = data?.viewer.projectTemplates.edges.map((edge) => edge.node) ?? [];

  const { createProjectFromTemplate, loading, projectCreatedFromTemplate } = useCreateProjectFromTemplate();

  const onCreateProject = (template: GQLProjectTemplate) => {
    if (!state.runningTemplate) {
      createProjectFromTemplate(template.label, template.id, template.natures);
      setState((prevState) => ({ ...prevState, runningTemplate: template }));
    }
  };

  useEffect(() => {
    if (projectCreatedFromTemplate) {
      const newUrl = redirectUrlFromTemplate(projectCreatedFromTemplate);
      navigate(newUrl, { replace: true });
    }
  }, [projectCreatedFromTemplate]);

  return (
    <>
      <div className={classes.createProjectArea}>
        <div className={classes.header}>
          <Typography variant="h4">Create a new project</Typography>
        </div>
        <div className={classes.content}>
          {projectTemplates.map((template) =>
            template.id === 'create-project' ? (
              <NewProjectCard key={template.id} />
            ) : template.id === 'upload-project' ? (
              <UploadProjectCard key={template.id} />
            ) : template.id === 'browse-all-project-templates' ? (
              <ShowAllProjectTemplatesCard key={template.id} />
            ) : (
              <ProjectTemplateCard
                template={template}
                disabled={loading}
                running={template.id === state.runningTemplate?.id}
                onCreateProject={() => onCreateProject(template)}
                key={template.id}
              />
            )
          )}
          {createProjectAreaCards.map(({ Component: Card }, index) => (
            <Card key={index} />
          ))}
        </div>
      </div>
    </>
  );
};
