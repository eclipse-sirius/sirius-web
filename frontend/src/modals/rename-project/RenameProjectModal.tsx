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
import { useMutation } from 'common/GraphQLHooks';
import { Buttons, ActionButton } from 'core/button/Button';
import { Form } from 'core/form/Form';
import { Label } from 'core/label/Label';
import { Textfield } from 'core/textfield/Textfield';
import gql from 'graphql-tag';
import { Modal } from 'modals/Modal';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';

const renameProjectMutation = gql`
  mutation renameProject($input: RenameProjectInput!) {
    renameProject(input: $input) {
      __typename
      ... on RenameProjectSuccessPayload {
        project {
          name
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

const propTypes = {
  projectId: PropTypes.string.isRequired,
  initialProjectName: PropTypes.string.isRequired,
  onProjectRenamed: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

/*
 * TODO: state.isNameValid is not used. It used to be passed to the <Textfield> as invalid={!isNameValid}, but
 * 'invalid' is not a prop supported by Textfield...
 */

export const RenameProjectModal = ({ projectId, initialProjectName, onProjectRenamed, onClose }) => {
  const initialState = {
    name: initialProjectName,
    isNameValid: false,
    error: '',
    isValid: false,
  };
  const [state, setState] = useState(initialState);
  const { name, isValid } = state;

  const onNewName = (event) => {
    const newName = event.target.value;

    setState(() => {
      let isNameValid = newName && newName.length >= 1;
      let error = '';
      if (!isNameValid) {
        error = 'The name is required';
      }
      return {
        name: newName,
        isNameValid,
        error,
        isValid: isNameValid,
      };
    });
  };

  const [renameProject, result] = useMutation(renameProjectMutation, {}, 'renameProject');
  useEffect(() => {
    if (!result.loading) {
      const { data, error } = result;
      if (error) {
        setState((prevState) => {
          const { name, isNameValid } = prevState;
          return { name, isNameValid, error, isValid: false };
        });
      } else {
        const { renameProject } = data.data;
        if (renameProject.__typename === 'RenameProjectSuccessPayload') {
          onProjectRenamed();
        } else if (renameProject.__typename === 'ErrorPayload') {
          const error = renameProject.message;
          setState((prevState) => {
            const { name, isNameValid } = prevState;
            return { name, isNameValid, error, isValid: false };
          });
        }
      }
    }
  }, [result, onProjectRenamed]);

  const onRenameProject = (event) => {
    event.preventDefault();
    const input = {
      projectId,
      newName: name,
    };
    renameProject({ input });
  };

  return (
    <Modal title="Rename the project" onClose={onClose}>
      <Form onSubmit={onRenameProject}>
        <Label value="Name">
          <Textfield
            name="name"
            placeholder="Enter the new project name"
            value={name}
            onChange={onNewName}
            data-testid="name"
          />
        </Label>
        <Buttons>
          <ActionButton type="submit" disabled={!isValid} label="Rename" data-testid="rename-project" />
        </Buttons>
      </Form>
    </Modal>
  );
};
RenameProjectModal.propTypes = propTypes;
