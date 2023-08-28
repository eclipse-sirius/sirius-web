/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
  EdgeChange,
  EdgeSelectionChange,
  EdgeTypes,
  NodeChange,
  NodeSelectionChange,
  NodeTypes,
  OnEdgesChange,
  OnNodesChange,
  ReactFlow,
  useEdgesState,
  useNodesState,
  useStoreApi,
} from 'reactflow';
import { DiagramRendererProps, DiagramRendererState, NodeData } from './DiagramRenderer.types';
import { ConnectorContextualMenu } from './connector/ConnectorContextualMenu';
import { useConnector } from './connector/useConnector';
import { useDiagramDelete } from './delete/useDiagramDelete';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { useDrop } from './drop/useDrop';
import { MultiLabelEdge } from './edge/MultiLabelEdge';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { ImageNode } from './node/ImageNode';
import { ListNode } from './node/ListNode';
import { RectangularNode } from './node/RectangularNode';
import { DiagramPalette } from './palette/DiagramPalette';
import { useDiagramPalette } from './palette/useDiagramPalette';
import { DiagramPanel } from './panel/DiagramPanel';
import { useReconnectEdge } from './reconnect-edge/useReconnectEdge';

import 'reactflow/dist/style.css';

const nodeTypes: NodeTypes = {
  rectangularNode: RectangularNode,
  imageNode: ImageNode,
  listNode: ListNode,
};

const edgeTypes: EdgeTypes = {
  multiLabelEdge: MultiLabelEdge,
};

const isNodeSelectChange = (change: NodeChange): change is NodeSelectionChange => change.type === 'select';
const isEdgeSelectChange = (change: EdgeChange): change is EdgeSelectionChange => change.type === 'select';

export const DiagramRenderer = ({ diagram, selection, setSelection }: DiagramRendererProps) => {
  const store = useStoreApi();
  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const [state, setState] = useState<DiagramRendererState>({
    snapToGrid: false,
  });
  const { onDiagramBackgroundClick, hideDiagramPalette } = useDiagramPalette();
  const { onConnect } = useConnector();
  const { reconnectEdge } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();

  const [nodes, setNodes, onNodesChange] = useNodesState<NodeData>(diagram.nodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState<MultiLabelEdgeData>(diagram.edges);

  useEffect(() => {
    setNodes(diagram.nodes);
    setEdges(diagram.edges);
    hideDiagramPalette();
  }, [diagram]);

  useEffect(() => {
    const selectionEntryIds = selection.entries.map((entry) => entry.id);
    const firstSelectedEdgeId = diagram.edges
      .filter((edge) => selectionEntryIds.includes(edge.data ? edge.data.targetObjectId : ''))
      .map((edge) => edge.id);
    const firstSelectedNodeId = diagram.nodes
      .filter((node) => selectionEntryIds.includes(node.data.targetObjectId))
      .map((node) => node.id);
    if (firstSelectedEdgeId.length === 0 && firstSelectedNodeId.length > 0) {
      const reactFlowState = store.getState();
      reactFlowState.unselectNodesAndEdges();
      // Support single graphical selection to display the palette on node containing compartment based on the same targetObjectId.
      reactFlowState.addSelectedNodes([firstSelectedNodeId[0]]);
    }
  }, [selection, diagram]);

  const handleNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    onNodesChange(changes);

    const selectionEntries: SelectionEntry[] = changes
      .filter(isNodeSelectChange)
      .filter((change) => change.selected)
      .flatMap((change) => diagram.nodes.filter((node) => node.id === change.id))
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
      .flatMap((change) => diagram.edges.filter((edge) => edge.id === change.id))
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
    const selection: Selection = {
      entries: [
        {
          id: diagram.metadata.id,
          kind: diagram.metadata.kind,
          label: diagram.metadata.label,
        },
      ],
    };

    setSelection(selection);
    onDiagramBackgroundClick(event);
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
      onEdgesChange={handleEdgesChange}
      onEdgeUpdate={reconnectEdge}
      onPaneClick={handlePaneClick}
      onMove={() => hideDiagramPalette()}
      onDrop={onDrop}
      onDragOver={onDragOver}
      maxZoom={40}
      minZoom={0.1}
      snapToGrid={state.snapToGrid}
      snapGrid={[10, 10]}
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
      <DiagramPalette targetObjectId={diagram.metadata.id} />
      <ConnectorContextualMenu />
    </ReactFlow>
  );
};
