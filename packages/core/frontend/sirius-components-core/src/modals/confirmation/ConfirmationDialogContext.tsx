/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
  ConfirmationDialogContextProviderState,
  ConfirmationDialogContextValue,
} from './ConfirmationDialogContext.types';

const defaultValue: ConfirmationDialogContextValue = {
  showConfirmation: () => {},
};

export const ConfirmationDialogContext = React.createContext<ConfirmationDialogContextValue>(defaultValue);

export const ConfirmationDialogContextProvider = ({ children }) => {
  const localStorageKey: string = 'sirius-confirmation-dialog-disabled';
  const storedPreference: string | null = localStorage.getItem(localStorageKey);
  const initialConfirmationDisabled: boolean = storedPreference ? JSON.parse(storedPreference) : false;

  const [state, setState] = useState<ConfirmationDialogContextProviderState>({
    open: false,
    confirmationDisabled: initialConfirmationDisabled,
    title: '',
    message: '',
    buttonLabel: '',
    onConfirm: () => {},
  });

  const showConfirmation = (title: string, message: string, buttonLabel: string, onConfirm: () => void) => {
    const storedPreference: string | null = localStorage.getItem(localStorageKey);
    const confirmationDisabled: boolean = storedPreference ? JSON.parse(storedPreference) : false;
    if (confirmationDisabled) {
      onConfirm();
    } else {
      setState({ open: true, confirmationDisabled, title, message, buttonLabel, onConfirm });
    }
  };

  const handleConfirmationDisabledChange = (checked: boolean) => {
    setState((prevState) => ({ ...prevState, confirmationDisabled: checked }));
  };

  const handleConfirm = () => {
    state.onConfirm();
    localStorage.setItem(localStorageKey, JSON.stringify(state.confirmationDisabled));
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
          confirmationDisabled={state.confirmationDisabled}
          onConfirmationDisabledChange={handleConfirmationDisabledChange}
          onCancel={handleClose}
          onConfirm={handleConfirm}
        />
      )}
    </ConfirmationDialogContext.Provider>
  );
};
