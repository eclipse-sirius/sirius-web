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
import DialogTitle from '@material-ui/core/DialogTitle';
import TextField from '@material-ui/core/TextField';
import { useEffect, useState } from 'react';
import { RenameProjectModalProps, RenameProjectModalState } from './RenameProjectModal.types';
import { useRenameProject } from './useRenameProject';

export const RenameProjectModal = ({ project, onCancel, onSuccess }: RenameProjectModalProps) => {
  const [state, setState] = useState<RenameProjectModalState>({
    newName: project.name,
    pristine: true,
  });

  const onNewName = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newName = event.target.value;
    setState((prevState) => ({ ...prevState, newName, pristine: false }));
  };

  const { renameProject, loading, projectRenamed } = useRenameProject();

  const onRenameProject = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    event.preventDefault();
    renameProject(project.id, state.newName);
  };

  useEffect(() => {
    if (projectRenamed) {
      onSuccess();
    }
  }, [projectRenamed, onSuccess]);

  const nameIsInvalid: boolean =
    !state.pristine && (state.newName.trim().length === 0 || state.newName.trim().length > 1024);

  return (
    <Dialog open onClose={onCancel} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
      <DialogTitle id="dialog-title">Rename the project</DialogTitle>
      <DialogContent>
        <TextField
          value={state.newName}
          onChange={onNewName}
          error={nameIsInvalid}
          helperText="The name is required and must contain less than 1024 characters"
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
          disabled={loading}
          onClick={onRenameProject}
          color="primary"
          data-testid="rename-project">
          Rename
        </Button>
      </DialogActions>
    </Dialog>
  );
};
