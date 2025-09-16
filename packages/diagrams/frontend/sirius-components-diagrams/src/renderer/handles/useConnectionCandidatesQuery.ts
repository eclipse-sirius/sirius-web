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
import {
  GQLDiagramDescription,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLRepresentationDescription,
} from '../connector/useConnector.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

const getConnectorToolsCandidatesQuery = gql`
  query getConnectorToolsCandidates($editingContextId: ID!, $diagramId: ID!, $sourceDiagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              connectorToolsCandidates(sourceDiagramElementId: $sourceDiagramElementId) {
                candidateDescriptionIds
              }
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

export const useConnectionCandidatesQuery = (editingContextId: string, diagramId: string, nodeId: string): string[] => {
  const { addErrorMessage } = useMultiToast();
  const { getNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const shouldSkip =
    getNodes().filter((node) => node.selected).length + getEdges().filter((edge) => edge.selected).length > 1;

  const { data, error } = useQuery<GQLGetToolSectionsData, GQLGetToolSectionsVariables>(
    getConnectorToolsCandidatesQuery,
    {
      variables: {
        editingContextId,
        diagramId,
        sourceDiagramElementId: nodeId,
      },
      skip: shouldSkip,
    }
  );

  const diagramDescription: GQLRepresentationDescription | null =
    data?.viewer.editingContext.representation.description ?? null;

  const candidateDescriptionIds: string[] = useMemo(() => {
    return diagramDescription && isDiagramDescription(diagramDescription)
      ? diagramDescription.connectorToolsCandidates.candidateDescriptionIds
      : [];
  }, [diagramDescription]);

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return candidateDescriptionIds;
};
