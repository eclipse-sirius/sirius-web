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

import LibraryAddIcon from '@mui/icons-material/LibraryAdd';
import CircularProgress from '@mui/material/CircularProgress';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { DuplicateProjectMenuItemProps } from './DuplicateProjectMenuItem.types';
import { useDuplicateProject } from './useDuplicateProject';

export const DuplicateProjectMenuItem = ({ projectId }: DuplicateProjectMenuItemProps) => {
  const { duplicateProject, loading, newProjectId } = useDuplicateProject();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'duplicateProjectMenuItem' });

  useEffect(() => {
    if (newProjectId) {
      const origin = window.location.origin;
      window.location.assign(`${origin}/projects/${newProjectId}/edit`);
    }
  }, [newProjectId]);

  const handleOnClick = () => {
    duplicateProject(projectId);
  };

  return (
    <MenuItem onClick={handleOnClick} disabled={loading} data-testid="duplicate">
      <ListItemIcon>{loading ? <CircularProgress size={15} /> : <LibraryAddIcon />}</ListItemIcon>
      <ListItemText primary={t('duplicate')} />
    </MenuItem>
  );
};
