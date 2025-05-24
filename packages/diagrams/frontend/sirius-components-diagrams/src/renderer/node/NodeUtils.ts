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
import { Node } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';

export const getNodeLabel = (node: Node<NodeData>): string => {
  let label = '';
  if (node.data.insideLabel) {
    label = node.data.insideLabel.text;
  } else if (node.data.outsideLabels) {
    if (node.data.outsideLabels.BOTTOM_BEGIN) {
      label = node.data.outsideLabels.BOTTOM_BEGIN.text;
    } else if (node.data.outsideLabels.BOTTOM_END) {
      label = node.data.outsideLabels.BOTTOM_END.text;
    } else if (node.data.outsideLabels.BOTTOM_MIDDLE) {
      label = node.data.outsideLabels.BOTTOM_MIDDLE.text;
    }
  }
  return label;
};
