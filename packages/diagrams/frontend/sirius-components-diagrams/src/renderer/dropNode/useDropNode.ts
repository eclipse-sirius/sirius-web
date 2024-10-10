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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, OnNodeDrag, XYPosition, useReactFlow, useStoreApi } from '@xyflow/react';
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
  GQLDropNodeData,
  GQLDropNodeInput,
  GQLDropNodePayload,
  GQLDropNodeVariables,
  GQLErrorPayload,
  GQLSuccessPayload,
  UseDropNodeValue,
} from './useDropNode.types';

const dropNodeMutation = gql`
  mutation dropNode($input: DropNodeInput!) {
    dropNode(input: $input) {
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

const isErrorPayload = (payload: GQLDropNodePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLDropNodePayload): payload is GQLSuccessPayload =>
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

const useDropNodeMutation = () => {
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { addErrorMessage, addMessages } = useMultiToast();
  const [dropMutation, { data: dropNodeData, error: dropNodeError }] = useMutation<
    GQLDropNodeData,
    GQLDropNodeVariables
  >(dropNodeMutation);

  useEffect(() => {
    if (dropNodeError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (dropNodeData) {
      const { dropNode } = dropNodeData;
      if (isSuccessPayload(dropNode)) {
        addMessages(dropNode.messages);
      }
      if (isErrorPayload(dropNode)) {
        addMessages(dropNode.messages);
      }
    }
  }, [dropNodeData, dropNodeError]);

  const invokeMutation = useCallback(
    (
      droppedNode: Node<NodeData>,
      targetElementId: string | null,
      dropPosition: XYPosition,
      onDragCancelled: (node: Node<NodeData>) => void
    ): void => {
      const input: GQLDropNodeInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        droppedElementId: droppedNode.id,
        targetElementId,
        x: dropPosition.x,
        y: dropPosition.y,
      };
      if (!readOnly) {
        dropMutation({ variables: { input } }).then((result) => {
          if (result.data?.dropNode && isErrorPayload(result.data?.dropNode)) {
            onDragCancelled(droppedNode);
          }
        });
      }
    },
    [readOnly]
  );

  return invokeMutation;
};

export const useDropNode = (): UseDropNodeValue => {
  const { droppableOnDiagram, initialPosition, draggedNodeId, initializeDrop, resetDrop } =
    useContext<DropNodeContextValue>(DropNodeContext);

  const { diagramDescription } = useDiagramDescription();
  const onDropNode = useDropNodeMutation();
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
      if (!node || nodes.length > 1) {
        // Drag on multi selection is not supported yet
        return;
      }
      const computedNode = getDraggableNode(node);

      const dropDataEntry: GQLDropNodeCompatibility | undefined = diagramDescription.dropNodeCompatibility.find(
        (entry) => entry.droppedNodeDescriptionId === (computedNode as Node<NodeData>).data.descriptionId
      );
      const compatibleNodes = getNodes()
        .filter(
          (candidate) => !candidate.hidden && !isDescendantOf(computedNode, candidate, storeApi.getState().nodeLookup)
        )
        .filter((candidate) =>
          dropDataEntry?.droppableOnNodeTypes.includes((candidate as Node<NodeData>).data.descriptionId)
        )
        .map((candidate) => candidate.id);

      initializeDrop({
        initialPosition: computedNode.position,
        droppableOnDiagram: dropDataEntry?.droppableOnDiagram || false,
        draggedNodeId: computedNode.id,
      });

      setNodes((nds) =>
        nds.map((n) => {
          if (compatibleNodes.includes(n.id)) {
            return {
              ...n,
              data: {
                ...n.data,
                isDropNodeCandidate: true,
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
    (_event, node, nodes) => {
      if (!node || nodes.length > 1) {
        // Drag on multi selection is not supported yet
        return;
      }

      const draggedNode = getNodes().find((node) => node.id === draggedNodeId) || null;

      if (draggedNode && !draggedNode.data.isBorderNode) {
        const intersections = getIntersectingNodes(draggedNode).filter((intersectingNode) => !intersectingNode.hidden);
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
    [droppableOnDiagram, draggedNodeId, getNodes]
  );

  const onNodeDragStop: OnNodeDrag<Node<NodeData>> = useCallback(
    (event) => {
      const draggedNode = getNodes().find((node) => node.id === draggedNodeId) || null;
      const dropPosition = screenToFlowPosition({
        x: event.clientX,
        y: event.clientY,
      });

      if (draggedNode) {
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
          onDropNode(draggedNode, target, dropPosition, cancelDrop);
        }
        if (!isDropOnNode && !isValidDropOnDiagram && !isDropFromDiagramToDiagram && !isBorderNodeDrop) {
          cancelDrop(draggedNode);
        } else if (isDropOnNode && !isValidDropOnNode && !isDropOnSameParent) {
          cancelDrop(draggedNode);
        } else if (isDropOnNode && draggedNode?.type === 'iconLabelNode' && isDropOnSameParent) {
          cancelDrop(draggedNode);
        }

        resetDrop();

        setNodes((nds) =>
          nds.map((n) => {
            if (n.data.isDropNodeCandidate || n.data.isDropNodeTarget) {
              return {
                ...n,
                data: {
                  ...n.data,
                  isDropNodeCandidate: false,
                  isDropNodeTarget: false,
                },
              };
            }
            return n;
          })
        );
      }
    },
    [droppableOnDiagram, initialPosition, draggedNodeId, getNodes]
  );

  const cancelDrop = (node: Node<NodeData>) => {
    if (initialPosition) {
      setNodes((nds) =>
        nds.map((n) => {
          if (n.id === node.id) {
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
    onNodeDragStart,
    onNodeDrag,
    onNodeDragStop,
  };
};
