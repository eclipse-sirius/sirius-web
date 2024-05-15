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
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { RenameProjectModalProps, RenameProjectModalState } from './RenameProjectModal.types';
import { useRenameProject } from './useRenameProject';

export const RenameProjectModal = ({ project, onCancel, onSuccess }: RenameProjectModalProps) => {
  const { t } = useTranslation('siriusWebApplication', { keyPrefix: 'project.rename' });

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
      <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
      <DialogContent>
        <TextField
          variant="standard"
          value={state.newName}
          onChange={onNewName}
          error={nameIsInvalid}
          helperText={t('name.helperText')}
          label={t('name.label')}
          placeholder={t('name.placeholder')}
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
          {t('submit')}
        </Button>
      </DialogActions>
    </Dialog>
  );
};
