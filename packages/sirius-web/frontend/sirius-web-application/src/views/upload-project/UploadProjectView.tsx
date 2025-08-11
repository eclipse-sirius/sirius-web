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
import { gql } from '@apollo/client';
import { ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import { useContext, useState } from 'react';
import { Navigate } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { FileUpload } from '../../core/file-upload/FileUpload';
import { sendFile } from '../../core/sendFile';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { useCurrentViewer } from '../../viewer/useCurrentViewer';
import {
  GQLUploadProjectPayload,
  GQLUploadProjectSuccessPayload,
  UploadProjectViewState,
} from './UploadProjectView.types';

const uploadProjectMutation = gql`
  mutation uploadProject($input: UploadProjectInput!) {
    uploadProject(input: $input) {
      __typename
      ... on UploadProjectSuccessPayload {
        project {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

const useUploadProjectViewStyles = makeStyles()((theme) => ({
  uploadProjectViewContainer: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(8),
  },
  uploadProjectView: {
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

const isUploadProjectSuccessPayload = (payload: GQLUploadProjectPayload): payload is GQLUploadProjectSuccessPayload =>
  payload && payload.__typename === 'UploadProjectSuccessPayload';

export const UploadProjectView = () => {
  const { classes } = useUploadProjectViewStyles();
  const { addErrorMessage } = useMultiToast();
  const [state, setState] = useState<UploadProjectViewState>({
    file: null,
    loading: false,
    newProjectId: null,
  });

  const {
    viewer: {
      capabilities: {
        projects: { canUpload },
      },
    },
  } = useCurrentViewer();

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const onUpload = (event: React.FormEvent<HTMLFormElement>) => {
    setState((prevState) => ({
      ...prevState,
      loading: true,
    }));
    onUploadProject(event);
  };

  const onUploadProject = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const variables = {
      input: {
        id: crypto.randomUUID(),
        file: null,
      },
    };

    try {
      const response = await sendFile(httpOrigin, uploadProjectMutation, variables, state.file);
      const { data, error } = response as any;
      if (error) {
        addErrorMessage('An unexpected error has occurred, the file uploaded may be too large');
      }
      if (data) {
        const { uploadProject } = data;
        if (isUploadProjectSuccessPayload(uploadProject)) {
          setState((prevState) => ({
            ...prevState,
            newProjectId: uploadProject.project.id,
            loading: false,
          }));
        } else {
          setState((prevState) => ({
            ...prevState,
            loading: false,
          }));
          addErrorMessage(uploadProject.message);
        }
      }
    } catch (exception) {
      addErrorMessage('An unexpected error has occurred, the file uploaded may be too large');
    }
  };

  const onFileSelected = (file: File) => {
    setState((prevState) => ({
      ...prevState,
      file: file,
    }));
  };

  if (!canUpload) {
    return <Navigate to={'/errors/404'} />;
  }

  if (state.newProjectId) {
    return <Navigate to={`/projects/${state.newProjectId}/edit`} />;
  }

  return (
    <div className={classes.uploadProjectView}>
      <NavigationBar />
      <main className={classes.main}>
        <Container maxWidth="sm">
          <div className={classes.uploadProjectViewContainer}>
            <div className={classes.titleContainer}>
              <Typography variant="h2" align="center" gutterBottom>
                Upload a project
              </Typography>
              <Typography variant="h4" align="center" gutterBottom>
                Start with an existing project
              </Typography>
            </div>
            <Paper>
              <form onSubmit={onUpload} encType="multipart/form-data" className={classes.form}>
                <FileUpload onFileSelected={onFileSelected} data-testid="file" />
                <div className={classes.buttons}>
                  <Button
                    variant="contained"
                    type="submit"
                    color="primary"
                    disabled={!state.file}
                    loading={state.loading}
                    data-testid="upload-project">
                    Upload
                  </Button>
                </div>
              </form>
            </Paper>
          </div>
        </Container>
      </main>
    </div>
  );
};
