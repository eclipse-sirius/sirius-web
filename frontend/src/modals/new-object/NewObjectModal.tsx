/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { useMutation, useQuery } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import IconButton from '@material-ui/core/IconButton';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import gql from 'graphql-tag';
import {
  GQLCreateChildMutationData,
  GQLCreateChildPayload,
  GQLErrorPayload,
  GQLGetChildCreationDescriptionsQueryData,
  GQLGetChildCreationDescriptionsQueryVariables,
  NewObjectModalProps,
} from 'modals/new-object/NewObjectModal.types';
import {
  ChangeChildCreationDescriptionEvent,
  CreateChildEvent,
  FetchedChildCreationDescriptionsEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewObjectModalContext,
  NewObjectModalEvent,
  newObjectModalMachine,
  SchemaValue,
  ShowToastEvent,
} from 'modals/new-object/NewObjectModalMachine';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';

const createChildMutation = gql`
  mutation createChild($input: CreateChildInput!) {
    createChild(input: $input) {
      __typename
      ... on CreateChildSuccessPayload {
        object {
          id
          label
          kind
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getChildCreationDescriptionsQuery = gql`
  query getChildCreationDescriptions($editingContextId: ID!, $classId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        childCreationDescriptions(classId: $classId) {
          id
          label
        }
      }
    }
  }
`;

const useNewObjectModalStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
}));

const isErrorPayload = (payload: GQLCreateChildPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const NewObjectModal = ({ editingContextId, item, onObjectCreated, onClose }: NewObjectModalProps) => {
  const classes = useNewObjectModalStyles();
  const [{ value, context }, dispatch] = useMachine<NewObjectModalContext, NewObjectModalEvent>(newObjectModalMachine);
  const { newObjectModal, toast } = value as SchemaValue;
  const { selectedChildCreationDescriptionId, childCreationDescriptions, objectToSelect, message } = context;

  const {
    loading: childCreationDescriptionsLoading,
    data: childCreationDescriptionsData,
    error: childCreationDescriptionsError,
  } = useQuery<GQLGetChildCreationDescriptionsQueryData, GQLGetChildCreationDescriptionsQueryVariables>(
    getChildCreationDescriptionsQuery,
    { variables: { editingContextId, classId: item.kind } }
  );
  useEffect(() => {
    if (!childCreationDescriptionsLoading) {
      if (childCreationDescriptionsError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (childCreationDescriptionsData) {
        const fetchChildCreationDescriptionsEvent: FetchedChildCreationDescriptionsEvent = {
          type: 'HANDLE_FETCHED_CHILD_CREATION_DESCRIPTIONS',
          data: childCreationDescriptionsData,
        };
        dispatch(fetchChildCreationDescriptionsEvent);
      }
    }
  }, [childCreationDescriptionsLoading, childCreationDescriptionsData, childCreationDescriptionsError, dispatch]);

  const onChildCreationDescriptionChange = (event) => {
    const value = event.target.value;
    const changeChildCreationDescriptionEvent: ChangeChildCreationDescriptionEvent = {
      type: 'CHANGE_CHILD_CREATION_DESCRIPTION',
      childCreationDescriptionId: value,
    };
    dispatch(changeChildCreationDescriptionEvent);
  };

  const [createChild, { loading: createChildLoading, data: createChildData, error: createChildError }] =
    useMutation<GQLCreateChildMutationData>(createChildMutation);
  useEffect(() => {
    if (!createChildLoading) {
      if (createChildError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (createChildData) {
        const handleResponseEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: createChildData };
        dispatch(handleResponseEvent);

        const { createChild } = createChildData;
        if (isErrorPayload(createChild)) {
          const { message } = createChild;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [createChildLoading, createChildData, createChildError, dispatch]);

  const onCreateObject = () => {
    dispatch({ type: 'CREATE_CHILD' } as CreateChildEvent);
    const input = {
      id: uuid(),
      editingContextId,
      objectId: item.id,
      childCreationDescriptionId: selectedChildCreationDescriptionId,
    };
    createChild({ variables: { input } });
  };

  useEffect(() => {
    if (newObjectModal === 'success') {
      onObjectCreated(objectToSelect);
    }
  }, [newObjectModal, onObjectCreated, objectToSelect]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Create a new object</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <InputLabel id="newObjectModalChildCreationDescriptionLabel">Object type</InputLabel>
            <Select
              value={selectedChildCreationDescriptionId}
              onChange={onChildCreationDescriptionChange}
              disabled={newObjectModal === 'loading' || newObjectModal === 'creatingChild'}
              labelId="newObjectModalChildCreationDescriptionLabel"
              fullWidth
              data-testid="childCreationDescription">
              {childCreationDescriptions.map((childCreationDescription) => (
                <MenuItem value={childCreationDescription.id} key={childCreationDescription.id}>
                  {childCreationDescription.label}
                </MenuItem>
              ))}
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={newObjectModal !== 'valid'}
            data-testid="create-object"
            color="primary"
            onClick={onCreateObject}>
            Create
          </Button>
        </DialogActions>
      </Dialog>
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
    </>
  );
};
