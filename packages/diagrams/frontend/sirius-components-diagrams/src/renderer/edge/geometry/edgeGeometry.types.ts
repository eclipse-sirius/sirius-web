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

import { Edge, InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { BendPointData } from '../rectilinear-edge/BendingPointsCalculation.types';
import { MiddlePoint } from '../rectilinear-edge/TemporaryLinesCalculation.types';

export interface EdgeGeometryInput {
  edgeId: string;
  source: XYPosition;
  target: XYPosition;
  sourcePosition: Position;
  targetPosition: Position;
  sourceNode: InternalNode<Node<NodeData>>;
  targetNode: InternalNode<Node<NodeData>>;
  bendingPoints: XYPosition[];
  customEdge: boolean;
  sourceAlignmentOffset?: XYPosition;
  targetAlignmentOffset?: XYPosition;
}

export interface EdgeGeometry {
  isDragging: boolean;
  source: XYPosition;
  target: XYPosition;
  bendingPoints: BendPointData[];
  middlePoints: MiddlePoint[];
  path: string;
  center: {
    position: XYPosition;
    segmentDirection: 'x' | 'y' | null;
  };
  customEdge: boolean;
}

export interface EdgeDragControl {
  isSegmentDragged: boolean;
  index: number;
  direction: 'x' | 'y';
}

export interface EdgeDragData extends EdgeDragControl {
  edgeId: string;
  originalGeometry: EdgeGeometryInput;
  geometry: EdgeGeometry;
  isSourceSegment: boolean;
  isTargetSegment: boolean;
  middleBendingPoints: MiddlePoint[];
}

export interface RenderedDiagram {
  nodes: Node<NodeData>[];
  edges: Edge<EdgeData>[];
}
