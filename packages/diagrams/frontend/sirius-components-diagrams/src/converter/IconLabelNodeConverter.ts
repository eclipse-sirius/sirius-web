/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { GQLDiagram, GQLHandleLayoutData, GQLNodeLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import {
  GQLIconLabelNodeStyle,
  GQLNode,
  GQLNodeStyle,
  GQLViewModifier,
} from '../graphql/subscription/nodeFragment.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { defaultHeight, defaultWidth } from '../renderer/layout/layoutParams';
import { IconLabelNodeData } from '../renderer/node/IconLabelNode.types';
import { GQLDiagramDescription } from '../representation/DiagramRepresentation.types';
import { IConvertEngine, INodeConverter } from './ConvertEngine.types';
import { convertBorderNodePosition } from './convertBorderNodes';
import { isListLayoutStrategy } from './convertDiagram';
import { convertHandles } from './convertHandles';
import { convertInsideLabel, convertOutsideLabels } from './convertLabel';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toIconLabelNode = (
  gqlDiagram: GQLDiagram,
  gqlNode: GQLNode<GQLIconLabelNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription,
  isBorderNode: boolean,
  gqlEdges: GQLEdge[]
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
    deletable,
    customizedStyleProperties,
  } = gqlNode;

  const gqlNodeLayoutData: GQLNodeLayoutData | undefined = gqlDiagram.layoutData.nodeLayoutData.find(
    (nodeLayoutData) => nodeLayoutData.id === id
  );
  const handleLayoutData: GQLHandleLayoutData[] = gqlNodeLayoutData?.handleLayoutData ?? [];

  const connectionHandles: ConnectionHandle[] = convertHandles(gqlNode.id, gqlEdges, handleLayoutData);
  const isNew = gqlNodeLayoutData === undefined;
  const resizedByUser = gqlNodeLayoutData?.resizedByUser ?? false;
  const movedByUser = gqlNodeLayoutData?.movedByUser ?? false;

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
    outsideLabels: convertOutsideLabels(outsideLabels, gqlDiagram.layoutData.labelLayoutData),
    isBorderNode: isBorderNode,
    borderNodePosition: convertBorderNodePosition(gqlNode.initialBorderNodePosition),
    faded: state === GQLViewModifier.Faded,
    pinned,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    labelEditable: labelEditable,
    deletable: deletable,
    connectionHandles,
    isNew,
    resizedByUser,
    movedByUser,
    isListChild: isListLayoutStrategy(gqlParentNode?.style.childrenLayoutStrategy),
    isDraggedNode: false,
    isDropNodeTarget: false,
    isDropNodeCandidate: false,
    isHovered: false,
    connectionLinePositionOnNode: 'none',
    nodeAppearanceData: {
      gqlStyle: style,
      customizedStyleProperties,
    },
    minComputedWidth: null,
    minComputedHeight: null,
    isLastNodeSelected: false,
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

  if (gqlNodeLayoutData) {
    const {
      position,
      size: { height, width },
    } = gqlNodeLayoutData;
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
  if (gqlNodeLayoutData?.size.height && gqlNodeLayoutData?.size.width) {
    node.measured = {
      height: gqlNodeLayoutData.size.height,
      width: gqlNodeLayoutData.size.width,
    };
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
    gqlEdges: GQLEdge[],
    parentNode: GQLNode<GQLNodeStyle> | null,
    isBorderNode: boolean,
    nodes: Node[],
    _diagramDescription: GQLDiagramDescription,
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    if (nodeDescription) {
      nodes.push(toIconLabelNode(gqlDiagram, gqlNode, parentNode, nodeDescription, isBorderNode, gqlEdges));
    }
  }
}
