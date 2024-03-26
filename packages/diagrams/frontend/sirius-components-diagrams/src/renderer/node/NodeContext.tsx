/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo and others.
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

import { Node } from '@xyflow/react';
import React, { useState } from 'react';
import { NodeData } from '../DiagramRenderer.types';
import { NodeContextProviderProps, NodeContextProviderState, NodeContextValue } from './NodeContext.types';
const defaultValue: NodeContextValue = {
  setHoveredNode: () => {},
  hoveredNode: null,
};

export const NodeContext = React.createContext<NodeContextValue>(defaultValue);

export const NodeContextProvider = ({ children }: NodeContextProviderProps) => {
  const [state, setState] = useState<NodeContextProviderState>({
    hoveredNode: null,
  });

  const setHoveredNode = (hoveredNode: Node<NodeData> | null) =>
    setState((prevState) => ({ ...prevState, hoveredNode }));

  return (
    <NodeContext.Provider
      value={{
        hoveredNode: state.hoveredNode,
        setHoveredNode,
      }}>
      {children}
    </NodeContext.Provider>
  );
};
