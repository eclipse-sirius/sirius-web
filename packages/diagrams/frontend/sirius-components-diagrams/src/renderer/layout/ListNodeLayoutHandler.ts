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
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousSize } from './bounds';
import { getNodeBorderWidth } from './getNodeBorderWidth';
import { ForcedDimensions, RawDiagram } from './layout.types';
import { getBorderNodeExtent } from './layoutBorderNodes';
import { measureInsideLabel } from './measureInsideLabel';
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

  handleLeafNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, DiagramNodeType>,
    _visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ) {
    const nodeMinComputeWidth = getInsideLabelWidthConstraint(node.data.insideLabel) + borderWidth * 2;

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

    measureInsideLabel(layoutEngine, node, (node.width ?? 0) - borderWidth * 2);
    const nodeMinComputeHeight = (node.data.insideLabel?.height ?? 0) + borderWidth * 2;

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
    borderWidth: number,
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

    let previousContentWidth = getDefaultOrMinWidth(0, node) - borderWidth * 2;
    if (node.data.resizedByUser) {
      previousContentWidth = (previousNode?.width ?? node.width ?? 0) - borderWidth * 2;
    }
    const fixedWidth = Math.max(
      directNodesChildren.reduce<number>(
        (widerWidth, child) => Math.max(child.width ?? 0, widerWidth),
        getInsideLabelWidthConstraint(node.data.insideLabel)
      ),
      northBorderNodeFootprintWidth,
      southBorderNodeFootprintWidth,
      previousContentWidth,
      (forceDimensions?.width ?? 0) - borderWidth * 2
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
    const nodeMinComputeWidth = Math.max(fixedWidth, labelOnlyWidth) + borderWidth * 2;

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

    measureInsideLabel(layoutEngine, node, (node.width ?? 0) - borderWidth * 2);
    const headerHeightFootprint = getHeaderHeightFootprint(node.data.insideLabel, 'TOP', borderWidth);
    const growableChilds = directNodesChildren.filter(
      (child) => node.data.growableNodeIds.includes(child.data.descriptionId) && !child.data.resizedByUser
    );
    let availableHeight =
      getDefaultOrMinHeight(forceDimensions?.height ?? 0, node) - borderWidth * 2 - headerHeightFootprint;
    if (node.data.resizedByUser) {
      availableHeight =
        previousDimensions.height - borderWidth * 2 - headerHeightFootprint - heightLostSincePrevDiagram;
    }
    availableHeight -= nonGrowableChilds.reduce<number>((height, child) => height + (child.height ?? 0), 0);
    availableHeight -= node.data.topGap + node.data.bottomGap;
    const childHeight = availableHeight / growableChilds.length;
    growableChilds.forEach((child) => {
      layoutEngine.layoutNodes(previousDiagram, visibleNodes, [child], newlyAddedNodes, {
        width: fixedWidth,
        height: Math.max(child.height ?? 0, childHeight),
      });
    });

    directNodesChildren.forEach((child, index) => {
      child.position = {
        x: borderWidth,
        y: headerHeightFootprint + node.data.topGap,
      };
      const previousSibling = directNodesChildren[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) };
      }
    });

    const childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren);
    // Growing a child while preserving its ratio can expand its width beyond the shared width.
    node.width = Math.max(forceDimensions?.width ?? node.width ?? 0, childrenContentBox.width + borderWidth * 2);

    const directChildrenAwareNodeHeight =
      childrenContentBox.y + childrenContentBox.height + borderWidth + node.data.bottomGap;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const nodeMinComputeHeight = Math.max(
      directChildrenAwareNodeHeight,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight,
      headerHeightFootprint
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
    node.data.minComputedWidth = Math.max(minComputeChildrenWidth, labelOnlyWidth) + borderWidth * 2;
    const minComputeChildrenHeight = directNodesChildren
      .map((node) => getChildNodeHeightMinFootPrint(node))
      .reduce((sum, height) => sum + height, 0);
    node.data.minComputedHeight = Math.max(
      childrenContentBox.y + minComputeChildrenHeight + borderWidth + node.data.bottomGap,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight
    );
  }
}
