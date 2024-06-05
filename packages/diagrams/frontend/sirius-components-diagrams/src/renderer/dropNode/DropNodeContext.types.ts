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

import { XYPosition } from 'reactflow';

export type DropNodeContextValue = DropNodeContextState & {
  initializeDrop: (newDropData: DropNodeContextState) => void;
  resetDrop: () => void;
};

export interface DropNodeContextState {
  initialPosition: XYPosition | null;
  initialPositionAbsolute: XYPosition | null;
  droppableOnDiagram: boolean;
  draggedNodeId: string;
}

export interface DropNodeContextProviderProps {
  children: React.ReactNode;
}
