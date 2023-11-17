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

import { Edge, Node } from 'reactflow';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagram } from '../graphql/subscription/diagramFragment.types';
import { GQLLabel, GQLLabelStyle } from '../graphql/subscription/labelFragment.types';
import { GQLNode, GQLNodeStyle, GQLViewModifier } from '../graphql/subscription/nodeFragment.types';
import { Diagram, Label, NodeData } from '../renderer/DiagramRenderer.types';
import { MultiLabelEdgeData } from '../renderer/edge/MultiLabelEdge.types';
import { DiagramNodeType } from '../renderer/node/NodeTypes.types';
import { IConvertEngine, INodeConverterHandler } from './ConvertEngine.types';
import { IconLabelNodeConverterHandler } from './IconLabelNodeConverterHandler';
import { ImageNodeConverterHandler } from './ImageNodeConverterHandler';
import { ListNodeConverterHandler } from './ListNodeConverterHandler';
import { RectangleNodeConverterHandler } from './RectangleNodeConverterHandler';

const nodeDepth = (nodeId2node: Map<string, Node>, nodeId: string): number => {
  const node = nodeId2node.get(nodeId);
  let depth = 0;

  let parentNode = node?.parentNode ? nodeId2node.get(node.parentNode) : undefined;
  while (parentNode) {
    depth = depth + 1;
    parentNode = parentNode.parentNode ? nodeId2node.get(parentNode.parentNode) : undefined;
  }

  return depth;
};

const convertEdgeLabel = (gqlEdgeLabel: GQLLabel): Label => {
  return {
    id: gqlEdgeLabel.id,
    text: gqlEdgeLabel.text,
    iconURL: gqlEdgeLabel.style.iconURL,
    style: {
      position: 'absolute',
      background: 'transparent',
      padding: 10,
      zIndex: 1001,
      ...convertLabelStyle(gqlEdgeLabel.style),
    },
  };
};

export const convertLabelStyle = (gqlLabelStyle: GQLLabelStyle): React.CSSProperties => {
  const style: React.CSSProperties = {};

  if (gqlLabelStyle.bold) {
    style.fontWeight = 'bold';
  }
  if (gqlLabelStyle.italic) {
    style.fontStyle = 'italic';
  }
  if (gqlLabelStyle.fontSize) {
    style.fontSize = gqlLabelStyle.fontSize;
  }
  if (gqlLabelStyle.color) {
    style.color = gqlLabelStyle.color;
  }

  let decoration: string = '';
  if (gqlLabelStyle.strikeThrough) {
    decoration = decoration + 'line-through';
  }
  if (gqlLabelStyle.underline) {
    const separator: string = decoration.length > 0 ? ' ' : '';
    decoration = decoration + separator + 'underline';
  }
  if (decoration.length > 0) {
    style.textDecoration = decoration;
  }

  return style;
};

const defaultNodeConverterHandlers: INodeConverterHandler[] = [
  new RectangleNodeConverterHandler(),
  new ImageNodeConverterHandler(),
  new IconLabelNodeConverterHandler(),
  new ListNodeConverterHandler(),
];

export const convertDiagram = (
  gqlDiagram: GQLDiagram,
  nodeConverterHandlerContributions: INodeConverterHandler[],
  nodeDescriptions: GQLNodeDescription[]
): Diagram => {
  const nodes: Node<NodeData, DiagramNodeType>[] = [];
  const convertEngine: IConvertEngine = {
    convertNodes(
      gqlDiagram: GQLDiagram,
      gqlNodesToConvert: GQLNode<GQLNodeStyle>[],
      parentNode: GQLNode<GQLNodeStyle> | null,
      nodes: Node[],
      nodeDescriptions: GQLNodeDescription[]
    ) {
      gqlNodesToConvert.forEach((node) => {
        const nodeConverterHandler: INodeConverterHandler | undefined = [
          ...defaultNodeConverterHandlers,
          ...nodeConverterHandlerContributions,
        ].find((handler) => handler.canHandle(node));
        if (nodeConverterHandler) {
          const isBorderNode: boolean = !!parentNode?.borderNodes?.map((borderNode) => borderNode.id).includes(node.id);
          nodeConverterHandler.handle(
            this,
            gqlDiagram,
            node,
            gqlDiagram.edges,
            parentNode,
            isBorderNode,
            nodes,
            nodeDescriptions
          );
        }
      });
    },
  };

  convertEngine.convertNodes(gqlDiagram, gqlDiagram.nodes, null, nodes, nodeDescriptions);

  const nodeId2node = new Map<string, Node>();
  nodes.forEach((node) => nodeId2node.set(node.id, node));

  const nodeId2Depth = new Map<string, number>();
  nodes.forEach((node) => nodeId2Depth.set(node.id, nodeDepth(nodeId2node, node.id)));
  let usedHandles: string[] = [];
  const edges: Edge[] = gqlDiagram.edges.map((gqlEdge) => {
    const sourceNode: Node<NodeData> | undefined = nodeId2node.get(gqlEdge.sourceId);
    const targetNode: Node<NodeData> | undefined = nodeId2node.get(gqlEdge.targetId);
    const data: MultiLabelEdgeData = {
      targetObjectId: gqlEdge.targetObjectId,
      targetObjectKind: gqlEdge.targetObjectKind,
      targetObjectLabel: gqlEdge.targetObjectLabel,
      label: null,
      faded: gqlEdge.state === GQLViewModifier.Faded,
    };

    if (gqlEdge.beginLabel) {
      data.beginLabel = convertEdgeLabel(gqlEdge.beginLabel);
    }
    if (gqlEdge.centerLabel) {
      data.label = convertEdgeLabel(gqlEdge.centerLabel);
    }
    if (gqlEdge.endLabel) {
      data.endLabel = convertEdgeLabel(gqlEdge.endLabel);
    }

    const sourceHandle = sourceNode?.data.connectionHandles
      .filter((connectionHandle) => connectionHandle.type === 'source')
      .find((connectionHandle) => !usedHandles.find((usedHandle) => usedHandle === connectionHandle.id));

    const targetHandle = targetNode?.data.connectionHandles
      .filter((connectionHandle) => connectionHandle.type === 'target')
      .find((connectionHandle) => !usedHandles.find((usedHandle) => usedHandle === connectionHandle.id));

    if (sourceHandle?.id && targetHandle?.id) {
      usedHandles.push(sourceHandle?.id, targetHandle.id);
    }

    return {
      id: gqlEdge.id,
      type: 'multiLabelEdge',
      source: gqlEdge.sourceId,
      target: gqlEdge.targetId,
      markerEnd: `${gqlEdge.style.targetArrow}--${gqlEdge.id}--markerEnd`,
      markerStart: `${gqlEdge.style.sourceArrow}--${gqlEdge.id}--markerStart`,
      zIndex: 2000,
      style: {
        stroke: gqlEdge.style.color,
        strokeWidth: gqlEdge.style.size,
      },
      data,
      hidden: gqlEdge.state === GQLViewModifier.Hidden,
      sourceHandle: sourceHandle?.id,
      targetHandle: targetHandle?.id,
      sourceNode: sourceNode,
      targetNode: targetNode,
    };
  });

  return {
    metadata: {
      id: gqlDiagram.id,
      label: gqlDiagram.metadata.label,
      kind: gqlDiagram.metadata.kind,
      targetObjectId: gqlDiagram.targetObjectId,
    },
    nodes,
    edges,
  };
};
