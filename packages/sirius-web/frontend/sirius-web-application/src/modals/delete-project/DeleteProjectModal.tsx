/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { useEffect } from 'react';
import { DeleteProjectModalProps } from './DeleteProjectModal.types';
import { useDeleteProject } from './useDeleteProject';

export const DeleteProjectModal = ({ project, onCancel, onSuccess }: DeleteProjectModalProps) => {
  const { deleteProject, loading, projectDeleted } = useDeleteProject();

  const onDeleteProject = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    event.preventDefault();
    deleteProject(project.id);
  };

  useEffect(() => {
    if (projectDeleted) {
      onSuccess();
    }
  }, [projectDeleted, onSuccess]);

  return (
    <Dialog open onClose={onCancel} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
      <DialogTitle id="dialog-title">Delete the project "{project.name}"</DialogTitle>
      <DialogContent>
        <DialogContentText>
          This action will delete everything in the project. All data and all representations will be lost. It cannot be
          reversed.
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={loading}
          onClick={onDeleteProject}
          color="primary"
          data-testid="delete-project">
          Delete
        </Button>
      </DialogActions>
    </Dialog>
  );
};
