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

import { useContext } from 'react';
import { SelectionDialogDescriptionContext } from './SelectionDialogDescriptionContext';
import { SelectionDialogContextValue } from './SelectionDialogDescriptionContext.types';
import { UseSelectionDialogValue } from './useSelectionDialog.types';

export const useSelectionDialog = (): UseSelectionDialogValue => {
  const {
    selectionDialogDescription,
    noSelectionOptionSelected,
    selectionOptionSelected,
    treeDescriptionId,
    multiple,
    optional,
    updateSelectionOptions,
  } = useContext<SelectionDialogContextValue>(SelectionDialogDescriptionContext);

  return {
    selectionDialogDescription,
    noSelectionOptionSelected,
    selectionOptionSelected,
    treeDescriptionId,
    multiple,
    optional,
    updateSelectionOptions,
  };
};
