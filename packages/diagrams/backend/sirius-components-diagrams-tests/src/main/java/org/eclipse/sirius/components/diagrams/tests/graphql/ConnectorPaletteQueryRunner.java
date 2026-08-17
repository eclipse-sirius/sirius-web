/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

/**
 * Used to retrieve the connector palette.
 *
 * @author mcharfadi
 */
@Service
public class ConnectorPaletteQueryRunner implements IQueryRunner {

    private static final String PALETTE_QUERY = """
            query getConnectorPalette($editingContextId: ID!, $representationId: ID!, $sourceDiagramElementId: ID!, $targetDiagramElementId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on DiagramDescription {
                        connectorPalette(sourceDiagramElementId: $sourceDiagramElementId, targetDiagramElementId: $targetDiagramElementId) {
                          id
                          quickAccessTools {
                            ...ToolFields
                          }
                          paletteEntries {
                            ...ToolFields
                            ... on ToolSection {
                              id
                              label
                              iconURL
                              tools {
                                ...ToolFields
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

            fragment ToolFields on Tool {
              __typename
              id
              label
              iconURL
              ... on SingleClickOnTwoDiagramElementsTool {
                dialogDescriptionId
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ConnectorPaletteQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(PALETTE_QUERY, variables);
    }
}
