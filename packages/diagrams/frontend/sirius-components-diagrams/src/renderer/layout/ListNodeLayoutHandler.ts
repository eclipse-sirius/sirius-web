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
  updateBorderNodesLayout,
} from './layoutNode';
import { updateInsideLabelSize } from './updateInsideLabelSize';
import { computeNodeHeight, computeNodeWidth } from './nodeDimensions';

const getChildNodeMinimumHeight = (node: Node<NodeData>): number => {
  if (node.type === 'iconLabelNode') {
    return node.data.minComputedHeight ?? 0;
  }
  return node.data.resizedByUser ? node.height ?? 0 : getDefaultOrMinHeight(node.data.minComputedHeight ?? 0, node);
};

export class ListNodeLayoutHandler implements INodeLayoutHandler<ListNodeData> {
  private layoutPhase: 'measure' | 'measureAtWidth' | 'distributeHeight' | null = null;

  public canHandle(node: Node<NodeData, DiagramNodeType>): boolean {
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
    if (this.layoutPhase !== null || directChildren.length === 0) {
      this.handleNode(
        layoutEngine,
        previousDiagram,
        node,
        visibleNodes,
        directChildren,
        newlyAddedNodes,
        forceDimensions
      );
      return;
    }

    for (const phase of ['measure', 'measureAtWidth', 'distributeHeight'] as const) {
      this.layoutPhase = phase;
      this.handleNode(
        layoutEngine,
        previousDiagram,
        node,
        visibleNodes,
        directChildren,
        newlyAddedNodes,
        forceDimensions
      );
    }
    this.layoutPhase = null;
  }

  private handleNode(
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

  private handleLeafNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, DiagramNodeType>,
    _visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ) {
    const minComputedWidth = getInsideLabelWidthConstraint(node.data.insideLabel) + borderWidth * 2;

    const previousNode = (previousDiagram?.nodes ?? []).find((candidate) => candidate.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);

    const removedChildrenHeight = this.computeRemovedChildrenHeight(previousDiagram, node, []);

    node.width = computeNodeWidth(node, minComputedWidth, previousDimensions.width, forceDimensions?.width);

    updateInsideLabelSize(layoutEngine, node, (node.width ?? 0) - borderWidth * 2);
    const minComputedHeight = (node.data.insideLabel?.height ?? 0) + borderWidth * 2;

    node.height = computeNodeHeight(
      node,
      minComputedHeight,
      previousDimensions.height,
      forceDimensions?.height,
      removedChildrenHeight
    );
    node.data.minComputedWidth = minComputedWidth;
    node.data.minComputedHeight = minComputedHeight;
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
    const previousNodesById: Map<string, Node<NodeData, DiagramNodeType>> = new Map(
      (previousDiagram?.nodes ?? []).map((previousNode) => [previousNode.id, previousNode])
    );
    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const contentChildren = directChildren.filter((child) => !child.data.isBorderNode);
    if (this.layoutPhase === 'measure') {
      // Border subtrees need their own complete layout before their footprint is measured.
      const parentLayoutPhase = this.layoutPhase;
      this.layoutPhase = null;
      layoutEngine.layoutNodes(previousDiagram, visibleNodes, borderNodes, newlyAddedNodes);
      this.layoutPhase = parentLayoutPhase;
      layoutEngine.layoutNodes(previousDiagram, visibleNodes, contentChildren, newlyAddedNodes);
    }

    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(
      visibleNodes,
      borderNodes,
      previousNodesById
    );
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(
      visibleNodes,
      borderNodes,
      previousNodesById
    );

    const previousNode: Node<NodeData, DiagramNodeType> | undefined = (previousDiagram?.nodes ?? []).find(
      (candidate) => candidate.id === node.id
    );

    const removedChildrenHeight = this.computeRemovedChildrenHeight(previousDiagram, node, directChildren);

    const sharedChildWidth = this.computeSharedChildWidth(
      node,
      previousNode,
      contentChildren,
      northBorderNodeFootprintWidth,
      southBorderNodeFootprintWidth,
      borderWidth,
      forceDimensions
    );
    const nonGrowableChildren = contentChildren.filter(
      (child) => !node.data.growableNodeIds.includes(child.data.descriptionId) || child.data.resizedByUser
    );
    if (this.layoutPhase === 'measureAtWidth') {
      contentChildren.forEach((child) => {
        layoutEngine.layoutNodes(previousDiagram, visibleNodes, [child], newlyAddedNodes, {
          width: sharedChildWidth,
          height: null,
        });
      });
    }

    const labelOnlyWidth = getInsideLabelWidthConstraint(node.data.insideLabel);
    const minComputedWidth = Math.max(sharedChildWidth, labelOnlyWidth) + borderWidth * 2;

    const previousDimensions = computePreviousSize(previousNode, node);
    node.width = computeNodeWidth(node, minComputedWidth, previousDimensions.width, forceDimensions?.width);

    updateInsideLabelSize(layoutEngine, node, (node.width ?? 0) - borderWidth * 2);
    const headerHeightFootprint = getHeaderHeightFootprint(node.data.insideLabel, 'TOP', borderWidth);
    const growableChildren = contentChildren.filter(
      (child) => node.data.growableNodeIds.includes(child.data.descriptionId) && !child.data.resizedByUser
    );
    const childHeight = this.computeGrowableChildHeight(
      node,
      nonGrowableChildren,
      growableChildren,
      previousDimensions.height,
      removedChildrenHeight,
      headerHeightFootprint,
      borderWidth,
      forceDimensions
    );
    if (this.layoutPhase === 'distributeHeight') {
      contentChildren.forEach((child) => {
        layoutEngine.layoutNodes(previousDiagram, visibleNodes, [child], newlyAddedNodes, {
          width: sharedChildWidth,
          height: growableChildren.includes(child) ? Math.max(child.height ?? 0, childHeight) : null,
        });
      });
    }

    this.positionChildren(node, contentChildren, headerHeightFootprint, borderWidth);

    const childrenContentBox = computeNodesBox(visibleNodes, contentChildren);
    // Growing a child while preserving its ratio can expand its width beyond the shared width.
    node.width = Math.max(forceDimensions?.width ?? node.width ?? 0, childrenContentBox.width + borderWidth * 2);

    const directChildrenAwareNodeHeight =
      childrenContentBox.y + childrenContentBox.height + borderWidth + node.data.bottomGap;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(
      visibleNodes,
      borderNodes,
      previousNodesById
    );
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(
      visibleNodes,
      borderNodes,
      previousNodesById
    );
    const minComputedHeight = Math.max(
      directChildrenAwareNodeHeight,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight,
      headerHeightFootprint
    );

    node.height = Math.max(
      minComputedHeight,
      computeNodeHeight(
        node,
        minComputedHeight,
        previousDimensions.height,
        forceDimensions?.height,
        removedChildrenHeight
      )
    );

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }
    updateBorderNodesLayout(node, borderNodes, previousNodesById);

    // The min size is different from the actual one since children list elements could be resized manually
    const minComputeChildrenWidth = contentChildren
      .map((node) => node.data.minComputedWidth ?? 0)
      .reduce((max, width) => Math.max(max, width), 0);
    node.data.minComputedWidth = Math.max(minComputeChildrenWidth, labelOnlyWidth) + borderWidth * 2;
    const minComputeChildrenHeight = contentChildren
      .map((node) => getChildNodeMinimumHeight(node))
      .reduce((sum, height) => sum + height, 0);
    node.data.minComputedHeight = Math.max(
      childrenContentBox.y + minComputeChildrenHeight + borderWidth + node.data.bottomGap,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight
    );
  }

  private computeRemovedChildrenHeight(
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, DiagramNodeType>,
    directChildren: Node<NodeData, DiagramNodeType>[]
  ): number {
    const currentChildIds = new Set(directChildren.map((child) => child.id));
    return (
      previousDiagram?.nodes
        .filter((child) => child.parentId === node.id && !child.hidden && !currentChildIds.has(child.id))
        .reduce<number>((height, child) => height + (child.height ?? 0), 0) ?? 0
    );
  }

  private computeSharedChildWidth(
    node: Node<ListNodeData, DiagramNodeType>,
    previousNode: Node<NodeData, DiagramNodeType> | undefined,
    contentChildren: Node<NodeData, DiagramNodeType>[],
    northBorderNodeFootprintWidth: number,
    southBorderNodeFootprintWidth: number,
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ): number {
    let previousContentWidth: number = getDefaultOrMinWidth(0, node) - borderWidth * 2;
    if (node.data.resizedByUser) {
      previousContentWidth = (previousNode?.width ?? node.width ?? 0) - borderWidth * 2;
    }
    return Math.max(
      contentChildren.reduce<number>(
        (widerWidth, child) => Math.max(child.width ?? 0, widerWidth),
        getInsideLabelWidthConstraint(node.data.insideLabel)
      ),
      northBorderNodeFootprintWidth,
      southBorderNodeFootprintWidth,
      previousContentWidth,
      (forceDimensions?.width ?? 0) - borderWidth * 2
    );
  }

  private computeGrowableChildHeight(
    node: Node<ListNodeData, DiagramNodeType>,
    nonGrowableChildren: Node<NodeData, DiagramNodeType>[],
    growableChildren: Node<NodeData, DiagramNodeType>[],
    previousHeight: number,
    removedChildrenHeight: number,
    headerHeightFootprint: number,
    borderWidth: number,
    forceDimensions?: ForcedDimensions
  ): number {
    let availableHeight =
      getDefaultOrMinHeight(forceDimensions?.height ?? 0, node) - borderWidth * 2 - headerHeightFootprint;
    if (node.data.resizedByUser) {
      availableHeight = previousHeight - borderWidth * 2 - headerHeightFootprint - removedChildrenHeight;
    }
    availableHeight -= nonGrowableChildren.reduce<number>((height, node) => height + (node.height ?? 0), 0);
    availableHeight -= node.data.topGap + node.data.bottomGap;

    return availableHeight / growableChildren.length;
  }

  private positionChildren(
    node: Node<ListNodeData, DiagramNodeType>,
    contentChildren: Node<NodeData, DiagramNodeType>[],
    headerHeightFootprint: number,
    borderWidth: number
  ): void {
    contentChildren.forEach((child, index) => {
      child.position = {
        x: borderWidth,
        y: headerHeightFootprint + node.data.topGap,
      };
      const previousSibling = contentChildren[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) };
      }
    });
  }
}
