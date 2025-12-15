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

import { useCallback, useState } from 'react';
import { UseConfirmationDialogSettingsValue } from './useConfirmationDialogSettings.types';

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

export const useConfirmationDialogSettings = (projectId: string): UseConfirmationDialogSettingsValue => {
  const canBeDisabled = isLocalStorageAvailable();

  const [isDisabled, setIsDisabled] = useState<boolean>(getDisabledProjectIds().includes(projectId));

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

  return { canBeDisabled, setIsDisabled: safeSetIsDisabled, isDisabled };
};
