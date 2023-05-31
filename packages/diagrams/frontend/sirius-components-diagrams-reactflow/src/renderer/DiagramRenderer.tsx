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

import { useEffect } from 'react';
import {
  EdgeChange,
  NodeChange,
  NodeTypes,
  OnEdgesChange,
  OnNodesChange,
  ReactFlow,
  useEdgesState,
  useNodesState,
} from 'reactflow';
import { DiagramRendererProps } from './DiagramRenderer.types';
import { ImageNode } from './ImageNode';
import { ListNode } from './ListNode';
import { RectangularNode } from './RectangularNode';

import 'reactflow/dist/style.css';

const nodeTypes: NodeTypes = {
  rectangularNode: RectangularNode,
  imageNode: ImageNode,
  listNode: ListNode,
};

export const DiagramRenderer = ({ diagram }: DiagramRendererProps) => {
  const [nodes, setNodes, onNodesChange] = useNodesState(diagram.nodes);
  const [edges, setEdges, onEdgesChange] = useEdgesState(diagram.edges);

  useEffect(() => {
    setNodes(diagram.nodes);
    setEdges(diagram.edges);
  }, [diagram]);

  const handleNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    onNodesChange(changes);
  };

  const handleEdgesChange: OnEdgesChange = (changes: EdgeChange[]) => {
    onEdgesChange(changes);
  };

  return (
    <ReactFlow
      nodes={nodes}
      nodeTypes={nodeTypes}
      onNodesChange={handleNodesChange}
      edges={edges}
      onEdgesChange={handleEdgesChange}
    />
  );
};
