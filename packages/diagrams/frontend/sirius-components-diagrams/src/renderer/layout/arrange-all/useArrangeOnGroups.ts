/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { Edge, Node } from '@xyflow/react';
import { LayoutOptions } from 'elkjs/lib/elk-api';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { RawDiagram } from '../layout.types';
import { GQLLayoutGroup } from './useLayoutGroups.types';

export const useArrangeOnGroups = async (
  nodes: Node<NodeData>[],
  edges: Edge<EdgeData>[],
  groups: GQLLayoutGroup[],
  layoutOptions: LayoutOptions,
  elkLayout: (nodes: Node<NodeData>[], edges: Edge<EdgeData>[], options: LayoutOptions) => Promise<RawDiagram>
): Promise<RawDiagram> => {
  if (groups && groups.length > 0) {
    const group = groups[0];
    if (group && group.nodeIds.length > 0) {
      const nodesToLayout = nodes.filter((node) => group.nodeIds.includes(node.id));
      const edgesToLayout = edges.filter(
        (edge) => group.nodeIds.includes(edge.source) && group.nodeIds.includes(edge.target)
      );
      return await elkLayout(nodesToLayout, edgesToLayout, layoutOptions);
    }
  }
  return await elkLayout(nodes, edges, layoutOptions);
};
