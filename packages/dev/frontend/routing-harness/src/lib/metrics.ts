import type { Edge } from '@xyflow/react';
import type { EdgeData } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/DiagramRenderer.types';
import type { RoutingMetrics } from '../types';

const parsePathPoints = (edgePath: string | undefined): number => {
  if (!edgePath) {
    return 0;
  }
  const matches = edgePath.match(/-?\d+(\.\d+)?/g);
  if (!matches) {
    return 0;
  }
  const coordinatePairs = Math.floor(matches.length / 2);
  const bendPoints = Math.max(0, coordinatePairs - 2);
  return bendPoints;
};

export const computeRoutingMetrics = (fixtureId: string, edges: Edge<EdgeData>[]): RoutingMetrics => {
  if (edges.length === 0) {
    return {
      fixtureId,
      totalEdges: 0,
      totalAutoBendPoints: 0,
      minBendPoints: 0,
      maxBendPoints: 0,
      averageBendPoints: 0,
    };
  }

  let totalBendPoints = 0;
  let minBendPoints = Number.POSITIVE_INFINITY;
  let maxBendPoints = 0;

  for (const edge of edges) {
    const bendPoints = parsePathPoints(edge.data?.edgePath);
    totalBendPoints += bendPoints;
    minBendPoints = Math.min(minBendPoints, bendPoints);
    maxBendPoints = Math.max(maxBendPoints, bendPoints);
  }

  return {
    fixtureId,
    totalEdges: edges.length,
    totalAutoBendPoints: totalBendPoints,
    minBendPoints: Number.isFinite(minBendPoints) ? minBendPoints : 0,
    maxBendPoints,
    averageBendPoints: totalBendPoints / edges.length,
  };
};
