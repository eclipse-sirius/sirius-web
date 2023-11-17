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
import {
  AlignmentMap,
  BorderNodePositon,
  ConnectionHandle,
  GQLEdge,
  GQLNode,
  GQLNodeDescription,
  GQLNodeStyle,
  GQLViewModifier,
  IConvertEngine,
  INodeConverterHandler,
  convertHandles,
  convertLabelStyle,
} from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { Node, XYPosition } from 'reactflow';
import { EllipseNodeData, GQLEllipseNodeStyle } from './EllipseNode.types';

const defaultPosition: XYPosition = { x: 0, y: 0 };
const toEllipseNode = (
  gqlNode: GQLNode<GQLEllipseNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription | undefined,
  isBorderNode: boolean,
  gqlEdges: GQLEdge[]
): Node<EllipseNodeData> => {
  const {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    id,
    insideLabel,
    state,
    style,
    labelEditable,
  } = gqlNode;

  const connectionHandles: ConnectionHandle[] = convertHandles(gqlNode, gqlEdges);
  const data: EllipseNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    style: {
      display: 'flex',
      backgroundColor: style.color,
      borderColor: style.borderColor,
      borderWidth: style.borderSize,
      borderStyle: style.borderStyle,
    },
    label: undefined,
    faded: state === GQLViewModifier.Faded,
    isBorderNode: isBorderNode,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    borderNodePosition: isBorderNode ? BorderNodePositon.EAST : null,
    connectionHandles,
    labelEditable,
  };

  if (insideLabel) {
    const labelStyle = insideLabel.style;
    data.label = {
      id: insideLabel.id,
      text: insideLabel.text,
      isHeader: insideLabel.isHeader,
      style: {
        display: 'flex',
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        padding: '8px 16px',
        textAlign: 'center',
        ...convertLabelStyle(labelStyle),
      },
      iconURL: labelStyle.iconURL,
    };

    const alignement = AlignmentMap[insideLabel.insideLabelLocation];
    if (alignement.isPrimaryVerticalAlignment) {
      if (alignement.primaryAlignment === 'TOP') {
        if (data.label.isHeader) {
          data.label.style.borderBottom = `${style.borderSize}px ${style.borderStyle} ${style.borderColor}`;
        }
        data.style = { ...data.style, display: 'flex', flexDirection: 'column', justifyContent: 'flex-start' };
      }
      if (alignement.secondaryAlignment === 'CENTER') {
        data.style = { ...data.style, alignItems: 'stretch' };
        data.label.style = { ...data.label.style, justifyContent: 'center' };
      }
    }
  }

  const node: Node<EllipseNodeData> = {
    id,
    type: 'ellipseNode',
    data,
    position: defaultPosition,
    hidden: gqlNode.state === GQLViewModifier.Hidden,
  };

  if (gqlParentNode) {
    node.parentNode = gqlParentNode.id;
  }

  return node;
};

export class EllipseNodeConverterHandler implements INodeConverterHandler {
  canHandle(gqlNode: GQLNode<GQLNodeStyle>) {
    return gqlNode.style.__typename === 'EllipseNodeStyle';
  }

  handle(
    convertEngine: IConvertEngine,
    gqlNode: GQLNode<GQLEllipseNodeStyle>,
    gqlEdges: GQLEdge[],
    parentNode: GQLNode<GQLNodeStyle> | null,
    isBorderNode: boolean,
    nodes: Node[],
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    nodes.push(toEllipseNode(gqlNode, parentNode, nodeDescription, isBorderNode, gqlEdges));
    convertEngine.convertNodes(
      gqlNode.borderNodes ?? [],
      gqlNode,
      nodes,
      nodeDescription?.borderNodeDescriptions ?? []
    );
    convertEngine.convertNodes(gqlNode.childNodes ?? [], gqlNode, nodes, nodeDescription?.childNodeDescriptions ?? []);
  }
}
