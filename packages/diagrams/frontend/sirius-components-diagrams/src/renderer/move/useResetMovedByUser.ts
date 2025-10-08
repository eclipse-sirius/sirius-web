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
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { UseResetMovedByUserValue } from './useResetMovedByUser.types';

export const useResetMovedByUser = (): UseResetMovedByUserValue => {
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { getNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const resetMovedByUser = (nodeId: string): void => {
    const finalDiagram: RawDiagram = {
      nodes: getNodes().map((node) => {
        if (node.id === nodeId) {
          return {
            ...node,
            data: {
              ...node.data,
              movedByUser: false,
            },
          };
        }
        return node;
      }),
      edges: getEdges(),
    };
    synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
  };

  return { resetMovedByUser };
};
