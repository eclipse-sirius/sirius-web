/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Edge, InternalNode, Node, OnNodeDrag, useReactFlow, useStoreApi, XYPosition } from '@xyflow/react';
import { NodeLookup, Rect } from '@xyflow/system';
import { useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { GQLDropNodeCompatibility } from '../../representation/DiagramRepresentation.types';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { isDescendantOf } from '../layout/layoutNode';
import { ListNodeData } from '../node/ListNode.types';
import { DropNodeContext } from './DropNodeContext';
import { DropNodeContextValue } from './DropNodeContext.types';
import {
  GQLDropNodesData,
  GQLDropNodesInput,
  GQLDropNodesPayload,
  GQLDropNodesVariables,
  GQLErrorPayload,
  GQLSuccessPayload,
  UseDropNodesValue,
} from './useDropNodes.types';

const dropNodesMutation = gql`
  mutation dropNodes($input: DropNodesInput!) {
    dropNodes(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDropNodesPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLDropNodesPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

const getNodeDepth = (node: Node<NodeData>, intersections: Node<NodeData>[]): number => {
  let nodeDepth = 0;

  let nodeHierarchy: Node<NodeData> | undefined = node;
  while (nodeHierarchy) {
    nodeDepth++;
    nodeHierarchy = intersections.find((node) => node.id === nodeHierarchy?.parentId);
  }
  return nodeDepth;
};

const evaluateAbsolutePosition = (node: Node, nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>): XYPosition => {
  let nextParentId: string | undefined = node.parentId;
  const positionAbsolute: XYPosition = { ...node.position };
  while (nextParentId) {
    const parent = nodeLookup.get(nextParentId);
    nextParentId = parent?.parentId;
    if (parent) {
      positionAbsolute.x += parent.position.x;
      positionAbsolute.y += parent.position.y;
    }
  }
  return positionAbsolute;
};

const useDropNodesMutation = () => {
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { addErrorMessage, addMessages } = useMultiToast();
  const [dropMutation, { data: dropNodesData, error: dropNodesError }] = useMutation<
    GQLDropNodesData,
    GQLDropNodesVariables
  >(dropNodesMutation);

  useEffect(() => {
    if (dropNodesError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (dropNodesData) {
      const { dropNodes } = dropNodesData;
      if (isSuccessPayload(dropNodes)) {
        addMessages(dropNodes.messages);
      }
      if (isErrorPayload(dropNodes)) {
        addMessages(dropNodes.messages);
      }
    }
  }, [dropNodesData, dropNodesError]);

  const invokeMutation = useCallback(
    (
      droppedNodes: Node<NodeData>[],
      targetElementId: string | null,
      dropPosition: XYPosition,
      onDragCancelled: (nodes: Node<NodeData>[]) => void
    ): void => {
      const input: GQLDropNodesInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        droppedElementIds: droppedNodes.map((node) => node.id),
        targetElementId,
        x: dropPosition.x,
        y: dropPosition.y,
      };
      if (!readOnly) {
        dropMutation({ variables: { input } }).then((result) => {
          if (result.data?.dropNodes && isErrorPayload(result.data?.dropNodes)) {
            onDragCancelled(droppedNodes);
          }
        });
      }
    },
    [readOnly]
  );

  return invokeMutation;
};

export const useDropNodes = (): UseDropNodesValue => {
  const { droppableOnDiagram, initialPosition, initializeDrop, resetDrop } =
    useContext<DropNodeContextValue>(DropNodeContext);

  const { diagramDescription } = useDiagramDescription();
  const onDropNodes = useDropNodesMutation();
  const { getNodes, getIntersectingNodes, screenToFlowPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { setNodes } = useStore();
  const storeApi = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  const getNodeById = (id: string) => storeApi.getState().nodeLookup.get(id);

  const getDraggableNode = (node: Node<NodeData>): Node<NodeData> => {
    if (node.parentId) {
      const parentNode = getNodeById(node.parentId);
      if (parentNode && isListData(parentNode) && !parentNode.data.areChildNodesDraggable) {
        return getDraggableNode(parentNode);
      }
    }
    return node;
  };

  const onNodeDragStart: OnNodeDrag<Node<NodeData>> = useCallback(
    (_event, node, nodes) => {
      if (!node) {
        return;
      }
      const draggedNodes = nodes.map((node) => getDraggableNode(node));

      const dropDataEntries: GQLDropNodeCompatibility[] = diagramDescription.dropNodeCompatibility.filter((entry) =>
        draggedNodes.some((node) => node.data.descriptionId === entry.droppedNodeDescriptionId)
      );
      const compatibleTargetNodes = getNodes()
        .filter(
          (candidate) =>
            // Only consider visible nodes
            !candidate.hidden &&
            // A node cannot be dropped on itself or one of its descendants
            !draggedNodes.some((draggedNode) =>
              isDescendantOf(draggedNode, candidate, storeApi.getState().nodeLookup)
            ) &&
            // Check declared compatibility
            dropDataEntries.some((entry) => entry.droppableOnNodeTypes.includes(candidate.data.descriptionId))
        )
        .map((candidate) => candidate.id);

      initializeDrop({
        initialPosition: getDraggableNode(node).position,
        droppableOnDiagram: dropDataEntries.length > 0 && dropDataEntries.some((entry) => entry.droppableOnDiagram),
        dragging: true,
      });

      setNodes((nds) =>
        nds.map((n) => {
          if (compatibleTargetNodes.includes(n.id)) {
            return {
              ...n,
              data: {
                ...n.data,
                isDraggedNode: draggedNodes.some((node) => node.id === n.id),
                isDropNodeCandidate: true,
              },
            };
          }
          if (draggedNodes.some((node) => node.parentId === n.id)) {
            return {
              ...n,
              data: {
                ...n.data,
                isDropNodeTarget: true,
              },
            };
          }
          if (draggedNodes.some((node) => node.id === n.id)) {
            return {
              ...n,
              data: {
                ...n.data,
                isDraggedNode: true,
              },
            };
          }
          return n;
        })
      );
    },
    [getNodes]
  );

  const onNodeDrag: OnNodeDrag<Node<NodeData>> = useCallback(
    (event, node, _nodes) => {
      if (!node) {
        return;
      }

      const draggedNode = getNodes().find((node) => node.data.isDraggedNode) || null;

      if (draggedNode && !draggedNode.data.isBorderNode) {
        const mouseXYPosition: XYPosition = screenToFlowPosition({
          x: event.clientX,
          y: event.clientY,
        });
        const rectNode: Rect = {
          x: mouseXYPosition.x,
          y: mouseXYPosition.y,
          width: 1,
          height: 1,
        };
        const intersections = getIntersectingNodes(rectNode).filter((intersectingNode) => !intersectingNode.hidden);

        const newParentId =
          [...intersections]
            .filter(
              (intersectingNode) => !isDescendantOf(draggedNode, intersectingNode, storeApi.getState().nodeLookup)
            )
            .sort((n1, n2) => getNodeDepth(n2, intersections) - getNodeDepth(n1, intersections))[0]?.id || null;

        const targetNode = getNodes().find((node) => node.data.isDropNodeTarget) || null;

        if (targetNode?.id != newParentId) {
          setNodes((nds) =>
            nds.map((n) => {
              if (n.id === newParentId) {
                return {
                  ...n,
                  data: {
                    ...n.data,
                    isDropNodeTarget: true,
                  },
                };
              } else if (n.data.isDropNodeTarget && n.id !== newParentId) {
                return {
                  ...n,
                  data: {
                    ...n.data,
                    isDropNodeTarget: false,
                  },
                };
              }
              return n;
            })
          );
        }
      }
    },
    [droppableOnDiagram, getNodes]
  );

  const onNodeDragStop: OnNodeDrag<Node<NodeData>> = useCallback(
    (_event, draggedNode, draggedNodes) => {
      if (draggedNode) {
        const dropPosition = evaluateAbsolutePosition(draggedNode, storeApi.getState().nodeLookup);
        const targetNode = getNodes().find((node) => node.data.isDropNodeTarget);
        const isDropOnNode: boolean = !!targetNode;

        const isDropOnSameParent: boolean =
          isDropOnNode && !!draggedNode?.parentId && draggedNode.parentId === targetNode?.id;

        const isDropFromDiagramToDiagram: boolean = !isDropOnNode && !draggedNode?.parentId;
        const isBorderNodeDrop: boolean =
          draggedNode.data.isBorderNode && (!isDropOnNode || draggedNode.parentId === targetNode?.id);

        const isValidDropOnNode: boolean = isDropOnNode && !!targetNode?.data.isDropNodeCandidate;
        const isValidDropOnDiagram: boolean = !isDropOnNode && droppableOnDiagram;

        if ((isValidDropOnDiagram && !isDropFromDiagramToDiagram) || (isValidDropOnNode && !isDropOnSameParent)) {
          const target = targetNode?.id || null;
          onDropNodes(draggedNodes, target, dropPosition, cancelDrop);
        }
        if (!isDropOnNode && !isValidDropOnDiagram && !isDropFromDiagramToDiagram && !isBorderNodeDrop) {
          cancelDrop(draggedNodes);
        } else if (isDropOnNode && !isValidDropOnNode && !isDropOnSameParent) {
          cancelDrop(draggedNodes);
        } else if (isDropOnNode && draggedNode?.type === 'iconLabelNode' && isDropOnSameParent) {
          cancelDrop(draggedNodes);
        }

        setNodes((previousNodes) =>
          previousNodes.map((previousNode) => {
            if (previousNode.data.isDropNodeCandidate || previousNode.data.isDropNodeTarget) {
              return {
                ...previousNode,
                data: {
                  ...previousNode.data,
                  isDropNodeCandidate: false,
                  isDropNodeTarget: false,
                },
              };
            }
            if (previousNode.dragging) {
              return {
                ...previousNode,
                dragging: false,
              };
            }
            return previousNode;
          })
        );
      }
      resetDrop();
    },
    [droppableOnDiagram, initialPosition, getNodes]
  );

  const cancelDrop = (nodes: Node<NodeData>[]) => {
    if (initialPosition) {
      setNodes((nds) =>
        nds.map((n) => {
          if (nodes.some((node) => node.id === n.id)) {
            return {
              ...n,
              position: initialPosition,
              dragging: false,
            };
          }
          return n;
        })
      );
    }
  };

  return {
    onNodesDragStart: onNodeDragStart,
    onNodesDrag: onNodeDrag,
    onNodesDragStop: onNodeDragStop,
  };
};
