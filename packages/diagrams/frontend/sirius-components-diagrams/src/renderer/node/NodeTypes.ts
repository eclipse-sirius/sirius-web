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

import { Node, NodeProps } from '@xyflow/react';
import { FC } from 'react';
import { FreeFormNode } from './FreeFormNode';
import { FreeFormNodeData } from './FreeFormNode.types';
import { IconLabelNode } from './IconLabelNode';
import { IconLabelNodeData } from './IconsLabelNode.types';
import { ListNode } from './ListNode';
import { ListNodeData } from './ListNode.types';

export const nodeTypes: NodeComponentsMap = {
  freeFormNode: FreeFormNode,
  iconLabelNode: IconLabelNode,
  listNode: ListNode,
};

export interface NodeDataMap {
  freeFormNode: FreeFormNodeData;
  iconLabelNode: IconLabelNodeData;
  listNode: ListNodeData;
}
export type NodeComponentsMap = {
  [K in keyof NodeDataMap]: FC<NodeProps<Node<NodeDataMap[K], K>>>;
};

export type NodePropsMap = {
  [K in keyof NodeDataMap]: NodeProps<Node<NodeDataMap[K], K>>;
};
