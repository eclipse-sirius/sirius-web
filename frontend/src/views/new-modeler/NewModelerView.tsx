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
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { Form } from 'core/form/Form';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import { Redirect, useParams } from 'react-router-dom';
import { FormContainer } from 'views/FormContainer';
import { View } from 'views/View';
import {
  HandleChangedNameEvent,
  HandleCreateModelerEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewModelerEvent,
  NewModelerViewContext,
  newModelerViewMachine,
  SchemaValue,
  ShowToastEvent,
} from './NewModelerViewMachine';

const createModelerMutation = gql`
  mutation createModeler($input: CreateModelerInput!) {
    createModeler(input: $input) {
      __typename
      ... on CreateModelerSuccessPayload {
        modeler {
          id
          name
          status
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const useNewProjectViewStyles = makeStyles((theme) => ({
  buttons: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'start',
  },
}));
export const NewModelerView = () => {
  const { projectId } = useParams();
  const classes = useNewProjectViewStyles();
  const [{ value, context }, dispatch] = useMachine<NewModelerViewContext, NewModelerEvent>(newModelerViewMachine);
  const { newModelerView, toast } = value as SchemaValue;
  const { name, nameMessage, nameIsInvalid, message } = context;
  const [createProject, { loading, data, error }] = useMutation(createModelerMutation);

  const onNameChange = (event) => {
    const value = event.target.value;
    const changeNameEvent: HandleChangedNameEvent = { type: 'HANDLE_CHANGED_NAME', name: value };
    dispatch(changeNameEvent);
  };

  const onCreateNewModeler = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        name: name.trim(),
        projectId,
      },
    };
    const submitEvent: HandleCreateModelerEvent = { type: 'HANDLE_CREATE_MODELER' };
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

        const typename = data.createModeler.__typename;
        if (typename === 'ErrorPayload') {
          const { message } = data.createModeler;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, dispatch]);

  if (newModelerView === 'success') {
    return <Redirect to={`/projects/${projectId}/modelers`} />;
  }

  return (
    <View condensed>
      <FormContainer title="Create a new modeler" subtitle="Get started by creating a new modeler">
        <Form onSubmit={onCreateNewModeler}>
          <TextField
            error={nameIsInvalid}
            helperText={nameMessage}
            label="Name"
            name="name"
            value={name}
            placeholder="Enter the modeler name"
            data-testid="name"
            autoFocus={true}
            onChange={onNameChange}
          />
          <div className={classes.buttons}>
            <Button
              variant="contained"
              type="submit"
              disabled={newModelerView !== 'valid'}
              data-testid="create-modeler"
              color="primary">
              Create
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
