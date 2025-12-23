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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get the library of a list of objects from the GraphQL API.
 *
 * @author pcdavid
 */
@Service
public class ObjectsLabelsQueryRunner implements IQueryRunner {

    private static final String OBJECT_LABELS_QUERY = """
              query getObjectsLabels($editingContextId: ID!, $objectIds: [ID!]!) {
                viewer {
                  editingContext(editingContextId: $editingContextId) {
                    objects(objectIds: $objectIds) {
                      id
                      label
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ObjectsLabelsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(OBJECT_LABELS_QUERY, variables);
    }
}
