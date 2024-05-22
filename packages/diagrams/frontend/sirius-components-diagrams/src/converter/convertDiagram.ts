/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { GQLLabel } from '../graphql/subscription/labelFragment.types';
import {
  GQLNode,
  GQLNodeStyle,
  GQLViewModifier,
  ILayoutStrategy,
  ListLayoutStrategy,
} from '../graphql/subscription/nodeFragment.types';
import { Diagram, EdgeLabel, NodeData } from '../renderer/DiagramRenderer.types';
import { MultiLabelEdgeData } from '../renderer/edge/MultiLabelEdge.types';
import { RawDiagram } from '../renderer/layout/layout.types';
import { computeBorderNodeExtents, computeBorderNodePositions } from '../renderer/layout/layoutBorderNodes';
import { layoutHandles } from '../renderer/layout/layoutHandles';
import { DiagramNodeType } from '../renderer/node/NodeTypes.types';
import { GQLDiagramDescription } from '../representation/DiagramRepresentation.types';
import { IConvertEngine, INodeConverter } from './ConvertEngine.types';
import { IconLabelNodeConverter } from './IconLabelNodeConverter';
import { ImageNodeConverter } from './ImageNodeConverter';
import { ListNodeConverter } from './ListNodeConverter';
import { RectangleNodeConverter } from './RectangleNodeConverter';
import { convertLabelStyle } from './convertLabel';

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

const convertEdgeLabel = (gqlEdgeLabel: GQLLabel): EdgeLabel => {
  return {
    id: gqlEdgeLabel.id,
    text: gqlEdgeLabel.text,
    iconURL: gqlEdgeLabel.style.iconURL,
    style: {
      position: 'absolute',
      background: 'transparent',
      padding: 5,
      zIndex: 1001,
      ...convertLabelStyle(gqlEdgeLabel.style),
    },
  };
};

export const convertLineStyle = (lineStyle: string): string => {
  if (lineStyle === 'Dash') {
    return 'dashed';
  } else if (lineStyle === 'Dot') {
    return 'dotted';
  } else if (lineStyle === 'Dash_Dot') {
    return 'dashed';
  }
  return 'solid';
};

export const isListLayoutStrategy = (strategy: ILayoutStrategy | undefined): strategy is ListLayoutStrategy =>
  strategy?.kind === 'List';

const defaultNodeConverters: INodeConverter[] = [
  new RectangleNodeConverter(),
  new ImageNodeConverter(),
  new IconLabelNodeConverter(),
  new ListNodeConverter(),
];

export const convertDiagram = (
  gqlDiagram: GQLDiagram,
  nodeConverterContributions: INodeConverter[],
  diagramDescription: GQLDiagramDescription
): Diagram => {
  const nodes: Node<NodeData, DiagramNodeType>[] = [];
  const convertEngine: IConvertEngine = {
    convertNodes(
      gqlDiagram: GQLDiagram,
      gqlNodesToConvert: GQLNode<GQLNodeStyle>[],
      parentNode: GQLNode<GQLNodeStyle> | null,
      nodes: Node[],
      diagramDescription: GQLDiagramDescription,
      nodeDescriptions: GQLNodeDescription[]
    ) {
      gqlNodesToConvert.forEach((node) => {
        const nodeConverter: INodeConverter | undefined = [
          ...defaultNodeConverters,
          ...nodeConverterContributions,
        ].find((handler) => handler.canHandle(node));

        if (nodeConverter) {
          const isBorderNode: boolean = !!parentNode?.borderNodes?.map((borderNode) => borderNode.id).includes(node.id);
          nodeConverter.handle(
            this,
            gqlDiagram,
            node,
            gqlDiagram.edges,
            parentNode,
            isBorderNode,
            nodes,
            diagramDescription,
            nodeDescriptions
          );
        }
      });
    },
  };

  convertEngine.convertNodes(
    gqlDiagram,
    gqlDiagram.nodes,
    null,
    nodes,
    diagramDescription,
    diagramDescription.nodeDescriptions
  );

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
      centerLabelEditable: gqlEdge.centerLabelEditable,
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

    let strokeDasharray: string | undefined = undefined;
    if (gqlEdge.style.lineStyle === 'Dash') {
      strokeDasharray = '5,5';
    } else if (gqlEdge.style.lineStyle === 'Dot') {
      strokeDasharray = '2,2';
    } else if (gqlEdge.style.lineStyle === 'Dash_Dot') {
      strokeDasharray = '10,5,2,2,2,5';
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
        strokeDasharray,
      },
      data,
      hidden: gqlEdge.state === GQLViewModifier.Hidden,
      sourceHandle: sourceHandle?.id,
      targetHandle: targetHandle?.id,
      sourceNode: sourceNode,
      targetNode: targetNode,
      updatable: false,
    };
  });

  const rawDiagram: RawDiagram = {
    nodes,
    edges,
  };
  computeBorderNodeExtents(rawDiagram.nodes);
  computeBorderNodePositions(rawDiagram.nodes);
  layoutHandles(rawDiagram, diagramDescription);

  return {
    metadata: {
      id: gqlDiagram.id,
      label: gqlDiagram.metadata.label,
      kind: gqlDiagram.metadata.kind,
      targetObjectId: gqlDiagram.targetObjectId,
    },
    nodes: rawDiagram.nodes,
    edges: rawDiagram.edges,
  };
};
