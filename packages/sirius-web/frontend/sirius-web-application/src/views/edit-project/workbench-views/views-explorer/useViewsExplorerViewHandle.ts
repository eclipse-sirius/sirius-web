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

import { Selection, WorkbenchViewHandle } from '@eclipse-sirius/sirius-components-core';
import { ForwardedRef, useImperativeHandle } from 'react';

export const useViewsExplorerViewHandle = (
  id: string,
  treeId: string | null,
  applySelection: (selection: Selection) => void,
  ref: ForwardedRef<WorkbenchViewHandle>
) => {
  const viewsExplorerViewHandleProvider = (): WorkbenchViewHandle => {
    return {
      id,
      getWorkbenchViewConfiguration: () => ({}),
      applySelection,
    };
  };
  useImperativeHandle(ref, viewsExplorerViewHandleProvider, [id, treeId]);
};
