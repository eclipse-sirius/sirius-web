import { Background, ReactFlow } from '@xyflow/react';
import '@xyflow/react/dist/style.css';
import { memo, useEffect, useMemo } from 'react';
import { NodeTypeContext } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/NodeContext';
import type { NodeTypeContextValue } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/contexts/NodeContext.types';
import { edgeTypes } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/edge/EdgeTypes';
import { StoreContextProvider } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/representation/StoreContext';
import { useStore } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/representation/useStore';
import type { DiagramFixture, RoutingMetrics } from '../types';
import { convertFixtureToDiagram } from '../lib/diagramConversion';
import { computeRoutingMetrics } from '../lib/metrics';
import { HarnessNode } from './HarnessNode';

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

  useEffect(() => {
    setNodes(diagram.nodes);
  }, [diagram.nodes, setNodes]);

  useEffect(() => {
    setEdges(diagram.edges);
  }, [diagram.edges, setEdges]);

  const nodes = getNodes();
  const edges = getEdges();

  useEffect(() => {
    onMetricsChange(computeRoutingMetrics(fixture.id, edges));
  }, [edges, fixture.id, onMetricsChange]);

  return (
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
      proOptions={{ hideAttribution: true }}
      panOnDrag={true}
      zoomOnScroll={true}>
      <Background gap={12} />
    </ReactFlow>
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
