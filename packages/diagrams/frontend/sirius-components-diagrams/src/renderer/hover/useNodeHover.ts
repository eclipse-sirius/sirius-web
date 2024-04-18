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

import { useCallback } from 'react';
import { Node, NodeMouseHandler, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseNodeHoverValue } from './useNodeHover.types';

export const useNodeHover = (): UseNodeHoverValue => {
  const { setNodes } = useReactFlow<NodeData, EdgeData>();

  const onNodeMouseEnter: NodeMouseHandler = useCallback(
    (_: React.MouseEvent<Element, MouseEvent>, node: Node<NodeData>) => {
      setNodes((nds) =>
        nds.map((n) => {
          if (n.id === node.id) {
            if (!n.data.isHovered) {
              n.data = {
                ...n.data,
                isHovered: true,
              };
            }
          }
          return n;
        })
      );
    },
    [setNodes]
  );

  const onNodeMouseLeave: NodeMouseHandler = useCallback(() => {
    setNodes((nds) =>
      nds.map((n) => {
        if (n.data.isHovered) {
          n.data = {
            ...n.data,
            isHovered: false,
          };
        }
        return n;
      })
    );
  }, [setNodes]);

  return {
    onNodeMouseEnter,
    onNodeMouseLeave,
  };
};
