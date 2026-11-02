/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { BendPointData } from './BendingPointsCalculation.types';

export interface UseEdgeDragStopHandlerValue {
  handleDragStop: (
    edgeId: string,
    source: XYPosition,
    setSource: (position: XYPosition) => void,
    sourceNode: InternalNode<Node<NodeData>>,
    sourceHandleId: string,
    sourcePosition: Position,
    target: XYPosition,
    setTarget: (position: XYPosition) => void,
    targetNode: InternalNode<Node<NodeData>>,
    targetHandleId: string,
    targetPosition: Position,
    isSourceSegment: boolean,
    isTargetSegment: boolean,
    localBendingPoints: BendPointData[]
  ) => void;
}
