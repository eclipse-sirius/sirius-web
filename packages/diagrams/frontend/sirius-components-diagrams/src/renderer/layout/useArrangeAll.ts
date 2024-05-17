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
import { LayoutOptions } from 'elkjs/lib/elk-api';
import ELK, { ElkLabel, ElkNode } from 'elkjs/lib/elk.bundled';
import { useContext } from 'react';
import { Edge, Node, useReactFlow, useViewport } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { useOverlap } from '../overlap/useOverlap';
import { RawDiagram } from './layout.types';
import { labelHorizontalPadding, labelVerticalPadding } from './layoutParams';
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

const elkOptions = (direction: string) => {
  return {
    'elk.algorithm': 'layered',
    'elk.layered.spacing.nodeNodeBetweenLayers': '100',
    'layering.strategy': 'NETWORK_SIMPLEX',
    'elk.spacing.nodeNode': '100',
    'elk.direction': `${direction}`,
    'elk.layered.spacing.edgeNodeBetweenLayers': '40',
    'elk.layered.nodePlacement.strategy': 'NETWORK_SIMPLEX',
  };
};

const computeHeaderVerticalFootprint = (
  node,
  viewportZoom: number,
  reactFlowWrapper: React.MutableRefObject<HTMLDivElement | null>
): number => {
  if (node && node.data.insideLabel?.isHeader) {
    const label = reactFlowWrapper?.current?.querySelector<HTMLDivElement>(
      `[data-id="${node.data.insideLabel.id}-content"]`
    );
    if (label) {
      return label.getBoundingClientRect().height / viewportZoom + labelVerticalPadding * 2;
    }
  }
  return 0;
};

const computeLabels = (
  node,
  viewportZoom: number,
  reactFlowWrapper: React.MutableRefObject<HTMLDivElement | null>
): ElkLabel[] => {
  const labels: ElkLabel[] = [];
  if (node && node.data.insideLabel) {
    const label = reactFlowWrapper?.current?.querySelector<HTMLDivElement>(
      `[data-id="${node.data.insideLabel.id}-content"]`
    );
    if (label) {
      const elkLabel: ElkLabel = {
        id: node.data.insideLabel.id,
        width: label.getBoundingClientRect().width / viewportZoom + labelHorizontalPadding * 2,
        height: label.getBoundingClientRect().height / viewportZoom + labelVerticalPadding * 2,
        text: node.data.insideLabel.text,
        x: 0,
        y: 0,
      };
      labels.push(elkLabel);
    }
  }
  if (node && node.data.outsideLabels.BOTTOM_MIDDLE) {
    const label = reactFlowWrapper?.current?.querySelector<HTMLDivElement>(
      `[data-id="${node.data.outsideLabels.BOTTOM_MIDDLE.id}-content"]`
    );
    if (label) {
      const elkLabel: ElkLabel = {
        id: node.data.outsideLabels.BOTTOM_MIDDLE.id,
        width: label.getBoundingClientRect().width / viewportZoom + labelHorizontalPadding * 2,
        height: label.getBoundingClientRect().height / viewportZoom + labelVerticalPadding * 2,
        text: node.data.outsideLabels.BOTTOM_MIDDLE.text,
        x: 0,
        y: node.height,
      };
      labels.push(elkLabel);
    }
  }
  return labels;
};

export const useArrangeAll = (reactFlowWrapper: React.MutableRefObject<HTMLDivElement | null>): UseArrangeAllValue => {
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<NodeData, EdgeData>();
  const viewport = useViewport();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { diagramDescription } = useDiagramDescription();
  const { refreshEventPayloadId } = useContext<DiagramContextValue>(DiagramContext);
  const { resolveNodeOverlap } = useOverlap();

  const elk = new ELK();

  const getELKLayout = async (
    nodes,
    edges,
    options: LayoutOptions = {},
    parentNodeId: string,
    headerVerticalFootprint: number
  ): Promise<any> => {
    const graph: ElkNode = {
      id: parentNodeId,
      layoutOptions: options,
      children: nodes.map((node) => ({
        labels: computeLabels(node, viewport.zoom, reactFlowWrapper),
        ...node,
      })),
      edges,
    };
    try {
      const layoutedGraph = await elk.layout(graph);
      return {
        nodes:
          layoutedGraph?.children?.map((node) => {
            const originalNode = nodes.find((node_1) => node_1.id === node.id);
            if (originalNode && originalNode.data.pinned) {
              return { ...node };
            } else {
              return {
                ...node,
                position: { x: node.x ?? 0, y: (node.y ?? 0) + headerVerticalFootprint },
              };
            }
          }) ?? [],
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
      if ((parentNode && isListData(parentNode)) || nodes.every((node) => node.data.isBorderNode)) {
        // No elk layout for child of container list or for border node
        layoutedAllNodes = [...layoutedAllNodes, ...nodes.reverse()];
        continue;
      }
      const headerVerticalFootprint: number = computeHeaderVerticalFootprint(
        parentNode,
        viewport.zoom,
        reactFlowWrapper
      );
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
      await getELKLayout(
        subGroupNodes,
        subGroupEdges,
        elkOptions(diagramDescription.arrangeLayoutDirection),
        parentNodeId,
        headerVerticalFootprint
      ).then(({ nodes: layoutedSubNodes, layoutReturn }) => {
        const parentNode = allNodes.find((node) => node.id === parentNodeId);
        if (parentNode) {
          parentNode.width = layoutReturn.width;
          parentNode.height = layoutReturn.height + headerVerticalFootprint;
          parentNode.style = { width: `${parentNode.width}px`, height: `${parentNode.height}px` };
          parentNodeWithNewSize.push(parentNode);
        }
        layoutedAllNodes = [
          ...layoutedAllNodes,
          ...layoutedSubNodes,
          ...nodes.filter((node) => node.data.isBorderNode),
        ];
      });
    }
    return layoutedAllNodes;
  };

  const arrangeAll = async (): Promise<void> => {
    const nodes: Node<NodeData, string>[] = [...getNodes()] as Node<NodeData, DiagramNodeType>[];
    const subNodes: Map<string, Node<NodeData, string>[]> = reverseOrdreMap(getSubNodes(nodes));
    await applyElkOnSubNodes(subNodes, nodes).then(async (nodes: Node<NodeData, string>[]) => {
      const laidOutNodesWithElk: Node<NodeData, string>[] = nodes.reverse();
      laidOutNodesWithElk.filter((laidOutNode) => {
        const parentNode = nodes.find((node) => node.id === laidOutNode.parentNode);
        return !parentNode || !isListData(parentNode);
      });

      const diagramToLayout: RawDiagram = {
        nodes: laidOutNodesWithElk,
        edges: getEdges(),
      };
      const layoutPromise = new Promise<void>((resolve) => {
        layout(diagramToLayout, diagramToLayout, null, (laidOutDiagram) => {
          laidOutNodesWithElk.map((node) => {
            const overlapFreeLaidOutNodes: Node<NodeData, string>[] = resolveNodeOverlap(
              laidOutDiagram.nodes,
              'horizontal'
            ) as Node<NodeData, DiagramNodeType>[];
            const existingNode = overlapFreeLaidOutNodes.find((laidOutNode) => laidOutNode.id === node.id);
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
          resolve();
        });
      });
      await layoutPromise;
    });
  };

  return {
    arrangeAll,
  };
};
