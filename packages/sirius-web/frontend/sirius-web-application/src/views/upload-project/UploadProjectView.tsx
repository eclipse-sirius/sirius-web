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
import { ServerContext, ServerContextValue, Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Paper from '@mui/material/Paper';
import Typography from '@mui/material/Typography';
import { useMachine } from '@xstate/react';
import { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { StateMachine } from 'xstate';
import { FileUpload } from '../../core/file-upload/FileUpload';
import { sendFile } from '../../core/sendFile';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { useCurrentViewer } from '../../viewer/useCurrentViewer';
import {
  SchemaValue,
  UploadProjectEvent,
  UploadProjectViewContext,
  UploadProjectViewStateSchema,
  uploadProjectMachine,
} from './UploadProjectViewMachine';

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

export const UploadProjectView = () => {
  const { classes } = useUploadProjectViewStyles();
  const [{ value, context }, dispatch] =
    useMachine<StateMachine<UploadProjectViewContext, UploadProjectViewStateSchema, UploadProjectEvent>>(
      uploadProjectMachine
    );
  const { uploadProjectView, toast } = value as SchemaValue;
  const { file, newProjectId, message } = context;

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const {
    viewer: {
      capabilities: {
        projects: { canUpload },
      },
    },
  } = useCurrentViewer();

  const onUploadProject = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const variables = {
      input: {
        id: crypto.randomUUID(),
        file: null,
      },
    };

    try {
      dispatch({ type: 'HANDLE_UPLOAD' });
      const response = await sendFile(httpOrigin, uploadProjectMutation, variables, file);
      const { data, error } = response as any;
      if (error) {
        dispatch({
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, the file uploaded may be too large',
        });
      }
      if (data) {
        const typename = data.uploadProject.__typename;
        if (typename === 'UploadProjectSuccessPayload') {
          dispatch({ type: 'HANDLE_RESPONSE', data });
        } else if (typename === 'ErrorMessage') {
          dispatch({ type: 'SHOW_TOAST', message: data.uploadProject.message });
        }
      }
    } catch (exception) {
      dispatch({ type: 'SHOW_TOAST', message: 'An unexpected error has occurred, the file uploaded may be too large' });
    }
  };

  const onFileSelected = (file: File) => {
    dispatch({ type: 'HANDLE_SELECTED_FILE', file });
  };

  if (!canUpload) {
    return <Navigate to={'/errors/404'} />;
  }

  if (uploadProjectView === 'success') {
    return <Navigate to={`/projects/${newProjectId}/edit`} />;
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
              <form onSubmit={onUploadProject} encType="multipart/form-data" className={classes.form}>
                <FileUpload onFileSelected={onFileSelected} data-testid="file" />
                <div className={classes.buttons}>
                  <Button
                    variant="contained"
                    type="submit"
                    color="primary"
                    disabled={uploadProjectView !== 'fileSelected'}
                    loading={uploadProjectView === 'uploading'}
                    data-testid="upload-project">
                    Upload
                  </Button>
                </div>
              </form>
            </Paper>
          </div>
        </Container>
      </main>
      <Toast message={message} open={toast === 'visible'} onClose={() => dispatch({ type: 'HIDE_TOAST' })} />
    </div>
  );
};
