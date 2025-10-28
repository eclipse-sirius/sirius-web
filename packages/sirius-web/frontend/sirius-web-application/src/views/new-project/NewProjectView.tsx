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

import { useComponent } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { useCurrentViewer } from '../../viewer/useCurrentViewer';
import { NewProjectViewState } from './NewProjectView.types';
import { useCreateProject } from './useCreateProject';

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

const isNameInvalid = (name: string) => name.trim().length < 3 || name.trim().length > 1024;

export const NewProjectView = () => {
  const { classes } = useNewProjectViewStyles();
  const navigate = useNavigate();

  const [state, setState] = useState<NewProjectViewState>({
    name: '',
  });

  const {
    viewer: {
      capabilities: {
        projects: { canCreate },
      },
    },
  } = useCurrentViewer();

  const { createProject, newProjectId } = useCreateProject();
  const { Component: Footer } = useComponent(footerExtensionPoint);

  const onNameChange = (event) => {
    const value = event.target.value;
    setState((prevState) => ({
      ...prevState,
      name: value,
    }));
  };

  const onCreateNewProject = (event) => {
    event.preventDefault();
    createProject(state.name.trim(), []);
  };

  useEffect(() => {
    if (!canCreate) {
      navigate('/errors/404');
    }
  }, [canCreate]);

  useEffect(() => {
    if (newProjectId) {
      navigate(`/projects/${newProjectId}/edit`);
    }
  }, [newProjectId]);

  const isError = isNameInvalid(state.name);
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
                    error={isError}
                    helperText="The name must contain between 3 and 1024 characters"
                    label="Name"
                    name="name"
                    value={state.name}
                    placeholder="Enter the project name"
                    slotProps={{
                      htmlInput: () => ({
                        'data-testid': 'name',
                      }),
                    }}
                    autoFocus={true}
                    onChange={onNameChange}
                  />
                  <div className={classes.buttons}>
                    <Button
                      variant="contained"
                      type="submit"
                      disabled={isError}
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
    </>
  );
};
