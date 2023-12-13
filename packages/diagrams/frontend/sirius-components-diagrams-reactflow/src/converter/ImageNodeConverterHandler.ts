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
import { Node, XYPosition } from 'reactflow';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagram } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import { GQLImageNodeStyle, GQLNode, GQLNodeStyle, GQLViewModifier } from '../graphql/subscription/nodeFragment.types';
import { BorderNodePosition } from '../renderer/DiagramRenderer.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { FreeFormNodeData } from '../renderer/node/FreeFormNode.types';
import { IConvertEngine, INodeConverterHandler } from './ConvertEngine.types';
import { convertHandles } from './convertHandles';
import { convertLabelStyle, convertOutsideLabels } from './convertLabel';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toImageNode = (
  gqlDiagram: GQLDiagram,
  gqlNode: GQLNode<GQLImageNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription | undefined,
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
    style,
    labelEditable,
  } = gqlNode;

  const connectionHandles: ConnectionHandle[] = convertHandles(gqlNode, gqlEdges);
  const isNew = gqlDiagram.layoutData.nodeLayoutData.find((nodeLayoutData) => nodeLayoutData.id === id) === undefined;

  const data: FreeFormNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    insideLabel: null,
    outsideLabels: convertOutsideLabels(outsideLabels),
    imageURL: style.imageURL,
    style: {},
    faded: state === GQLViewModifier.Faded,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePosition.WEST : null,
    labelEditable,
    positionDependentRotation: style.positionDependentRotation,
    connectionHandles,
    isNew,
  };

  if (insideLabel) {
    const {
      id,
      text,
      style: labelStyle,
      style: { iconURL },
    } = insideLabel;
    data.outsideLabels = {
      BOTTOM_MIDDLE: {
        id,
        text,
        iconURL,
        style: {
          ...convertLabelStyle(labelStyle),
        },
      },
    };
  }

  const node: Node<FreeFormNodeData> = {
    id,
    type: 'freeFormNode',
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

export class ImageNodeConverterHandler implements INodeConverterHandler {
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
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    nodes.push(toImageNode(gqlDiagram, gqlNode, parentNode, nodeDescription, isBorderNode, gqlEdges));
    convertEngine.convertNodes(
      gqlDiagram,
      gqlNode.borderNodes ?? [],
      gqlNode,
      nodes,
      nodeDescription?.borderNodeDescriptions ?? []
    );
    convertEngine.convertNodes(
      gqlDiagram,
      gqlNode.childNodes ?? [],
      gqlNode,
      nodes,
      nodeDescription?.childNodeDescriptions ?? []
    );
  }
}
