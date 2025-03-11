/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

export interface UseTemporaryLinesValue {
  middleBendingPoints: MiddlePoint[];
  onTemporaryLineDragStop: (eventData: DraggableData, index: number) => void;
  onTemporaryLineDrag: (eventData: DraggableData, index: number, direction: 'x' | 'y') => void;
}

export type MiddlePoint = {
  x: number;
  y: number;
  direction: 'x' | 'y';
  segmentLength: number;
};
