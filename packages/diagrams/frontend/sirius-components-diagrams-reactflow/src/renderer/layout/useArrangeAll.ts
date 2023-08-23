/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { Edge, Node, useReactFlow } from 'reactflow';
import { UseArrangeAllValue } from './useArrangeAll.types';
import { useLayout } from './useLayout';

export const useArrangeAll = (): UseArrangeAllValue => {
  const { autoLayout } = useLayout();
  const { setNodes, getZoom } = useReactFlow();

  const layoutDiagram = (nodes: Node[], edges: Edge[]) => {
    autoLayout(nodes, edges, getZoom()).then(({ nodes }) => {
      setNodes(nodes);
    });
  };

  return {
    onArrangeAll: layoutDiagram,
  };
};
