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
import { Node, XYPosition } from '@xyflow/react';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagram, GQLHandleLayoutData, GQLNodeLayoutData } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLImageNodeStyle, GQLNode, GQLNodeStyle, GQLViewModifier } from '../graphql/subscription/nodeFragment.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { defaultHeight, defaultWidth } from '../renderer/layout/layoutParams';
import { FreeFormNodeData } from '../renderer/node/FreeFormNode.types';
import { GQLDiagramDescription } from '../representation/DiagramRepresentation.types';
import { IConvertEngine, INodeConverter } from './ConvertEngine.types';
import { convertBorderNodePosition } from './convertBorderNodes';
import { convertLineStyle, isListLayoutStrategy } from './convertDiagram';
import { convertHandles } from './convertHandles';
import { convertInsideLabel, convertOutsideLabels } from './convertLabel';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toImageNode = (
  gqlDiagram: GQLDiagram,
  gqlNode: GQLNode<GQLImageNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription,
  isBorderNode: boolean,
  gqlEdges: GQLEdge[]
): Node<FreeFormNodeData> => {
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
    customizedStyleProperties,
  } = gqlNode;

  const handleLayoutData: GQLHandleLayoutData[] = gqlDiagram.layoutData.nodeLayoutData
    .filter((nodeLayoutData) => nodeLayoutData.id === id)
    .flatMap((nodeLayoutData) => nodeLayoutData.handleLayoutData);

  const connectionHandles: ConnectionHandle[] = convertHandles(gqlNode.id, gqlEdges, handleLayoutData);

  const gqlNodeLayoutData: GQLNodeLayoutData | undefined = gqlDiagram.layoutData.nodeLayoutData.find(
    (nodeLayoutData) => nodeLayoutData.id === id
  );
  const isNew = gqlNodeLayoutData === undefined;
  const resizedByUser = gqlNodeLayoutData?.resizedByUser ?? false;
  const movedByUser = gqlNodeLayoutData?.movedByUser ?? false;

  const data: FreeFormNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    insideLabel: null,
    outsideLabels: convertOutsideLabels(outsideLabels, gqlDiagram.layoutData.labelLayoutData),
    imageURL: style.imageURL,
    style: {
      borderColor: style.borderColor,
      borderRadius: style.borderRadius,
      borderWidth: style.borderSize,
      borderStyle: convertLineStyle(style.borderStyle),
    },
    faded: state === GQLViewModifier.Faded,
    pinned,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    isBorderNode: isBorderNode,
    borderNodePosition: convertBorderNodePosition(gqlNode.initialBorderNodePosition),
    labelEditable,
    positionDependentRotation: style.positionDependentRotation,
    connectionHandles,
    isNew,
    resizedByUser,
    movedByUser,
    isListChild: isListLayoutStrategy(gqlParentNode?.style.childrenLayoutStrategy),
    isDraggedNode: false,
    isDropNodeTarget: false,
    isDropNodeCandidate: false,
    connectionLinePositionOnNode: 'none',
    isHovered: false,
    nodeAppearanceData: {
      gqlStyle: style,
      customizedStyleProperties,
    },
    minComputedWidth: null,
    minComputedHeight: null,
  };

  data.insideLabel = convertInsideLabel(
    insideLabel,
    data,
    `${style.borderSize}px ${style.borderStyle} ${style.borderColor}`,
    gqlNode.childNodes?.some((child) => child.state !== GQLViewModifier.Hidden)
  );

  const node: Node<FreeFormNodeData> = {
    id,
    type: 'freeFormNode',
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
    node.dragHandle = '.custom-drag-handle';
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

export class ImageNodeConverter implements INodeConverter {
  canHandle(gqlNode: GQLNode<GQLNodeStyle>) {
    return gqlNode.style.__typename === 'ImageNodeStyle';
  }

  handle(
    convertEngine: IConvertEngine,
    gqlDiagram: GQLDiagram,
    gqlNode: GQLNode<GQLImageNodeStyle>,
    gqlEdges: GQLEdge[],
    parentNode: GQLNode<GQLNodeStyle> | null,
    isBorderNode: boolean,
    nodes: Node[],
    diagramDescription: GQLDiagramDescription,
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    if (nodeDescription) {
      nodes.push(toImageNode(gqlDiagram, gqlNode, parentNode, nodeDescription, isBorderNode, gqlEdges));
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
  }
}
