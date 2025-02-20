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

import { OmniboxCommandComponentProps } from '@eclipse-sirius/sirius-components-omnibox';
import { useState } from 'react';
import { PublishLibraryCommandState } from './PublishStudioLibraryCommand.types';
import { PublishLibraryDialog } from './PublishLibraryDialog';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';

export const PublishStudioLibraryCommand = ({ command, onKeyDown, onClose }: OmniboxCommandComponentProps) => {
  const [state, setState] = useState<PublishLibraryCommandState>({
    open: false,
  });

  const handleClick = () => setState((prevState) => ({ ...prevState, open: true }));

  return (
    <>
      <ListItemButton key={command.id} data-testid={command.label} onClick={handleClick} onKeyDown={onKeyDown}>
        <ListItemIcon>
          <IconOverlay iconURL={command.iconURLs} alt={command.label} />
        </ListItemIcon>
        <ListItemText sx={{ whiteSpace: 'nowrap', textOverflow: 'ellipsis' }}>{command.label}</ListItemText>
      </ListItemButton>
      {state.open && (
        <PublishLibraryDialog
          open={state.open}
          title={'Publish Studios'}
          message={'Publish all the domains and descriptions in the project as independent libraries'}
          publicationKind={'studio-all'}
          onClose={onClose}
        />
      )}
    </>
  );
};
