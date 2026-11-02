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
import { BendPointData } from '../rectilinear-edge/BendingPointsCalculation.types';
import { MiddlePoint } from '../rectilinear-edge/TemporaryLinesCalculation.types';

export interface EdgeGeometry {
  isDragging: boolean;
  source: XYPosition;
  target: XYPosition;
  bendingPoints: BendPointData[];
  middlePoints: MiddlePoint[];
  path: string;
  center: EdgeGeometryCenter;
  customEdge: boolean;
}

export interface EdgeGeometryCenter {
  position: XYPosition;
  segmentDirection: 'x' | 'y' | null;
}
