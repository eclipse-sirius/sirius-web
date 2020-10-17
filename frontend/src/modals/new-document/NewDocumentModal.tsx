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
import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import gql from 'graphql-tag';

import { useQuery, useMutation } from 'common/GraphQLHooks';
import { ActionButton } from 'core/button/Button';
import { Form } from 'core/form/Form';
import { Select } from 'core/select/Select';
import { Label } from 'core/label/Label';
import { Textfield } from 'core/textfield/Textfield';
import { Modal } from 'modals/Modal';

const createDocumentMutation = gql`
  mutation createDocument($input: CreateDocumentInput!) {
    createDocument(input: $input) {
      __typename
      ... on CreateDocumentSuccessPayload {
        document {
          id
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

const getStereotypeDescriptionsQuery = gql`
  query getStereotypeDescriptions {
    viewer {
      stereotypeDescriptions {
        id
        label
      }
    }
  }
`.loc.source.body;

const propTypes = {
  projectId: PropTypes.string.isRequired,
  onDocumentCreated: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const NewDocumentModal = ({ projectId, onDocumentCreated, onClose }) => {
  const initialState = {
    name: '',
    stereotypeDescriptions: [],
    selectedStereotypeDescriptionId: undefined,
    isValid: false,
    message: undefined,
  };
  const [state, setState] = useState(initialState);
  const { name, stereotypeDescriptions, selectedStereotypeDescriptionId, isValid } = state;

  // Retrieve the available stereotypeDescriptions
  const { loading, data, error } = useQuery(getStereotypeDescriptionsQuery, {}, 'getStereotypeDescriptions');
  useEffect(() => {
    if (!loading) {
      if (!error && data) {
        const { stereotypeDescriptions } = data.data.viewer;

        setState((prevState) => {
          const newState = { ...prevState };
          newState.stereotypeDescriptions = [...prevState.stereotypeDescriptions, ...stereotypeDescriptions];
          if (newState.stereotypeDescriptions.length > 0) {
            newState.selectedStereotypeDescriptionId = newState.stereotypeDescriptions[0].id;
          }
          return newState;
        });
      }
    }
  }, [loading, error, data]);

  // Update the name in the state
  const onNewName = (event) => {
    const newName = event.target.value;

    setState((prevState) => {
      const newState = { ...prevState };
      newState.name = newName;
      newState.isValid = newName != null && newName.length > 0;
      newState.message = 'Please enter a name for the new project.';
      return newState;
    });
  };

  // Update the currently selected stereotypeDescription
  const onNewSelectedStereotypeDescription = (event) => {
    const value = event.target.value;

    setState((prevState) => {
      const newState = { ...prevState };
      newState.selectedStereotypeDescriptionId = value;
      return newState;
    });
  };

  // Validate the form
  useEffect(() => {
    let isValid = true;

    isValid = isValid && name && name.trim().length > 0;
    isValid = isValid && selectedStereotypeDescriptionId;

    setState((prevState) => {
      return { ...prevState, isValid };
    });
  }, [name, selectedStereotypeDescriptionId]);

  // Execute the creation of a new document and redirect to the newly created document
  const [createDocument, createDocumentResult] = useMutation(createDocumentMutation, {}, 'createDocument');
  const createNewDocument = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        projectId,
        name: name.trim(),
        stereotypeDescriptionId: selectedStereotypeDescriptionId,
      },
    };
    createDocument(variables);
  };
  useEffect(() => {
    if (!createDocumentResult.loading) {
      const { createDocument } = createDocumentResult.data.data;
      if (createDocument.__typename === 'CreateDocumentSuccessPayload') {
        const { id } = createDocument.document;
        onDocumentCreated(id);
      } else if (createDocument.__typename === 'ErrorPayload') {
        setState((prevState) => {
          const newState = { ...prevState };
          newState.message = createDocument.message;
          newState.isValid = false;
          return newState;
        });
      }
    }
  }, [createDocumentResult, onDocumentCreated]);

  return (
    <Modal title="Create a new model" onClose={onClose}>
      <Form onSubmit={createNewDocument}>
        <Label value="Name">
          <Textfield
            name="name"
            placeholder="Enter the model name"
            value={name}
            onChange={onNewName}
            autoFocus
            data-testid="name"
          />
        </Label>
        <Label value="Model type">
          <Select
            name="stereotype"
            value={selectedStereotypeDescriptionId}
            options={stereotypeDescriptions}
            onChange={onNewSelectedStereotypeDescription}
            data-testid="stereotype"
          />
        </Label>
        <ActionButton type="submit" disabled={!isValid} label="Create" data-testid="create-document" />
        <ActionButton onClick={onClose} label="Cancel" data-testid="cancel" />
      </Form>
    </Modal>
  );
};
NewDocumentModal.propTypes = propTypes;
