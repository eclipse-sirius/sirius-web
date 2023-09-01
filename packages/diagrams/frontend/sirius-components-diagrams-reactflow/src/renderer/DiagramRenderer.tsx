/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { Selection, SelectionEntry } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useRef, useState } from 'react';
import {
  Background,
  BackgroundVariant,
  ConnectionMode,
  EdgeChange,
  EdgeSelectionChange,
  Node,
  NodeChange,
  NodeSelectionChange,
  OnEdgesChange,
  OnNodesChange,
  ReactFlow,
  useEdgesState,
  useNodesState,
  useReactFlow,
  useStoreApi,
} from 'reactflow';
import { convertDiagram } from '../converter/convertDiagram';
import { Diagram, DiagramRendererProps, DiagramRendererState, EdgeData, NodeData } from './DiagramRenderer.types';
import { ConnectorContextualMenu } from './connector/ConnectorContextualMenu';
import { useConnector } from './connector/useConnector';
import { useDiagramDelete } from './delete/useDiagramDelete';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { useDrop } from './drop/useDrop';
import { edgeTypes } from './edge/EdgeTypes';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { useLayout } from './layout/useLayout';
import { nodeTypes } from './node/NodeTypes';
import { DiagramNodeType } from './node/NodeTypes.types';
import { DiagramPalette } from './palette/DiagramPalette';
import { useDiagramPalette } from './palette/useDiagramPalette';
import { useEdgePalette } from './palette/useEdgePalette';
import { DiagramPanel } from './panel/DiagramPanel';
import { useReconnectEdge } from './reconnect-edge/useReconnectEdge';

import 'reactflow/dist/style.css';

const isNodeSelectChange = (change: NodeChange): change is NodeSelectionChange => change.type === 'select';
const isEdgeSelectChange = (change: EdgeChange): change is EdgeSelectionChange => change.type === 'select';

export const DiagramRenderer = ({ diagramRefreshedEventPayload, selection, setSelection }: DiagramRendererProps) => {
  const store = useStoreApi();
  const reactFlowInstance = useReactFlow<NodeData, EdgeData>();
  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const [state, setState] = useState<DiagramRendererState>({
    snapToGrid: false,
    fitviewLifecycle: 'neverRendered',
  });

  const { layout } = useLayout();

  const { onDiagramBackgroundClick, hideDiagramPalette } = useDiagramPalette();
  const { onEdgeClick } = useEdgePalette();

  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { reconnectEdge } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();

  const [nodes, setNodes, onNodesChange] = useNodesState<NodeData>([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState<MultiLabelEdgeData>([]);

  useEffect(() => {
    const { diagram } = diagramRefreshedEventPayload;
    const convertedDiagram: Diagram = convertDiagram(diagram);

    const previousDiagram: Diagram = {
      metadata: { ...convertedDiagram.metadata },
      nodes: nodes as Node<NodeData, DiagramNodeType>[],
      edges,
    };

    layout(previousDiagram, convertedDiagram, (laidOutDiagram) => {
      setNodes(laidOutDiagram.nodes);
      setEdges(laidOutDiagram.edges);
      hideDiagramPalette();
      if (state.fitviewLifecycle === 'neverRendered') {
        setState((prevState) => ({ ...prevState, fitviewLifecycle: 'shouldFitview' }));
      }
    });
  }, [diagramRefreshedEventPayload]);

  useEffect(() => {
    if (state.fitviewLifecycle === 'shouldFitview') {
      reactFlowInstance.fitView({ minZoom: 0.5 });
      setState((prevState) => ({ ...prevState, fitviewLifecycle: 'viewfit' }));
    }
  }, [state.fitviewLifecycle]);

  useEffect(() => {
    const selectionEntryIds = selection.entries.map((entry) => entry.id);
    const edgesMatchingWorkbenchSelection = edges.filter((edge) =>
      selectionEntryIds.includes(edge.data ? edge.data.targetObjectId : '')
    );
    const nodesMatchingWorkbenchSelection = nodes.filter((node) =>
      selectionEntryIds.includes(node.data.targetObjectId)
    );

    const alreadySelectedNodesMatchingWorkbenchSelection = nodesMatchingWorkbenchSelection.filter(
      (node) => node.selected
    );
    const firstNodeMatchingWorkbenchSelection =
      alreadySelectedNodesMatchingWorkbenchSelection[0] ?? nodesMatchingWorkbenchSelection[0];

    if (edgesMatchingWorkbenchSelection.length === 0 && firstNodeMatchingWorkbenchSelection) {
      const firstNodeIdMatchingWorkbenchSelection = firstNodeMatchingWorkbenchSelection.id;

      // Support single graphical selection to display the palette on node containing compartment based on the same targetObjectId.
      const reactFlowState = store.getState();
      const currentlySelectedNodes = reactFlowState.getNodes().filter((node) => node.selected);

      const isAlreadySelected = currentlySelectedNodes
        .map((node) => node.id)
        .includes(firstNodeIdMatchingWorkbenchSelection);
      if (!isAlreadySelected) {
        reactFlowState.unselectNodesAndEdges();
        reactFlowState.addSelectedNodes([firstNodeIdMatchingWorkbenchSelection]);

        const selectedNodes = reactFlowState
          .getNodes()
          .filter((node) => firstNodeIdMatchingWorkbenchSelection === node.id);
        reactFlowInstance.fitView({ nodes: selectedNodes, maxZoom: 2, duration: 1000 });
      }
    }
  }, [selection]);

  const handleNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    onNodesChange(changes);

    const selectionEntries: SelectionEntry[] = changes
      .filter(isNodeSelectChange)
      .filter((change) => change.selected)
      .flatMap((change) => nodes.filter((node) => node.id === change.id))
      .map((node) => {
        const { targetObjectId, targetObjectKind, targetObjectLabel } = node.data;
        return {
          id: targetObjectId,
          kind: targetObjectKind,
          label: targetObjectLabel,
        };
      });

    const currentSelectionEntryIds = selection.entries.map((selectionEntry) => selectionEntry.id);
    const shouldUpdateSelection =
      selectionEntries.map((entry) => entry.id).filter((entryId) => currentSelectionEntryIds.includes(entryId))
        .length !== selectionEntries.length;

    if (selectionEntries.length > 0 && shouldUpdateSelection) {
      setSelection({ entries: selectionEntries });
    }

    if (selectionEntries.length > 0) {
      hideDiagramPalette();
    }
  };

  const handleEdgesChange: OnEdgesChange = (changes: EdgeChange[]) => {
    onEdgesChange(changes);

    const selectionEntries: SelectionEntry[] = changes
      .filter(isEdgeSelectChange)
      .filter((change) => change.selected)
      .flatMap((change) => edges.filter((edge) => edge.id === change.id))
      .map((edge) => {
        if (edge.data) {
          const { targetObjectId, targetObjectKind, targetObjectLabel } = edge.data;
          return {
            id: targetObjectId,
            kind: targetObjectKind,
            label: targetObjectLabel,
          };
        } else {
          return { id: '', kind: '', label: '' };
        }
      });

    const currentSelectionEntryIds = selection.entries.map((selectionEntry) => selectionEntry.id);
    const shouldUpdateSelection =
      selectionEntries.map((entry) => entry.id).filter((entryId) => currentSelectionEntryIds.includes(entryId))
        .length !== selectionEntries.length;

    if (selectionEntries.length > 0 && shouldUpdateSelection) {
      setSelection({ entries: selectionEntries });
    }
    if (selectionEntries.length > 0) {
      hideDiagramPalette();
    }
  };

  const handlePaneClick = (event: React.MouseEvent<Element, MouseEvent>) => {
    const {
      diagram: {
        id,
        metadata: { kind, label },
      },
    } = diagramRefreshedEventPayload;
    const selection: Selection = {
      entries: [
        {
          id,
          kind,
          label,
        },
      ],
    };

    setSelection(selection);
    onDiagramBackgroundClick(event);
  };

  const handleEdgeClick = (event: React.MouseEvent<Element, MouseEvent>) => {
    onEdgeClick(event);
  };

  const handleSnapToGrid = (snapToGrid: boolean) => setState((prevState) => ({ ...prevState, snapToGrid }));

  const onKeyDown = (event: React.KeyboardEvent<Element>) => {
    onDirectEdit(event);
    onDelete(event);
  };

  return (
    <ReactFlow
      nodes={nodes}
      nodeTypes={nodeTypes}
      onNodesChange={handleNodesChange}
      edges={edges}
      edgeTypes={edgeTypes}
      onKeyDown={onKeyDown}
      onConnect={onConnect}
      onConnectStart={onConnectStart}
      onConnectEnd={onConnectEnd}
      onEdgesChange={handleEdgesChange}
      onEdgeUpdate={reconnectEdge}
      onPaneClick={handlePaneClick}
      onEdgeClick={handleEdgeClick}
      onMove={() => hideDiagramPalette()}
      onDrop={onDrop}
      onDragOver={onDragOver}
      maxZoom={40}
      minZoom={0.1}
      snapToGrid={state.snapToGrid}
      snapGrid={[10, 10]}
      connectionMode={ConnectionMode.Loose}
      ref={ref}>
      {state.snapToGrid ? (
        <>
          <Background
            id="small-grid"
            style={{ backgroundColor: '#ffffff' }}
            variant={BackgroundVariant.Lines}
            gap={10}
            color="#f1f1f1"
          />
          <Background id="large-grid" variant={BackgroundVariant.Lines} gap={100} offset={1} color="#cccccc" />
        </>
      ) : (
        <Background style={{ backgroundColor: '#ffffff' }} variant={BackgroundVariant.Lines} color="#ffffff" />
      )}
      <DiagramPanel snapToGrid={state.snapToGrid} onSnapToGrid={handleSnapToGrid} />
      <DiagramPalette targetObjectId={diagramRefreshedEventPayload.diagram.id} />
      <ConnectorContextualMenu />
    </ReactFlow>
  );
};
