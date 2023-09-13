/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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

import { ImageNode } from './ImageNode';
import { ListNode } from './ListNode';
import { DiagramNodeTypes } from './NodeTypes.types';
import { RectangularNode } from './RectangularNode';

export const nodeTypes: DiagramNodeTypes = {
  rectangularNode: RectangularNode,
  imageNode: ImageNode,
  listNode: ListNode,
};
