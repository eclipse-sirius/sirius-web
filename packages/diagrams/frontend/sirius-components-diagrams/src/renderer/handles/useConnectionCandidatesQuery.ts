/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo and others.
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
import { useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useMemo } from 'react';
import {
  GQLDiagramDescription,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLNodeDescription,
  GQLRepresentationDescription,
  GQLSingleClickOnTwoDiagramElementsTool,
  GQLTool,
} from '../connector/useConnector.types';

const getToolSectionsQuery = gql`
  query getToolSections($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              palette(diagramElementId: $diagramElementId) {
                tools {
                  __typename
                  ... on SingleClickOnTwoDiagramElementsTool {
                    candidates {
                      sources {
                        id
                      }
                      targets {
                        id
                      }
                    }
                  }
                }
                toolSections {
                  tools {
                    __typename
                    ... on SingleClickOnTwoDiagramElementsTool {
                      candidates {
                        sources {
                          id
                        }
                        targets {
                          id
                        }
                      }
                    }
                  }
                }
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
const isSingleClickOnTwoDiagramElementsTool = (tool: GQLTool): tool is GQLSingleClickOnTwoDiagramElementsTool =>
  tool.__typename === 'SingleClickOnTwoDiagramElementsTool';

export const useConnectionCandidatesQuery = (
  editingContextId: string,
  diagramId: string,
  nodeId: string
): GQLNodeDescription[] | null => {
  const { addErrorMessage } = useMultiToast();
  const { selection } = useSelection();

  const { data, error } = useQuery<GQLGetToolSectionsData, GQLGetToolSectionsVariables>(getToolSectionsQuery, {
    variables: {
      editingContextId,
      diagramId,
      diagramElementId: nodeId,
    },
    skip: selection.entries.length > 1,
  });
  const diagramDescription: GQLRepresentationDescription | null =
    data?.viewer.editingContext.representation.description ?? null;

  const nodeCandidates: GQLNodeDescription[] = useMemo(() => {
    const candidates: GQLNodeDescription[] = [];
    if (diagramDescription && isDiagramDescription(diagramDescription)) {
      diagramDescription.palette.tools.filter(isSingleClickOnTwoDiagramElementsTool).forEach((tool) => {
        tool.candidates.forEach((candidate) => candidates.push(...candidate.targets));
      });
      diagramDescription.palette.toolSections.forEach((toolSection) => {
        toolSection.tools.filter(isSingleClickOnTwoDiagramElementsTool).forEach((tool) => {
          tool.candidates.forEach((candidate) => candidates.push(...candidate.targets));
        });
      });
    }

    return candidates;
  }, [diagramDescription]);

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return nodeCandidates;
};
