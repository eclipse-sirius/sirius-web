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

import { Edge, HandleType, Node } from '@xyflow/react';
import { GQLEdgeAnchorLayoutData, GQLNodeLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLPosition } from '../graphql/subscription/nodeFragment.types';
import { EdgeData } from '../renderer/DiagramRenderer.types';
import { EdgeAnchorNodeData } from '../renderer/node/EdgeAnchorNode.types';
import { convertEdgeAnchorNodeHandles } from './convertHandles';

export const getNodePositionAbsoluteFromSvgPathAndPositionRation = (
  edgePath: string,
  positionRatio: number,
  isLayouted: boolean
) => {
  //Get the point on the svgPath where the EdgeAnchorNode will be placed
  var svgPath = document.createElementNS('http://www.w3.org/2000/svg', 'path');
  svgPath.setAttribute('d', edgePath);
  const pathLength = svgPath.getTotalLength();
  let points = svgPath.getPointAtLength(pathLength * 0.5);
  if (isLayouted && positionRatio) {
    points = svgPath.getPointAtLength(pathLength * positionRatio);
  }

  return { x: points.x, y: points.y };
};

export const createEdgeAnchorNode = (
  edge: GQLEdge,
  existingEdge: Edge<EdgeData> | null,
  type: HandleType,
  nodeLayoutData: GQLNodeLayoutData[],
  edgeAnchorLayoutData: GQLEdgeAnchorLayoutData | undefined
): Node<EdgeAnchorNodeData> => {
  const id = edge.id;

  const connectionHandles = convertEdgeAnchorNodeHandles(edge, type, edgeAnchorLayoutData);

  let position: GQLPosition = nodeLayoutData.find((nodeLayoutData) => nodeLayoutData.id === id)?.position ?? {
    x: 0,
    y: 0,
  };

  if (existingEdge) {
    const edgePath = existingEdge.data?.edgePath || null;
    if (edgePath) {
      position = getNodePositionAbsoluteFromSvgPathAndPositionRation(edgePath, 0.5, true);
    }
  }

  return {
    type: 'edgeAnchorNode',
    id,
    position,
    height: 5, // The size is fixed for this type of node
    width: 5, // The size is fixed for this type of node
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
      connectionHandles: connectionHandles,
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
      decorators: [],
      sourceTargetEdgeId: type === 'target' ? edge.targetId : edge.sourceId,
      sourceTargetNodeId: type === 'target' ? edge.sourceId : edge.targetId,
      positionRatio: edgeAnchorLayoutData ? edgeAnchorLayoutData.positionRatio : 0.5,
      isLayouted: !!edgeAnchorLayoutData,
    },
  };
};
