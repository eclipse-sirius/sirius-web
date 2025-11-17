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

import { XYPosition } from '@xyflow/react';
import { EdgeCrossingGap } from '../MultiLabelEdge.types';

/**
 * A single window expressed both as normalized ratios (used by the renderer) and raw coordinates
 * at which the intersection occurred (useful for debugging overlays).
 */
export interface EdgeCrossingWindow extends EdgeCrossingGap {
  /**
   * Absolute XY coordinates of the intersection. Consumers read this when they need
   * to draw debugger overlays or log the exact crossing location.
   */
  intersection: XYPosition;
}

/**
 * Map keyed by edge id listing every crossing window the detector emitted for that edge.
 */
export type EdgeCrossingMap = Map<string, EdgeCrossingWindow[]>;
