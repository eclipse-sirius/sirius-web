/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
  GQLDiagram,
  GQLEdge,
  GQLImageNodeStyle,
  GQLINodeStyle,
  GQLLabel,
  GQLNode,
} from 'diagram/DiagramWebSocketContainer.types';
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
  viewportFeature,
  withEditLabelFeature,
} from 'sprotty';

/**
 * Convert the given diagram object to a Sprotty diagram.
 *
 * SiriusWeb diagram and Sprotty diagram does not match exactly the same API.
 * This converter will ensure the creation of a proper Sprotty diagram from a given Sirius Web diagram..
 *
 * @param diagram the diagram object to convert
 * @param httpOrigin the URL of the server hosting the images
 * @param autoLayout whether the diagram's layout is automatic (handled server-side)
 * @param readOnly whether the diagram is readonly
 * @return a Sprotty diagram object
 */
export const convertDiagram = (diagram: GQLDiagram, httpOrigin: string, autoLayout: boolean, readOnly: boolean) => {
  const { id, kind, targetObjectId, position, size } = diagram;
  const nodes = diagram.nodes.map((node) => convertNode(node, httpOrigin, readOnly, autoLayout));
  const edges = diagram.edges.map((edge) => convertEdge(edge, httpOrigin, readOnly));

  return {
    id,
    kind,
    type: 'graph',
    targetObjectId,
    position,
    features: createFeatureSet([hoverFeedbackFeature, viewportFeature]),
    size,
    autoLayout,
    children: [...nodes, ...edges],
  };
};

const convertNode = (node: GQLNode, httpOrigin: string, readOnly: boolean, autoLayout: boolean) => {
  const { id, type, targetObjectId, targetObjectKind, targetObjectLabel, descriptionId, label, style, size, position } =
    node;

  let borderNodes = [];
  if (node.borderNodes) {
    borderNodes = node.borderNodes.map((borderNode) => convertNode(borderNode, httpOrigin, readOnly, autoLayout));
  }
  let childNodes = [];
  if (node.childNodes) {
    childNodes = node.childNodes.map((childNode) => convertNode(childNode, httpOrigin, readOnly, autoLayout));
  }

  const convertedLabel = convertLabel(label, httpOrigin, readOnly);
  const convertedStyle = convertNodeStyle(style, httpOrigin);

  const features = handleNodeFeatures(node, readOnly, autoLayout);

  const editableLabel = handleEditableLabel(convertedLabel, readOnly);

  return {
    id,
    type,
    targetObjectId,
    targetObjectKind,
    targetObjectLabel,
    descriptionId,
    style: convertedStyle,
    size,
    position,
    features,
    editableLabel,
    children: [convertedLabel, ...borderNodes, ...childNodes],
  };
};

const handleEditableLabel = (label: GQLLabel, readOnly: boolean) => {
  let editableLabel = undefined;
  if (!readOnly) {
    editableLabel = label;
  }

  return editableLabel;
};

const handleNodeFeatures = (node: GQLNode, readOnly: boolean, autoLayout: boolean): FeatureSet => {
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

  if (node.type === 'node:list') {
    features.delete(resizeFeature);
  } else if (node.type === 'node:list:item') {
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

const convertLabel = (label, httpOrigin: string, readOnly: boolean) => {
  let convertedLabel = { ...label };

  if (!readOnly) {
    convertedLabel = {
      ...convertedLabel,
      features: createFeatureSet([...SLabel.DEFAULT_FEATURES, editLabelFeature]),
    };
  }

  if (convertedLabel.style?.iconURL !== undefined && convertedLabel?.style?.iconURL !== '') {
    const { style } = convertedLabel;
    return { ...convertedLabel, style: { ...style, iconURL: httpOrigin + style.iconURL } };
  }
  return convertedLabel;
};

const isImageNodeStyle = (style: GQLINodeStyle): style is GQLImageNodeStyle => style.__typename === 'ImageNodeStyle';

const convertNodeStyle = (style: GQLINodeStyle, httpOrigin: string) => {
  if (isImageNodeStyle(style)) {
    return { ...style, imageURL: httpOrigin + style.imageURL };
  }
  return style;
};

const convertEdge = (edge: GQLEdge, httpOrigin: string, readOnly: boolean) => {
  const {
    id,
    type,
    targetObjectId,
    targetObjectKind,
    targetObjectLabel,
    descriptionId,
    beginLabel,
    centerLabel,
    endLabel,
    sourceId,
    targetId,
    style,
    routingPoints,
  } = edge;

  let editableLabel;
  let children = [];
  if (beginLabel) {
    const convertedBeginLabel = convertLabel(beginLabel, httpOrigin, readOnly);
    children.push(convertedBeginLabel);
  }
  if (centerLabel) {
    const convertedCenterLabel = convertLabel(centerLabel, httpOrigin, readOnly);
    editableLabel = handleEditableLabel(convertedCenterLabel, readOnly);
    children.push(convertedCenterLabel);
  }
  if (endLabel) {
    const convertedEndLabel = convertLabel(endLabel, httpOrigin, readOnly);
    children.push(convertedEndLabel);
  }

  const features = handleEdgeFeatures(readOnly);

  return {
    id,
    type,
    targetObjectId,
    targetObjectKind,
    targetObjectLabel,
    descriptionId,
    sourceId,
    targetId,
    style,
    routingPoints,
    features,
    editableLabel,
    children: children,
  };
};

const handleEdgeFeatures = (readOnly: boolean): FeatureSet => {
  if (readOnly) {
    return createFeatureSet([selectFeature, fadeFeature, hoverFeedbackFeature]);
  }

  return createFeatureSet([deletableFeature, selectFeature, fadeFeature, hoverFeedbackFeature]);
};
