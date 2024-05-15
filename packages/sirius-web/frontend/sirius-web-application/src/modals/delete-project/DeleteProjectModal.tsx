/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { DeleteProjectModalProps } from './DeleteProjectModal.types';
import { useDeleteProject } from './useDeleteProject';

export const DeleteProjectModal = ({ project, onCancel, onSuccess }: DeleteProjectModalProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'project.delete' });

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
      <DialogTitle id="dialog-title">{t('title', { name: project.name })}</DialogTitle>
      <DialogContent>
        <DialogContentText>{t('content')}</DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button
          variant="contained"
          disabled={loading}
          onClick={onDeleteProject}
          color="primary"
          data-testid="delete-project">
          {t('submit')}
        </Button>
      </DialogActions>
    </Dialog>
  );
};
