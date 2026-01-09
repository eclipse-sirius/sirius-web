/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { Rect } from '@xyflow/system';
import { useCallback, useContext, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { GQLDropNodeCompatibility } from '../../representation/DiagramRepresentation.types';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { isDescendantOf } from '../layout/layoutNode';
import { ListNodeData } from '../node/ListNode.types';
import { evaluateAbsolutePosition } from '../node/NodeUtils';
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

const useDropNodesMutation = () => {
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'useDropNodeMutation' });
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { addErrorMessage, addMessages } = useMultiToast();
  const [dropMutation, { data: dropNodesData, error: dropNodesError }] = useMutation<
    GQLDropNodesData,
    GQLDropNodesVariables
  >(dropNodesMutation);

  useEffect(() => {
    if (dropNodesError) {
      addErrorMessage(t('errors.unexpected'));
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

  return useCallback(
    (
      droppedNodes: Node<NodeData>[],
      targetElementId: string | null,
      dropPositions: XYPosition[],
      onDragCancelled: (nodes: Node<NodeData>[]) => void
    ): void => {
      const input: GQLDropNodesInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        droppedElementIds: droppedNodes.map((node) => node.id),
        targetElementId,
        dropPositions,
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
};

export const useDropNodes = (): UseDropNodesValue => {
  const { droppableOnDiagram, initialPositions, initializeDrop, resetDrop } =
    useContext<DropNodeContextValue>(DropNodeContext);

  const { diagramDescription } = useDiagramDescription();
  const onDropNodes = useDropNodesMutation();
  const { getNodes, getIntersectingNodes, screenToFlowPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { setNodes } = useStore();
  const storeApi = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const getNodeById = (id: string) => storeApi.getState().nodeLookup.get(id);

  // Some nodes are not draggable themselves.
  // In these cases, what we *actually* want to drag is their parent.
  const getDraggableNode = (node: Node<NodeData>): Node<NodeData> => {
    if (node.parentId) {
      const parentNode = getNodeById(node.parentId);
      if (parentNode && isListData(parentNode) && !parentNode.data.areChildNodesDraggable) {
        return getDraggableNode(parentNode);
      }
    }
    return node;
  };

  const onNodesDragStart: OnNodeDrag<Node<NodeData>> = useCallback(
    (_event, _node, nodes) => {
      if (nodes.length === 0) {
        return;
      }
      const draggedNodes = nodes.map((node) => getDraggableNode(node));

      // Store the initial positions to be able to reset them correctly on cancel.
      const initialPositions: Map<string, XYPosition> = new Map();
      draggedNodes.forEach((node) => {
        initialPositions.set(node.id, node.position);
      });

      // Find the entries which accept at least one of the dragged nodes
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

      const droppableOnDiagram: boolean = dropDataEntries.some((entry) => entry.droppableOnDiagram);

      initializeDrop({
        initialPositions,
        droppableOnDiagram,
        dragging: true,
      });

      const draggedNodeIds = new Set();
      draggedNodes.forEach((node) => draggedNodeIds.add(node.id));

      setNodes((nds) =>
        nds.map((n) => {
          if (compatibleTargetNodes.includes(n.id)) {
            return {
              ...n,
              data: {
                ...n.data,
                isDraggedNode: draggedNodeIds.has(n.id),
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
          if (draggedNodeIds.has(n.id)) {
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

  const onNodesDrag: OnNodeDrag<Node<NodeData>> = useCallback(
    (event, _node, nodes) => {
      if (nodes.length === 0) {
        return;
      }
      const draggedNodes: Node<NodeData>[] = getNodes().filter((node) => node.data.isDraggedNode);

      if (draggedNodes.length > 0 && !draggedNodes.some((node) => node.data.isBorderNode)) {
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
              (intersectingNode) =>
                !draggedNodes.some((draggedNode) =>
                  isDescendantOf(draggedNode, intersectingNode, storeApi.getState().nodeLookup)
                )
            )
            .sort((n1, n2) => getNodeDepth(n2, intersections) - getNodeDepth(n1, intersections))[0]?.id || null;

        const previousTargetNode = getNodes().find((node) => node.data.isDropNodeTarget) || null;

        if (previousTargetNode?.id != newParentId) {
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

  const onNodesDragStop: OnNodeDrag<Node<NodeData>> = useCallback(
    (_event, node, _nodes) => {
      const draggedNodes: Node<NodeData>[] = getNodes().filter((node) => node.data.isDraggedNode);
      const draggedNode: Node<NodeData> | undefined =
        draggedNodes.find((draggedNode) => draggedNode.id === node.id) || undefined;
      if (draggedNode) {
        const dropPositions = draggedNodes.map((draggedNode) =>
          evaluateAbsolutePosition(draggedNode, storeApi.getState().nodeLookup)
        );
        const targetNode = getNodes().find((node) => node.data.isDropNodeTarget);
        const isDropOnNode: boolean = !!targetNode;

        const isDropOnSameParent: boolean =
          isDropOnNode && !!draggedNode?.parentId && draggedNode.parentId === targetNode?.id;

        const isDropFromDiagramToDiagram: boolean = !isDropOnNode && !draggedNode?.parentId;
        const isBorderNodeDrop: boolean =
          draggedNode.data.isBorderNode && (!isDropOnNode || draggedNode.parentId === targetNode?.id);

        const isValidDropOnNode: boolean = isDropOnNode && !!targetNode?.data.isDropNodeCandidate;
        const isValidDropOnDiagram: boolean = !isDropOnNode && droppableOnDiagram;

        const resetInitialPositions = (nodes: Node<NodeData>[]) => {
          setNodes((nds) =>
            nds.map((n) => {
              if (nodes.some((node) => node.id === n.id)) {
                return {
                  ...n,
                  position: initialPositions[n.id] || n.position,
                  dragging: false,
                };
              }
              return n;
            })
          );
        };

        if ((isValidDropOnDiagram && !isDropFromDiagramToDiagram) || (isValidDropOnNode && !isDropOnSameParent)) {
          const target = targetNode?.id || null;
          onDropNodes(draggedNodes, target, dropPositions, resetInitialPositions);
        }
        let dropCancelled = false;
        if (!isDropOnNode && !isValidDropOnDiagram && !isDropFromDiagramToDiagram && !isBorderNodeDrop) {
          dropCancelled = true;
        } else if (isDropOnNode && !isValidDropOnNode && !isDropOnSameParent) {
          dropCancelled = true;
        } else if (isDropOnNode && draggedNode?.type === 'iconLabelNode' && isDropOnSameParent) {
          dropCancelled = true;
        }

        setNodes((nodes) =>
          nodes.map((node) => {
            let finalPosition: XYPosition = node.position;
            if (dropCancelled && initialPositions.get(node.id)) {
              finalPosition = initialPositions.get(node.id) || finalPosition;
            }
            if (node.data.isDropNodeCandidate || node.data.isDropNodeTarget) {
              return {
                ...node,
                position: finalPosition,
                data: {
                  ...node.data,
                  isDropNodeCandidate: false,
                  isDropNodeTarget: false,
                },
              };
            }
            if (draggedNodes.some((draggedNode) => draggedNode.id === node.id)) {
              return {
                ...node,
                position: finalPosition,
                dragging: false,
              };
            }
            return node;
          })
        );
      }
      resetDrop();
    },
    [droppableOnDiagram, initialPositions, getNodes]
  );

  return {
    onNodesDragStart,
    onNodesDrag,
    onNodesDragStop,
  };
};
