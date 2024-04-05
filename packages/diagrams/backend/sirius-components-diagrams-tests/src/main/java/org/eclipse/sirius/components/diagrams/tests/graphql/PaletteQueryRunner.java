/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve the palette.
 *
 * @author sbegaudeau
 */
@Service
public class PaletteQueryRunner implements IQueryRunner {

    private static final String PALETTE_QUERY = """
            query getPalette($editingContextId: ID!, $representationId: ID!, $diagramElementId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on DiagramDescription {
                        palette(diagramElementId: $diagramElementId) {
                          id
                          tools {
                            ...ToolFields
                          }
                          toolSections {
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
            
            fragment ToolFields on Tool {
              __typename
              id
              label
              iconURL
              ... on SingleClickOnDiagramElementTool {
                targetDescriptions {
                  id
                }
                appliesToDiagramRoot
                selectionDescriptionId
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public PaletteQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(PALETTE_QUERY, variables);
    }
}
