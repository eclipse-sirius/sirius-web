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

import { Node, NodeMouseHandler } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { NodeData } from '../DiagramRenderer.types';
import { NodeContext } from '../node/NodeContext';
import { NodeContextValue } from '../node/NodeContext.types';
import { UseNodeHoverValue } from './useNodeHover.types';

export const useNodeHover = (): UseNodeHoverValue => {
  const { setHoveredNode } = useContext<NodeContextValue>(NodeContext);

  const onNodeMouseEnter: NodeMouseHandler<Node<NodeData>> = useCallback(
    (_: React.MouseEvent<Element, MouseEvent>, node: Node<NodeData>) => {
      setHoveredNode(node);
    },
    []
  );

  const onNodeMouseLeave: NodeMouseHandler<Node<NodeData>> = useCallback(() => setHoveredNode(null), []);
  return {
    onNodeMouseEnter,
    onNodeMouseLeave,
  };
};
