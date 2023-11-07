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
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  Diagram,
  DiagramNodeType,
  findNodeIndex,
  getBorderNodeExtent,
  getChildNodePosition,
  getEastBorderNodeFootprintHeight,
  getNodeOrMinHeight,
  getNodeOrMinWidth,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  ILayoutEngine,
  INodeLayoutHandler,
  NodeData,
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

      const createdNode = newlyAddedNode?.id === child.id ? newlyAddedNode : undefined;

      if (!!createdNode) {
        child.position = createdNode.position;
      } else if (previousNode) {
        child.position = previousNode.position;
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
    node.width = forceWidth ?? getNodeOrMinWidth(nodeWidth, node);
    node.height = getNodeOrMinHeight(nodeHeight, node);
    if (node.data.nodeDescription?.keepAspectRatio) {
      applyRatioOnNewNodeSizeValue(node);
    }

    // Update border nodes positions
    borderNodes.forEach((borderNode) => {
      borderNode.extent = getBorderNodeExtent(node, borderNode);
    });
    setBorderNodesPosition(borderNodes, node, previousDiagram);
  }
}
