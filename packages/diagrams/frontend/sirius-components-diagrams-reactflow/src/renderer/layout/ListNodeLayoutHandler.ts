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
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousSize } from './bounds';
import { RawDiagram } from './layout.types';
import { getBorderNodeExtent } from './layoutBorderNodes';
import {
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  findNodeIndex,
  getEastBorderNodeFootprintHeight,
  getHeaderFootprintHeight,
  getNodeOrMinHeight,
  getNodeOrMinWidth,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  setBorderNodesPosition,
} from './layoutNode';

export class ListNodeLayoutHandler implements INodeLayoutHandler<NodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'listNode';
  }

  public handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<NodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceWidth?: number
  ) {
    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const nodeElement = document.getElementById(`${node.id}-listNode-${nodeIndex}`)?.children[0];
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

  handleLeafNode(
    previousDiagram: RawDiagram | null,
    node: Node<NodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    forceWidth?: number
  ) {
    const labelElement = document.getElementById(`${node.id}-label-${findNodeIndex(visibleNodes, node.id)}`);

    const labelWidth = (labelElement?.getBoundingClientRect().width ?? 0) + borderWidth * 2;
    const labelHeight = (labelElement?.getBoundingClientRect().height ?? 0) + borderWidth * 2;
    const minNodeWith = forceWidth ?? getNodeOrMinWidth(labelWidth, node);
    const minNodeheight = getNodeOrMinHeight(labelHeight, node);

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
  }

  private handleParentNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<NodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    borderWidth: number,
    forceWidth?: number
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNode, forceWidth);

    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);
    const withHeader: boolean = node.data.insideLabel?.isHeader ?? false;
    const displayHeaderSeparator: boolean = node.data.insideLabel?.displayHeaderSeparator ?? false;

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);

    const previousNode: Node<NodeData, string> | undefined = (previousDiagram?.nodes ?? []).find(
      (previouseNode) => previouseNode.id === node.id
    );

    if (!forceWidth) {
      let previousChildrenContentBoxWidthToConsider: number = 0;
      if (node.data.nodeDescription?.userResizable) {
        previousChildrenContentBoxWidthToConsider = (previousNode?.width ?? 0) - borderWidth * 2;
      }
      const widerWidth = Math.max(
        directNodesChildren.reduce<number>(
          (widerWidth, child) => Math.max(child.width ?? 0, widerWidth),
          labelElement?.getBoundingClientRect().width ?? 0
        ),
        northBorderNodeFootprintWidth,
        southBorderNodeFootprintWidth,
        previousChildrenContentBoxWidthToConsider
      );

      layoutEngine.layoutNodes(previousDiagram, visibleNodes, directNodesChildren, newlyAddedNode, widerWidth);
    }

    directNodesChildren.forEach((child, index) => {
      const headerFootprintHeight = getHeaderFootprintHeight(labelElement, withHeader, displayHeaderSeparator);
      child.position = {
        x: borderWidth,
        y: borderWidth + headerFootprintHeight,
      };
      const previousSibling = directNodesChildren[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) };
      }
    });

    const childrenContentBox = computeNodesBox(visibleNodes, directNodesChildren);

    const labelOnlyWidth = labelElement?.getBoundingClientRect().width ?? 0;
    const nodeWidth = Math.max(childrenContentBox.width, labelOnlyWidth) + borderWidth * 2;

    const directChildrenAwareNodeHeight = childrenContentBox.y + childrenContentBox.height + borderWidth;

    const eastBorderNodeFootprintHeight = getEastBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);
    const westBorderNodeFootprintHeight = getWestBorderNodeFootprintHeight(visibleNodes, borderNodes, previousDiagram);

    const nodeHeight = Math.max(
      directChildrenAwareNodeHeight,
      eastBorderNodeFootprintHeight,
      westBorderNodeFootprintHeight
    );

    node.width = forceWidth ?? getNodeOrMinWidth(nodeWidth, node);

    const minNodeheight = getNodeOrMinHeight(nodeHeight, node);
    if (node.data.nodeDescription?.userResizable && previousNode) {
      if (minNodeheight > (previousNode.height ?? 0)) {
        node.height = minNodeheight;
      } else {
        node.height = previousNode.height;
      }
    } else {
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
