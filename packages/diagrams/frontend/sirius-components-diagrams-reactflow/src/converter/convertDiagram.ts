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

import { Edge, Node, XYPosition } from 'reactflow';
import { GQLDiagram } from '../graphql/subscription/diagramFragment.types';
import { GQLLabel, GQLLabelStyle } from '../graphql/subscription/labelFragment.types';
import {
  GQLIconLabelNodeStyle,
  GQLImageNodeStyle,
  GQLNode,
  GQLNodeStyle,
  GQLRectangularNodeStyle,
  GQLViewModifier,
} from '../graphql/subscription/nodeFragment.types';
import { BorderNodePositon, Diagram, Label, NodeData } from '../renderer/DiagramRenderer.types';
import { MultiLabelEdgeData } from '../renderer/edge/MultiLabelEdge.types';
import { IconLabelNodeData } from '../renderer/node/IconsLabelNode.types';
import { ImageNodeData } from '../renderer/node/ImageNode.types';
import { ListNodeData } from '../renderer/node/ListNode.types';
import { DiagramNodeType } from '../renderer/node/NodeTypes.types';
import { RectangularNodeData } from '../renderer/node/RectangularNode.types';

const defaultPosition: XYPosition = { x: 0, y: 0 };

const toRectangularNode = (
  gqlNode: GQLNode<GQLRectangularNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  isBorderNode: boolean
): Node<RectangularNodeData> => {
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

  const data: RectangularNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    style: {
      backgroundColor: style.color,
      borderColor: style.borderColor,
      borderRadius: style.borderRadius,
      borderWidth: style.borderSize,
      borderStyle: style.borderStyle,
    },
    label: undefined,
    faded: state === GQLViewModifier.Faded,
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePositon.EAST : null,
    labelEditable,
  };

  if (insideLabel) {
    const labelStyle = insideLabel.style;
    data.label = {
      id: insideLabel.id,
      text: insideLabel.text,
      style: {
        textAlign: 'center',
        ...convertLabelStyle(labelStyle),
      },
      iconURL: labelStyle.iconURL,
    };
    const verticalAlignmentIndex = insideLabel.type.indexOf('v_');
    const horizontalAlignmentIndex = insideLabel.type.indexOf('-h_');
    const verticalAlignment = insideLabel.type.substring(
      verticalAlignmentIndex + 'v_'.length,
      horizontalAlignmentIndex
    );
    const horizontalAliment = insideLabel.type.substring(horizontalAlignmentIndex + '-h_'.length);

    if (verticalAlignment === 'top') {
      data.label.style.alignSelf = 'flex-start';
    } else if (verticalAlignment === 'center') {
      data.label.style.alignSelf = 'center';
    } else if (verticalAlignment === 'bottom') {
      data.label.style.alignSelf = 'flex-end';
    }

    if (horizontalAliment === 'start') {
      data.label.style.marginRight = 'auto';
    } else if (horizontalAliment === 'center') {
      data.label.style.marginLeft = 'auto';
      data.label.style.marginRight = 'auto';
    } else if (horizontalAliment === 'end') {
      data.label.style.marginLeft = 'auto';
    }

    if (insideLabel.type === 'label:inside-center') {
      data.label.style.marginLeft = 'auto';
      data.label.style.marginRight = 'auto';
    }
  }

  const node: Node<RectangularNodeData> = {
    id,
    type: 'rectangularNode',
    data,
    position: defaultPosition,
    hidden: state === GQLViewModifier.Hidden,
  };

  if (gqlParentNode) {
    node.parentNode = gqlParentNode.id;
  }

  return node;
};

const toIconLabelNode = (
  gqlNode: GQLNode<GQLIconLabelNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  isBorderNode: boolean
): Node<IconLabelNodeData> => {
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

  const data: IconLabelNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    style: {
      textAlign: 'left',
      backgroundColor: style.backgroundColor,
    },
    label: undefined,
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePositon.WEST : null,
    faded: state === GQLViewModifier.Faded,
    labelEditable: labelEditable,
  };

  if (insideLabel) {
    const labelStyle = insideLabel.style;

    data.label = {
      id: insideLabel.id,
      text: insideLabel.text,
      style: {
        ...convertLabelStyle(labelStyle),
      },
      iconURL: labelStyle.iconURL,
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

  return node;
};

const toListNode = (
  gqlNode: GQLNode<GQLRectangularNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
  isBorderNode: boolean
): Node<ListNodeData> => {
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

  const data: ListNodeData = {
    targetObjectId,
    targetObjectLabel,
    targetObjectKind,
    descriptionId,
    style: {
      backgroundColor: style.color,
      borderColor: style.borderColor,
      borderRadius: style.borderRadius,
      borderWidth: style.borderSize,
      borderStyle: style.borderStyle,
    },
    label: undefined,
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePositon.WEST : null,
    faded: state === GQLViewModifier.Faded,
    labelEditable,
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

    if (style.withHeader) {
      data.label.style.borderBottom = `${style.borderSize}px ${style.borderStyle} ${style.borderColor}`;
    }

    const verticalAlignmentIndex = insideLabel.type.indexOf('v_');
    const horizontalAlignmentIndex = insideLabel.type.indexOf('-h_');
    const verticalAlignment = insideLabel.type.substring(
      verticalAlignmentIndex + 'v_'.length,
      horizontalAlignmentIndex
    );
    const horizontalAlignment = insideLabel.type.substring(horizontalAlignmentIndex + '-h_'.length);

    if (verticalAlignment === 'top') {
      data.label.style.alignSelf = 'flex-start';
    } else if (verticalAlignment === 'center') {
      data.label.style.alignSelf = 'center';
    } else if (verticalAlignment === 'bottom') {
      data.label.style.alignSelf = 'flex-end';
    }

    if (horizontalAlignment === 'start') {
      data.label.style.marginRight = 'auto';
    } else if (horizontalAlignment === 'center') {
      data.label.style.marginLeft = 'auto';
      data.label.style.marginRight = 'auto';
    } else if (horizontalAlignment === 'end') {
      data.label.style.marginLeft = 'auto';
    }

    if (insideLabel.type === 'label:inside-center') {
      data.label.style.marginLeft = 'auto';
      data.label.style.marginRight = 'auto';
    }
  }

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

  return node;
};

const toImageNode = (
  gqlNode: GQLNode<GQLImageNodeStyle>,
  gqlParentNode: GQLNode<GQLNodeStyle> | null,
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
    isBorderNode: isBorderNode,
    borderNodePosition: isBorderNode ? BorderNodePositon.WEST : null,
    labelEditable,
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

const isRectangularNode = (gqlNode: GQLNode<GQLNodeStyle>): gqlNode is GQLNode<GQLRectangularNodeStyle> =>
  gqlNode.style.__typename === 'RectangularNodeStyle';

const isImageNode = (gqlNode: GQLNode<GQLNodeStyle>): gqlNode is GQLNode<GQLImageNodeStyle> =>
  gqlNode.style.__typename === 'ImageNodeStyle';

const isIconLabelNode = (gqlNode: GQLNode<GQLNodeStyle>): gqlNode is GQLNode<GQLIconLabelNodeStyle> =>
  gqlNode.style.__typename === 'IconLabelNodeStyle';

const convertNode = (gqlNode: GQLNode<GQLNodeStyle>, parentNode: GQLNode<GQLNodeStyle> | null, nodes: Node[]): void => {
  const isBorderNode: boolean = !!parentNode?.borderNodes?.map((borderNode) => borderNode.id).includes(gqlNode.id);

  if (isRectangularNode(gqlNode)) {
    const isList = gqlNode.childrenLayoutStrategy?.kind === 'List';
    if (!isList) {
      nodes.push(toRectangularNode(gqlNode, parentNode, isBorderNode));
      (gqlNode.borderNodes ?? []).forEach((gqlBorderNode) => convertNode(gqlBorderNode, gqlNode, nodes));
      (gqlNode.childNodes ?? []).forEach((gqlChildNode) => convertNode(gqlChildNode, gqlNode, nodes));
    } else {
      nodes.push(toListNode(gqlNode, parentNode, isBorderNode));

      (gqlNode.borderNodes ?? []).forEach((gqlBorderNode) => convertNode(gqlBorderNode, gqlNode, nodes));
      (gqlNode.childNodes ?? []).forEach((gqlChildNode) => convertNode(gqlChildNode, gqlNode, nodes));
    }
  } else if (isImageNode(gqlNode)) {
    nodes.push(toImageNode(gqlNode, parentNode, isBorderNode));

    (gqlNode.borderNodes ?? []).forEach((gqlBorderNode) => convertNode(gqlBorderNode, gqlNode, nodes));
    (gqlNode.childNodes ?? []).forEach((gqlChildNode) => convertNode(gqlChildNode, gqlNode, nodes));
  } else if (isIconLabelNode(gqlNode)) {
    nodes.push(toIconLabelNode(gqlNode, parentNode, isBorderNode));
  }
};

const nodeDepth = (nodeId2node: Map<string, Node>, nodeId: string): number => {
  const node = nodeId2node.get(nodeId);
  let depth = 0;

  let parentNode = node?.parentNode ? nodeId2node.get(node.parentNode) : undefined;
  while (parentNode) {
    depth = depth + 1;
    parentNode = parentNode.parentNode ? nodeId2node.get(parentNode.parentNode) : undefined;
  }

  return depth;
};

const convertEdgeLabel = (gqlEdgeLabel: GQLLabel): Label => {
  return {
    id: gqlEdgeLabel.id,
    text: gqlEdgeLabel.text,
    iconURL: gqlEdgeLabel.style.iconURL,
    style: {
      position: 'absolute',
      background: 'transparent',
      padding: 10,
      zIndex: 999,
      ...convertLabelStyle(gqlEdgeLabel.style),
    },
  };
};

const convertLabelStyle = (gqlLabelStyle: GQLLabelStyle): React.CSSProperties => {
  const style: React.CSSProperties = {};

  if (gqlLabelStyle.bold) {
    style.fontWeight = 'bold';
  }
  if (gqlLabelStyle.italic) {
    style.fontStyle = 'italic';
  }
  if (gqlLabelStyle.fontSize) {
    style.fontSize = gqlLabelStyle.fontSize;
  }
  if (gqlLabelStyle.color) {
    style.color = gqlLabelStyle.color;
  }

  let decoration: string = '';
  if (gqlLabelStyle.strikeThrough) {
    decoration = decoration + 'line-through';
  }
  if (gqlLabelStyle.underline) {
    const separator: string = decoration.length > 0 ? ' ' : '';
    decoration = decoration + separator + 'underline';
  }
  if (decoration.length > 0) {
    style.textDecoration = decoration;
  }

  return style;
};

export const convertDiagram = (gqlDiagram: GQLDiagram): Diagram => {
  const nodes: Node<NodeData, DiagramNodeType>[] = [];
  gqlDiagram.nodes.forEach((gqlNode) => convertNode(gqlNode, null, nodes));

  const nodeId2node = new Map<string, Node>();
  nodes.forEach((node) => nodeId2node.set(node.id, node));

  const nodeId2Depth = new Map<string, number>();
  nodes.forEach((node) => nodeId2Depth.set(node.id, nodeDepth(nodeId2node, node.id)));

  const edges: Edge[] = gqlDiagram.edges.map((gqlEdge) => {
    const data: MultiLabelEdgeData = {
      targetObjectId: gqlEdge.targetObjectId,
      targetObjectKind: gqlEdge.targetObjectKind,
      targetObjectLabel: gqlEdge.targetObjectLabel,
      label: null,
      faded: gqlEdge.state === GQLViewModifier.Faded,
    };

    if (gqlEdge.beginLabel) {
      data.beginLabel = convertEdgeLabel(gqlEdge.beginLabel);
    }
    if (gqlEdge.centerLabel) {
      data.label = convertEdgeLabel(gqlEdge.centerLabel);
    }
    if (gqlEdge.endLabel) {
      data.endLabel = convertEdgeLabel(gqlEdge.endLabel);
    }

    return {
      id: gqlEdge.id,
      type: 'multiLabelEdge',
      source: gqlEdge.sourceId,
      target: gqlEdge.targetId,
      markerEnd: `${gqlEdge.style.targetArrow}--${gqlEdge.id}--markerEnd`,
      markerStart: `${gqlEdge.style.sourceArrow}--${gqlEdge.id}--markerStart`,
      zIndex: 2000,
      style: {
        stroke: gqlEdge.style.color,
        strokeWidth: gqlEdge.style.size,
      },
      data,
      hidden: gqlEdge.state === GQLViewModifier.Hidden,
    };
  });

  return {
    metadata: {
      id: gqlDiagram.id,
      label: gqlDiagram.metadata.label,
      kind: gqlDiagram.metadata.kind,
      targetObjectId: gqlDiagram.targetObjectId,
    },
    nodes,
    edges,
  };
};
