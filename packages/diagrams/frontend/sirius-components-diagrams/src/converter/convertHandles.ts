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
import { GQLViewModifier } from '../graphql/subscription/nodeFragment.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';

export const convertHandles = (
  elementId: string,
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

    if (edge.sourceId === elementId) {
      connectionHandles.push({
        id: `handle--source--${elementId}--${sourceHandlesCounter}`,
        edgeId: edge.id,
        index: 0,
        nodeId: elementId,
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

    if (edge.targetId === elementId) {
      connectionHandles.push({
        id: `handle--target--${elementId}--${targetHandlesCounter}`,
        edgeId: edge.id,
        index: 0,
        nodeId: elementId,
        position: alreadyLaidOutTargetHandle ? <Position>alreadyLaidOutTargetHandle.handlePosition : Position.Left,
        XYPosition: alreadyLaidOutTargetHandle ? alreadyLaidOutTargetHandle.position : undefined,
        type: 'target',
        hidden: edge.state === GQLViewModifier.Hidden,
      });
      targetHandlesCounter += 1;
    }
  });

  connectionHandles.push({
    id: `handle--source--${elementId}--${sourceHandlesCounter}`,
    edgeId: '',
    index: 0,
    nodeId: elementId,
    position: Position.Right,
    type: 'source',
    hidden: true,
  });

  connectionHandles.push({
    id: `handle--target--${elementId}--${targetHandlesCounter}`,
    edgeId: '',
    index: 0,
    nodeId: elementId,
    position: Position.Left,
    type: 'target',
    hidden: true,
  });

  return connectionHandles;
};
