/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { ActionButton, Buttons } from 'core/button/Button';
import { Checkbox } from 'core/checkbox/Checkbox';
import { Form } from 'core/form/Form';
import { Label } from 'core/label/Label';
import { Select } from 'core/select/Select';
import gql from 'graphql-tag';
import { Modal } from 'modals/Modal';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import styles from './NewRootObjectModal.module.css';

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
  query getNamespaces {
    viewer {
      namespaces {
        id
        label
      }
    }
  }
`;

const getRootObjectCreationDescriptionsQuery = gql`
  query getRootObjectCreationDescriptions($namespaceId: ID!, $suggested: Boolean!) {
    viewer {
      rootObjectCreationDescriptions(namespaceId: $namespaceId, suggested: $suggested) {
        id
        label
      }
    }
  }
`;

const propTypes = {
  projectId: PropTypes.string.isRequired,
  documentId: PropTypes.string.isRequired,
  onObjectCreated: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const NewRootObjectModal = ({ projectId, documentId, onObjectCreated, onClose }) => {
  const initialState = {
    namespaces: [],
    selectedNamespaceId: undefined,
    rootObjectCreationDescriptions: [],
    selectedRootObjectCreationDescriptionId: undefined,
    suggestedRootObject: true,
  };
  const [state, setState] = useState(initialState);
  const {
    namespaces,
    selectedNamespaceId,
    rootObjectCreationDescriptions,
    selectedRootObjectCreationDescriptionId,
    suggestedRootObject,
  } = state;

  // Fetch the available namespaces only once, they are supposed static (at least for the lifetime of the modal)
  const { loading: namespacesLoading, data: namespacesResult, error: namespacesError } = useQuery(getNamespacesQuery);
  useEffect(() => {
    if (!namespacesLoading && !namespacesError && namespacesResult?.viewer) {
      setState((prevState) => {
        const namespaces = namespacesResult.viewer.namespaces;
        const selectedNamespaceId = namespaces.length > 0 ? namespaces[0].id : undefined;
        return { ...prevState, namespaces, selectedNamespaceId };
      });
    }
  }, [namespacesLoading, namespacesResult, namespacesError]);

  // Fetch the corresponding object creation description whenever the user selects a new namespace or toggles the checkbox
  const [
    getRootObjectCreationDescriptions,
    { loading: descriptionsLoading, data: descriptionsResult, error: descriptionError },
  ] = useLazyQuery(getRootObjectCreationDescriptionsQuery);
  useEffect(() => {
    if (selectedNamespaceId) {
      getRootObjectCreationDescriptions({
        variables: { namespaceId: selectedNamespaceId, suggested: suggestedRootObject },
      });
    }
  }, [suggestedRootObject, selectedNamespaceId, getRootObjectCreationDescriptions]);

  // Updates the state when we get the result from the lazy query above
  useEffect(() => {
    if (!descriptionsLoading && !descriptionError && descriptionsResult?.viewer?.rootObjectCreationDescriptions) {
      setState((prevState) => {
        const rootObjectCreationDescriptions = descriptionsResult.viewer.rootObjectCreationDescriptions;
        const selectedRootObjectCreationDescriptionId =
          rootObjectCreationDescriptions.length > 0 ? rootObjectCreationDescriptions[0].id : undefined;
        return { ...prevState, rootObjectCreationDescriptions, selectedRootObjectCreationDescriptionId };
      });
    }
  }, [descriptionsLoading, descriptionsResult, descriptionError]);

  const setSelectedNamespaceId = (event) => {
    const selectedNamespaceId = event.target.value;

    setState((prevState) => {
      const newState = { ...prevState };
      newState.selectedNamespaceId = selectedNamespaceId;
      return newState;
    });
  };

  // Used to update the selected root object creation description id
  const setSelectedRootObjectCreationDescriptionId = (event) => {
    const selectedRootObjectCreationDescriptionId = event.target.value;

    setState((prevState) => {
      return { ...prevState, selectedRootObjectCreationDescriptionId };
    });
  };

  // Used to update root object list
  const onChangePreferred = (event) => {
    const suggestedRootObject = event.target.checked;
    setState((prevState) => {
      return { ...prevState, suggestedRootObject };
    });
  };

  // Create the new child
  const [createRootObject, { loading, error, data }] = useMutation(createRootObjectMutation);
  useEffect(() => {
    if (!loading && !error && data?.createRootObject?.object) {
      onObjectCreated(data.createRootObject.object);
    }
  }, [loading, error, data, onObjectCreated]);

  const onCreateRootObject = (event) => {
    event.preventDefault();
    const input = {
      projectId,
      documentId,
      namespaceId: selectedNamespaceId,
      rootObjectCreationDescriptionId: selectedRootObjectCreationDescriptionId,
    };
    createRootObject({ variables: { input } });
  };

  return (
    <Modal title="Create a new root object" onClose={onClose}>
      <Form onSubmit={onCreateRootObject}>
        <Label value="Namespace">
          <Select
            name="namespace"
            value={selectedNamespaceId}
            options={namespaces}
            onChange={setSelectedNamespaceId}
            autoFocus
            data-testid="namespace"
          />
        </Label>
        <div className={styles.rootObjectArea}>
          <Label value="Object type">
            <Select
              name="type"
              value={selectedRootObjectCreationDescriptionId}
              options={rootObjectCreationDescriptions}
              onChange={setSelectedRootObjectCreationDescriptionId}
              data-testid="type"
            />
          </Label>
          <Checkbox
            name="suggested"
            checked={suggestedRootObject}
            label="Show only suggested root type"
            onChange={onChangePreferred}
            data-testid="suggested"></Checkbox>
        </div>
        <Buttons>
          <ActionButton type="submit" label="Create" data-testid="create-object" />
        </Buttons>
      </Form>
    </Modal>
  );
};
NewRootObjectModal.propTypes = propTypes;
