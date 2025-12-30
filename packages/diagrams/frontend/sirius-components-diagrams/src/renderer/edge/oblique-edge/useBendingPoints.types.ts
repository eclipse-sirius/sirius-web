/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { DraggableData } from 'react-draggable';
import { XYPosition } from '@xyflow/react';

export interface UseBendingPointsValue {
  localBendingPoints: XYPosition[];
  draggingPointIndex: number | null;
  onBendingPointDragStop: (eventData: DraggableData, index: number) => void;
  onBendingPointDrag: (eventData: DraggableData, index: number, direction: 'x' | 'y') => void;
}

export interface BendingPointsState {
  localBendingPoints: XYPosition[];
  draggingPointIndex: number | null;
}
