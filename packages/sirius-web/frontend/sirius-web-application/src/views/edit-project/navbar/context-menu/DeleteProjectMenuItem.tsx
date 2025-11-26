/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import DeleteIcon from '@mui/icons-material/Delete';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { DeleteProjectModal } from '../../../../modals/delete-project/DeleteProjectModal';
import { DeleteProjectMenuItemProps, DeleteProjectMenuItemState } from './DeleteProjectMenuItem.types';

export const DeleteProjectMenuItem = ({ project, onCancel, onSuccess }: DeleteProjectMenuItemProps) => {
  const [state, setState] = useState<DeleteProjectMenuItemState>({
    showModal: false,
  });
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'deleteProjectMenuItem' });
  const handleCancel = () => {
    setState((prevState) => ({ ...prevState, showModal: false }));
    onCancel();
  };

  const handleSuccess = () => {
    setState((prevState) => ({ ...prevState, showModal: false }));
    onSuccess();
  };

  return (
    <>
      <MenuItem onClick={() => setState({ showModal: true })} data-testid="delete">
        <ListItemIcon>
          <DeleteIcon />
        </ListItemIcon>
        <ListItemText primary={t('delete')} />
      </MenuItem>
      {state.showModal ? (
        <DeleteProjectModal project={project} onCancel={handleCancel} onSuccess={handleSuccess} />
      ) : null}
    </>
  );
};
