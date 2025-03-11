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

export type LocalBendingPointsSetter = (
  localBendingPoints: BendPointData[] | ((prevState: BendPointData[]) => BendPointData[])
) => void;

export interface UseBendingPointsValue {
  localBendingPoints: BendPointData[];
  setLocalBendingPoints: LocalBendingPointsSetter;
  onBendingPointDragStop: (eventData: DraggableData, index: number) => void;
  onBendingPointDrag: (eventData: DraggableData, index: number) => void;
}

export type BendPointData = {
  x: number;
  y: number;
  pathOrder: number;
};
