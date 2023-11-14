/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { useTheme } from '@material-ui/core/styles';
import { useCallback, useContext, useEffect } from 'react';
import { Node, NodeDragHandler, Viewport, XYPosition, useReactFlow, useStoreApi, useViewport } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { GQLDropNodeCompatibility } from '../../representation/DiagramRepresentation.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useLayout } from '../layout/useLayout';
import { DropNodeContext } from './DropNodeContext';
import { DropNodeContextValue } from './DropNodeContext.types';
import {
  DiagramBackgroundStyle,
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

const getNodeDepth = (node: Node<NodeData>, intersections: Node<NodeData>[]): number => {
  let nodeDepth = 0;

  let nodeHierarchy: Node<NodeData> | undefined = node;
  while (nodeHierarchy) {
    nodeDepth++;
    nodeHierarchy = intersections.find((node) => node.id === nodeHierarchy?.parentNode);
  }
  return nodeDepth;
};

const useDropNodeMutation = () => {
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { addErrorMessage, addMessages } = useMultiToast();
  const [dropMutation, { data: dropNodeData, error: dropNodeError }] = useMutation<
    GQLDropNodeData,
    GQLDropNodeVariables
  >(dropNodeMutation);
  const { setReferencePosition, resetReferencePosition } = useLayout();

  useEffect(() => {
    if (dropNodeError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
      resetReferencePosition();
    }
    if (dropNodeData) {
      const { dropNode } = dropNodeData;
      if (isSuccessPayload(dropNode)) {
        addMessages(dropNode.messages);
      }
      if (isErrorPayload(dropNode)) {
        addMessages(dropNode.messages);
        resetReferencePosition();
      }
    }
  }, [dropNodeData, dropNodeError]);

  const invokeMutation = (droppedElementId: string, targetElementId: string | null, dropPosition: XYPosition): void => {
    const input: GQLDropNodeInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      droppedElementId,
      targetElementId,
    };
    setReferencePosition(dropPosition, targetElementId);
    dropMutation({ variables: { input } });
  };

  return invokeMutation;
};

const isDescendantOf = (parent: Node, candidate: Node, nodeById: (string) => Node | undefined): boolean => {
  if (parent.id === candidate.id) {
    return true;
  } else {
    const candidateParent = nodeById(candidate.parentNode);
    return candidateParent !== undefined && isDescendantOf(parent, candidateParent, nodeById);
  }
};

const computeDropPosition = (
  event: MouseEvent | React.MouseEvent,
  { x: viewportX, y: viewportY, zoom: viewportZoom }: Viewport,
  bounds?: DOMRect
): XYPosition => {
  return {
    x: (event.clientX - (bounds?.left ?? 0) - viewportX) / viewportZoom,
    y: (event.clientY - (bounds?.top ?? 0) - viewportY) / viewportZoom,
  };
};

export const useDropNode = (): UseDropNodeValue => {
  const {
    initialParentId,
    draggedNode,
    targetNodeId,
    compatibleNodeIds,
    droppableOnDiagram,
    initializeDrop,
    setTargetNodeId,
    resetDrop,
  } = useContext<DropNodeContextValue>(DropNodeContext);

  const { diagramDescription } = useDiagramDescription();

  const onDropNode = useDropNodeMutation();
  const { getNodes, getIntersectingNodes } = useReactFlow<NodeData, EdgeData>();
  const viewport = useViewport();
  const { domNode } = useStoreApi().getState();
  const element = domNode?.getBoundingClientRect();

  const getNodeById: (string) => Node | undefined = (id: string) => getNodes().find((n) => n.id === id);

  const onNodeDragStart: NodeDragHandler = (_event, node) => {
    const dropDataEntry: GQLDropNodeCompatibility | undefined = diagramDescription.dropNodeCompatibility.find(
      (entry) => entry.droppedNodeDescriptionId === (node as Node<NodeData>).data.descriptionId
    );
    const compatibleNodes = getNodes()
      .filter((candidate) => !candidate.hidden && !isDescendantOf(node, candidate, getNodeById))
      .filter((candidate) =>
        dropDataEntry?.droppableOnNodeTypes.includes((candidate as Node<NodeData>).data.descriptionId)
      )
      .map((candidate) => candidate.id);

    initializeDrop({
      initialParentId: node.parentNode || null,
      draggedNode: node,
      targetNodeId: node.parentNode || null,
      compatibleNodeIds: compatibleNodes,
      droppableOnDiagram: dropDataEntry?.droppableOnDiagram || false,
    });
  };

  const onNodeDrag: NodeDragHandler = (_event, node) => {
    if (draggedNode && !draggedNode.data.isBorderNode) {
      const intersections = getIntersectingNodes(node).filter((intersectingNode) => !intersectingNode.hidden);
      const newParentId =
        [...intersections]
          .filter((intersectingNode) => !isDescendantOf(draggedNode, intersectingNode, getNodeById))
          .sort((n1, n2) => getNodeDepth(n2, intersections) - getNodeDepth(n1, intersections))[0]?.id || null;
      setTargetNodeId(newParentId);
    }
  };

  const onNodeDragStop: (onDragCancelled: (node: Node) => void) => NodeDragHandler = useCallback(
    (onDragCancelled) => {
      return (event, _node) => {
        const dropPosition = computeDropPosition(event, viewport, element);
        if (draggedNode) {
          const oldParentId: string | null = draggedNode.parentNode || null;
          const newParentId: string | null = targetNodeId;
          const validNewParent =
            (newParentId === null && droppableOnDiagram) ||
            (newParentId !== null && compatibleNodeIds.includes(newParentId));
          if (oldParentId !== newParentId) {
            if (validNewParent) {
              onDropNode(draggedNode.id, newParentId, dropPosition);
            } else {
              onDragCancelled(draggedNode);
            }
          } else if (draggedNode.type === 'iconLabelNode') {
            // For list items, any move which did not change the parent (and potentially
            // trigger a DnD operation) should be reverted as their layout is fixed.
            onDragCancelled(draggedNode);
          }
        }
        resetDrop();
      };
    },
    [element?.top, element?.left, viewport, draggedNode, targetNodeId, droppableOnDiagram, compatibleNodeIds]
  );

  const hasDroppedNodeParentChanged = (): boolean => {
    return (draggedNode?.parentNode ?? null) !== targetNodeId;
  };

  const theme = useTheme();
  const diagramTargeted = targetNodeId === null && initialParentId !== null;
  const diagramForbidden = diagramTargeted && draggedNode?.id !== null && !droppableOnDiagram;
  const backgroundColor = diagramForbidden ? theme.palette.action.disabledBackground : theme.palette.background.default;
  const diagramBackgroundStyle: DiagramBackgroundStyle = {
    backgroundColor,
    smallGridColor: diagramForbidden ? backgroundColor : '#f1f1f1',
    largeGridColor: diagramForbidden ? backgroundColor : '#cccccc',
  };

  return {
    onNodeDragStart,
    onNodeDrag,
    onNodeDragStop,
    hasDroppedNodeParentChanged,
    compatibleNodeIds,
    draggedNode,
    targetNodeId,
    diagramBackgroundStyle,
  };
};
