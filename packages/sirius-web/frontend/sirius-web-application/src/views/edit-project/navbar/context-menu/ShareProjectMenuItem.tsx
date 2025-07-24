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

import ShareIcon from '@mui/icons-material/Share';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useState } from 'react';
import { ShareProjectMenuItemProps, ShareProjectMenuItemState } from './ShareProjectMenuItem.types';
import { ShareProjectModal } from './ShareProjectModal';

export const ShareProjectMenuItem = ({ projectId, workbenchHandle }: ShareProjectMenuItemProps) => {
  const [state, setState] = useState<ShareProjectMenuItemState>({
    isOpen: false,
  });

  return (
    <>
      <MenuItem onClick={() => setState((prevState) => ({ ...prevState, isOpen: true }))}>
        <ListItemIcon>
          <ShareIcon />
        </ListItemIcon>
        <ListItemText primary="Share" />
      </MenuItem>
      {state.isOpen ? (
        <ShareProjectModal
          projectId={projectId}
          workbenchConfiguration={workbenchHandle.getConfiguration()}
          onClose={() => setState((prevState) => ({ ...prevState, isOpen: false }))}
        />
      ) : null}
    </>
  );
};
