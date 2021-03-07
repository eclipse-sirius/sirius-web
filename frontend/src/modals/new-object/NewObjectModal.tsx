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
import { useMutation, useQuery } from '@apollo/client';
import { ActionButton, Buttons } from 'core/button/Button';
import { Form } from 'core/form/Form';
import { Label } from 'core/label/Label';
import { Select } from 'core/select/Select';
import gql from 'graphql-tag';
import { Modal } from 'modals/Modal';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';

const createChildMutation = gql`
  mutation createChild($input: CreateChildInput!) {
    createChild(input: $input) {
      __typename
      ... on CreateChildSuccessPayload {
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

const getChildCreationDescriptionsQuery = gql`
  query getChildCreationDescriptions($classId: ID!) {
    viewer {
      childCreationDescriptions(classId: $classId) {
        id
        label
      }
    }
  }
`;

const propTypes = {
  projectId: PropTypes.string.isRequired,
  classId: PropTypes.string.isRequired,
  objectId: PropTypes.string.isRequired,
  onObjectCreated: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const NewObjectModal = ({ projectId, classId, objectId, onObjectCreated, onClose }) => {
  const initialState = {
    selectedChildCreationDescriptionId: undefined,
    childCreationDescriptions: [],
  };
  const [state, setState] = useState(initialState);
  const { selectedChildCreationDescriptionId, childCreationDescriptions } = state;

  const {
    loading: descriptionsLoading,
    data: descriptionsResult,
    error: descriptionError,
  } = useQuery(getChildCreationDescriptionsQuery, { variables: { classId } });
  useEffect(() => {
    if (!descriptionsLoading && !descriptionError && descriptionsResult?.viewer) {
      setState((prevState) => {
        const newState = { ...prevState };
        newState.childCreationDescriptions = descriptionsResult.viewer.childCreationDescriptions;

        if (newState.childCreationDescriptions.length > 0) {
          newState.selectedChildCreationDescriptionId = newState.childCreationDescriptions[0].id;
        }

        return newState;
      });
    }
  }, [descriptionsLoading, descriptionsResult, descriptionError]);

  // Used to update the selected child creation description id
  const setSelectedChildCreationDescriptionId = (event) => {
    const selectedChildCreationDescriptionId = event.target.value;

    setState((prevState) => {
      const newState = { ...prevState };
      newState.selectedChildCreationDescriptionId = selectedChildCreationDescriptionId;
      return newState;
    });
  };

  // Create the new child
  const [createChild, { loading, data, error }] = useMutation(createChildMutation);
  const onCreateChild = (event) => {
    event.preventDefault();
    const input = {
      projectId,
      objectId,
      childCreationDescriptionId: selectedChildCreationDescriptionId,
    };
    createChild({ variables: { input } });
  };
  useEffect(() => {
    if (!loading && !error && data?.createChild?.object) {
      onObjectCreated(data.createChild.object);
    }
  }, [loading, data, error, onObjectCreated]);

  return (
    <Modal title="Create a new object" onClose={onClose}>
      <Form onSubmit={onCreateChild}>
        <Label value="Object type">
          <Select
            name="stereotype"
            value={selectedChildCreationDescriptionId}
            options={childCreationDescriptions}
            onChange={setSelectedChildCreationDescriptionId}
            autoFocus
            data-testid="stereotype"
          />
        </Label>
        <Buttons>
          <ActionButton type="submit" label="Create" data-testid="create-object" />
        </Buttons>
      </Form>
    </Modal>
  );
};
NewObjectModal.propTypes = propTypes;
