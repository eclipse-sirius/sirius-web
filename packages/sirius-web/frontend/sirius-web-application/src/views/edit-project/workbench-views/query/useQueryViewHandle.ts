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
import { WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import { ForwardedRef, RefObject, useImperativeHandle } from 'react';
import { ExpressionAreaHandle } from './QueryView.types';

export const useQueryViewHandle = (
  id: string,
  expressionAreaRef: RefObject<ExpressionAreaHandle | null>,
  ref: ForwardedRef<WorkbenchViewHandle>
) => {
  const workbenchViewHandleProvider = (): WorkbenchViewHandle => {
    return {
      id,
      getWorkbenchViewConfiguration: () => {
        return {
          queryText: expressionAreaRef.current?.getExpression() || '',
        };
      },
      applySelection: null,
    };
  };
  useImperativeHandle(ref, workbenchViewHandleProvider, [id, expressionAreaRef]);
};
