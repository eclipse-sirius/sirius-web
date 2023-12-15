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
import {
  GQLNode,
  GQLNodeStyle,
  GQLRectangularNodeStyle,
  GQLViewModifier,
} from '../graphql/subscription/nodeFragment.types';
import { BorderNodePosition, NodeData } from '../renderer/DiagramRenderer.types';
import { ConnectionHandle } from '../renderer/handles/ConnectionHandles.types';
import { IConvertEngine, INodeConverter } from './ConvertEngine.types';
import { convertLineStyle } from './convertDiagram';
import { convertHandles } from './convertHandles';
import { convertLabelStyle, convertOutsideLabels } from './convertLabel';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toListNode = (
  gqlDiagram: GQLDiagram,
  gqlNode: GQLNode<GQLRectangularNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  nodeDescription: GQLNodeDescription,
  isBorderNode: boolean,
  gqlEdges: GQLEdge[]
): Node<NodeData> => {
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

  const data: NodeData = {
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
      borderStyle: convertLineStyle(style.borderStyle),
    },
    insideLabel: null,
    insideLabelLocation: null,
    outsideLabels: convertOutsideLabels(outsideLabels),
    imageURL: null,
    faded: state === GQLViewModifier.Faded,
    nodeDescription,
    defaultWidth: gqlNode.defaultWidth,
    defaultHeight: gqlNode.defaultHeight,
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePosition.WEST : null,
    labelEditable,
    positionDependentRotation: false,
    connectionHandles,
    isNew,
  };

  if (insideLabel) {
    const labelStyle = insideLabel.style;
    data.insideLabel = {
      id: insideLabel.id,
      text: insideLabel.text,
      iconURL: labelStyle.iconURL,
      isHeader: insideLabel.isHeader,
      displayHeaderSeparator: insideLabel.displayHeaderSeparator,
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
    data.insideLabelLocation = insideLabel.insideLabelLocation;
  }

  const node: Node<NodeData> = {
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
    nodeDescriptions: GQLNodeDescription[]
  ) {
    const nodeDescription = nodeDescriptions.find((description) => description.id === gqlNode.descriptionId);
    if (nodeDescription) {
      nodes.push(toListNode(gqlDiagram, gqlNode, parentNode, nodeDescription, isBorderNode, gqlEdges));
    }
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
    adaptChildrenBorderNodes(nodes, gqlNode.childNodes ?? []);
  }
}
