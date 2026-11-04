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

import { XYPosition } from '@xyflow/react';
import { ReactNode } from 'react';

export interface EdgeGeometryContextValue {
  onDragStart: (edgeId: string, index: number, direction: 'x' | 'y', isSegmentDragged: boolean) => void;
  onBendingPointDrag: (edgeId: string, eventData: XYPosition, index: number, direction: 'x' | 'y') => void;
  onBendingPointDragStop: (edgeId: string, eventData: XYPosition, index: number) => void;
  onTemporaryLineDrag: (edgeId: string, eventData: XYPosition, index: number, direction: 'x' | 'y') => void;
  onTemporaryLineDragStop: (edgeId: string, eventData: XYPosition, index: number) => void;
  cancelDrag: () => void;
}

export interface EdgeGeometryContextProviderProps {
  children: ReactNode;
}
