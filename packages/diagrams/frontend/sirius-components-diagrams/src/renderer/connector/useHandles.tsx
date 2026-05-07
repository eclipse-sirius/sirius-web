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

import { Edge, Node, Position, useReactFlow, XYPosition } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getMiddleOfEdgePath, getSegmentDirection } from '../edge/EdgeLayout';
import { HandleNodeData, isHandleNode } from '../node/HandleNode.types';
import { UseHandlesValue } from './useHandles.types';

const HANDLE_OFFSET = 30;

export const getHandlePosition = (
  position: Position,
  xyPosition: XYPosition,
  width: number,
  height: number
): XYPosition => {
  if (position === Position.Bottom) {
    xyPosition = {
      x: xyPosition.x + width / 2,
      y: xyPosition.y + height + HANDLE_OFFSET,
    };
  } else if (position === Position.Top) {
    xyPosition = {
      x: xyPosition.x + width / 2,
      y: xyPosition.y - HANDLE_OFFSET,
    };
  } else if (position === Position.Left) {
    xyPosition = {
      x: xyPosition.x - HANDLE_OFFSET,
      y: xyPosition.y + height / 2,
    };
  } else if (position === Position.Right) {
    xyPosition = {
      x: xyPosition.x + width + HANDLE_OFFSET,
      y: xyPosition.y + height / 2,
    };
  }
  return xyPosition;
};

const createHandleNode = (
  nodeId: string | null,
  edgeId: string | null,
  position: Position,
  xyPosition: XYPosition,
  width: number,
  height: number
): Node<HandleNodeData> => {
  xyPosition = getHandlePosition(position, xyPosition, width, height);

  return {
    type: 'handleNode',
    id: `handleNode_${nodeId || edgeId}_${position}`,
    position: xyPosition,
    height: 1, // The size is fixed for this type of node
    width: 1, // The size is fixed for this type of node
    style: { zIndex: 3000 },
    data: {
      targetObjectId: '',
      targetObjectKind: '',
      targetObjectLabel: '',
      descriptionId: '',
      insideLabel: null,
      outsideLabels: {},
      faded: false,
      pinned: false,
      position,
      nodeDescription: {
        id: '',
        borderNodeDescriptionIds: [],
        childNodeDescriptionIds: [],
        userResizable: 'NONE',
        keepAspectRatio: false,
      },
      nodeAppearanceData: {
        customizedStyleProperties: [],
        gqlStyle: { __typename: '', childrenLayoutStrategy: { __typename: '', kind: '' } },
      },
      defaultWidth: null,
      defaultHeight: null,
      isBorderNode: false,
      borderNodePosition: null,
      labelEditable: false,
      deletable: false,
      style: {},
      connectionHandles: [],
      isNew: false,
      resizedByUser: false,
      movedByUser: false,
      isListChild: false,
      isDraggedNode: false,
      isDropNodeTarget: false,
      isDragNodeSource: false,
      isDropNodeCandidate: false,
      isHovered: false,
      connectionLinePositionOnNode: 'none',
      minComputedWidth: null,
      minComputedHeight: null,
      isLastNodeSelected: false,
      moving: false,
      nodeId,
      isSelected: false,
      decorators: [],
      edgeId,
    },
  };
};

export const useHandles = (): UseHandlesValue => {
  const { setNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const mountNodeHandles = (nodeId: string, nodeXYPosition: XYPosition, nodeWidth: number, nodeHeight: number) => {
    const handleNodes: Node<HandleNodeData>[] = Object.values(Position).map((position) =>
      createHandleNode(nodeId, null, position, nodeXYPosition, nodeWidth, nodeHeight)
    );
    setNodes((previousNodes) => previousNodes.concat(handleNodes));
  };

  const mountEdgeHandles = (edgeId: string, edgePath: string) => {
    const edgeCenterPosition = getMiddleOfEdgePath(edgePath);
    const edgeDirection = getSegmentDirection(edgePath, edgeCenterPosition);
    const positions: Position[] =
      edgeDirection === 'x' ? [Position.Bottom, Position.Top] : [Position.Right, Position.Left];

    const handleNodes: Node<HandleNodeData>[] = positions.map((position) =>
      createHandleNode(null, edgeId, position, edgeCenterPosition, 0, 0)
    );

    setNodes((previousNodes) => previousNodes.concat(handleNodes));
  };

  const updateNodeHandles = (nodeId: string, nodeXYPosition: XYPosition, nodeWidth: number, nodeHeight: number) => {
    setNodes((previousNodes) =>
      previousNodes.map((previousNode) => {
        if (isHandleNode(previousNode) && previousNode.data.nodeId === nodeId) {
          return {
            ...previousNode,
            position: getHandlePosition(previousNode.data.position, nodeXYPosition, nodeWidth, nodeHeight),
          };
        }

        return previousNode;
      })
    );
  };

  const unMountHandles = () => {
    setNodes((previousNodes) => previousNodes.filter((previousNode) => !previousNode.id.startsWith('handleNode_')));
  };

  return {
    mountNodeHandles,
    updateNodeHandles,
    mountEdgeHandles,
    unMountHandles,
  };
};
