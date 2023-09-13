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
import { ListNodeData } from '../node/ListNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';

const defaultWidth = 150;
const defaultHeight = 70;

export class ListNodeLayoutHandler implements INodeLayoutHandler<ListNodeData> {
  public canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'listNode';
  }

  public handle(
    _layoutEngine: ILayoutEngine,
    _previousDiagram: Diagram | null,
    node: Node<ListNodeData, 'listNode'>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    _directChildren: Node<NodeData, DiagramNodeType>[]
  ) {
    const nodeList = document.getElementById(`${node.id}-${this.findNodeIndex(visibleNodes, node.id)}`)?.children[0];

    node.width = this.getNodeOrMinWidth(nodeList?.getBoundingClientRect().width);
    node.height = this.getNodeOrMinHeight(nodeList?.getBoundingClientRect().height);
  }

  private findNodeIndex(nodes: Node<NodeData>[], nodeId: string): number {
    return nodes.findIndex((node) => node.id === nodeId);
  }

  private getNodeOrMinWidth(nodeWidth: number | undefined): number {
    return Math.max(nodeWidth ?? -Infinity, defaultWidth);
  }

  private getNodeOrMinHeight(nodeHeight: number | undefined): number {
    return Math.max(nodeHeight ?? -Infinity, defaultHeight);
  }
}
