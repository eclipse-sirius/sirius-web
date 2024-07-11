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
package org.eclipse.sirius.components.forms.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve the completion proposals of the widget.
 *
 * @author sbegaudeau
 */
@Service
public class CompletionProposalsQueryRunner implements IQueryRunner {

    private static final String COMPLETION_PROPOSALS_QUERY = """
            query completionProposals(
              $editingContextId: ID!
              $formId: ID!
              $widgetId: ID!
              $currentText: String!
              $cursorPosition: Int!
            ) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $formId) {
                    description {
                      ... on FormDescription {
                        completionProposals(widgetId: $widgetId, currentText: $currentText, cursorPosition: $cursorPosition) {
                          description
                          textToInsert
                          charsToReplace
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public CompletionProposalsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(COMPLETION_PROPOSALS_QUERY, variables);
    }
}
