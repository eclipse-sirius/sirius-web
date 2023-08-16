/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import Paper from '@material-ui/core/Paper';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import { Footer } from '../../footer/Footer';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { GQLCreateProjectMutationData, GQLCreateProjectPayload, GQLErrorPayload } from './NewProjectView.types';
import {
  ChangeNameEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewProjectEvent,
  NewProjectViewContext,
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

const useNewProjectViewStyles = makeStyles((theme) => ({
  newProjectViewContainer: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(8),
  },
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
  const classes = useNewProjectViewStyles();
  const [{ value, context }, dispatch] = useMachine<NewProjectViewContext, NewProjectEvent>(newProjectViewMachine);
  const { newProjectView, toast } = value as SchemaValue;
  const { name, nameMessage, nameIsInvalid, message, newProjectId } = context;
  const [createProject, { loading, data, error }] = useMutation<GQLCreateProjectMutationData>(createProjectMutation);

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
  }, [loading, data, error, dispatch]);

  if (newProjectView === 'success') {
    return <Redirect to={`/projects/${newProjectId}/edit`} push />;
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
