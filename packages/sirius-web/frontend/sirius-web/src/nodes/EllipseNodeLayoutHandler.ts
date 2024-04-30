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
import {
  Diagram,
  DiagramNodeType,
  ForcedDimensions,
  ILayoutEngine,
  INodeLayoutHandler,
  NodeData,
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  computePreviousPosition,
  computePreviousSize,
  findNodeIndex,
  getBorderNodeExtent,
  getChildNodePosition,
  getEastBorderNodeFootprintHeight,
  getHeaderHeightFootprint,
  getDefaultOrMinHeight,
  getDefaultOrMinWidth,
  getInsideLabelWidthConstraint,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  setBorderNodesPosition,
} from '@eclipse-sirius/sirius-components-diagrams';
import { HandleElement, Node, Position, XYPosition } from 'reactflow';

export class EllipseNodeLayoutHandler implements INodeLayoutHandler<NodeData> {
  canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'ellipseNode';
  }

  handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: Diagram | null,
    node: Node<NodeData>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceDimensions?: ForcedDimensions
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNode);

    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const nodeElement = document.getElementById(`${node.id}-ellipseNode-${nodeIndex}`)?.children[0];
    const borderWidth = nodeElement ? parseFloat(window.getComputedStyle(nodeElement).borderWidth) : 0;
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);

    // Update children position to be under the label and at the right padding.
    directNodesChildren.forEach((child, index) => {
      const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === child.id);
      const previousPosition = computePreviousPosition(previousNode, child);
      const createdNode = newlyAddedNode?.id === child.id ? newlyAddedNode : undefined;
      const headerHeightFootprint = labelElement ? getHeaderHeightFootprint(labelElement, false, false) : 0;

      if (!!createdNode) {
        child.position = createdNode.position;
        if (child.position.y < borderWidth + headerHeightFootprint) {
          child.position = { ...child.position, y: borderWidth + headerHeightFootprint };
        }
      } else if (previousPosition) {
        child.position = previousPosition;
        if (child.position.y < borderWidth + headerHeightFootprint) {
          child.position = { ...child.position, y: borderWidth + headerHeightFootprint };
        }
        if (child.position.x < borderWidth) {
          child.position = { ...child.position, x: borderWidth };
        }
      } else {
        child.position = child.position = getChildNodePosition(visibleNodes, child, headerHeightFootprint, borderWidth);
        const previousSibling = directNodesChildren[index - 1];
        if (previousSibling) {
          child.position = getChildNodePosition(
            visibleNodes,
            child,
            headerHeightFootprint,
            borderWidth,
            previousSibling
          );
        }
      }
    });

    // Update node to layout size
    // WARN: We suppose label are always on top of children (that wrong)
    const childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren); // WARN: The current content box algorithm does not take the margin of direct children (it should)
    const directChildrenAwareNodeWidth = childrenContentBox.x + childrenContentBox.width;
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const labelOnlyWidth = getInsideLabelWidthConstraint(node.data.insideLabel, labelElement);

    const nodeMinComputeWidth =
      Math.max(
        directChildrenAwareNodeWidth,
        labelOnlyWidth,
        northBorderNodeFootprintWidth,
        southBorderNodeFootprintWidth
      ) +
      borderWidth * 2;

    // WARN: the label is not used for the height because children are already position under the label
    const directChildrenAwareNodeHeight = childrenContentBox.y + childrenContentBox.height;
    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);

    const nodeMinComputeHeight =
      Math.max(directChildrenAwareNodeHeight, eastBorderNodeFootprintHeight, westBorderNodeFootprintHeight) +
      borderWidth * 2;

    const nodeWith = forceDimensions?.width ?? getDefaultOrMinWidth(nodeMinComputeWidth, node);
    const nodeHeight = getDefaultOrMinHeight(nodeMinComputeHeight, node);

    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);
    if (node.data.resizedByUser) {
      if (nodeMinComputeWidth > previousDimensions.width) {
        node.width = nodeMinComputeWidth;
      } else {
        node.width = previousDimensions.width;
      }
      if (nodeMinComputeHeight > previousDimensions.height) {
        node.height = nodeMinComputeHeight;
      } else {
        node.height = previousDimensions.height;
      }
    } else {
      node.width = nodeWith;
      node.height = nodeHeight;
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

  calculateCustomNodeEdgeHandlePosition(
    node: Node<NodeData>,
    handlePosition: Position,
    handle: HandleElement
  ): XYPosition {
    let offsetX = handle.width / 2;
    let offsetY = handle.height / 2;
    const nodeWidth: number = node.width ?? 0;
    const nodeHeight: number = node.height ?? 0;
    const a: number = nodeWidth / 2;
    const b: number = nodeHeight / 2;

    let realY: number = handle.y;
    let realX: number = handle.x;
    switch (handlePosition) {
      case Position.Left:
        realX = Math.sqrt((1 - Math.pow(handle.y + offsetY - b, 2) / Math.pow(b, 2)) * Math.pow(a, 2)) + a;
        realX = nodeWidth - realX;
        break;
      case Position.Right:
        realX = Math.sqrt((1 - Math.pow(handle.y + offsetY - b, 2) / Math.pow(b, 2)) * Math.pow(a, 2)) + a;
        offsetX = -offsetX;
        break;
      case Position.Top:
        realY = Math.sqrt((1 - Math.pow(handle.x + offsetX - a, 2) / Math.pow(a, 2)) * Math.pow(b, 2)) + b;
        realY = nodeHeight - realY;
        break;
      case Position.Bottom:
        realY = Math.sqrt((1 - Math.pow(handle.x + offsetX - a, 2) / Math.pow(a, 2)) * Math.pow(b, 2)) + b;
        offsetY = -offsetY;
        break;
    }

    return {
      x: realX + offsetX,
      y: realY + offsetY,
    };
  }
}
