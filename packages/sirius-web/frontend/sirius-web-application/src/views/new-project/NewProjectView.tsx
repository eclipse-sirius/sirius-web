/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { Toast, useComponent } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { Navigate } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { StateMachine } from 'xstate';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { useCurrentViewer } from '../../viewer/useCurrentViewer';
import { GQLCreateProjectMutationData, GQLCreateProjectPayload, GQLErrorPayload } from './NewProjectView.types';
import {
  ChangeNameEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewProjectEvent,
  NewProjectViewContext,
  NewProjectViewStateSchema,
  RequestProjectCreationEvent,
  SchemaValue,
  ShowToastEvent,
  newProjectViewMachine,
} from './NewProjectViewMachine';

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

const useNewProjectViewStyles = makeStyles()((theme) => ({
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
  newProjectViewContainer: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(8),
  },
  titleContainer: {
    display: 'flex',
    flexDirection: 'column',
    paddingBottom: theme.spacing(2),
  },
  buttons: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'start',
  },
  form: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(1),
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2),
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
}));

const isErrorPayload = (payload: GQLCreateProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const NewProjectView = () => {
  const { classes } = useNewProjectViewStyles();
  const [{ value, context }, dispatch] =
    useMachine<StateMachine<NewProjectViewContext, NewProjectViewStateSchema, NewProjectEvent>>(newProjectViewMachine);
  const { newProjectView, toast } = value as SchemaValue;
  const { name, nameMessage, nameIsInvalid, message, newProjectId } = context;
  const [createProject, { loading, data, error }] = useMutation<GQLCreateProjectMutationData>(createProjectMutation);
  const { Component: Footer } = useComponent(footerExtensionPoint);
  const {
    viewer: {
      capabilities: {
        projects: { canCreate },
      },
    },
  } = useCurrentViewer();

  const onNameChange = (event) => {
    const value = event.target.value;
    const changeNameEvent: ChangeNameEvent = { type: 'CHANGE_NAME', name: value };
    dispatch(changeNameEvent);
  };

  const onCreateNewProject = (event) => {
    event.preventDefault();
    const variables = {
      input: {
        id: crypto.randomUUID(),
        name: name.trim(),
        natures: [],
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
  }, [loading, data, error]);

  if (!canCreate) {
    return <Navigate to={'/errors/404'} />;
  }

  if (newProjectView === 'success') {
    return <Navigate to={`/projects/${newProjectId}/edit`} />;
  }

  return (
    <>
      <div className={classes.newProjectView}>
        <NavigationBar />
        <main className={classes.main}>
          <Container maxWidth="sm">
            <div className={classes.newProjectViewContainer}>
              <div className={classes.titleContainer}>
                <Typography variant="h2" align="center" gutterBottom>
                  Create a new project
                </Typography>
                <Typography variant="h4" align="center" gutterBottom>
                  Get started by creating a new project
                </Typography>
              </div>
              <Paper>
                <form onSubmit={onCreateNewProject} className={classes.form}>
                  <TextField
                    variant="standard"
                    error={nameIsInvalid}
                    helperText={nameMessage}
                    label="Name"
                    name="name"
                    value={name}
                    placeholder="Enter the project name"
                    inputProps={{ 'data-testid': 'name' }}
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
                </form>
              </Paper>
            </div>
          </Container>
        </main>
        <Footer />
      </div>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
