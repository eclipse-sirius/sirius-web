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

import { gql, useMutation, useQuery } from '@apollo/client';
import { Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import TextField from '@mui/material/TextField';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { StateMachine } from 'xstate';
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
  NewRepresentationModalStateSchema,
  SchemaValue,
  ShowToastEvent,
  newRepresentationModalMachine,
} from './NewRepresentationModalMachine';

const createRepresentationMutation = gql`
  mutation createRepresentation($input: CreateRepresentationInput!) {
    createRepresentation(input: $input) {
      __typename
      ... on CreateRepresentationSuccessPayload {
        representation {
          id
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

export const NewRepresentationModal = ({
  editingContextId,
  item,
  onRepresentationCreated,
  onClose,
}: NewRepresentationModalProps) => {
  const { classes } = useNewRepresentationModalStyles();
  const { t } = useTranslation('siriusWebApplication', { keyPrefix: 'representation.create' });
  const { t: coreT } = useTranslation('siriusComponentsCore');
  const [{ value, context }, dispatch] =
    useMachine<
      StateMachine<NewRepresentationModalContext, NewRepresentationModalStateSchema, NewRepresentationModalEvent>
    >(newRepresentationModalMachine);
  const { newRepresentationModal, toast } = value as SchemaValue;
  const {
    name,
    nameIsInvalid,
    selectedRepresentationDescriptionId,
    representationDescriptions,
    createdRepresentationId,
    createdRepresentationKind,
    message,
  } = context;

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
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: coreT('errors.unexpected'),
        };
        dispatch(showToastEvent);
      }
      if (representationDescriptionsData) {
        const fetchRepresentationDescriptionsEvent: FetchedRepresentationDescriptionsEvent = {
          type: 'HANDLE_FETCHED_REPRESENTATION_CREATION_DESCRIPTIONS',
          data: representationDescriptionsData,
        };
        dispatch(fetchRepresentationDescriptionsEvent);
      }
    }
  }, [coreT, representationDescriptionsLoading, representationDescriptionsData, representationDescriptionsError]);

  const onNameChange = (event) => {
    const value = event.target.value;
    const changeNamedEvent: ChangeNameEvent = { type: 'CHANGE_NAME', name: value };
    dispatch(changeNamedEvent);
  };

  const onRepresentationDescriptionChange = (event) => {
    const value = event.target.value;
    const changeRepresentationDescriptionEvent: ChangeRepresentationDescriptionEvent = {
      type: 'CHANGE_REPRESENTATION_CREATION_DESCRIPTION',
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
          message: coreT('errors.unexpected'),
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
  }, [coreT, createRepresentationLoading, createRepresentationData, createRepresentationError]);

  const onCreateRepresentation = () => {
    dispatch({ type: 'CREATE_REPRESENTATION' } as CreateRepresentationEvent);
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      objectId: item.id,
      representationDescriptionId: selectedRepresentationDescriptionId,
      representationName: name,
    };

    createRepresentation({ variables: { input } });
  };

  useEffect(() => {
    if (newRepresentationModal === 'success') {
      onRepresentationCreated({
        entries: [
          {
            id: createdRepresentationId,
            kind: createdRepresentationKind,
          },
        ],
      });
    }
  }, [createdRepresentationId, createdRepresentationKind, newRepresentationModal]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <TextField
              variant="standard"
              error={nameIsInvalid}
              helperText={t('name.helperText')}
              label={t('name.label')}
              name="name"
              value={name}
              placeholder={t('name.placeholder')}
              inputProps={{ 'data-testid': 'name' }}
              autoFocus={true}
              onChange={onNameChange}
              disabled={newRepresentationModal === 'loading' || newRepresentationModal === 'creatingRepresentation'}
            />
            <InputLabel id="newRepresentationModalRepresentationDescriptionLabel">{t('type.label')}</InputLabel>
            <Select
              variant="standard"
              value={selectedRepresentationDescriptionId}
              onChange={onRepresentationDescriptionChange}
              disabled={newRepresentationModal === 'loading' || newRepresentationModal === 'creatingRepresentation'}
              labelId="newRepresentationModalRepresentationDescriptionLabel"
              inputProps={{ 'data-testid': 'representationDescription-input' }}
              fullWidth
              data-testid="representationDescription">
              {representationDescriptions
                .sort((a, b) => a.label.localeCompare(b.label))
                .map((representationDescription) => (
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
            disabled={newRepresentationModal !== 'valid'}
            data-testid="create-representation"
            color="primary"
            onClick={onCreateRepresentation}>
            {t('submit')}
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
