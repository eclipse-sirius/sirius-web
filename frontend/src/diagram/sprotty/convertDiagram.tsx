/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
  GQLArrowStyle,
  GQLDiagram,
  GQLEdge,
  GQLImageNodeStyle,
  GQLINodeStyle,
  GQLLabel,
  GQLLineStyle,
  GQLListItemNodeStyle,
  GQLListNodeStyle,
  GQLNode,
  GQLRectangularNodeStyle,
} from 'diagram/DiagramWebSocketContainer.types';
import {
  ArrowStyle,
  BorderNode,
  Diagram,
  Edge,
  EdgeStyle,
  ImageNodeStyle,
  INodeStyle,
  Label,
  LabelStyle,
  LineStyle,
  ListItemNodeStyle,
  ListNodeStyle,
  Node,
  RectangularNodeStyle,
} from 'diagram/sprotty/Diagram.types';
import { resizeFeature } from 'diagram/sprotty/resize/model';
import {
  boundsFeature,
  connectableFeature,
  createFeatureSet,
  deletableFeature,
  editLabelFeature,
  fadeFeature,
  FeatureSet,
  hoverFeedbackFeature,
  layoutContainerFeature,
  moveFeature,
  popupFeature,
  selectFeature,
  SLabel,
  SParentElement,
  viewportFeature,
  withEditLabelFeature,
} from 'sprotty';

/**
 * Convert the given diagram object to a Sprotty diagram.
 *
 * SiriusWeb diagram and Sprotty diagram does not match exactly the same API.
 * This converter will ensure the creation of a proper Sprotty diagram from a given Sirius Web diagram..
 *
 * @param gqlDiagram the diagram object to convert
 * @param httpOrigin the URL of the server hosting the images
 * @param readOnly Whether the diagram is readonly
 * @return a Sprotty diagram object
 */
export const convertDiagram = (gqlDiagram: GQLDiagram, httpOrigin: string, readOnly: boolean): Diagram => {
  const {
    id,
    metadata: { label, kind, description },
    targetObjectId,
    autoLayout,
    nodes,
    edges,
  } = gqlDiagram;

  const diagram = new Diagram();
  diagram.id = id;
  diagram.type = 'graph';
  diagram.kind = kind;
  diagram.label = label;
  diagram.descriptionId = description.id;
  diagram.targetObjectId = targetObjectId;
  diagram.features = createFeatureSet([hoverFeedbackFeature, viewportFeature]);

  nodes.map((node) => convertNode(diagram, node, httpOrigin, readOnly, autoLayout));
  edges.map((edge) => convertEdge(diagram, edge, httpOrigin, readOnly));

  return diagram;
};

const convertNode = (
  parentElement: SParentElement,
  gqlNode: GQLNode,
  httpOrigin: string,
  readOnly: boolean,
  autoLayout: boolean
): Node => {
  const {
    id,
    label,
    descriptionId,
    type,
    targetObjectId,
    targetObjectKind,
    targetObjectLabel,
    size,
    position,
    style,
    borderNodes,
    childNodes,
  } = gqlNode;

  const node: Node = new Node();
  parentElement.add(node);

  const convertedLabel = convertLabel(node, label, httpOrigin, readOnly);
  (borderNodes ?? []).map((borderNode) => convertBorderNode(node, borderNode, httpOrigin, readOnly, autoLayout));
  (childNodes ?? []).map((childNode) => convertNode(node, childNode, httpOrigin, readOnly, autoLayout));

  node.id = id;
  node.type = type;
  node.kind = `siriusComponents://graphical?representationType=Diagram&type=Node`;
  node.descriptionId = descriptionId;
  node.style = convertNodeStyle(style, httpOrigin);
  node.editableLabel = !readOnly ? convertedLabel : null;
  node.targetObjectId = targetObjectId;
  node.targetObjectKind = targetObjectKind;
  node.targetObjectLabel = targetObjectLabel;
  node.position = position;
  node.size = size;
  node.features = handleNodeFeatures(gqlNode, readOnly, autoLayout);

  return node;
};
const convertBorderNode = (
  parentElement: SParentElement,
  gqlNode: GQLNode,
  httpOrigin: string,
  readOnly: boolean,
  autoLayout: boolean
): BorderNode => {
  const { id, label, descriptionId, type, targetObjectId, targetObjectKind, targetObjectLabel, size, position, style } =
    gqlNode;

  const node: BorderNode = new BorderNode();
  parentElement.add(node);

  const convertedLabel = convertLabel(node, label, httpOrigin, readOnly);

  node.id = id;
  node.type = type.replace('node:', 'port:');
  node.kind = `siriusComponents://graphical?representationType=Diagram&type=Node`;
  node.descriptionId = descriptionId;
  node.style = convertNodeStyle(style, httpOrigin);
  node.editableLabel = !readOnly ? convertedLabel : null;
  node.targetObjectId = targetObjectId;
  node.targetObjectKind = targetObjectKind;
  node.targetObjectLabel = targetObjectLabel;
  node.position = position;
  node.size = size;
  node.features = handleNodeFeatures(gqlNode, readOnly, autoLayout);
  return node;
};

const handleNodeFeatures = (gqlNode: GQLNode, readOnly: boolean, autoLayout: boolean): FeatureSet => {
  const features = new Set<symbol>([
    connectableFeature,
    deletableFeature,
    selectFeature,
    boundsFeature,
    layoutContainerFeature,
    fadeFeature,
    hoverFeedbackFeature,
    popupFeature,
    moveFeature,
    resizeFeature,
    withEditLabelFeature,
  ]);

  if (gqlNode.type === 'node:list') {
    features.delete(resizeFeature);
  } else if (gqlNode.type === 'node:list:item') {
    features.delete(resizeFeature);
    features.delete(connectableFeature);
    features.delete(moveFeature);
  }

  if (readOnly) {
    features.delete(resizeFeature);
    features.delete(moveFeature);
    features.delete(withEditLabelFeature);
  }

  if (autoLayout) {
    features.delete(resizeFeature);
    features.delete(moveFeature);
  }

  return features;
};

const convertLabel = (
  parentElement: SParentElement,
  gqlLabel: GQLLabel,
  httpOrigin: string,
  readOnly: boolean
): Label => {
  const { id, text, type, style, alignment, position, size } = gqlLabel;

  const label: Label = new Label();
  parentElement.add(label);
  label.id = id;
  label.text = text;
  label.type = type;
  label.alignment = alignment;
  label.position = position;
  label.size = size;

  if (!readOnly) {
    label.features = createFeatureSet([...SLabel.DEFAULT_FEATURES, editLabelFeature]);
  }

  const { bold, color, fontSize, iconURL, italic, strikeThrough, underline } = style;

  const labelStyle: LabelStyle = new LabelStyle();
  labelStyle.bold = bold;
  labelStyle.color = color;
  labelStyle.fontSize = fontSize;
  labelStyle.iconURL = iconURL;
  labelStyle.italic = italic;
  labelStyle.strikeThrough = strikeThrough;
  labelStyle.underline = underline;

  if (labelStyle.iconURL?.length ?? 0 > 0) {
    labelStyle.iconURL = httpOrigin + labelStyle.iconURL;
  }

  label.style = labelStyle;

  return label;
};

const isImageNodeStyle = (style: GQLINodeStyle): style is GQLImageNodeStyle => style.__typename === 'ImageNodeStyle';
const isRectangularNodeStyle = (style: GQLINodeStyle): style is GQLRectangularNodeStyle =>
  style.__typename === 'RectangularNodeStyle';
const isListNodeStyle = (style: GQLINodeStyle): style is GQLListNodeStyle => style.__typename === 'ListNodeStyle';
const isListItemNodeStyle = (style: GQLINodeStyle): style is GQLListItemNodeStyle =>
  style.__typename === 'ListItemNodeStyle';

const convertNodeStyle = (style: GQLINodeStyle, httpOrigin: string): INodeStyle | null => {
  let convertedStyle: INodeStyle | null = null;

  if (isImageNodeStyle(style)) {
    const { imageURL, borderColor, borderSize, borderStyle, borderRadius } = style;

    const imageNodeStyle = new ImageNodeStyle();
    imageNodeStyle.imageURL = httpOrigin + imageURL;
    imageNodeStyle.borderColor = borderColor;
    imageNodeStyle.borderSize = borderSize;
    imageNodeStyle.borderRadius = borderRadius;
    imageNodeStyle.borderStyle = LineStyle[GQLLineStyle[borderStyle]];

    convertedStyle = imageNodeStyle;
  } else if (isRectangularNodeStyle(style)) {
    const { color, borderColor, borderRadius, borderSize, borderStyle } = style;

    const rectangularNodeStyle = new RectangularNodeStyle();
    rectangularNodeStyle.color = color;
    rectangularNodeStyle.borderColor = borderColor;
    rectangularNodeStyle.borderRadius = borderRadius;
    rectangularNodeStyle.borderSize = borderSize;
    rectangularNodeStyle.borderStyle = LineStyle[GQLLineStyle[borderStyle]];

    convertedStyle = rectangularNodeStyle;
  } else if (isListNodeStyle(style)) {
    const { color, borderColor, borderRadius, borderSize, borderStyle } = style;

    const listNodeStyle = new ListNodeStyle();
    listNodeStyle.color = color;
    listNodeStyle.borderColor = borderColor;
    listNodeStyle.borderRadius = borderRadius;
    listNodeStyle.borderSize = borderSize;
    listNodeStyle.borderStyle = LineStyle[GQLLineStyle[borderStyle]];

    convertedStyle = listNodeStyle;
  } else if (isListItemNodeStyle(style)) {
    const { backgroundColor } = style;

    const listItemNodeStyle = new ListItemNodeStyle();
    listItemNodeStyle.backgroundColor = backgroundColor;

    convertedStyle = listItemNodeStyle;
  }
  return convertedStyle;
};

const convertEdge = (diagram: Diagram, gqlEdge: GQLEdge, httpOrigin: string, readOnly: boolean): Edge => {
  const {
    id,
    type,
    sourceId,
    targetId,
    routingPoints,
    descriptionId,
    targetObjectId,
    targetObjectKind,
    targetObjectLabel,
    beginLabel,
    centerLabel,
    endLabel,
    style,
  } = gqlEdge;

  const edgeStyle = new EdgeStyle();
  edgeStyle.color = style.color;
  edgeStyle.size = style.size;
  edgeStyle.lineStyle = LineStyle[GQLLineStyle[style.lineStyle]];
  edgeStyle.sourceArrow = ArrowStyle[GQLArrowStyle[style.sourceArrow]];
  edgeStyle.targetArrow = ArrowStyle[GQLArrowStyle[style.targetArrow]];

  const edge = new Edge();
  diagram.add(edge);

  if (beginLabel) {
    convertLabel(edge, beginLabel, httpOrigin, readOnly);
  }
  const convertedCenterLabel: Label | null = centerLabel ? convertLabel(edge, centerLabel, httpOrigin, readOnly) : null;
  if (endLabel) {
    convertLabel(edge, endLabel, httpOrigin, readOnly);
  }

  edge.id = id;
  edge.type = type;
  edge.kind = `siriusComponents://graphical?representationType=Diagram&type=Edge`;
  edge.sourceId = sourceId;
  edge.targetId = targetId;
  edge.routingPoints = routingPoints;
  edge.editableLabel = !readOnly ? convertedCenterLabel : null;
  edge.descriptionId = descriptionId;
  edge.style = edgeStyle;
  edge.targetObjectId = targetObjectId;
  edge.targetObjectKind = targetObjectKind;
  edge.targetObjectLabel = targetObjectLabel;
  edge.features = handleEdgeFeatures(readOnly);

  return edge;
};

const handleEdgeFeatures = (readOnly: boolean): FeatureSet => {
  if (readOnly) {
    return createFeatureSet([selectFeature, fadeFeature, hoverFeedbackFeature]);
  }

  return createFeatureSet([deletableFeature, selectFeature, fadeFeature, hoverFeedbackFeature]);
};
