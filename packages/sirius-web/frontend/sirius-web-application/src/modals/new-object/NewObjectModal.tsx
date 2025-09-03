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
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import InputLabel from '@mui/material/InputLabel';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import {
  GQLCreateChildMutationData,
  GQLCreateChildPayload,
  GQLCreateChildSuccessPayload,
  GQLErrorPayload,
  GQLGetChildCreationDescriptionsQueryData,
  GQLGetChildCreationDescriptionsQueryVariables,
  NewObjectModalProps,
  NewObjectModalStates,
} from './NewObjectModal.types';

const createChildMutation = gql`
  mutation createChild($input: CreateChildInput!) {
    createChild(input: $input) {
      __typename
      ... on CreateChildSuccessPayload {
        object {
          id
        }
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const getChildCreationDescriptionsQuery = gql`
  query getChildCreationDescriptions($editingContextId: ID!, $containerId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        childCreationDescriptions(containerId: $containerId) {
          id
          label
          iconURL
        }
      }
    }
  }
`;

const useNewObjectModalStyles = makeStyles()((theme) => ({
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

const isErrorPayload = (payload: GQLCreateChildPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isCreateChildSuccessPayload = (payload: GQLCreateChildPayload): payload is GQLCreateChildSuccessPayload => {
  return payload.__typename === 'CreateChildSuccessPayload';
};

export const NewObjectModal = ({ editingContextId, item, onObjectCreated, onClose }: NewObjectModalProps) => {
  const { classes } = useNewObjectModalStyles();
  const { addErrorMessage, addMessages } = useMultiToast();
  const [state, setState] = useState<NewObjectModalStates>({
    childCreationDescriptions: [],
    selectedChildCreationDescriptionId: '',
  });

  const {
    loading: childCreationDescriptionsLoading,
    data: childCreationDescriptionsData,
    error: childCreationDescriptionsError,
  } = useQuery<GQLGetChildCreationDescriptionsQueryData, GQLGetChildCreationDescriptionsQueryVariables>(
    getChildCreationDescriptionsQuery,
    { variables: { editingContextId, containerId: item.id } }
  );

  useEffect(() => {
    if (!childCreationDescriptionsLoading) {
      if (childCreationDescriptionsError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (childCreationDescriptionsData) {
        const childCreationDescriptions = childCreationDescriptionsData.viewer.editingContext.childCreationDescriptions;
        setState((prevState) => ({
          ...prevState,
          childCreationDescriptions: childCreationDescriptions,
          selectedChildCreationDescriptionId: childCreationDescriptions[0] ? childCreationDescriptions[0].id : '',
        }));
      }
    }
  }, [childCreationDescriptionsLoading, childCreationDescriptionsData, childCreationDescriptionsError]);

  const [createChild, { loading: createChildLoading, data: createChildData, error: createChildError }] =
    useMutation<GQLCreateChildMutationData>(createChildMutation);

  const onCreateObject = () => {
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      objectId: item.id,
      childCreationDescriptionId: state.selectedChildCreationDescriptionId,
    };
    createChild({ variables: { input } });
  };

  const onChildCreationDescriptionChange = (event) =>
    setState((prevState) => ({
      ...prevState,
      selectedChildCreationDescriptionId: event.target.value,
    }));

  useEffect(() => {
    if (!createChildLoading) {
      if (createChildError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (createChildData) {
        const { createChild } = createChildData;
        if (isErrorPayload(createChild)) {
          const { messages } = createChild;
          addMessages(messages);
        } else if (isCreateChildSuccessPayload(createChild)) {
          const { object } = createChild;
          onObjectCreated({ entries: [object] });
        }
      }
    }
  }, [createChildLoading, createChildData, createChildError]);

  return (
    <>
      <Dialog
        open={true}
        onClose={onClose}
        aria-labelledby="dialog-title"
        maxWidth="xs"
        fullWidth
        data-testid={'new-object-modal'}>
        <DialogTitle id="dialog-title">Create a new object</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <InputLabel id="newObjectModalChildCreationDescriptionLabel">Object type</InputLabel>
            <Select
              variant="standard"
              classes={{ select: classes.select }}
              value={state.selectedChildCreationDescriptionId}
              onChange={onChildCreationDescriptionChange}
              disabled={childCreationDescriptionsLoading || createChildLoading}
              labelId="newObjectModalChildCreationDescriptionLabel"
              fullWidth
              data-testid="childCreationDescription">
              {state.childCreationDescriptions.map((childCreationDescription) => (
                <MenuItem value={childCreationDescription.id} key={childCreationDescription.id}>
                  {childCreationDescription.iconURL.length > 0 && (
                    <ListItemIcon className={classes.iconRoot}>
                      <IconOverlay iconURL={childCreationDescription.iconURL} alt={childCreationDescription.label} />
                    </ListItemIcon>
                  )}
                  <ListItemText primary={childCreationDescription.label} />
                </MenuItem>
              ))}
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            data-testid="create-object"
            color="primary"
            onClick={onCreateObject}
            disabled={!state.selectedChildCreationDescriptionId}>
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
