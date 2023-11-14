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
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { RectangularNodeData } from '../node/RectangularNode.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { RawDiagram } from './layout.types';
import { getBorderNodeExtent } from './layoutBorderNodes';
import {
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  findNodeIndex,
  getChildNodePosition,
  getEastBorderNodeFootprintHeight,
  getHeaderFootprint,
  getNodeOrMinHeight,
  getNodeOrMinWidth,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  setBorderNodesPosition,
} from './layoutNode';
import { rectangularNodePadding } from './layoutParams';

export class RectangleNodeLayoutHandler implements INodeLayoutHandler<RectangularNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'rectangularNode';
  }

  public handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<RectangularNodeData, 'rectangularNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceWidth?: number
  ) {
    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const nodeElement = document.getElementById(`${node.id}-rectangularNode-${nodeIndex}`)?.children[0];
    const borderWidth = nodeElement ? parseFloat(window.getComputedStyle(nodeElement).borderWidth) : 0;

    if (directChildren.length > 0) {
      this.handleParentNode(
        layoutEngine,
        previousDiagram,
        node,
        visibleNodes,
        directChildren,
        newlyAddedNode,
        borderWidth,
        forceWidth
      );
    } else {
      this.handleLeafNode(previousDiagram, node, visibleNodes, borderWidth, forceWidth);
    }
  }

  private handleParentNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<RectangularNodeData, 'rectangularNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    borderWidth: number,
    forceWidth?: number
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNode);

    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);
    const withHeader: boolean = node.data.label?.isHeader ?? false;
    const displayHeaderSeparator: boolean = node.data.label?.displayHeaderSeparator ?? false;

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);

    // Update children position to be under the label and at the right padding.
    directNodesChildren.forEach((child, index) => {
      const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === child.id);
      const createdNode = newlyAddedNode?.id === child.id ? newlyAddedNode : undefined;

      if (!!createdNode) {
        // WARN: this prevent the created to overlep the TOP header. It is a quick fix but a proper solution should be implemented.
        const headerHeightFootprint = labelElement
          ? getHeaderFootprint(labelElement, withHeader, displayHeaderSeparator)
          : 0;
        child.position = createdNode.position;
        if (child.position.y < borderWidth + headerHeightFootprint) {
          child.position = { ...child.position, y: borderWidth + headerHeightFootprint };
        }
      } else if (previousNode) {
        // WARN: this prevent the moved node to overlep the TOP header or appear outside of its container. It is a quick fix but a proper solution should be implemented.
        const headerHeightFootprint = labelElement
          ? getHeaderFootprint(labelElement, withHeader, displayHeaderSeparator)
          : 0;
        child.position = previousNode.position;
        if (child.position.y < borderWidth + headerHeightFootprint) {
          child.position = { ...child.position, y: borderWidth + headerHeightFootprint };
        }
        if (child.position.x < borderWidth + rectangularNodePadding) {
          child.position = { ...child.position, x: borderWidth + rectangularNodePadding };
        }
      } else {
        child.position = getChildNodePosition(
          visibleNodes,
          child,
          labelElement,
          withHeader,
          displayHeaderSeparator,
          borderWidth
        );
        const previousSibling = directNodesChildren[index - 1];
        if (previousSibling) {
          child.position = getChildNodePosition(
            visibleNodes,
            child,
            labelElement,
            withHeader,
            displayHeaderSeparator,
            borderWidth,
            previousSibling
          );
        }
      }
    });

    // Update node to layout size
    // WARN: We suppose label are always on top of children (that wrong)
    const childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren); // WARN: The current content box algorithm does not take the margin of direct children (it should)
    const directChildrenAwareNodeWidth = childrenContentBox.x + childrenContentBox.width + rectangularNodePadding;
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const labelOnlyWidth =
      rectangularNodePadding + (labelElement?.getBoundingClientRect().width ?? 0) + rectangularNodePadding;

    const nodeWidth =
      Math.max(
        directChildrenAwareNodeWidth,
        labelOnlyWidth,
        northBorderNodeFootprintWidth,
        southBorderNodeFootprintWidth
      ) +
      borderWidth * 2;

    // WARN: the label is not used for the height because children are already position under the label
    const directChildrenAwareNodeHeight = childrenContentBox.y + childrenContentBox.height + rectangularNodePadding;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);

    const nodeHeight =
      Math.max(directChildrenAwareNodeHeight, eastBorderNodeFootprintHeight, westBorderNodeFootprintHeight) +
      borderWidth * 2;

    const minNodeWith = forceWidth ?? getNodeOrMinWidth(nodeWidth, node); // WARN: not sure yet for the forceWidth to be here.
    const minNodeheight = getNodeOrMinHeight(nodeHeight, node);

    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    if (previousNode && node.data.nodeDescription?.userResizable) {
      if (minNodeWith > (previousNode.width ?? 0)) {
        node.width = minNodeWith;
      } else {
        node.width = previousNode.width;
      }
      if (minNodeheight > (previousNode.height ?? 0)) {
        node.height = minNodeheight;
      } else {
        node.height = previousNode.height;
      }
    } else {
      node.width = minNodeWith;
      node.height = minNodeheight;
    }

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }

    // Update border nodes positions
    borderNodes.forEach((borderNode) => {
      borderNode.extent = getBorderNodeExtent(node, borderNode);
    });
    setBorderNodesPosition(borderNodes, node, previousDiagram);
  }

  private handleLeafNode(
    previousDiagram: RawDiagram | null,
    node: Node<RectangularNodeData, 'rectangularNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    forceWidth?: number
  ) {
    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);

    const labelWidth =
      rectangularNodePadding +
      (labelElement?.getBoundingClientRect().width ?? 0) +
      rectangularNodePadding +
      borderWidth * 2;
    const labelHeight =
      rectangularNodePadding + (labelElement?.getBoundingClientRect().height ?? 0) + rectangularNodePadding;

    const minNodeWith = forceWidth ?? getNodeOrMinWidth(labelWidth, node);
    const minNodeheight = getNodeOrMinHeight(labelHeight, node);

    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    if (previousNode && node.data.nodeDescription?.userResizable) {
      if (minNodeWith > (previousNode.width ?? 0)) {
        node.width = minNodeWith;
      } else {
        node.width = previousNode.width;
      }
      if (minNodeheight > (previousNode.height ?? 0)) {
        node.height = minNodeheight;
      } else {
        node.height = previousNode.height;
      }
    } else {
      node.width = minNodeWith;
      node.height = minNodeheight;
    }

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }
  }
}
