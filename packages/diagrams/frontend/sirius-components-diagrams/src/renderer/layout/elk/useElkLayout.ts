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
import { Edge, Node, Position, XYPosition } from '@xyflow/react';
import { LayoutOptions } from 'elkjs/lib/elk-api';
import ELK, { ElkLabel, ElkNode } from 'elkjs/lib/elk.bundled';
import { BorderNodePosition, EdgeData, NodeData } from '../../DiagramRenderer.types';
import { ConnectionHandle } from '../../handles/ConnectionHandles.types';
import { isEdgeAnchorNode } from '../../node/EdgeAnchorNode.types';
import { DiagramNodeType } from '../../node/NodeTypes.types';
import { RawDiagram } from '../layout.types';
import { UseElkLayoutValue } from './useElkLayout.types';

const computeLabels = (node: Node<NodeData, DiagramNodeType>): ElkLabel[] => {
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

const convertHandleAbsolutePositionToReactFlowPosition = (
  elkHandleAbsolutePosition: XYPosition,
  x: number,
  y: number,
  width: number,
  height: number
): { position: Position; XYPosition: XYPosition } => {
  const { x: handleX, y: handleY } = elkHandleAbsolutePosition;
  const nodeLeft = x;
  const nodeRight = x + width;
  const nodeTop = y;
  const nodeBottom = y + height;

  const distanceToLeft = Math.abs(handleX - nodeLeft);
  const distanceToRight = Math.abs(handleX - nodeRight);
  const distanceToTop = Math.abs(handleY - nodeTop);
  const distanceToBottom = Math.abs(handleY - nodeBottom);

  const minDistance = Math.min(distanceToLeft, distanceToRight, distanceToTop, distanceToBottom);

  let position: Position;
  let relativeXYPosition: XYPosition;

  if (minDistance === distanceToLeft) {
    position = Position.Left;
    relativeXYPosition = { x: 0, y: handleY - nodeTop };
  } else if (minDistance === distanceToRight) {
    position = Position.Right;
    relativeXYPosition = { x: 0, y: handleY - nodeTop };
  } else if (minDistance === distanceToTop) {
    position = Position.Top;
    relativeXYPosition = { x: handleX - nodeLeft, y: 0 };
  } else {
    position = Position.Bottom;
    relativeXYPosition = { x: handleX - nodeLeft, y: 0 };
  }

  return { position, XYPosition: relativeXYPosition };
};

const computePortSide = (borderNodePosition: BorderNodePosition | null): string => {
  let portSide: string = 'UNDEFINED';
  if (borderNodePosition === BorderNodePosition.EAST) {
    portSide = 'EAST';
  } else if (borderNodePosition === BorderNodePosition.WEST) {
    portSide = 'WEST';
  } else if (borderNodePosition === BorderNodePosition.NORTH) {
    portSide = 'NORTH';
  } else if (borderNodePosition === BorderNodePosition.SOUTH) {
    portSide = 'SOUTH';
  }
  return portSide;
};

const buildElkNodeChildrenAndPorts = (
  node: Node<NodeData>,
  nodes: Node<NodeData>[],
  options: LayoutOptions
): ElkNode => {
  const elkNodeChildren: ElkNode[] = nodes
    .filter((n) => !n.data.isBorderNode)
    .filter((n) => n.parentId === node.id)
    .map((n) => buildElkNodeChildrenAndPorts(n, nodes, options));

  const elkNodePorts: ElkNode[] = nodes
    .filter((n) => n.data.isBorderNode)
    .filter((n) => n.parentId === node.id)
    .map((n) => buildElkNodeChildrenAndPorts(n, nodes, options));

  return {
    id: node.id,
    x: node.position.x,
    y: node.position.y,
    width: node.width,
    height: node.height,
    labels: computeLabels(node),
    children: elkNodeChildren,
    ports: elkNodePorts,
    layoutOptions: {
      ...options,
      'elk.port.side': `${computePortSide(node.data.borderNodePosition)}`,
    },
  };
};

const convertToElkGraph = (nodes: Node<NodeData>[], options: LayoutOptions): ElkNode[] => {
  return nodes
    .filter((n) => !n.parentId)
    .filter((n) => n.type !== 'edgeAnchorNodeCreationHandles')
    .map((node) => buildElkNodeChildrenAndPorts(node, nodes, options));
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
      const elkNodesMap = new Map<string, ElkNode>();
      const connectionHandlesMap = new Map<string, ConnectionHandle[]>();

      const collectNode = (node: ElkNode) => {
        const originalNode = nodes.find((n) => n.id === node.id);
        if (originalNode?.data.connectionHandles) {
          const updatedHandles = originalNode.data.connectionHandles.map((handle) => {
            const elkHandle = layoutGraph?.edges?.find((edge) => edge.id === handle.edgeId);
            if (elkHandle && elkHandle.sections) {
              const elkHandleAbsolutePosition: XYPosition =
                handle.type === 'source'
                  ? {
                      x: elkHandle.sections.at(0)?.startPoint?.x ?? 0,
                      y: elkHandle.sections.at(0)?.startPoint?.y ?? 0,
                    }
                  : {
                      x: elkHandle.sections.at(0)?.endPoint?.x ?? 0,
                      y: elkHandle.sections.at(0)?.endPoint?.y ?? 0,
                    };
              const reactFlowHandlePosition = convertHandleAbsolutePositionToReactFlowPosition(
                elkHandleAbsolutePosition,
                node.x ?? 0,
                node.y ?? 0,
                node.width ?? 0,
                node.height ?? 0
              );
              return {
                ...handle,
                position: reactFlowHandlePosition.position,
                XYPosition: reactFlowHandlePosition.XYPosition,
              };
            } else {
              return {
                ...handle,
                position: Position.Right,
                XYPosition: null,
              };
            }
          });
          connectionHandlesMap.set(node.id, updatedHandles);
        }

        const elkNode = {
          ...node,
          position: originalNode?.data.pinned ? originalNode.position : { x: node.x ?? 0, y: node.y ?? 0 },
        };
        elkNodesMap.set(node.id, elkNode);
        if (node.children) {
          node.children.forEach((child) => collectNode(child));
        }
      };

      layoutGraph?.children?.forEach((rootNode: ElkNode) => collectNode(rootNode));

      const processedEdges = edges.map((edge) => {
        const elkEdge = layoutGraph?.edges?.find((e) => e.id === edge.id);
        if (elkEdge) {
          return {
            ...edge,
            data: {
              ...edge.data,
              bendingPoints: elkEdge.sections?.flatMap((section) => section.bendPoints).filter(Boolean) ?? [],
            },
          };
        }
        return edge;
      });

      return {
        elkNodesMap,
        edges: processedEdges,
        connectionHandlesMap,
      };
    } catch (message) {
      addErrorMessage('An error occurred during the arrange all elements ');
      return [];
    }
  };

  const applyElkOnDiagram = async (
    nodes: Node<NodeData, DiagramNodeType>[],
    edges: Edge<EdgeData>[],
    layoutOptions: LayoutOptions
  ): Promise<RawDiagram> => {
    let layoutAllNodes: Node<NodeData, DiagramNodeType>[] = [];
    let layoutAllEdges: Edge<EdgeData>[] = [];
    await getELKLayout(
      nodes.filter((node) => !node.hidden && !isEdgeAnchorNode(node)),
      edges,
      layoutOptions
    ).then(({ elkNodesMap, edges: elkEdges, connectionHandlesMap }) => {
      layoutAllNodes = nodes.map((node) => {
        const elkNode = elkNodesMap.get(node.id);
        if (elkNode) {
          const connectionHandles: ConnectionHandle[] | undefined = connectionHandlesMap.get(node.id);
          return {
            ...node,
            position: {
              x: elkNode.x ?? node.position.x,
              y: elkNode.y ?? node.position.y,
            },
            width: elkNode.width,
            height: elkNode.height,
            data: {
              ...node.data,
              connectionHandles: connectionHandles ?? node.data.connectionHandles,
            },
          };
        }
        return node;
      });
      layoutAllEdges = elkEdges;
    });
    return { nodes: layoutAllNodes, edges: layoutAllEdges };
  };

  const elkLayout = async (
    nodes: Node<NodeData, DiagramNodeType>[],
    edges: Edge<EdgeData>[],
    layoutOptions: LayoutOptions
  ): Promise<RawDiagram> => {
    const diagram = await applyElkOnDiagram(nodes, edges, layoutOptions);
    return { nodes: diagram.nodes, edges: diagram.edges };
  };

  return {
    elkLayout,
  };
};
