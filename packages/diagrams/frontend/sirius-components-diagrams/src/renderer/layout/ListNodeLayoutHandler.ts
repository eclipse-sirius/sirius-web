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
import { NodeBorderWidths, NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousSize } from './bounds';
import { getNodeBorderWidths } from './getNodeBorderWidth';
import { ForcedDimensions, RawDiagram } from './layout.types';
import { getBorderNodeExtent } from './layoutBorderNodes';
import {
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
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
import { updateInsideLabelSize } from './updateInsideLabelSize';

const getChildNodeHeightMinFootPrint = (node: Node<NodeData>): number => {
  if (node.type === 'iconLabelNode') {
    return node.data.minComputedHeight ?? 0;
  }
  return node.data.resizedByUser ? node.height ?? 0 : getDefaultOrMinHeight(node.data.minComputedHeight ?? 0, node);
};

export class ListNodeLayoutHandler implements INodeLayoutHandler<ListNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'listNode';
  }

  public handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, DiagramNodeType>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNodes: Node<NodeData, DiagramNodeType>[],
    forceDimensions?: ForcedDimensions
  ) {
    const borderWidths = getNodeBorderWidths(node.data);
    if (directChildren.length > 0) {
      this.handleParentNode(
        layoutEngine,
        previousDiagram,
        node,
        visibleNodes,
        directChildren,
        newlyAddedNodes,
        borderWidths,
        forceDimensions
      );
    } else {
      this.handleLeafNode(layoutEngine, previousDiagram, node, borderWidths, forceDimensions);
    }
  }

  private handleLeafNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, DiagramNodeType>,
    borderWidths: NodeBorderWidths,
    forceDimensions?: ForcedDimensions
  ) {
    const horizontalBorders = borderWidths.left + borderWidths.right;
    const nodeMinComputeWidth = getInsideLabelWidthConstraint(node.data.insideLabel) + horizontalBorders;

    const previousNode = (previousDiagram?.nodes ?? []).find((candidate) => candidate.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);

    const heightLostSincePrevDiagram =
      previousDiagram?.nodes
        .filter((child) => child.parentId === node.id && !child.hidden)
        .reduce<number>((height, child) => height + (child.height ?? 0), 0) ?? 0;

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

    updateInsideLabelSize(layoutEngine, node, (node.width ?? 0) - horizontalBorders);
    const nodeMinComputeHeight = (node.data.insideLabel?.height ?? 0) + borderWidths.top + borderWidths.bottom;

    const nodeHeight = forceDimensions?.height ?? getDefaultOrMinHeight(nodeMinComputeHeight, node);
    if (node.data.resizedByUser && !forceDimensions?.height) {
      node.height = Math.max(nodeMinComputeHeight, previousDimensions.height - heightLostSincePrevDiagram);
    } else {
      node.height = nodeHeight;
    }
    node.data.minComputedWidth = nodeMinComputeWidth;
    node.data.minComputedHeight = nodeMinComputeHeight;
  }

  private handleParentNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, DiagramNodeType>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNodes: Node<NodeData, DiagramNodeType>[],
    borderWidths: NodeBorderWidths,
    forceDimensions?: ForcedDimensions
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNodes);

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);

    const previousNode: Node<NodeData, DiagramNodeType> | undefined = (previousDiagram?.nodes ?? []).find(
      (candidate) => candidate.id === node.id
    );

    const heightLostSincePrevDiagram =
      previousDiagram?.nodes
        .filter((child) => child.parentId === node.id && !child.hidden)
        .filter((child) => !directChildren.map((currentChild) => currentChild.id).includes(child.id))
        .reduce<number>((height, child) => height + (child.height ?? 0), 0) ?? 0;

    const horizontalBorders = borderWidths.left + borderWidths.right;
    let previousContentWidth = getDefaultOrMinWidth(0, node) - horizontalBorders;
    if (node.data.resizedByUser) {
      previousContentWidth = (previousNode?.width ?? node.width ?? 0) - horizontalBorders;
    }
    const fixedWidth = Math.max(
      directNodesChildren.reduce<number>(
        (widerWidth, child) => Math.max(child.width ?? 0, widerWidth),
        getInsideLabelWidthConstraint(node.data.insideLabel)
      ),
      northBorderNodeFootprintWidth,
      southBorderNodeFootprintWidth,
      previousContentWidth,
      (forceDimensions?.width ?? 0) - horizontalBorders
    );
    const nonGrowableChilds = directNodesChildren.filter(
      (child) => !node.data.growableNodeIds.includes(child.data.descriptionId) || child.data.resizedByUser
    );
    directNodesChildren.forEach((child) => {
      layoutEngine.layoutNodes(previousDiagram, visibleNodes, [child], newlyAddedNodes, {
        width: fixedWidth,
        height: null,
      });
    });

    const labelOnlyWidth = getInsideLabelWidthConstraint(node.data.insideLabel);
    const nodeMinComputeWidth = Math.max(fixedWidth, labelOnlyWidth) + horizontalBorders;

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

    updateInsideLabelSize(layoutEngine, node, (node.width ?? 0) - horizontalBorders);
    const spaceAboveChildren =
      borderWidths.top + getHeaderHeightFootprint(node.data.insideLabel, 'TOP') + node.data.topGap;
    const spaceBelowChildren =
      borderWidths.bottom + getHeaderHeightFootprint(node.data.insideLabel, 'BOTTOM') + node.data.bottomGap;
    const growableChilds = directNodesChildren.filter(
      (child) => node.data.growableNodeIds.includes(child.data.descriptionId) && !child.data.resizedByUser
    );
    let availableHeight =
      getDefaultOrMinHeight(forceDimensions?.height ?? 0, node) - spaceAboveChildren - spaceBelowChildren;
    if (node.data.resizedByUser) {
      availableHeight =
        previousDimensions.height - spaceAboveChildren - spaceBelowChildren - heightLostSincePrevDiagram;
    }
    availableHeight -= nonGrowableChilds.reduce<number>((height, child) => height + (child.height ?? 0), 0);
    const childHeight = availableHeight / growableChilds.length;
    growableChilds.forEach((child) => {
      layoutEngine.layoutNodes(previousDiagram, visibleNodes, [child], newlyAddedNodes, {
        width: fixedWidth,
        height: Math.max(child.height ?? 0, childHeight),
      });
    });

    directNodesChildren.forEach((child, index) => {
      child.position = {
        x: borderWidths.left,
        y: spaceAboveChildren,
      };
      const previousSibling = directNodesChildren[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) };
      }
    });

    const childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren);
    // Growing a child while preserving its ratio can expand its width beyond the shared width.
    node.width = Math.max(forceDimensions?.width ?? node.width ?? 0, childrenContentBox.width + horizontalBorders);

    const directChildrenAwareNodeHeight =
      Math.max(spaceAboveChildren, childrenContentBox.y + childrenContentBox.height) + spaceBelowChildren;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const nodeMinComputeHeight = Math.max(
      directChildrenAwareNodeHeight,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight
    );

    const nodeHeight = forceDimensions?.height ?? getDefaultOrMinHeight(nodeMinComputeHeight, node);
    if (node.data.resizedByUser && !forceDimensions?.height) {
      if (nodeMinComputeHeight > previousDimensions.height) {
        node.height = nodeMinComputeHeight;
      } else {
        node.height = previousDimensions.height - heightLostSincePrevDiagram;
      }
    } else {
      node.height = nodeHeight;
    }
    node.height = Math.max(nodeMinComputeHeight, node.height);

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }
    borderNodes.forEach((borderNode) => {
      borderNode.extent = getBorderNodeExtent(node, borderNode);
    });
    setBorderNodesPosition(borderNodes, node, previousDiagram);

    // The min size is different from the actual one since children list elements could be resized manually
    const minComputeChildrenWidth = directNodesChildren
      .map((node) => node.data.minComputedWidth ?? 0)
      .reduce((max, width) => Math.max(max, width), 0);
    node.data.minComputedWidth = Math.max(minComputeChildrenWidth, labelOnlyWidth) + horizontalBorders;
    const minComputeChildrenHeight = directNodesChildren
      .map((node) => getChildNodeHeightMinFootPrint(node))
      .reduce((sum, height) => sum + height, 0);
    node.data.minComputedHeight = Math.max(
      spaceAboveChildren + minComputeChildrenHeight + spaceBelowChildren,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight
    );
  }
}
