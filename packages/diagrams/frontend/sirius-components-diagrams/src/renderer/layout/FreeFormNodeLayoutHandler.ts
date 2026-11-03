/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { Node } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';
import { FreeFormNodeData } from '../node/FreeFormNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousPosition, computePreviousSize } from './bounds';
import { getNodeBorderWidth } from './getNodeBorderWidth';
import { ForcedDimensions, RawDiagram } from './layout.types';
import { measureInsideLabel } from './measureInsideLabel';
import { computeNodeHeight, computeNodeWidth } from './nodeDimensions';
import {
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  getChildNodePosition,
  getEastBorderNodeFootprintHeight,
  getHeaderHeightFootprint,
  getInsideLabelWidthConstraint,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  updateBorderNodesLayout,
} from './layoutNode';
import { rectangularNodePadding } from './layoutParams';

export class FreeFormNodeLayoutHandler implements INodeLayoutHandler<FreeFormNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>): boolean {
    return node.type === 'freeFormNode';
  }

  public handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<FreeFormNodeData, 'freeFormNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNodes: Node<NodeData, DiagramNodeType>[],
    forceDimensions?: ForcedDimensions
  ) {
    const borderWidth: number = getNodeBorderWidth(node.data.style);

    if (directChildren.length > 0) {
      this.handleParentNode(
        layoutEngine,
        previousDiagram,
        node,
        visibleNodes,
        directChildren,
        newlyAddedNodes,
        borderWidth,
        forceDimensions
      );
    } else {
      this.handleLeafNode(layoutEngine, previousDiagram, node, borderWidth, forceDimensions);
    }
  }

  private handleParentNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<FreeFormNodeData, 'freeFormNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNodes);

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const contentChildren = directChildren.filter((child) => !child.data.isBorderNode);

    this.positionChildrenHorizontally(previousDiagram, visibleNodes, contentChildren, newlyAddedNodes, borderWidth);

    // Update node to layout size
    let childrenContentBox = computeNodesBox(visibleNodes, contentChildren);
    const directChildrenAwareNodeWidth = childrenContentBox.x + childrenContentBox.width + rectangularNodePadding;
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const labelOnlyWidth =
      rectangularNodePadding + getInsideLabelWidthConstraint(node.data.insideLabel) + rectangularNodePadding;

    const requiredWidth =
      Math.max(
        directChildrenAwareNodeWidth,
        labelOnlyWidth,
        northBorderNodeFootprintWidth,
        southBorderNodeFootprintWidth
      ) + borderWidth;

    const previousNode = (previousDiagram?.nodes ?? []).find((candidate) => candidate.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);
    node.width = computeNodeWidth(node, requiredWidth, previousDimensions.width, forceDimensions?.width);

    measureInsideLabel(layoutEngine, node, (node.width ?? 0) - borderWidth * 2);
    const headerHeightFootprint = getHeaderHeightFootprint(node.data.insideLabel, 'TOP', borderWidth);
    const footerHeightFootprint = getHeaderHeightFootprint(node.data.insideLabel, 'BOTTOM', borderWidth);
    // Position children vertically only after measuring the header at its calculated width.
    this.positionChildrenVertically(
      previousDiagram,
      visibleNodes,
      contentChildren,
      newlyAddedNodes,
      borderWidth,
      headerHeightFootprint
    );
    childrenContentBox = computeNodesBox(visibleNodes, contentChildren);

    // WARN: the label is not used for the height because children are already position under the label
    const directChildrenAwareNodeHeight =
      childrenContentBox.y + childrenContentBox.height + footerHeightFootprint + rectangularNodePadding;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);

    const requiredHeight =
      Math.max(directChildrenAwareNodeHeight, eastBorderNodeFootprintHeight, westBorderNodeFootprintHeight) +
      borderWidth;

    node.height = computeNodeHeight(node, requiredHeight, previousDimensions.height, forceDimensions?.height);

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }

    updateBorderNodesLayout(node, borderNodes, previousDiagram);

    node.data.minComputedWidth =
      Math.max(
        childrenContentBox.width + rectangularNodePadding * 2,
        labelOnlyWidth,
        northBorderNodeFootprintWidth,
        southBorderNodeFootprintWidth
      ) +
      borderWidth * 2;
    node.data.minComputedHeight =
      Math.max(
        childrenContentBox.height + headerHeightFootprint + footerHeightFootprint + rectangularNodePadding * 2,
        eastBorderNodeFootprintHeight,
        westBorderNodeFootprintHeight
      ) +
      borderWidth * 2;
  }

  private handleLeafNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<FreeFormNodeData, 'freeFormNode'>,
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ) {
    const requiredWidth = getInsideLabelWidthConstraint(node.data.insideLabel) + borderWidth * 2;

    const previousNode = (previousDiagram?.nodes ?? []).find((prevNode) => prevNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);

    node.width = computeNodeWidth(node, requiredWidth, previousDimensions.width, forceDimensions?.width);

    measureInsideLabel(layoutEngine, node, (node.width ?? 0) - borderWidth * 2);
    const requiredHeight = (node.data.insideLabel?.height ?? 0) + borderWidth * 2;
    node.height = computeNodeHeight(node, requiredHeight, previousDimensions.height, forceDimensions?.height);

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }

    node.data.minComputedWidth = requiredWidth;
    node.data.minComputedHeight = requiredHeight;
  }

  private positionChildrenHorizontally(
    previousDiagram: RawDiagram | null,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    contentChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number
  ): void {
    contentChildren.forEach((child, index) => {
      const previousChild = previousDiagram?.nodes.find((candidate) => candidate.id === child.id);
      const previousChildPosition = computePreviousPosition(previousChild, child);
      const createdChild = newlyAddedNodes.find((candidate) => candidate.id === child.id);
      let x: number;
      if (createdChild) {
        x = createdChild.position.x;
      } else if (previousChildPosition) {
        x = Math.max(previousChildPosition.x, borderWidth + rectangularNodePadding);
      } else {
        x = getChildNodePosition(visibleNodes, child, 0, borderWidth, contentChildren[index - 1]).x;
      }
      child.position = { ...child.position, x };
    });
  }

  private positionChildrenVertically(
    previousDiagram: RawDiagram | null,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    contentChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    headerHeightFootprint: number
  ): void {
    contentChildren.forEach((child) => {
      const previousNode = (previousDiagram?.nodes ?? []).find((candidate) => candidate.id === child.id);
      const previousPosition = computePreviousPosition(previousNode, child);
      const createdNode = newlyAddedNodes.find((n) => n.id === child.id);

      if (!!createdNode) {
        child.position = createdNode.position;
        if (child.position.y < borderWidth + rectangularNodePadding + headerHeightFootprint) {
          child.position = { ...child.position, y: borderWidth + rectangularNodePadding + headerHeightFootprint };
        }
      } else if (previousPosition) {
        child.position = previousPosition;
        if (child.position.y < borderWidth + rectangularNodePadding + headerHeightFootprint) {
          child.position = { ...child.position, y: borderWidth + rectangularNodePadding + headerHeightFootprint };
        }
        if (child.position.x < borderWidth + rectangularNodePadding) {
          child.position = { ...child.position, x: borderWidth + rectangularNodePadding };
        }
      } else {
        const { y } = getChildNodePosition(visibleNodes, child, headerHeightFootprint, borderWidth);
        child.position = { ...child.position, y };
      }
    });
  }
}
