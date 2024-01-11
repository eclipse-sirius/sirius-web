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

import { Node, NodeChange, NodeDimensionChange } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { IconLabelNodeLayoutHandler } from './IconLabelNodeLayoutHandler';
import { ImageNodeLayoutHandler } from './ImageNodeLayoutHandler';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { ListNodeLayoutHandler } from './ListNodeLayoutHandler';
import { RectangleNodeLayoutHandler } from './RectangleNodeLayoutHandler';
import { RawDiagram } from './layout.types';

export class LayoutEngine implements ILayoutEngine {
  nodeLayoutHandlers: INodeLayoutHandler<NodeData>[] = [
    new RectangleNodeLayoutHandler(),
    new ListNodeLayoutHandler(),
    new ImageNodeLayoutHandler(),
    new IconLabelNodeLayoutHandler(),
  ];

  partialNodeLayout(
    previousDiagram: RawDiagram | null,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    nodesToLayout: Node<NodeData, DiagramNodeType>[],
    nodeDimensionChange: NodeDimensionChange,
    forceDimension?: { width?: number; height?: number }
  ): NodeChange[] {
    const newChanges: NodeChange[] = [];
    nodesToLayout.forEach((node) => {
      const nodeLayoutHandler: INodeLayoutHandler<NodeData> | undefined = this.nodeLayoutHandlers.find((handler) =>
        handler.canHandle(node)
      );
      const directChildren = visibleNodes
        .filter((visibleNode) => visibleNode.parentNode === node.id)
        .map((child) => ({
          ...child,
          data: {
            ...child.data,
          },
        })); // Create a deep copy of each children
      if (nodeLayoutHandler) {
        const childrenNodeChange = nodeLayoutHandler?.handle2(
          this,
          previousDiagram,
          node,
          visibleNodes,
          directChildren,
          undefined,
          nodeDimensionChange,
          forceDimension
        );
        newChanges.push(...childrenNodeChange);
      }
    });

    return newChanges;
  }

  public layoutNodes(
    previousDiagram: RawDiagram | null,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    nodesToLayout: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceWidth?: number
  ) {
    nodesToLayout.forEach((node) => {
      const nodeLayoutHandler: INodeLayoutHandler<NodeData> | undefined = this.nodeLayoutHandlers.find((handler) =>
        handler.canHandle(node)
      );
      if (nodeLayoutHandler) {
        const directChildren = visibleNodes.filter((visibleNode) => visibleNode.parentNode === node.id);
        nodeLayoutHandler.handle(this, previousDiagram, node, visibleNodes, directChildren, newlyAddedNode, forceWidth);

        node.style = {
          ...node.style,
          width: `${node.width}px`,
          height: `${node.height}px`,
        };
      }
    });
  }

  public registerNodeLayoutHandlerContribution(nodeLayoutHandlerContribution: INodeLayoutHandler<NodeData>) {
    this.nodeLayoutHandlers.push(nodeLayoutHandlerContribution);
  }
}
