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
import DeleteIcon from '@material-ui/icons/Delete';
import { Banner } from 'core/banner/Banner';
import gql from 'graphql-tag';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';

const deleteProjectMutation = gql`
  mutation deleteProject($input: DeleteProjectInput!) {
    deleteProject(input: $input) {
      __typename
      ... on DeleteProjectSuccessPayload {
        viewer {
          id
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
  onProjectDeleted: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const DeleteProjectModal = ({ projectId, onProjectDeleted, onClose }) => {
  const [errorMessage, setErrorMessage] = useState('');
  const [performProjectDeletion, { loading, data, error }] = useMutation(deleteProjectMutation);
  const onDeleteProject = async (event) => {
    event.preventDefault();
    const variables = {
      input: {
        projectId,
      },
    };
    performProjectDeletion({ variables });
  };

  useEffect(() => {
    if (!loading) {
      if (error) {
        setErrorMessage(error.message);
      } else if (data?.deleteProject) {
        const { deleteProject } = data;
        if (deleteProject.__typename === 'DeleteProjectSuccessPayload') {
          onProjectDeleted();
        } else if (deleteProject.__typename === 'ErrorPayload') {
          setErrorMessage(deleteProject.message);
        }
      }
    }
  }, [loading, data, error, onProjectDeleted]);

  let bannerContent = null;
  if (errorMessage) {
    bannerContent = <Banner data-testid="banner" content={errorMessage} />;
  }
  return (
    <Dialog open={true} onClose={onClose} aria-labelledby="form-dialog-title">
      <DialogTitle id="form-dialog-title">Delete the project</DialogTitle>
      <DialogContent>
        <DialogContentText>
          This action will delete everything in the project, it cannot be reversed.
          <div>{bannerContent}</div>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button variant="contained" onClick={onClose} color="primary">
          Cancel
        </Button>
        <Button
          variant="contained"
          color="secondary"
          startIcon={<DeleteIcon />}
          onClick={onDeleteProject}
          data-testid="delete-project">
          Delete
        </Button>
      </DialogActions>
    </Dialog>
  );
};
DeleteProjectModal.propTypes = propTypes;
