/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import { ToastProps } from './Toast.types';

export const Toast = ({ message, open, onClose }: ToastProps) => {
  return (
    <Snackbar
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      open={open}
      autoHideDuration={3000}
      onClose={onClose}
      message={message}
      action={
        <IconButton size="small" aria-label="close" color="inherit" onClick={onClose}>
          <CloseIcon fontSize="small" />
        </IconButton>
      }
      data-testid="toast"
    />
  );
};
