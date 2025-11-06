/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { Dimensions, Node } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { FreeFormNodeLayoutHandler } from './FreeFormNodeLayoutHandler';
import { IconLabelNodeLayoutHandler } from './IconLabelNodeLayoutHandler';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { ListNodeLayoutHandler } from './ListNodeLayoutHandler';
import { RawDiagram } from './layout.types';

export class LayoutEngine implements ILayoutEngine {
  nodeLayoutHandlers: INodeLayoutHandler<NodeData>[] = [
    new FreeFormNodeLayoutHandler(),
    new ListNodeLayoutHandler(),
    new IconLabelNodeLayoutHandler(),
  ];

  public layoutNodes(
    previousDiagram: RawDiagram | null,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    nodesToLayout: Node<NodeData, DiagramNodeType>[],
    newlyAddedNodes: Node<NodeData, DiagramNodeType>[],
    forceDimensions?: Dimensions
  ) {
    nodesToLayout.forEach((node) => {
      const nodeLayoutHandler: INodeLayoutHandler<NodeData> | undefined = this.nodeLayoutHandlers.find((handler) =>
        handler.canHandle(node)
      );
      if (nodeLayoutHandler) {
        const directChildren = visibleNodes.filter((visibleNode) => visibleNode.parentId === node.id);
        nodeLayoutHandler.handle(
          this,
          previousDiagram,
          node,
          visibleNodes,
          directChildren,
          newlyAddedNodes,
          forceDimensions
        );

        node.style = {
          ...node.style,
          width: `${node.width}px`,
          height: `${node.height}px`,
        };
      }
      this.layoutOutsideLabels(previousDiagram, node);
    });
  }

  public registerNodeLayoutHandlerContribution(nodeLayoutHandlerContribution: INodeLayoutHandler<NodeData>) {
    this.nodeLayoutHandlers.push(nodeLayoutHandlerContribution);
  }

  private layoutOutsideLabels = (previousDiagram: RawDiagram | null, node: Node<NodeData>) => {
    const previousNode = (previousDiagram?.nodes ?? []).find((prevNode) => prevNode.id === node.id);
    if (node.data.outsideLabels.BOTTOM_MIDDLE) {
      const outsideLabel = node.data.outsideLabels.BOTTOM_MIDDLE;
      const labelElement = document.getElementById(`${outsideLabel.id}-label`);
      const labelHeight = labelElement?.getBoundingClientRect().height ?? 0;
      const labelWidth = labelElement?.getBoundingClientRect().width ?? 0;
      if (!outsideLabel.resizedByUser) {
        outsideLabel.width = labelWidth;
        outsideLabel.height = labelHeight;
      } else if (previousNode) {
        outsideLabel.width = previousNode.data.outsideLabels.BOTTOM_MIDDLE?.width ?? labelWidth;
        outsideLabel.height = previousNode.data.outsideLabels.BOTTOM_MIDDLE?.height ?? labelHeight;
      }
    }
  };
}
