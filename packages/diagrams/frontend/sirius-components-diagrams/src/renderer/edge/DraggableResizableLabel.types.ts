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
import React from 'react';
import { DraggableData } from 'react-draggable';
import { EdgeLabel } from '../DiagramRenderer.types';

export interface DraggableResizableLabelProps {
  id: string;
  label: EdgeLabel;
  position: { x: number; y: number };
  positionOffset?: { x: number; y: number };
  onDragStop: (e: any, eventData: DraggableData) => void;
  onResizeStop: (e: React.SyntheticEvent, data: { size: { width: number; height: number } }) => void;
  zoom: number;
  readOnly: boolean;
  selected: boolean;
  faded: boolean;
  resizeHandlePosition: 'se' | 'ne' | 'nw';
  transform?: string;
}

export type LabelSize = {
  width: number;
  height: number;
};
