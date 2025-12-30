/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { Position, XYPosition } from '@xyflow/react';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';

export type DraggableEdgeLabelsProps = {
  id: string;
  data: MultiLabelEdgeData;
  selected: boolean;
  sourcePosition: Position;
  targetPosition: Position;
  sourceX: number;
  sourceY: number;
  targetX: number;
  targetY: number;
  edgeCenter: XYPosition;
};
