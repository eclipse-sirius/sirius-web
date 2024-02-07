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

import { useContext } from 'react';
import { ConfirmationDialogContext } from './ConfirmationDialogContext';
import { UseConfirmationDialogValue, UseDeletionConfirmationDialogValue } from './useConfirmationDialog.types';

export const useConfirmationDialog = (): UseConfirmationDialogValue => {
  const { showConfirmation } = useContext<UseConfirmationDialogValue>(ConfirmationDialogContext);
  return { showConfirmation };
};

export const useDeletionConfirmationDialog = (): UseDeletionConfirmationDialogValue => {
  const { showConfirmation } = useConfirmationDialog();
  return {
    showDeletionConfirmation: (onConfirm: () => void) => {
      showConfirmation(
        'Are you sure?',
        "This action will delete the selected element(s).\nYou won't be able to revert this.",
        'Delete',
        onConfirm
      );
    },
  };
};
