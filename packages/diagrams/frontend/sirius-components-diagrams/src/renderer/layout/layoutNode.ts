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
import { Box, Dimensions, Node, NodeInternals, Rect, XYPosition, boxToRect, rectToBox } from 'reactflow';
import { InsideLabel, NodeData } from '../DiagramRenderer.types';
import { computePreviousPosition } from './bounds';
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

export const getDefaultOrMinWidth = (minWidth: number | undefined, node: Node<NodeData>): number => {
  return Math.max(minWidth ?? -Infinity, node.data.defaultWidth ?? defaultWidth);
};

export const getDefaultOrMinHeight = (minHeight: number | undefined, node: Node<NodeData>): number => {
  return Math.max(minHeight ?? -Infinity, node.data.defaultHeight ?? defaultHeight);
};

export const getChildNodePosition = (
  allVisibleNodes: Node<NodeData>[],
  child: Node<NodeData>,
  headerHeightFootprint: number,
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
    return {
      x: rectangularNodePadding + borderWidth + maxWestBorderNodeWidth,
      y: borderWidth + headerHeightFootprint + maxNorthBorderNodeHeight,
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

export const getHeaderHeightFootprint = (
  labelElement: HTMLElement | null,
  insideLabel: InsideLabel | null,
  headerPosition: string | null
): number => {
  let headerHeightFootprint = 0;
  const withHeader: boolean = insideLabel?.isHeader ?? false;
  const displayHeaderSeparator: boolean = insideLabel?.displayHeaderSeparator ?? false;

  if (!labelElement) {
    return headerHeightFootprint;
  }
  if (withHeader && insideLabel?.headerPosition === headerPosition) {
    headerHeightFootprint = labelElement.getBoundingClientRect().height;
    if (displayHeaderSeparator) {
      headerHeightFootprint += rectangularNodePadding;
    }
  } else {
    headerHeightFootprint = rectangularNodePadding;
  }

  return headerHeightFootprint;
};

export const getInsideLabelWidthConstraint = (
  insideLabel: InsideLabel | null,
  labelElement: HTMLElement | null
): number => {
  if (insideLabel && labelElement) {
    // for other strategies, label width has no effect on node size
    if (insideLabel.overflowStrategy === 'NONE') {
      return labelElement?.getBoundingClientRect().width ?? 0;
    }
  }
  return 0;
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
      const outsideLabel = child.data.outsideLabels.BOTTOM_MIDDLE;
      let outsideLabelHeightFootPrint: number = 0;
      if (outsideLabel) {
        const outsideLabelElement = document.getElementById(`${outsideLabel.id}-label`);
        outsideLabelHeightFootPrint = outsideLabelElement?.getBoundingClientRect().height ?? 0;
      }
      const nodeBox = rectToBox({
        x: node !== child && child.data.isBorderNode ? node.position.x + child.position.x : child.position.x,
        y: node !== child && child.data.isBorderNode ? node.position.y + child.position.y : child.position.y,
        width: child.width ?? 0,
        height: (child.height ?? 0) + outsideLabelHeightFootPrint,
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

const getLowestSibling = (
  siblings: Node<NodeData>[],
  previousDiagram: RawDiagram | null
): Node<NodeData> | undefined => {
  return siblings
    .map((borderNode) => (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === borderNode.id))
    .reduce<Node<NodeData> | undefined>((lowestBorderNode, borderNode) => {
      if (!borderNode) return lowestBorderNode;
      if (!lowestBorderNode) return borderNode;

      const borderNodeBottom = borderNode.position.y + (borderNode.height ?? 0);
      const lowestBorderNodeBottom = lowestBorderNode.position.y + (lowestBorderNode.height ?? 0);

      return borderNodeBottom > lowestBorderNodeBottom ? borderNode : lowestBorderNode;
    }, undefined);
};

const getRightMostSibling = (
  siblings: Node<NodeData>[],
  previousDiagram: RawDiagram | null
): Node<NodeData> | undefined => {
  return siblings
    .map((borderNode) => (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === borderNode.id))
    .reduce<Node<NodeData> | undefined>((rightMostBorderNode, borderNode) => {
      if (!borderNode) return rightMostBorderNode;
      if (!rightMostBorderNode) return borderNode;

      const borderNodeRight = borderNode.position.x + (borderNode.width ?? 0);
      const rightMostBorderNodeRight = rightMostBorderNode.position.x + (rightMostBorderNode.width ?? 0);

      return borderNodeRight > rightMostBorderNodeRight ? borderNode : rightMostBorderNode;
    }, undefined);
};

export const setBorderNodesPosition = (
  borderNodes: Node<NodeData, string>[],
  nodeToLayout: Node<NodeData>,
  previousDiagram: RawDiagram | null,
  calculateCustomNodeBorderNodePosition?:
    | ((node: Node<NodeData>, borderNode: XYPosition & Dimensions, isDragging: boolean) => XYPosition)
    | undefined
): void => {
  if (calculateCustomNodeBorderNodePosition) {
    borderNodes.forEach((child) => {
      const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
      const previousPosition = computePreviousPosition(previousBorderNode, child);
      if (previousPosition) {
        child.position = calculateCustomNodeBorderNodePosition(
          nodeToLayout,
          {
            ...previousPosition,
            width: child.width ?? 0,
            height: child.height ?? 0,
          },
          false
        );
      }
    });
  } else {
    const borderNodesEast = borderNodes.filter(isEastBorderNode);
    borderNodesEast.forEach((child) => {
      const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
      const previousPosition = computePreviousPosition(previousBorderNode, child);
      if (previousPosition) {
        let newY = previousPosition.y;
        if (nodeToLayout.height && newY > nodeToLayout.height) {
          newY = nodeToLayout.height - borderNodeOffset;
        }
        child.position = {
          x: nodeToLayout.width ?? 0,
          y: newY,
        };
      } else {
        child.position = { x: nodeToLayout.width ?? 0, y: defaultNodeMargin };
        const previousSibling = getLowestSibling(borderNodesEast, previousDiagram);
        if (previousSibling) {
          child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) + gap };
          child.extent = getBorderNodeExtent(nodeToLayout, child);
        }
      }
      child.position.x = child.position.x - borderNodeOffset;
    });

    const borderNodesWest = borderNodes.filter(isWestBorderNode);
    borderNodesWest.forEach((child) => {
      const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
      const previousPosition = computePreviousPosition(previousBorderNode, child);
      if (previousPosition) {
        let newY = previousPosition.y;
        if (nodeToLayout.height && newY > nodeToLayout.height) {
          newY = nodeToLayout.height - borderNodeOffset;
        }
        child.position = {
          x: 0 - (child.width ?? 0),
          y: newY,
        };
      } else {
        child.position = { x: 0 - (child.width ?? 0), y: defaultNodeMargin };
        const previousSibling = getLowestSibling(borderNodesWest, previousDiagram);
        if (previousSibling) {
          child.position = { ...child.position, y: previousSibling.position.y + (previousSibling.height ?? 0) + gap };
        }
      }
      child.position.x = child.position.x + borderNodeOffset;
    });

    const borderNodesSouth = borderNodes.filter(isSouthBorderNode);
    borderNodesSouth.forEach((child) => {
      const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
      const previousPosition = computePreviousPosition(previousBorderNode, child);
      if (previousPosition) {
        let newX = previousPosition.x;
        if (nodeToLayout.width && newX > nodeToLayout.width) {
          newX = nodeToLayout.width - borderNodeOffset;
        }
        child.position = {
          x: newX,
          y: nodeToLayout.height ?? 0,
        };
      } else {
        child.position = { x: defaultNodeMargin, y: nodeToLayout.height ?? 0 };
        const previousSibling = getRightMostSibling(borderNodesSouth, previousDiagram);
        if (previousSibling) {
          child.position = { ...child.position, x: previousSibling.position.x + (previousSibling.width ?? 0) + gap };
          child.extent = getBorderNodeExtent(nodeToLayout, child);
        }
      }
      child.position.y = child.position.y - borderNodeOffset;
    });

    const borderNodesNorth = borderNodes.filter(isNorthBorderNode);
    borderNodesNorth.forEach((child) => {
      const previousBorderNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === child.id);
      const previousPosition = computePreviousPosition(previousBorderNode, child);
      if (previousPosition) {
        let newX = previousPosition.x;
        if (nodeToLayout.width && newX > nodeToLayout.width) {
          newX = nodeToLayout.width - borderNodeOffset;
        }
        child.position = {
          x: newX,
          y: 0 - (child.height ?? 0),
        };
      } else {
        child.position = { x: defaultNodeMargin, y: 0 - (child.height ?? 0) };
        const previousSibling = getRightMostSibling(borderNodesNorth, previousDiagram);
        if (previousSibling) {
          child.position = { ...child.position, x: previousSibling.position.x + (previousSibling.width ?? 0) + gap };
        }
      }
      child.position.y = child.position.y + borderNodeOffset;
    });
  }
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

export const isDescendantOf = (parent: Node, candidate: Node, nodeInternals: NodeInternals): boolean => {
  if (parent.id === candidate.id) {
    return true;
  } else {
    if (candidate.parentNode) {
      const candidateParent: Node | undefined = nodeInternals.get(candidate.parentNode);
      return !!candidateParent && isDescendantOf(parent, candidateParent, nodeInternals);
    }
    return false;
  }
};

export const isSiblingOrDescendantOf = (sibling: Node, candidate: Node, nodeInternals: NodeInternals): boolean => {
  if (sibling.parentNode === candidate.id) {
    return true;
  } else {
    if (candidate.parentNode) {
      const candidateParent: Node | undefined = nodeInternals.get(candidate.parentNode);
      return !!candidateParent && isSiblingOrDescendantOf(sibling, candidateParent, nodeInternals);
    }
    return false;
  }
};
