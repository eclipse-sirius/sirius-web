/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { Position } from '@xyflow/react';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLNode, GQLNodeStyle, GQLViewModifier } from '../graphql/subscription/nodeFragment.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';

export const convertHandles = (gqlNode: GQLNode<GQLNodeStyle>, gqlEdges: GQLEdge[]): ConnectionHandle[] => {
  return convertHandle(gqlNode.id, gqlEdges);
};

export const convertEdgeAnchorNodeHandles = (id: string, gqlEdges: GQLEdge[]): ConnectionHandle[] => {
  return convertHandle(id, gqlEdges);
};

export const convertHandle = (id: string, gqlEdges: GQLEdge[]): ConnectionHandle[] => {
  const connectionHandles: ConnectionHandle[] = [];
  let sourceHandlesCounter = 0;
  let targetHandlesCounter = 0;
  gqlEdges.forEach((edge) => {
    if (edge.sourceId === id) {
      connectionHandles.push({
        id: `handle--source--${id}--${sourceHandlesCounter}`,
        index: 0,
        nodeId: id,
        position: Position.Right,
        type: 'source',
        hidden: edge.state === GQLViewModifier.Hidden,
      });
      sourceHandlesCounter += 1;
    }

    if (edge.targetId === id) {
      connectionHandles.push({
        id: `handle--target--${id}--${targetHandlesCounter}`,
        index: 0,
        nodeId: id,
        position: Position.Left,
        type: 'target',
        hidden: edge.state === GQLViewModifier.Hidden,
      });
      targetHandlesCounter += 1;
    }
  });

  connectionHandles.push({
    id: `handle--source--${id}--${sourceHandlesCounter}`,
    index: 0,
    nodeId: id,
    position: Position.Right,
    type: 'source',
    hidden: true,
  });

  connectionHandles.push({
    id: `handle--target--${id}--${targetHandlesCounter}`,
    index: 0,
    nodeId: id,
    position: Position.Left,
    type: 'target',
    hidden: true,
  });

  return connectionHandles;
};
