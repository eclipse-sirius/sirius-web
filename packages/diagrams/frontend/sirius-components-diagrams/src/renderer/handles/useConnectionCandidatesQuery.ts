/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo and others.
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
import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useEffect, useMemo } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  GQLDiagramDescription,
  GQLGetConnectionCandidatesData,
  GQLGetConnectionCandidatesVariables,
  GQLRepresentationDescription,
} from './useConnectionCandidatesQuery.types';

const getConnectorToolCandidateDescriptionIdsQuery = gql`
  query getConnectorToolCandidateDescriptionIds($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              connectorToolCandidateDescriptionIds(diagramElementId: $diagramElementId)
            }
          }
        }
      }
    }
  }
`;

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const useConnectionCandidatesQuery = (
  editingContextId: string,
  diagramId: string,
  diagramElementId: string
): string[] | null => {
  const { addErrorMessage } = useMultiToast();
  const { getNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const shouldSkip =
    getNodes().filter((node) => node.selected).length + getEdges().filter((edge) => edge.selected).length > 1;

  const { data, error } = useQuery<GQLGetConnectionCandidatesData, GQLGetConnectionCandidatesVariables>(
    getConnectorToolCandidateDescriptionIdsQuery,
    {
      variables: {
        editingContextId,
        diagramId,
        diagramElementId,
      },
      skip: shouldSkip,
    }
  );

  const diagramDescription: GQLRepresentationDescription | null =
    data?.viewer.editingContext?.representation?.description ?? null;

  const nodeCandidates: string[] = useMemo(() => {
    if (diagramDescription && isDiagramDescription(diagramDescription)) {
      return diagramDescription.connectorToolCandidateDescriptionIds;
    }
    return [];
  }, [diagramDescription]);

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return nodeCandidates;
};
