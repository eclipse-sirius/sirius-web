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
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLRenameProjectMutationData,
  GQLRenameProjectPayload,
  RenameProjectModalProps,
} from './RenameProjectModal.types';
import {
  ChangeInitialNameEvent,
  ChangeNameEvent,
  HandleResponseEvent,
  HideToastEvent,
  RenameProjectModalContext,
  RenameProjectModalEvent,
  RequestProjectRenamingEvent,
  SchemaValue,
  ShowToastEvent,
  renameProjectModalMachine,
} from './RenameProjectModalMachine';

const renameProjectMutation = gql`
  mutation renameProject($input: RenameProjectInput!) {
    renameProject(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLRenameProjectPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
export const RenameProjectModal = ({ projectId, initialProjectName, onRename, onClose }: RenameProjectModalProps) => {
  const [{ value, context }, dispatch] = useMachine<RenameProjectModalContext, RenameProjectModalEvent>(
    renameProjectModalMachine,
    {
      context: {
        name: initialProjectName,
        initialName: initialProjectName,
      },
    }
  );
  const { toast, renameProjectModal } = value as SchemaValue;
  const { name, nameIsInvalid, nameMessage, message } = context;

  useEffect(() => {
    const changeInitialNameEvent: ChangeInitialNameEvent = {
      type: 'CHANGE_INITIAL_NAME',
      initialName: initialProjectName,
    };
    dispatch(changeInitialNameEvent);
  }, [dispatch, initialProjectName]);

  const onNewName = (event: React.ChangeEvent<HTMLInputElement>) => {
    const name = event.target.value;
    const changeNameEvent: ChangeNameEvent = { type: 'CHANGE_NAME', name };
    dispatch(changeNameEvent);
  };

  const [renameProject, { loading, data, error }] = useMutation<GQLRenameProjectMutationData>(renameProjectMutation);
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

        const { renameProject } = data;
        if (isErrorPayload(renameProject)) {
          const { message } = renameProject;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, onRename, dispatch]);

  const onRenameProject = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    const requestProjectRenamingEvent: RequestProjectRenamingEvent = { type: 'REQUEST_PROJECT_RENAMING' };
    dispatch(requestProjectRenamingEvent);

    event.preventDefault();
    const variables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
        newName: name,
      },
    };
    renameProject({ variables });
  };

  useEffect(() => {
    if (renameProjectModal === 'success') {
      onRename();
    }
  }, [renameProjectModal, onRename]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Rename the project</DialogTitle>
        <DialogContent>
          <TextField
            value={name}
            onChange={onNewName}
            error={nameIsInvalid}
            helperText={nameMessage}
            label="Name"
            placeholder="Enter a new project name"
            data-testid="rename-textfield"
            autoFocus
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={renameProjectModal !== 'valid'}
            onClick={onRenameProject}
            color="primary"
            data-testid="rename-project">
            Rename
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
