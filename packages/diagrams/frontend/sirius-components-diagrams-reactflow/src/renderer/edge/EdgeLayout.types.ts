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

import { Node, Position } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';

export type GetEdgeParameters = (source: Node<NodeData>, target: Node<NodeData>) => EdgeParameters;

export interface EdgeParameters {
  sourceX: number;
  sourceY: number;
  sourcePosition: Position;
  targetX: number;
  targetY: number;
  targetPosition: Position;
}

export type GetParameters = (nodeA: Node<NodeData>, nodeB: Node<NodeData>) => Parameters;

export interface Parameters {
  x: number;
  y: number;
  position: Position;
}

export type GetNodeCenter = (node: Node<NodeData>) => NodeCenter;

export interface NodeCenter {
  x: number;
  y: number;
}

export type GetHandleCoordinatesByPosition = (node: Node<NodeData>, handlePosition: Position) => HandleCoordinates;

export interface HandleCoordinates {
  x: number;
  y: number;
}
