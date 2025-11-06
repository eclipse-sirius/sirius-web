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
export interface UseLabelResizeValue {
  onLabelResizeStop: (nodeId: string, size: { width: number; height: number }) => void;
  onEdgeLabelResizeStop: (
    edgeId: string,
    size: { width: number; height: number },
    labelPosition: 'begin' | 'center' | 'end'
  ) => void;
}
