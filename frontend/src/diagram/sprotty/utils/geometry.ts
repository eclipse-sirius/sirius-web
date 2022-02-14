/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { Bounds, Point } from 'sprotty-protocol/';
import { RectangleSide, SnapPointToRectangleInfo, SnapToRectangleInfo } from './geometry.types';

/**
 * Returns the distance between the point p and the segment made of p1 and p2
 * @param p the point
 * @param p1 the first point of the segment
 * @param p2 the second point of the segment
 */
export const closestPointOnSegment = (p: Point, p1: Point, p2: Point): Point => {
  const p1pVectordx = p.x - p1.x;
  const p1pVectordy = p.y - p1.y;
  const p2p1Vectordx = p2.x - p1.x;
  const p2p1Vectordy = p2.y - p1.y;

  const dot = p1pVectordx * p2p1Vectordx + p1pVectordy * p2p1Vectordy;
  const lengthSquare = p2p1Vectordx * p2p1Vectordx + p2p1Vectordy * p2p1Vectordy;
  // param is the projection distance from p1 on the segment. This is the fraction of the segment that p is closest to.
  let param = -1;
  if (lengthSquare != 0) {
    //if the line has 0 length
    param = dot / lengthSquare;
  }

  let nearestPoint: Point = p1;

  if (param < 0) {
    // The point is not "in front of" the segment. It is "away" on the p1 side
    nearestPoint = { x: p1.x, y: p1.y };
  } else if (param > 1) {
    // The point is not "in front of" the segment. It is "away" on the p1 side
    nearestPoint = { x: p2.x, y: p2.y };
  } else {
    // The point is "in front of" the segment
    // get the projection of p on the segment
    nearestPoint = { x: p1.x + param * p2p1Vectordx, y: p1.y + param * p2p1Vectordy };
  }

  return nearestPoint;
};

/**
 * Returns a point on the rectangle that is the closest from the given p point.
 */
export const snapToRectangle = (point: Point, rectangle: Bounds): SnapToRectangleInfo => {
  const upperLeftCorner: Point = { x: rectangle.x, y: rectangle.y };
  const upperRightCorner: Point = { x: rectangle.x + rectangle.width, y: rectangle.y };
  const bottomLeftCorner: Point = { x: rectangle.x, y: rectangle.y + rectangle.height };
  const bottomRightCorner: Point = { x: rectangle.x + rectangle.width, y: rectangle.y + rectangle.height };

  let closestPointInfo = computeClosestPoint(null, point, upperLeftCorner, upperRightCorner, RectangleSide.north);
  closestPointInfo = computeClosestPoint(
    closestPointInfo,
    point,
    upperRightCorner,
    bottomRightCorner,
    RectangleSide.east
  );
  closestPointInfo = computeClosestPoint(
    closestPointInfo,
    point,
    bottomRightCorner,
    bottomLeftCorner,
    RectangleSide.south
  );
  closestPointInfo = computeClosestPoint(
    closestPointInfo,
    point,
    bottomLeftCorner,
    upperLeftCorner,
    RectangleSide.west
  );

  return { pointOnRectangleSide: closestPointInfo.pointOnSegment, side: closestPointInfo.side };
};

const computeClosestPoint = (
  currentClosestPointInfo: SnapPointToRectangleInfo,
  p: Point,
  p1: Point,
  p2: Point,
  side: RectangleSide
): SnapPointToRectangleInfo => {
  const pointOnSegment: Point = closestPointOnSegment(p, p1, p2);
  let distanceSquare = getDistanceSquare(p, pointOnSegment);

  let updateTheClosestSide = false;
  if (currentClosestPointInfo === null) {
    updateTheClosestSide = true;
  } else if (distanceSquare <= currentClosestPointInfo.distanceSquare) {
    if (currentClosestPointInfo.distanceSquare === distanceSquare) {
      // Manage the case when p is outside the rectangle in a corner to get the best side
      if (side === RectangleSide.north || side === RectangleSide.south) {
        updateTheClosestSide = Math.abs(p.y - pointOnSegment.y) > Math.abs(p.x - pointOnSegment.x);
      } else if (side === RectangleSide.west || side === RectangleSide.east) {
        updateTheClosestSide = Math.abs(p.y - pointOnSegment.y) < Math.abs(p.x - pointOnSegment.x);
      }
    } else {
      updateTheClosestSide = true;
    }
  }

  if (updateTheClosestSide) {
    return { pointOnSegment, distanceSquare, side };
  } else {
    return currentClosestPointInfo;
  }
};

const getDistanceSquare = (p1: Point, p2: Point): number => {
  var dx = p1.x - p2.x;
  var dy = p1.y - p2.y;
  return dx * dx + dy * dy;
};
