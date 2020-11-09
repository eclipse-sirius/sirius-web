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
import { GraphQLClient } from 'common/GraphQLClient';
import { Buttons, ActionButton, SecondaryButton } from 'core/button/Button';
import { Form } from 'core/form/Form';
import { FileUpload } from 'core/file-upload/FileUpload';
import React, { useContext, useReducer } from 'react';
import { Redirect } from 'react-router-dom';
import { FormContainer } from 'views/FormContainer';
import { View } from 'views/View';
import { HANDLE_SELECTED__ACTION, HANDLE_SUBMIT__ACTION, SELECTED__STATE, SUBMIT_SUCCESS__STATE } from './machine';
import { initialState, reducer } from './reducer';
import gql from 'graphql-tag';

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

export const UploadProjectView = () => {
  const { graphQLHttpClient } = useContext(GraphQLClient);
  const [state, dispatch] = useReducer(reducer, initialState);
  const { viewState, file, newProjectId } = state;

  const onUploadProject = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        file: null,
      },
    };
    try {
      const response = await graphQLHttpClient.sendFile(uploadProjectMutation, variables, file);
      const action = { type: HANDLE_SUBMIT__ACTION, response };
      dispatch(action);
    } catch (exception) {
      const action = { type: HANDLE_SUBMIT__ACTION, response: { error: exception.toString() } };
      dispatch(action);
    }
  };

  const onFileSelected = (file) => {
    const action = { type: HANDLE_SELECTED__ACTION, file };
    dispatch(action);
  };

  if (viewState === SUBMIT_SUCCESS__STATE) {
    return <Redirect to={`/projects/${newProjectId}/edit`} />;
  }

  const canSubmit = viewState === SELECTED__STATE;
  return (
    <View condensed>
      <FormContainer title="Upload a project" subtitle="Start with an existing project">
        <Form onSubmit={onUploadProject} encType="multipart/form-data">
          <FileUpload onFileSelected={onFileSelected} data-testid="file" />
          <Buttons>
            <ActionButton type="submit" disabled={!canSubmit} label="Upload" data-testid="upload-project" />
            <SecondaryButton type="submit" to={`/`} label="Cancel" data-testid="back" />
          </Buttons>
        </Form>
      </FormContainer>
    </View>
  );
};
