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
import { ListNodeData } from '../../node/ListNode.types';
import { UseElkLayoutValue } from './useElkLayout.types';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

const reverseOrderMap = <K, V>(map: Map<K, V>): Map<K, V> => {
  const reversedNodes = Array.from(map.entries()).reverse();
  return new Map<K, V>(reversedNodes);
};

const getSubNodes = (nodes: Node<NodeData, string>[]): Map<string, Node<NodeData, string>[]> => {
  const subNodes: Map<string, Node<NodeData, string>[]> = new Map<string, Node<NodeData, string>[]>();
  for (const node of nodes.filter((n) => !n.hidden)) {
    const parentNodeId: string = node.parentId ?? 'root';
    if (!subNodes.has(parentNodeId)) {
      subNodes.set(parentNodeId, []);
    }
    subNodes.get(parentNodeId)?.push(node);
  }
  return subNodes;
};

const computeHeaderVerticalFootprint = (node: Node<NodeData, string> | undefined): number => {
  if (node && node.data.insideLabel?.isHeader) {
    return node.data.insideLabel.height;
  }
  return 0;
};

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

export const useElkLayout = (): UseElkLayoutValue => {
  const { addErrorMessage } = useMultiToast();
  const elk = new ELK();

  const getELKLayout = async (
    nodes: Node<NodeData>[],
    edges: Edge<EdgeData>[],
    options: LayoutOptions = {},
    parentNodeId: string,
    headerVerticalFootprint: number
  ): Promise<any> => {
    const graph: ElkNode = {
      id: parentNodeId,
      layoutOptions: options,
      children: nodes.map((node) => ({
        labels: computeLabels(node),
        ...node,
      })),
      edges: edges
        .filter(
          (edge) => nodes.some((node) => node.id === edge.source) && nodes.some((node) => node.id === edge.target)
        )
        .map((edge) => {
          return {
            id: edge.id,
            sources: [edge.source],
            targets: [edge.target],
          };
        }),
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
                position: { x: node.x ?? 0, y: (node.y ?? 0) + headerVerticalFootprint },
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

  const applyElkOnSubNodes = async (
    subNodes: Map<string, Node<NodeData, string>[]>,
    allNodes: Node<NodeData, string>[],
    edges: Edge<EdgeData>[],
    layoutOptions: LayoutOptions
  ): Promise<Node<NodeData, string>[]> => {
    let layoutAllNodes: Node<NodeData, string>[] = [];
    const parentNodeWithNewSize: Node<NodeData>[] = [];
    for (const [parentNodeId, nodes] of subNodes) {
      const parentNode: Node<NodeData, string> | undefined = allNodes.find((node) => node.id === parentNodeId);
      const subGroupEdges: Edge<EdgeData>[] = [];
      edges.forEach((edge) => {
        const isTargetInside = nodes.some((node) => node.id === edge.target);
        const isSourceInside = nodes.some((node) => node.id === edge.source);
        if (isTargetInside && isSourceInside) {
          subGroupEdges.push(edge);
        }
        if (isTargetInside && !isSourceInside) {
          edge.target = parentNodeId;
        }
        if (!isTargetInside && isSourceInside) {
          edge.source = parentNodeId;
        }
      });
      if ((parentNode && isListData(parentNode)) || nodes.every((node) => node.data.isBorderNode)) {
        // No elk layout for child of container list or for border node
        layoutAllNodes = [...layoutAllNodes, ...nodes.reverse()];
        continue;
      }
      const headerVerticalFootprint: number = computeHeaderVerticalFootprint(parentNode);
      const subGroupNodes: Node<NodeData>[] = nodes
        .filter((node) => !node.data.isBorderNode)
        .map((node) => {
          return parentNodeWithNewSize.find((layoutNode) => layoutNode.id === node.id) ?? node;
        });
      await getELKLayout(subGroupNodes, subGroupEdges, layoutOptions, parentNodeId, headerVerticalFootprint).then(
        ({ nodes: layoutSubNodes, layoutReturn }) => {
          const parentNode = allNodes.find((node) => node.id === parentNodeId);
          if (layoutReturn) {
            if (parentNode) {
              parentNode.width = layoutReturn.width;
              parentNode.height = layoutReturn.height + headerVerticalFootprint;
              parentNode.style = { width: `${parentNode.width}px`, height: `${parentNode.height}px` };
              parentNodeWithNewSize.push(parentNode);
            }
            layoutAllNodes = [...layoutAllNodes, ...layoutSubNodes, ...nodes.filter((node) => node.data.isBorderNode)];
          } else {
            layoutAllNodes = nodes;
          }
        }
      );
    }
    return layoutAllNodes;
  };

  const elkLayout = async (
    nodes: Node<NodeData, string>[],
    edges: Edge<EdgeData>[],
    layoutOptions: LayoutOptions
  ): Promise<Node<NodeData, string>[]> => {
    const subNodes: Map<string, Node<NodeData, string>[]> = reverseOrderMap(getSubNodes(nodes));
    const layoutNodes = await applyElkOnSubNodes(subNodes, nodes, edges, layoutOptions);
    return layoutNodes.reverse();
  };

  return {
    elkLayout,
  };
};
