/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
  const connectionHandles: ConnectionHandle[] = [];
  let sourceHandlesCounter = 0;
  let targetHandlesCounter = 0;
  gqlEdges.forEach((edge) => {
    if (edge.sourceId === gqlNode.id) {
      connectionHandles.push({
        id: `handle--source--${gqlNode.id}--${sourceHandlesCounter}`,
        index: 0,
        nodeId: gqlNode.id,
        position: Position.Right,
        type: 'source',
        hidden: edge.state === GQLViewModifier.Hidden,
      });
      sourceHandlesCounter += 1;
    }

    if (edge.targetId === gqlNode.id) {
      connectionHandles.push({
        id: `handle--target--${gqlNode.id}--${targetHandlesCounter}`,
        index: 0,
        nodeId: gqlNode.id,
        position: Position.Left,
        type: 'target',
        hidden: edge.state === GQLViewModifier.Hidden,
      });
      targetHandlesCounter += 1;
    }
  });
  connectionHandles.push({
    id: `handle--source--${gqlNode.id}--${sourceHandlesCounter}`,
    index: 0,
    nodeId: gqlNode.id,
    position: Position.Right,
    type: 'source',
    hidden: true,
  });

  connectionHandles.push({
    id: `handle--target--${gqlNode.id}--${targetHandlesCounter}`,
    index: 0,
    nodeId: gqlNode.id,
    position: Position.Left,
    type: 'target',
    hidden: true,
  });

  return connectionHandles;
};
