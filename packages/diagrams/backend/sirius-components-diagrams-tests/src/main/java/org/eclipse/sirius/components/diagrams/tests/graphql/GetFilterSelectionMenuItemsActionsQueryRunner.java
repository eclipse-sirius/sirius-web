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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve the list of menu items of the filter menu in the diagram toolbar that can be executed using the GraphQL API.
 *
 * @author mcharfadi
 */
@Service
public class GetFilterSelectionMenuItemsActionsQueryRunner implements IQueryRunner {

    private static final String GET_FILTER_SELECTION_MENU_ITEMS = """
              query getDiagramDescription($editingContextId: ID!, $representationId: ID!, $diagramElementIds: [ID!]!) {
                viewer {
                  editingContext(editingContextId: $editingContextId) {
                    representation(representationId: $representationId) {
                      description {
                        ... on DiagramDescription {
                          id
                          toolbar {
                            filterSelectionMenuItems(diagramElementIds: $diagramElementIds) {
                              id
                              label
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

    public GetFilterSelectionMenuItemsActionsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(GET_FILTER_SELECTION_MENU_ITEMS, variables);
    }
}
