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
import { EdgeProps } from 'reactflow';
import { EdgeData, EdgeLabel } from '../DiagramRenderer.types';

export type MultiLabelEdgeProps<T = any> = {
  edgeCenterX: number;
  edgeCenterY: number;
  svgPathString: string;
} & EdgeProps<T>;

export interface MultiLabelEdgeData extends EdgeData {
  beginLabel?: EdgeLabel;
  endLabel?: EdgeLabel;
}
