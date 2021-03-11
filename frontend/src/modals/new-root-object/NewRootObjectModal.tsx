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
import { useLazyQuery, useMutation, useQuery } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Checkbox from '@material-ui/core/Checkbox';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormControlLabel from '@material-ui/core/FormControlLabel';
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
  GQLCreateRootObjectMutationData,
  GQLGetNamespacesQueryData,
  GQLGetNamespacesQueryVariables,
  GQLGetRootObjectCreationDescriptionsQueryData,
  GQLGetRootObjectCreationDescriptionsQueryVariables,
  NewRootObjectModalProps,
} from 'modals/new-root-object/NewRootObjectModal.types';
import {
  ChangeNamespaceEvent,
  ChangeRootObjectCreationDescriptionEvent,
  ChangeSuggestedEvent,
  CreateRootObjectEvent,
  FetchedNamespacesEvent,
  FetchedRootObjectCreationDescriptionsEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewRootObjectModalContext,
  NewRootObjectModalEvent,
  newRootObjectModalMachine,
  SchemaValue,
  ShowToastEvent,
} from 'modals/new-root-object/NewRootObjectModalMachine';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';

const createRootObjectMutation = gql`
  mutation createRootObject($input: CreateRootObjectInput!) {
    createRootObject(input: $input) {
      __typename
      ... on CreateRootObjectSuccessPayload {
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

const getNamespacesQuery = gql`
  query getNamespaces($editingContextId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        namespaces {
          id
          label
        }
      }
    }
  }
`;

const getRootObjectCreationDescriptionsQuery = gql`
  query getRootObjectCreationDescriptions($editingContextId: ID!, $namespaceId: ID!, $suggested: Boolean!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        rootObjectCreationDescriptions(namespaceId: $namespaceId, suggested: $suggested) {
          id
          label
        }
      }
    }
  }
`;

const useNewRootObjectModalStyles = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
}));

export const NewRootObjectModal = ({
  editingContextId,
  documentId,
  onObjectCreated,
  onClose,
}: NewRootObjectModalProps) => {
  const classes = useNewRootObjectModalStyles();
  const [{ value, context }, dispatch] = useMachine<NewRootObjectModalContext, NewRootObjectModalEvent>(
    newRootObjectModalMachine
  );
  const { newRootObjectModal, toast } = value as SchemaValue;
  const {
    namespaces,
    selectedNamespaceId,
    rootObjectCreationDescriptions,
    selectedRootObjectCreationDescriptionId,
    suggestedRootObject,
    objectToSelect,
    message,
  } = context;

  // Fetch the available namespaces only once, they are supposed static (at least for the lifetime of the modal)
  const { loading: namespacesLoading, data: namespacesData, error: namespacesError } = useQuery<
    GQLGetNamespacesQueryData,
    GQLGetNamespacesQueryVariables
  >(getNamespacesQuery, { variables: { editingContextId } });
  useEffect(() => {
    if (!namespacesLoading) {
      if (namespacesError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (namespacesData) {
        const fetchNamespacesEvent: FetchedNamespacesEvent = {
          type: 'HANDLE_FETCHED_NAMESPACES',
          data: namespacesData,
        };
        dispatch(fetchNamespacesEvent);
      }
    }
  }, [namespacesLoading, namespacesData, namespacesError, dispatch]);

  // Fetch the corresponding object creation description whenever the user selects a new namespace or toggles the checkbox
  const [
    getRootObjectCreationDescriptions,
    { loading: descriptionsLoading, data: descriptionsData, error: descriptionError },
  ] = useLazyQuery<GQLGetRootObjectCreationDescriptionsQueryData, GQLGetRootObjectCreationDescriptionsQueryVariables>(
    getRootObjectCreationDescriptionsQuery
  );
  useEffect(() => {
    if (!descriptionsLoading) {
      if (descriptionError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (descriptionsData) {
        const fetchDescriptionsEvent: FetchedRootObjectCreationDescriptionsEvent = {
          type: 'HANDLE_FETCHED_ROOT_OBJECT_CREATION_DESCRIPTIONS',
          data: descriptionsData,
        };
        dispatch(fetchDescriptionsEvent);
      }
    }
  }, [descriptionsLoading, descriptionsData, descriptionError, dispatch]);

  useEffect(() => {
    if (newRootObjectModal === 'loadingRootObjectCreationDescriptions' && selectedNamespaceId) {
      getRootObjectCreationDescriptions({
        variables: { editingContextId, namespaceId: selectedNamespaceId, suggested: suggestedRootObject },
      });
    }
  }, [
    newRootObjectModal,
    getRootObjectCreationDescriptions,
    editingContextId,
    selectedNamespaceId,
    suggestedRootObject,
  ]);

  // Create the new child
  const [
    createRootObject,
    { loading: createRootObjectLoading, data: createRootObjectData, error: createRootObjectError },
  ] = useMutation<GQLCreateRootObjectMutationData>(createRootObjectMutation);
  useEffect(() => {
    if (!createRootObjectLoading) {
      if (createRootObjectError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (createRootObjectData) {
        const handleResponseEvent: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data: createRootObjectData };
        dispatch(handleResponseEvent);
      }
    }
  }, [createRootObjectLoading, createRootObjectError, createRootObjectData, dispatch]);

  const onCreateRootObject = () => {
    dispatch({ type: 'CREATE_ROOT_OBJECT' } as CreateRootObjectEvent);
    const input = {
      id: uuid(),
      editingContextId,
      documentId,
      namespaceId: selectedNamespaceId,
      rootObjectCreationDescriptionId: selectedRootObjectCreationDescriptionId,
    };
    createRootObject({ variables: { input } });
  };

  const onNamespaceChange = (event) => {
    const { value } = event.target;
    const changeNamespaceEvent: ChangeNamespaceEvent = { type: 'CHANGE_NAMESPACE', namespaceId: value };
    dispatch(changeNamespaceEvent);
  };

  const onRootObjectCreationDescriptionChange = (event) => {
    const { value } = event.target;
    const changeRootObjectCreationDescriptionEvent: ChangeRootObjectCreationDescriptionEvent = {
      type: 'CHANGE_ROOT_OBJECT_CREATION_DESCRIPTION',
      rootObjectCreationDescriptionId: value,
    };
    dispatch(changeRootObjectCreationDescriptionEvent);
  };

  const onSuggestedRootObjectChange = (event) => {
    const { checked } = event.target;
    const changeSuggestedRootObject: ChangeSuggestedEvent = { type: 'CHANGE_SUGGESTED', suggestedRootObject: checked };
    dispatch(changeSuggestedRootObject);
  };

  useEffect(() => {
    if (newRootObjectModal === 'success') {
      onObjectCreated(objectToSelect);
    }
  }, [newRootObjectModal, onObjectCreated, objectToSelect]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Create a new root object</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <InputLabel id="namespacesLabel">Namespace</InputLabel>
            <Select
              value={selectedNamespaceId}
              onChange={onNamespaceChange}
              disabled={newRootObjectModal !== 'valid'}
              labelId="namespacesLabel"
              fullWidth
              data-testid="namespace">
              {namespaces.map((namespace) => (
                <MenuItem value={namespace.id} key={namespace.id}>
                  {namespace.label}
                </MenuItem>
              ))}
            </Select>
            <InputLabel id="rootObjectCreationDescriptionsLabel">Object type</InputLabel>
            <Select
              value={selectedRootObjectCreationDescriptionId}
              onChange={onRootObjectCreationDescriptionChange}
              disabled={newRootObjectModal !== 'valid'}
              labelId="rootObjectCreationDescriptionsLabel"
              fullWidth
              data-testid="type">
              {rootObjectCreationDescriptions.map((rootObjectCreationDescription) => (
                <MenuItem value={rootObjectCreationDescription.id} key={rootObjectCreationDescription.id}>
                  {rootObjectCreationDescription.label}
                </MenuItem>
              ))}
            </Select>
            <FormControlLabel
              control={
                <Checkbox
                  checked={suggestedRootObject}
                  onChange={onSuggestedRootObjectChange}
                  name="suggested"
                  color="primary"
                  data-testid="suggested"
                />
              }
              label="Show only suggested root type"
            />
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={newRootObjectModal !== 'valid'}
            data-testid="create-object"
            color="primary"
            onClick={onCreateRootObject}>
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
