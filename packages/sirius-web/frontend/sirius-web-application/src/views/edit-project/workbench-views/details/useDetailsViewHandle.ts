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

import { Selection, WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import { FormHandle } from '@eclipse-sirius/sirius-components-forms';
import { ForwardedRef, MutableRefObject, useImperativeHandle } from 'react';

export const useDetailsViewHandle = (
  id: string,
  formBasedViewRef: MutableRefObject<FormHandle | null>,
  applySelection: (selection: Selection) => void,
  ref: ForwardedRef<WorkbenchViewHandle>
) => {
  const detailsViewHandlerProvider = () => {
    return {
      id,
      getWorkbenchViewConfiguration: () => {
        return {
          selectedPageId: formBasedViewRef.current?.selectedPageId || null,
        };
      },
      applySelection,
    };
  };
  useImperativeHandle(ref, detailsViewHandlerProvider, [id, formBasedViewRef]);
};
