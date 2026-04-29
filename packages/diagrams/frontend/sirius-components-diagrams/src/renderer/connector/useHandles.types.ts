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

import { XYPosition } from '@xyflow/react';

export interface UseHandlesValue {
  mountNodeHandles: (nodeId: string, nodeXYPosition: XYPosition, nodeWidth: number, nodeHeight: number) => void;
  updateNodeHandles: (nodeId: string, nodeXYPosition: XYPosition, nodeWidth: number, nodeHeight: number) => void;
  mountEdgeHandles: (edgeId: string, edgePath: string) => void;
  unMountHandles: () => void;
}
