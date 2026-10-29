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

import { Edge, Node, useStoreApi } from '@xyflow/react';
import { createContext, useCallback, useContext } from 'react';
import { useStore } from '../representation/useStore';
import { EdgeData, NodeData } from '../renderer/DiagramRenderer.types';
import { initializeEdgeDrag, moveBendingPoint, moveTemporaryLine } from '../renderer/edge/geometry/edgeGeometry';
import { resolveEdgeGeometryInput } from '../renderer/edge/geometry/resolveEdgeGeometry';
import { setConnectionHandlesHidden, useEdgeDragStopHandler } from '../renderer/edge/geometry/useEdgeDragStopHandler';
import { DiagramContext } from './DiagramContext';
import { DiagramContextValue } from './DiagramContext.types';
import { EdgeGeometryContextProviderProps, EdgeGeometryContextValue } from './EdgeGeometryContext.types';
import { NodeTypeContext } from './NodeContext';
import { NodeTypeContextValue } from './NodeContext.types';

const defaultValue: EdgeGeometryContextValue = {
  onDragStart: () => {},
  onBendingPointDrag: () => {},
  onBendingPointDragStop: () => {},
  onTemporaryLineDrag: () => {},
  onTemporaryLineDragStop: () => {},
  cancelDrag: () => {},
};

export const EdgeGeometryContext = createContext<EdgeGeometryContextValue>(defaultValue);

export const EdgeGeometryContextProvider = ({ children }: EdgeGeometryContextProviderProps) => {
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { getEdges, setNodes, setEdges } = useStore();
  const edges = getEdges();
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const { handleDragStop } = useEdgeDragStopHandler();
  const dragData =
    edges.find((edge) => {
      return !!edge.data?.edgeDragData;
    })?.data?.edgeDragData ?? null;
  const canFinishDrag =
    !readOnly &&
    edges.some((edge) => {
      return edge.id === dragData?.edgeId && edge.selected !== false;
    });
  const cancelDrag = useCallback((): void => {
    setEdges((previousEdges) => {
      let changed = false;
      const updatedEdges = previousEdges.map((edge) => {
        if (!edge.data?.edgeDragData) {
          return edge;
        }
        const { edgeDragData: _edgeDragData, ...data } = edge.data;
        changed = true;
        return { ...edge, data };
      });
      return changed ? updatedEdges : previousEdges;
    });
  }, [setEdges]);

  const value: EdgeGeometryContextValue = {
    onDragStart: (edgeId, index, direction, isSegmentDragged): void => {
      if (readOnly || dragData) {
        return;
      }
      const edge = getEdges().find((candidate) => {
        return candidate.id === edgeId;
      });
      if (!edge?.data || edge.type !== 'manhattan' || edge.selected === false) {
        return;
      }
      const input = resolveEdgeGeometryInput(edge, store.getState().nodeLookup, nodeLayoutHandlers);
      if (input) {
        const edgeDragData = initializeEdgeDrag(input, { index, direction, isSegmentDragged });
        setEdges((previousEdges) => {
          return previousEdges.map((candidate) => {
            return candidate.id === edgeId && candidate.data
              ? { ...candidate, data: { ...candidate.data, edgeDragData } }
              : candidate;
          });
        });
        if (isSegmentDragged) {
          setNodes((previousNodes) => {
            return setConnectionHandlesHidden(previousNodes, input, true);
          });
        }
      }
    },
    onBendingPointDrag: (edgeId, eventData, index): void => {
      setEdges((previousEdges) => {
        let changed = false;
        const updatedEdges = previousEdges.map((edge) => {
          const previousState = edge.data?.edgeDragData;
          if (
            edge.id !== edgeId ||
            !edge.data ||
            !previousState ||
            previousState.index !== index ||
            previousState.isSegmentDragged
          ) {
            return edge;
          }
          changed = true;
          return {
            ...edge,
            data: { ...edge.data, edgeDragData: moveBendingPoint(previousState, eventData) },
          };
        });
        return changed ? updatedEdges : previousEdges;
      });
    },
    onBendingPointDragStop: (edgeId, eventData, index): void => {
      if (canFinishDrag && dragData?.edgeId === edgeId && dragData.index === index && !dragData.isSegmentDragged) {
        handleDragStop(moveBendingPoint(dragData, eventData));
      } else {
        cancelDrag();
      }
    },
    onTemporaryLineDrag: (edgeId, eventData, index): void => {
      setEdges((previousEdges) => {
        let changed = false;
        const updatedEdges = previousEdges.map((edge) => {
          const previousState = edge.data?.edgeDragData;
          if (
            edge.id !== edgeId ||
            !edge.data ||
            !previousState ||
            previousState.index !== index ||
            !previousState.isSegmentDragged
          ) {
            return edge;
          }
          changed = true;
          return {
            ...edge,
            data: { ...edge.data, edgeDragData: moveTemporaryLine(previousState, eventData) },
          };
        });
        return changed ? updatedEdges : previousEdges;
      });
    },
    onTemporaryLineDragStop: (edgeId, eventData, index): void => {
      if (canFinishDrag && dragData?.edgeId === edgeId && dragData.index === index && dragData.isSegmentDragged) {
        handleDragStop(moveTemporaryLine(dragData, eventData));
      } else {
        cancelDrag();
      }
    },
    cancelDrag,
  };

  return <EdgeGeometryContext.Provider value={value}>{children}</EdgeGeometryContext.Provider>;
};
