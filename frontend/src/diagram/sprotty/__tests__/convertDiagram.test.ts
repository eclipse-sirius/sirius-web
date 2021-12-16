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
import { httpOrigin } from 'common/URL';
import { GQLImageNodeStyle, GQLINodeStyle } from 'diagram/DiagramWebSocketContainer.types';
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
import { convertDiagram } from '../convertDiagram';
import { resizeFeature } from '../resize/model';
import { siriusWebDiagram } from './siriusWebDiagram';

const isImageNodeStyle = (nodeStyle: GQLINodeStyle): nodeStyle is GQLImageNodeStyle =>
  nodeStyle.__typename === 'ImageNodeStyle';

describe('ModelConverter', () => {
  it('converts a diagram', () => {
    const sprottyDiagram = convertDiagram(siriusWebDiagram, httpOrigin, false, false);

    expect(sprottyDiagram).not.toBeNull();
    expect(sprottyDiagram).not.toBeUndefined();

    expect(Object.keys(sprottyDiagram)).toStrictEqual([
      'id',
      'kind',
      'type',
      'targetObjectId',
      'position',
      'features',
      'size',
      'autoLayout',
      'children',
    ]);

    expect(sprottyDiagram.id).toBe(siriusWebDiagram.id);
    expect(sprottyDiagram.kind).toBe(siriusWebDiagram.kind);
    expect(sprottyDiagram.type).toBe('graph');
    expect(sprottyDiagram.targetObjectId).toBe(siriusWebDiagram.targetObjectId);
    expect(sprottyDiagram.position).toBe(siriusWebDiagram.position);
    expect(sprottyDiagram.size).toBe(siriusWebDiagram.size);

    const expectedDiagramChildrenLength = siriusWebDiagram.nodes.length + siriusWebDiagram.edges.length;
    expect(sprottyDiagram.children).toHaveLength(expectedDiagramChildrenLength);

    for (let index = 0; index < sprottyDiagram.children.length; index++) {
      if (index < siriusWebDiagram.nodes.length) {
        const sprottyNode = sprottyDiagram.children[index];
        const odWebNode = siriusWebDiagram.nodes[index];

        const { id, type, targetObjectId, descriptionId, style, size, position } = odWebNode;
        expect(Object.keys(sprottyNode)).toStrictEqual([
          'id',
          'type',
          'targetObjectId',
          'targetObjectKind',
          'targetObjectLabel',
          'descriptionId',
          'style',
          'size',
          'position',
          'features',
          'editableLabel',
          'children',
        ]);
        expect(sprottyNode.id).toBe(id);
        expect(sprottyNode.type).toBe(type);
        expect(sprottyNode.targetObjectId).toBe(targetObjectId);
        expect(sprottyNode.descriptionId).toBe(descriptionId);
        let convertedStyle;
        if (isImageNodeStyle(style)) {
          convertedStyle = { ...style, imageURL: httpOrigin + style.imageURL };
        } else {
          convertedStyle = style;
        }
        expect(sprottyNode.style).toStrictEqual(convertedStyle);
        expect((sprottyNode as any).size).toBe(size);
        expect((sprottyNode as any).position).toBe(position);
        expect(sprottyNode.features).toStrictEqual(
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

        const expectedNodeChildrenLength = 1 + odWebNode.borderNodes.length + odWebNode.childNodes.length;
        expect(sprottyNode.children).toHaveLength(expectedNodeChildrenLength);

        expect(sprottyNode.children[0].id).toBe(odWebNode.label.id);
        expect(sprottyNode.children[0].type).toBe(odWebNode.label.type);
        expect(sprottyNode.children[0].text).toBe(odWebNode.label.text);
      } else {
        const sprottyEdge = sprottyDiagram.children[index];
        const odWebEdge = siriusWebDiagram.edges[index - siriusWebDiagram.nodes.length];

        expect(Object.keys(sprottyEdge)).toStrictEqual([
          'id',
          'type',
          'targetObjectId',
          'targetObjectKind',
          'targetObjectLabel',
          'descriptionId',
          'sourceId',
          'targetId',
          'style',
          'routingPoints',
          'features',
          'editableLabel',
          'children',
        ]);

        expect(sprottyEdge.id).toBe(odWebEdge.id);
        expect(sprottyEdge.type).toBe(odWebEdge.type);
        expect(sprottyEdge.targetObjectId).toBe(odWebEdge.targetObjectId);
        expect(sprottyEdge.descriptionId).toBe(odWebEdge.descriptionId);
        expect((sprottyEdge as any).sourceId).toBe(odWebEdge.sourceId);
        expect((sprottyEdge as any).targetId).toBe(odWebEdge.targetId);
        expect(sprottyEdge.style).toBe(odWebEdge.style);
        expect((sprottyEdge as any).routingPoints).toBe(odWebEdge.routingPoints);
        expect(sprottyEdge.features).toStrictEqual(
          createFeatureSet([deletableFeature, selectFeature, fadeFeature, hoverFeedbackFeature])
        );
      }
    }
  });
});
