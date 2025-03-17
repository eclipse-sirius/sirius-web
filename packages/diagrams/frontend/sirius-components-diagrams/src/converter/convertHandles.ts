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
import { GQLHandleLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLNode, GQLNodeStyle, GQLViewModifier } from '../graphql/subscription/nodeFragment.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';

export const convertHandles = (
  gqlNode: GQLNode<GQLNodeStyle>,
  gqlEdges: GQLEdge[],
  handleLayoutData: GQLHandleLayoutData[]
): ConnectionHandle[] => {
  const connectionHandles: ConnectionHandle[] = [];
  let sourceHandlesCounter = 0;
  let targetHandlesCounter = 0;

  gqlEdges.forEach((edge) => {
    const alreadyLaidOutSourceHandle = handleLayoutData.find(
      (handleLayoutData) => handleLayoutData.edgeId === edge.id && handleLayoutData.type === 'source'
    );

    if (edge.sourceId === gqlNode.id) {
      connectionHandles.push({
        id: `handle--source--${gqlNode.id}--${sourceHandlesCounter}`,
        edgeId: edge.id,
        index: 0,
        nodeId: gqlNode.id,
        position: alreadyLaidOutSourceHandle ? <Position>alreadyLaidOutSourceHandle.handlePosition : Position.Left,
        XYPosition: alreadyLaidOutSourceHandle ? alreadyLaidOutSourceHandle.position : undefined,
        type: 'source',
        hidden: edge.state === GQLViewModifier.Hidden,
      });
      sourceHandlesCounter += 1;
    }

    const alreadyLaidOutTargetHandle = handleLayoutData.find(
      (handleLayoutData) => handleLayoutData.edgeId === edge.id && handleLayoutData.type === 'target'
    );

    if (edge.targetId === gqlNode.id) {
      connectionHandles.push({
        id: `handle--target--${gqlNode.id}--${targetHandlesCounter}`,
        edgeId: edge.id,
        index: 0,
        nodeId: gqlNode.id,
        position: alreadyLaidOutTargetHandle ? <Position>alreadyLaidOutTargetHandle.handlePosition : Position.Left,
        XYPosition: alreadyLaidOutTargetHandle ? alreadyLaidOutTargetHandle.position : undefined,
        type: 'target',
        hidden: edge.state === GQLViewModifier.Hidden,
      });
      targetHandlesCounter += 1;
    }
  });
  connectionHandles.push({
    id: `handle--source--${gqlNode.id}--${sourceHandlesCounter}`,
    edgeId: '',
    index: 0,
    nodeId: gqlNode.id,
    position: Position.Right,
    type: 'source',
    hidden: true,
  });

  connectionHandles.push({
    id: `handle--target--${gqlNode.id}--${targetHandlesCounter}`,
    edgeId: '',
    index: 0,
    nodeId: gqlNode.id,
    position: Position.Left,
    type: 'target',
    hidden: true,
  });

  return connectionHandles;
};
