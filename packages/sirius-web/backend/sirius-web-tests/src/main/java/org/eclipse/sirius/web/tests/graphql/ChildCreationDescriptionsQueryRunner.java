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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get the child creation descriptions from the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class ChildCreationDescriptionsQueryRunner implements IQueryRunner {

    private static final String CHILD_CREATION_DESCRIPTIONS = """
            query getChildCreationDescriptions($editingContextId: ID!, $kind: ID!, $referenceKind: String) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  childCreationDescriptions(kind: $kind, referenceKind: $referenceKind) {
                    id
                    label
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ChildCreationDescriptionsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(CHILD_CREATION_DESCRIPTIONS, variables);
    }
}
