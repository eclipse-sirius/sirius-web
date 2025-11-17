/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { Edge, Node, useReactFlow, XYPosition } from '@xyflow/react';
import { useEffect } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  EDGE_ANCHOR_NODE_DEFAULT_SIZE,
  EdgeAnchorNodeCreationHandlesData,
} from '../node/EdgeAnchorNodeCreationHandles.types';
import { EdgeCreationHandleProps } from './EdgeCreationHandle.types';

const createEdgeAnchorNodeCreationHandles = (
  edgeId: string,
  position: XYPosition
): Node<EdgeAnchorNodeCreationHandlesData> => {
  return {
    id: `edgeAnchorNodeCreationHandles-${edgeId}`,
    type: 'edgeAnchorNodeCreationHandles',
    position,
    data: {
      targetObjectId: '',
      targetObjectKind: '',
      targetObjectLabel: '',
      descriptionId: '',
      insideLabel: null,
      outsideLabels: {},
      faded: false,
      pinned: false,
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
      isDropNodeCandidate: false,
      connectionLinePositionOnNode: 'none',
      isHovered: false,
      edgeId: edgeId,
      minComputedWidth: null,
      minComputedHeight: null,
      isLastNodeSelected: false,
    },
  };
};

export const EdgeCreationHandle = ({ edgeId, edgePath }: EdgeCreationHandleProps) => {
  const { setNodes, getNode } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const currentEdgeAnchorNode = getNode(`edgeAnchorNodeCreationHandles-${edgeId}`);
  useEffect(() => {
    if (!currentEdgeAnchorNode) {
      var svgPath = document.createElementNS('http://www.w3.org/2000/svg', 'path');
      svgPath.setAttribute('d', edgePath);
      const pathLength = svgPath.getTotalLength();
      const points = svgPath.getPointAtLength(pathLength * 0.5);
      const offSet = EDGE_ANCHOR_NODE_DEFAULT_SIZE / 2;

      const nodePosition = { x: points.x - offSet, y: points.y - offSet };
      const node = createEdgeAnchorNodeCreationHandles(edgeId, nodePosition);

      setNodes((prevNodes) => prevNodes.concat(node));
    }
  }, [currentEdgeAnchorNode]);

  useEffect(() => {
    return () => {
      setNodes((prevNodes) => prevNodes.filter((node) => node.id !== `edgeAnchorNodeCreationHandles-${edgeId}`));
    };
  }, [setNodes]);

  return null;
};
