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
import { GQLImageNodeStyle, GQLNode, GQLNodeStyle, GQLViewModifier } from '../graphql/subscription/nodeFragment.types';
import { BorderNodePositon } from '../renderer/DiagramRenderer.types';
import { ImageNodeData } from '../renderer/node/ImageNode.types';
import { convertLabelStyle } from './convertDiagram';
import { AlignmentMap } from './convertDiagram.types';
import { IConvertEngine, INodeConverterHandler } from './ConvertEngine.types';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toImageNode = (
  gqlNode: GQLNode<GQLImageNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription | undefined,
  isBorderNode: boolean
): Node<ImageNodeData> => {
  const {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    insideLabel,
    id,
    state,
    style,
    labelEditable,
  } = gqlNode;

  const data: ImageNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    label: undefined,
    imageURL: style.imageURL,
    style: {},
    faded: state === GQLViewModifier.Faded,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePositon.WEST : null,
    labelEditable,
    positionDependentRotation: style.positionDependentRotation,
  };

  if (insideLabel) {
    const labelStyle = insideLabel.style;
    data.label = {
      id: insideLabel.id,
      text: insideLabel.text,
      iconURL: labelStyle.iconURL,
      style: {
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        padding: '8px 16px',
        textAlign: 'center',
        ...convertLabelStyle(labelStyle),
      },
    };

    const alignement = AlignmentMap[insideLabel.insideLabelLocation];
    if (alignement.isPrimaryVerticalAlignment) {
      if (alignement.primaryAlignment === 'TOP') {
        data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-start' };
      }
      if (alignement.secondaryAlignment === 'CENTER') {
        data.style = { ...data.style, alignItems: 'stretch' };
        data.label.style = { ...data.label.style, justifyContent: 'center' };
      }
    }
  }

  const node: Node<ImageNodeData> = {
    id,
    type: 'imageNode',
    data,
    position: defaultPosition,
    hidden: state === GQLViewModifier.Hidden,
  };

  if (gqlParentNode) {
    node.parentNode = gqlParentNode.id;
  }

  return node;
};

export class ImageNodeConverterHandler implements INodeConverterHandler {
  canHandle(gqlNode: GQLNode<GQLNodeStyle>) {
    return gqlNode.style.__typename === 'ImageNodeStyle';
  }

  handle(
    convertEngine: IConvertEngine,
    gqlNode: GQLNode<GQLImageNodeStyle>,
    parentNode: GQLNode<GQLNodeStyle> | null,
    isBorderNode: boolean,
    nodes: Node[],
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    nodes.push(toImageNode(gqlNode, parentNode, nodeDescription, isBorderNode));
    convertEngine.convertNodes(gqlNode.borderNodes ?? [], gqlNode, nodes, nodeDescriptions);
    convertEngine.convertNodes(gqlNode.childNodes ?? [], gqlNode, nodes, nodeDescriptions);
  }
}
