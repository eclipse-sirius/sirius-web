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
package org.eclipse.sirius.components.widget.reference.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to retrieve root creation descriptions for a reference widget.
 *
 * @author tgiraudet
 */
@Service
public class ReferenceWidgetRootCreationDescriptionsQueryRunner implements IQueryRunner {

    public static final String QUERY = """
            query getReferenceWidgetRootCreationDescriptions($editingContextId: ID!, $representationId: ID!, $domainId: ID!, $referenceKind: String, $descriptionId: String!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  referenceWidgetRootCreationDescriptions(
                    representationId: $representationId,
                    domainId: $domainId,
                    referenceKind: $referenceKind,
                    descriptionId: $descriptionId
                  ) {
                    id
                    label
                    iconURL
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ReferenceWidgetRootCreationDescriptionsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(QUERY, variables);
    }
}
