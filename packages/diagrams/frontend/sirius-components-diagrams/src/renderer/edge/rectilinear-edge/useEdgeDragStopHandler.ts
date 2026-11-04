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
import { useEdgeDragStopHandler as useGeometryDragStopHandler } from '../geometry/useEdgeDragStopHandler';
import { computeEdgeGeometry } from '../geometry/edgeGeometry';
import { UseEdgeDragStopHandlerValue } from './useEdgeDragStopHandler.types';

export const useEdgeDragStopHandler = (): UseEdgeDragStopHandlerValue => {
  const { handleDragStop: saveDrag } = useGeometryDragStopHandler();
  const handleDragStop = (
    edgeId: string,
    source: XYPosition,
    setSource: (position: XYPosition) => void,
    sourceNode: InternalNode<Node<NodeData>>,
    _sourceHandleId: string,
    sourcePosition: Position,
    target: XYPosition,
    setTarget: (position: XYPosition) => void,
    targetNode: InternalNode<Node<NodeData>>,
    _targetHandleId: string,
    targetPosition: Position,
    isSourceSegment: boolean,
    isTargetSegment: boolean,
    localBendingPoints: BendPointData[]
  ) => {
    const originalGeometry = {
      edgeId,
      source,
      target,
      sourceNode,
      targetNode,
      sourcePosition,
      targetPosition,
      bendingPoints: [...localBendingPoints].sort((left, right) => left.pathOrder - right.pathOrder),
      customEdge: false,
    };
    const updatedEndpoints = saveDrag({
      edgeId,
      originalGeometry,
      geometry: computeEdgeGeometry(originalGeometry),
      isSourceSegment,
      isTargetSegment,
    });
    if (updatedEndpoints) {
      setSource(updatedEndpoints.source);
      setTarget(updatedEndpoints.target);
    }
  };
  return { handleDragStop };
};
