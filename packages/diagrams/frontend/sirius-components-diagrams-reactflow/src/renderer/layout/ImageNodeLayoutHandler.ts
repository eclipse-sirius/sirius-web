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
import { Diagram, NodeData } from '../DiagramRenderer.types';
import { ImageNodeData } from '../node/ImageNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';

const defaultWidth = 150;
const defaultHeight = 70;

export class ImageNodeLayoutHandler implements INodeLayoutHandler<ImageNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'imageNode';
  }

  public handle(
    _layoutEngine: ILayoutEngine,
    previousDiagram: Diagram | null,
    node: Node<ImageNodeData, 'imageNode'>,
    _visibleNodes: Node<NodeData, DiagramNodeType>[],
    _directChildren: Node<NodeData, DiagramNodeType>[]
  ) {
    node.width = defaultWidth;
    node.height = defaultHeight;

    const previousNode = (previousDiagram?.nodes ?? []).find((previousNode) => previousNode.id === node.id);
    if (previousNode && previousNode.width && previousNode.height) {
      node.width = previousNode.width;
      node.height = previousNode.height;
    }
  }
}
