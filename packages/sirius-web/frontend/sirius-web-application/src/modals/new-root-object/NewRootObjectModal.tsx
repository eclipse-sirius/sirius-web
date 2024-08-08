/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { gql, useLazyQuery, useMutation, useQuery } from '@apollo/client';
import { IconOverlay, Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormControlLabel from '@mui/material/FormControlLabel';
import InputLabel from '@mui/material/InputLabel';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { makeStyles } from 'tss-react/mui';
import {
  GQLCreateRootObjectMutationData,
  GQLGetRootDomainsQueryData,
  GQLGetRootDomainsQueryVariables,
  GQLGetRootObjectCreationDescriptionsQueryData,
  GQLGetRootObjectCreationDescriptionsQueryVariables,
  NewRootObjectModalProps,
} from './NewRootObjectModal.types';
import {
  ChangeDomainEvent,
  ChangeRootObjectCreationDescriptionEvent,
  ChangeSuggestedEvent,
  CreateRootObjectEvent,
  FetchedDomainsEvent,
  FetchedRootObjectCreationDescriptionsEvent,
  HandleResponseEvent,
  HideToastEvent,
  NewRootObjectModalContext,
  NewRootObjectModalEvent,
  SchemaValue,
  ShowToastEvent,
  newRootObjectModalMachine,
} from './NewRootObjectModalMachine';

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

const getRootDomainsQuery = gql`
  query getRootDomains($editingContextId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        domains(rootDomainsOnly: true) {
          id
          label
        }
      }
    }
  }
`;

const getRootObjectCreationDescriptionsQuery = gql`
  query getRootObjectCreationDescriptions($editingContextId: ID!, $domainId: ID!, $suggested: Boolean!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        rootObjectCreationDescriptions(domainId: $domainId, suggested: $suggested) {
          id
          label
          iconURL
        }
      }
    }
  }
`;

const useNewRootObjectModalStyles = makeStyles()((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    '& > *': {
      marginBottom: theme.spacing(1),
    },
  },
  select: {
    '&': {
      display: 'flex',
      alignItems: 'center',
    },
  },
  iconRoot: {
    minWidth: theme.spacing(3),
  },
}));

export const NewRootObjectModal = ({ editingContextId, item, onObjectCreated, onClose }: NewRootObjectModalProps) => {
  const { classes } = useNewRootObjectModalStyles();
  const [{ value, context }, dispatch] = useMachine<NewRootObjectModalContext, NewRootObjectModalEvent>(
    newRootObjectModalMachine
  );
  const { newRootObjectModal, toast } = value as SchemaValue;
  const {
    domains,
    selectedDomainId,
    rootObjectCreationDescriptions,
    selectedRootObjectCreationDescriptionId,
    suggestedRootObject,
    objectToSelect,
    message,
  } = context;

  // Fetch the available domains only once, they are supposed static (at least for the lifetime of the modal)
  const {
    loading: domainsLoading,
    data: domainsData,
    error: domainsError,
  } = useQuery<GQLGetRootDomainsQueryData, GQLGetRootDomainsQueryVariables>(getRootDomainsQuery, {
    variables: { editingContextId },
  });
  useEffect(() => {
    if (!domainsLoading) {
      if (domainsError) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (domainsData) {
        const fetchDomainsEvent: FetchedDomainsEvent = {
          type: 'HANDLE_FETCHED_DOMAINS',
          data: domainsData,
        };
        dispatch(fetchDomainsEvent);
      }
    }
  }, [domainsLoading, domainsData, domainsError, dispatch]);

  // Fetch the corresponding object creation description whenever the user selects a new domain or toggles the checkbox
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
    if (newRootObjectModal === 'loadingRootObjectCreationDescriptions' && selectedDomainId) {
      getRootObjectCreationDescriptions({
        variables: { editingContextId, domainId: selectedDomainId, suggested: suggestedRootObject },
      });
    }
  }, [newRootObjectModal, getRootObjectCreationDescriptions, editingContextId, selectedDomainId, suggestedRootObject]);

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
      id: crypto.randomUUID(),
      editingContextId,
      documentId: item.id,
      domainId: selectedDomainId,
      rootObjectCreationDescriptionId: selectedRootObjectCreationDescriptionId,
    };
    createRootObject({ variables: { input } });
  };

  const onDomainChange = (event) => {
    const { value } = event.target;
    const changeDomainEvent: ChangeDomainEvent = { type: 'CHANGE_DOMAIN', domainId: value };
    dispatch(changeDomainEvent);
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
      onObjectCreated({ entries: [objectToSelect] });
    }
  }, [newRootObjectModal, onObjectCreated, objectToSelect]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Create a new root object</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <InputLabel id="domainsLabel">Domain</InputLabel>
            <Select
              variant="standard"
              value={selectedDomainId}
              onChange={onDomainChange}
              disabled={newRootObjectModal !== 'valid'}
              labelId="domainsLabel"
              fullWidth
              data-testid="domain">
              {domains.map((domain) => (
                <MenuItem value={domain.id} key={domain.id}>
                  {domain.label}
                </MenuItem>
              ))}
            </Select>
            <InputLabel id="rootObjectCreationDescriptionsLabel">Object type</InputLabel>
            <Select
              variant="standard"
              classes={{ select: classes.select }}
              value={selectedRootObjectCreationDescriptionId}
              onChange={onRootObjectCreationDescriptionChange}
              disabled={newRootObjectModal !== 'valid'}
              labelId="rootObjectCreationDescriptionsLabel"
              fullWidth
              data-testid="type">
              {rootObjectCreationDescriptions.map((rootObjectCreationDescription) => (
                <MenuItem value={rootObjectCreationDescription.id} key={rootObjectCreationDescription.id}>
                  {rootObjectCreationDescription.iconURL.length > 0 && (
                    <ListItemIcon className={classes.iconRoot}>
                      <IconOverlay
                        iconURL={rootObjectCreationDescription.iconURL}
                        alt={rootObjectCreationDescription.label}
                      />
                    </ListItemIcon>
                  )}
                  <ListItemText primary={rootObjectCreationDescription.label} />
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
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
