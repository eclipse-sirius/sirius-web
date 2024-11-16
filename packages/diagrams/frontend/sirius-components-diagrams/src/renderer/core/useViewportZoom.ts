/*******************************************************************************
 * Copyright (c) 2024 Obeo and others.
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

import type { ReactFlowState } from '@xyflow/react';
import { useStore } from '@xyflow/react';
import { shallow } from 'zustand/shallow';

const viewportZoomSelector = (state: ReactFlowState) => state.transform[2];

export function useViewportZoom(): number {
  return useStore(viewportZoomSelector, shallow);
}
