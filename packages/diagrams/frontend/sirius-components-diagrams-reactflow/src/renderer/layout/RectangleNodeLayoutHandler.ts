/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Dimensions, Node, NodeChange, NodeDimensionChange, Rect } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { RectangularNodeData } from '../node/RectangularNode.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousPosition, computePreviousSize } from './bounds';
import { RawDiagram } from './layout.types';
import { getBorderNodeExtent } from './layoutBorderNodes';
import {
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  findNodeIndex,
  getChildNodePosition,
  getDefaultOrMinHeight,
  getDefaultOrMinWidth,
  getEastBorderNodeFootprintHeight,
  getHeaderFootprint,
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

  public handle2(
    _layoutEngine: ILayoutEngine,
    _previousDiagram: RawDiagram | null,
    _node: Node<RectangularNodeData, 'rectangularNode'>,
    _visibleNodes: Node<NodeData, string>[],
    _directChildren: Node<NodeData, string>[],
    _newlyAddedNode: Node<NodeData, string> | undefined,
    _nodeDimensionChange: NodeDimensionChange,
    _forceDimension?: { width?: number | undefined; height?: number | undefined } | undefined
  ): NodeChange[] {
    const nodeChange: NodeChange[] = [];

    return nodeChange;
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

    const directGrandChildren = visibleNodes.filter((visibleNode) => visibleNode.parentNode === node.id);
    if (directGrandChildren.length === 4) {
      debugger;
    }

    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);
    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);

    this.computeChildrenPosition(
      directNodesChildren,
      visibleNodes,
      previousDiagram,
      newlyAddedNode,
      labelElement,
      node,
      borderWidth
    );

    // Update node to layout size
    // WARN: We suppose label are always on top of children (that wrong)
    const childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren); // WARN: The current content box algorithm does not take the margin of direct children (it should)
    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);

    node.width =
      forceWidth ??
      this.computeParentNodeWidth(
        childrenContentBox,
        visibleNodes,
        borderNodes,
        previousDiagram,
        labelElement,
        borderWidth,
        node,
        previousDimensions
      );
    node.height = this.computeParentNodeHeight(
      childrenContentBox,
      visibleNodes,
      borderNodes,
      previousDiagram,
      borderWidth,
      node,
      previousDimensions
    );

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }

    // Update border nodes positions
    borderNodes.forEach((borderNode) => {
      borderNode.extent = getBorderNodeExtent(node, borderNode);
    });
    setBorderNodesPosition(borderNodes, node, previousDiagram);
  }

  private computeChildrenPosition(
    directNodesChildren: Node<NodeData, string>[],
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    previousDiagram: RawDiagram | null,
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    labelElement: HTMLElement | null,
    node: Node<RectangularNodeData, 'rectangularNode'>,
    borderWidth: number
  ) {
    const withHeader: boolean = node.data.insideLabel?.isHeader ?? false;
    const displayHeaderSeparator: boolean = node.data.insideLabel?.displayHeaderSeparator ?? false;

    // Update children position to be under the label and at the right padding.
    directNodesChildren.forEach((child, index) => {
      const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === child.id);
      const previousPosition = computePreviousPosition(previousNode, child);
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
      } else if (previousPosition) {
        // WARN: this prevent the moved node to overlep the TOP header or appear outside of its container. It is a quick fix but a proper solution should be implemented.
        const headerHeightFootprint = labelElement
          ? getHeaderFootprint(labelElement, withHeader, displayHeaderSeparator)
          : 0;
        child.position = previousPosition;
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
  }

  private computeParentNodeWidth(
    childrenContentBox: Rect,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderNodes: Node<NodeData, string>[],
    previousDiagram: RawDiagram | null,
    labelElement: HTMLElement | null,
    borderWidth: number,
    node: Node<RectangularNodeData, 'rectangularNode'>,
    previousDimensions: Dimensions
  ): number {
    let parentNodeWidth: number = 0;

    const directChildrenAwareNodeWidth = childrenContentBox.x + childrenContentBox.width + rectangularNodePadding;
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const labelOnlyWidth =
      rectangularNodePadding + (labelElement?.getBoundingClientRect().width ?? 0) + rectangularNodePadding;

    const nodeMinComputedWidth =
      Math.max(
        directChildrenAwareNodeWidth,
        labelOnlyWidth,
        northBorderNodeFootprintWidth,
        southBorderNodeFootprintWidth
      ) +
      borderWidth * 2;

    if (node.data.resizedByUser) {
      parentNodeWidth =
        nodeMinComputedWidth > previousDimensions.width ? nodeMinComputedWidth : previousDimensions.width;
    } else {
      parentNodeWidth = getDefaultOrMinWidth(nodeMinComputedWidth, node);
    }

    return parentNodeWidth;
  }

  private computeParentNodeHeight(
    childrenContentBox: Rect,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderNodes: Node<NodeData, string>[],
    previousDiagram: RawDiagram | null,
    borderWidth: number,
    node: Node<RectangularNodeData, 'rectangularNode'>,
    previousDimensions: Dimensions
  ): number {
    let parentNodeHeight: number = 0;

    // WARN: the label is not used for the height because children are already position under the label
    const directChildrenAwareNodeHeight = childrenContentBox.y + childrenContentBox.height + rectangularNodePadding;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);

    const nodeMinComputedHeight =
      Math.max(directChildrenAwareNodeHeight, eastBorderNodeFootprintHeight, westBorderNodeFootprintHeight) +
      borderWidth * 2;

    if (node.data.resizedByUser) {
      parentNodeHeight =
        nodeMinComputedHeight > previousDimensions.height ? nodeMinComputedHeight : previousDimensions.height;
    } else {
      parentNodeHeight = getDefaultOrMinHeight(nodeMinComputedHeight, node);
    }

    return parentNodeHeight;
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

    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);

    node.width = forceWidth ?? this.computeLeafNodeWidth(labelElement, borderWidth, node, previousDimensions);
    node.height = this.computeLeafNodeHeight(labelElement, borderWidth, node, previousDimensions);

    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }
  }

  computeLeafNodeWidth(
    labelElement: HTMLElement | null,
    borderWidth: number,
    node: Node<RectangularNodeData, 'rectangularNode'>,
    previousDimensions: Dimensions
  ): number {
    let leafNodeWidth: number = 0;
    const nodeMinComputedWidth =
      rectangularNodePadding +
      (labelElement?.getBoundingClientRect().width ?? 0) +
      rectangularNodePadding +
      borderWidth * 2;

    if (node.data.resizedByUser) {
      leafNodeWidth = nodeMinComputedWidth > previousDimensions.width ? nodeMinComputedWidth : previousDimensions.width;
    } else {
      leafNodeWidth = getDefaultOrMinWidth(nodeMinComputedWidth, node);
    }
    return leafNodeWidth;
  }

  computeLeafNodeHeight(
    labelElement: HTMLElement | null,
    borderWidth: number,
    node: Node<RectangularNodeData, 'rectangularNode'>,
    previousDimensions: Dimensions
  ): number {
    let leafNodeHeight: number = 0;

    const nodeMinComputedHeight =
      rectangularNodePadding +
      (labelElement?.getBoundingClientRect().height ?? 0) +
      rectangularNodePadding +
      borderWidth * 2;

    if (node.data.resizedByUser) {
      leafNodeHeight =
        nodeMinComputedHeight > previousDimensions.height ? nodeMinComputedHeight : previousDimensions.height;
    } else {
      leafNodeHeight = getDefaultOrMinHeight(nodeMinComputedHeight, node);
    }
    return leafNodeHeight;
  }
}
