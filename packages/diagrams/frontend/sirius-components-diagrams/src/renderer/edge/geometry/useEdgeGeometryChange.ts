/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { Edge, Node, NodeChange, useStoreApi } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { NodeTypeContext } from '../../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../../contexts/NodeContext.types';
import { useStore } from '../../../representation/useStore';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { isBoundsChangeFinished } from '../../layout-events/isBoundsChangeFinished';
import { getEdgesUpdatedWithGeometry } from './resolveEdgeGeometry';
import { UseEdgeGeometryChangeValue } from './useEdgeGeometryChange.types';

export const useEdgeGeometryChange = (): UseEdgeGeometryChangeValue => {
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { getNodes } = useStore();
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const applyEdgeGeometryChange = useCallback(
    (changes: NodeChange<Node<NodeData>>[], nodes: Node<NodeData>[], edges: Edge<EdgeData>[]): Edge<EdgeData>[] => {
      if (!isBoundsChangeFinished(changes, getNodes())) {
        return edges;
      }
      return getEdgesUpdatedWithGeometry(nodes, edges, store.getState().nodeLookup, nodeLayoutHandlers);
    },
    [getNodes, store, nodeLayoutHandlers]
  );
  return { applyEdgeGeometryChange };
};
