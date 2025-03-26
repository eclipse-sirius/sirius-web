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

import { Edge, EdgeMouseHandler } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { EdgeData } from '../DiagramRenderer.types';
import { UseEdgeHoverValue } from './useEdgeHover.types';

export const useEdgeHover = (): UseEdgeHoverValue => {
  const { setEdges } = useStore();

  const onEdgeMouseEnter: EdgeMouseHandler<Edge<EdgeData>> = useCallback(
    (_: React.MouseEvent<Element, MouseEvent>, edge: Edge<EdgeData>) => {
      setEdges((prevEdges) =>
        prevEdges.map((prevEdge) => {
          if (edge.id === prevEdge.id && prevEdge.data && !prevEdge.data.isHovered) {
            return {
              ...prevEdge,
              data: {
                ...prevEdge.data,
                isHovered: true,
              },
            };
          }
          return prevEdge;
        })
      );
    },
    [setEdges]
  );

  const onEdgeMouseLeave: EdgeMouseHandler = useCallback(() => {
    setEdges((prevEdges) =>
      prevEdges.map((prevEdge) => {
        if (prevEdge.data && prevEdge.data.isHovered) {
          return {
            ...prevEdge,
            data: {
              ...prevEdge.data,
              isHovered: false,
            },
          };
        }
        return prevEdge;
      })
    );
  }, [setEdges]);

  return {
    onEdgeMouseEnter,
    onEdgeMouseLeave,
  };
};
