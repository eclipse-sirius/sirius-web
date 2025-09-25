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
import { TreeFilter } from '@eclipse-sirius/sirius-components-trees';
import { ForwardedRef, useImperativeHandle } from 'react';

export const useExplorerViewHandle = (
  id: string,
  treeFilters: TreeFilter[],
  activeTreeDescriptionId: string | null,
  applySelection: (selection: Selection) => void,
  ref: ForwardedRef<WorkbenchViewHandle>
) => {
  const workbenchViewHandleProvider = (): WorkbenchViewHandle => {
    return {
      id,
      getWorkbenchViewConfiguration: () => {
        return {
          activeTreeFilters: treeFilters,
          activeTreeDescriptionId,
        };
      },
      applySelection,
    };
  };
  useImperativeHandle(ref, workbenchViewHandleProvider, [id, treeFilters, activeTreeDescriptionId]);
};
