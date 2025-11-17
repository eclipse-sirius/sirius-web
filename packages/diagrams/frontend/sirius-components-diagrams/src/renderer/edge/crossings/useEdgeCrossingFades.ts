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

import { Edge, Node, useStoreApi } from '@xyflow/react';
import { useContext, useEffect, useMemo } from 'react';
import { useStore } from '../../../representation/useStore';
import { MultiLabelEdgeData, EdgeCrossingGap } from '../MultiLabelEdge.types';
import { NodeData } from '../../DiagramRenderer.types';
import { detectEdgeCrossings } from './detectEdgeCrossings';
import { EdgeCrossingWindow } from './edgeCrossing.types';
import { DropNodeContext } from '../../dropNode/DropNodeContext';
import { DropNodeContextValue } from '../../dropNode/DropNodeContext.types';

/**
 * Build a signature string representing the current edge topology + path definitions.
 *
 * @param edges - Edges currently stored in the diagram store.
 * @returns A string that changes whenever an edge path or its endpoints change.
 *
 * React Flow edges can re-render frequently. Using a memoized signature ensures the effect
 * only re-runs crossing detection when the path geometry actually changed.
 */
const buildSignature = (edges: Edge<MultiLabelEdgeData>[]): string => {
  return edges.map((edge) => `${edge.id}:${edge.data?.edgePath ?? ''}:${edge.source}->${edge.target}`).join('|');
};

/**
 * Sanitize, shrink and sort the raw windows so they can be stored back on the edge data.
 *
 * @param windows - Raw windows emitted by the detector, containing ratios and coordinates.
 * @returns A sorted array of normalized gaps with the `intersection` pruned away.
 */
const normalizeWindows = (windows: EdgeCrossingWindow[] | undefined): EdgeCrossingGap[] => {
  if (!windows || windows.length === 0) {
    return [];
  }
  return windows
    .map(({ startRatio, endRatio }) => ({
      startRatio,
      endRatio,
    }))
    .sort((first, second) => first.startRatio - second.startRatio);
};

/**
 * Compare two crossing window arrays while tolerating tiny floating-point drifts.
 *
 * @param first - Existing gaps already stored in the edge data.
 * @param second - Newly computed gaps.
 * @returns True when both arrays are equivalent.
 */
const areGapsEqual = (first?: EdgeCrossingGap[], second?: EdgeCrossingGap[]): boolean => {
  const safeFirst = first?.filter(({ endRatio, startRatio }) => endRatio > startRatio) ?? [];
  const safeSecond = second?.filter(({ endRatio, startRatio }) => endRatio > startRatio) ?? [];
  if (safeFirst.length !== safeSecond.length) {
    return false;
  }
  return safeFirst.every((gap, index) => {
    const otherGap = safeSecond[index];
    if (!otherGap) {
      return false;
    }
    return Math.abs(gap.startRatio - otherGap.startRatio) < 0.001 && Math.abs(gap.endRatio - otherGap.endRatio) < 0.001;
  });
};

/**
 * React hook that keeps `edge.data.crossingGaps` in sync with the detector output.
 *
 * The hook reads the latest edges/nodes from the diagram store, executes the crossing
 * detector, compares the resulting windows to what each edge already stores, and only
 * triggers `setEdges` when a difference arises. While the user is dragging nodes we skip
 * the computation entirely to avoid wasteful work, then recompute right after the drag ends.
 */
export const useEdgeCrossingFades = (): void => {
  const { getEdges, setEdges } = useStore();
  const edges = getEdges() as Edge<MultiLabelEdgeData>[];
  const { dragging } = useContext<DropNodeContextValue>(DropNodeContext);
  const store = useStoreApi<Node<NodeData>, Edge<MultiLabelEdgeData>>();

  const signature = useMemo(() => buildSignature(edges), [edges]);

  useEffect(() => {
    // When a drag is ongoing we skip the entire detection pass so we do not flood
    // React Flow with state updates while coordinates are still changing.
    if (!edges.length || dragging) {
      return;
    }
    const { nodeLookup } = store.getState();
    const crossingWindows = detectEdgeCrossings(edges, nodeLookup);
    let requiresUpdate = false;
    edges.forEach((edge) => {
      const existingGaps = edge.data?.crossingGaps;
      const nextGaps = normalizeWindows(crossingWindows.get(edge.id));
      const normalizedExisting = existingGaps ?? [];
      if (!areGapsEqual(normalizedExisting, nextGaps)) {
        requiresUpdate = true;
      }
    });
    if (!requiresUpdate) {
      return;
    }

    setEdges((previousEdges) =>
      previousEdges.map((edge) => {
        const edgeData = edge.data as MultiLabelEdgeData | undefined;
        if (!edgeData) {
          return edge;
        }
        const nextGaps = normalizeWindows(crossingWindows.get(edge.id));
        if (!nextGaps.length) {
          if (!edgeData.crossingGaps || edgeData.crossingGaps.length === 0) {
            return edge;
          }
          return {
            ...edge,
            data: {
              ...edgeData,
              crossingGaps: undefined,
            },
          };
        }
        return {
          ...edge,
          data: {
            ...edgeData,
            crossingGaps: nextGaps,
          },
        };
      })
    );
  }, [dragging, edges, setEdges, signature, store]);
};
