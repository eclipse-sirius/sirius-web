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

import ELK, { ElkExtendedEdge, ElkLabel, ElkNode, LayoutOptions } from 'elkjs/lib/elk.bundled.js';
import { Edge, Node } from 'reactflow';
import { RectangularNodeData } from './RectangularNode.types';

const elk = new ELK();

export const performLayout = (
  nodes: Node[],
  edges: Edge[],
  layoutOptions: LayoutOptions
): Promise<{ nodes: Node[] }> => {
  const graph: ElkNode = {
    id: 'root',
    layoutOptions,
    children: [],
    edges: [],
  };

  const hiddenNodes = nodes.filter((node) => node.hidden);
  const visibleNodes = nodes.filter((node) => !node.hidden);
  const nodeId2Node = new Map<string, Node>();
  visibleNodes.forEach((node) => nodeId2Node.set(node.id, node));

  const nodeId2ElkNode = new Map<String, ElkNode>();
  visibleNodes.forEach((node) => {
    const elkNode: ElkNode = {
      id: node.id,
      children: [],
      labels: [],
      layoutOptions: {
        'nodeLabels.placement': '[H_CENTER, V_TOP, INSIDE]',
      },
    };

    if (node.type === 'rectangularNode') {
      const rectangularNodeData: RectangularNodeData = node.data as RectangularNodeData;

      const label = document.querySelector<HTMLDivElement>(`[data-id="${rectangularNodeData.label.id}"]`);
      const elkLabel: ElkLabel = {
        width: label.getBoundingClientRect().width,
        height: label.getBoundingClientRect().height,
        text: rectangularNodeData.label.text,
      };

      elkNode.labels.push(elkLabel);
    }

    const element = document.querySelector(`[data-id="${node.id}"]`);
    if (element) {
      elkNode.width = element.clientWidth;
      elkNode.height = element.clientHeight;
    }

    nodeId2ElkNode.set(elkNode.id, elkNode);
  });

  visibleNodes.forEach((node) => {
    if (graph.children) {
      if (!!node.parentNode) {
        const elknodeChild = nodeId2ElkNode.get(node.id);
        const elkNodeParent = nodeId2ElkNode.get(node.parentNode);
        if (elkNodeParent && elkNodeParent.children) {
          elkNodeParent.children.push(elknodeChild);
        }
      } else {
        const elkNodeRoot = nodeId2ElkNode.get(node.id);
        graph.children.push(elkNodeRoot);
      }
    }
  });

  edges
    .filter((edge) => !edge.hidden)
    .forEach((edge) => {
      if (graph.edges) {
        const elkEdge: ElkExtendedEdge = {
          id: edge.id,
          sources: [edge.source],
          targets: [edge.target],
        };
        graph.edges.push(elkEdge);
      }
    });

  return elk
    .layout(graph)
    .then((laidoutGraph) => elkToReactFlow(laidoutGraph.children ?? [], nodeId2Node))
    .then((laidoutNodes) => {
      return {
        nodes: [...laidoutNodes, ...hiddenNodes],
      };
    });
};

const elkToReactFlow = (elkNodes: ElkNode[], nodeId2Node: Map<string, Node>): Node[] => {
  const nodes: Node[] = [];
  elkNodes.forEach((elkNode) => {
    const node = nodeId2Node.get(elkNode.id);
    if (node) {
      node.position.x = elkNode.x ?? 0;
      node.position.y = elkNode.y ?? 0;
      node.width = elkNode.width ?? 150;
      node.height = elkNode.height ?? 70;
      if (node.style) {
        if (node.style.width) {
          node.style.width = `${node.width}px`;
        }
        if (node.style.height) {
          node.style.height = `${node.height}px`;
        }
      }
      nodes.push(node);
    }
    if (elkNode.children.length > 0) {
      const laidoutChildren = elkToReactFlow(elkNode.children, nodeId2Node);
      nodes.push(...laidoutChildren);
    }
  });
  return nodes;
};
