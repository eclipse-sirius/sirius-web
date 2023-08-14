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

import { ToastContext } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import { SnackbarProvider, useSnackbar } from 'notistack';
import React from 'react';
import { ToastCloseButtonProps, ToastContextInitializerProps, ToastProviderProps } from './ToastProvider.types';

const ToastCloseButton = ({ toastKey }: ToastCloseButtonProps) => {
  const { closeSnackbar } = useSnackbar();

  return (
    <IconButton size="small" aria-label="close" color="inherit" onClick={() => closeSnackbar(toastKey)}>
      <CloseIcon fontSize="small" />
    </IconButton>
  );
};

const ToastContextInitializer = ({ children }: ToastContextInitializerProps) => {
  const { enqueueSnackbar } = useSnackbar();

  return (
    <ToastContext.Provider
      value={{
        enqueueSnackbar,
      }}>
      {children}
    </ToastContext.Provider>
  );
};

export const ToastProvider = ({ children }: ToastProviderProps) => {
  return (
    <SnackbarProvider
      maxSnack={5}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      action={(key) => <ToastCloseButton toastKey={key} />}
      autoHideDuration={10000}
      data-testid="toast">
      <ToastContextInitializer>{children}</ToastContextInitializer>
    </SnackbarProvider>
  );
};
