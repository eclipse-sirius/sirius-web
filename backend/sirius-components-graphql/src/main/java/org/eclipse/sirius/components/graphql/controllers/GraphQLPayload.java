/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.graphql.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The GraphQL request sent by a client.
 * <p>
 * A GraphQL query sent to the server should use the following data structure. This request should at the very least
 * contain a valid GraphQL query. Both the fields variables and operationName can be missing if they are not used.
 * </p>
 *
 * <pre>
 * {
 *   "query": "...",
 *   "variables": {
 *     "key": "value"
 *   },
 *   "extensions": { ... },
 *   "operationName": "..."
 * }
 * </pre>
 *
 * <p>
 * In this class, we are making sure that the variables field cannot be initialized to "null" in order to have at least
 * an empty map.
 * </p>
 *
 * @author sbegaudeau
 */
@Immutable
public final class GraphQLPayload {
    private String query;

    private Map<String, Object> variables = new HashMap<>();

    private Map<String, Object> extensions = new HashMap<>();

    private String operationName;

    public String getQuery() {
        return this.query;
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public Map<String, Object> getExtensions() {
        return this.extensions;
    }

    public String getOperationName() {
        return this.operationName;
    }

    public static Builder newGraphQLPayload() {
        return new Builder();
    }

    /**
     * The builder of the payload.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String query;

        private Map<String, Object> variables = new HashMap<>();

        private Map<String, Object> extensions = new HashMap<>();

        private String operationName;

        private Builder() {
            // Prevent instantiation
        }

        public Builder query(String query) {
            this.query = Objects.requireNonNull(query);
            return this;
        }

        public Builder variables(Map<String, Object> variables) {
            this.variables = Objects.requireNonNull(variables);
            return this;
        }

        public Builder extensions(Map<String, Object> extensions) {
            this.extensions = Objects.requireNonNull(extensions);
            return this;
        }

        public Builder operationName(String operationName) {
            this.operationName = Objects.requireNonNull(operationName);
            return this;
        }

        public GraphQLPayload build() {
            GraphQLPayload graphQLPayload = new GraphQLPayload();
            graphQLPayload.query = Objects.requireNonNull(this.query);
            graphQLPayload.variables = Objects.requireNonNull(this.variables);
            graphQLPayload.extensions = Objects.requireNonNull(this.extensions);
            graphQLPayload.operationName = this.operationName;
            return graphQLPayload;
        }
    }
}
