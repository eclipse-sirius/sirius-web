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

import { Dimensions, Node } from 'reactflow';
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
    newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceDimensions?: Dimensions
  ) {
    nodesToLayout.forEach((node) => {
      const nodeLayoutHandler: INodeLayoutHandler<NodeData> | undefined = this.nodeLayoutHandlers.find((handler) =>
        handler.canHandle(node)
      );
      if (nodeLayoutHandler) {
        const directChildren = visibleNodes.filter((visibleNode) => visibleNode.parentNode === node.id);
        nodeLayoutHandler.handle(
          this,
          previousDiagram,
          node,
          visibleNodes,
          directChildren,
          newlyAddedNode,
          forceDimensions
        );

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
