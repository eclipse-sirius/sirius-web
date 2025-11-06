/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { Edge, HandleType, Node, ReactFlowState } from '@xyflow/react';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLReferencePosition } from '../graphql/subscription/diagramEventSubscription.types';
import { GQLDiagram, GQLLabelLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLLabel } from '../graphql/subscription/labelFragment.types';
import {
  GQLNode,
  GQLNodeStyle,
  GQLViewModifier,
  ILayoutStrategy,
  ListLayoutStrategy,
} from '../graphql/subscription/nodeFragment.types';
import { Diagram, EdgeData, EdgeLabel, NodeData } from '../renderer/DiagramRenderer.types';
import { MultiLabelEdgeData } from '../renderer/edge/MultiLabelEdge.types';
import { updateHandleFromReferencePosition } from '../renderer/layout/UpdateHandleFromReferencePosition';
import { RawDiagram } from '../renderer/layout/layout.types';
import { computeBorderNodeExtents, computeBorderNodePositions } from '../renderer/layout/layoutBorderNodes';
import { layoutHandles } from '../renderer/layout/layoutHandles';
import { updateHandleViewModifier } from '../renderer/layout/updateHandleViewModifier';
import { GQLEdgeLayoutData } from '../renderer/layout/useSynchronizeLayoutData.types';
import { EdgeAnchorNodeData } from '../renderer/node/EdgeAnchorNode.types';
import { DiagramNodeType } from '../renderer/node/NodeTypes.types';
import { GQLDiagramDescription } from '../representation/DiagramRepresentation.types';
import { IConvertEngine, INodeConverter } from './ConvertEngine.types';
import { IconLabelNodeConverter } from './IconLabelNodeConverter';
import { ImageNodeConverter } from './ImageNodeConverter';
import { ListNodeConverter } from './ListNodeConverter';
import { RectangleNodeConverter } from './RectangleNodeConverter';
import { convertEdgeType } from './convertEdge';
import { convertContentStyle, convertLabelStyle } from './convertLabel';
import { createEdgeAnchorNode } from './edgeAnchorNodeFactory';

const nodeDepth = (nodeId2node: Map<string, Node>, nodeId: string): number => {
  const node = nodeId2node.get(nodeId);
  let depth = 0;

  let parentNode = node?.parentId ? nodeId2node.get(node.parentId) : undefined;
  while (parentNode) {
    depth = depth + 1;
    parentNode = parentNode.parentId ? nodeId2node.get(parentNode.parentId) : undefined;
  }

  return depth;
};

const convertEdgeLabel = (gqlEdgeLabel: GQLLabel, gqlLabelLayoutData: GQLLabelLayoutData[]): EdgeLabel => {
  const labelLayoutData = gqlLabelLayoutData.find((labelLayoutData) => labelLayoutData.id === gqlEdgeLabel.id);
  return {
    id: gqlEdgeLabel.id,
    text: gqlEdgeLabel.text,
    iconURL: gqlEdgeLabel.style.iconURL,
    style: {
      ...convertLabelStyle(gqlEdgeLabel.style),
    },
    contentStyle: {
      ...convertContentStyle(gqlEdgeLabel.style),
    },
    position: labelLayoutData?.position ?? {
      x: 0,
      y: 0,
    },
    appearanceData: {
      customizedStyleProperties: gqlEdgeLabel.customizedStyleProperties,
      gqlStyle: gqlEdgeLabel.style,
    },
    width: labelLayoutData?.size.width ?? 0,
    height: labelLayoutData?.size.height ?? 0,
    resizedByUser: labelLayoutData?.resizedByUser ?? false,
    overflowStrategy: 'WRAP', // all edge labels use the wrap strategy
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

const getOrCreateAnchorNodeEdge = (
  gqlDiagram: GQLDiagram,
  state: ReactFlowState<Node<NodeData>, Edge<EdgeData>>,
  type: HandleType,
  gqlEdge: GQLEdge,
  edges: GQLEdge[]
): Node<NodeData | EdgeAnchorNodeData> | undefined => {
  // We need to use the already rendered node in order to preserve its position and avoid flickering after a refresh
  const id = type === 'source' ? gqlEdge.sourceId : gqlEdge.targetId;
  let edgeAnchorNode = state.nodeLookup.get(id) || createEdgeAnchorNode(gqlEdge, type, edges);

  // Today, we only have one EdgeAnchorNode for each edge used as source or target
  const edgeLayoutData = gqlDiagram.layoutData.edgeLayoutData.find((layoutData) => layoutData.id === id);
  if (edgeLayoutData && edgeLayoutData.edgeAnchorLayoutData.length === 1 && edgeLayoutData.edgeAnchorLayoutData[0]) {
    return {
      ...edgeAnchorNode,
      data: {
        ...edgeAnchorNode.data,
        positionRatio: edgeLayoutData.edgeAnchorLayoutData[0].positionRatio,
        isLayouted: true,
      },
    };
  }
  return edgeAnchorNode;
};

const defaultNodeConverters: INodeConverter[] = [
  new RectangleNodeConverter(),
  new ImageNodeConverter(),
  new IconLabelNodeConverter(),
  new ListNodeConverter(),
];

export const convertDiagram = (
  gqlDiagram: GQLDiagram,
  referencePosition: GQLReferencePosition | null,
  nodeConverterContributions: INodeConverter[],
  diagramDescription: GQLDiagramDescription,
  state: ReactFlowState<Node<NodeData>, Edge<EdgeData>>
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

  const nodeId2node = new Map<string, Node<NodeData>>();
  nodes.forEach((node) => nodeId2node.set(node.id, node));

  const nodeId2Depth = new Map<string, number>();
  nodes.forEach((node) => nodeId2Depth.set(node.id, nodeDepth(nodeId2node, node.id)));

  let usedHandles: string[] = [];

  let edges: Edge<EdgeData>[] = gqlDiagram.edges.map((gqlEdge) => {
    let sourceNode: Node<NodeData> | undefined = nodeId2node.get(gqlEdge.sourceId);
    let targetNode: Node<NodeData> | undefined = nodeId2node.get(gqlEdge.targetId);
    const edgePath = state.edgeLookup.get(gqlEdge.id) ? state.edgeLookup.get(gqlEdge.id)?.data?.edgePath : '';

    //If the node have not been converted, then the source or target is an edge
    if (!sourceNode) {
      //If the node used as an anchor was not converted already
      if (!nodeId2node.get(gqlEdge.sourceId)) {
        sourceNode = getOrCreateAnchorNodeEdge(gqlDiagram, state, 'source', gqlEdge, gqlDiagram.edges);
        if (sourceNode) {
          nodeId2node.set(sourceNode.id, sourceNode);
          nodes.push(sourceNode);
        }
      }
    }

    if (!targetNode) {
      if (!nodeId2node.get(gqlEdge.targetId)) {
        targetNode = getOrCreateAnchorNodeEdge(gqlDiagram, state, 'target', gqlEdge, gqlDiagram.edges);
        if (targetNode) {
          nodeId2node.set(targetNode.id, targetNode);
          nodes.push(targetNode);
        }
      }
    }

    const edgeLayoutData: GQLEdgeLayoutData | undefined = gqlDiagram.layoutData.edgeLayoutData.find(
      (layoutData) => layoutData.id === gqlEdge.id
    );

    let strokeDasharray: string | undefined = undefined;
    if (gqlEdge.style.lineStyle === 'Dash') {
      strokeDasharray = '5,5';
    } else if (gqlEdge.style.lineStyle === 'Dot') {
      strokeDasharray = '2,2';
    } else if (gqlEdge.style.lineStyle === 'Dash_Dot') {
      strokeDasharray = '10,5,2,2,2,5';
    }

    const data: MultiLabelEdgeData = {
      targetObjectId: gqlEdge.targetObjectId,
      targetObjectKind: gqlEdge.targetObjectKind,
      targetObjectLabel: gqlEdge.targetObjectLabel,
      descriptionId: gqlEdge.descriptionId,
      label: null,
      faded: gqlEdge.state === GQLViewModifier.Faded,
      centerLabelEditable: gqlEdge.centerLabelEditable,
      bendingPoints: edgeLayoutData?.bendingPoints ?? null,
      edgePath,
      isHovered: false,
      edgeAppearanceData: {
        gqlStyle: gqlEdge.style,
        customizedStyleProperties: gqlEdge.customizedStyleProperties,
      },
    };

    if (gqlEdge.beginLabel) {
      data.beginLabel = convertEdgeLabel(gqlEdge.beginLabel, gqlDiagram.layoutData.labelLayoutData);
    }
    if (gqlEdge.centerLabel) {
      data.label = convertEdgeLabel(gqlEdge.centerLabel, gqlDiagram.layoutData.labelLayoutData);
    }
    if (gqlEdge.endLabel) {
      data.endLabel = convertEdgeLabel(gqlEdge.endLabel, gqlDiagram.layoutData.labelLayoutData);
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
      type: convertEdgeType(gqlEdge.style.edgeType),
      source: sourceNode ? sourceNode.id : '',
      target: targetNode ? targetNode.id : '',
      markerEnd: `${gqlEdge.style.targetArrow}--${gqlEdge.id}--markerEnd`,
      markerStart: `${gqlEdge.style.sourceArrow}--${gqlEdge.id}--markerStart`,
      zIndex: 2000,
      data,
      hidden: gqlEdge.state === GQLViewModifier.Hidden,
      sourceHandle: sourceHandle?.id,
      targetHandle: targetHandle?.id,
      sourceNode: sourceNode,
      targetNode: targetNode,
      reconnectable: !!state.edgeLookup.get(gqlEdge.id)?.reconnectable,
      style: {
        stroke: gqlEdge.style.color,
        strokeWidth: gqlEdge.style.size,
        strokeDasharray,
      },
    };
  });

  const rawDiagram: RawDiagram = {
    nodes,
    edges,
  };

  const nodeLookUp = new Map();
  nodes.forEach((node) => {
    nodeLookUp.set(node.id, node);
  });

  updateHandleViewModifier(rawDiagram.nodes, state);
  computeBorderNodeExtents(rawDiagram.nodes);
  computeBorderNodePositions(rawDiagram.nodes);
  layoutHandles(rawDiagram, diagramDescription, nodeLookUp);
  updateHandleFromReferencePosition(rawDiagram, state, referencePosition);

  return {
    nodes: rawDiagram.nodes,
    edges: rawDiagram.edges,
  };
};
