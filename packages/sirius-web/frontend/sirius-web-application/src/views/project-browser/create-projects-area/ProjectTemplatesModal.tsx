/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Pagination from '@mui/material/Pagination';
import Typography from '@mui/material/Typography';
import gql from 'graphql-tag';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Navigate } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { ProjectTemplateCard } from './ProjectTemplateCard';
import { ProjectTemplatesModalProps, ProjectTemplatesModalState } from './ProjectTemplatesModal.types';
import { redirectUrlFromTemplate } from './redirectUrlFromTemplate';
import { useCreateProjectFromTemplate } from './useCreateProjectFromTemplate';
import { useProjectTemplates } from './useProjectTemplates';
import { GQLProjectTemplate, ProjectTemplateContext } from './useProjectTemplates.types';

export const getProjectTemplatesQuery = gql`
  query getProjectTemplates($page: Int!) {
    viewer {
      projectTemplates(page: $page, limit: 12) {
        edges {
          node {
            id
            label
            imageURL
          }
        }
        pageInfo {
          count
        }
      }
    }
  }
`;

const useProjectTemplatesModalStyles = makeStyles()((theme) => ({
  content: {
    display: 'grid',
    gridTemplateRows: '1fr min-content',
    gap: theme.spacing(2),
  },
  templateCards: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr 1fr 1fr',
    gridTemplateRows: '1fr 1fr 1fr',
    alignItems: 'center',
    justifyItems: 'center',
    gap: theme.spacing(4),
  },
  navigation: {
    justifySelf: 'center',
  },
}));

export const ProjectTemplatesModal = ({ onClose }: ProjectTemplatesModalProps) => {
  const { classes: styles } = useProjectTemplatesModalStyles();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'projectTemplatesModal' });

  const [state, setState] = useState<ProjectTemplatesModalState>({
    page: 0,
    limit: 12,
    runningTemplate: null,
    projectTemplates: null,
    count: null,
  });

  // Index of the last page, including all templates *and* the 2 special cards
  const lastPage: number = state.count ? Math.ceil((state.count + 2) / 12) : 0;

  const skip: boolean = state.projectTemplates !== null;

  const { data, loading } = useProjectTemplates(
    state.page,
    state.limit,
    ProjectTemplateContext.PROJECT_TEMPLATE_MODAL,
    skip
  );

  useEffect(() => {
    if (data) {
      const projectTemplates: GQLProjectTemplate[] = data.viewer.projectTemplates.edges.map((edge) => edge.node) ?? [];
      const count = data.viewer.projectTemplates.pageInfo.count;
      setState((prevState) => ({ ...prevState, projectTemplates, count }));
    }
  }, [data]);

  const {
    createProjectFromTemplate,
    loading: createProjectLoading,
    projectCreatedFromTemplate,
  } = useCreateProjectFromTemplate();

  const redirectUrl: string | null = projectCreatedFromTemplate
    ? redirectUrlFromTemplate(projectCreatedFromTemplate)
    : null;
  if (redirectUrl) {
    return <Navigate to={redirectUrl} replace />;
  }

  const cards: JSX.Element[] = [];
  if (state.projectTemplates) {
    state.projectTemplates
      .map((template) => (
        <ProjectTemplateCard
          template={template}
          disabled={createProjectLoading}
          running={template === state.runningTemplate}
          onCreateProject={() => createProjectFromTemplate(template.label, template.id, template.natures)}
          key={template.id}
        />
      ))
      .forEach((card: JSX.Element) => cards.push(card));
  }

  let content: JSX.Element;
  if (loading) {
    content = <Typography>{t('messages.loading')}</Typography>;
  } else {
    content = (
      <>
        <div className={styles.templateCards} data-testid="project-templates-cards">
          {cards}
        </div>
        <Pagination
          className={styles.navigation}
          page={state.page + 1}
          count={lastPage}
          onChange={(_, value: number) => {
            const page: number = value;
            setState((prevState) => ({ ...prevState, page }));
          }}
        />
      </>
    );
  }

  return (
    <Dialog
      open={true}
      onClose={() => {
        if (!loading) {
          onClose();
        }
      }}
      aria-labelledby="dialog-title"
      data-testid="project-templates-modal"
      maxWidth="md"
      fullWidth>
      <DialogTitle id="dialog-title">{t('selectProjectTemplate')}</DialogTitle>
      <DialogContent className={styles.content}>{content}</DialogContent>
    </Dialog>
  );
};
