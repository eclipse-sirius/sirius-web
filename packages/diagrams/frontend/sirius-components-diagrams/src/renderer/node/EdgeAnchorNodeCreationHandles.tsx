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
import { Node, NodeProps } from '@xyflow/react';
import { memo } from 'react';
import { ConnectionCreationHandles } from '../handles/ConnectionCreationHandles';
import {
  EDGE_ANCHOR_NODE_DEFAULT_SIZE,
  EdgeAnchorNodeCreationHandlesData,
} from './EdgeAnchorNodeCreationHandles.types';
import { NodeComponentsMap } from './NodeTypes';

export const EdgeAnchorNodeCreationHandles: NodeComponentsMap['edgeAnchorNodeCreationHandles'] = memo(
  ({ id, data }: NodeProps<Node<EdgeAnchorNodeCreationHandlesData>>) => {
    return (
      <div
        style={{
          width: EDGE_ANCHOR_NODE_DEFAULT_SIZE,
          height: EDGE_ANCHOR_NODE_DEFAULT_SIZE,
        }}>
        <ConnectionCreationHandles nodeId={id} diagramElementId={data.edgeId}></ConnectionCreationHandles>
      </div>
    );
  }
);
