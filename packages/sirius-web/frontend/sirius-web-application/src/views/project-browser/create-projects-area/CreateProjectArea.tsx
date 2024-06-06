/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useState } from 'react';
import { Redirect } from 'react-router-dom';
import { CreateProjectAreaProps, CreateProjectAreaState } from './CreateProjectArea.types';
import { createProjectAreaCardExtensionPoint } from './CreateProjectAreaExtensionPoints';
import { ProjectTemplateCard } from './ProjectTemplateCard';
import { redirectUrlFromTemplate } from './redirectUrlFromTemplate';
import { useCreateProjectFromTemplate } from './useCreateProjectFromTemplate';
import { useProjectTemplates } from './useProjectTemplates';
import { GQLProjectTemplate } from './useProjectTemplates.types';

const useCreateProjectAreaStyles = makeStyles((theme) => ({
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
  const classes = useCreateProjectAreaStyles();

  const createProjectAreaCards = useComponents(createProjectAreaCardExtensionPoint);

  const limit = Math.max(0, 6 - createProjectAreaCards.length);
  const [state, setState] = useState<CreateProjectAreaState>({
    page: 0,
    limit,
    runningTemplate: null,
  });

  const { data } = useProjectTemplates(state.page, state.limit);
  const projectTemplates: GQLProjectTemplate[] = data?.viewer.projectTemplates.edges.map((edge) => edge.node) ?? [];

  const { createProjectFromTemplate, loading, projectCreatedFromTemplate } = useCreateProjectFromTemplate();

  const onCreateProject = (template: GQLProjectTemplate) => {
    if (!state.runningTemplate) {
      createProjectFromTemplate(template.id);
      setState((prevState) => ({ ...prevState, runningTemplate: template }));
    }
  };

  const redirectUrl: string | null = projectCreatedFromTemplate
    ? redirectUrlFromTemplate(projectCreatedFromTemplate)
    : null;
  if (redirectUrl) {
    return <Redirect to={redirectUrl} />;
  }

  return (
    <>
      <div className={classes.createProjectArea}>
        <div className={classes.header}>
          <Typography variant="h4">Create a new project</Typography>
        </div>
        <div className={classes.content}>
          {projectTemplates.map((template) => (
            <ProjectTemplateCard
              template={template}
              disabled={loading}
              running={template.id === state.runningTemplate?.id}
              onCreateProject={() => onCreateProject(template)}
              key={template.id}
            />
          ))}
          {createProjectAreaCards.map(({ Component: Card }, index) => (
            <Card key={index} />
          ))}
        </div>
      </div>
    </>
  );
};
