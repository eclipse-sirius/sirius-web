/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Node } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';
import { ILayoutEngine } from './LayoutEngine.types';

export const measureInsideLabel = (layoutEngine: ILayoutEngine, node: Node<NodeData>, contentWidth: number): void => {
  const labelSize = layoutEngine.measureInsideLabel(node, contentWidth);
  if (labelSize && node.data.insideLabel) {
    node.data.insideLabel.width = labelSize.width;
    node.data.insideLabel.height = labelSize.height;
  }
};
