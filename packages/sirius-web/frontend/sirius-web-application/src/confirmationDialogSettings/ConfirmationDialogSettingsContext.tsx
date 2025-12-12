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

import { ConfirmationDialogContextProvider } from '@eclipse-sirius/sirius-components-core';
import React, { useMemo } from 'react';
import { ConfirmationDialogSettingsContextValue } from './ConfirmationDialogSettingsContext.types';
import { useConfirmationDialogSettings } from './useConfirmationDialogSettings';

const defaultValue: ConfirmationDialogSettingsContextValue = {
  canBeDisabled: false,
  setIsDisabled: () => {},
  isDisabled: false,
};

export const ConfirmationDialogSettingsContext =
  React.createContext<ConfirmationDialogSettingsContextValue>(defaultValue);

export const ConfirmationDialogSettingsContextProvider = ({ children, projectId }) => {
  const { canBeDisabled, setIsDisabled, isDisabled } = useConfirmationDialogSettings(projectId);

  const contextValue = useMemo(
    () => ({ canBeDisabled, setIsDisabled, isDisabled }),
    [isDisabled, setIsDisabled, canBeDisabled]
  );

  return (
    <ConfirmationDialogSettingsContext.Provider value={contextValue}>
      <ConfirmationDialogContextProvider
        canBeDisabled={canBeDisabled}
        isDisabled={isDisabled}
        setIsDisabled={setIsDisabled}>
        {children}
      </ConfirmationDialogContextProvider>
    </ConfirmationDialogSettingsContext.Provider>
  );
};
