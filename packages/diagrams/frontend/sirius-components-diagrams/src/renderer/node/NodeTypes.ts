/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { FreeFormNode } from './FreeFormNode';
import { IconLabelNode } from './IconLabelNode';
import { ListNode } from './ListNode';
import { DiagramNodeTypes } from './NodeTypes.types';

export const nodeTypes: DiagramNodeTypes = {
  freeFormNode: FreeFormNode,
  listNode: ListNode,
  iconLabelNode: IconLabelNode,
};
