/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { useSelection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useNodesInitialized, useReactFlow } from '@xyflow/react';
import { useEffect, useState } from 'react';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useApplyWorkbenchSelectionToDiagram } from '../selection/useApplyWorkbenchSelectionToDiagram';
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
  const { selection } = useSelection();
  const { fitView } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { getNodes, setNodes, getEdges, setEdges } = useStore();

  console.debug('fit-to-screen has been performed:' + state.initialFitToScreenPerformed);
  // We cannot perform the fit to screen directly but instead need to wait for the next render in order to retrieve the updated nodes and edges in the react flow instance
  useEffect(() => {
    if (nodesInitialized && !state.initialFitToScreenPerformed) {
      if (selection.entries.length === 0) {
        reactFlowInstance.fitView({ duration: 200, nodes: reactFlowInstance.getNodes() }).then(() => {
          setState({ initialFitToScreenPerformed: true });
        });
      } else {
        useApplyWorkbenchSelectionToDiagram(selection, getNodes, setNodes, getEdges, setEdges, fitView);
      }
    }
  }, [nodesInitialized, state.initialFitToScreenPerformed, selection]);
};
