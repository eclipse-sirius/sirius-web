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

import { useEffect, useState } from 'react';
import { UseExpressionState, UseExpressionValue } from './useExpression.types';

export const useExpression = (editingContextId: string): UseExpressionValue => {
  const sessionStorageKey = `query_view_expression#${editingContextId}`;

  const defaultValue: string = isSessionStorageAvailable()
    ? window.sessionStorage.getItem(sessionStorageKey) ?? ''
    : '';
  const [state, setState] = useState<UseExpressionState>({
    expression: defaultValue,
  });

  useEffect(() => {
    if (isSessionStorageAvailable()) {
      window.sessionStorage.setItem(sessionStorageKey, state.expression);
    }
  }, [state.expression]);

  const onExpressionChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const {
      target: { value },
    } = event;
    setState((prevState) => ({ ...prevState, expression: value }));
  };

  return {
    expression: state.expression,
    onExpressionChange,
  };
};

const isSessionStorageAvailable = (): boolean => {
  let isSessionStorageAvailable: boolean = false;

  if (
    window.sessionStorage &&
    window.sessionStorage.setItem &&
    window.sessionStorage.getItem &&
    window.sessionStorage.removeItem
  ) {
    try {
      window.sessionStorage.setItem('session_storage_availability', 'value');
      window.sessionStorage.getItem('session_storage_availability');
      window.sessionStorage.removeItem('session_storage_availability');
      isSessionStorageAvailable = true;
    } catch (error) {
      isSessionStorageAvailable = false;
    }
  }

  return isSessionStorageAvailable;
};
