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

import { Box, Node, Rect, boxToRect, rectToBox } from 'reactflow';
import { Diagram, NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';

const rectangularNodePadding = 8;
const defaultWidth = 150;
const defaultHeight = 70;

export class ListNodeLayoutHandler implements INodeLayoutHandler<ListNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'listNode';
  }

  public handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: Diagram | null,
    node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[]
  ) {
    const nodeIndex = this.findNodeIndex(visibleNodes, node.id);
    const nodeElement = document.getElementById(`${node.id}-listNode-${nodeIndex}`)?.children[0];
    const borderWidth = nodeElement ? parseFloat(window.getComputedStyle(nodeElement).borderWidth) : 0;

    if (directChildren.length > 0) {
      this.handleParentNode(layoutEngine, previousDiagram, node, visibleNodes, directChildren, borderWidth);
    } else {
      this.handleLeafNode(previousDiagram, node, visibleNodes, borderWidth);
    }
  }
  handleLeafNode(
    _previousDiagram: Diagram | null,
    node: Node<ListNodeData, 'listNode'>,
    _visibleNodes: Node<NodeData, DiagramNodeType>[],
    _borderWidth: number
  ) {
    node.width = this.getNodeOrMinWidth(undefined);
    node.height = this.getNodeOrMinHeight(undefined);
  }
  private handleParentNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: Diagram | null,
    node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    borderWidth: number
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren);

    const nodeIndex = this.findNodeIndex(visibleNodes, node.id);
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);

    const iconLabelNodes = directChildren.filter((child) => child.type === 'iconLabelNode');
    iconLabelNodes.forEach((child, index) => {
      child.position = {
        x: 0,
        y: (labelElement?.getBoundingClientRect().height ?? 0) + rectangularNodePadding,
      };
      const previousSibling = iconLabelNodes[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) };
      }
    });

    const childrenFootprint = this.getChildrenFootprint(iconLabelNodes);
    const childrenAwareNodeWidth = childrenFootprint.x + childrenFootprint.width + rectangularNodePadding;
    const labelOnlyWidth =
      rectangularNodePadding + (labelElement?.getBoundingClientRect().width ?? 0) + rectangularNodePadding;
    const nodeWidth = Math.max(childrenAwareNodeWidth, labelOnlyWidth) + borderWidth * 2;
    node.width = this.getNodeOrMinWidth(nodeWidth);
    node.height = this.getNodeOrMinHeight(
      (labelElement?.getBoundingClientRect().height ?? 0) +
        rectangularNodePadding +
        childrenFootprint.height +
        borderWidth * 2
    );

    if (nodeWidth > childrenAwareNodeWidth) {
      // we need to adjust the width of children
      iconLabelNodes.forEach((child) => {
        child.width = nodeWidth;
        child.style = {
          ...child.style,
          width: `${nodeWidth}px`,
        };
      });
    }
  }

  private findNodeIndex(nodes: Node<NodeData>[], nodeId: string): number {
    return nodes.findIndex((node) => node.id === nodeId);
  }

  private getChildrenFootprint(children: Node<NodeData>[]): Rect {
    const footPrint: Box = children.reduce(
      (currentFootPrint, node) => {
        const { x, y } = node.position;
        const nodeBox = rectToBox({
          x,
          y,
          width: node.width ?? 0,
          height: node.height ?? 0,
        });

        return this.getBoundsOfBoxes(currentFootPrint, nodeBox);
      },
      { x: Infinity, y: Infinity, x2: -Infinity, y2: -Infinity }
    );
    return boxToRect(footPrint);
  }

  private getBoundsOfBoxes(box1: Box, box2: Box): Box {
    return {
      x: Math.min(box1.x, box2.x),
      y: Math.min(box1.y, box2.y),
      x2: Math.max(box1.x2, box2.x2),
      y2: Math.max(box1.y2, box2.y2),
    };
  }

  private getNodeOrMinWidth(nodeWidth: number | undefined): number {
    return Math.max(nodeWidth ?? -Infinity, defaultWidth);
  }

  private getNodeOrMinHeight(nodeHeight: number | undefined): number {
    return Math.max(nodeHeight ?? -Infinity, defaultHeight);
  }
}
