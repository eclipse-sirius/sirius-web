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

import { Dispatch, SetStateAction } from 'react';
import { Edge, EdgeChange, Node, NodeChange } from 'reactflow';
import { EdgeData, NodeData } from '../renderer/DiagramRenderer.types';

export type SetState<T> = React.Dispatch<React.SetStateAction<T>>;
export type SetNodesRefType = React.MutableRefObject<SetState<Node<NodeData>[]>> | null;
export type SetEdgesRefType = React.MutableRefObject<SetState<Edge<EdgeData>[]>> | null;

export interface StoreContextState {
  nodes: Node<NodeData>[];
  edges: Edge<EdgeData>[];
}

export type StoreContextValue = {
  getNodes: () => Node<NodeData>[];
  getNode: (id: string) => Node<NodeData> | undefined;
  setNodes: Dispatch<SetStateAction<Node<NodeData>[]>>;
  getEdges: () => Edge<EdgeData>[];
  getEdge: (id: string) => Edge<EdgeData> | undefined;
  setEdges: Dispatch<SetStateAction<Edge<EdgeData>[]>>;
  onNodesChange: (changes: NodeChange[]) => void;
  onEdgesChange: (changes: EdgeChange[]) => void;
};

export interface StoreContextProviderProps {
  children: React.ReactNode;
}
