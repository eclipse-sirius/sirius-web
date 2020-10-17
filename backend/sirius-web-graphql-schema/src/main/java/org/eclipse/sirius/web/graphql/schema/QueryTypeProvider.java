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
package org.eclipse.sirius.web.graphql.schema;

import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.schema.IQueryTypeProvider;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;

/**
 * This class is used to create the definition of the Query type.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Query {
 *   viewer: Viewer!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@Service
public class QueryTypeProvider implements IQueryTypeProvider {
    public static final String TYPE = "Query"; //$NON-NLS-1$

    public static final String VIEWER_FIELD = "viewer"; //$NON-NLS-1$

    @Override
    public GraphQLObjectType getType() {
        // @formatter:off
        return GraphQLObjectType.newObject()
                .name(TYPE)
                .field(this.getViewerField())
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getViewerField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(VIEWER_FIELD)
                .type(new GraphQLNonNull(new GraphQLTypeReference(ViewerTypeProvider.TYPE)))
                .build();
        // @formatter:on
    }

    @Override
    public Set<GraphQLType> getAdditionalTypes() {
        return Set.of();
    }
}
