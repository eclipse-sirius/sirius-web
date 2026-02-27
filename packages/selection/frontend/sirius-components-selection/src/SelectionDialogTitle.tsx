/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import CloseIcon from '@mui/icons-material/Close';
import DialogTitle from '@mui/material/DialogTitle';
import IconButton from '@mui/material/IconButton';
import { SelectionDialogTitleProps } from './SelectionDialogTitle.types';
import { useSelectionDialog } from './useSelectionDialog';

export const SelectionDialogTitle = ({ onClose }: SelectionDialogTitleProps) => {
  const {
    selectionDialogDescription: { titles },
    selectionOptionSelected,
    noSelectionOptionSelected,
  } = useSelectionDialog();

  let title = titles.defaultTitle;
  if (noSelectionOptionSelected) {
    title = titles.noSelectionTitle;
  } else if (selectionOptionSelected) {
    title = titles.selectionTitle;
  }

  return (
    <DialogTitle
      id="selection-dialog-title"
      component="div"
      sx={() => ({ display: 'flex', alignItems: 'center', justifyContent: 'space-between' })}>
      {title}
      <IconButton onClick={onClose} aria-label="close" size="small">
        <CloseIcon />
      </IconButton>
    </DialogTitle>
  );
};
