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
import { Box, Node, Rect, XYPosition, boxToRect, rectToBox } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from './layout.types';
import {
  getBorderNodeExtent,
  isEastBorderNode,
  isNorthBorderNode,
  isSouthBorderNode,
  isWestBorderNode,
} from './layoutBorderNodes';
import {
  borderNodeOffset,
  defaultHeight,
  defaultNodeMargin,
  defaultWidth,
  gap,
  rectangularNodePadding,
} from './layoutParams';

/**
 * It requires that nodes are already positioned
 */
export const computeNodesBox = (allVisibleNodes: Node<NodeData>[], nodes: Node[]): Rect => {
  if (nodes.length <= 0) {
    return boxToRect({ x: 0, y: 0, x2: 0, y2: 0 });
  }

  const contentBox: Box = nodes.reduce<Box>(
    (currentBox, node) => {
      const bounds = getNodeFootprint(allVisibleNodes, node);
      const nodeBox = rectToBox({
        x: bounds.x,
        y: bounds.y,
        width: bounds.width ?? 0,
        height: bounds.height ?? 0,
      });

      return getBoundsOfBoxes(currentBox, nodeBox);
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

export const getNodeOrMinWidth = (nodeWidth: number | undefined, node: Node<NodeData>): number => {
  return Math.max(nodeWidth ?? -Infinity, node.data.defaultWidth ?? defaultWidth);
};

export const getNodeOrMinHeight = (nodeHeight: number | undefined, node: Node<NodeData>): number => {
  return Math.max(nodeHeight ?? -Infinity, node.data.defaultHeight ?? defaultHeight);
};

export const getChildNodePosition = (
  allVisibleNodes: Node<NodeData>[],
  child: Node<NodeData>,
  labelElement: HTMLElement | null,
  withHeader: boolean,
  displayHeaderSeparator: boolean,
  borderWidth: number,
  previousSibling?: Node<NodeData>
): XYPosition => {
  const maxWestBorderNodeWidth = getChildren(child, allVisibleNodes)
    .filter(isWestBorderNode)
    .map((borderNode) => getNodeFootprint(allVisibleNodes, borderNode).width || 0)
    .reduce((a, b) => Math.max(a, b), 0);

  const maxNorthBorderNodeHeight = getChildren(child, allVisibleNodes)
    .filter(isNorthBorderNode)
    .map((borderNode) => getNodeFootprint(allVisibleNodes, borderNode).height || 0)
    .reduce((a, b) => Math.max(a, b), 0);

  if (!previousSibling) {
    const headerFootprint = labelElement ? getHeaderFootprint(labelElement, withHeader, displayHeaderSeparator) : 0;

    return {
      x: rectangularNodePadding + borderWidth + maxWestBorderNodeWidth,
      y: borderWidth + headerFootprint + maxNorthBorderNodeHeight,
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

export const getHeaderFootprint = (
  labelElement: HTMLElement,
  withHeader: boolean,
  displayHeaderSeparator: boolean
): number => {
  let headerFootprint = 0;

  if (withHeader) {
    headerFootprint = labelElement.getBoundingClientRect().height;
    if (displayHeaderSeparator) {
      headerFootprint += rectangularNodePadding;
    }
  }

  return headerFootprint;
};

/**
 * Returns the node footprint.
 * It requires node border nodes to be positioned.
 *
 * WARN: It takes only node border nodes into account, it will be updated to support outside label and the margin
 *
 * @param allVisibleNodes all diagram visible nodes
 * @param node the node used to compute the footprint
 * @returns the rect that represents the node footprint
 */
const getNodeFootprint = (allVisibleNodes: Node<NodeData>[], node: Node<NodeData>): Rect => {
  const borderNodes = allVisibleNodes
    .filter((visibleNode) => visibleNode.parentNode === node.id)
    .filter((visibleNode) => visibleNode.data.isBorderNode);

  const footPrint: Box = [node, ...borderNodes].reduce<Box>(
    (currentFootPrint, child) => {
      const nodeBox = rectToBox({
        x: node !== child && child.data.isBorderNode ? node.position.x + child.position.x : child.position.x,
        y: node !== child && child.data.isBorderNode ? node.position.y + child.position.y : child.position.y,
        width: child.width ?? 0,
        height: child.height ?? 0,
      });

      return getBoundsOfBoxes(currentFootPrint, nodeBox);
    },
    { x: Infinity, y: Infinity, x2: -Infinity, y2: -Infinity }
  );
  return boxToRect(footPrint);
};

const spreadPositionedNodesFromNonPositionedNodes = (
  nodes: Node<NodeData>[],
  previousDiagram: RawDiagram | null
): { positionedNodes: Node<NodeData>[]; nonPositionedNodes: Node<NodeData>[] } => {
  const positionedNodes: Node<NodeData>[] = [];
  const nonPositionedNodes: Node<NodeData>[] = [];

  nodes.forEach((borderNode) => {
    const previousNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === borderNode.id);
    if (previousNode) {
      positionedNodes.push(previousNode);
    } else {
      nonPositionedNodes.push(borderNode);
    }
  });

  return {
    positionedNodes,
    nonPositionedNodes,
  };
};

export const setBorderNodesPosition = (
  borderNodes: Node<NodeData>[],
  nodeToLayout: Node<NodeData>,
  previousDiagram: RawDiagram | null
): void => {
  const borderNodesEast = borderNodes.filter(isEastBorderNode);
  borderNodesEast.forEach((child, index) => {
    const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
    if (previousBorderNode) {
      child.position = {
        x: nodeToLayout.width ?? 0,
        y: previousBorderNode.position.y,
      };
    } else {
      child.position = { x: nodeToLayout.width ?? 0, y: defaultNodeMargin };
      const previousSibling = borderNodesEast[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) + gap };
        child.extent = getBorderNodeExtent(nodeToLayout, child);
      }
    }
    child.position.x = child.position.x - borderNodeOffset;
  });

  const borderNodesWest = borderNodes.filter(isWestBorderNode);
  borderNodesWest.forEach((child, index) => {
    const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
    if (previousBorderNode) {
      child.position = {
        x: 0 - (child.width ?? 0),
        y: previousBorderNode.position.y,
      };
    } else {
      child.position = { x: 0 - (child.width ?? 0), y: defaultNodeMargin };
      const previousSibling = borderNodesWest[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) + gap };
      }
    }
    child.position.x = child.position.x + borderNodeOffset;
  });

  const borderNodesSouth = borderNodes.filter(isSouthBorderNode);
  borderNodesSouth.forEach((child, index) => {
    const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
    if (previousBorderNode) {
      child.position = {
        x: previousBorderNode.position.x,
        y: nodeToLayout.height ?? 0,
      };
    } else {
      child.position = { x: defaultNodeMargin, y: nodeToLayout.height ?? 0 };
      const previousSibling = borderNodesSouth[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, x: previousSibling.position.x + (previousSibling.width ?? 0) + gap };
        child.extent = getBorderNodeExtent(nodeToLayout, child);
      }
    }
    child.position.y = child.position.y - borderNodeOffset;
  });

  const borderNodesNorth = borderNodes.filter(isNorthBorderNode);
  borderNodesNorth.forEach((child, index) => {
    const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
    if (previousBorderNode) {
      child.position = {
        x: previousBorderNode.position.x,
        y: 0 - (child.height ?? 0),
      };
    } else {
      child.position = { x: defaultNodeMargin, y: 0 - (child.height ?? 0) };
      const previousSibling = borderNodesNorth[index - 1];
      if (previousSibling) {
        child.position = { ...child.position, x: previousSibling.position.x + (previousSibling.width ?? 0) + gap };
      }
    }
    child.position.y = child.position.y + borderNodeOffset;
  });
};

/**
 * Returns the footprint width of north border nodes from given border nodes.
 *
 * It relies on _computeNodesBox_ to compute the width of already positioned border nodes.
 * Then it considers new border nodes by adding their width and a gap if necessary.
 *
 * @param allVisibleNodes all diagram visible nodes
 * @param borderNodes The border nodes used to compute the footprint width
 * @param previousDiagram The previous diagram
 * @returns the footprint width of north border nodes from given border nodes
 */
export const getNorthBorderNodeFootprintWidth = (
  allVisibleNodes: Node<NodeData>[],
  borderNodes: Node<NodeData>[],
  previousDiagram: RawDiagram | null
): number => {
  const northBorderNodes = borderNodes.filter(isNorthBorderNode);
  const { positionedNodes: previousBorderNodes, nonPositionedNodes: nonPositionedBorderNode } =
    spreadPositionedNodesFromNonPositionedNodes(northBorderNodes, previousDiagram);
  const previousBorderNodeFootprint = computeNodesBox(allVisibleNodes, previousBorderNodes);

  return nonPositionedBorderNode.reduce<number>(
    (accumulatedSideWidth, currentBorderNode, currentBordernodeIndex) =>
      accumulatedSideWidth +
      (getNodeFootprint(allVisibleNodes, currentBorderNode).width ?? 0) +
      (currentBordernodeIndex < nonPositionedBorderNode.length - 1 ? gap : 0), // WARN: Once getNodeFootprint will take the margin into account, we will have to merge the footprint margin of two siblings next to each other and remove the next line under.
    // WARN: Because computeNodeBox rely on getNodeFootprint which do not take the margin into account we must add the margin twice.
    // This will be updated once footprint will take the margin into account
    previousBorderNodeFootprint.width + defaultNodeMargin * 2
  );
};

/**
 * Returns the footprint width of south border nodes from given border nodes.
 *
 * It relies on _computeNodesBox_ to compute the width of already positioned border nodes.
 * Then it considers new border nodes by adding their width and a gap if necessary.
 *
 * @param allVisibleNodes all diagram visible nodes
 * @param borderNodes The border nodes used to compute the footprint width
 * @param previousDiagram The previous diagram
 * @returns the footprint width of south border nodes from given border nodes
 */
export const getSouthBorderNodeFootprintWidth = (
  allVisibleNodes: Node<NodeData>[],
  borderNodes: Node<NodeData>[],
  previousDiagram: RawDiagram | null
): number => {
  const southBorderNodes = borderNodes.filter(isSouthBorderNode);
  const { positionedNodes: previousBorderNodes, nonPositionedNodes: nonPositionedBorderNode } =
    spreadPositionedNodesFromNonPositionedNodes(southBorderNodes, previousDiagram);
  const previousBorderNodeFootprint = computeNodesBox(allVisibleNodes, previousBorderNodes);

  return nonPositionedBorderNode.reduce<number>(
    (accumulatedSideWidth, currentBorderNode, currentBordernodeIndex) =>
      accumulatedSideWidth +
      (getNodeFootprint(allVisibleNodes, currentBorderNode).width ?? 0) + // WARN: Once getNodeFootprint will take the margin into account, we will have to merge the footprint margin of two siblings next to each other and remove the next line under.
      (currentBordernodeIndex < nonPositionedBorderNode.length - 1 ? gap : 0),
    // WARN: Because computeNodeBox rely on getNodeFootprint which do not take the margin into account we must add the margin twice.
    // This will be updated once footprint will take the margin into account
    previousBorderNodeFootprint.width + defaultNodeMargin * 2
  );
};

/**
 * Returns the footprint height of east border nodes from given border nodes.
 *
 * It relies on _computeNodesBox_ to compute the height of already positioned border nodes.
 * Then it considers new border nodes by adding their height and a gap if necessary.
 *
 * @param allVisibleNodes all diagram visible nodes
 * @param borderNodes The border nodes used to compute the footprint height
 * @param previousDiagram The previous diagram
 * @returns the footprint height of east border nodes from given border nodes
 */
export const getEastBorderNodeFootprintHeight = (
  allVisibleNodes: Node<NodeData>[],
  borderNodes: Node<NodeData>[],
  previousDiagram: RawDiagram | null
): number => {
  const eastBorderNodes = borderNodes.filter(isEastBorderNode);
  const { positionedNodes: previousBorderNodes, nonPositionedNodes: nonPositionedBorderNode } =
    spreadPositionedNodesFromNonPositionedNodes(eastBorderNodes, previousDiagram);
  const previousBorderNodeFootprint = computeNodesBox(allVisibleNodes, previousBorderNodes);

  return nonPositionedBorderNode.reduce<number>(
    (accumulatedSideHeight, currentBorderNode, currentBordernodeIndex) =>
      accumulatedSideHeight +
      (getNodeFootprint(allVisibleNodes, currentBorderNode).height ?? 0) +
      (currentBordernodeIndex < nonPositionedBorderNode.length - 1 ? gap : 0), // WARN: Once getNodeFootprint will take the margin into account, we will have to merge the footprint margin of two siblings next to each other and remove the next line under.
    // WARN: Because computeNodeBox rely on getNodeFootprint which do not take the margin into account we must add the margin twice.
    // This will be updated once footprint will take the margin into account
    previousBorderNodeFootprint.height + defaultNodeMargin * 2
  );
};

/**
 * Returns the footprint height of west border nodes from given border nodes.
 *
 * It relies on _computeNodesBox_ to compute the height of already positioned border nodes.
 * Then it considers new border nodes by adding their height and a gap if necessary.
 *
 * @param allVisibleNodes all diagram visible nodes
 * @param borderNodes The border nodes used to compute the footprint height
 * @param previousDiagram The previous diagram
 * @returns the footprint height of west border nodes from given border nodes
 */
export const getWestBorderNodeFootprintHeight = (
  allVisibleNodes: Node<NodeData>[],
  borderNodes: Node<NodeData>[],
  previousDiagram: RawDiagram | null
): number => {
  const westBorderNodes = borderNodes.filter(isWestBorderNode);
  const { positionedNodes: previousBorderNodes, nonPositionedNodes: nonPositionedBorderNode } =
    spreadPositionedNodesFromNonPositionedNodes(westBorderNodes, previousDiagram);
  const previousBorderNodeFootprint = computeNodesBox(allVisibleNodes, previousBorderNodes);

  return nonPositionedBorderNode.reduce<number>(
    (accumulatedSideHeight, currentBorderNode, currentBordernodeIndex) =>
      accumulatedSideHeight +
      (getNodeFootprint(allVisibleNodes, currentBorderNode).height ?? 0) +
      (currentBordernodeIndex < nonPositionedBorderNode.length - 1 ? gap : 0), // WARN: Once getNodeFootprint will take the margin into account, we will have to merge the footprint margin of two siblings next to each other and remove the next line under.
    // WARN: Because computeNodeBox rely on getNodeFootprint which do not take the margin into account we must add the margin twice.
    // This will be updated once footprint will take the margin into account
    previousBorderNodeFootprint.height + defaultNodeMargin * 2
  );
};

export const getChildren = (node: Node<NodeData>, allVisibleNodes: Node<NodeData>[]): Node<NodeData>[] => {
  return allVisibleNodes.filter((child) => child.parentNode === node.id);
};

export const applyRatioOnNewNodeSizeValue = (node: Node<NodeData>) => {
  const initRatio = (node.data.defaultWidth || defaultWidth) / (node.data.defaultHeight || defaultHeight);
  if (node.width && node.height) {
    const newRatio = node.width / node.height;
    if (initRatio > newRatio) {
      node.width = node.height * initRatio;
    }
    if (initRatio < newRatio) {
      node.height = node.width / initRatio;
    }
  }
};
