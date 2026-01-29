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

import { HandleType, Node } from '@xyflow/react';
import { GQLNodeLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLPosition } from '../graphql/subscription/nodeFragment.types';
import { NodeData } from '../renderer/DiagramRenderer.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { convertHandles } from './convertHandles';

export const createEdgeAnchorNode = (
  edge: GQLEdge,
  type: HandleType,
  gqlEdges: GQLEdge[],
  nodeLayoutData: GQLNodeLayoutData[]
): Node<NodeData> => {
  const id = type === 'source' ? edge.sourceId : edge.targetId;
  const connectionHandles: ConnectionHandle[] = convertHandles(id, gqlEdges, []);

  const position: GQLPosition = nodeLayoutData.find((nodeLayoutData) => nodeLayoutData.id === id)?.position ?? {
    x: 0,
    y: 0,
  };

  return {
    type: 'edgeAnchorNode',
    id,
    position,
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
      connectionHandles,
      isNew: false,
      resizedByUser: false,
      movedByUser: false,
      isListChild: false,
      isDraggedNode: false,
      isDropNodeTarget: false,
      isDropNodeCandidate: false,
      isHovered: false,
      connectionLinePositionOnNode: 'none',
      minComputedWidth: null,
      minComputedHeight: null,
      isLastNodeSelected: false,
    },
  };
};
