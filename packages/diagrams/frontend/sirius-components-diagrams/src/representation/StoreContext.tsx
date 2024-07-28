/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { Edge, EdgeChange, Node, NodeChange, applyEdgeChanges, applyNodeChanges } from '@xyflow/react';
import React, { useCallback, useState } from 'react';
import { EdgeData, NodeData } from '../renderer/DiagramRenderer.types';
import { StoreContextValue } from './StoreContext.types';

const defaultValue: StoreContextValue = {
  getNodes: () => [],
  getNode: () => undefined,
  setNodes: () => {},
  getEdges: () => [],
  getEdge: () => undefined,
  setEdges: () => {},
  onNodesChange: () => {},
  onEdgesChange: () => {},
};

export const StoreContext = React.createContext<StoreContextValue>(defaultValue);

export const StoreContextProvider = ({ children }) => {
  const [nodes, setNodes] = useState<Node<NodeData>[]>([]);
  const [edges, setEdges] = useState<Edge<EdgeData>[]>([]);

  const onNodesChange = useCallback(
    (changes: NodeChange<Node<NodeData>>[]) => setNodes((prevState) => applyNodeChanges(changes, prevState)),
    [setEdges]
  );

  const onEdgesChange = useCallback(
    (changes: EdgeChange<Edge<EdgeData>>[]) => setEdges((prevState) => applyEdgeChanges(changes, prevState)),
    [setEdges]
  );

  return (
    <StoreContext.Provider
      value={{
        setEdges,
        setNodes,
        getEdges: () => edges,
        getNodes: () => nodes,
        getEdge: (id: string) => edges.find((edge) => edge.id === id),
        getNode: (id: string) => nodes.find((node) => node.id === id),
        onNodesChange,
        onEdgesChange,
      }}>
      {children}
    </StoreContext.Provider>
  );
};
