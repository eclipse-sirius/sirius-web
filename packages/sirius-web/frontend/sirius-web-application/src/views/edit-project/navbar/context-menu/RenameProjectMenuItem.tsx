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

import EditIcon from '@mui/icons-material/Edit';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { RenameProjectModal } from '../../../../modals/rename-project/RenameProjectModal';
import { RenameProjectMenuItemProps, RenameProjectMenuItemState } from './RenameProjectMenuItem.types';

export const RenameProjectMenuItem = ({ project, onCancel, onSuccess }: RenameProjectMenuItemProps) => {
  const [state, setState] = useState<RenameProjectMenuItemState>({
    showModal: false,
  });
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'renameProjectMenuItem' });
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
      <MenuItem onClick={() => setState((prevState) => ({ ...prevState, showModal: true }))} data-testid="rename">
        <ListItemIcon>
          <EditIcon />
        </ListItemIcon>
        <ListItemText primary={t('rename')} />
      </MenuItem>
      {state.showModal ? (
        <RenameProjectModal project={project} onCancel={handleCancel} onSuccess={handleSuccess} />
      ) : null}
    </>
  );
};
