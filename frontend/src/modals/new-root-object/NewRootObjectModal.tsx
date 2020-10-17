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
import { useLazyQuery, useMutation, useQuery } from 'common/GraphQLHooks';
import { ActionButton } from 'core/button/Button';
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
`.loc.source.body;

const getNamespacesQuery = gql`
  query getNamespaces {
    viewer {
      namespaces {
        id
        label
      }
    }
  }
`.loc.source.body;

const getRootObjectCreationDescriptionsQuery = gql`
  query getRootObjectCreationDescriptions($namespaceId: ID!, $suggested: Boolean!) {
    viewer {
      rootObjectCreationDescriptions(namespaceId: $namespaceId, suggested: $suggested) {
        id
        label
      }
    }
  }
`.loc.source.body;

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

  const { loading: namespacesLoading, data: namespacesResult } = useQuery(getNamespacesQuery, {}, 'getNamespaces');
  useEffect(() => {
    if (!namespacesLoading && namespacesResult && namespacesResult?.data?.viewer) {
      setState((prevState) => {
        const namespaces = namespacesResult.data.viewer.namespaces;
        const selectedNamespaceId = namespaces.length > 0 ? namespaces[0].id : undefined;
        return { ...prevState, namespaces, selectedNamespaceId };
      });
    }
  }, [namespacesLoading, namespacesResult]);

  const [getRootObjectCreationDescriptions, { loading: descriptionsLoading, data: descriptionsResult }] = useLazyQuery(
    getRootObjectCreationDescriptionsQuery,
    {},
    'getRootObjectCreationDescriptions'
  );
  useEffect(() => {
    if (selectedNamespaceId) {
      getRootObjectCreationDescriptions({ namespaceId: selectedNamespaceId, suggested: suggestedRootObject });
    }
  }, [suggestedRootObject, selectedNamespaceId, getRootObjectCreationDescriptions]);

  useEffect(() => {
    if (
      !descriptionsLoading &&
      descriptionsResult &&
      descriptionsResult?.data?.viewer?.rootObjectCreationDescriptions
    ) {
      setState((prevState) => {
        const rootObjectCreationDescriptions = descriptionsResult.data.viewer.rootObjectCreationDescriptions;
        const selectedRootObjectCreationDescriptionId =
          rootObjectCreationDescriptions.length > 0 ? rootObjectCreationDescriptions[0].id : undefined;
        return { ...prevState, rootObjectCreationDescriptions, selectedRootObjectCreationDescriptionId };
      });
    }
  }, [descriptionsLoading, descriptionsResult]);

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
  const [createRootObject, createRootObjectResult] = useMutation(createRootObjectMutation, {}, 'createRootObject');
  const onCreateRootObject = (event) => {
    event.preventDefault();
    const input = {
      projectId,
      documentId,
      namespaceId: selectedNamespaceId,
      rootObjectCreationDescriptionId: selectedRootObjectCreationDescriptionId,
    };
    createRootObject({ input });
  };
  useEffect(() => {
    if (createRootObjectResult?.data?.data?.createRootObject?.object) {
      onObjectCreated(createRootObjectResult.data.data.createRootObject.object);
    }
  }, [createRootObjectResult, onObjectCreated]);

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
        <ActionButton type="submit" label="Create" data-testid="create-object" />
      </Form>
    </Modal>
  );
};
NewRootObjectModal.propTypes = propTypes;
