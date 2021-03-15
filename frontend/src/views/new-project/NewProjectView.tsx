/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { useMutation } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { useBranding } from 'common/BrandingContext';
import { Form } from 'core/form/Form';
import gql from 'graphql-tag';
import { LoggedInNavbar } from 'navbar/LoggedInNavbar';
import React, { useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import { v4 as uuid } from 'uuid';
import { FormContainer } from 'views/FormContainer';
import {
  GQLCreateProjectMutationData,
  GQLCreateProjectPayload,
  GQLErrorPayload,
} from 'views/new-project/NewProjectView.types';
import {
  ChangeNamedEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewProjectEvent,
  NewProjectViewContext,
  newProjectViewMachine,
  RequestProjectCreationEvent,
  SchemaValue,
  ShowToastEvent,
} from 'views/new-project/NewProjectViewMachine';

const createProjectMutation = gql`
  mutation createProject($input: CreateProjectInput!) {
    createProject(input: $input) {
      __typename
      ... on CreateProjectSuccessPayload {
        project {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const useNewProjectViewStyles = makeStyles((theme) => ({
  newProjectView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
  },
  buttons: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'start',
  },
}));

const isErrorPayload = (payload: GQLCreateProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const NewProjectView = () => {
  const classes = useNewProjectViewStyles();
  const { footer } = useBranding();
  const [{ value, context }, dispatch] = useMachine<NewProjectViewContext, NewProjectEvent>(newProjectViewMachine);
  const { newProjectView, toast } = value as SchemaValue;
  const { name, nameMessage, nameIsInvalid, message, newProjectId } = context;
  const [createProject, { loading, data, error }] = useMutation<GQLCreateProjectMutationData>(createProjectMutation);

  const onNameChange = (event) => {
    const value = event.target.value;
    const changeNameEvent: ChangeNamedEvent = { type: 'CHANGE_NAME', name: value };
    dispatch(changeNameEvent);
  };

  const onCreateNewProject = (event) => {
    event.preventDefault();
    const variables = {
      input: {
        id: uuid(),
        name: name.trim(),
        visibility: 'PUBLIC',
      },
    };
    const submitEvent: RequestProjectCreationEvent = { type: 'REQUEST_PROJECT_CREATION' };
    dispatch(submitEvent);
    createProject({ variables });
  };

  useEffect(() => {
    if (!loading) {
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (data) {
        const handleResponseEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data };
        dispatch(handleResponseEvent);

        const { createProject } = data;
        if (isErrorPayload(createProject)) {
          const { message } = createProject;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, dispatch]);

  if (newProjectView === 'success') {
    return <Redirect to={`/projects/${newProjectId}/edit`} push />;
  }

  return (
    <>
      <div className={classes.newProjectView}>
        <LoggedInNavbar />
        <main className={classes.main}>
          <Container maxWidth="sm">
            <FormContainer title="Create a new project" subtitle="Get started by creating a new project">
              <Form onSubmit={onCreateNewProject}>
                <TextField
                  error={nameIsInvalid}
                  helperText={nameMessage}
                  label="Name"
                  name="name"
                  value={name}
                  placeholder="Enter the project name"
                  data-testid="name"
                  autoFocus={true}
                  onChange={onNameChange}
                />
                <div className={classes.buttons}>
                  <Button
                    variant="contained"
                    type="submit"
                    disabled={newProjectView !== 'valid'}
                    data-testid="create-project"
                    color="primary">
                    Create
                  </Button>
                </div>
              </Form>
            </FormContainer>
          </Container>
        </main>
        {footer}
      </div>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
