/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { UseMakeSameSizeValue } from './useMakeSameSize.types';
import { useProcessLayoutTool } from './useProcessLayoutTool';

export const useMakeSameSize = (): UseMakeSameSizeValue => {
  const { getNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { processLayoutTool } = useProcessLayoutTool();

  const makeNodesSameSize = useCallback((selectedNodeIds: string[], refElementId: string | null) => {
    processLayoutTool(
      selectedNodeIds,
      (_selectedNodes, refNode) => {
        return getNodes().map((node) => {
          if (!selectedNodeIds.includes(node.id) || node.data.nodeDescription?.userResizable === 'NONE') {
            return node;
          }

          return {
            ...node,
            width: refNode.width,
            height: refNode.height,
            data: {
              ...node.data,
              resizedByUser: true,
            },
          };
        });
      },
      null,
      refElementId
    );
  }, []);

  return {
    makeNodesSameSize,
  };
};
