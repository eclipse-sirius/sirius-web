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
import { gql } from '@apollo/client';
import { FileUpload, Form, FormContainer, sendFile } from '@eclipse-sirius/sirius-components';
import { ServerContext, ServerContextValue, Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useContext } from 'react';
import { Redirect } from 'react-router-dom';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import {
  SchemaValue,
  UploadProjectEvent,
  UploadProjectViewContext,
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

const useUploadProjectViewStyles = makeStyles((theme) => ({
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
  buttons: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'start',
  },
}));

export const UploadProjectView = () => {
  const classes = useUploadProjectViewStyles();
  const [{ value, context }, dispatch] = useMachine<UploadProjectViewContext, UploadProjectEvent>(uploadProjectMachine);
  const { uploadProjectView, toast } = value as SchemaValue;
  const { file, newProjectId, message } = context;

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const onUploadProject = async (event) => {
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
        dispatch({ type: 'SHOW_TOAST', message: 'An unexpected error has occurred, please refresh the page' });
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
      dispatch({ type: 'SHOW_TOAST', message: 'An unexpected error has occurred, please refresh the page' });
    }
  };

  const onFileSelected = (file: File) => {
    dispatch({ type: 'HANDLE_SELECTED_FILE', file });
  };

  if (uploadProjectView === 'success') {
    return <Redirect to={`/projects/${newProjectId}/edit`} push />;
  }
  return (
    <div className={classes.uploadProjectView}>
      <NavigationBar />
      <main className={classes.main}>
        <Container maxWidth="sm">
          <FormContainer title="Upload a project" subtitle="Start with an existing project">
            <Form onSubmit={onUploadProject} encType="multipart/form-data">
              <FileUpload onFileSelected={onFileSelected} data-testid="file" />
              <div className={classes.buttons}>
                <Button
                  variant="contained"
                  type="submit"
                  color="primary"
                  disabled={uploadProjectView !== 'fileSelected'}
                  data-testid="upload-project">
                  Upload
                </Button>
              </div>
            </Form>
          </FormContainer>
        </Container>
      </main>
      <Toast message={message} open={toast === 'visible'} onClose={() => dispatch({ type: 'HIDE_TOAST' })} />
    </div>
  );
};
