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
import { Edge, Node, ReactFlowState } from '@xyflow/react';
import { GQLReferencePosition } from '../../graphql/subscription/diagramEventSubscription.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getNearestPointInPerimeter, getUpdatedHandleForNode } from '../edge/EdgeLayout';
import { RawDiagram } from './layout.types';

const isHandleReferencePosition = (causedBy: string) => causedBy === 'InvokeSingleClickOnTwoDiagramElementsToolInput';

export const updateHandleFromReferencePosition = (
  rawDiagram: RawDiagram,
  state: ReactFlowState<Node<NodeData>, Edge<EdgeData>>,
  referencePosition: GQLReferencePosition | null
) => {
  if (
    referencePosition &&
    isHandleReferencePosition(referencePosition.causedBy) &&
    referencePosition.parentId &&
    referencePosition.positions[0]
  ) {
    const targetedNode = state.nodeLookup.get(referencePosition.parentId);
    if (targetedNode && targetedNode.width && targetedNode.height) {
      const { position, XYPosition } = getNearestPointInPerimeter(
        targetedNode.internals.positionAbsolute.x,
        targetedNode.internals.positionAbsolute.y,
        targetedNode.width,
        targetedNode.height,
        referencePosition.positions[0].x,
        referencePosition.positions[0].y
      );

      rawDiagram.nodes = rawDiagram.nodes.map((node) => {
        if (node.id === referencePosition.parentId) {
          const addedHandle = node.data.connectionHandles.find((handle) => !state.edgeLookup.get(handle.edgeId));
          if (addedHandle) {
            const updatedConnectionHandles = getUpdatedHandleForNode(
              targetedNode,
              addedHandle.edgeId,
              addedHandle.id || '',
              { x: XYPosition.x, y: XYPosition.y },
              position
            );
            return {
              ...node,
              data: {
                ...node.data,
                connectionHandles: updatedConnectionHandles,
              },
            };
          }
        }
        return node;
      });
    }
  }
};
