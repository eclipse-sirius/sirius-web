/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { useCallback } from 'react';
import {
  GQLDeleteFromDiagramData,
  GQLDeleteFromDiagramInput,
  GQLDeleteFromDiagramVariables,
  GQLDeletionPolicy,
  UseDeleteValue,
} from './useDelete.types';

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

export const useDelete = (editingContextId: string, diagramId: string): UseDeleteValue => {
  const [rawDeleteFromDiagram, rawDeleteFromDiagramResult] = useMutation<
    GQLDeleteFromDiagramData,
    GQLDeleteFromDiagramVariables
  >(deleteFromDiagramMutation);

  const deleteDiagramElements = useCallback(
    (nodeIds: string[], edgeIds: string[], deletionPolicy: GQLDeletionPolicy) => {
      const input: GQLDeleteFromDiagramInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        nodeIds,
        edgeIds,
        deletionPolicy,
      };
      rawDeleteFromDiagram({ variables: { input } });
    },
    [editingContextId, diagramId, rawDeleteFromDiagram]
  );

  useReporting(rawDeleteFromDiagramResult, (data: GQLDeleteFromDiagramData) => data.deleteFromDiagram);

  return { deleteDiagramElements };
};
