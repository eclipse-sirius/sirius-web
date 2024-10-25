/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { gql, useLazyQuery, useMutation } from '@apollo/client';
import { IconOverlay, Selection, SelectionContext, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import { useMachine } from '@xstate/react';
import { useEffect, useMemo } from 'react';
import { makeStyles } from 'tss-react/mui';
import { StateMachine } from 'xstate';
import { ModelBrowserTreeView } from '../components/ModelBrowserTreeView';
import {
  CreateModalProps,
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
import {
  ChangeChildCreationDescriptionEvent,
  ChangeContainerSelectionEvent,
  ChangeContainmentModeEvent,
  ChangeDomainEvent,
  CreateChildEvent,
  CreateModalContext,
  CreateModalEvent,
  CreateModalStateSchema,
  CreateRootEvent,
  FetchedChildCreationDescriptionsEvent,
  FetchedDomainsEvent,
  FetchedRootObjectCreationDescriptionsEvent,
  HandleCreateElementResponseEvent,
  SchemaValue,
  createModalMachine,
} from './CreateModalMachine';

const useStyle = makeStyles()((theme) => ({
  title: {
    opacity: 0.6,
    fontSize: theme.typography.caption.fontSize,
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
    $kind: ID!
    $referenceKind: String
    $descriptionId: String!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        referenceWidgetChildCreationDescriptions(
          kind: $kind
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
const isSuccessPayload = (
  payload: GQLCreateElementInReferencePayload
): payload is GQLCreateElementInReferenceSuccessPayload =>
  payload.__typename === 'CreateElementInReferenceSuccessPayload';

export const CreateModal = ({ editingContextId, widget, onClose, formId }: CreateModalProps) => {
  const { classes } = useStyle();
  const { addErrorMessage, addMessages } = useMultiToast();
  const [{ value, context }, dispatch] =
    useMachine<StateMachine<CreateModalContext, CreateModalStateSchema, CreateModalEvent>>(createModalMachine);
  const { createModal } = value as SchemaValue;

  const {
    domains,
    selectedChildCreationDescriptionId,
    creationDescriptions,
    newObjectId,
    containerSelected,
    containerId,
    containerKind,
    selectedDomainId,
  } = context;

  useEffect(() => {
    const changeContainmentModeEvent: ChangeContainmentModeEvent = {
      containment: widget.reference.containment,
      containerKind: widget.reference.containment ? widget.reference.ownerKind : null,
      containerId: widget.reference.containment ? widget.ownerId : null,
      type: 'CHANGE_CONTAINMENT_MODE',
    };
    dispatch(changeContainmentModeEvent);
  }, []);

  const [getDomains, { loading: domainsLoading, data: domainsData, error: domainsError }] = useLazyQuery<
    GQLGetDomainsQueryData,
    GQLGetDomainsQueryVariables
  >(getDomainsQuery, {
    variables: { editingContextId },
  });

  useEffect(() => {
    if (!domainsLoading) {
      if (domainsError) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (domainsData) {
        const fetchDomainsEvent: FetchedDomainsEvent = {
          type: 'HANDLE_FETCHED_DOMAINS',
          data: domainsData,
        };
        dispatch(fetchDomainsEvent);
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
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (descriptionsData) {
        const fetchDescriptionsEvent: FetchedRootObjectCreationDescriptionsEvent = {
          type: 'HANDLE_FETCHED_ROOT_OBJECT_CREATION_DESCRIPTIONS',
          data: descriptionsData,
        };
        dispatch(fetchDescriptionsEvent);
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
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (childCreationDescriptionsData) {
        const fetchChildCreationDescriptionsEvent: FetchedChildCreationDescriptionsEvent = {
          type: 'HANDLE_FETCHED_CHILD_CREATION_DESCRIPTIONS',
          data: childCreationDescriptionsData,
        };
        dispatch(fetchChildCreationDescriptionsEvent);
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
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
      if (createElementData) {
        const handleResponseEvent: HandleCreateElementResponseEvent = {
          type: 'HANDLE_CREATE_ELEMENT_RESPONSE',
          data: createElementData,
        };
        dispatch(handleResponseEvent);

        const { createElementInReference } = createElementData;
        if (isErrorPayload(createElementInReference) || isSuccessPayload(createElementInReference)) {
          const { messages } = createElementInReference;
          addMessages(messages);
        }
      }
    }
  }, [createElementLoading, createElementData, createElementError]);

  const onCreateObject = () => {
    let input: GQLCreateElementInReferenceInput | null = null;
    if (containerId) {
      if (createModal === 'validForChild') {
        dispatch({ type: 'CREATE_CHILD' } as CreateChildEvent);
        input = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          containerId,
          domainId: null,
          creationDescriptionId: selectedChildCreationDescriptionId,
          descriptionId: widget.descriptionId,
        };
      } else if (createModal === 'validForRoot') {
        dispatch({ type: 'CREATE_ROOT' } as CreateRootEvent);
        input = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: formId,
          referenceWidgetId: widget.id,
          containerId,
          domainId: selectedDomainId,
          creationDescriptionId: selectedChildCreationDescriptionId,
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
    const changeDomainEvent: ChangeDomainEvent = { type: 'CHANGE_DOMAIN', domainId: value };
    dispatch(changeDomainEvent);
  };

  const onChildCreationDescriptionChange = (event) => {
    const value = event.target.value;
    const changeChildCreationDescriptionEvent: ChangeChildCreationDescriptionEvent = {
      type: 'CHANGE_CHILD_CREATION_DESCRIPTION',
      childCreationDescriptionId: value,
    };
    dispatch(changeChildCreationDescriptionEvent);
  };

  useEffect(() => {
    if (createModal === 'loadingChildCreationDescription' && containerKind) {
      getChildCreationDescription({
        variables: {
          editingContextId,
          kind: containerKind,
          referenceKind: widget.reference.referenceKind,
          descriptionId: widget.descriptionId,
        },
      });
    }
    if (createModal === 'loadingDomains') {
      getDomains({ variables: { editingContextId } });
    }
    if (createModal === 'loadingRootObjectCreationDescriptions' && selectedDomainId) {
      getRootObjectCreationDescriptions({
        variables: {
          editingContextId,
          domainId: selectedDomainId,
          referenceKind: widget.reference.referenceKind,
          descriptionId: widget.descriptionId,
        },
      });
    }
    if (createModal === 'success') {
      onClose(newObjectId);
    }
  }, [createModal]);

  const onBrowserSelection = (selection: Selection) => {
    const changeContainerSelectionEvent: ChangeContainerSelectionEvent = {
      type: 'CHANGE_CONTAINER_SELECTION',
      container: selection,
    };
    dispatch(changeContainerSelectionEvent);
  };

  const markedItemIds = useMemo(() => [], []);

  return (
    <SelectionContext.Provider
      value={{
        selection: containerSelected,
        setSelection: onBrowserSelection,
      }}>
      <Dialog
        open={true}
        onClose={() => onClose(null)}
        aria-labelledby="dialog-title"
        fullWidth
        data-testid="create-modal">
        <DialogTitle id="dialog-title">Create an object</DialogTitle>
        <DialogContent>
          {widget.reference.containment ? null : (
            <ModelBrowserTreeView
              editingContextId={editingContextId}
              widget={widget}
              markedItemIds={markedItemIds}
              enableMultiSelection={false}
              title={'Select the container'}
              leafType={'container'}
              ownerKind={widget.reference.referenceKind}
            />
          )}
          {containerKind === 'siriusWeb://document' && (
            <>
              <span className={classes.title}>Select the domain</span>
              <Select
                variant="standard"
                value={selectedDomainId}
                onChange={onDomainChange}
                disabled={createModal === 'loadingDomains' || createModal === 'creatingChild'}
                labelId="createModalChildCreationDescriptionLabel"
                fullWidth
                data-testid="domain">
                {domains.map((domain) => (
                  <MenuItem value={domain.id} key={domain.id}>
                    {domain.label}
                  </MenuItem>
                ))}
              </Select>
            </>
          )}
          <span className={classes.title}>Select the object type</span>
          <Select
            variant="standard"
            classes={{ select: classes.select }}
            value={selectedChildCreationDescriptionId}
            onChange={onChildCreationDescriptionChange}
            disabled={createModal !== 'validForChild' && createModal !== 'validForRoot'}
            labelId="createModalChildCreationDescriptionLabel"
            fullWidth
            data-testid="childCreationDescription">
            {creationDescriptions.map((creationDescription) => (
              <MenuItem value={creationDescription.id} key={creationDescription.id}>
                {creationDescription.iconURL.length > 0 && (
                  <ListItemIcon className={classes.iconRoot}>
                    <IconOverlay iconURL={creationDescription.iconURL} alt={creationDescription.label} />
                  </ListItemIcon>
                )}
                <ListItemText primary={creationDescription.label} />
              </MenuItem>
            ))}
          </Select>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            color="primary"
            type="button"
            data-testid="create-object"
            onClick={onCreateObject}
            disabled={createModal !== 'validForChild' && createModal !== 'validForRoot'}>
            Create
          </Button>
        </DialogActions>
      </Dialog>
    </SelectionContext.Provider>
  );
};
