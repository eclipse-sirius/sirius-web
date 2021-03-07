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
import { useQuery, useMutation } from '@apollo/client';
import { ActionButton, Buttons } from 'core/button/Button';
import { Form } from 'core/form/Form';
import { Label } from 'core/label/Label';
import { Select } from 'core/select/Select';
import { Textfield } from 'core/textfield/Textfield';
import gql from 'graphql-tag';
import { Modal } from 'modals/Modal';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';

const createRepresentationMutation = gql`
  mutation createRepresentation($input: CreateRepresentationInput!) {
    createRepresentation(input: $input) {
      __typename
      ... on CreateRepresentationSuccessPayload {
        representation {
          id
          label
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
  query getRepresentationDescriptions($classId: ID!) {
    viewer {
      representationDescriptions(classId: $classId) {
        edges {
          node {
            id
            label
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
`;

const propTypes = {
  projectId: PropTypes.string.isRequired,
  classId: PropTypes.string.isRequired,
  objectId: PropTypes.string.isRequired,
  onRepresentationCreated: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const NewRepresentationModal = ({ projectId, classId, objectId, onRepresentationCreated, onClose }) => {
  const initialState = {
    representationDescriptions: [],
    selectedRepresentationDescription: undefined,
    name: '',
    userChosenName: false,
  };
  const [state, setState] = useState(initialState);
  const { representationDescriptions, selectedRepresentationDescription, name } = state;

  const { loading, data: queryResult, error } = useQuery(getRepresentationDescriptionsQuery, {
    variables: { classId },
  });
  useEffect(() => {
    if (!loading && !error && queryResult) {
      const representationDescriptions = queryResult.viewer.representationDescriptions.edges.map((edge) => edge.node);

      setState((prevState) => {
        const newState = { ...prevState };
        newState.representationDescriptions = representationDescriptions;

        if (newState.representationDescriptions.length > 0) {
          newState.selectedRepresentationDescription = newState.representationDescriptions[0];
          if (!prevState.userChosenName) {
            newState.name = newState.selectedRepresentationDescription.label;
          }
        }
        return newState;
      });
    }
  }, [loading, queryResult, error]);

  const setSelectedRepresentationDescription = (event) => {
    const selectedId = event.target.value;

    setState((prevState) => {
      const newState = { ...prevState };
      const selectedRepresentationDescription = representationDescriptions.find(
        (candidate) => candidate.id === selectedId
      );
      if (selectedRepresentationDescription) {
        newState.selectedRepresentationDescription = selectedRepresentationDescription;
        if (!prevState.userChosenName) {
          newState.name = newState.selectedRepresentationDescription.label;
        }
      }
      return newState;
    });
  };

  const onNewName = (event) => {
    const newName = event.target.value;

    setState((prevState) => {
      const newState = { ...prevState };
      newState.name = newName;
      newState.userChosenName = true;
      return newState;
    });
  };

  const [
    createRepresentation,
    { loading: createRepresentationLoading, data: createRepresentationData, error: createRepresentationError },
  ] = useMutation(createRepresentationMutation);
  useEffect(() => {
    if (
      !createRepresentationLoading &&
      !createRepresentationError &&
      createRepresentationData?.createRepresentation?.representation
    ) {
      const { id, label, __typename } = createRepresentationData.createRepresentation.representation;
      onRepresentationCreated({ id, label, kind: __typename });
    }
  }, [createRepresentationLoading, createRepresentationData, createRepresentationError, onRepresentationCreated]);

  const onCreateRepresentation = (event) => {
    event.preventDefault();
    const input = {
      projectId,
      objectId,
      representationDescriptionId: selectedRepresentationDescription.id,
      representationName: name,
    };
    createRepresentation({ variables: { input } });
  };

  let invalid = !(name != null && name.length > 0 && selectedRepresentationDescription); // TODO Textfield does not actually support an "invalid" prop

  let selectedValue = undefined;
  if (selectedRepresentationDescription) {
    selectedValue = selectedRepresentationDescription.id;
  }

  return (
    <Modal title="Create a new representation" onClose={onClose}>
      <Form onSubmit={onCreateRepresentation}>
        <Label value="Name">
          <Textfield
            name="name"
            placeholder="Enter the representation name"
            value={name}
            onChange={onNewName}
            autoFocus
            data-testid="name"
          />
        </Label>
        <Label value="Description">
          <Select
            name="description"
            value={selectedValue}
            options={representationDescriptions}
            onChange={setSelectedRepresentationDescription}
            data-testid="description"
          />
        </Label>
        <Buttons>
          <ActionButton type="submit" disabled={invalid} label="Create" data-testid="create-representation" />
        </Buttons>
      </Form>
    </Modal>
  );
};
NewRepresentationModal.propTypes = propTypes;
