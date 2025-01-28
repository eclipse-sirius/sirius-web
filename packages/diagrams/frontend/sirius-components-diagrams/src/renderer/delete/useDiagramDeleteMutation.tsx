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
import { useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLDeleteFromDiagramData,
  GQLDeleteFromDiagramInput,
  GQLDeleteFromDiagramPayload,
  GQLDeleteFromDiagramSuccessPayload,
  GQLDeleteFromDiagramVariables,
  GQLDeletionPolicy,
  GQLErrorPayload,
  UseDiagramDeleteMutation,
} from './useDiagramDeleteMutation.types';

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

export const useDiagramDeleteMutation = (): UseDiagramDeleteMutation => {
  const { addMessages, addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [deleteElementsMutation, { data: deleteElementsData, error: deleteElementsError }] = useMutation<
    GQLDeleteFromDiagramData,
    GQLDeleteFromDiagramVariables
  >(deleteFromDiagramMutation);

  const deleteElements = useCallback(
    async (nodeIds: string[], edgesId: string[], deletePocily: GQLDeletionPolicy) => {
      const input: GQLDeleteFromDiagramInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        nodeIds: nodeIds,
        edgeIds: edgesId,
        deletionPolicy: deletePocily,
      };

      const { data } = await deleteElementsMutation({ variables: { input } });
      if (data) {
        const { deleteFromDiagram } = data;
        if (isErrorPayload(deleteFromDiagram) || isSuccessPayload(deleteFromDiagram)) {
          addMessages(deleteFromDiagram.messages);
        }
      }
    },
    [editingContextId, diagramId, deleteElementsMutation]
  );

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

  return { deleteElements };
};
