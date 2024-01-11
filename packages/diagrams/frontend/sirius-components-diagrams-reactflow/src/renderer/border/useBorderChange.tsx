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
import { useCallback } from 'react';
import { Edge, Node, NodeChange, NodeDimensionChange, useReactFlow, XYPosition } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { findBorderNodePosition } from '../layout/layoutBorderNodes';
import { LayoutEngine } from '../layout/LayoutEngine';
import { ILayoutEngine } from '../layout/LayoutEngine.types';
import { borderNodeOffset } from '../layout/layoutParams';
import { UseBorderChangeValue } from './useBorderChange.types';

const isNewPositionInsideIsParent = (newNodePosition: XYPosition, movedNode: Node, parentNode: Node): boolean => {
  if (movedNode.width && movedNode.height && parentNode?.positionAbsolute && parentNode.width && parentNode.height) {
    return (
      parentNode.positionAbsolute.x + borderNodeOffset < newNodePosition.x + movedNode.width &&
      parentNode.positionAbsolute.x + parentNode.width - borderNodeOffset > newNodePosition.x &&
      parentNode.positionAbsolute.y + borderNodeOffset < newNodePosition.y + movedNode.height &&
      parentNode.positionAbsolute.y + parentNode.height - borderNodeOffset > newNodePosition.y
    );
  }
  return false;
};

const isResize = (change: NodeChange): change is NodeDimensionChange =>
  change.type === 'dimensions' && typeof change.resizing === 'boolean' && change.resizing;

const computeRecursiveNodeChanges = (
  accumulatedNodeChanges: NodeChange[],
  change: NodeChange,
  nodes: Node<NodeData, string>[],
  edges: Edge<EdgeData>[]
): void => {
  if (isResize(change)) {
    const resizedNode = nodes.find((node) => node.id === change.id);
    if (resizedNode && resizedNode.type === 'listNode') {
      resizedNode.data.resizedByUser = true;
      const allVisibleNodes = nodes.filter((node) => !node.hidden);
      const diagramToLayout: RawDiagram = {
        nodes,
        edges,
      };

      const layoutEngine: ILayoutEngine = new LayoutEngine();
      const nodeChanges = layoutEngine.partialNodeLayout(
        diagramToLayout,
        allVisibleNodes,
        [resizedNode],
        change,
        change.dimensions
      );

      // console.log(nodeChanges);

      // const childrenNodeChanges: NodeChange[] = [];
      // const childNodes = nodes.filter((node) => node.parentNode === resizedNode.id);
      // childNodes.forEach((child) => {
      //   const childNodeChange: NodeDimensionChange = {
      //     ...change,
      //     id: child.id,
      //     dimensions: {
      //       width: change.dimensions?.width ? change.dimensions?.width - 2 : child.width ?? 0,
      //       height: child.height ?? 0,
      //     },
      //   };
      //   childrenNodeChanges.push(childNodeChange);
      //   computeRecursiveNodeChanges(accumulatedNodeChanges, childNodeChange, nodes, edges);
      // });
      accumulatedNodeChanges.push(...nodeChanges);
    }
  }
};

export const useBorderChange = (): UseBorderChangeValue => {
  const { getNodes, getEdges } = useReactFlow<NodeData, EdgeData>();

  const transformBorderNodeChanges = useCallback((changes: NodeChange[]): NodeChange[] => {
    const newChanges: NodeChange[] = [];

    changes.reduce<NodeChange[]>((previous: NodeChange[], change: NodeChange) => {
      computeRecursiveNodeChanges(previous, change, getNodes() as Node<NodeData, string>[], getEdges());
      if (change.type === 'position' && change.positionAbsolute) {
        const movedNode = getNodes().find((node) => change.id === node.id);
        if (movedNode?.data.isBorderNode) {
          const parentNode = getNodes().find((node) => movedNode?.parentNode === node.id);
          if (parentNode && isNewPositionInsideIsParent(change.positionAbsolute, movedNode, parentNode)) {
            //Invalid position, reset to the initial one
            change.position = movedNode.position;
            change.positionAbsolute = movedNode.positionAbsolute;
          } else {
            movedNode.data.borderNodePosition = findBorderNodePosition(change.position, movedNode, parentNode);
          }
        }
      }
      previous.push(change);
      return previous;
    }, newChanges);

    changes.map((change) => {
      return change;
    });

    return newChanges;
  }, []);

  return { transformBorderNodeChanges };
};
