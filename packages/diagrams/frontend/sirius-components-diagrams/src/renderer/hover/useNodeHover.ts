/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { useStore } from '../../representation/useStore';
import { NodeData } from '../DiagramRenderer.types';
import { DropNodeContext } from '../dropNode/DropNodeContext';
import { DropNodeContextValue } from '../dropNode/DropNodeContext.types';
import { UseNodeHoverValue } from './useNodeHover.types';

export const useNodeHover = (): UseNodeHoverValue => {
  const { setNodes } = useStore();
  const { dragging } = useContext<DropNodeContextValue>(DropNodeContext);

  const onNodeMouseEnter: NodeMouseHandler<Node<NodeData>> = useCallback(
    (_: React.MouseEvent<Element, MouseEvent>, node: Node<NodeData>) => {
      if (!dragging) {
        setNodes((nds) =>
          nds.map((n) => {
            if (n.id === node.id) {
              if (!n.data.isHovered) {
                return {
                  ...n,
                  data: {
                    ...n.data,
                    isHovered: true,
                  },
                };
              }
            }
            return n;
          })
        );
      }
    },
    [setNodes, dragging]
  );

  const onNodeMouseLeave: NodeMouseHandler = useCallback(() => {
    if (!dragging) {
      setNodes((nds) =>
        nds.map((n) => {
          if (n.data.isHovered) {
            return {
              ...n,
              data: {
                ...n.data,
                isHovered: false,
                connectionLinePositionOnNode: 'none',
              },
            };
          }
          return n;
        })
      );
    }
  }, [setNodes, dragging]);

  return {
    onNodeMouseEnter,
    onNodeMouseLeave,
  };
};
