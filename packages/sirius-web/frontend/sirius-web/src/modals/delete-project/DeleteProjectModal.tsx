/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import {
  DeleteProjectModalProps,
  GQLDeleteProjectMutationData,
  GQLDeleteProjectPayload,
  GQLErrorPayload,
} from './DeleteProjectModal.types';
import {
  DeleteProjectModalContext,
  DeleteProjectModalEvent,
  HandleResponseEvent,
  HideToastEvent,
  RequestProjectDeletionEvent,
  SchemaValue,
  ShowToastEvent,
  deleteProjectModalMachine,
} from './DeleteProjectModalMachine';

const deleteProjectMutation = gql`
  mutation deleteProject($input: DeleteProjectInput!) {
    deleteProject(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDeleteProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
export const DeleteProjectModal = ({ projectId, onDelete, onClose }: DeleteProjectModalProps) => {
  const [{ value, context }, dispatch] = useMachine<DeleteProjectModalContext, DeleteProjectModalEvent>(
    deleteProjectModalMachine
  );
  const { toast, deleteProjectModal } = value as SchemaValue;
  const { message } = context;

  const [deleteProject, { loading, data, error }] = useMutation<GQLDeleteProjectMutationData>(deleteProjectMutation);
  useEffect(() => {
    if (!loading) {
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: error.message,
        };
        dispatch(showToastEvent);
      }
      if (data) {
        const event: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data };
        dispatch(event);

        const { deleteProject } = data;
        if (isErrorPayload(deleteProject)) {
          const { message } = deleteProject;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, onDelete, dispatch]);

  const onDeleteProject = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    const requestProjectDeletionEvent: RequestProjectDeletionEvent = { type: 'REQUEST_PROJECT_DELETION' };
    dispatch(requestProjectDeletionEvent);

    event.preventDefault();
    const variables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
      },
    };
    deleteProject({ variables });
  };

  useEffect(() => {
    if (deleteProjectModal === 'success') {
      onDelete();
    }
  }, [deleteProjectModal, onDelete]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Delete the project</DialogTitle>
        <DialogContent>
          <DialogContentText>
            This action will delete everything in the project, it cannot be reversed.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={deleteProjectModal !== 'idle'}
            onClick={onDeleteProject}
            color="primary"
            data-testid="delete-project">
            Delete
          </Button>
        </DialogActions>
      </Dialog>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
