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

import { Dimensions, Node, Position, XYPosition } from '@xyflow/react';
import { NodeHandle } from '@xyflow/system';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ForcedDimensions, LabelSize, RawDiagram } from './layout.types';

export interface ILayoutEngine {
  /**
   * Measures the inside label within the available content width, or returns null when no measurement is available.
   *
   * @since v2026.12.0
   */
  measureInsideLabel(node: Node<NodeData>, contentWidth: number): LabelSize | null;

  registerNodeLayoutHandlerContribution(nodeLayoutHandlerContribution: INodeLayoutHandler<NodeData>);

  layoutNodes(
    previousDiagram: RawDiagram | null,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    nodesToLayout: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType>[],
    forceDimensions?: ForcedDimensions
  );
}

export interface INodeLayoutHandler<T extends NodeData> {
  canHandle(node: Node<NodeData, DiagramNodeType>);

  handle(
    layoutEngine: ILayoutEngine,
    previousDiagram: RawDiagram | null,
    node: Node<T>,
    visibleNodes: Node<NodeData, DiagramNodeType>[],
    directChildren: Node<NodeData, DiagramNodeType>[],
    newlyAddedNode: Node<NodeData, DiagramNodeType>[],
    forceDimensions?: ForcedDimensions
  );

  calculateCustomNodeEdgeHandlePosition?(
    node: Node<NodeData>,
    handlePosition: Position,
    handle: NodeHandle
  ): XYPosition;

  calculateCustomNodeBorderNodePosition?(
    node: Node<NodeData>,
    borderNode: XYPosition & Dimensions,
    isDragging: boolean
  ): XYPosition;
}
