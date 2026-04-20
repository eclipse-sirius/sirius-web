/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node } from '@xyflow/react';
import { LayoutOptions } from 'elkjs/lib/elk-api';
import ELK, { ElkLabel, ElkNode } from 'elkjs/lib/elk.bundled';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { isEdgeAnchorNode } from '../../node/EdgeAnchorNode.types';
import { UseElkLayoutValue } from './useElkLayout.types';

const computeLabels = (node: Node<NodeData, string>): ElkLabel[] => {
  const labels: ElkLabel[] = [];
  if (node && node.data.insideLabel) {
    const elkLabel: ElkLabel = {
      id: node.data.insideLabel.id,
      width: node.data.insideLabel.width,
      height: node.data.insideLabel.height,
      text: node.data.insideLabel.text,
      x: 0,
      y: 0,
    };
    labels.push(elkLabel);
  }
  if (node && node.data.outsideLabels.BOTTOM_MIDDLE) {
    const elkLabel: ElkLabel = {
      id: node.data.outsideLabels.BOTTOM_MIDDLE.id,
      width: node.data.outsideLabels.BOTTOM_MIDDLE.width,
      height: node.data.outsideLabels.BOTTOM_MIDDLE.height,
      text: node.data.outsideLabels.BOTTOM_MIDDLE.text,
      x: 0,
      y: node.height,
    };
    labels.push(elkLabel);
  }
  return labels;
};
const buildElkNodeAndChildren = (node: Node<NodeData>, nodes: Node<NodeData>[], options: LayoutOptions): ElkNode => {
  const elkNodeChildren: ElkNode[] = nodes
    .filter((n) => n.parentId === node.id)
    .map((n) => buildElkNodeAndChildren(n, nodes, options));

  return {
    labels: computeLabels(node),
    children: elkNodeChildren,
    ...node,
    layoutOptions: options,
  };
};

const convertToElkGraph = (nodes: Node<NodeData>[], options: LayoutOptions): ElkNode[] => {
  return nodes.filter((n) => !n.parentId).map((node) => buildElkNodeAndChildren(node, nodes, options));
};

export const useElkLayout = (): UseElkLayoutValue => {
  const { addErrorMessage } = useMultiToast();
  const elk = new ELK();

  const getELKLayout = async (
    nodes: Node<NodeData>[],
    edges: Edge<EdgeData>[],
    options: LayoutOptions = {}
  ): Promise<any> => {
    const graph: ElkNode = {
      id: 'root',
      children: convertToElkGraph(nodes, options),
      edges: edges
        .filter(
          (edge) => nodes.some((node) => node.id === edge.source) && nodes.some((node) => node.id === edge.target)
        )
        .map((edge) => ({
          id: edge.id,
          sources: [edge.source],
          targets: [edge.target],
        })),
      layoutOptions: options,
    };
    try {
      const layoutGraph = await elk.layout(graph);
      return {
        nodes:
          layoutGraph?.children?.map((node) => {
            const originalNode = nodes.find((n) => n.id === node.id);
            if (originalNode && originalNode.data.pinned) {
              return { ...node };
            } else {
              return {
                ...node,
                position: { x: node.x ?? 0, y: node.y ?? 0 },
              };
            }
          }) ?? [],
        layoutReturn: layoutGraph,
      };
    } catch (message) {
      addErrorMessage('An error occurred during the arrange all elements ');
      return [];
    }
  };

  const applyElkOnNodes = async (
    nodes: Node<NodeData, string>[],
    edges: Edge<EdgeData>[],
    layoutOptions: LayoutOptions
  ): Promise<Node<NodeData, string>[]> => {
    let layoutAllNodes: Node<NodeData, string>[] = [];
    await getELKLayout(
      nodes.filter((node) => !node.hidden && !isEdgeAnchorNode(node)),
      edges,
      layoutOptions
    ).then(({ nodes: elkNodes }) => {
      const elkNodesMap = new Map<string, ElkNode>();

      const traverse = (node: ElkNode) => {
        elkNodesMap.set(node.id, node);
        if (node.children) {
          node.children.forEach((child) => traverse(child));
        }
      };

      elkNodes.forEach((rootNode: ElkNode) => traverse(rootNode));
      layoutAllNodes = nodes.map((node) => {
        const elkNode = elkNodesMap.get(node.id);
        if (elkNode) {
          return {
            ...node,
            position: {
              x: elkNode.x ?? node.position.x,
              y: elkNode.y ?? node.position.y,
            },
            width: elkNode.width,
            height: elkNode.height,
          };
        }
        return node;
      });
    });
    return layoutAllNodes;
  };

  const elkLayout = async (
    nodes: Node<NodeData, string>[],
    edges: Edge<EdgeData>[],
    layoutOptions: LayoutOptions
  ): Promise<Node<NodeData, string>[]> => {
    return await applyElkOnNodes(nodes, edges, layoutOptions);
  };

  return {
    elkLayout,
  };
};
