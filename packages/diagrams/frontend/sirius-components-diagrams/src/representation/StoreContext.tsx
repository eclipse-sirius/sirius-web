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

import React, { useCallback, useState } from 'react';
import { Edge, EdgeChange, Node, NodeChange, applyEdgeChanges, applyNodeChanges } from 'reactflow';
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
  const [nodes, setNodes] = useState<Node[]>([]);
  const [edges, setEdges] = useState<Edge[]>([]);

  const onNodesChange = useCallback(
    (changes: NodeChange[]) => setNodes((prevState) => applyNodeChanges(changes, prevState)),
    [setEdges]
  );

  const onEdgesChange = useCallback(
    (changes: EdgeChange[]) => setEdges((prevState) => applyEdgeChanges(changes, prevState)),
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
