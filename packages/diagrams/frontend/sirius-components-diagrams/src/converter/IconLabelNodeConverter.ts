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
import { Node, XYPosition } from '@xyflow/react';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagram, GQLNodeLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import {
  GQLIconLabelNodeStyle,
  GQLNode,
  GQLNodeStyle,
  GQLViewModifier,
} from '../graphql/subscription/nodeFragment.types';
import { BorderNodePosition } from '../renderer/DiagramRenderer.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { defaultHeight, defaultWidth } from '../renderer/layout/layoutParams';
import { IconLabelNodeData } from '../renderer/node/IconsLabelNode.types';
import { GQLDiagramDescription } from '../representation/DiagramRepresentation.types';
import { IConvertEngine, INodeConverter } from './ConvertEngine.types';
import { isListLayoutStrategy } from './convertDiagram';
import { convertInsideLabel, convertOutsideLabels } from './convertLabel';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toIconLabelNode = (
  gqlDiagram: GQLDiagram,
  gqlNode: GQLNode<GQLIconLabelNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription,
  isBorderNode: boolean
): Node<IconLabelNodeData> => {
  const {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    id,
    insideLabel,
    outsideLabels,
    state,
    pinned,
    style,
    labelEditable,
  } = gqlNode;

  const connectionHandles: ConnectionHandle[] = [];
  const gqlNodeLayoutData: GQLNodeLayoutData | undefined = gqlDiagram.layoutData.nodeLayoutData.find(
    (nodeLayoutData) => nodeLayoutData.id === id
  );
  const isNew = gqlNodeLayoutData === undefined;
  const resizedByUser = gqlNodeLayoutData?.resizedByUser ?? false;

  const data: IconLabelNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    style: {
      textAlign: 'left',
      background: style.background,
    },
    insideLabel: null,
    outsideLabels: convertOutsideLabels(outsideLabels),
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePosition.WEST : null,
    faded: state === GQLViewModifier.Faded,
    pinned,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    labelEditable: labelEditable,
    connectionHandles,
    isNew,
    resizedByUser,
    isListChild: isListLayoutStrategy(gqlParentNode?.childrenLayoutStrategy),
    isDropNodeTarget: false,
    isDropNodeCandidate: false,
    isHovered: false,
  };

  data.insideLabel = convertInsideLabel(insideLabel, data, '', false, '0 8px 0 8px');

  const node: Node<IconLabelNodeData> = {
    id,
    type: 'iconLabelNode',
    data,
    position: defaultPosition,
    hidden: state === GQLViewModifier.Hidden,
  };

  if (gqlParentNode) {
    node.parentId = gqlParentNode.id;
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
  } else {
    node.height = data.defaultHeight ?? defaultHeight;
    node.width = data.defaultWidth ?? defaultWidth;
  }

  return node;
};

export class IconLabelNodeConverter implements INodeConverter {
  canHandle(gqlNode: GQLNode<GQLNodeStyle>) {
    return gqlNode.style.__typename === 'IconLabelNodeStyle';
  }

  handle(
    _convertEngine: IConvertEngine,
    gqlDiagram: GQLDiagram,
    gqlNode: GQLNode<GQLIconLabelNodeStyle>,
    _gqlEdges: GQLEdge[],
    parentNode: GQLNode<GQLNodeStyle> | null,
    isBorderNode: boolean,
    nodes: Node[],
    _diagramDescription: GQLDiagramDescription,
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    if (nodeDescription) {
      nodes.push(toIconLabelNode(gqlDiagram, gqlNode, parentNode, nodeDescription, isBorderNode));
    }
  }
}
