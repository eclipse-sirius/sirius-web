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
import { Edge, Node, useReactFlow, useViewport } from '@xyflow/react';
import { LayoutOptions } from 'elkjs/lib/elk-api';
import ELK, { ElkLabel, ElkNode } from 'elkjs/lib/elk.bundled';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { RawDiagram } from './layout.types';
import { headerVerticalOffset, labelHorizontalPadding, labelVerticalPadding } from './layoutParams';
import { UseArrangeAllValue } from './useArrangeAll.types';
import { useLayout } from './useLayout';
import { useSynchronizeLayoutData } from './useSynchronizeLayoutData';

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

const computeLabels = (node, viewportZoom: number): ElkLabel[] => {
  const labels: ElkLabel[] = [];
  if (node && node.data.insideLabel) {
    const label = document.querySelector<HTMLDivElement>(`[data-id="${node.data.insideLabel.id}-content"]`);
    if (label) {
      const elkLabel: ElkLabel = {
        id: node.data.insideLabel.id,
        width: label.getBoundingClientRect().width / viewportZoom + labelHorizontalPadding * 2,
        height: label.getBoundingClientRect().height / viewportZoom + labelVerticalPadding * 2,
        text: node.data.insideLabel.text,
      };
      labels.push(elkLabel);
    }
  }
  if (node && node.data.outsideLabels.BOTTOM_MIDDLE) {
    const label = document.querySelector<HTMLDivElement>(
      `[data-id="${node.data.outsideLabels.BOTTOM_MIDDLE.id}-content"]`
    );
    if (label) {
      const elkLabel: ElkLabel = {
        id: node.data.outsideLabels.BOTTOM_MIDDLE.id,
        width: label.getBoundingClientRect().width / viewportZoom + labelHorizontalPadding * 2,
        height: label.getBoundingClientRect().height / viewportZoom + labelVerticalPadding * 2,
        text: node.data.outsideLabels.BOTTOM_MIDDLE.text,
      };
      labels.push(elkLabel);
    }
  }
  return labels;
};

export const useArrangeAll = (refreshEventPayloadId: string): UseArrangeAllValue => {
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const viewport = useViewport();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

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
        labels: computeLabels(node, viewport.zoom),
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
        layoutedAllNodes = [...layoutedAllNodes, ...nodes.reverse()];
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
      const laidOutNodesWithElk: Node<NodeData, string>[] = nodes.reverse();
      laidOutNodesWithElk
        .filter((laidOutNode) => {
          const parentNode = nodes.find((node) => node.id === laidOutNode.parentNode);
          return !parentNode || !isListData(parentNode);
        })
        .forEach((laidOutNode) => (laidOutNode.data.resizedByUser = true)); // allows elk to resize nodes during layout

      const diagramToLayout: RawDiagram = {
        nodes: laidOutNodesWithElk,
        edges: getEdges(),
      };

      layout(diagramToLayout, diagramToLayout, null, (laidOutDiagram) => {
        laidOutNodesWithElk.map((node) => {
          const existingNode = laidOutDiagram.nodes.find((laidOutNode) => laidOutNode.id === node.id);
          if (existingNode) {
            return {
              ...node,
              position: existingNode.position,
              width: existingNode.width,
              height: existingNode.height,
              style: {
                ...node.style,
                width: `${existingNode.width}px`,
                height: `${existingNode.height}px`,
              },
            };
          }
          return node;
        });
        setNodes(laidOutNodesWithElk);
        setEdges(laidOutDiagram.edges);
        const finalDiagram: RawDiagram = {
          nodes: laidOutNodesWithElk,
          edges: laidOutDiagram.edges,
        };
        synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
      });
    });
  };

  return {
    arrangeAll,
  };
};
