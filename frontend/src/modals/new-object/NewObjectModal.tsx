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
import { useMutation, useQuery } from 'common/GraphQLHooks';
import { ActionButton } from 'core/button/Button';
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
`.loc.source.body;

const getChildCreationDescriptionsQuery = gql`
  query getChildCreationDescriptions($classId: ID!) {
    viewer {
      childCreationDescriptions(classId: $classId) {
        id
        label
      }
    }
  }
`.loc.source.body;

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

  const { loading: descriptionsLoading, data: descriptionsResult } = useQuery(
    getChildCreationDescriptionsQuery,
    { classId },
    'getChildCreationDescriptions'
  );
  useEffect(() => {
    if (!descriptionsLoading && descriptionsResult && descriptionsResult.data.viewer) {
      setState((prevState) => {
        const newState = { ...prevState };
        newState.childCreationDescriptions = descriptionsResult.data.viewer.childCreationDescriptions;

        if (newState.childCreationDescriptions.length > 0) {
          newState.selectedChildCreationDescriptionId = newState.childCreationDescriptions[0].id;
        }

        return newState;
      });
    }
  }, [descriptionsLoading, descriptionsResult]);

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
  const [createChild, createChildResult] = useMutation(createChildMutation, {}, 'createChild');
  const onCreateChild = (event) => {
    event.preventDefault();
    const input = {
      projectId,
      objectId,
      childCreationDescriptionId: selectedChildCreationDescriptionId,
    };
    createChild({ input });
  };
  useEffect(() => {
    if (createChildResult?.data?.data?.createChild?.object) {
      onObjectCreated(createChildResult.data.data.createChild.object);
    }
  }, [createChildResult, onObjectCreated]);

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
        <ActionButton type="submit" label="Create" data-testid="create-object" />
        <ActionButton onClick={onClose} label="Cancel" data-testid="cancel" />
      </Form>
    </Modal>
  );
};
NewObjectModal.propTypes = propTypes;
