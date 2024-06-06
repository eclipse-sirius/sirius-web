/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Pagination from '@material-ui/lab/Pagination';
import gql from 'graphql-tag';
import { useEffect, useState } from 'react';
import { Redirect } from 'react-router-dom';
import { NewProjectCard } from './NewProjectCard';
import { ProjectTemplateCard } from './ProjectTemplateCard';
import { ProjectTemplatesModalProps, ProjectTemplatesModalState } from './ProjectTemplatesModal.types';
import { UploadProjectCard } from './UploadProjectCard';
import { redirectUrlFromTemplate } from './redirectUrlFromTemplate';
import { useCreateProjectFromTemplate } from './useCreateProjectFromTemplate';
import { useProjectTemplates } from './useProjectTemplates';
import { GQLProjectTemplate } from './useProjectTemplates.types';

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

const useProjectTemplatesModalStyles = makeStyles((theme) => ({
  content: {
    display: 'grid',
    gridTemplateRows: '1fr min-content',
    gap: theme.spacing(2),
  },
  templateCards: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr 1fr',
    gridTemplateRows: '1fr 1fr 1fr 1fr',
  },
  navigation: {
    justifySelf: 'center',
  },
}));

export const ProjectTemplatesModal = ({ onClose }: ProjectTemplatesModalProps) => {
  const styles = useProjectTemplatesModalStyles();

  const [state, setState] = useState<ProjectTemplatesModalState>({
    page: 0,
    limit: 12,
    runningTemplate: null,
    projectTemplates: null,
    count: null,
  });

  // Index of the last page, including all templates *and* the 2 special cards
  const lastPage: number = state.count ? Math.ceil((state.count + 2) / 12) : 0;
  // Index of the last page which actually contains templates (and not just special cards)
  const lastPageWithTemplates: number = state.count ? Math.ceil(state.count / 12) : 0;

  const skip: boolean = state.projectTemplates !== null && state.page > lastPageWithTemplates;

  const { data, loading } = useProjectTemplates(state.page, state.limit, skip);

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
    return <Redirect to={redirectUrl} />;
  }

  const cards: JSX.Element[] = [];
  if (state.projectTemplates && state.page <= lastPageWithTemplates) {
    state.projectTemplates
      .map((template) => (
        <ProjectTemplateCard
          template={template}
          disabled={createProjectLoading}
          running={template === state.runningTemplate}
          onCreateProject={() => createProjectFromTemplate(template.id)}
          key={template.id}
        />
      ))
      .forEach((card: JSX.Element) => cards.push(card));
    if (cards.length < 12) {
      cards.push(<NewProjectCard key="new-project" />);
    }
    if (cards.length < 12) {
      cards.push(<UploadProjectCard key="upload-project" />);
    }
  } else if (state.count % 12 === 11) {
    cards.push(<UploadProjectCard key="upload-project" />);
  } else if (state.count % 12 === 0) {
    cards.push(<NewProjectCard key="new-project" />);
    cards.push(<UploadProjectCard key="upload-project" />);
  }

  let content: JSX.Element;
  if (loading) {
    content = <Typography>Loading...</Typography>;
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
      <DialogTitle id="dialog-title">Select a project template</DialogTitle>
      <DialogContent className={styles.content}>{content}</DialogContent>
    </Dialog>
  );
};
