/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { Edge, EdgeProps, Node, Position, useInternalNode } from '@xyflow/react';
import { memo, useContext } from 'react';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { NodeData } from '../DiagramRenderer.types';
import { resolveEdgeGeometryInput } from './geometry/resolveEdgeGeometry';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';

export const SmoothStepEdgeWrapper = memo((props: EdgeProps<Edge<MultiLabelEdgeData>>) => {
  const { source, target, sourceHandleId, targetHandleId, data } = props;
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const sourceNode = useInternalNode<Node<NodeData>>(source);
  const targetNode = useInternalNode<Node<NodeData>>(target);

  if (!sourceNode || !targetNode) {
    return null;
  }

  const input = resolveEdgeGeometryInput(
    { ...props, sourceHandle: sourceHandleId, targetHandle: targetHandleId },
    new Map([
      [source, sourceNode],
      [target, targetNode],
    ]),
    nodeLayoutHandlers
  );

  if (!input) {
    return null;
  }

  if (data && (input.customEdge || data.bendingPoints?.length)) {
    data.bendingPoints = input.customEdge ? input.bendingPoints : [];
  }

  const sourceHandle = sourceNode.data.connectionHandles.find((handle) => handle.id === sourceHandleId);
  const targetHandle = targetNode.data.connectionHandles.find((handle) => handle.id === targetHandleId);

  if (
    (input.sourcePosition === Position.Top || input.sourcePosition === Position.Bottom) &&
    input.source.x === input.target.x
  ) {
    if (sourceHandle) {
      sourceHandle.handleStyleTransform = `translateX(${input.sourceAlignmentOffset?.x ?? 0}px)`;
    }
    if (targetHandle) {
      targetHandle.handleStyleTransform = `translateX(${input.targetAlignmentOffset?.x ?? 0}px)`;
    }
  }

  if (
    (input.sourcePosition === Position.Left || input.sourcePosition === Position.Right) &&
    input.source.y === input.target.y
  ) {
    if (sourceHandle) {
      sourceHandle.handleStyleTransform = `translateY(${input.sourceAlignmentOffset?.y ?? 0}px)`;
    }
    if (targetHandle) {
      targetHandle.handleStyleTransform = `translateY(${input.targetAlignmentOffset?.y ?? 0}px)`;
    }
  }

  return (
    <MultiLabelRectilinearEditableEdge
      {...props}
      sourceX={input.source.x}
      sourceY={input.source.y}
      targetX={input.target.x}
      targetY={input.target.y}
      bendingPoints={input.customEdge && data?.bendingPoints ? data.bendingPoints : input.bendingPoints}
      customEdge={input.customEdge}
      sourceNode={sourceNode}
      targetNode={targetNode}
    />
  );
});
