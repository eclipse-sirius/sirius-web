/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { UseDistributeElementsValue } from './useDistributeElements.types';
import { useProcessLayoutTool } from './useProcessLayoutTool';

export const useDistributeElements = (): UseDistributeElementsValue => {
  const { getNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { processLayoutTool } = useProcessLayoutTool();

  const distributeAlign = (orientation: 'left' | 'right' | 'top' | 'bottom' | 'center' | 'middle') => {
    return useCallback((selectedNodeIds: string[], refElementId: string | null) => {
      processLayoutTool(
        selectedNodeIds,
        (_selectedNodes, refNode) => {
          return getNodes().map((node) => {
            if (!selectedNodeIds.includes(node.id) || node.data.pinned) {
              return node;
            }
            const referencePositionValue: number = (() => {
              switch (orientation) {
                case 'left':
                  return refNode.position.x;
                case 'right':
                  return refNode.position.x + (refNode.width ?? 0) - (node.width ?? 0);
                case 'center':
                  return refNode.position.x + (refNode.width ?? 0) / 2 - (node.width ?? 0) / 2;
                case 'top':
                  return refNode.position.y;
                case 'bottom':
                  return refNode.position.y + (refNode.height ?? 0) - (node.height ?? 0);
                case 'middle':
                  return refNode.position.y + (refNode.height ?? 0) / 2 - (node.height ?? 0) / 2;
              }
            })();

            const referencePositionVariable: string = (() => {
              switch (orientation) {
                case 'left':
                case 'right':
                case 'center':
                  return 'x';
                case 'top':
                case 'bottom':
                case 'middle':
                  return 'y';
              }
            })();

            return {
              ...node,
              position: {
                ...node.position,
                [referencePositionVariable]: referencePositionValue,
              },
            };
          });
        },
        null,
        refElementId
      );
    }, []);
  };

  const distributeAlignLeft = distributeAlign('left');
  const distributeAlignRight = distributeAlign('right');
  const distributeAlignCenter = distributeAlign('center');
  const distributeAlignTop = distributeAlign('top');
  const distributeAlignBottom = distributeAlign('bottom');
  const distributeAlignMiddle = distributeAlign('middle');

  return {
    distributeAlignLeft,
    distributeAlignRight,
    distributeAlignCenter,
    distributeAlignTop,
    distributeAlignBottom,
    distributeAlignMiddle,
  };
};
