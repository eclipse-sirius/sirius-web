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
import { Node } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
import { IconLabelNodeData } from '../node/IconsLabelNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { RawDiagram } from './layout.types';
import { getInsideLabelWidthConstraint } from './layoutNode';

const rectangularNodePadding = 8;

export class IconLabelNodeLayoutHandler implements INodeLayoutHandler<IconLabelNodeData> {
  canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'iconLabelNode';
  }

  handle(
    _layoutEngine: ILayoutEngine,
    _previousDiagram: RawDiagram | null,
    node: Node<IconLabelNodeData>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    _directChildren: Node<NodeData, DiagramNodeType>[],
    _newlyAddedNode: Node<NodeData, DiagramNodeType> | undefined,
    forceWidth?: number
  ) {
    const nodeIndex = this.findNodeIndex(visibleNodes, node.id);
    const labelElement = document.getElementById(`${node.id}-label-${nodeIndex}`);

    node.width =
      forceWidth ??
      rectangularNodePadding +
        getInsideLabelWidthConstraint(node.data.insideLabel, labelElement) +
        rectangularNodePadding;
    node.height = labelElement?.getBoundingClientRect().height;
  }

  private findNodeIndex(nodes: Node<NodeData>[], nodeId: string): number {
    return nodes.findIndex((node) => node.id === nodeId);
  }
}
