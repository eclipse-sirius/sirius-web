/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { useContext } from 'react';
import { useTranslation } from 'react-i18next';
import { RepresentationPathContext } from '../../contexts/RepresentationPathContext';
import { RepresentationPathContextValue } from '../../contexts/RepresentationPathContext.types';
import { ShareRepresentationModalProps } from './ShareRepresentationModal.types';

export const ShareRepresentationModal = ({ representationId, onClose }: ShareRepresentationModalProps) => {
  const { t } = useTranslation('siriusComponentsCore', { keyPrefix: 'share' });

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

  const { getRepresentationPath } = useContext<RepresentationPathContextValue>(RepresentationPathContext);
  const path: string = window.location.origin + getRepresentationPath(representationId);

  let title = t('link');
  if (navigator.clipboard && document.hasFocus()) {
    navigator.clipboard.writeText(path);
    title += ' ' + t('copied');
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
