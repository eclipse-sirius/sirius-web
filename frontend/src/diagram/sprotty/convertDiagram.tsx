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
  boundsFeature,
  connectableFeature,
  createFeatureSet,
  deletableFeature,
  fadeFeature,
  hoverFeedbackFeature,
  layoutContainerFeature,
  popupFeature,
  selectFeature,
  viewportFeature,
} from 'sprotty';

/**
 * Convert the given diagram object to a Sprotty diagram.
 *
 * SiriusWeb diagram and Sprotty diagram does not match exactly the same API.
 * This converter will ensure the creation of a proper Sprotty diagram from a given Sirius Web diagram..
 *
 * @param diagram the diagram object to convert
 * @param httpOrigin the URL of the server hosting the images
 * @return a Sprotty diagram object
 */
export const convertDiagram = (diagram, httpOrigin: string) => {
  const { id, descriptionId, kind, targetObjectId, label, position, size } = diagram;
  const nodes = diagram.nodes.map((node) => convertNode(node, httpOrigin));
  const edges = diagram.edges.map((edge) => convertEdge(edge, httpOrigin));

  return {
    id,
    descriptionId,
    kind,
    type: 'graph',
    targetObjectId,
    label,
    position,
    features: createFeatureSet([hoverFeedbackFeature, viewportFeature]),
    size,
    children: [...nodes, ...edges],
  };
};

const convertNode = (node, httpOrigin: string) => {
  const {
    id,
    type,
    targetObjectId,
    targetObjectKind,
    targetObjectLabel,
    descriptionId,
    label,
    style,
    size,
    position,
  } = node;

  let borderNodes = [];
  if (node.borderNodes) {
    borderNodes = node.borderNodes.map((borderNode) => convertNode(borderNode, httpOrigin));
  }
  let childNodes = [];
  if (node.childNodes) {
    childNodes = node.childNodes.map((childNode) => convertNode(childNode, httpOrigin));
  }

  const convertedLabel = convertLabel(label, httpOrigin);
  const convertedStyle = convertNodeStyle(style, httpOrigin);

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
    features: createFeatureSet([
      connectableFeature,
      deletableFeature,
      selectFeature,
      boundsFeature,
      layoutContainerFeature,
      fadeFeature,
      hoverFeedbackFeature,
      popupFeature,
    ]),
    editableLabel: convertedLabel,
    children: [convertedLabel, ...borderNodes, ...childNodes],
  };
};

const convertLabel = (label, httpOrigin: string) => {
  if (label?.style?.iconURL !== undefined && label?.style?.iconURL !== '') {
    const { style } = label;
    return { ...label, style: { ...style, iconURL: httpOrigin + style.iconURL } };
  }
  return label;
};

const convertNodeStyle = (style, httpOrigin: string) => {
  if (style?.imageURL !== undefined && style?.imageURL !== '') {
    return { ...style, imageURL: httpOrigin + style.imageURL };
  }
  return style;
};

const convertEdge = (edge, httpOrigin: string) => {
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

  let children = [];
  if (beginLabel) {
    const convertedBeginLabel = convertLabel(beginLabel, httpOrigin);
    children.push(convertedBeginLabel);
  }
  if (centerLabel) {
    const convertedCenterLabel = convertLabel(centerLabel, httpOrigin);
    children.push(convertedCenterLabel);
  }
  if (endLabel) {
    const convertedEndLabel = convertLabel(endLabel, httpOrigin);
    children.push(convertedEndLabel);
  }

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
    features: createFeatureSet([deletableFeature, selectFeature, fadeFeature, hoverFeedbackFeature]),
    children: children,
  };
};
