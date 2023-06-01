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
import { useContext } from 'react';
import { ToastContext } from '../contexts/ToastContext';

const getVariantFromMessageLevel = (level: string): 'default' | 'error' | 'success' | 'warning' | 'info' => {
  switch (level) {
    case 'ERROR':
      return 'error';
    case 'INFO':
      return 'info';
    case 'WARNING':
      return 'warning';
    case 'SUCCESS':
      return 'success';
    default:
      return 'default';
  }
};

export const useMultiToast = () => {
  const { enqueueSnackbar } = useContext(ToastContext).useToast();

  const addMessages = (messages) =>
    messages.map((message) => enqueueSnackbar(message.body, { variant: getVariantFromMessageLevel(message.level) }));

  const addErrorMessage = (message: string) => addMessages([{ body: message, level: 'error' }]);

  return {
    addErrorMessage,
    addMessages,
  };
};
