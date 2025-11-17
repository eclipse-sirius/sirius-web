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

import { XYPosition, Edge } from '@xyflow/react';
import { EdgeCrossingGap, MultiLabelEdgeData } from '../MultiLabelEdge.types';

export type ParsedSegment = {
  start: XYPosition;
  end: XYPosition;
  startLength: number;
  endLength: number;
  length: number;
};

export type ParsedEdge = {
  edge: Edge<MultiLabelEdgeData>;
  segments: ParsedSegment[];
  totalLength: number;
};

export type NodeBounds = {
  x: number;
  y: number;
  width: number;
  height: number;
};

/**
 * A single window expressed both as normalized ratios (used by the renderer) and raw coordinates
 * at which the intersection occurred (useful for debugging overlays).
 */
export interface EdgeCrossingWindow extends EdgeCrossingGap {
  /**
   * Absolute XY coordinates of the intersection. Consumers read this when they need
   * to draw troubleshoot overlays or log the exact crossing location.
   */
  intersection: XYPosition;
}

/**
 * Map keyed by edge id listing every crossing window the detector emitted for that edge.
 */
export type EdgeCrossingMap = Map<string, EdgeCrossingWindow[]>;
