/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { GraphQLClient } from 'common/GraphQLClient';
import { FileUpload } from 'core/file-upload/FileUpload';
import { Form } from 'core/form/Form';
import gql from 'graphql-tag';
import React, { useContext } from 'react';
import { Redirect } from 'react-router-dom';
import { FormContainer } from 'views/FormContainer';
import { View } from 'views/View';
import {
  SchemaValue,
  UploadProjectEvent,
  uploadProjectMachine,
  UploadProjectViewContext,
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
  buttons: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'start',
  },
}));

export const UploadProjectView = () => {
  const classes = useUploadProjectViewStyles();
  const { graphQLHttpClient } = useContext(GraphQLClient);
  const [{ value, context }, dispatch] = useMachine<UploadProjectViewContext, UploadProjectEvent>(uploadProjectMachine);
  const { uploadProjectView, toast } = value as SchemaValue;
  const { file, newProjectId, message } = context;

  const onUploadProject = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        file: null,
      },
    };

    try {
      dispatch({ type: 'HANDLE_UPLOAD' });
      const response = await graphQLHttpClient.sendFile(uploadProjectMutation, variables, file);
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
    return <Redirect to={`/projects/${newProjectId}/edit`} />;
  }
  return (
    <View condensed>
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
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' })}
        message={message}
        data-testid="snackbar"
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={() => dispatch({ type: 'HIDE_TOAST' })}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
      />
    </View>
  );
};
