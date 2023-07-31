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
import { NodeData } from '../DiagramRenderer.types';
import {
  getBorderNodeExtent,
  isEastBorderNode,
  isNorthBorderNode,
  isSouthBorderNode,
  isWestBorderNode,
} from './layoutBorderNodes';
import { defaultHeight, defaultWidth, gap, rectangularNodePadding } from './layoutParams';

export const computeContentBox = (allVisibleNodes: Node<NodeData>[], children: Node[]): Rect => {
  if (children.length <= 0) {
    return boxToRect({ x: 0, y: 0, x2: 0, y2: 0 });
  }

  const contentBox: Box = children.reduce<Box>(
    (currentContentBox, child) => {
      const bounds = getNodeFootprint(allVisibleNodes, child);
      const nodeBox = rectToBox({
        x: bounds.x,
        y: bounds.y,
        width: bounds.width ?? 0,
        height: bounds.height ?? 0,
      });

      return getBoundsOfBoxes(currentContentBox, nodeBox);
    },
    { x: Infinity, y: Infinity, x2: -Infinity, y2: -Infinity }
  );
  return boxToRect(contentBox);
};

const getBoundsOfBoxes = (box1: Box, box2: Box): Box => {
  return {
    x: Math.min(box1.x, box2.x),
    y: Math.min(box1.y, box2.y),
    x2: Math.max(box1.x2, box2.x2),
    y2: Math.max(box1.y2, box2.y2),
  };
};

export const findNodeIndex = (nodes: Node<NodeData>[], nodeId: string): number => {
  return nodes.findIndex((node) => node.id === nodeId);
};

export const getNodeOrMinWidth = (nodeWidth: number | undefined): number => {
  return Math.max(nodeWidth ?? -Infinity, defaultWidth);
};

export const getNodeOrMinHeight = (nodeHeight: number | undefined): number => {
  return Math.max(nodeHeight ?? -Infinity, defaultHeight);
};

export const getChildNodePosition = (
  allVisibleNodes: Node<NodeData>[],
  child: Node<NodeData>,
  labelElement: HTMLElement | null,
  borderWidth: number,
  previousSibling?: Node<NodeData>
) => {
  const maxWestBorderNodeWidth = getChildren(child, allVisibleNodes)
    .filter(isWestBorderNode)
    .map((borderNode) => getNodeFootprint(allVisibleNodes, borderNode).width || 0)
    .reduce((a, b) => Math.max(a, b), 0);

  const maxNorthBorderNodeHeight = getChildren(child, allVisibleNodes)
    .filter(isNorthBorderNode)
    .map((borderNode) => getNodeFootprint(allVisibleNodes, borderNode).height || 0)
    .reduce((a, b) => Math.max(a, b), 0);

  if (!previousSibling) {
    return {
      x: rectangularNodePadding + borderWidth + maxWestBorderNodeWidth,
      y:
        borderWidth +
        rectangularNodePadding +
        maxNorthBorderNodeHeight +
        (labelElement?.getBoundingClientRect().height ?? 0) +
        rectangularNodePadding,
    };
  } else {
    const previousSiblingsMaxEastBorderNodeWidth = getChildren(previousSibling, allVisibleNodes)
      .filter(isEastBorderNode)
      .map((borderNode) => getNodeFootprint(allVisibleNodes, borderNode).width || 0)
      .reduce((a, b) => Math.max(a, b), 0);

    return {
      ...child.position,
      x:
        previousSibling.position.x +
        previousSiblingsMaxEastBorderNodeWidth +
        maxWestBorderNodeWidth +
        (previousSibling.width ?? 0) +
        gap,
    };
  }
};

// WARN: This does not take the node margin into account (it should)
// WARN: This should return only the Dimensions
const getNodeFootprint = (allVisibleNodes: Node<NodeData>[], node: Node<NodeData>): Rect => {
  const borderNodes = allVisibleNodes
    .filter((visibleNode) => visibleNode.parentNode === node.id)
    .filter((visibleNode) => visibleNode.data.isBorderNode);

  const footPrint: Box = [node, ...borderNodes].reduce<Box>(
    (currentFootPrint, child) => {
      const nodeBox = rectToBox({
        x: child.data.isBorderNode ? node.position.x + child.position.x : child.position.x,
        y: child.data.isBorderNode ? node.position.y + child.position.y : child.position.y,
        width: child.width ?? 0,
        height: child.height ?? 0,
      });

      return getBoundsOfBoxes(currentFootPrint, nodeBox);
    },
    { x: Infinity, y: Infinity, x2: -Infinity, y2: -Infinity }
  );
  return boxToRect(footPrint);
};

export const getNorthBorderNodeFootprintWidth = (
  allVisibleNodes: Node<NodeData>[],
  borderNodes: Node<NodeData>[]
): number => {
  const northBorderNodes = borderNodes.filter(isNorthBorderNode);

  return northBorderNodes.reduce<number>(
    (accumulatedSideWidth, currentBorderNode, currentBordernodeIndex) =>
      accumulatedSideWidth +
      (getNodeFootprint(allVisibleNodes, currentBorderNode).width ?? 0) +
      (currentBordernodeIndex < northBorderNodes.length ? gap : 0), // WARN: Once getNodeFootprint will take the margin into account, we will have to merge the footprint margin of two siblings next to each other and remove the next line under.
    0
  );
};

export const setBorderNodesPosition = (borderNodes: Node<NodeData>[], nodeToLayout: Node<NodeData>) => {
  const borderNodesEast = borderNodes.filter(isEastBorderNode);
  borderNodesEast.forEach((child, index) => {
    child.position = { x: nodeToLayout.width ?? 0, y: rectangularNodePadding };
    const previousSibling = borderNodesEast[index - 1];
    if (previousSibling) {
      child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) + gap };
      child.extent = getBorderNodeExtent(nodeToLayout, child);
    }
  });

  const borderNodesWest = borderNodes.filter(isWestBorderNode);
  borderNodesWest.forEach((child, index) => {
    child.position = { x: 0 - (child.width ?? 0), y: rectangularNodePadding };
    const previousSibling = borderNodesWest[index - 1];
    if (previousSibling) {
      child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) + gap };
    }
  });

  const borderNodesSouth = borderNodes.filter(isSouthBorderNode);
  borderNodesSouth.forEach((child, index) => {
    child.position = { x: rectangularNodePadding, y: nodeToLayout.height ?? 0 };
    const previousSibling = borderNodesSouth[index - 1];
    if (previousSibling) {
      child.position = { ...child.position, x: previousSibling.position.x + (previousSibling.width ?? 0) + gap };
      child.extent = getBorderNodeExtent(nodeToLayout, child);
    }
  });

  const borderNodesNorth = borderNodes.filter(isNorthBorderNode);
  borderNodesNorth.forEach((child, index) => {
    child.position = { x: rectangularNodePadding, y: 0 - (child.height ?? 0) };
    const previousSibling = borderNodesNorth[index - 1];
    if (previousSibling) {
      child.position = { ...child.position, x: previousSibling.position.x + (previousSibling.width ?? 0) + gap };
    }
  });
};

export const getSouthBorderNodeFootprintWidth = (
  allVisibleNodes: Node<NodeData>[],
  borderNodes: Node<NodeData>[]
): number => {
  const southBorderNodes = borderNodes.filter(isSouthBorderNode);

  return southBorderNodes.reduce<number>(
    (accumulatedSideWidth, currentBorderNode, currentBordernodeIndex) =>
      accumulatedSideWidth +
      (getNodeFootprint(allVisibleNodes, currentBorderNode).width ?? 0) + // WARN: Once getNodeFootprint will take the margin into account, we will have to merge the footprint margin of two siblings next to each other and remove the next line under.
      (currentBordernodeIndex < southBorderNodes.length ? gap : 0),
    0
  );
};

export const getEastBorderNodeFootprintHeight = (
  allVisibleNodes: Node<NodeData>[],
  borderNodes: Node<NodeData>[]
): number => {
  const eastBorderNodes = borderNodes.filter(isEastBorderNode);

  return eastBorderNodes.reduce<number>(
    (accumulatedSideHeight, currentBorderNode, currentBordernodeIndex) =>
      accumulatedSideHeight +
      (getNodeFootprint(allVisibleNodes, currentBorderNode).height ?? 0) +
      (currentBordernodeIndex < eastBorderNodes.length ? gap : 0), // WARN: Once getNodeFootprint will take the margin into account, we will have to merge the footprint margin of two siblings next to each other and remove the next line under.
    0
  );
};

export const getWestBorderNodeFootprintHeight = (
  allVisibleNodes: Node<NodeData>[],
  borderNodes: Node<NodeData>[]
): number => {
  const westBorderNodes = borderNodes.filter(isWestBorderNode);

  return westBorderNodes.reduce<number>(
    (accumulatedSideHeight, currentBorderNode, currentBordernodeIndex) =>
      accumulatedSideHeight +
      (getNodeFootprint(allVisibleNodes, currentBorderNode).height ?? 0) +
      (currentBordernodeIndex < westBorderNodes.length ? gap : 0), // WARN: Once getNodeFootprint will take the margin into account, we will have to merge the footprint margin of two siblings next to each other and remove the next line under.
    0
  );
};

export const getChildren = (node: Node<NodeData>, allVisibleNodes: Node<NodeData>[]): Node<NodeData>[] => {
  return allVisibleNodes.filter((child) => child.parentNode === node.id);
};
