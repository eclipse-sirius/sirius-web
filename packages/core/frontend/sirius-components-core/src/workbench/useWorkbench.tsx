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

import { useContext } from 'react';
import { UseWorkbenchValue } from './useWorkbench.types';
import { WorkbenchContext } from './WorkbenchContext';
import { WorkbenchContextValue } from './WorkbenchContext.types';

export const useWorkbench = (): UseWorkbenchValue => {
  const { displayedRepresentationMetadata, getWorkbenchPanelHandles, getDisplayedRepresentationHandle } =
    useContext<WorkbenchContextValue>(WorkbenchContext);

  return {
    displayedRepresentationMetadata,
    getDisplayedRepresentationHandle,
    getWorkbenchPanelHandles,
  };
};
