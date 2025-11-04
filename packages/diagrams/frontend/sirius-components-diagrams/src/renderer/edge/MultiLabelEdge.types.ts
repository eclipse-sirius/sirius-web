/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { Edge, EdgeProps } from '@xyflow/react';
import { EdgeData, EdgeLabel } from '../DiagramRenderer.types';

export type MultiLabelEdgeProps<T extends Edge<Record<string, unknown>, string | undefined>> = {
  edgeCenterX?: number;
  edgeCenterY?: number;
  svgPathString: string;
} & EdgeProps<T>;

export type RectilinearTurnPreference = 'target' | 'source' | 'middle';

export interface MultiLabelEdgeData extends EdgeData {
  beginLabel?: EdgeLabel;
  endLabel?: EdgeLabel;
  /**
   * Controls where the first rectilinear turn should occur when no custom bending points were provided.
   * - target: turn closer to the target handle (default).
   * - source: turn closer to the source handle.
   * - middle: turn halfway between source and target.
   */
  rectilinearTurnPreference?: RectilinearTurnPreference;
  /**
   * Minimum length (in diagram units/pixels) of the initial segment that travels outward from the source handle
   * before the first turn is allowed. Defaults to 4 when unspecified.
   */
  rectilinearMinOutwardLength?: number;
  /**
   * Enables the automatic fan-in spacing that spreads parallel edges when they arrive on the same node side.
   * Defaults to true.
   */
  rectilinearFanInEnabled?: boolean;
  /**
   * Enables the automatic fan-out spacing that spreads parallel edges when they leave the same node side.
   * Defaults to true.
   */
  rectilinearFanOutEnabled?: boolean;
  /**
   * Enables the post-processing pass that separates overlapping rectilinear segments by offsetting them
   * by a few pixels. Defaults to true.
   */
  rectilinearParallelSpacingEnabled?: boolean;
}
