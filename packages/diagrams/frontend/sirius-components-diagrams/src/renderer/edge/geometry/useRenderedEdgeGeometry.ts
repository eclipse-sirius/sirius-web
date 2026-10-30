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

import { Edge, Node, useStore as useReactFlowStore, useStoreApi } from '@xyflow/react';
import { useContext, useEffect, useMemo } from 'react';
import { DiagramContext } from '../../../contexts/DiagramContext';
import { EdgeGeometryContext } from '../../../contexts/EdgeGeometryContext';
import { useStore } from '../../../representation/useStore';
import { setConnectionHandlesHidden } from './useEdgeDragStopHandler';
import { NodeTypeContext } from '../../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../../contexts/NodeContext.types';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { RenderedDiagram } from './edgeGeometry.types';
import { getRenderedDiagram } from './resolveEdgeGeometry';

/** Derives drawing geometry and cleans up transient edge interactions. */
export const useRenderedEdgeGeometry = (nodes: Node<NodeData>[], edges: Edge<EdgeData>[]): RenderedDiagram => {
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { readOnly } = useContext(DiagramContext);
  const { cancelDrag } = useContext(EdgeGeometryContext);
  const { setNodes } = useStore();
  const draggedEdge = edges.find((edge) => {
    return !!edge.data?.edgeDragData;
  });
  const dragData = draggedEdge?.data?.edgeDragData ?? null;
  const originalGeometry = dragData?.originalGeometry ?? null;
  const isSegmentDragged = dragData?.isSegmentDragged ?? false;

  useEffect(() => {
    return cancelDrag;
  }, [cancelDrag]);

  useEffect(() => {
    return () => {
      if (originalGeometry && isSegmentDragged) {
        setNodes((previousNodes) => {
          return setConnectionHandlesHidden(previousNodes, originalGeometry, false);
        });
      }
    };
  }, [originalGeometry, isSegmentDragged, setNodes]);

  useEffect(() => {
    if (dragData && (readOnly || draggedEdge?.selected === false)) {
      cancelDrag();
    }
  }, [dragData?.edgeId, readOnly, draggedEdge?.selected, cancelDrag]);
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const trackedIds = new Set(
    edges
      .filter((edge) => {
        return edge.type === 'manhattan';
      })
      .flatMap((edge) => {
        return [edge.source, edge.target];
      })
  );
  const measurementSignature = useReactFlowStore((flow): string => {
    return JSON.stringify(
      [...trackedIds].map((id) => {
        const node = flow.nodeLookup.get(id);
        return [id, node?.width, node?.height, node?.internals.positionAbsolute, node?.internals.handleBounds];
      })
    );
  });
  const measuredNodes = useMemo(() => {
    return new Map(store.getState().nodeLookup);
  }, [store, measurementSignature]);

  return useMemo(() => {
    return getRenderedDiagram(nodes, edges, measuredNodes, nodeLayoutHandlers, dragData);
  }, [nodes, edges, measuredNodes, nodeLayoutHandlers, dragData]);
};
