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
 * Used to get the domains from the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class DomainsQueryRunner implements IQueryRunner {

    private static final String DOMAINS_QUERY = """
            query getDomains($editingContextId: ID!, $rootDomainsOnly: Boolean!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  domains(rootDomainsOnly: $rootDomainsOnly) {
                    id
                    label
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DomainsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(DOMAINS_QUERY, variables);
    }
}
