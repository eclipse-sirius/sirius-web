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
import { GQLNodeLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLPosition } from '../graphql/subscription/nodeFragment.types';
import { EdgeData, NodeData } from '../renderer/DiagramRenderer.types';

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
  nodeLayoutData: GQLNodeLayoutData[]
): Node<NodeData> => {
  const id = type === 'source' ? edge.sourceId : edge.targetId;

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
      edgeId: edge.id,
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
      isDragNodeSource: false,
      isDropNodeCandidate: false,
      isHovered: false,
      connectionLinePositionOnNode: 'none',
      minComputedWidth: null,
      minComputedHeight: null,
      isLastNodeSelected: false,
      moving: false,
      decorators: [],
    },
  };
};
