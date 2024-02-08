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
import { Node, XYPosition } from 'reactflow';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagram, GQLNodeLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import {
  GQLNode,
  GQLNodeStyle,
  GQLRectangularNodeStyle,
  GQLViewModifier,
} from '../graphql/subscription/nodeFragment.types';
import { BorderNodePosition } from '../renderer/DiagramRenderer.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { ListNodeData } from '../renderer/node/ListNode.types';
import { GQLDiagramDescription } from '../representation/DiagramRepresentation.types';
import { IConvertEngine, INodeConverter } from './ConvertEngine.types';
import { convertHandles } from './convertHandles';
import { convertOutsideLabels, convertInsideLabel } from './convertLabel';
import { isListLayoutStrategy } from './convertDiagram';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toListNode = (
  gqlDiagram: GQLDiagram,
  gqlNode: GQLNode<GQLRectangularNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription,
  isBorderNode: boolean,
  gqlEdges: GQLEdge[]
): Node<ListNodeData> => {
  const {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    insideLabel,
    outsideLabels,
    id,
    state,
    pinned,
    style,
    labelEditable,
  } = gqlNode;

  const connectionHandles: ConnectionHandle[] = convertHandles(gqlNode, gqlEdges);
  const gqlNodeLayoutData: GQLNodeLayoutData | undefined = gqlDiagram.layoutData.nodeLayoutData.find(
    (nodeLayoutData) => nodeLayoutData.id === id
  );
  const isNew = gqlNodeLayoutData === undefined;
  const resizedByUser = gqlNodeLayoutData?.resizedByUser ?? false;

  const data: ListNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    style: {
      backgroundColor: style.color,
      borderTopColor: style.borderColor,
      borderBottomColor: style.borderColor,
      borderLeftColor: style.borderColor,
      borderRightColor: style.borderColor,
      borderRadius: style.borderRadius,
      borderTopWidth: style.borderSize,
      borderBottomWidth: style.borderSize,
      borderLeftWidth: style.borderSize,
      borderRightWidth: style.borderSize,
      borderStyle: style.borderStyle,
    },
    insideLabel: null,
    outsideLabels: convertOutsideLabels(outsideLabels),
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePosition.WEST : null,
    faded: state === GQLViewModifier.Faded,
    pinned,
    labelEditable,
    nodeDescription,
    connectionHandles,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    isNew,
    areChildNodesDraggable: isListLayoutStrategy(gqlNode.childrenLayoutStrategy)
      ? gqlNode.childrenLayoutStrategy.areChildNodesDraggable
      : true,
    topGap: isListLayoutStrategy(gqlNode.childrenLayoutStrategy) ? gqlNode.childrenLayoutStrategy.topGap : 0,
    bottomGap: isListLayoutStrategy(gqlNode.childrenLayoutStrategy) ? gqlNode.childrenLayoutStrategy.bottomGap : 0,
    isListChild: isListLayoutStrategy(gqlParentNode?.childrenLayoutStrategy),
    resizedByUser,
    growableNodeIds: isListLayoutStrategy(gqlNode.childrenLayoutStrategy)
      ? gqlNode.childrenLayoutStrategy.growableNodeIds
      : [],
  };

  data.insideLabel = convertInsideLabel(
    insideLabel,
    data,
    `${style.borderSize}px ${style.borderStyle} ${style.borderColor}`
  );

  const node: Node<ListNodeData> = {
    id,
    type: 'listNode',
    data,
    position: defaultPosition,
    hidden: state === GQLViewModifier.Hidden,
  };

  if (gqlParentNode) {
    node.parentNode = gqlParentNode.id;
  }

  const nodeLayoutData = gqlDiagram.layoutData.nodeLayoutData.filter((data) => data.id === id)[0];
  if (nodeLayoutData) {
    const {
      position,
      size: { height, width },
    } = nodeLayoutData;
    node.position = position;
    node.height = height;
    node.width = width;
    node.style = {
      ...node.style,
      width: `${node.width}px`,
      height: `${node.height}px`,
    };
  }

  return node;
};

const adaptChildrenBorderNodes = (nodes: Node[], gqlChildrenNodes: GQLNode<GQLNodeStyle>[]): void => {
  const childrenNodes = nodes.filter(
    (child) =>
      gqlChildrenNodes.map((gqlChild) => gqlChild.id).find((gqlChildId) => gqlChildId === child.id) !== undefined
  );
  childrenNodes.forEach((child, index) => {
    // Hide children node borders to prevent a 'bold' aspect.
    child.data.style = {
      ...child.data.style,
      borderTopColor: 'transparent',
      borderLeftColor: 'transparent',
      borderRightColor: 'transparent',
    };

    if (index === childrenNodes.length - 1) {
      child.data.style = {
        ...child.data.style,
        borderBottomColor: 'transparent',
      };
    }
  });
};

export class ListNodeConverter implements INodeConverter {
  canHandle(gqlNode: GQLNode<GQLNodeStyle>) {
    return gqlNode.style.__typename === 'RectangularNodeStyle' && gqlNode.childrenLayoutStrategy?.kind === 'List';
  }

  handle(
    convertEngine: IConvertEngine,
    gqlDiagram: GQLDiagram,
    gqlNode: GQLNode<GQLRectangularNodeStyle>,
    gqlEdges: GQLEdge[],
    parentNode: GQLNode<GQLNodeStyle> | null,
    isBorderNode: boolean,
    nodes: Node[],
    diagramDescription: GQLDiagramDescription,
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    if (nodeDescription) {
      nodes.push(toListNode(gqlDiagram, gqlNode, parentNode, nodeDescription, isBorderNode, gqlEdges));
    }

    const borderNodeDescriptions: GQLNodeDescription[] = (nodeDescription?.borderNodeDescriptionIds ?? []).flatMap(
      (nodeDescriptionId) =>
        diagramDescription.nodeDescriptions.filter((nodeDescription) => nodeDescription.id === nodeDescriptionId)
    );
    const childNodeDescriptions: GQLNodeDescription[] = (nodeDescription?.childNodeDescriptionIds ?? []).flatMap(
      (nodeDescriptionId) =>
        diagramDescription.nodeDescriptions.filter((nodeDescription) => nodeDescription.id === nodeDescriptionId)
    );

    convertEngine.convertNodes(
      gqlDiagram,
      gqlNode.borderNodes ?? [],
      gqlNode,
      nodes,
      diagramDescription,
      borderNodeDescriptions
    );
    convertEngine.convertNodes(
      gqlDiagram,
      gqlNode.childNodes ?? [],
      gqlNode,
      nodes,
      diagramDescription,
      childNodeDescriptions
    );
    adaptChildrenBorderNodes(nodes, gqlNode.childNodes ?? []);
  }
}
