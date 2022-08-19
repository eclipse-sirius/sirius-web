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
import 'reflect-metadata';
import {
  boundsFeature,
  connectableFeature,
  createFeatureSet,
  deletableFeature,
  fadeFeature,
  hoverFeedbackFeature,
  layoutContainerFeature,
  moveFeature,
  popupFeature,
  selectFeature,
  withEditLabelFeature,
} from 'sprotty';
import { expect, test } from 'vitest';
import {
  GQLImageNodeStyle,
  GQLINodeStyle,
  GQLRectangularNodeStyle,
} from '../../representation/DiagramRepresentation.types';
import { convertDiagram } from '../convertDiagram';
import { Diagram, Edge, ImageNodeStyle, Label, Node, RectangularNodeStyle } from '../Diagram.types';
import { resizeFeature } from '../resize/model';
import { siriusWebDiagram } from './siriusWebDiagram';

const isImageNodeStyle = (nodeStyle: GQLINodeStyle): nodeStyle is GQLImageNodeStyle =>
  nodeStyle.__typename === 'ImageNodeStyle';

const isRectangleNodeStyle = (nodeStyle: GQLINodeStyle): nodeStyle is GQLRectangularNodeStyle =>
  nodeStyle.__typename === 'RectangularNodeStyle';

const httpOrigin = 'http://localhost';

test('converts a diagram', () => {
  const sprottyDiagram = convertDiagram(siriusWebDiagram, httpOrigin, false);

  expect(sprottyDiagram).not.toBeNull();
  expect(sprottyDiagram).not.toBeUndefined();

  expect(sprottyDiagram).toBeInstanceOf(Diagram);

  expect(sprottyDiagram.id).toBe(siriusWebDiagram.id);
  expect(sprottyDiagram.kind).toBe(siriusWebDiagram.metadata.kind);
  expect(sprottyDiagram.type).toBe('graph');
  expect(sprottyDiagram.targetObjectId).toBe(siriusWebDiagram.targetObjectId);
  expect(sprottyDiagram.label).toBe(siriusWebDiagram.metadata.label);

  const expectedDiagramChildrenLength = siriusWebDiagram.nodes.length + siriusWebDiagram.edges.length;
  expect(sprottyDiagram.children).toHaveLength(expectedDiagramChildrenLength);

  for (let index = 0; index < sprottyDiagram.children.length; index++) {
    if (index < siriusWebDiagram.nodes.length) {
      const sprottyNode = sprottyDiagram.children[index];
      const gqlNode = siriusWebDiagram.nodes[index];

      const { id, type, targetObjectId, descriptionId, style, size, position } = gqlNode;
      expect(sprottyNode).toBeInstanceOf(Node);

      const node: Node = sprottyNode as Node;
      expect(node.id).toBe(id);
      expect(node.type).toBe(type);
      expect(node.targetObjectId).toBe(targetObjectId);
      expect(node.descriptionId).toBe(descriptionId);

      if (isImageNodeStyle(style)) {
        expect(node.style).toBeInstanceOf(ImageNodeStyle);
        if (node.style instanceof ImageNodeStyle) {
          expect(node.style.imageURL).toStrictEqual(httpOrigin + style.imageURL);
          expect(node.style.borderColor).toStrictEqual(style.borderColor);
          expect(node.style.borderSize).toStrictEqual(style.borderSize);
          expect(node.style.borderRadius).toStrictEqual(style.borderRadius);
          expect(node.style.borderStyle).toStrictEqual(style.borderStyle);
        }
      } else if (isRectangleNodeStyle(style)) {
        expect(node.style).toBeInstanceOf(RectangularNodeStyle);
        if (node.style instanceof RectangularNodeStyle) {
          expect(node.style.borderColor).toStrictEqual(style.borderColor);
          expect(node.style.borderSize).toStrictEqual(style.borderSize);
          expect(node.style.borderRadius).toStrictEqual(style.borderRadius);
          expect(node.style.borderStyle).toStrictEqual(style.borderStyle);
        }
      }

      expect((node as any).size).toBe(size);
      expect((node as any).position).toBe(position);
      expect(node.features).toStrictEqual(
        createFeatureSet([
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
        ])
      );

      const expectedNodeChildrenLength = 1 + gqlNode.borderNodes.length + gqlNode.childNodes.length;
      expect(sprottyNode.children).toHaveLength(expectedNodeChildrenLength);

      const label = node.children[0] as Label;

      expect(label.id).toBe(gqlNode.label.id);
      expect(label.type).toBe(gqlNode.label.type);
      expect(label.text).toBe(gqlNode.label.text);
    } else {
      const sprottyEdge = sprottyDiagram.children[index];
      const odWebEdge = siriusWebDiagram.edges[index - siriusWebDiagram.nodes.length];

      expect(sprottyEdge).toBeInstanceOf(Edge);

      const edge: Edge = sprottyEdge as Edge;

      expect(edge.id).toBe(odWebEdge.id);
      expect(edge.type).toBe(odWebEdge.type);
      expect(edge.targetObjectId).toBe(odWebEdge.targetObjectId);
      expect(edge.descriptionId).toBe(odWebEdge.descriptionId);
      expect((edge as any).sourceId).toBe(odWebEdge.sourceId);
      expect((edge as any).targetId).toBe(odWebEdge.targetId);
      expect((edge as any).routingPoints).toBe(odWebEdge.routingPoints);
      expect(edge.features).toStrictEqual(
        createFeatureSet([deletableFeature, selectFeature, fadeFeature, hoverFeedbackFeature])
      );
    }
  }
});
