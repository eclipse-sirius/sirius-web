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
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { useParams } from 'react-router-dom';
import { ConfirmationSettingsContextValue, ConfirmationSettingsParams } from './ConfirmationSettingsContext.types';

const LOCAL_STORAGE_KEY = 'sirius-confirmation-dialog-disabled';

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

const getDisabledProjectIds = (): string[] => {
  try {
    const storedPreference = localStorage.getItem(LOCAL_STORAGE_KEY);
    if (!storedPreference) {
      return [];
    }
    const parsed = JSON.parse(storedPreference);
    return Array.isArray(parsed) ? parsed : [];
  } catch (_) {
    return [];
  }
};

const defaultValue: ConfirmationSettingsContextValue = {
  canBeDisabled: false,
  setIsDisabled: () => {},
  isDisabled: null,
};

export const ConfirmationSettingsContext = React.createContext<ConfirmationSettingsContextValue>(defaultValue);

export const ConfirmationSettingsContextProvider = ({ children }) => {
  const { projectId } = useParams<ConfirmationSettingsParams>();
  const canBeDisabled = isLocalStorageAvailable();

  const getInitialValue = useCallback((): boolean => {
    if (canBeDisabled && projectId) {
      const disabledIds = getDisabledProjectIds();
      return disabledIds.includes(projectId);
    }
    return false;
  }, [canBeDisabled, projectId]);

  const [isDisabled, setIsDisabled] = useState<boolean>(getInitialValue);

  useEffect(() => {
    setIsDisabled(getInitialValue());
  }, [getInitialValue]);

  const safeSetIsDisabled = useCallback(
    (newValue: boolean) => {
      if (canBeDisabled && projectId) {
        const disabledIds = getDisabledProjectIds();
        let updatedIds: string[];

        if (newValue) {
          updatedIds = disabledIds.includes(projectId) ? disabledIds : [...disabledIds, projectId];
        } else {
          updatedIds = disabledIds.filter((id: string) => id !== projectId);
        }

        localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(updatedIds));
        setIsDisabled(newValue);
      }
    },
    [canBeDisabled, projectId]
  );

  const contextValue = useMemo(
    () => ({ canBeDisabled, setIsDisabled: safeSetIsDisabled, isDisabled }),
    [isDisabled, safeSetIsDisabled, canBeDisabled]
  );

  return (
    <ConfirmationSettingsContext.Provider value={contextValue}>
      <ConfirmationDialogContextProvider
        canBeDisabled={canBeDisabled}
        isDisabled={isDisabled}
        setIsDisabled={safeSetIsDisabled}>
        {children}
      </ConfirmationDialogContextProvider>
    </ConfirmationSettingsContext.Provider>
  );
};
