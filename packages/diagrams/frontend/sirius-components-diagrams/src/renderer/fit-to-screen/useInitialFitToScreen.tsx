/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Edge, Node, useNodesInitialized, useReactFlow } from '@xyflow/react';
import { useEffect, useState } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseInitialFitToScreenState } from './useInitialFitToScreen.types';

const options = {
  includeHiddenNodes: false,
};

export const useInitialFitToScreen = () => {
  const nodesInitialized = useNodesInitialized(options);
  const reactFlowInstance = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const [state, setState] = useState<UseInitialFitToScreenState>({
    initialFitToScreenPerformed: false,
  });

  // We cannot perform the fit to screen directly but instead need to wait for the next render in order to retrieve the updated nodes and edges in the react flow instance
  useEffect(() => {
    if (nodesInitialized && !state.initialFitToScreenPerformed) {
      reactFlowInstance.fitView({ duration: 200, nodes: reactFlowInstance.getNodes() });
      setState({ initialFitToScreenPerformed: true });
    }
  }, [nodesInitialized, state.initialFitToScreenPerformed]);
};
