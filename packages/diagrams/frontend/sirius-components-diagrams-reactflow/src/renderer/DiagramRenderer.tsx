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
import { useEffect } from 'react';
import {
  EdgeChange,
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
import { DiagramRendererProps, NodeData } from './DiagramRenderer.types';
import { ImageNode } from './ImageNode';
import { ListNode } from './ListNode';
import { RectangularNode } from './RectangularNode';

import 'reactflow/dist/style.css';

const nodeTypes: NodeTypes = {
  rectangularNode: RectangularNode,
  imageNode: ImageNode,
  listNode: ListNode,
};

const isSelectChange = (change: NodeChange): change is NodeSelectionChange => change.type === 'select';

export const DiagramRenderer = ({ diagram, selection, setSelection }: DiagramRendererProps) => {
  const store = useStoreApi();
  const [nodes, setNodes, onNodesChange] = useNodesState(diagram.nodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(diagram.edges);

  useEffect(() => {
    setNodes(diagram.nodes);
    setEdges(diagram.edges);
  }, [diagram]);

  useEffect(() => {
    const selectionEntryIds = selection.entries.map((entry) => entry.id);
    const nodesIds = diagram.nodes
      .filter((node) => selectionEntryIds.includes((node.data as NodeData).targetObjectId))
      .map((node) => node.id);
    const reactFlowState = store.getState();
    reactFlowState.unselectNodesAndEdges();
    reactFlowState.addSelectedNodes(nodesIds);
  }, [selection]);

  const handleNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    onNodesChange(changes);

    const selectionEntries: SelectionEntry[] = changes
      .filter(isSelectChange)
      .filter((change) => change.selected)
      .flatMap((change) => diagram.nodes.filter((node) => node.id === change.id))
      .map((node) => {
        const nodeData = node.data as NodeData;
        const { targetObjectId, targetObjectKind, targetObjectLabel } = nodeData;
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
  };

  const handleEdgesChange: OnEdgesChange = (changes: EdgeChange[]) => {
    onEdgesChange(changes);
  };

  const handlePaneClick = () => {
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
  };

  return (
    <ReactFlow
      nodes={nodes}
      nodeTypes={nodeTypes}
      onNodesChange={handleNodesChange}
      edges={edges}
      onEdgesChange={handleEdgesChange}
      onPaneClick={handlePaneClick}
    />
  );
};
