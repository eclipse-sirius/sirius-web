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

import React, { useState } from 'react';
import { ConfirmationDialog } from './ConfirmationDialog';
import {
  ConfirmationDialogContextProviderProps,
  ConfirmationDialogContextProviderState,
  ConfirmationDialogContextValue,
} from './ConfirmationDialogContext.types';

const defaultValue: ConfirmationDialogContextValue = {
  showConfirmation: () => {},
};

export const ConfirmationDialogContext = React.createContext<ConfirmationDialogContextValue>(defaultValue);

export const ConfirmationDialogContextProvider = ({
  children,
  canBeDisabled,
  isDisabled,
  setIsDisabled,
}: ConfirmationDialogContextProviderProps) => {
  const [state, setState] = useState<ConfirmationDialogContextProviderState>({
    open: false,
    allowConfirmationDisabled: canBeDisabled,
    confirmationDisabled: isDisabled,
    title: '',
    message: '',
    buttonLabel: '',
    onConfirm: () => {},
  });

  const showConfirmation = (title: string, message: string, buttonLabel: string, onConfirm: () => void) => {
    if (isDisabled) {
      onConfirm();
    } else {
      setState({
        open: true,
        allowConfirmationDisabled: canBeDisabled,
        confirmationDisabled: isDisabled,
        title,
        message,
        buttonLabel,
        onConfirm,
      });
    }
  };

  const handleConfirmationDisabledChange = (checked: boolean) => {
    setState((prevState) => ({ ...prevState, confirmationDisabled: checked }));
  };

  const handleConfirm = () => {
    state.onConfirm();
    if (canBeDisabled) {
      setIsDisabled(state.confirmationDisabled);
    }
    handleClose();
  };

  const handleClose = () => {
    setState((prevState) => ({ ...prevState, open: false }));
  };

  return (
    <ConfirmationDialogContext.Provider value={{ showConfirmation }}>
      {children}
      {state.open && (
        <ConfirmationDialog
          open={state.open}
          title={state.title}
          message={state.message}
          buttonLabel={state.buttonLabel}
          allowConfirmationDisabled={state.allowConfirmationDisabled}
          confirmationDisabled={state.confirmationDisabled}
          onConfirmationDisabledChange={handleConfirmationDisabledChange}
          onCancel={handleClose}
          onConfirm={handleConfirm}
        />
      )}
    </ConfirmationDialogContext.Provider>
  );
};
