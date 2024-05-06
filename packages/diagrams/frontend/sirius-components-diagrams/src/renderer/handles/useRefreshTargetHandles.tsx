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

import { useEffect } from 'react';
import { useUpdateNodeInternals } from 'reactflow';
export const useRefreshTargetHandles = (id: string, shouldRefresh: boolean) => {
  const updateNodeInternals = useUpdateNodeInternals();
  useEffect(() => {
    if (shouldRefresh) {
      updateNodeInternals(id);
    }
  }, [shouldRefresh]);
};
