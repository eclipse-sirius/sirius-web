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
import { getBorderNodeExtent } from './layoutBorderNodes';
import {
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  getChildNodePosition,
  getDefaultOrMinHeight,
  getDefaultOrMinWidth,
  getEastBorderNodeFootprintHeight,
  getHeaderHeightFootprint,
  getInsideLabelWidthConstraint,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  setBorderNodesPosition,
} from './layoutNode';
import { rectangularNodePadding } from './layoutParams';
import { updateInsideLabelSize } from './updateInsideLabelSize';

export class FreeFormNodeLayoutHandler implements INodeLayoutHandler<FreeFormNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
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
      this.handleLeafNode(layoutEngine, previousDiagram, node, visibleNodes, borderWidth, forceDimensions);
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
    const previousNodesById = new Map(
      (previousDiagram?.nodes ?? []).map((previousNode) => [previousNode.id, previousNode])
    );
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNodes);

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);

    directNodesChildren.forEach((child, index) => {
      const previousChild = previousNodesById.get(child.id);
      const previousChildPosition = computePreviousPosition(previousChild, child);
      const createdChild = newlyAddedNodes.find((candidate) => candidate.id === child.id);
      let x: number;
      if (createdChild) {
        x = createdChild.position.x;
      } else if (previousChildPosition) {
        x = Math.max(previousChildPosition.x, borderWidth + rectangularNodePadding);
      } else {
        x = getChildNodePosition(visibleNodes, child, 0, borderWidth, directNodesChildren[index - 1]).x;
      }
      child.position = { ...child.position, x };
    });

    // Update node to layout size
    let childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren);
    const directChildrenAwareNodeWidth = childrenContentBox.x + childrenContentBox.width + rectangularNodePadding;
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const labelOnlyWidth =
      rectangularNodePadding + getInsideLabelWidthConstraint(node.data.insideLabel) + rectangularNodePadding;

    const nodeMinComputeWidth =
      Math.max(
        directChildrenAwareNodeWidth,
        labelOnlyWidth,
        northBorderNodeFootprintWidth,
        southBorderNodeFootprintWidth
      ) + borderWidth;

    const previousNode = previousNodesById.get(node.id);
    const previousDimensions = computePreviousSize(previousNode, node);
    const nodeWidth = forceDimensions?.width ?? getDefaultOrMinWidth(nodeMinComputeWidth, node);
    if (node.data.resizedByUser && !forceDimensions?.width) {
      if (nodeMinComputeWidth > previousDimensions.width) {
        node.width = nodeMinComputeWidth;
      } else {
        node.width = previousDimensions.width;
      }
    } else {
      node.width = nodeWidth;
    }

    updateInsideLabelSize(layoutEngine, node, (node.width ?? 0) - borderWidth * 2);
    const headerHeightFootprint = getHeaderHeightFootprint(node.data.insideLabel, 'TOP', borderWidth);
    const footerHeightFootprint = getHeaderHeightFootprint(node.data.insideLabel, 'BOTTOM', borderWidth);
    // Position children vertically only after measuring the header at its calculated width.
    directNodesChildren.forEach((child) => {
      const previousNode = previousNodesById.get(child.id);
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

    childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren);

    // WARN: the label is not used for the height because children are already position under the label
    const directChildrenAwareNodeHeight =
      childrenContentBox.y + childrenContentBox.height + footerHeightFootprint + rectangularNodePadding;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);

    const nodeMinComputeHeight =
      Math.max(directChildrenAwareNodeHeight, eastBorderNodeFootprintHeight, westBorderNodeFootprintHeight) +
      borderWidth;

    const nodeHeight = forceDimensions?.height ?? getDefaultOrMinHeight(nodeMinComputeHeight, node);
    if (node.data.resizedByUser && !forceDimensions?.height) {
      if (nodeMinComputeHeight > previousDimensions.height) {
        node.height = nodeMinComputeHeight;
      } else {
        node.height = previousDimensions.height;
      }
    } else {
      node.height = nodeHeight;
    }

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }

    borderNodes.forEach((borderNode) => {
      borderNode.extent = getBorderNodeExtent(node, borderNode);
    });
    setBorderNodesPosition(borderNodes, node, previousDiagram);

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
    _visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ) {
    const nodeMinComputeWidth = getInsideLabelWidthConstraint(node.data.insideLabel) + borderWidth * 2;

    const previousNode = (previousDiagram?.nodes ?? []).find((prevNode) => prevNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);

    const nodeWidth = forceDimensions?.width ?? getDefaultOrMinWidth(nodeMinComputeWidth, node);
    if (node.data.resizedByUser && !forceDimensions?.width) {
      if (nodeMinComputeWidth > previousDimensions.width) {
        node.width = nodeMinComputeWidth;
      } else {
        node.width = previousDimensions.width;
      }
    } else {
      node.width = nodeWidth;
    }

    updateInsideLabelSize(layoutEngine, node, (node.width ?? 0) - borderWidth * 2);
    const nodeMinComputeHeight = (node.data.insideLabel?.height ?? 0) + borderWidth * 2;
    const nodeHeight = forceDimensions?.height ?? getDefaultOrMinHeight(nodeMinComputeHeight, node);
    if (node.data.resizedByUser && !forceDimensions?.height) {
      if (nodeMinComputeHeight > previousDimensions.height) {
        node.height = nodeMinComputeHeight;
      } else {
        node.height = previousDimensions.height;
      }
    } else {
      node.height = nodeHeight;
    }

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }

    node.data.minComputedWidth = nodeMinComputeWidth;
    node.data.minComputedHeight = nodeMinComputeHeight;
  }
}
