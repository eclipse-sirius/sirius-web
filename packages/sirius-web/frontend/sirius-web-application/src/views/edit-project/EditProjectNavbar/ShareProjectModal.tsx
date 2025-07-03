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

import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { ShareProjectModalProps } from './ShareProjectModal.types';

export const ShareProjectModal = ({ workbenchConfiguration, onClose }: ShareProjectModalProps) => {
  return (
    <Dialog open onClose={onClose} aria-labelledby="dialog-title" fullWidth>
      <DialogTitle>Share project</DialogTitle>
      <DialogContent>
        <DialogContentText>
          {workbenchConfiguration.panels
            .map((panel) => `${panel.id} - ${panel.views.map((view) => `${view.id} - ${view.active}`).join(', ')}`)
            .join(' - ')}
        </DialogContentText>
      </DialogContent>
    </Dialog>
  );
};
