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
import { gql, useMutation, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useTheme } from '@material-ui/core/styles';
import { useContext, useEffect, useState } from 'react';
import { Node, NodeDragHandler, useReactFlow } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DropNodeContext } from './DropNodeContext';
import { DropNodeContextValue } from './DropNodeContext.types';
import {
  DiagramBackgroundStyle,
  GQLDropNodeCompatibilityEntry,
  GQLDropNodeData,
  GQLDropNodeInput,
  GQLDropNodePayload,
  GQLDropNodeVariables,
  GQLErrorPayload,
  GQLGetDropNodeCompatibilityQueryData,
  GQLGetDropNodeCompatibilityQueryInput,
  GQLSuccessPayload,
  StyleProvider,
  UseDropNodeValue,
} from './useDropNode.types';

const dropNodeCompatibilityQuery = gql`
  query dropNodeCompatibilityQuery($editingContextId: ID!, $diagramId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              dropNodeCompatibility {
                droppedNodeDescriptionId
                droppableOnDiagram
                droppableOnNodeTypes
              }
            }
          }
        }
      }
    }
  }
`;

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

const useDropNodeMutation = () => {
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
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
      if (isErrorPayload(dropNode) || isSuccessPayload(dropNode)) {
        addMessages(dropNode.messages);
      }
    }
  }, [dropNodeData, dropNodeError]);

  const invokeMutation = (droppedElementId: string, targetElementId: string | null): void => {
    const input: GQLDropNodeInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      droppedElementId,
      targetElementId,
    };
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

export const useDropNode = (): UseDropNodeValue => {
  const [draggedNode, setDraggedNode] = useState<Node | null>(null);
  const { dropData, setDropData } = useContext<DropNodeContextValue>(DropNodeContext);
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const [dropNodeCompatibilityData, setDropNodeCompatibilityData] = useState<GQLDropNodeCompatibilityEntry[]>([]);
  const { loading, data, error } = useQuery<
    GQLGetDropNodeCompatibilityQueryData,
    GQLGetDropNodeCompatibilityQueryInput
  >(dropNodeCompatibilityQuery, {
    variables: {
      diagramId,
      editingContextId,
    },
  });
  useEffect(() => {
    if (!loading && !error && data) {
      const entries = data.viewer.editingContext.representation.description.dropNodeCompatibility;
      setDropNodeCompatibilityData(entries);
    }
  }, [data, loading, error, setDropNodeCompatibilityData]);

  const onDropNode = useDropNodeMutation();
  const { getNodes, getIntersectingNodes } = useReactFlow<NodeData, EdgeData>();

  const getNodeById: (string) => Node | undefined = (id: string) => getNodes().find((n) => n.id === id);

  const onNodeDragStart: NodeDragHandler = (_event, node) => {
    setDraggedNode(node);
    const dropDataEntry: GQLDropNodeCompatibilityEntry | undefined = dropNodeCompatibilityData.find(
      (entry) => entry.droppedNodeDescriptionId === (node as Node<NodeData>).data.descriptionId
    );
    const compatibleNodes = getNodes()
      .filter((candidate) => !isDescendantOf(node, candidate, getNodeById))
      .filter((n) => dropDataEntry?.droppableOnNodeTypes.includes((n as Node<NodeData>).data.descriptionId))
      .map((n) => n.id);

    setDropData({
      initialParentId: null,
      draggedNodeId: null,
      targetNodeId: node.parentNode || null,
      compatibleNodeIds: compatibleNodes,
      droppableOnDiagram: dropDataEntry?.droppableOnDiagram || false,
    });
  };

  const onNodeDrag: NodeDragHandler = (_event, node) => {
    if (draggedNode && !draggedNode.data.isBorderNode) {
      const intersections = getIntersectingNodes(node)
        .filter((intersectingNode) => !isDescendantOf(draggedNode, intersectingNode, getNodeById))
        .sort((n1, n2) => {
          if (n1.parentNode === n2.id) {
            return -1;
          } else if (n2.parentNode === n1.id) {
            return 1;
          } else {
            return 0;
          }
        });
      const newParentId = intersections[0]?.id || null;
      setDropData({
        initialParentId: node.parentNode || null,
        draggedNodeId: node.id,
        targetNodeId: newParentId,
        compatibleNodeIds: dropData.compatibleNodeIds,
        droppableOnDiagram: dropData.droppableOnDiagram,
      });
    }
  };

  const onNodeDragStop: NodeDragHandler = (_event, _node) => {
    if (draggedNode && draggedNode.id === dropData.draggedNodeId) {
      const oldParentId: string | null = draggedNode.parentNode || null;
      const newParentId: string | null = dropData.targetNodeId;
      const validNewParent =
        (newParentId === null && dropData.droppableOnDiagram) ||
        (newParentId !== null && dropData.compatibleNodeIds.includes(newParentId));
      if (oldParentId !== newParentId && validNewParent) {
        onDropNode(dropData.draggedNodeId, newParentId);
      }
    }
    setDraggedNode(null);
    setDropData({
      initialParentId: null,
      draggedNodeId: null,
      targetNodeId: null,
      droppableOnDiagram: false,
      compatibleNodeIds: [],
    });
  };

  const theme = useTheme();

  const dropFeedbackStyleProvider: StyleProvider = {
    getNodeStyle(nodeId: string): React.CSSProperties {
      const isCompatibleDropTarget: boolean = dropData.compatibleNodeIds.includes(nodeId);
      const isSelectedDropTarget: boolean = nodeId === dropData.targetNodeId;

      let opacity: string | undefined = undefined;
      let boxShadow: string | undefined = undefined;

      if (dropData.draggedNodeId !== null) {
        if (dropData.draggedNodeId !== nodeId && !isCompatibleDropTarget) {
          opacity = '0.4';
        }
        if (isSelectedDropTarget && isCompatibleDropTarget) {
          boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
        }
      }
      if (opacity) {
        return { opacity, boxShadow };
      } else {
        return { boxShadow };
      }
    },

    getDiagramBackgroundStyle(): DiagramBackgroundStyle {
      const diagramForbidden = dropData.draggedNodeId !== null && !dropData.droppableOnDiagram;
      const diagramTargeted = dropData.targetNodeId === null && dropData.initialParentId !== null;
      const backgroundColor =
        diagramTargeted && diagramForbidden
          ? theme.palette.action.disabledBackground
          : theme.palette.background.default;
      return {
        backgroundColor,
        smallGridColor: diagramForbidden ? backgroundColor : '#f1f1f1',
        largeGridColor: diagramForbidden ? backgroundColor : '#cccccc',
      };
    },
  };

  return { onNodeDragStart, onNodeDrag, onNodeDragStop, dropData, dropFeedbackStyleProvider };
};
