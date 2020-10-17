/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
  createFeatureSet,
  connectableFeature,
  deletableFeature,
  selectFeature,
  boundsFeature,
  layoutContainerFeature,
  fadeFeature,
  hoverFeedbackFeature,
  popupFeature,
} from 'sprotty';
import { convertDiagram } from '../convertDiagram';

import siriusWebDiagram from './siriusWebDiagram.json';

describe('ModelConverter', () => {
  it('converts a diagram', () => {
    const sprottyDiagram = convertDiagram(siriusWebDiagram);

    expect(sprottyDiagram).not.toBeNull();
    expect(sprottyDiagram).not.toBeUndefined();

    expect(Object.keys(sprottyDiagram)).toStrictEqual([
      'id',
      'kind',
      'type',
      'targetObjectId',
      'label',
      'position',
      'size',
      'children',
    ]);

    expect(sprottyDiagram.id).toBe(siriusWebDiagram.id);
    expect(sprottyDiagram.kind).toBe(siriusWebDiagram.kind);
    expect(sprottyDiagram.type).toBe('graph');
    expect(sprottyDiagram.targetObjectId).toBe(siriusWebDiagram.targetObjectId);
    expect(sprottyDiagram.label).toBe(siriusWebDiagram.label);
    expect(sprottyDiagram.position).toBe(siriusWebDiagram.position);
    expect(sprottyDiagram.size).toBe(siriusWebDiagram.size);

    const expectedDiagramChildrenLength = siriusWebDiagram.nodes.length + siriusWebDiagram.edges.length;
    expect(sprottyDiagram.children).toHaveLength(expectedDiagramChildrenLength);

    for (let index = 0; index < sprottyDiagram.children.length; index++) {
      if (index < siriusWebDiagram.nodes.length) {
        const sprottyNode = sprottyDiagram.children[index];
        const siriusWebNode = siriusWebDiagram.nodes[index];

        const { id, type, targetObjectId, descriptionId, style, size, position } = siriusWebNode;
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
        expect(sprottyNode.style).toBe(style);
        expect(sprottyNode.size).toBe(size);
        expect(sprottyNode.position).toBe(position);
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
          ])
        );

        const expectedNodeChildrenLength = 1 + siriusWebNode.borderNodes.length + siriusWebNode.childNodes.length;
        expect(sprottyNode.children).toHaveLength(expectedNodeChildrenLength);

        expect(sprottyNode.children[0].id).toBe(siriusWebNode.label.id);
        expect(sprottyNode.children[0].type).toBe(siriusWebNode.label.type);
        expect(sprottyNode.children[0].text).toBe(siriusWebNode.label.text);
      } else {
        const sprottyEdge = sprottyDiagram.children[index];
        const siriusWebEdge = siriusWebDiagram.edges[index - siriusWebDiagram.nodes.length];

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
          'children',
        ]);

        expect(sprottyEdge.id).toBe(siriusWebEdge.id);
        expect(sprottyEdge.type).toBe(siriusWebEdge.type);
        expect(sprottyEdge.targetObjectId).toBe(siriusWebEdge.targetObjectId);
        expect(sprottyEdge.descriptionId).toBe(siriusWebEdge.descriptionId);
        expect(sprottyEdge.sourceId).toBe(siriusWebEdge.sourceId);
        expect(sprottyEdge.targetId).toBe(siriusWebEdge.targetId);
        expect(sprottyEdge.style).toBe(siriusWebEdge.style);
        expect(sprottyEdge.routingPoints).toBe(siriusWebEdge.routingPoints);
        expect(sprottyEdge.features).toStrictEqual(
          createFeatureSet([deletableFeature, selectFeature, fadeFeature, hoverFeedbackFeature])
        );
      }
    }
  });
});
