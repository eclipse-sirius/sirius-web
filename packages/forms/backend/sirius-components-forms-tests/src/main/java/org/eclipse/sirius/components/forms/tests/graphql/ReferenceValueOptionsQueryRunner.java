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
 * Used to retrieve the options for the reference value.
 *
 * @author sbegaudeau
 */
@Service
public class ReferenceValueOptionsQueryRunner implements IQueryRunner {

    private static final String REFERENCE_VALUE_OPTIONS_QUERY = """
            query getReferenceValueOptions($editingContextId: ID!, $representationId: ID!, $referenceWidgetId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on FormDescription {
                        referenceValueOptions(referenceWidgetId: $referenceWidgetId) {
                          id
                          label
                          kind
                          iconURL
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ReferenceValueOptionsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(REFERENCE_VALUE_OPTIONS_QUERY, variables);
    }
}
