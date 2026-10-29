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

import { Position, XYPosition } from '@xyflow/react';
import { BendPointData } from '../rectilinear-edge/BendingPointsCalculation.types';
import { EdgeGeometryInput } from './edgeGeometry.types';

const copyPoints = (points: XYPosition[]): BendPointData[] => {
  return points.map((point, index) => {
    return { ...point, pathOrder: index };
  });
};

/** Resolves terminal bend coordinates without constructing drawing-only geometry. */
export const getBendingPoints = (input: EdgeGeometryInput): BendPointData[] => {
  const points = copyPoints(input.bendingPoints);
  if (input.customEdge) {
    const firstPoint = points[0];
    if (firstPoint) {
      if (input.sourcePosition === Position.Left || input.sourcePosition === Position.Right) {
        firstPoint.y = input.source.y;
      } else {
        firstPoint.x = input.source.x;
      }
    }
    const lastPoint = points[points.length - 1];
    if (lastPoint) {
      if (input.targetPosition === Position.Left || input.targetPosition === Position.Right) {
        lastPoint.y = input.target.y;
      } else {
        lastPoint.x = input.target.x;
      }
    }
  }
  return points;
};
