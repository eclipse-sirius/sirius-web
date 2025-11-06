import {
  Background,
  ReactFlow,
  ReactFlowProvider,
  getViewportForBounds,
  type Edge as ReactFlowEdge,
  type Node as ReactFlowNode,
  type Rect,
  type ReactFlowInstance,
  type Viewport,
  type XYPosition,
} from '@xyflow/react';
import '@xyflow/react/dist/style.css';
import { forwardRef, useCallback, useEffect, useImperativeHandle, useMemo, useRef, useState } from 'react';
import { NodeTypeContext } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/NodeContext';
import type { NodeTypeContextValue } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/NodeContext.types';
import {
  edgeTypes as defaultEdgeTypes,
  type EdgeComponentsMap,
} from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/EdgeTypes';
import { ObliqueEdgeWrapper } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/ObliqueEdgeWrapper';
import { SmartStepEdgeWrapper } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/SmartStepEdgeWrapper';
import { SmoothStepEdgeWrapper } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/SmoothStepEdgeWrapper';
import {
  RoutingTraceProvider,
  type RoutingTraceEvent,
} from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/RoutingTraceContext';
import { StoreContextProvider } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/representation/StoreContext';
import { useStore } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/representation/useStore';
import type {
  EdgeData,
  NodeData,
} from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/DiagramRenderer.types';
import type { DiagramFixture, FixtureEdge, RoutingMetrics } from '../types';
import { convertFixtureToDiagram } from '../lib/diagramConversion';
import { computeRoutingMetrics } from '../lib/metrics';
import { HarnessNode } from './HarnessNode';
import { BendpointOverlay } from './BendpointOverlay';
import { EdgeDebugPanel, type EdgeDebugEntry, type OverlapInfo, type SegmentInfo } from './EdgeDebugPanel';
import { RoutingLogPanel, type RoutingTraceLogEntry } from './RoutingLogPanel';
import { toPng } from 'html-to-image';
import { HarnessMarkerDefinitions } from './HarnessMarkerDefinitions';

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

const CONTENT_MARGIN = 48;

const PNG_DATA_URL_PREFIX = 'data:image/png;base64,';
const PNG_SIGNATURE_LENGTH = 8;
const PNG_CHUNK_HEADER_SIZE = 8; // length (4) + type (4)
const PNG_CHUNK_FOOTER_SIZE = 4; // CRC
const PNG_TIME_CHUNK = 'tIME';

export type EdgeRoutingAlgorithm = FixtureEdge['type'];

const EDGE_WRAPPER_OVERRIDES: EdgeComponentsMap = {
  manhattan: SmoothStepEdgeWrapper,
  smartManhattan: SmartStepEdgeWrapper,
  oblique: ObliqueEdgeWrapper,
};

const base64ToUint8Array = (base64: string): Uint8Array => {
  const binary = atob(base64);
  const length = binary.length;
  const bytes = new Uint8Array(length);
  for (let index = 0; index < length; index += 1) {
    bytes[index] = binary.charCodeAt(index);
  }
  return bytes;
};

const uint8ArrayToBase64 = (bytes: Uint8Array): string => {
  let binary = '';
  const chunkSize = 0x8000;
  for (let i = 0; i < bytes.length; i += chunkSize) {
    const chunk = bytes.subarray(i, i + chunkSize);
    binary += String.fromCharCode(...chunk);
  }
  return btoa(binary);
};

const sanitizePngDataUrl = (dataUrl: string): string => {
  if (!dataUrl.startsWith(PNG_DATA_URL_PREFIX)) {
    return dataUrl;
  }
  try {
    const base64 = dataUrl.slice(PNG_DATA_URL_PREFIX.length);
    const bytes = base64ToUint8Array(base64);
    if (bytes.length <= PNG_SIGNATURE_LENGTH + PNG_CHUNK_HEADER_SIZE + PNG_CHUNK_FOOTER_SIZE) {
      return dataUrl;
    }

    const signature = bytes.slice(0, PNG_SIGNATURE_LENGTH);
    const chunks: number[] = [...signature];
    let offset = PNG_SIGNATURE_LENGTH;
    const view = new DataView(bytes.buffer, bytes.byteOffset, bytes.byteLength);

    while (offset + PNG_CHUNK_HEADER_SIZE <= bytes.length) {
      const length = view.getUint32(offset);
      const typeArray = bytes.slice(offset + 4, offset + 8);
      const type = String.fromCharCode(...typeArray);
      const chunkTotalLength = PNG_CHUNK_HEADER_SIZE + length + PNG_CHUNK_FOOTER_SIZE;
      const nextOffset = offset + chunkTotalLength;

      if (nextOffset > bytes.length) {
        break;
      }

      if (type !== PNG_TIME_CHUNK) {
        chunks.push(...bytes.slice(offset, nextOffset));
      }

      offset = nextOffset;
      if (type === 'IEND') {
        break;
      }
    }

    const sanitizedBytes = new Uint8Array(chunks);
    const sanitizedBase64 = uint8ArrayToBase64(sanitizedBytes);
    return `${PNG_DATA_URL_PREFIX}${sanitizedBase64}`;
  } catch (error) {
    return dataUrl;
  }
};

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
  edgeAlgorithmOverride?: EdgeRoutingAlgorithm;
}

export interface DiagramViewerHandle {
  exportAsPng: () => Promise<string>;
}

const collectNodePoints = (node: ReactFlowNode<NodeData>): XYPosition[] => {
  const absolutePosition = node.positionAbsolute ?? node.position ?? { x: 0, y: 0 };
  const width =
    (typeof node.width === 'number' && Number.isFinite(node.width) ? node.width : undefined) ??
    (typeof node.data?.defaultWidth === 'number' && Number.isFinite(node.data.defaultWidth) ? node.data.defaultWidth : undefined) ??
    0;
  const height =
    (typeof node.height === 'number' && Number.isFinite(node.height) ? node.height : undefined) ??
    (typeof node.data?.defaultHeight === 'number' && Number.isFinite(node.data.defaultHeight) ? node.data.defaultHeight : undefined) ??
    0;

  const x = absolutePosition.x;
  const y = absolutePosition.y;
  const w = Math.max(width, 0);
  const h = Math.max(height, 0);

  return [
    { x, y },
    { x: x + w, y },
    { x, y: y + h },
    { x: x + w, y: y + h },
  ];
};

const collectEdgePoints = (edge: ReactFlowEdge<EdgeData>): XYPosition[] => {
  const points: XYPosition[] = [];
  if (typeof edge.sourceX === 'number' && Number.isFinite(edge.sourceX) && typeof edge.sourceY === 'number' && Number.isFinite(edge.sourceY)) {
    points.push({ x: edge.sourceX, y: edge.sourceY });
  }
  if (Array.isArray(edge.data?.bendingPoints)) {
    edge.data.bendingPoints.forEach((point) => {
      if (point && typeof point.x === 'number' && typeof point.y === 'number') {
        points.push(point);
      }
    });
  }
  const parsedPath = parseEdgePath(edge.data?.edgePath);
  if (parsedPath.length > 0) {
    points.push(...parsedPath);
  }
  if (typeof edge.targetX === 'number' && Number.isFinite(edge.targetX) && typeof edge.targetY === 'number' && Number.isFinite(edge.targetY)) {
    points.push({ x: edge.targetX, y: edge.targetY });
  }
  return points;
};

const computeDiagramBounds = (
  nodes: ReactFlowNode<NodeData>[],
  edges: ReactFlowEdge<EdgeData>[],
  margin = CONTENT_MARGIN
): Rect => {
  const points: XYPosition[] = [];

  nodes.forEach((node) => {
    points.push(...collectNodePoints(node));
  });

  edges.forEach((edge) => {
    points.push(...collectEdgePoints(edge));
  });

  if (points.length === 0) {
    const safeMargin = Math.max(margin, 1);
    return {
      x: -safeMargin,
      y: -safeMargin,
      width: safeMargin * 2,
      height: safeMargin * 2,
    };
  }

  let minX = points[0]?.x ?? 0;
  let minY = points[0]?.y ?? 0;
  let maxX = minX;
  let maxY = minY;

  points.forEach((point) => {
    if (typeof point?.x === 'number' && Number.isFinite(point.x) && typeof point?.y === 'number' && Number.isFinite(point.y)) {
      minX = Math.min(minX, point.x);
      minY = Math.min(minY, point.y);
      maxX = Math.max(maxX, point.x);
      maxY = Math.max(maxY, point.y);
    }
  });

  const paddedMinX = minX - margin;
  const paddedMinY = minY - margin;
  const paddedMaxX = maxX + margin;
  const paddedMaxY = maxY + margin;

  const width = Math.max(paddedMaxX - paddedMinX, 1);
  const height = Math.max(paddedMaxY - paddedMinY, 1);

  return {
    x: paddedMinX,
    y: paddedMinY,
    width,
    height,
  };
};

const DiagramRuntime = forwardRef<DiagramViewerHandle, DiagramViewerProps>(
  ({ fixture, onMetricsChange, showDebug = false, edgeAlgorithmOverride }, ref) => {
    const { getNodes, getEdges, onNodesChange, onEdgesChange, setNodes, setEdges } = useStore();
    const diagram = useMemo(() => convertFixtureToDiagram(fixture), [fixture]);
    const instanceRef = useRef<ReactFlowInstance | null>(null);
    const wrapperRef = useRef<HTMLDivElement | null>(null);
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

    const resolvedEdgeTypes = useMemo<EdgeComponentsMap>(() => {
      if (!edgeAlgorithmOverride) {
        return defaultEdgeTypes;
      }
      const overrideWrapper = EDGE_WRAPPER_OVERRIDES[edgeAlgorithmOverride];
      if (!overrideWrapper) {
        return defaultEdgeTypes;
      }
      return {
        manhattan: overrideWrapper,
        smartManhattan: overrideWrapper,
        oblique: overrideWrapper,
      };
    }, [edgeAlgorithmOverride]);

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

    const fitToContent = useCallback(() => {
      const instance = instanceRef.current;
      const wrapper = wrapperRef.current;
      if (!instance || !wrapper) {
        return;
      }

      const viewportWidth = wrapper.clientWidth;
      const viewportHeight = wrapper.clientHeight;
      if (viewportWidth <= 0 || viewportHeight <= 0) {
        return;
      }

      const nodesSnapshot = instance.getNodes();
      const edgesSnapshot = instance.getEdges();
      const contentBounds = computeDiagramBounds(nodesSnapshot, edgesSnapshot, CONTENT_MARGIN);

      const viewport = getViewportForBounds(contentBounds, viewportWidth, viewportHeight, 0.5, 2, 0.05);
      instance.setViewport(viewport, { duration: 0 });
    }, []);

    const handleInit = useCallback(
      (instance: ReactFlowInstance) => {
        instanceRef.current = instance;
        fitToContent();
      },
      [fitToContent]
    );

    useEffect(() => {
      const frame = requestAnimationFrame(() => {
        fitToContent();
      });
      return () => cancelAnimationFrame(frame);
    }, [fitToContent, diagram.nodes, diagram.edges]);

    useEffect(() => {
      const handleResize = () => {
        requestAnimationFrame(() => {
          fitToContent();
        });
      };
      window.addEventListener('resize', handleResize);
      return () => {
        window.removeEventListener('resize', handleResize);
      };
    }, [fitToContent]);

    useEffect(() => {
      onMetricsChange(computeRoutingMetrics(fixture.id, edges));
    }, [edges, fixture.id, onMetricsChange]);

    const waitForEdgePaths = useCallback(async (maxAttempts = 20) => {
      for (let attempt = 0; attempt < maxAttempts; attempt += 1) {
        const snapshot = instanceRef.current?.getEdges() ?? [];
        const allReady = snapshot.every(
          (edge) => typeof edge.data?.edgePath === 'string' && edge.data.edgePath.length > 0
        );
        if (allReady) {
          return snapshot;
        }
        await new Promise<void>((resolve) => window.requestAnimationFrame(() => resolve()));
      }
      return instanceRef.current?.getEdges() ?? [];
    }, []);

    const exportDiagramAsPng = useCallback(async (): Promise<string> => {
      const instance = instanceRef.current;
      const wrapper = wrapperRef.current;
      if (!instance || !wrapper) {
        throw new Error('Diagram is not ready for export.');
      }

      fitToContent();
      await new Promise<void>((resolve) => window.requestAnimationFrame(() => resolve()));

      await new Promise<void>((resolve) => window.requestAnimationFrame(() => resolve()));
      const edgesSnapshot = await waitForEdgePaths();
      const nodesSnapshot = instance.getNodes();
      const contentBounds = computeDiagramBounds(nodesSnapshot, edgesSnapshot, CONTENT_MARGIN);

      const exportWidth = Math.max(Math.round(contentBounds.width), 1);
      const exportHeight = Math.max(Math.round(contentBounds.height), 1);

      const viewport: Viewport = getViewportForBounds(contentBounds, exportWidth, exportHeight, 0.5, 2, 0.05);

      const edgesLayer = wrapper.querySelector<HTMLElement>('.react-flow__edges');
      const markerDefs =
        wrapper.querySelector<HTMLElement>('#harness-edge-markers') ??
        wrapper.querySelector<HTMLElement>('#edge-markers');
      const viewportElement = wrapper.querySelector<HTMLElement>('.react-flow__viewport');

      if (!viewportElement) {
        throw new Error('Unable to locate the diagram viewport for export.');
      }

      let clonedMarkerDefs: Node | null = null;
      if (edgesLayer && markerDefs) {
        clonedMarkerDefs = markerDefs.cloneNode(true);
        edgesLayer.insertBefore(clonedMarkerDefs, edgesLayer.firstChild);
      }

      try {
        const dataUrl = await toPng(viewportElement, {
          backgroundColor: '#ffffff',
          width: exportWidth,
          height: exportHeight,
          style: {
            width: `${exportWidth}`,
            height: `${exportHeight}`,
            transform: `translate(${viewport.x}px, ${viewport.y}px) scale(${viewport.zoom})`,
          },
          pixelRatio: 2,
        });
        return sanitizePngDataUrl(dataUrl);
      } finally {
        if (clonedMarkerDefs && edgesLayer) {
          edgesLayer.removeChild(clonedMarkerDefs);
        }
      }
    }, [fitToContent, waitForEdgePaths]);

    useImperativeHandle(
      ref,
      () => ({
        exportAsPng: exportDiagramAsPng,
      }),
      [exportDiagramAsPng]
    );

    return (
      <RoutingTraceProvider collector={handleTraceEvent}>
        <HarnessMarkerDefinitions />
        <div className="harness-diagram-wrapper" ref={wrapperRef}>
          <ReactFlow
            nodes={nodes}
            edges={edges}
            onNodesChange={onNodesChange}
            onEdgesChange={onEdgesChange}
            nodeTypes={nodeTypes}
            edgeTypes={resolvedEdgeTypes}
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
  }
);

DiagramRuntime.displayName = 'DiagramRuntime';

export const DiagramViewer = forwardRef<DiagramViewerHandle, DiagramViewerProps>(
  ({ fixture, onMetricsChange, showDebug = false, edgeAlgorithmOverride }, ref) => {
    return (
      <ReactFlowProvider>
        <NodeTypeContext.Provider value={nodeContextValue}>
          <StoreContextProvider>
            <DiagramRuntime
              ref={ref}
              fixture={fixture}
              onMetricsChange={onMetricsChange}
              showDebug={showDebug}
              edgeAlgorithmOverride={edgeAlgorithmOverride}
            />
          </StoreContextProvider>
        </NodeTypeContext.Provider>
      </ReactFlowProvider>
    );
  }
);
DiagramViewer.displayName = 'DiagramViewer';
