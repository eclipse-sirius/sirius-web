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
import { gql, useLazyQuery, useMutation, useQuery } from '@apollo/client';
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
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
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import {
  GQLCreateRootObjectMutationData,
  GQLCreateRootObjectPayload,
  GQLCreateRootObjectSuccessPayload,
  GQLErrorPayload,
  GQLGetRootDomainsQueryData,
  GQLGetRootDomainsQueryVariables,
  GQLGetRootObjectCreationDescriptionsQueryData,
  GQLGetRootObjectCreationDescriptionsQueryVariables,
  NewRootObjectModalProps,
  NewRootObjectModalState,
} from './NewRootObjectModal.types';

const createRootObjectMutation = gql`
  mutation createRootObject($input: CreateRootObjectInput!) {
    createRootObject(input: $input) {
      __typename
      ... on CreateRootObjectSuccessPayload {
        object {
          id
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

const isCreateRootObjectSuccessPayload = (
  payload: GQLCreateRootObjectPayload
): payload is GQLCreateRootObjectSuccessPayload => {
  return payload.__typename === 'CreateRootObjectSuccessPayload';
};

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

const isErrorPayload = (payload: GQLCreateRootObjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const NewRootObjectModal = ({ editingContextId, item, onObjectCreated, onClose }: NewRootObjectModalProps) => {
  const { classes } = useNewRootObjectModalStyles();
  const { addErrorMessage, addMessages } = useMultiToast();
  const [state, setState] = useState<NewRootObjectModalState>({
    domains: [],
    selectedDomainId: '',
    rootObjectCreationDescriptions: [],
    selectedRootObjectCreationDescriptionId: '',
    isSuggestedRootObjectChecked: true,
  });

  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'newRootObjectModal' });

  // Fetch the corresponding object creation description whenever the user selects a new domain or toggles the checkbox
  const [
    getRootObjectCreationDescriptions,
    { loading: descriptionsLoading, data: descriptionsData, error: descriptionError },
  ] = useLazyQuery<GQLGetRootObjectCreationDescriptionsQueryData, GQLGetRootObjectCreationDescriptionsQueryVariables>(
    getRootObjectCreationDescriptionsQuery
  );

  useEffect(() => {
    getRootObjectCreationDescriptions({
      variables: {
        editingContextId,
        domainId: state.selectedDomainId,
        suggested: state.isSuggestedRootObjectChecked,
      },
    });
  }, [state.selectedDomainId, state.isSuggestedRootObjectChecked]);

  useEffect(() => {
    if (!descriptionsLoading) {
      if (descriptionError) {
        addErrorMessage(descriptionError.message);
      }
      if (descriptionsData) {
        const { rootObjectCreationDescriptions } = descriptionsData.viewer.editingContext;
        setState((prevState) => ({
          ...prevState,
          rootObjectCreationDescriptions: rootObjectCreationDescriptions,
          selectedRootObjectCreationDescriptionId: rootObjectCreationDescriptions[0]
            ? rootObjectCreationDescriptions[0].id
            : '',
        }));
      }
    }
  }, [descriptionsLoading, descriptionsData, descriptionError]);

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
        addErrorMessage(domainsError.message);
      }
      if (domainsData) {
        const { domains } = domainsData.viewer.editingContext;
        setState((prevState) => ({
          ...prevState,
          domains: domains,
          selectedDomainId: domains[0] ? domains[0].id : '',
        }));
      }
    }
  }, [domainsLoading, domainsData, domainsError]);

  // Create the new child
  const [
    createRootObject,
    { loading: createRootObjectLoading, data: createRootObjectData, error: createRootObjectError },
  ] = useMutation<GQLCreateRootObjectMutationData>(createRootObjectMutation);
  useEffect(() => {
    if (!createRootObjectLoading) {
      if (createRootObjectError) {
        addErrorMessage(createRootObjectError.message);
      }
      if (createRootObjectData) {
        const { createRootObject } = createRootObjectData;
        if (isErrorPayload(createRootObject)) {
          const { message } = createRootObject;
          addMessages(message);
        } else if (isCreateRootObjectSuccessPayload(createRootObject)) {
          const { object } = createRootObject;
          onObjectCreated({ entries: [object] });
        }
      }
    }
  }, [createRootObjectLoading, createRootObjectError, createRootObjectData]);

  const onCreateRootObject = () => {
    const input = {
      id: crypto.randomUUID(),
      editingContextId,
      documentId: item.id,
      domainId: state.selectedDomainId,
      rootObjectCreationDescriptionId: state.selectedRootObjectCreationDescriptionId,
    };
    createRootObject({ variables: { input } });
  };

  const onDomainChange = (event) => {
    const { value } = event.target;
    setState((prevState) => ({
      ...prevState,
      selectedDomainId: value,
    }));
  };

  const onRootObjectCreationDescriptionChange = (event) => {
    const { value } = event.target;
    setState((prevState) => ({
      ...prevState,
      selectedRootObjectCreationDescriptionId: value,
    }));
  };

  const onSuggestedRootObjectChange = (event) => {
    const { checked } = event.target;
    setState((prevState) => ({
      ...prevState,
      isSuggestedRootObjectChecked: checked,
    }));
  };

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
        <DialogContent>
          <div className={classes.form}>
            <InputLabel id="domainsLabel">{t('domain.label')}</InputLabel>
            <Select
              variant="standard"
              value={state.selectedDomainId}
              onChange={onDomainChange}
              disabled={!state.selectedDomainId}
              labelId="domainsLabel"
              fullWidth
              data-testid="domain">
              {state.domains.map((domain) => (
                <MenuItem value={domain.id} key={domain.id}>
                  {domain.label}
                </MenuItem>
              ))}
            </Select>
            <InputLabel id="rootObjectCreationDescriptionsLabel">{t('type.label')}</InputLabel>
            <Select
              variant="standard"
              classes={{ select: classes.select }}
              value={state.selectedRootObjectCreationDescriptionId}
              onChange={onRootObjectCreationDescriptionChange}
              disabled={!state.selectedRootObjectCreationDescriptionId}
              labelId="rootObjectCreationDescriptionsLabel"
              fullWidth
              data-testid="type">
              {state.rootObjectCreationDescriptions.map((rootObjectCreationDescription) => (
                <MenuItem value={rootObjectCreationDescription.id} key={rootObjectCreationDescription.id}>
                  {rootObjectCreationDescription.iconURL.length > 0 && (
                    <ListItemIcon className={classes.iconRoot}>
                      <IconOverlay
                        iconURLs={rootObjectCreationDescription.iconURL}
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
                  checked={state.isSuggestedRootObjectChecked}
                  onChange={onSuggestedRootObjectChange}
                  name="suggested"
                  color="primary"
                  data-testid="suggested"
                />
              }
              label={t('suggestedType.label')}
            />
          </div>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={!state.selectedRootObjectCreationDescriptionId && !state.selectedDomainId}
            data-testid="create-object"
            color="primary"
            onClick={onCreateRootObject}>
            {t('submit')}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
