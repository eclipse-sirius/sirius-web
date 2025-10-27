import { Background, ReactFlow, type ReactFlowInstance } from '@xyflow/react';
import '@xyflow/react/dist/style.css';
import { memo, useCallback, useEffect, useMemo, useRef } from 'react';
import { NodeTypeContext } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/NodeContext';
import type { NodeTypeContextValue } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/NodeContext.types';
import { edgeTypes } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/EdgeTypes';
import { StoreContextProvider } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/representation/StoreContext';
import { useStore } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/representation/useStore';
import type { DiagramFixture, RoutingMetrics } from '../types';
import { convertFixtureToDiagram } from '../lib/diagramConversion';
import { computeRoutingMetrics } from '../lib/metrics';
import { HarnessNode } from './HarnessNode';
import { BendpointOverlay } from './BendpointOverlay';

const nodeContextValue: NodeTypeContextValue = {
  nodeConverters: [],
  nodeLayoutHandlers: [],
  nodeTypeContributions: [],
};

const nodeTypes = {
  harnessNode: HarnessNode,
};

interface DiagramViewerProps {
  fixture: DiagramFixture;
  onMetricsChange: (metrics: RoutingMetrics) => void;
}

const DiagramRuntime = memo(({ fixture, onMetricsChange }: DiagramViewerProps) => {
  const { getNodes, getEdges, onNodesChange, onEdgesChange, setNodes, setEdges } = useStore();
  const diagram = useMemo(() => convertFixtureToDiagram(fixture), [fixture]);
  const instanceRef = useRef<ReactFlowInstance | null>(null);

  useEffect(() => {
    setNodes(diagram.nodes);
  }, [diagram.nodes, setNodes]);

  useEffect(() => {
    setEdges(diagram.edges);
  }, [diagram.edges, setEdges]);

  const nodes = getNodes();
  const edges = getEdges();

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
    </div>
  );
});

export const DiagramViewer = ({ fixture, onMetricsChange }: DiagramViewerProps) => {
  return (
    <NodeTypeContext.Provider value={nodeContextValue}>
      <StoreContextProvider>
        <DiagramRuntime fixture={fixture} onMetricsChange={onMetricsChange} />
      </StoreContextProvider>
    </NodeTypeContext.Provider>
  );
};
