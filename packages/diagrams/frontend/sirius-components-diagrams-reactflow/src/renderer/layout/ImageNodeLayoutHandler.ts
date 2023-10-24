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

import { Node } from 'reactflow';
import { Diagram, NodeData } from '../DiagramRenderer.types';
import { ImageNodeData } from '../node/ImageNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { getBorderNodeExtent } from './layoutBorderNodes';
import {
  computeNodesBox,
  getEastBorderNodeFootprintHeight,
  getNodeOrMinHeight,
  getNodeOrMinWidth,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  setBorderNodesPosition,
} from './layoutNode';
import {
  borderLeftAndRight,
  borderTopAndBottom,
  defaultHeight,
  defaultWidth,
  rectangularNodePadding,
} from './layoutParams';

export class ImageNodeLayoutHandler implements INodeLayoutHandler<ImageNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'imageNode';
  }

  public handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: Diagram | null,
    node: Node<ImageNodeData, 'imageNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceWidth?: number
  ) {
    if (directChildren.length > 0) {
      this.handleParentNode(layoutEngine, previousDiagram, node, visibleNodes, directChildren, newlyAddedNode);
    } else {
      node.width = forceWidth ?? defaultWidth;
      node.height = defaultHeight;
    }
  }

  private handleParentNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: Diagram | null,
    node: Node<ImageNodeData, 'imageNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNode);

    const previousNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === node.id);

    if (previousNode && previousNode.width && previousNode.height) {
      node.width = previousNode.width;
      node.height = previousNode.height;
    } else {
      node.width = defaultWidth;
      node.height = defaultHeight;
    }

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);

    const childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren); // WARN: The current content box algorithm does not take the margin of direct children (it should)

    const directChildrenAwareNodeWidth = childrenContentBox.x + childrenContentBox.width + rectangularNodePadding;
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const nodeWidth = Math.max(
      directChildrenAwareNodeWidth,
      node.width,
      northBorderNodeFootprintWidth,
      southBorderNodeFootprintWidth
    );

    // WARN: the label is not used for the height because children are already position under the label
    const directChildrenAwareNodeHeight = childrenContentBox.y + childrenContentBox.height + rectangularNodePadding;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const nodeHeight = Math.max(
      directChildrenAwareNodeHeight,
      node.height,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight
    );

    node.width = getNodeOrMinWidth(nodeWidth + borderLeftAndRight);
    node.height = getNodeOrMinHeight(nodeHeight + borderTopAndBottom);

    // Update border nodes positions
    borderNodes.forEach((borderNode) => {
      borderNode.extent = getBorderNodeExtent(node, borderNode);
    });
    setBorderNodesPosition(borderNodes, node, previousDiagram);
  }
}
