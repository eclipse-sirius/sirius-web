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
import { Edge, EdgeChange } from '@xyflow/react';
import { EdgeData } from '../DiagramRenderer.types';

export interface UseSelectEdgeChangeValue {
  onEdgeSelectedChange: (changes: EdgeChange<Edge<EdgeData>>[]) => void;
}
