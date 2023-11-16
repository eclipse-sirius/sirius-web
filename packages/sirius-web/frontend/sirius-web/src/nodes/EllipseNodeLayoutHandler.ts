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
import {
  Diagram,
  DiagramNodeType,
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
  getHeaderFootprintHeight,
  getNodeOrMinHeight,
  getNodeOrMinWidth,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  setBorderNodesPosition,
} from '@eclipse-sirius/sirius-components-diagrams-reactflow';
import { Node } from 'reactflow';

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
    forceWidth?: number
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
      const previousPosition = computePreviousPosition(previousNode, node);
      const createdNode = newlyAddedNode?.id === child.id ? newlyAddedNode : undefined;

      if (!!createdNode) {
        // WARN: this prevent the created to overlep the TOP header. It is a quick fix but a proper solution should be implemented.
        const headerHeightFootprint = labelElement ? getHeaderFootprintHeight(labelElement, false, false) : 0;
        child.position = createdNode.position;
        if (child.position.y < borderWidth + headerHeightFootprint) {
          child.position = { ...child.position, y: borderWidth + headerHeightFootprint };
        }
      } else if (previousPosition) {
        // WARN: this prevent the moved node to overlep the TOP header or appear outside of its container. It is a quick fix but a proper solution should be implemented.
        const headerHeightFootprint = labelElement ? getHeaderFootprintHeight(labelElement, false, false) : 0;
        child.position = previousPosition;
        if (child.position.y < borderWidth + headerHeightFootprint) {
          child.position = { ...child.position, y: borderWidth + headerHeightFootprint };
        }
        if (child.position.x < borderWidth) {
          child.position = { ...child.position, x: borderWidth };
        }
      } else {
        child.position = child.position = getChildNodePosition(
          visibleNodes,
          child,
          labelElement,
          false,
          false,
          borderWidth
        );
        const previousSibling = directNodesChildren[index - 1];
        if (previousSibling) {
          child.position = getChildNodePosition(
            visibleNodes,
            child,
            labelElement,
            false,
            false,
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
    const labelOnlyWidth = labelElement?.getBoundingClientRect().width ?? 0;

    const nodeWidth =
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

    const nodeHeight =
      Math.max(directChildrenAwareNodeHeight, eastBorderNodeFootprintHeight, westBorderNodeFootprintHeight) +
      borderWidth * 2;

    const minNodeWith = forceWidth ?? getNodeOrMinWidth(nodeWidth, node); // WARN: not sure yet for the forceWidth to be here.
    const minNodeheight = getNodeOrMinHeight(nodeHeight, node);

    const previousNode = (previousDiagram?.nodes ?? []).find((previouseNode) => previouseNode.id === node.id);
    const previousDimensions = computePreviousSize(previousNode, node);
    if (node.data.nodeDescription?.userResizable) {
      if (minNodeWith > previousDimensions.width) {
        node.width = minNodeWith;
      } else {
        node.width = previousDimensions.width;
      }
      if (minNodeheight > previousDimensions.height) {
        node.height = minNodeheight;
      } else {
        node.height = previousDimensions.height;
      }
    } else {
      node.width = minNodeWith;
      node.height = minNodeheight;
    }

    if (node.data.nodeDescription.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }

    // Update border nodes positions
    borderNodes.forEach((borderNode) => {
      borderNode.extent = getBorderNodeExtent(node, borderNode);
    });
    setBorderNodesPosition(borderNodes, node, previousDiagram);
  }
}
