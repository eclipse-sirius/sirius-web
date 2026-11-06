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
import { InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { NodeData } from '../../DiagramRenderer.types';

export type BendPointData = {
  x: number;
  y: number;
  pathOrder: number;
};

export interface BendingPointsCalculationInput {
  originalGeometry: {
    source: XYPosition;
    target: XYPosition;
    bendingPoints: XYPosition[];
    sourceNode: InternalNode<Node<NodeData>>;
    targetNode: InternalNode<Node<NodeData>>;
    sourcePosition: Position;
    targetPosition: Position;
  };
  index: number;
  direction: 'x' | 'y';
  isSourceSegment: boolean;
  isTargetSegment: boolean;
}

export interface BendingPointsCalculationValue {
  source: XYPosition;
  target: XYPosition;
  localBendingPoints: BendPointData[];
  isSourceSegment: boolean;
  isTargetSegment: boolean;
}
