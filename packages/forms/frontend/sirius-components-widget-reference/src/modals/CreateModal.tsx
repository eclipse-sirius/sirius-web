/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { ModelBrowserTreeView } from '@eclipse-sirius/sirius-components-browser';
import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import {
  CreateModalProps,
  CreateModalState,
  GQLCreateElementInReferenceInput,
  GQLCreateElementInReferenceMutationData,
  GQLCreateElementInReferenceMutationVariables,
  GQLCreateElementInReferencePayload,
  GQLCreateElementInReferenceSuccessPayload,
  GQLErrorPayload,
  GQLGetChildCreationDescriptionsQueryData,
  GQLGetChildCreationDescriptionsQueryVariables,
  GQLGetDomainsQueryData,
  GQLGetDomainsQueryVariables,
  GQLGetRootObjectCreationDescriptionsQueryData,
  GQLGetRootObjectCreationDescriptionsQueryVariables,
} from './CreateModal.types';

const useStyle = makeStyles()((theme) => ({
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

const createElementInReferenceMutation = gql`
  mutation createElementInReference($input: CreateElementInReferenceInput!) {
    createElementInReference(input: $input) {
      __typename
      ... on CreateElementInReferenceSuccessPayload {
        object {
          id
          label
          kind
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
  query getChildCreationDescriptions(
    $editingContextId: ID!
    $containerId: ID!
    $referenceKind: String
    $descriptionId: String!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        referenceWidgetChildCreationDescriptions(
          containerId: $containerId
          referenceKind: $referenceKind
          descriptionId: $descriptionId
        ) {
          id
          label
          iconURL
        }
      }
    }
  }
`;

const getRootObjectCreationDescriptionsQuery = gql`
  query getRootObjectCreationDescriptions(
    $editingContextId: ID!
    $domainId: ID!
    $referenceKind: String
    $descriptionId: String!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        referenceWidgetRootCreationDescriptions(
          domainId: $domainId
          referenceKind: $referenceKind
          descriptionId: $descriptionId
        ) {
          id
          label
          iconURL
        }
      }
    }
  }
`;

const getDomainsQuery = gql`
  query getDomains($editingContextId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        domains(rootDomainsOnly: false) {
          id
          label
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLCreateElementInReferencePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isCreateElementSuccessPayload = (
  payload: GQLCreateElementInReferencePayload
): payload is GQLCreateElementInReferenceSuccessPayload =>
  payload.__typename === 'CreateElementInReferenceSuccessPayload';

export const CreateModal = ({ editingContextId, widget, onClose, formId }: CreateModalProps) => {
  const { classes } = useStyle();
  const { t } = useTranslation('sirius-components-widget-reference', { keyPrefix: 'createModal' });
  const { addErrorMessage, addMessages } = useMultiToast();

  const [state, setState] = useState<CreateModalState>({
    domains: [],
    selectedDomainId: '',
    creationDescriptions: [],
    selectedChildCreationDescriptionId: '',
    newObjectId: null,
    containerSelected: null,
    containerId: widget.reference.containment ? widget.ownerId : null,
    containerKind: widget.reference.containment ? widget.reference.ownerKind : null,
  });

  const {
    loading: domainsLoading,
    data: domainsData,
    error: domainsError,
  } = useQuery<GQLGetDomainsQueryData, GQLGetDomainsQueryVariables>(getDomainsQuery, {
    variables: { editingContextId },
    skip: widget.reference.containment,
  });

  useEffect(() => {
    if (!domainsLoading) {
      if (domainsError) {
        addErrorMessage(domainsError.message);
      }
      if (domainsData) {
        const { domains } = domainsData.viewer.editingContext;
        const selectedDomainId = domains[0]?.id || '';
        setState((prevState) => ({
          ...prevState,
          selectedDomainId: selectedDomainId,
          domains: domains,
        }));
      }
    }
  }, [domainsLoading, domainsData, domainsError]);

  const [
    getRootObjectCreationDescriptions,
    { loading: descriptionsLoading, data: descriptionsData, error: descriptionError },
  ] = useLazyQuery<GQLGetRootObjectCreationDescriptionsQueryData, GQLGetRootObjectCreationDescriptionsQueryVariables>(
    getRootObjectCreationDescriptionsQuery
  );

  useEffect(() => {
    if (!descriptionsLoading) {
      if (descriptionError) {
        addErrorMessage(descriptionError.message);
      }
      if (descriptionsData) {
        const referenceWidgetRootCreationDescriptions =
          descriptionsData.viewer.editingContext.referenceWidgetRootCreationDescriptions;
        const selectedChildCreationDescriptionId = referenceWidgetRootCreationDescriptions[0]?.id || '';
        setState((prevState) => ({
          ...prevState,
          creationDescriptions: referenceWidgetRootCreationDescriptions,
          selectedChildCreationDescriptionId: selectedChildCreationDescriptionId,
        }));
      }
    }
  }, [descriptionsLoading, descriptionsData, descriptionError]);

  const [
    getChildCreationDescription,
    {
      loading: childCreationDescriptionsLoading,
      data: childCreationDescriptionsData,
      error: childCreationDescriptionsError,
    },
  ] = useLazyQuery<GQLGetChildCreationDescriptionsQueryData, GQLGetChildCreationDescriptionsQueryVariables>(
    getChildCreationDescriptionsQuery
  );

  useEffect(() => {
    if (!childCreationDescriptionsLoading) {
      if (childCreationDescriptionsError) {
        addErrorMessage(childCreationDescriptionsError.message);
      }
      if (childCreationDescriptionsData) {
        const { referenceWidgetChildCreationDescriptions } = childCreationDescriptionsData.viewer.editingContext;
        const selectedChildCreationDescriptionId = referenceWidgetChildCreationDescriptions[0]?.id || '';
        setState((prevState) => ({
          ...prevState,
          creationDescriptions: referenceWidgetChildCreationDescriptions,
          selectedChildCreationDescriptionId: selectedChildCreationDescriptionId,
        }));
      }
    }
  }, [childCreationDescriptionsLoading, childCreationDescriptionsData, childCreationDescriptionsError]);

  const [
    createElementInReference,
    { loading: createElementLoading, error: createElementError, data: createElementData },
  ] = useMutation<GQLCreateElementInReferenceMutationData, GQLCreateElementInReferenceMutationVariables>(
    createElementInReferenceMutation
  );

  useEffect(() => {
    if (!createElementLoading) {
      if (createElementError) {
        addErrorMessage(createElementError.message);
      }
      if (createElementData) {
        const { createElementInReference } = createElementData;
        if (isErrorPayload(createElementInReference)) {
          const { messages } = createElementInReference;
          addMessages(messages);
        }
        if (isCreateElementSuccessPayload(createElementInReference)) {
          const { object } = createElementInReference;
          onClose(object.id);
        }
      }
    }
  }, [createElementLoading, createElementData, createElementError]);

  const onCreateObject = () => {
    let input: GQLCreateElementInReferenceInput | null = null;
    if (state.containerId) {
      if (state.containerKind !== 'siriusWeb://document') {
        input = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          containerId: state.containerId,
          domainId: null,
          creationDescriptionId: state.selectedChildCreationDescriptionId,
          descriptionId: widget.descriptionId,
        };
      } else {
        input = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          containerId: state.containerId,
          domainId: state.selectedDomainId,
          creationDescriptionId: state.selectedChildCreationDescriptionId,
          descriptionId: widget.descriptionId,
        };
      }
    }
    if (input) {
      createElementInReference({ variables: { input } });
    }
  };

  const onDomainChange = (event) => {
    const { value } = event.target;
    setState((prevState) => ({
      ...prevState,
      selectedDomainId: value,
    }));
  };

  const onChildCreationDescriptionChange = (event) => {
    const value = event.target.value;
    setState((prevState) => ({
      ...prevState,
      selectedChildCreationDescriptionId: value,
    }));
  };

  useEffect(() => {
    if (state.containerKind === 'siriusWeb://document' && state.selectedDomainId) {
      getRootObjectCreationDescriptions({
        variables: {
          editingContextId,
          domainId: state.selectedDomainId,
          referenceKind: widget.reference.referenceKind,
          descriptionId: widget.descriptionId,
        },
      });
    } else if (state.containerKind !== 'siriusWeb://document' && state.containerId) {
      getChildCreationDescription({
        variables: {
          editingContextId,
          containerId: state.containerId,
          referenceKind: widget.reference.referenceKind,
          descriptionId: widget.descriptionId,
        },
      });
    }
  }, [state.selectedDomainId, state.containerId, state.containerKind]);

  const onTreeItemClick = (_event, item: GQLTreeItem) => {
    setState((prevState) => ({
      ...prevState,
      containerSelected: item,
      containerId: item.id,
      containerKind: item.kind,
    }));
  };

  return (
    <Dialog
      open={true}
      onClose={() => onClose(null)}
      aria-labelledby="dialog-title"
      fullWidth
      data-testid="create-modal">
      <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
      <DialogContent>
        <Box sx={(theme) => ({ display: 'flex', flexDirection: 'column', gap: theme.spacing(1) })}>
          {widget.reference.containment ? null : (
            <ModelBrowserTreeView
              editingContextId={editingContextId}
              referenceKind={widget.reference.referenceKind}
              ownerId={widget.ownerId}
              descriptionId={widget.descriptionId}
              isContainment={widget.reference.containment}
              markedItemIds={[]}
              title={t('container.label')}
              leafType={'container'}
              ownerKind={widget.reference.referenceKind}
              onTreeItemClick={onTreeItemClick}
              selectedTreeItemIds={state.containerSelected ? [state.containerSelected.id] : []}
            />
          )}
          {state.containerKind === 'siriusWeb://document' && (
            <Box sx={{ display: 'flex', flexDirection: 'column' }}>
              <Typography gutterBottom variant="subtitle1">
                {t('domain.label')}
              </Typography>
              <Select
                variant="standard"
                value={state.selectedDomainId}
                onChange={onDomainChange}
                disabled={!state.selectedDomainId}
                labelId="createModalChildCreationDescriptionLabel"
                fullWidth
                data-testid="domain">
                {state.domains.map((domain) => (
                  <MenuItem value={domain.id} key={domain.id}>
                    {domain.label}
                  </MenuItem>
                ))}
              </Select>
            </Box>
          )}
          <Box sx={{ display: 'flex', flexDirection: 'column' }}>
            <Typography gutterBottom variant="subtitle1">
              {t('type.label')}
            </Typography>
            <Select
              variant="standard"
              classes={{ select: classes.select }}
              value={state.selectedChildCreationDescriptionId}
              onChange={onChildCreationDescriptionChange}
              disabled={!state.creationDescriptions}
              labelId="createModalChildCreationDescriptionLabel"
              fullWidth
              data-testid="childCreationDescription">
              {state.creationDescriptions.map((creationDescription) => (
                <MenuItem value={creationDescription.id} key={creationDescription.id}>
                  {creationDescription.iconURL.length > 0 && (
                    <ListItemIcon className={classes.iconRoot}>
                      <IconOverlay iconURLs={creationDescription.iconURL} alt={creationDescription.label} />
                    </ListItemIcon>
                  )}
                  <ListItemText primary={creationDescription.label} />
                </MenuItem>
              ))}
            </Select>
          </Box>
        </Box>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          color="primary"
          type="button"
          data-testid="create-object"
          onClick={onCreateObject}
          disabled={!state.selectedChildCreationDescriptionId}>
          {t('submit')}
        </Button>
      </DialogActions>
    </Dialog>
  );
};
