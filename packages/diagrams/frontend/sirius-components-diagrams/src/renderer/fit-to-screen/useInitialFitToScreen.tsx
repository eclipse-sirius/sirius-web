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

import { Edge, Node, useNodesInitialized, useReactFlow } from '@xyflow/react';
import { useEffect, useState } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

export const useInitialFitToScreen = (emptyDiagram: boolean) => {
  const nodesInitialized = useNodesInitialized({ includeHiddenNodes: false });
  const reactFlowInstance = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const [initialFitToScreenPerformed, setInitialFitToScreenPerformed] = useState<boolean>(false);

  // We cannot perform the fit to screen directly but instead need to wait for the next render
  // in order to retrieve the updated nodes and edges in the react flow instance
  useEffect(() => {
    // @ts-ignore
    if (!document.DEACTIVATE_FIT_VIEW_FOR_CYPRESS_TESTS) {
      if (nodesInitialized && !emptyDiagram && !initialFitToScreenPerformed) {
        console.debug('fit-to-screen has been performed:' + initialFitToScreenPerformed);
        reactFlowInstance.fitView({ duration: 400, nodes: reactFlowInstance.getNodes() }).then(() => {
          setInitialFitToScreenPerformed(true);
        });
      } else if (emptyDiagram) {
        // Consider we are done to avoid an unwanted fit when adding the first node
        setInitialFitToScreenPerformed(true);
      }
    }
  }, [emptyDiagram, nodesInitialized, initialFitToScreenPerformed]);
};
