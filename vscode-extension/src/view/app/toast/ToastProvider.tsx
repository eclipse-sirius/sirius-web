/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import CloseIcon from '@mui/icons-material/Close';
import IconButton from '@mui/material/IconButton';
import { makeStyles } from 'tss-react/mui';
import { MaterialDesignContent, SnackbarProvider, useSnackbar } from 'notistack';
import React from 'react';
import { ToastCloseButtonProps, ToastContextInitializerProps, ToastProviderProps } from './ToastProvider.types';

const useStyles = makeStyles()((theme) => ({
  materialDesignContent: {
    '&.notistack-MuiContent-default': {
      backgroundColor: theme.palette.primary.main,
    },
    '&.notistack-MuiContent-success': {
      backgroundColor: theme.palette.success.main,
    },
    '&.notistack-MuiContent-error': {
      backgroundColor: theme.palette.error.main,
    },
    '&.notistack-MuiContent-warning': {
      backgroundColor: theme.palette.warning.main,
    },
    '&.notistack-MuiContent-info': {
      backgroundColor: theme.palette.info.main,
    },
  },
}));

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
  const { classes } = useStyles();

  return (
    <SnackbarProvider
      maxSnack={5}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
      action={(key) => <ToastCloseButton toastKey={key} />}
      autoHideDuration={10000}
      data-testid="toast"
      Components={{
        default: (props) => <MaterialDesignContent {...props} className={classes.materialDesignContent} />,
        success: (props) => <MaterialDesignContent {...props} className={classes.materialDesignContent} />,
        error: (props) => <MaterialDesignContent {...props} className={classes.materialDesignContent} />,
        warning: (props) => <MaterialDesignContent {...props} className={classes.materialDesignContent} />,
        info: (props) => <MaterialDesignContent {...props} className={classes.materialDesignContent} />,
      }}>
      <ToastContextInitializer>{children}</ToastContextInitializer>
    </SnackbarProvider>
  );
};
