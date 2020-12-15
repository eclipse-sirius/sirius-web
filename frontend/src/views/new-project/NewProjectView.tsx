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
import { useMutation } from '@apollo/client';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import TextField from '@material-ui/core/TextField';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { Form } from 'core/form/Form';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import { Redirect } from 'react-router-dom';
import { FormContainer } from 'views/FormContainer';
import { View } from 'views/View';
import {
  ChangeNameEvent,
  HandleErrorEvent,
  HandleSuccessEvent,
  HideToastEvent,
  NewProjectEvent,
  NewProjectViewContext,
  newProjectViewMachine,
  SchemaValue,
  ShowToastEvent,
  SubmitEvent,
} from './NewProjectViewMachine';

const createProjectMutation = gql`
  mutation createProject($input: CreateProjectInput!) {
    createProject(input: $input) {
      __typename
      ... on CreateProjectSuccessPayload {
        project {
          id
          owner {
            id
            username
          }
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

/**
 * Communicates with the server to handle the operations related to the
 * new project page.
 *
 * @author hmarchadour
 * @author sbegaudeau
 * @author lfasani
 */
export const NewProjectView = () => {
  const [createProject, { loading, data, error }] = useMutation(createProjectMutation);
  const [{ value, context }, dispatch] = useMachine<NewProjectViewContext, NewProjectEvent>(newProjectViewMachine);
  const { newProjectView, toast } = value as SchemaValue;
  const { name, newProjectId, message, isValidName, nameMessage } = context;

  const onNameChange = (event) => {
    const value = event.target.value;
    const changeNameEvent: ChangeNameEvent = { type: 'CHANGE_NAME', name: value };
    dispatch(changeNameEvent);
  };

  const onCreateNewProject = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        name: name.trim(),
        visibility: 'PUBLIC',
      },
    };
    const submitEvent: SubmitEvent = { type: 'SUBMIT' };
    dispatch(submitEvent);
    createProject({ variables });
  };

  useEffect(() => {
    if (!loading) {
      if (error) {
        const { message } = error;
        const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
        dispatch(showToastEvent);
      }
      if (data) {
        const typename = data.createProject.__typename;
        if (typename === 'CreateProjectSuccessPayload') {
          const { id } = data.createProject.project;
          const HandleSuccessEvent: HandleSuccessEvent = { type: 'HANDLE_SUCCESS', id };
          dispatch(HandleSuccessEvent);
        } else if (typename === 'ErrorPayload') {
          const { message } = data.createProject;
          const handlErrorEvent: HandleErrorEvent = { type: 'HANDLE_ERROR' };
          dispatch(handlErrorEvent);
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, dispatch]);

  if (newProjectView === 'success') {
    return <Redirect to={`/projects/${newProjectId}/edit`} />;
  }

  const canBeSubmitted = newProjectView === 'valid';

  return (
    <View condensed>
      <FormContainer title="Create a new project" subtitle="Get started by creating a new project">
        <Form onSubmit={onCreateNewProject}>
          <TextField
            error={!isValidName}
            helperText={nameMessage}
            label="Name"
            name="name"
            value={name}
            placeholder="Enter the project name"
            data-testid="name"
            autoFocus={true}
            onChange={onNameChange}
          />
          <Button
            variant="outlined"
            type="submit"
            disabled={!canBeSubmitted}
            data-testid="create-project"
            color="primary">
            Create
          </Button>
        </Form>
      </FormContainer>
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
    </View>
  );
};
