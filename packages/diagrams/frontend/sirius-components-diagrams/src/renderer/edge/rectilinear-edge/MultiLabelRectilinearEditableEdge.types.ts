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
import { Edge, EdgeProps } from '@xyflow/react';
import { EdgeGeometry } from '../geometry/edgeGeometry.types';

export type MultiLabelEditableEdgeProps<T extends Edge<Record<string, unknown>, string | undefined>> = EdgeProps<T> & {
  geometry: EdgeGeometry;
};
