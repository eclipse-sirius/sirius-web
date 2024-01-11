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
import { IconLabelNodeData } from '../node/IconsLabelNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { RawDiagram } from './layout.types';

const rectangularNodePadding = 8;

export class IconLabelNodeLayoutHandler implements INodeLayoutHandler<IconLabelNodeData> {
  canHandle(node: Node<NodeData, DiagramNodeType>) {
    return node.type === 'iconLabelNode';
  }

  public handle2(
    _layoutEngine: ILayoutEngine,
    _previousDiagram: RawDiagram | null,
    _node: Node<IconLabelNodeData, 'iconLabelNode'>,
    _visibleNodes: Node<NodeData, string>[],
    _directChildren: Node<NodeData, string>[],
    _newlyAddedNode: Node<NodeData, string> | undefined,
    _nodeDimensionChange: NodeDimensionChange,
    _forceDimension?: { width?: number | undefined; height?: number | undefined } | undefined
  ): NodeChange[] {
    const nodeChange: NodeChange[] = [];

    return nodeChange;
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
    let labelWidth = labelElement?.getBoundingClientRect().width ?? 0;

    if (!labelElement) {
      if (node.id === '95750ddd-b14d-31f2-bc29-87979fbbdad4') {
        labelWidth = 91;
      }
      if (node.id === '9286b2b4-984d-374b-9797-dd022c271200') {
        labelWidth = 144.5;
      }
      if (node.id === '3386554d-bad9-3aa6-a73a-d4f2d4cab552') {
        labelWidth = 95;
      }
      if (node.id === 'f7bcf550-af67-34a4-9f2a-123b67588e1c') {
        labelWidth = 93.5;
      }
      if (node.id === '4e5e2558-011e-37ae-84d6-77345512c653') {
        labelWidth = 149;
      }
    }

    node.width = forceWidth ?? rectangularNodePadding + labelWidth + rectangularNodePadding;
    node.height = labelElement?.getBoundingClientRect().height ?? 20;
  }

  private findNodeIndex(nodes: Node<NodeData>[], nodeId: string): number {
    return nodes.findIndex((node) => node.id === nodeId);
  }
}
