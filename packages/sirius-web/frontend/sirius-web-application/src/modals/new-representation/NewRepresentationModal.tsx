/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import { gql, useMutation, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import {
  GQLCreateRepesentationSuccessPayload,
  GQLCreateRepresentationMutationData,
  GQLCreateRepresentationPayload,
  GQLErrorPayload,
  GQLGetRepresentationDescriptionsQueryData,
  GQLGetRepresentationDescriptionsQueryVariables,
  NewRepresentationModalProps,
  NewRepresentationState,
} from './NewRepresentationModal.types';

const createRepresentationMutation = gql`
  mutation createRepresentation($input: CreateRepresentationInput!) {
    createRepresentation(input: $input) {
      __typename
      ... on CreateRepresentationSuccessPayload {
        representation {
          id
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
  query getRepresentationDescriptions($editingContextId: ID!, $objectId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representationDescriptions(objectId: $objectId) {
          edges {
            node {
              id
              label
              defaultName
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

const useNewRepresentationModalStyles = makeStyles()((theme) => ({
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

const isNameInvalid = (name: string) => name.trim().length === 0;

const isCreateRepresentationSuccessPayload = (
  payload: GQLCreateRepresentationPayload | null
): payload is GQLCreateRepesentationSuccessPayload => {
  return payload && payload.__typename === 'CreateRepresentationSuccessPayload';
};

export const NewRepresentationModal = ({
  editingContextId,
  item,
  onRepresentationCreated,
  onClose,
}: NewRepresentationModalProps) => {
  const { classes } = useNewRepresentationModalStyles();
  const { addErrorMessage } = useMultiToast();
  const [state, setState] = useState<NewRepresentationState>({
    representationDescriptions: [],
    selectedRepresentationDescriptionId: '',
    name: '',
    nameHasBeenModified: false,
  });

  const {
    loading: representationDescriptionsLoading,
    data: representationDescriptionsData,
    error: representationDescriptionsError,
  } = useQuery<GQLGetRepresentationDescriptionsQueryData, GQLGetRepresentationDescriptionsQueryVariables>(
    getRepresentationDescriptionsQuery,
    { variables: { editingContextId, objectId: item.id } }
  );

  useEffect(() => {
    if (!representationDescriptionsLoading) {
      if (representationDescriptionsError) {
        addErrorMessage(representationDescriptionsError.message);
      }
      if (representationDescriptionsData) {
        const representationDescriptions =
          representationDescriptionsData.viewer.editingContext.representationDescriptions.edges.map(
            (edge) => edge.node
          );

        const selectedRepresentationDescriptionId =
          representationDescriptions.length > 0 ? representationDescriptions[0].id : '';

        const name = representationDescriptions.length > 0 ? representationDescriptions[0].defaultName : '';

        setState((prevState) => ({
          ...prevState,
          representationDescriptions: representationDescriptions,
          selectedRepresentationDescriptionId: selectedRepresentationDescriptionId,
          name: name,
        }));
      }
    }
  }, [representationDescriptionsLoading, representationDescriptionsData, representationDescriptionsError]);

  const onNameChange = (event) => {
    const value = event.target.value;
    setState((prevState) => ({
      ...prevState,
      name: value,
      nameHasBeenModified: true,
    }));
  };

  const onRepresentationDescriptionChange = (event) => {
    const value = event.target.value;

    setState((prevState) => ({
      ...prevState,
      selectedRepresentationDescriptionId: value,
      name: state.nameHasBeenModified
        ? prevState.name
        : state.representationDescriptions.filter(
            (representationDescription) => representationDescription.id === value
          )[0].defaultName,
    }));
  };

  const [
    createRepresentation,
    { loading: createRepresentationLoading, data: createRepresentationData, error: createRepresentationError },
  ] = useMutation<GQLCreateRepresentationMutationData>(createRepresentationMutation);
  useEffect(() => {
    if (!createRepresentationLoading) {
      if (createRepresentationError) {
        addErrorMessage(createRepresentationError.message);
      }
      if (createRepresentationData) {
        const { createRepresentation } = createRepresentationData;
        if (isErrorPayload(createRepresentation)) {
          const { message } = createRepresentation;
          addErrorMessage(message);
        }

        if (isCreateRepresentationSuccessPayload(createRepresentation)) {
          const { representation } = createRepresentation;
          onRepresentationCreated({
            entries: [
              {
                id: representation.id,
              },
            ],
          });
        }
      }
    }
  }, [createRepresentationLoading, createRepresentationData, createRepresentationError]);

  const onCreateRepresentation = () => {
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      objectId: item.id,
      representationDescriptionId: state.selectedRepresentationDescriptionId,
      representationName: state.name,
    };

    createRepresentation({ variables: { input } });
  };

  const nameIsInvalid = isNameInvalid(state.name);
  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Create a new representation</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <TextField
              variant="standard"
              error={nameIsInvalid}
              helperText="The name cannot be empty"
              label="Name"
              name="name"
              value={state.name}
              placeholder="Enter the name of the representation"
              inputProps={{ 'data-testid': 'name' }}
              autoFocus={true}
              onChange={onNameChange}
              disabled={!state.selectedRepresentationDescriptionId}
            />
            <InputLabel id="newRepresentationModalRepresentationDescriptionLabel">Representation type</InputLabel>
            <Select
              variant="standard"
              value={state.selectedRepresentationDescriptionId}
              onChange={onRepresentationDescriptionChange}
              labelId="newRepresentationModalRepresentationDescriptionLabel"
              inputProps={{ 'data-testid': 'representationDescription-input' }}
              fullWidth
              data-testid="representationDescription">
              {state.representationDescriptions.map((representationDescription) => (
                <MenuItem
                  value={representationDescription.id}
                  key={representationDescription.id}
                  data-testid={representationDescription.label}>
                  {representationDescription.label}
                </MenuItem>
              ))}
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={nameIsInvalid || !state.selectedRepresentationDescriptionId}
            data-testid="create-representation"
            color="primary"
            onClick={onCreateRepresentation}>
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
