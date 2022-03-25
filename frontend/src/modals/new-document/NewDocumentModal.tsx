/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import TextField from '@material-ui/core/TextField';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import gql from 'graphql-tag';
import {
  GQLCreateDocumentMutationData,
  GQLCreateDocumentPayload,
  GQLErrorPayload,
  GQLGetStereotypeDescriptionsQueryData,
  GQLGetStereotypeDescriptionsQueryVariables,
  NewDocumentModalProps,
} from 'modals/new-document/NewDocumentModal.types';
import {
  ChangeNameEvent,
  ChangeStereotypeDescriptionEvent,
  CreateDocumentEvent,
  FetchedStereotypeDescriptionsEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewDocumentModalContext,
  NewDocumentModalEvent,
  newDocumentModalMachine,
  SchemaValue,
  ShowToastEvent,
} from 'modals/new-document/NewDocumentModalMachine';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';

const createDocumentMutation = gql`
  mutation createDocument($input: CreateDocumentInput!) {
    createDocument(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getStereotypeDescriptionsQuery = gql`
  query getStereotypeDescriptions($editingContextId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        stereotypeDescriptions {
          edges {
            node {
              id
              label
            }
          }
        }
      }
    }
  }
`;

const useNewDocumentModalStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
}));

const isErrorPayload = (payload: GQLCreateDocumentPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const NewDocumentModal = ({ editingContextId, onClose }: NewDocumentModalProps) => {
  const classes = useNewDocumentModalStyles();
  const [{ value, context }, dispatch] = useMachine<NewDocumentModalContext, NewDocumentModalEvent>(
    newDocumentModalMachine
  );
  const { newDocumentModal, toast } = value as SchemaValue;
  const { name, nameMessage, nameIsInvalid, selectedStereotypeDescriptionId, stereotypeDescriptions, message } =
    context;

  const {
    loading: stereotypeDescriptionsLoading,
    data: stereotypeDescriptionsData,
    error: stereotypeDescriptionsError,
  } = useQuery<GQLGetStereotypeDescriptionsQueryData, GQLGetStereotypeDescriptionsQueryVariables>(
    getStereotypeDescriptionsQuery,
    { variables: { editingContextId } }
  );
  useEffect(() => {
    if (!stereotypeDescriptionsLoading) {
      if (stereotypeDescriptionsError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (stereotypeDescriptionsData) {
        const fetchStereotypeDescriptionsEvent: FetchedStereotypeDescriptionsEvent = {
          type: 'HANDLE_FETCHED_STEREOTYPE_DESCRIPTIONS',
          data: stereotypeDescriptionsData,
        };
        dispatch(fetchStereotypeDescriptionsEvent);
      }
    }
  }, [stereotypeDescriptionsLoading, stereotypeDescriptionsData, stereotypeDescriptionsError, dispatch]);

  const onNameChange = (event) => {
    const value = event.target.value;
    const changeNameEvent: ChangeNameEvent = { type: 'CHANGE_NAME', name: value };
    dispatch(changeNameEvent);
  };

  const onStereotypeDescriptionChange = (event) => {
    const value = event.target.value;
    const changeStereotypeDescriptionEvent: ChangeStereotypeDescriptionEvent = {
      type: 'CHANGE_STEREOTYPE_DESCRIPTION',
      stereotypeDescriptionId: value,
    };
    dispatch(changeStereotypeDescriptionEvent);
  };

  const [createDocument, { loading: createDocumentLoading, data: createDocumentData, error: createDocumentError }] =
    useMutation<GQLCreateDocumentMutationData>(createDocumentMutation);
  useEffect(() => {
    if (!createDocumentLoading) {
      if (createDocumentError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (createDocumentData) {
        const handleResponseEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: createDocumentData };
        dispatch(handleResponseEvent);

        const { createDocument } = createDocumentData;
        if (isErrorPayload(createDocument)) {
          const { message } = createDocument;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [createDocumentLoading, createDocumentData, createDocumentError, dispatch]);

  const onCreateDocument = () => {
    dispatch({ type: 'CREATE_DOCUMENT' } as CreateDocumentEvent);
    const input = {
      id: uuid(),
      editingContextId,
      name,
      stereotypeDescriptionId: selectedStereotypeDescriptionId,
    };
    createDocument({ variables: { input } });
  };

  useEffect(() => {
    if (newDocumentModal === 'success') {
      onClose();
    }
  }, [newDocumentModal, onClose]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Create a new model</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <TextField
              error={nameIsInvalid}
              helperText={nameMessage}
              label="Name"
              name="name"
              value={name}
              placeholder="Enter the name of the model"
              data-testid="name"
              inputProps={{ 'data-testid': 'name-input' }}
              autoFocus={true}
              onChange={onNameChange}
              disabled={newDocumentModal === 'loading' || newDocumentModal === 'creatingDocument'}
            />
            <InputLabel id="newDocumentModalStereotypeDescriptionLabel">Model type</InputLabel>
            <Select
              value={selectedStereotypeDescriptionId}
              onChange={onStereotypeDescriptionChange}
              disabled={newDocumentModal === 'loading' || newDocumentModal === 'creatingDocument'}
              labelId="newDocumentModalStereotypeDescriptionLabel"
              fullWidth
              inputProps={{ 'data-testid': 'stereotype-input' }}
              data-testid="stereotype"
            >
              {stereotypeDescriptions.map((stereotypeDescription) => (
                <MenuItem value={stereotypeDescription.id} key={stereotypeDescription.id}>
                  {stereotypeDescription.label}
                </MenuItem>
              ))}
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={newDocumentModal !== 'valid'}
            data-testid="create-document"
            color="primary"
            onClick={onCreateDocument}
          >
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
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
