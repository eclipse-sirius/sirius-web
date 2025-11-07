import type { XYPosition } from '@xyflow/react';

export type FixtureHandlePosition = 'top' | 'right' | 'bottom' | 'left';

export interface FixtureManifestEntry {
  id: string;
  name: string;
  description?: string;
  categories?: string[];
  fileName: string;
}

export interface FixtureNode {
  id: string;
  label: string;
  position: XYPosition;
  size: {
    width: number;
    height: number;
  };
  handles?: FixtureHandle[];
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
  sourcePoint?: XYPosition;
  targetPoint?: XYPosition;
  bendingPoints?: XYPosition[];
}

export interface FixtureHandle {
  id: string;
  type: 'source' | 'target';
  position: FixtureHandlePosition;
  x?: number;
  y?: number;
  isHidden?: boolean;
  isVirtualHandle?: boolean;
}

export interface DiagramFixture {
  id: string;
  name: string;
  description?: string;
  categories?: string[];
  expectedEdgeShape?: string;
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
