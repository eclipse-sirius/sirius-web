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
import { GQLDiagram } from '../graphql/subscription/diagramFragment.types';
import { GQLEdge } from '../graphql/subscription/edgeFragment.types';
import {
  GQLIconLabelNodeStyle,
  GQLNode,
  GQLNodeStyle,
  GQLViewModifier,
} from '../graphql/subscription/nodeFragment.types';
import { BorderNodePosition } from '../renderer/DiagramRenderer.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { IconLabelNodeData } from '../renderer/node/IconsLabelNode.types';
import { IConvertEngine, INodeConverterHandler } from './ConvertEngine.types';
import { convertLabelStyle, convertOutsideLabels } from './convertLabel';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toIconLabelNode = (
  gqlDiagram: GQLDiagram,
  gqlNode: GQLNode<GQLIconLabelNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription | undefined,
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
    style,
    labelEditable,
  } = gqlNode;

  const connectionHandles: ConnectionHandle[] = [];
  const isNew = gqlDiagram.layoutData.nodeLayoutData.find((nodeLayoutData) => nodeLayoutData.id === id) === undefined;

  const data: IconLabelNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    style: {
      textAlign: 'left',
      backgroundColor: style.backgroundColor,
    },
    insideLabel: null,
    outsideLabels: convertOutsideLabels(outsideLabels),
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePosition.WEST : null,
    faded: state === GQLViewModifier.Faded,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    labelEditable: labelEditable,
    connectionHandles,
    isNew,
  };

  if (insideLabel) {
    const labelStyle = insideLabel.style;

    data.insideLabel = {
      id: insideLabel.id,
      text: insideLabel.text,
      style: {
        ...convertLabelStyle(labelStyle),
      },
      iconURL: labelStyle.iconURL,
      isHeader: insideLabel.isHeader,
      displayHeaderSeparator: insideLabel.displayHeaderSeparator,
    };
  }

  const node: Node<IconLabelNodeData> = {
    id,
    type: 'iconLabelNode',
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

export class IconLabelNodeConverterHandler implements INodeConverterHandler {
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
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    nodes.push(toIconLabelNode(gqlDiagram, gqlNode, parentNode, nodeDescription, isBorderNode));
  }
}
