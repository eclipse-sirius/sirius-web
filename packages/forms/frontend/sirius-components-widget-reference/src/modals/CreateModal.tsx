/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { Selection, ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useContext, useEffect } from 'react';
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
  createModalMachine,
  CreateRootEvent,
  FetchedChildCreationDescriptionsEvent,
  FetchedDomainsEvent,
  FetchedRootObjectCreationDescriptionsEvent,
  HandleCreateElementResponseEvent,
  SchemaValue,
} from './CreateModalMachine';

const useStyle = makeStyles((theme) => ({
  title: {
    opacity: 0.6,
    fontSize: theme.typography.caption.fontSize,
  },
  select: {
    display: 'flex',
    alignItems: 'center',
  },
  iconRoot: {
    minWidth: theme.spacing(3),
  },
  iconContainer: {
    position: 'relative',
    width: '16px',
    height: '16px',
  },
  icon: {
    position: 'absolute',
    top: 0,
    left: 0,
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
  query getChildCreationDescriptions($editingContextId: ID!, $kind: ID!, $referenceKind: String) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        childCreationDescriptions(kind: $kind, referenceKind: $referenceKind) {
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
    $suggested: Boolean!
    $referenceKind: String
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        rootObjectCreationDescriptions(domainId: $domainId, suggested: $suggested, referenceKind: $referenceKind) {
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
  const classes = useStyle();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { addErrorMessage, addMessages } = useMultiToast();
  const [{ value, context }, dispatch] = useMachine<CreateModalContext, CreateModalEvent>(createModalMachine);
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
      };
    }
    createElementInReference({ variables: { input } });
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
          suggested: false,
          referenceKind: widget.reference.referenceKind,
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

  return (
    <>
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
              selection={containerSelected}
              setSelection={onBrowserSelection}
              widget={widget}
              markedItemIds={[]}
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
                    <div className={classes.iconContainer}>
                      {creationDescription.iconURL.map((icon, index) => (
                        <img
                          height="16"
                          width="16"
                          key={index}
                          alt={creationDescription.label}
                          src={httpOrigin + icon}
                          className={classes.icon}
                          style={{ zIndex: index }}></img>
                      ))}
                    </div>
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
    </>
  );
};
