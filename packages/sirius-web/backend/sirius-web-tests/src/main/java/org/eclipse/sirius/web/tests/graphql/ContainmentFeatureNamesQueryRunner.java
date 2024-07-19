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

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get the containment feature from the GraphQL API.
 *
 * @author frouene
 */
@Service
public class ContainmentFeatureNamesQueryRunner implements IQueryRunner {

    private static final String CONTAINMENT_FEATURE_NAME_QUERY = """
            query getContainmentFeatureNames($editingContextId: ID!, $containerId: ID!, $containedObjectId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  containmentFeatureNames(containerId: $containerId, containedObjectId: $containedObjectId)
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ContainmentFeatureNamesQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(CONTAINMENT_FEATURE_NAME_QUERY, variables);
    }
}
