/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { useMutation, useQuery } from '@apollo/client';
import { Toast } from '@eclipse-sirius/sirius-components-core';
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Pagination from '@material-ui/lab/Pagination';
import { useMachine } from '@xstate/react';
import gql from 'graphql-tag';
import { useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import {
  NewProjectCard,
  ProjectTemplateCard,
  UploadProjectCard,
} from '../../views/project-template-card/ProjectTemplateCard';
import {
  GQLCreateProjectFromTemplateMutationData,
  GQLCreateProjectFromTemplatePayload,
  GQLCreateProjectFromTemplateSuccessPayload,
  ProjectTemplate,
} from '../../views/projects/ProjectsView.types';
import { GQLErrorPayload, ProjectTemplatesModalProps } from './ProjectTemplatesModal.types';
import {
  ChangePageEvent,
  FetchedProjectTemplatesEvent,
  HideToastEvent,
  InvokeTemplateEvent,
  ProjectTemplatesModalContext,
  ProjectTemplatesModalEvent,
  projectTemplatesModalMachine,
  RedirectEvent,
  SchemaValue,
  ShowToastEvent,
} from './ProjectTemplatesModalMachine';

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

export const createProjectFromTemplateMutation = gql`
  mutation createProjectFromTemplate($input: CreateProjectFromTemplateInput!) {
    createProjectFromTemplate(input: $input) {
      __typename
      ... on CreateProjectFromTemplateSuccessPayload {
        project {
          id
        }
        representationToOpen {
          id
        }
      }
      ... on ErrorPayload {
        message
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

const isProjectTemplateErrorPayload = (payload: GQLCreateProjectFromTemplatePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isProjectTemplateSuccessPayload = (
  payload: GQLCreateProjectFromTemplatePayload
): payload is GQLCreateProjectFromTemplateSuccessPayload =>
  payload.__typename === 'CreateProjectFromTemplateSuccessPayload';

export const ProjectTemplatesModal = ({ onClose }: ProjectTemplatesModalProps) => {
  const [{ value, context }, dispatch] = useMachine<ProjectTemplatesModalContext, ProjectTemplatesModalEvent>(
    projectTemplatesModalMachine
  );
  const { toast, projectTemplatesModal } = value as SchemaValue;
  const { page, templates, templatesCount, runningTemplate, redirectURL, message } = context;

  // Index of the last page, including all templates *and* the 2 special cards
  const lastPage: number = templatesCount ? Math.ceil((templatesCount + 2) / 12) : 0;
  // Index of the last page which actually contains templates (and not just special cards)
  const lastPageWithTemplates: number = templatesCount ? Math.ceil(templatesCount / 12) : 0;

  const { loading, data, error, refetch } = useQuery(getProjectTemplatesQuery, {
    variables: { page: page - 1 },
    skip: !!templatesCount && page > lastPageWithTemplates,
  });
  useEffect(() => {
    if (!loading) {
      if (data) {
        const fetchedTemplatesEvent: FetchedProjectTemplatesEvent = { type: 'HANDLE_FETCHED_TEMPLATES', data: data };
        dispatch(fetchedTemplatesEvent);
      }
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
    }
  }, [loading, data, error, dispatch]);

  const [
    createProjectFromTemplate,
    { loading: templateExecuting, data: templateInvocationResult, error: templateInvocationError },
  ] = useMutation<GQLCreateProjectFromTemplateMutationData>(createProjectFromTemplateMutation);
  useEffect(() => {
    if (!templateExecuting) {
      if (templateInvocationResult) {
        if (isProjectTemplateSuccessPayload(templateInvocationResult.createProjectFromTemplate)) {
          const projectId = templateInvocationResult.createProjectFromTemplate.project.id;
          const representationId = templateInvocationResult.createProjectFromTemplate.representationToOpen?.id;
          const redirectEvent: RedirectEvent = { type: 'REDIRECT', projectId, representationId };
          dispatch(redirectEvent);
        } else if (isProjectTemplateErrorPayload(templateInvocationResult.createProjectFromTemplate)) {
          const { message } = templateInvocationResult.createProjectFromTemplate;
          const showToastEvent: ShowToastEvent = {
            type: 'SHOW_TOAST',
            message,
          };
          dispatch(showToastEvent);
        }
      }
    }
  }, [templateInvocationResult, templateInvocationError, templateExecuting, dispatch]);

  const styles = useProjectTemplatesModalStyles();

  if (redirectURL) {
    return <Redirect to={redirectURL} />;
  }

  const cards: JSX.Element[] = [];
  if (page <= lastPageWithTemplates) {
    templates
      .map((template: ProjectTemplate) => (
        <ProjectTemplateCard
          key={template.id}
          disabled={!!runningTemplate}
          running={template === runningTemplate}
          template={template}
          onCreateProject={() => {
            const event: InvokeTemplateEvent = { type: 'INVOKE_TEMPLATE', template };
            dispatch(event);
            const variables = {
              input: {
                id: crypto.randomUUID(),
                templateId: template.id,
              },
            };
            createProjectFromTemplate({ variables });
          }}
        />
      ))
      .forEach((card: JSX.Element) => cards.push(card));
    if (cards.length < 12) {
      cards.push(<NewProjectCard key="new-project" />);
    }
    if (cards.length < 12) {
      cards.push(<UploadProjectCard key="upload-project" />);
    }
  } else if (templatesCount % 12 === 11) {
    cards.push(<UploadProjectCard key="upload-project" />);
  } else if (templatesCount % 12 === 0) {
    cards.push(<NewProjectCard key="new-project" />);
    cards.push(<UploadProjectCard key="upload-project" />);
  }

  let content: JSX.Element;
  if (projectTemplatesModal === 'loading') {
    content = <Typography>Loading...</Typography>;
  } else {
    content = (
      <>
        <div className={styles.templateCards} data-testid="project-templates-cards">
          {cards}
        </div>
        <Pagination
          className={styles.navigation}
          page={page}
          count={lastPage}
          onChange={(_, value: number) => {
            const newPage = value;
            const changePageEvent: ChangePageEvent = { type: 'CHANGE_PAGE', page: newPage };
            dispatch(changePageEvent);
            if (newPage <= lastPageWithTemplates) {
              refetch();
            }
          }}
        />
      </>
    );
  }

  return (
    <>
      <Dialog
        open={true}
        onClose={() => {
          if (projectTemplatesModal !== 'executingTemplate') {
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
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
