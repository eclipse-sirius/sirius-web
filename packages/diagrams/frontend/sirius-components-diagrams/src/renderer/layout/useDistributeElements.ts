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
import { useCallback, useContext } from 'react';
import { Node, XYPosition, useReactFlow } from 'reactflow';
import { UseDistributeElementsValue } from './useDistributeElements.types';
import { NodeData, EdgeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { useSynchronizeLayoutData } from './useSynchronizeLayoutData';
import { RawDiagram } from './layout.types';
import { useLayout } from './useLayout';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramContext } from '../../contexts/DiagramContext';
import { useOverlap } from '../overlap/useOverlap';

function getComparePositionFn(direction: 'horizontal' | 'vertical') {
  return (node1: Node, node2: Node) => {
    const positionNode1: XYPosition = node1.position;
    const positionNode2: XYPosition = node2.position;
    if (positionNode1 && positionNode2) {
      return direction === 'horizontal' ? positionNode1.x - positionNode2.x : positionNode1.y - positionNode2.y;
    }
    return 0;
  };
}

const arrangeGapBetweenElements: number = 32;
export const useDistributeElements = (): UseDistributeElementsValue => {
  const { getNodes, getEdges, setNodes } = useReactFlow<NodeData, EdgeData>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { addMessages } = useMultiToast();
  const { refreshEventPayloadId } = useContext<DiagramContextValue>(DiagramContext);
  const { resolveNodeOverlap } = useOverlap();

  const processLayoutTool = (
    selectedNodeIds: string[],
    layoutFn: (selectedNodes: Node<NodeData>[], refNode: Node) => Node<NodeData>[],
    sortFn: ((node1: Node, node2: Node) => number) | null = null,
    refElementId: string | null = null,
    direction: 'horizontal' | 'vertical' = 'horizontal'
  ): void => {
    const selectedNodes: Node<NodeData>[] = getNodes().filter((node) => selectedNodeIds.includes(node.id));
    const firstParent = selectedNodes[0]?.parentNode;
    const sameParent: boolean = selectedNodes.reduce(
      (isSameParent, node) => isSameParent && node.parentNode === firstParent,
      true
    );
    if (selectedNodes.length < 2) {
      return;
    }
    if (!sameParent) {
      addMessages([{ body: 'This tool can only be applied on elements on the same level', level: 'WARNING' }]);
      return;
    }
    if (sortFn) {
      selectedNodes.sort(sortFn);
    }

    let refNode: Node<NodeData> | undefined = selectedNodes[0];
    if (refElementId) {
      refNode = selectedNodes.find((node) => node.id === refElementId);
    }
    if (refNode) {
      const updatedNodes: Node<NodeData>[] = layoutFn(selectedNodes, refNode);
      const overlapFreeNodes: Node[] = resolveNodeOverlap(updatedNodes, direction);
      const diagramToLayout: RawDiagram = {
        nodes: [...overlapFreeNodes] as Node<NodeData, DiagramNodeType>[],
        edges: getEdges(),
      };
      layout(diagramToLayout, diagramToLayout, null, (laidOutDiagram) => {
        const overlapFreeNodesAfterLayout: Node[] = resolveNodeOverlap(laidOutDiagram.nodes, 'horizontal');
        setNodes(overlapFreeNodesAfterLayout);
        const finalDiagram: RawDiagram = {
          nodes: overlapFreeNodesAfterLayout as Node<NodeData, DiagramNodeType>[],
          edges: laidOutDiagram.edges,
        };
        synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
      });
    }
  };

  const distributeNodesOnGap = (direction: 'horizontal' | 'vertical') => {
    return useCallback((selectedNodeIds: string[]) => {
      const selectedNodes: Node<NodeData>[] = getNodes().filter((node) => selectedNodeIds.includes(node.id));
      const firstParent = selectedNodes[0]?.parentNode;
      const sameParent: boolean = selectedNodes.reduce(
        (isSameParent, node) => isSameParent && node.parentNode === firstParent,
        true
      );
      if (selectedNodes.length < 3 || !sameParent) {
        return;
      }

      selectedNodes.sort(getComparePositionFn(direction));

      const firstNode = selectedNodes[0];
      const lastNode = selectedNodes[selectedNodes.length - 1];

      if (firstNode && lastNode) {
        const totalSize: number = selectedNodes
          .filter((node) => node.id !== firstNode.id && node.id !== lastNode.id)
          .reduce((total, node) => total + (direction === 'horizontal' ? node.width ?? 0 : node.height ?? 0), 0);
        const numberOfGap: number = selectedNodes.length - 1;
        const gap: number =
          ((direction === 'horizontal'
            ? lastNode.position.x - firstNode.position.x - (firstNode.width ?? 0)
            : lastNode.position.y - firstNode.position.y - (firstNode.height ?? 0)) -
            totalSize) /
          numberOfGap;
        const updatedNodes = getNodes().map((node) => {
          if (!selectedNodeIds.includes(node.id) || node.data.pinned) {
            return node;
          }

          const index: number = selectedNodes.findIndex((selectedNode) => selectedNode.id === node.id);
          const currentSelectedNode = selectedNodes[index];
          const previousNode = selectedNodes[index - 1];

          let newPosition: number = direction === 'horizontal' ? node.position.x : node.position.y;

          if (index > 0 && index < selectedNodes.length - 1 && previousNode && currentSelectedNode) {
            newPosition =
              direction === 'horizontal'
                ? previousNode.position.x + (previousNode.width ?? 0) + gap
                : previousNode.position.y + (previousNode.height ?? 0) + gap;
            currentSelectedNode.position[direction === 'horizontal' ? 'x' : 'y'] = newPosition;
          }

          return {
            ...node,
            position: {
              ...node.position,
              [direction === 'horizontal' ? 'x' : 'y']: newPosition,
            },
          };
        });
        const overlapFreeNodes: Node[] = resolveNodeOverlap(updatedNodes, direction);
        const diagramToLayout: RawDiagram = {
          nodes: [...overlapFreeNodes] as Node<NodeData, DiagramNodeType>[],
          edges: getEdges(),
        };
        layout(diagramToLayout, diagramToLayout, null, (laidOutDiagram) => {
          setNodes(laidOutDiagram.nodes);
          const finalDiagram: RawDiagram = {
            nodes: laidOutDiagram.nodes,
            edges: laidOutDiagram.edges,
          };
          synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
        });
      }
    }, []);
  };

  const distributeAlign = (orientation: 'left' | 'right' | 'top' | 'bottom' | 'center' | 'middle') => {
    return useCallback(
      (selectedNodeIds: string[], refElementId: string | null) => {
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
          refElementId,
          ['left', 'right', 'center'].includes(orientation) ? 'vertical' : 'horizontal'
        );
      },
      [resolveNodeOverlap]
    );
  };

  const justifyElements = (
    justifyElementsFn: (selectedNodes: Node[], selectedNodeIds: string[], refNode: Node) => Node[]
  ) => {
    return useCallback(
      (selectedNodeIds: string[], refElementId: string | null) => {
        processLayoutTool(
          selectedNodeIds,
          (selectedNodes, refNode) => {
            selectedNodes.sort(getComparePositionFn('horizontal'));
            return justifyElementsFn(selectedNodes, selectedNodeIds, refNode);
          },
          null,
          refElementId
        );
      },
      [resolveNodeOverlap]
    );
  };

  const justifyHorizontally = justifyElements(
    (selectedNodes: Node[], selectedNodeIds: string[], refNode: Node): Node[] => {
      const largestWidth: number = selectedNodes.reduce((width, node) => Math.max(width, node.width ?? 0), 0);
      return getNodes().map((node) => {
        if (
          !selectedNodeIds.includes(node.id) ||
          node.data.nodeDescription?.userResizable === 'NONE' ||
          node.data.pinned
        ) {
          return node;
        }
        return {
          ...node,
          width: largestWidth,
          position: {
            ...node.position,
            x: refNode.position.x,
          },
          data: {
            ...node.data,
            resizedByUser: true,
          },
        };
      });
    }
  );

  const justifyVertically = justifyElements(
    (selectedNodes: Node[], selectedNodeIds: string[], refNode: Node): Node[] => {
      const largestHeight: number = selectedNodes.reduce((height, node) => Math.max(height, node.height ?? 0), 0);
      return getNodes().map((node) => {
        if (
          !selectedNodeIds.includes(node.id) ||
          node.data.nodeDescription?.userResizable === 'NONE' ||
          node.data.pinned
        ) {
          return node;
        }
        return {
          ...node,
          height: largestHeight,
          position: {
            ...node.position,
            y: refNode.position.y,
          },
          data: {
            ...node.data,
            resizedByUser: true,
          },
        };
      });
    }
  );

  const arrangeInRow = (selectedNodeIds: string[]) => {
    processLayoutTool(
      selectedNodeIds,
      (selectedNodes, refNode) => {
        let nextXPosition: number = refNode.position.x;
        const updatedSelectedNodes = selectedNodes
          .filter((node) => !node.data.pinned)
          .map((node) => {
            const updatedNode = {
              ...node,
              position: {
                ...node.position,
                x: nextXPosition,
                y: refNode.position.y,
              },
            };
            nextXPosition = updatedNode.position.x + (updatedNode.width ?? 0) + arrangeGapBetweenElements;
            return updatedNode;
          });

        return getNodes().map((node) => {
          const replacedNode = updatedSelectedNodes.find((updatedSelectedNode) => updatedSelectedNode.id === node.id);
          if (replacedNode) {
            return replacedNode;
          }
          return node;
        });
      },
      getComparePositionFn('horizontal')
    );
  };

  const arrangeInColumn = (selectedNodeIds: string[]) => {
    processLayoutTool(
      selectedNodeIds,
      (selectedNodes, refNode) => {
        let nextYPosition: number = refNode.position.y;
        const updatedSelectedNodes = selectedNodes
          .filter((node) => !node.data.pinned)
          .map((node) => {
            const updatedNode = {
              ...node,
              position: {
                ...node.position,
                x: refNode.position.x,
                y: nextYPosition,
              },
            };
            nextYPosition = updatedNode.position.y + (updatedNode.height ?? 0) + arrangeGapBetweenElements;
            return updatedNode;
          });

        return getNodes().map((node) => {
          const replacedNode = updatedSelectedNodes.find((updatedSelectedNode) => updatedSelectedNode.id === node.id);
          if (replacedNode) {
            return replacedNode;
          }
          return node;
        });
      },
      getComparePositionFn('vertical'),
      null,
      'vertical'
    );
  };

  const arrangeInGrid = (selectedNodeIds: string[]) => {
    processLayoutTool(
      selectedNodeIds,
      (selectedNodes, refNode) => {
        const columnNumber: number = Math.round(Math.sqrt(selectedNodeIds.length));
        let nextXPosition: number = refNode.position.x;
        let nextYPosition: number = refNode.position.y;
        const updatedSelectedNodes = selectedNodes
          .filter((node) => !node.data.pinned)
          .map((node, index) => {
            const columnIndex = index + 1;
            const updatedNode = {
              ...node,
              position: {
                ...node.position,
                x: nextXPosition,
                y: nextYPosition,
              },
            };
            nextXPosition = updatedNode.position.x + (updatedNode.width ?? 0) + arrangeGapBetweenElements;
            if (columnIndex % columnNumber === 0) {
              nextXPosition = refNode.position.x;
              nextYPosition =
                updatedNode.position.y +
                arrangeGapBetweenElements +
                selectedNodes
                  .slice(columnIndex - columnNumber, columnIndex)
                  .reduce((maxHeight, rowNode) => Math.max(maxHeight, rowNode.height ?? 0), 0);
            }
            return updatedNode;
          });

        return getNodes().map((node) => {
          const replacedNode = updatedSelectedNodes.find((updatedSelectedNode) => updatedSelectedNode.id === node.id);
          if (replacedNode) {
            return replacedNode;
          }
          return node;
        });
      },
      getComparePositionFn('horizontal')
    );
  };

  const distributeGapHorizontally = distributeNodesOnGap('horizontal');
  const distributeGapVertically = distributeNodesOnGap('vertical');
  const distributeAlignLeft = distributeAlign('left');
  const distributeAlignRight = distributeAlign('right');
  const distributeAlignCenter = distributeAlign('center');
  const distributeAlignTop = distributeAlign('top');
  const distributeAlignBottom = distributeAlign('bottom');
  const distributeAlignMiddle = distributeAlign('middle');

  const makeNodesSameSize = useCallback(
    (selectedNodeIds: string[], refElementId: string | null) => {
      processLayoutTool(
        selectedNodeIds,
        (_selectedNodes, refNode) => {
          return getNodes().map((node) => {
            if (!selectedNodeIds.includes(node.id) || node.data.nodeDescription?.userResizable === 'NONE') {
              return node;
            }

            return {
              ...node,
              width: refNode.width,
              height: refNode.height,
              data: {
                ...node.data,
                resizedByUser: true,
              },
            };
          });
        },
        null,
        refElementId
      );
    },
    [resolveNodeOverlap]
  );

  return {
    distributeGapVertically,
    distributeGapHorizontally,
    distributeAlignLeft,
    distributeAlignRight,
    distributeAlignCenter,
    distributeAlignTop,
    distributeAlignBottom,
    distributeAlignMiddle,
    justifyHorizontally,
    justifyVertically,
    arrangeInRow,
    arrangeInColumn,
    arrangeInGrid,
    makeNodesSameSize,
  };
};
