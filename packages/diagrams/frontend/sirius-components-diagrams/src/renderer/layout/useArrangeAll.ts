/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Edge, Node, useReactFlow, useStoreApi } from '@xyflow/react';
import { LayoutOptions } from 'elkjs/lib/elk-api';
import ELK, { ElkLabel, ElkNode } from 'elkjs/lib/elk.bundled';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useFitView } from '../fit-to-screen/useFitView';
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
  for (const node of nodes.filter((n) => !n.hidden)) {
    const parentNodeId: string = node.parentId ?? 'root';
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
    'elk.layered.spacing.nodeNodeBetweenLayers': '80',
    'layering.strategy': 'NETWORK_SIMPLEX',
    'elk.spacing.componentComponent': '60',
    'elk.spacing.nodeNode': '80',
    'elk.direction': `${direction}`,
    'elk.layered.spacing.edgeNodeBetweenLayers': '80',
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
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { diagramDescription } = useDiagramDescription();
  const { resolveNodeOverlap } = useOverlap();
  const { addErrorMessage } = useMultiToast();
  const { fitView } = useFitView();

  const elk = new ELK();

  const getDimension = (value: unknown, fallback?: number | null): number => {
    if (typeof value === 'number' && !Number.isNaN(value)) {
      return value;
    }
    if (typeof value === 'string') {
      const parsed = parseFloat(value);
      if (!Number.isNaN(parsed)) {
        return parsed;
      }
    }
    if (typeof fallback === 'number' && !Number.isNaN(fallback)) {
      return fallback;
    }
    return 0;
  };

  const getELKLayout = async (
    nodes,
    edges,
    options: LayoutOptions = {},
    parentNodeId: string,
    headerVerticalFootprint: number
  ): Promise<any> => {
    const zoom = store.getState().transform[2];
    const graph: ElkNode = {
      id: parentNodeId,
      layoutOptions: options,
      children: nodes.map((node) => {
        const width = getDimension(node.width, node.data.defaultWidth);
        const height = getDimension(node.height, node.data.defaultHeight);

        const childLayoutOptions: LayoutOptions | undefined = node.data.resizedByUser
          ? {
              'org.eclipse.elk.nodeSize.constraints': 'FIXED',
              'org.eclipse.elk.nodeSize.minimum': `${Math.max(width, 1)}, ${Math.max(height, 1)}`,
              'org.eclipse.elk.nodeSize.maximum': `${Math.max(width, 1)}, ${Math.max(height, 1)}`,
            }
          : undefined;

        if (node.data.resizedByUser) {
          console.log('[arrangeAll] node marked resizedByUser', {
            id: node.id,
            descriptionId: node.data.descriptionId,
            width,
            height,
            layoutOptions: childLayoutOptions,
          });
        }

        return {
          ...node,
          width,
          height,
          labels: computeLabels(node, zoom, reactFlowWrapper),
          ...(childLayoutOptions ? { layoutOptions: childLayoutOptions } : {}),
        };
      }),
      edges,
    };
    console.log('[arrangeAll] ELK graph children sample', graph.children?.slice(0, 5));
    try {
      const layoutedGraph = await elk.layout(graph);
      console.log(
        '[arrangeAll] ELK layout result',
        layoutedGraph?.children?.map((child) => ({ id: child.id, width: child.width, height: child.height })).slice(0, 5)
      );
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
      addErrorMessage('An error occurred during the arrange all elements ');
      return [];
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
        layoutedAllNodes = [...layoutedAllNodes, ...nodes.reverse()];
        continue;
      }
      const zoom = store.getState().transform[2];
      const headerVerticalFootprint: number = computeHeaderVerticalFootprint(parentNode, zoom, reactFlowWrapper);
      const subGroupNodes: Node<NodeData>[] = nodes
        .filter((node) => !node.data.isBorderNode)
        .map((node) => {
          return parentNodeWithNewSize.find((layoutNode) => layoutNode.id === node.id) ?? node;
        });
      await getELKLayout(
        subGroupNodes,
        subGroupEdges,
        elkOptions(diagramDescription.arrangeLayoutDirection),
        parentNodeId,
        headerVerticalFootprint
      ).then(({ nodes: layoutedSubNodes, layoutReturn }) => {
        const parentNode = allNodes.find((node) => node.id === parentNodeId);
        if (layoutReturn) {
          if (parentNode) {
            const existingWidth = getDimension(parentNode.width, parentNode.data.defaultWidth);
            const existingHeight = getDimension(parentNode.height, parentNode.data.defaultHeight);
            const layoutWidth = getDimension(layoutReturn.width, existingWidth);
            const layoutHeight = getDimension(layoutReturn.height, existingHeight);

            if (parentNode.data.resizedByUser) {
              parentNode.width = existingWidth > 0 ? existingWidth : layoutWidth;
              parentNode.height = Math.max(existingHeight, layoutHeight + headerVerticalFootprint);
              console.log('[arrangeAll] preserving parent size', {
                id: parentNode.id,
                width: parentNode.width,
                existingWidth,
                layoutWidth,
              });
            } else {
              parentNode.width = layoutWidth;
              parentNode.height = layoutHeight + headerVerticalFootprint;
            }

            parentNode.style = {
              ...parentNode.style,
              width: `${parentNode.width}px`,
              height: `${parentNode.height}px`,
            };
            parentNodeWithNewSize.push(parentNode);
          }
          layoutedAllNodes = [
            ...layoutedAllNodes,
            ...layoutedSubNodes,
            ...nodes.filter((node) => node.data.isBorderNode),
          ];
        } else {
          layoutedAllNodes = nodes;
        }
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
        const parentNode = nodes.find((node) => node.id === laidOutNode.parentId);
        return !parentNode || !isListData(parentNode);
      });

      const laidOutMovedNodeIds = laidOutNodesWithElk
        .filter((node) => !node.data.isBorderNode && !node.data.pinned)
        .map((node) => node.id);
      const edges = getEdges();
      edges
        .filter((edge) => laidOutMovedNodeIds.includes(edge.source) || laidOutMovedNodeIds.includes(edge.target))
        .forEach((edge: Edge<EdgeData, string>) => {
          if (edge.data?.bendingPoints) {
            edge.data.bendingPoints = null;
          }
        });

      const diagramToLayout: RawDiagram = {
        nodes: laidOutNodesWithElk,
        edges: edges,
      };
      const layoutPromise = new Promise<void>((resolve) => {
        layout(diagramToLayout, diagramToLayout, null, (laidOutDiagram) => {
          const overlapFreeLaidOutNodes: Node<NodeData, string>[] = resolveNodeOverlap(
            laidOutDiagram.nodes.filter((n) => !n.data.isBorderNode),
            'horizontal'
          ) as Node<NodeData, DiagramNodeType>[];
          laidOutNodesWithElk.map((node) => {
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
          fitView({ duration: 200, nodes: laidOutNodesWithElk });
          synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
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
