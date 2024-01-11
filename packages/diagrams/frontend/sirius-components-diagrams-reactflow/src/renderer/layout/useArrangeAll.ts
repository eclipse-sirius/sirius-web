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
import { UseArrangeAllValue } from './useArrangeAll.types';
import { Edge, Node, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ListNodeData } from '../node/ListNode.types';
import ELK, { ElkNode } from 'elkjs/lib/elk.bundled';
import { LayoutOptions } from 'elkjs/lib/elk-api';
import { headerVerticalOffset } from './layoutParams';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

function reverseOrdreMap<K, V>(map: Map<K, V>): Map<K, V> {
  const reversedNodes = Array.from(map.entries()).reverse();
  return new Map<K, V>(reversedNodes);
}

const getSubNodes = (nodes: Node<NodeData, string>[]): Map<string, Node<NodeData, string>[]> => {
  const subNodes: Map<string, Node<NodeData, string>[]> = new Map<string, Node<NodeData, string>[]>();
  for (const node of nodes) {
    const parentNodeId: string = node.parentNode ?? 'root';
    if (!subNodes.has(parentNodeId)) {
      subNodes.set(parentNodeId, []);
    }
    subNodes.get(parentNodeId)?.push(node);
  }
  return subNodes;
};

const elkOptions = {
  'elk.algorithm': 'layered',
  'elk.layered.spacing.nodeNodeBetweenLayers': '100',
  'layering.strategy': 'NETWORK_SIMPLEX',
  'elk.spacing.nodeNode': '100',
  'elk.direction': 'RIGHT',
  'elk.layered.spacing.edgeNodeBetweenLayers': '40',
};

export const useArrangeAll = (): UseArrangeAllValue => {
  const { getNodes, getEdges, setNodes } = useReactFlow<NodeData, EdgeData>();
  const elk = new ELK();

  const getELKLayout = async (
    nodes,
    edges,
    options: LayoutOptions = {},
    parentNodeId: string,
    withHeader: boolean
  ): Promise<any> => {
    const graph: ElkNode = {
      id: parentNodeId,
      layoutOptions: options,
      children: nodes.map((node) => ({
        ...node,
      })),
      edges,
    };
    try {
      const layoutedGraph = await elk.layout(graph);
      return {
        nodes:
          layoutedGraph?.children?.map((node_1) => ({
            ...node_1,
            position: { x: node_1.x ?? 0, y: (node_1.y ?? 0) + (withHeader ? headerVerticalOffset : 0) },
          })) ?? [],
        layoutReturn: layoutedGraph,
      };
    } catch (message) {
      return console.error(message);
    }
  };

  const applyElkOnSubNodes = async (
    subNodes: Map<string, Node<NodeData, string>[]>,
    allNodes: Node<NodeData, string>[]
  ): Promise<Node<NodeData, string>[]> => {
    let layoutedAllNodes: Node<NodeData, string>[] = [];
    const parentNodeWithNewSize: Node<NodeData>[] = [];
    const edges: Edge<EdgeData>[] = getEdges();
    for (const [parentNodeId, nodes] of subNodes) {
      const parentNode = allNodes.find((node) => node.id === parentNodeId);
      if (parentNode && isListData(parentNode)) {
        // No elk layout for child of container list
        layoutedAllNodes = [...layoutedAllNodes, ...nodes];
        continue;
      }
      const withHeader: boolean = parentNode?.data.insideLabel?.isHeader ?? false;
      const subGroupNodes: Node<NodeData>[] = nodes
        .filter((node) => !node.data.isBorderNode)
        .map((node) => {
          return parentNodeWithNewSize.find((layoutNode) => layoutNode.id === node.id) ?? node;
        });
      const subGroupEdges: Edge<EdgeData>[] = [];
      edges.forEach((edge) => {
        const isTargetInside = subGroupNodes.some((node) => node.id === edge.target);
        const isSourceInside = subGroupNodes.some((node) => node.id === edge.source);
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
      await getELKLayout(subGroupNodes, subGroupEdges, elkOptions, parentNodeId, withHeader).then(
        ({ nodes: layoutedSubNodes, layoutReturn }) => {
          const parentNode = allNodes.find((node) => node.id === parentNodeId);
          if (parentNode) {
            parentNode.width = layoutReturn.width;
            parentNode.height = layoutReturn.height + (withHeader ? headerVerticalOffset : 0);
            parentNode.style = { width: `${parentNode.width}px`, height: `${parentNode.height}px` };
            parentNodeWithNewSize.push(parentNode);
          }
          layoutedAllNodes = [
            ...layoutedAllNodes,
            ...layoutedSubNodes,
            ...nodes.filter((node) => node.data.isBorderNode),
          ];
        }
      );
    }
    return layoutedAllNodes;
  };

  const arrangeAll = (): void => {
    const nodes: Node<NodeData, string>[] = [...getNodes()] as Node<NodeData, DiagramNodeType>[];
    const subNodes: Map<string, Node<NodeData, string>[]> = reverseOrdreMap(getSubNodes(nodes));
    applyElkOnSubNodes(subNodes, nodes).then((nodes: Node<NodeData, string>[]) => {
      const reversedOrder: Node<NodeData, string>[] = nodes.reverse();
      setNodes(reversedOrder);
    });
  };

  return {
    arrangeAll,
  };
};
