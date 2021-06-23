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
import TextField from '@material-ui/core/TextField';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';
import {
  GQLCreateRepresentationMutationData,
  GQLCreateRepresentationPayload,
  GQLErrorPayload,
  GQLGetRepresentationDescriptionsQueryData,
  GQLGetRepresentationDescriptionsQueryVariables,
  NewRepresentationModalProps,
} from './NewRepresentationModal.types';
import {
  ChangeNameEvent,
  ChangeRepresentationDescriptionEvent,
  CreateRepresentationEvent,
  FetchedRepresentationDescriptionsEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewRepresentationModalContext,
  NewRepresentationModalEvent,
  newRepresentationModalMachine,
  SchemaValue,
  ShowToastEvent,
} from './NewRepresentationModalMachine';

const createRepresentationMutation = gql`
  mutation createRepresentation($input: CreateRepresentationInput!) {
    createRepresentation(input: $input) {
      __typename
      ... on CreateRepresentationSuccessPayload {
        representation {
          id
          label
          kind
          __typename
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getRepresentationDescriptionsQuery = gql`
  query getRepresentationDescriptions($editingContextId: ID!, $classId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representationDescriptions(classId: $classId) {
          edges {
            node {
              id
              label
            }
          }
          pageInfo {
            hasNextPage
            hasPreviousPage
            startCursor
            endCursor
          }
        }
      }
    }
  }
`;

const useNewRepresentationModalStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
}));

const isErrorPayload = (payload: GQLCreateRepresentationPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const NewRepresentationModal = ({
  editingContextId,
  classId,
  objectId,
  onRepresentationCreated,
  onClose,
}: NewRepresentationModalProps) => {
  const classes = useNewRepresentationModalStyles();
  const [{ value, context }, dispatch] = useMachine<NewRepresentationModalContext, NewRepresentationModalEvent>(
    newRepresentationModalMachine
  );
  const { newRepresentationModal, toast } = value as SchemaValue;
  const {
    name,
    nameMessage,
    nameIsInvalid,
    selectedRepresentationDescriptionId,
    representationDescriptions,
    createdRepresentationId,
    createdRepresentationLabel,
    createdRepresentationKind,
    message,
  } = context;

  const {
    loading: representationDescriptionsLoading,
    data: representationDescriptionsData,
    error: representationDescriptionsError,
  } = useQuery<GQLGetRepresentationDescriptionsQueryData, GQLGetRepresentationDescriptionsQueryVariables>(
    getRepresentationDescriptionsQuery,
    { variables: { editingContextId, classId } }
  );

  useEffect(() => {
    if (!representationDescriptionsLoading) {
      if (representationDescriptionsError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (representationDescriptionsData) {
        const fetchRepresentationDescriptionsEvent: FetchedRepresentationDescriptionsEvent = {
          type: 'HANDLE_FETCHED_REPRESENTATION_DESCRIPTIONS',
          data: representationDescriptionsData,
        };
        dispatch(fetchRepresentationDescriptionsEvent);
      }
    }
  }, [representationDescriptionsLoading, representationDescriptionsData, representationDescriptionsError, dispatch]);

  const onNameChange = (event) => {
    const value = event.target.value;
    const changeNamedEvent: ChangeNameEvent = { type: 'CHANGE_NAME', name: value };
    dispatch(changeNamedEvent);
  };

  const onRepresentationDescriptionChange = (event) => {
    const value = event.target.value;
    const changeRepresentationDescriptionEvent: ChangeRepresentationDescriptionEvent = {
      type: 'CHANGE_REPRESENTATION_DESCRIPTION',
      representationDescriptionId: value,
    };
    dispatch(changeRepresentationDescriptionEvent);
  };

  const [
    createRepresentation,
    { loading: createRepresentationLoading, data: createRepresentationData, error: createRepresentationError },
  ] = useMutation<GQLCreateRepresentationMutationData>(createRepresentationMutation);
  useEffect(() => {
    if (!createRepresentationLoading) {
      if (createRepresentationError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (createRepresentationData) {
        const handleResponseEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: createRepresentationData };
        dispatch(handleResponseEvent);

        const { createRepresentation } = createRepresentationData;
        if (isErrorPayload(createRepresentation)) {
          const { message } = createRepresentation;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [createRepresentationLoading, createRepresentationData, createRepresentationError, dispatch]);

  const onCreateRepresentation = () => {
    dispatch({ type: 'CREATE_REPRESENTATION' } as CreateRepresentationEvent);
    const input = {
      id: uuid(),
      editingContextId,
      objectId,
      representationDescriptionId: selectedRepresentationDescriptionId,
      representationName: name,
    };

    createRepresentation({ variables: { input } });
  };

  useEffect(() => {
    if (newRepresentationModal === 'success') {
      onRepresentationCreated({
        id: createdRepresentationId,
        label: createdRepresentationLabel,
        kind: createdRepresentationKind,
      });
    }
  }, [
    createdRepresentationId,
    createdRepresentationKind,
    createdRepresentationLabel,
    newRepresentationModal,
    onRepresentationCreated,
  ]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Create a new representation</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <TextField
              error={nameIsInvalid}
              helperText={nameMessage}
              label="Name"
              name="name"
              value={name}
              placeholder="Enter the name of the representation"
              data-testid="name"
              autoFocus={true}
              onChange={onNameChange}
              disabled={newRepresentationModal === 'loading' || newRepresentationModal === 'creatingRepresentation'}
            />
            <InputLabel id="newRepresentationModalRepresentationDescriptionLabel">Representation type</InputLabel>
            <Select
              value={selectedRepresentationDescriptionId}
              onChange={onRepresentationDescriptionChange}
              disabled={newRepresentationModal === 'loading' || newRepresentationModal === 'creatingRepresentation'}
              labelId="newDocumentModalStereotypeDescriptionLabel"
              fullWidth
              data-testid="representationDescription">
              {representationDescriptions.map((representationDescription) => (
                <MenuItem value={representationDescription.id} key={representationDescription.id}>
                  {representationDescription.label}
                </MenuItem>
              ))}
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={newRepresentationModal !== 'valid'}
            data-testid="create-representation"
            color="primary"
            onClick={onCreateRepresentation}>
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
