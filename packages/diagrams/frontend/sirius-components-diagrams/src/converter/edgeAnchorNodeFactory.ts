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

import { HandleType, Node } from '@xyflow/react';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { NodeData } from '../renderer/DiagramRenderer.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { convertHandles } from './convertHandles';

export const createEdgeAnchorNode = (edge: GQLEdge, type: HandleType, gqlEdges: GQLEdge[]): Node<NodeData> => {
  const id = type === 'source' ? edge.sourceId : edge.targetId;
  const connectionHandles: ConnectionHandle[] = convertHandles(id, gqlEdges, []);

  return {
    type: 'edgeAnchorNode',
    id,
    position: {
      x: 0,
      y: 0,
    },
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
      style: {},
      connectionHandles,
      isNew: false,
      resizedByUser: false,
      isListChild: false,
      isDropNodeTarget: false,
      isDropNodeCandidate: false,
      isHovered: false,
    },
  };
};
