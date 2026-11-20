/******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Node, ReactFlowProvider } from '@xyflow/react';
import { CSSProperties } from 'react';
import { flushSync } from 'react-dom';
import { createRoot } from 'react-dom/client';
import {
  InsideLabel,
  NodeData,
} from '../../../../packages/diagrams/frontend/sirius-components-diagrams/src/renderer/DiagramRenderer.types';
import { LayoutEngine } from '../../../../packages/diagrams/frontend/sirius-components-diagrams/src/renderer/layout/LayoutEngine';
import {
  createInsideLabelMeasurer,
  prepareLayoutArea,
  prepareLayoutLabels,
} from '../../../../packages/diagrams/frontend/sirius-components-diagrams/src/renderer/layout/layout';
import { getNodeInnerBorderRadius } from '../../../../packages/diagrams/frontend/sirius-components-diagrams/src/renderer/node/getNodeInnerBorderRadius';
import { NodeInsideLabel } from '../../../../packages/diagrams/frontend/sirius-components-diagrams/src/renderer/node/NodeInsideLabel';

export const measureHeaderGap = async (type: 'freeFormNode' | 'listNode') => {
  const style: CSSProperties = { borderWidth: 1, borderStyle: 'solid' };
  const insideLabel: InsideLabel = {
    id: 'header',
    text: 'Header',
    isHeader: true,
    headerPosition: 'TOP',
    overflowStrategy: 'NONE',
    displayHeaderSeparator: false,
    headerSeparatorStyle: {},
    style: { padding: '8px 16px' },
    contentStyle: { background: 'red' },
    iconURL: [],
    width: 0,
    height: 0,
    appearanceData: {
      customizedStyleProperties: [],
      gqlStyle: {
        color: 'black',
        fontSize: 16,
        bold: false,
        italic: false,
        underline: false,
        strikeThrough: false,
        iconURL: [],
        background: 'red',
        borderColor: 'black',
        borderStyle: 'None',
        borderSize: 0,
        borderRadius: 0,
        maxWidth: '',
        visibility: 'visible',
      },
    },
  };
  const node: Node<NodeData, typeof type> = {
    id: 'node',
    type,
    position: { x: 0, y: 0 },
    width: 120,
    data: {
      targetObjectId: 'object',
      targetObjectKind: 'fixture:Node',
      targetObjectLabel: 'Header',
      descriptionId: 'description',
      insideLabel,
      outsideLabels: {},
      faded: false,
      pinned: false,
      nodeDescription: {
        id: 'description',
        borderNodeDescriptionIds: [],
        childNodeDescriptionIds: [],
        userResizable: 'BOTH',
        keepAspectRatio: false,
      },
      defaultWidth: 120,
      defaultHeight: 0,
      isBorderNode: false,
      borderNodePosition: null,
      labelEditable: false,
      deletable: false,
      style,
      nodeBorderWidths: null,
      connectionHandles: [],
      isNew: true,
      resizedByUser: false,
      movedByUser: false,
      isListChild: false,
      isDraggedNode: false,
      isDropNodeTarget: false,
      isDragNodeSource: false,
      isDropNodeCandidate: false,
      isHovered: false,
      isLastNodeSelected: false,
      connectionLinePositionOnNode: 'none',
      nodeAppearanceData: {
        customizedStyleProperties: [],
        gqlStyle: {
          __typename: 'RectangularNodeStyle',
          childrenLayoutStrategy:
            type === 'listNode'
              ? { __typename: 'ListLayoutStrategy', kind: 'List' }
              : { __typename: 'FreeFormLayoutStrategy', kind: 'FreeForm' },
        },
      },
      minComputedWidth: null,
      minComputedHeight: null,
      moving: false,
      decorators: [],
    },
  };
  const diagram = { nodes: [node], edges: [] };
  const { hiddenContainer, root } = await new Promise<ReturnType<typeof prepareLayoutArea>>((resolve) => {
    const area = prepareLayoutArea(diagram, () => resolve(area), '');
  });
  const mount = document.body.appendChild(document.createElement('div'));
  const visibleRoot = createRoot(mount);
  try {
    prepareLayoutLabels(null, diagram);
    new LayoutEngine(createInsideLabelMeasurer(hiddenContainer)).layoutNodes(null, diagram.nodes, diagram.nodes, []);

    flushSync(() => {
      visibleRoot.render(
        <ReactFlowProvider>
          <div
            style={{
              ...style,
              width: node.width,
              height: node.height,
              boxSizing: 'border-box',
              position: 'relative',
              isolation: 'isolate',
            }}>
            <NodeInsideLabel
              diagramElementId={node.id}
              insideLabel={insideLabel}
              faded={false}
              innerBorderRadius={getNodeInnerBorderRadius(style)}
            />
          </div>
        </ReactFlowProvider>
      );
    });

    const host = mount.firstElementChild!;
    const background = host.querySelector('[aria-hidden="true"]')?.firstElementChild;
    if (!background) {
      throw new Error('The header background was not rendered');
    }
    const borderWidth = parseFloat(getComputedStyle(host).borderBottomWidth);
    return {
      borderWidth,
      backgroundColor: getComputedStyle(background).backgroundColor,
      gap: host.getBoundingClientRect().bottom - borderWidth - background.getBoundingClientRect().bottom,
    };
  } finally {
    visibleRoot.unmount();
    mount.remove();
    root.unmount();
    hiddenContainer.remove();
  }
};
