import { Background, ReactFlow, type ReactFlowInstance, type XYPosition } from '@xyflow/react';
import '@xyflow/react/dist/style.css';
import { memo, useCallback, useEffect, useMemo, useRef, useState } from 'react';
import { NodeTypeContext } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/NodeContext';
import type { NodeTypeContextValue } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/NodeContext.types';
import { edgeTypes } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/EdgeTypes';
import {
  RoutingTraceProvider,
  type RoutingTraceEvent,
} from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/RoutingTraceContext';
import { StoreContextProvider } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/representation/StoreContext';
import { useStore } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/representation/useStore';
import type { DiagramFixture, RoutingMetrics } from '../types';
import { convertFixtureToDiagram } from '../lib/diagramConversion';
import { computeRoutingMetrics } from '../lib/metrics';
import { HarnessNode } from './HarnessNode';
import { BendpointOverlay } from './BendpointOverlay';
import { EdgeDebugPanel, type EdgeDebugEntry, type OverlapInfo, type SegmentInfo } from './EdgeDebugPanel';
import { RoutingLogPanel, type RoutingTraceLogEntry } from './RoutingLogPanel';

const nodeContextValue: NodeTypeContextValue = {
  nodeConverters: [],
  nodeLayoutHandlers: [],
  nodeTypeContributions: [],
};

const nodeTypes = {
  harnessNode: HarnessNode,
};

const PATH_COMMAND_PATTERN = /[ML]/i;
const COORDINATE_SEPARATOR = /[ ,]+/;
const DEFAULT_AXIS_TOLERANCE = 0.25;

const parseEdgePath = (path?: string): XYPosition[] => {
  if (!path || typeof path !== 'string') {
    return [];
  }

  const tokens = path
    .trim()
    .split(PATH_COMMAND_PATTERN)
    .map((token) => token.trim())
    .filter((token) => token.length > 0);

  const points: XYPosition[] = [];
  tokens.forEach((token) => {
    const parts = token.split(COORDINATE_SEPARATOR).filter((part) => part.length > 0);
    if (parts.length >= 2) {
      const x = Number.parseFloat(parts[0] ?? '');
      const y = Number.parseFloat(parts[1] ?? '');
      if (Number.isFinite(x) && Number.isFinite(y)) {
        points.push({ x, y });
      }
    }
  });

  return points;
};

const buildSegments = (points: XYPosition[], tolerance = DEFAULT_AXIS_TOLERANCE): SegmentInfo[] => {
  const segments: SegmentInfo[] = [];

  for (let index = 1; index < points.length; index++) {
    const prev = points[index - 1];
    const current = points[index];
    if (!prev || !current) {
      continue;
    }

    const deltaX = Math.abs(prev.x - current.x);
    const deltaY = Math.abs(prev.y - current.y);

    if (deltaX <= tolerance && deltaY <= tolerance) {
      continue;
    }

    if (deltaX <= tolerance) {
      segments.push({
        axis: 'vertical',
        coordinate: current.x,
        start: Math.min(prev.y, current.y),
        end: Math.max(prev.y, current.y),
      });
      continue;
    }

    if (deltaY <= tolerance) {
      segments.push({
        axis: 'horizontal',
        coordinate: current.y,
        start: Math.min(prev.x, current.x),
        end: Math.max(prev.x, current.x),
      });
    }
  }

  return segments;
};

const findOverlaps = (
  entries: EdgeDebugEntry[],
  tolerance = 0.5,
  minOverlap = 1
): OverlapInfo[] => {
  const overlaps: OverlapInfo[] = [];

  for (let i = 0; i < entries.length; i++) {
    const first = entries[i];
    if (!first) {
      continue;
    }

    for (let j = i + 1; j < entries.length; j++) {
      const second = entries[j];
      if (!second) {
        continue;
      }

      first.segments.forEach((segmentA) => {
        second.segments.forEach((segmentB) => {
          if (segmentA.axis !== segmentB.axis) {
            return;
          }
          if (Math.abs(segmentA.coordinate - segmentB.coordinate) > tolerance) {
            return;
          }
          const overlapStart = Math.max(segmentA.start, segmentB.start);
          const overlapEnd = Math.min(segmentA.end, segmentB.end);
          if (overlapEnd - overlapStart < minOverlap) {
            return;
          }
          overlaps.push({
            edgeId: first.id,
            otherEdgeId: second.id,
            axis: segmentA.axis,
            coordinate: segmentA.coordinate,
            start: overlapStart,
            end: overlapEnd,
          });
        });
      });
    }
  }

  return overlaps;
};

interface DiagramViewerProps {
  fixture: DiagramFixture;
  onMetricsChange: (metrics: RoutingMetrics) => void;
  showDebug?: boolean;
}

const DiagramRuntime = memo(({ fixture, onMetricsChange, showDebug = false }: DiagramViewerProps) => {
  const { getNodes, getEdges, onNodesChange, onEdgesChange, setNodes, setEdges } = useStore();
  const diagram = useMemo(() => convertFixtureToDiagram(fixture), [fixture]);
  const instanceRef = useRef<ReactFlowInstance | null>(null);
  const [traceEvents, setTraceEvents] = useState<RoutingTraceLogEntry[]>([]);

  useEffect(() => {
    setTraceEvents([]);
  }, [fixture.id]);

  useEffect(() => {
    setNodes(diagram.nodes);
  }, [diagram.nodes, setNodes]);

  useEffect(() => {
    setEdges(diagram.edges);
  }, [diagram.edges, setEdges]);

  const nodes = getNodes();
  const edges = getEdges();

  const debugEntries = useMemo<EdgeDebugEntry[]>(() => {
    if (!showDebug) {
      return [];
    }

    return edges
      .map<EdgeDebugEntry | null>((edge) => {
        const path = typeof edge.data?.edgePath === 'string' ? edge.data.edgePath : undefined;
        const points = parseEdgePath(path);
        if (points.length < 2) {
          return null;
        }
        return {
          id: edge.id,
          path,
          points,
          segments: buildSegments(points),
        };
      })
      .filter((entry): entry is EdgeDebugEntry => entry !== null);
  }, [edges, showDebug]);

  const debugOverlaps = useMemo<OverlapInfo[]>(() => {
    if (!showDebug) {
      return [];
    }
    return findOverlaps(debugEntries);
  }, [debugEntries, showDebug]);

  const handleTraceEvent = useCallback((event: RoutingTraceEvent) => {
    setTraceEvents((previous) => {
      const timestamp =
        typeof performance !== 'undefined' && typeof performance.now === 'function'
          ? performance.now()
          : Date.now();
      return [...previous, { ...event, sequence: previous.length, timestamp }];
    });
  }, []);

  const fitToWidth = useCallback(() => {
    if (!instanceRef.current) {
      return;
    }
    instanceRef.current.fitView({
      padding: 0.06,
      includeHiddenNodes: true,
      duration: 0,
    });
  }, []);

  const handleInit = useCallback((instance: ReactFlowInstance) => {
    instanceRef.current = instance;
    fitToWidth();
  }, [fitToWidth]);

  useEffect(() => {
    const frame = requestAnimationFrame(() => {
      fitToWidth();
    });
    return () => cancelAnimationFrame(frame);
  }, [fitToWidth, diagram.nodes, diagram.edges]);

  useEffect(() => {
    const handleResize = () => {
      requestAnimationFrame(() => {
        fitToWidth();
      });
    };
    window.addEventListener('resize', handleResize);
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, [fitToWidth]);

  useEffect(() => {
    onMetricsChange(computeRoutingMetrics(fixture.id, edges));
  }, [edges, fixture.id, onMetricsChange]);

  return (
    <RoutingTraceProvider collector={handleTraceEvent}>
      <div className="harness-diagram-wrapper">
        <ReactFlow
          nodes={nodes}
          edges={edges}
          onNodesChange={onNodesChange}
          onEdgesChange={onEdgesChange}
          nodeTypes={nodeTypes}
          edgeTypes={edgeTypes}
          nodesDraggable={false}
          nodesConnectable={false}
          edgesFocusable={false}
          fitView
          fitViewOptions={{ padding: 0.2 }}
          onInit={handleInit}
          proOptions={{ hideAttribution: true }}
          panOnDrag={true}
          zoomOnScroll={true}>
          <Background gap={12} />
        </ReactFlow>
        <BendpointOverlay edges={edges} />
        {showDebug && debugEntries.length > 0 ? (
          <EdgeDebugPanel fixtureId={fixture.id} entries={debugEntries} overlaps={debugOverlaps} />
        ) : null}
      </div>
      <RoutingLogPanel fixtureId={fixture.id} events={traceEvents} />
    </RoutingTraceProvider>
  );
});

DiagramRuntime.displayName = 'DiagramRuntime';

export const DiagramViewer = ({ fixture, onMetricsChange, showDebug = false }: DiagramViewerProps) => {
  return (
    <NodeTypeContext.Provider value={nodeContextValue}>
      <StoreContextProvider>
        <DiagramRuntime fixture={fixture} onMetricsChange={onMetricsChange} showDebug={showDebug} />
      </StoreContextProvider>
    </NodeTypeContext.Provider>
  );
};
