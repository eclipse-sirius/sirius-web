/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

export interface BendPointProps {
  x: number;
  y: number;
  index: number;
  direction: 'x' | 'y';
  onDrag: (eventData: DraggableData, index: number, direction: 'x' | 'y') => void;
  onDragStop: (eventData: DraggableData, index: number) => void;
  temporary: boolean;
}

export interface TemporaryMovingLineProps {
  x: number;
  y: number;
  direction: 'x' | 'y';
  segmentLength: number;
  index: number;
  onDrag: (eventData: DraggableData, index: number, direction: 'x' | 'y') => void;
  onDragStop: (eventData: DraggableData, index: number) => void;
}
