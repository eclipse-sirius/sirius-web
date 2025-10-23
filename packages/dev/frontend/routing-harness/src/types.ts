import type { XYPosition } from '@xyflow/react';

export type FixtureHandlePosition = 'top' | 'right' | 'bottom' | 'left';

export interface FixtureManifestEntry {
  id: string;
  name: string;
  description?: string;
  categories?: string[];
}

export interface FixtureNode {
  id: string;
  label: string;
  position: XYPosition;
  size: {
    width: number;
    height: number;
  };
}

export interface FixtureEdge {
  id: string;
  source: string;
  target: string;
  type: 'manhattan' | 'smartManhattan' | 'oblique';
  sourcePosition?: FixtureHandlePosition;
  targetPosition?: FixtureHandlePosition;
  sourceHandleId?: string;
  targetHandleId?: string;
}

export interface DiagramFixture {
  id: string;
  name: string;
  description?: string;
  nodes: FixtureNode[];
  edges: FixtureEdge[];
}

export interface RoutingMetrics {
  fixtureId: string;
  totalEdges: number;
  totalAutoBendPoints: number;
  minBendPoints: number;
  maxBendPoints: number;
  averageBendPoints: number;
}
