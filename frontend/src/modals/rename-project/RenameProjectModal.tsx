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
import { useMutation } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import gql from 'graphql-tag';
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
`;

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

  const [renameProject, { loading, data, error }] = useMutation(renameProjectMutation);
  useEffect(() => {
    if (!loading) {
      if (error) {
        setState((prevState) => {
          const { name, isNameValid } = prevState;
          return { name, isNameValid, error: error.message, isValid: false };
        });
      } else if (data?.renameProject) {
        const { renameProject } = data;
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
  }, [loading, data, error, onProjectRenamed]);

  const onRenameProject = (event) => {
    event.preventDefault();
    const input = {
      projectId,
      newName: name,
    };
    renameProject({ variables: { input } });
  };

  return (
    <Dialog open={true} onClose={onClose} aria-labelledby="form-dialog-title">
      <DialogTitle id="dialog-title">Rename the project</DialogTitle>
      <DialogContent>
        <DialogContentText></DialogContentText>
        <TextField
          autoFocus
          label="Name"
          placeholder="Enter the new project name"
          value={name}
          onChange={onNewName}
          data-testid="name"
          fullWidth
        />
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={!isValid}
          onClick={onRenameProject}
          color="primary"
          data-testid="rename-project">
          Rename
        </Button>
      </DialogActions>
    </Dialog>
  );
};
RenameProjectModal.propTypes = propTypes;
