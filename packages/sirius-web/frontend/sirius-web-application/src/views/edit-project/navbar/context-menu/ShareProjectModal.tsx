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

import {
  RepresentationPathContext,
  RepresentationPathContextValue,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useContext } from 'react';
import { generatePath } from 'react-router-dom';
import { selectionToSearchParamsValue } from '../../SelectionSynchronizer';
import { ShareProjectModalProps } from './ShareProjectModal.types';

export const ShareProjectModal = ({ projectId, workbenchConfiguration, onClose }: ShareProjectModalProps) => {
  const refCallback = (node: HTMLElement) => {
    if (node !== null) {
      var range = document.createRange();
      range.selectNodeContents(node);
      var selection = window.getSelection();
      if (selection) {
        selection.removeAllRanges();
        selection.addRange(range);
      }
    }
  };

  const { selection } = useSelection();
  const searchParams: URLSearchParams = new URLSearchParams({
    workbenchConfiguration: JSON.stringify(workbenchConfiguration),
    selection: selectionToSearchParamsValue(selection),
  });

  const representationId: string | null =
    workbenchConfiguration.mainPanel?.representationEditors.find((configuration) => configuration.isActive)
      ?.representationMetadata.id ?? null;

  let path: string;
  if (representationId) {
    const { getRepresentationPath } = useContext<RepresentationPathContextValue>(RepresentationPathContext);
    path = window.location.origin + getRepresentationPath(representationId) + '?' + searchParams.toString();
  } else {
    path =
      generatePath(':origin/projects/:projectId/edit', { origin: window.location.origin, projectId: projectId }) +
      '?' +
      searchParams.toString();
  }

  let title = 'Shareable link';
  if (navigator.clipboard && document.hasFocus()) {
    navigator.clipboard.writeText(path);
    title += ' (copied into the clipboard)';
  }

  return (
    <Dialog open onClose={onClose} aria-labelledby="dialog-title" fullWidth>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent ref={refCallback}>
        <DialogContentText>{path}</DialogContentText>
      </DialogContent>
    </Dialog>
  );
};
