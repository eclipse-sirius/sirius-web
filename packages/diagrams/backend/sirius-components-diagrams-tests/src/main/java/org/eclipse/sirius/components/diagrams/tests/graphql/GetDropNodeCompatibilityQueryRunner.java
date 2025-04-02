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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * Used to request the compatibility of the drop node tool.
 *
 * @author sbegaudeau
 */
@Service
public class GetDropNodeCompatibilityQueryRunner implements IQueryRunner {

    private static final String GET_DROP_NODE_COMPATIBILITY = """
            query getDropNodeCompatibility($editingContextId: ID!, $representationId: ID!) {
              viewer {
                editingContext(editingContextId: $editingContextId) {
                  representation(representationId: $representationId) {
                    description {
                      ... on DiagramDescription {
                        dropNodeCompatibility {
                          droppedNodeDescriptionId
                          droppableOnDiagram
                          droppableOnNodeTypes
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public GetDropNodeCompatibilityQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(GET_DROP_NODE_COMPATIBILITY, variables);
    }
}
