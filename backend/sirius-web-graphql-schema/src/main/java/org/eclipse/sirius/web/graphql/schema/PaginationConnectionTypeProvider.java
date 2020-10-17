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

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;

/**
 * This class is used to create the definition of a XxxYyyConnection type.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type XxxYyyConnection {
 *   edges: [XxxYyyEdge!]!
 *   pageInfo: PageInfo!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
public class PaginationConnectionTypeProvider {
    public static final String EDGES_FIELD = "edges"; //$NON-NLS-1$

    public static final String PAGE_INFO_FIELD = "pageInfo"; //$NON-NLS-1$

    private String name;

    private String edgeTypeName;

    public PaginationConnectionTypeProvider(String name, String edgeTypeName) {
        this.name = name;
        this.edgeTypeName = edgeTypeName;
    }

    public GraphQLObjectType getType() {
        // @formatter:off
        return GraphQLObjectType.newObject()
                .name(this.name)
                .field(this.getEdgesField())
                .field(this.getPageInfo())
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getEdgesField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(EDGES_FIELD)
                .type(new GraphQLNonNull(new GraphQLList(new GraphQLNonNull(new GraphQLTypeReference(this.edgeTypeName)))))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getPageInfo() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(PAGE_INFO_FIELD)
                .type(new GraphQLNonNull(new GraphQLTypeReference(PageInfoTypeProvider.TYPE)))
                .build();
        // @formatter:on
    }
}
