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
import { useDeletionConfirmationDialog, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  GQLDeleteFromDiagramData,
  GQLDeleteFromDiagramInput,
  GQLDeleteFromDiagramPayload,
  GQLDeleteFromDiagramSuccessPayload,
  GQLDeleteFromDiagramVariables,
  GQLDeletionPolicy,
  GQLErrorPayload,
} from '../palette/Palette.types';
import { UseDiagramDeleteValue } from './useDiagramDelete.types';

export const deleteFromDiagramMutation = gql`
  mutation deleteFromDiagram($input: DeleteFromDiagramInput!) {
    deleteFromDiagram(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on DeleteFromDiagramSuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDeleteFromDiagramPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLDeleteFromDiagramPayload): payload is GQLDeleteFromDiagramSuccessPayload =>
  payload.__typename === 'DeleteFromDiagramSuccessPayload';

export const useDiagramDelete = (): UseDiagramDeleteValue => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { getNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const [deleteElementsMutation, { data: deleteElementsData, error: deleteElementsError }] = useMutation<
    GQLDeleteFromDiagramData,
    GQLDeleteFromDiagramVariables
  >(deleteFromDiagramMutation);

  useEffect(() => {
    if (deleteElementsError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (deleteElementsData) {
      const { deleteFromDiagram } = deleteElementsData;
      if (isErrorPayload(deleteFromDiagram) || isSuccessPayload(deleteFromDiagram)) {
        addMessages(deleteFromDiagram.messages);
      }
    }
  }, [deleteElementsData, deleteElementsError]);

  const onDelete = useCallback((event: React.KeyboardEvent<Element>) => {
    const { key } = event;
    /*If a modifier key is hit alone, do nothing*/
    const isTextField = event.target instanceof HTMLInputElement || event.target instanceof HTMLTextAreaElement;
    if (((event.altKey || event.shiftKey) && event.getModifierState(key)) || isTextField) {
      return;
    }
    event.preventDefault();

    if (key === 'Delete' && editingContextId && diagramId && !readOnly) {
      const nodeToDeleteIds: string[] = getNodes()
        .filter((node) => node.selected)
        .map((node) => node.id);
      const input: GQLDeleteFromDiagramInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        nodeIds: nodeToDeleteIds,
        edgeIds: [],
        deletionPolicy: GQLDeletionPolicy.SEMANTIC,
      };
      showDeletionConfirmation(() => {
        deleteElementsMutation({ variables: { input } });
      });
    }
  }, []);

  return {
    onDelete,
  };
};
