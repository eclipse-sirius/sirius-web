/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { Node } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';
import { IconLabelNodeData } from '../node/IconLabelNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ILayoutEngine, INodeLayoutHandler } from './LayoutEngine.types';
import { ForcedDimensions, RawDiagram } from './layout.types';
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
    _visibleNodes: Node<NodeData, DiagramNodeType>[],
    _directChildren: Node<NodeData, DiagramNodeType>[],
    _newlyAddedNodes: Node<NodeData, DiagramNodeType>[],
    forceDimensions?: ForcedDimensions
  ) {
    const insideLabelWidthConstraint = getInsideLabelWidthConstraint(node.data.insideLabel);

    node.width = forceDimensions?.width ?? insideLabelWidthConstraint + rectangularNodePadding * 2;
    node.height = node.data.insideLabel?.height;

    node.data.minComputedWidth = insideLabelWidthConstraint + rectangularNodePadding * 2;
    node.data.minComputedHeight = node.height ?? 0;
  }
}
