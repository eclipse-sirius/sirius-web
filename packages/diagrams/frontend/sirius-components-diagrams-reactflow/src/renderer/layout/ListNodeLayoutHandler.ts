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

import { Node, NodeChange, NodeDimensionChange } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { computePreviousSize } from './bounds';
import { RawDiagram } from './layout.types';
import { getBorderNodeExtent } from './layoutBorderNodes';
import {
  applyRatioOnNewNodeSizeValue,
  computeNodesBox,
  findNodeIndex,
  getDefaultOrMinHeight,
  getDefaultOrMinWidth,
  getEastBorderNodeFootprintHeight,
  getNorthBorderNodeFootprintWidth,
  getSouthBorderNodeFootprintWidth,
  getWestBorderNodeFootprintHeight,
  setBorderNodesPosition,
} from './layoutNode';

export class ListNodeLayoutHandler implements INodeLayoutHandler<ListNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'listNode';
  }

  public handle2(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    _node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, string>[],
    directChildren: Node<NodeData, string>[],
    newlyAddedNode: Node<NodeData, string> | undefined,
    nodeDimensionChange: NodeDimensionChange,
    forceDimension?: { width?: number; height?: number } | undefined
  ): NodeChange[] {
    const nodeChanges: NodeChange[] = [];

    if (directChildren.length > 0) {
      // Compute the minimum size of children
      layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNode);

      // Reduce the height of the forced dimension by the label height to split remaining space between children
      let contentForcedDimension: { width?: number; height?: number } = {};
      if (forceDimension) {
        if (forceDimension.height) {
          contentForcedDimension.height = forceDimension.height - 37 - 2; // remove the label height is 37 and twice the border size
        }
        if (forceDimension.width) {
          contentForcedDimension.width = forceDimension.width - 2; // Remove the border size twice
        }
      }

      directChildren.forEach((child, index) => {
        const directGrandChildren = visibleNodes.filter((visibleNode) => visibleNode.parentNode === child.id);
        if (directGrandChildren.length === 4) {
          // console.log(child.width);
        }

        let widthTaken: number = contentForcedDimension.width ?? 0;
        if ((child.width ?? 0) > (contentForcedDimension.width ?? 0)) {
          widthTaken = child.width ?? 0;
        }
        let heightTaken: number;
        if (index < directChildren.length - 1) {
          heightTaken = child.height ?? 0;
          if (contentForcedDimension.height) {
            contentForcedDimension.height = contentForcedDimension.height - heightTaken;
          }
        } else {
          // The last child have the remaining space if it is greater than the minimal size
          heightTaken = contentForcedDimension.height ?? 0;
          if ((child.height ?? 0) > (contentForcedDimension.height ?? 0)) {
            heightTaken = child.height ?? 0;
          }
        }

        if (widthTaken !== child.width || heightTaken !== child.height) {
          const childDimensionChange: NodeDimensionChange = {
            ...nodeDimensionChange,
            id: child.id,
            dimensions: {
              width: widthTaken,
              height: heightTaken,
            },
          };
          const grandChildrenDimensionChanges: NodeChange[] = layoutEngine.partialNodeLayout(
            previousDiagram,
            visibleNodes,
            [child],
            childDimensionChange,
            childDimensionChange.dimensions
          );
          Trouver comment s'arrêter à la taille minimal (a faire potentiellement dans le parend de ListNodeLayoutHandler ? ou au global quand on a récupérer tout les node dimension change)
          - Regarder ce qui est retourner dans les nodeChanges aux différents niveaux et voir ce qu'il est possible de faire pour stopper le resize si trop petit
          
          Pour fixer le problème du compartiment qui reprend la taille min à la fin du resize
          - Regarder dans le `useLayoutOnBoundsChange` et voir se qu'il s'y passe.
          // if (directChildren.length === 2) {
          console.log(childDimensionChange.dimensions?.width);
          console.log('---');

          grandChildrenDimensionChanges.forEach((grandChildrenDimensionChange: any) => {
            console.log(grandChildrenDimensionChange.dimensions?.width);
          });
          console.log('-------------------');
          // }

          nodeChanges.push(childDimensionChange);
          nodeChanges.push(...grandChildrenDimensionChanges);
        }
      });
    } else {
    }

    return nodeChanges;
  }

  public handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, 'listNode'>,
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
    node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    borderWidth: number,
    forceWidth?: number
  ) {
    const labelElement = document.getElementById(`${node.id}-label-${findNodeIndex(visibleNodes, node.id)}`);

    const nodeMinComputeWidth = (labelElement?.getBoundingClientRect().width ?? 0) + borderWidth * 2;
    const nodeMinComputeHeight = (labelElement?.getBoundingClientRect().height ?? 0) + borderWidth * 2;
    const nodeWith = forceWidth ?? getDefaultOrMinWidth(nodeMinComputeWidth, node);
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
  }

  private handleParentNode(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    borderWidth: number,
    forceWidth?: number
  ) {
    layoutEngine.layoutNodes(previousDiagram, visibleNodes, directChildren, newlyAddedNode, forceWidth);

    const nodeIndex = findNodeIndex(visibleNodes, node.id);
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);

    const borderNodes = directChildren.filter((node) => node.data.isBorderNode);
    const directNodesChildren = directChildren.filter((child) => !child.data.isBorderNode);
    const northBorderNodeFootprintWidth = getNorthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);
    const southBorderNodeFootprintWidth = getSouthBorderNodeFootprintWidth(visibleNodes, borderNodes, previousDiagram);

    const previousNode: Node<NodeData, string> | undefined = (previousDiagram?.nodes ?? []).find(
      (previouseNode) => previouseNode.id === node.id
    );

    if (!forceWidth) {
      let previousChildrenContentBoxWidthToConsider: number = 0;
      if (node.data.resizedByUser) {
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

    this.computeChildrenPosition(directNodesChildren, labelElement, node, borderWidth);

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

    node.width = forceWidth ?? getDefaultOrMinWidth(nodeWidth, node);

    const minNodeheight = getDefaultOrMinHeight(nodeHeight, node);
    if (node.data.resizedByUser && previousNode) {
      if (minNodeheight > (previousNode.height ?? 0)) {
        node.height = minNodeheight;
      } else {
        node.height = previousNode.height;
      }
    } else {
      node.height = minNodeheight;
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

  computeChildrenPosition(
    directNodesChildren: Node<NodeData, string>[],
    labelElement: HTMLElement | null,
    node: Node<ListNodeData, 'listNode'>,
    borderWidth: number
  ) {
    const withHeader: boolean = node.data.insideLabel?.isHeader ?? false;

    directNodesChildren.forEach((child, index) => {
      child.position = {
        x: borderWidth,
        y: borderWidth + (withHeader ? labelElement?.getBoundingClientRect().height ?? 0 : 0),
      };
      const previousSibling = directNodesChildren[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) };
      }
    });
  }
}
