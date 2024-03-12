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

const isLocalStorageAvailable = (): boolean => {
  const test: string = 'localStorageTest';
  try {
    localStorage.setItem(test, test);
    localStorage.removeItem(test);
    return true;
  } catch (_) {
    return false;
  }
};

export const ConfirmationDialogContext = React.createContext<ConfirmationDialogContextValue>(defaultValue);

export const ConfirmationDialogContextProvider = ({ children }) => {
  const localStorageKey: string = 'sirius-confirmation-dialog-disabled';
  let allowConfirmationDisabled: boolean = false;
  let storedPreference: string | null = null;
  if (isLocalStorageAvailable()) {
    allowConfirmationDisabled = true;
    storedPreference = localStorage.getItem(localStorageKey);
  }
  const initialConfirmationDisabled: boolean = storedPreference ? JSON.parse(storedPreference) : false;

  const [state, setState] = useState<ConfirmationDialogContextProviderState>({
    open: false,
    allowConfirmationDisabled,
    confirmationDisabled: initialConfirmationDisabled,
    title: '',
    message: '',
    buttonLabel: '',
    onConfirm: () => {},
  });

  const showConfirmation = (title: string, message: string, buttonLabel: string, onConfirm: () => void) => {
    let allowConfirmationDisabled: boolean = false;
    let storedPreference: string | null = null;
    if (isLocalStorageAvailable()) {
      allowConfirmationDisabled = true;
      storedPreference = localStorage.getItem(localStorageKey);
    }
    const confirmationDisabled: boolean = storedPreference ? JSON.parse(storedPreference) : false;
    if (confirmationDisabled) {
      onConfirm();
    } else {
      setState({ open: true, allowConfirmationDisabled, confirmationDisabled, title, message, buttonLabel, onConfirm });
    }
  };

  const handleConfirmationDisabledChange = (checked: boolean) => {
    setState((prevState) => ({ ...prevState, confirmationDisabled: checked }));
  };

  const handleConfirm = () => {
    state.onConfirm();
    if (isLocalStorageAvailable()) {
      localStorage.setItem(localStorageKey, JSON.stringify(state.confirmationDisabled));
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
