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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get an impact analysis report from a diagram.
 *
 * @author frouene
 * @since v2026.12.0
 */
@Service
public class DiagramImpactAnalysisReportQueryRunner implements IQueryRunner {

    public static final String DIAGRAM_IMPACT_ANALYSIS_REPORT_QUERY = """
            query getImpactAnalysisReport(
              $editingContextId: ID!
              $representationId: ID!
              $toolId: ID!
              $diagramElementId: ID!
              $variables: [ToolVariable!]!
            ) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on DiagramDescription {
                        diagramImpactAnalysisReport(toolId: $toolId, diagramElementId: $diagramElementId, variables: $variables) {
                          nbElementDeleted
                          nbElementModified
                          nbElementCreated
                          additionalReports
                          impactTree {
                            id
                            nodes {
                              id
                              parentId
                              label {
                                styledStringFragments {
                                  text
                                }
                              }
                              iconURLs
                              endIconsURLs
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DiagramImpactAnalysisReportQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(DIAGRAM_IMPACT_ANALYSIS_REPORT_QUERY, variables);
    }
}
