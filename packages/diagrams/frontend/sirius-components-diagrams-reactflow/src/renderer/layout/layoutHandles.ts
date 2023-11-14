/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { Diagram } from '../DiagramRenderer.types';
import { getEdgeParameters, getUpdatedConnectionHandles } from '../edge/EdgeLayout';
import { ConnectionHandle } from '../handles/ConnectionHandles.types';

export const layoutHandles = (diagram: Diagram) => {
  diagram.edges.forEach((edge) => {
    const { sourceNode, targetNode, sourceHandle, targetHandle } = edge;
    if (sourceNode && targetNode && sourceHandle && targetHandle) {
      const { sourcePosition, targetPosition } = getEdgeParameters(sourceNode, targetNode, diagram.nodes);

      const nodeSourceConnectionHandle: ConnectionHandle | undefined = sourceNode.data.connectionHandles.find(
        (connectionHandle: ConnectionHandle) => connectionHandle.id === sourceHandle
      );
      const nodeTargetConnectionHandle: ConnectionHandle | undefined = targetNode.data.connectionHandles.find(
        (connectionHandle: ConnectionHandle) => connectionHandle.id === targetHandle
      );

      if (
        nodeSourceConnectionHandle?.position !== sourcePosition &&
        nodeTargetConnectionHandle?.position !== targetPosition
      ) {
        const { sourceConnectionHandles, targetConnectionHandles } = getUpdatedConnectionHandles(
          sourceNode,
          targetNode,
          sourcePosition,
          targetPosition,
          sourceHandle,
          targetHandle
        );

        diagram.nodes = diagram.nodes.map((node) => {
          if (edge.sourceNode && edge.targetNode) {
            if (edge.sourceNode.id === node.id) {
              node.data = { ...node.data, connectionHandles: sourceConnectionHandles };
            }
            if (edge.targetNode.id === node.id) {
              node.data = { ...node.data, connectionHandles: targetConnectionHandles };
            }
          }
          return node;
        });
      }
    }
  });
};
