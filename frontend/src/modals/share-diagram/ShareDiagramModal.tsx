/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import Dialog from '@material-ui/core/Dialog';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import React from 'react';
import { ShareDiagramModalProps } from './ShareDiagramModal.types';

export const ShareDiagramModal = ({ url, onClose }: ShareDiagramModalProps) => {
  let message = 'Shareable link';
  if (navigator.clipboard && document.hasFocus()) {
    navigator.clipboard.writeText(url);
    message += ' (copied into the clipboard)';
  }

  const refCallback = (node: HTMLElement) => {
    if (node !== null) {
      var range = document.createRange();
      range.selectNodeContents(node);
      var selection = window.getSelection();
      selection.removeAllRanges();
      selection.addRange(range);
    }
  };

  return (
    <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" fullWidth>
      <DialogTitle id="dialog-title">{message}</DialogTitle>
      <DialogContent ref={refCallback}>
        <DialogContentText>{url}</DialogContentText>
      </DialogContent>
    </Dialog>
  );
};
