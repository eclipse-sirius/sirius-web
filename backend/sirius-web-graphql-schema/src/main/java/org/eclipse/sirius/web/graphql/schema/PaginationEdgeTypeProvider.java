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
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;

/**
 * This class is used to create the definition of a XxxYyyEdge type.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type XxxYyyEdge {
 *   node: Yyy!
 * }
 * </pre>
 *
 * <p>
 * With XxxYyy being the name and Yyy being the target name.
 * </p>
 *
 * @author sbegaudeau
 */
public class PaginationEdgeTypeProvider {
    public static final String NODE_FIELD = "node"; //$NON-NLS-1$

    private String name;

    private String targetName;

    public PaginationEdgeTypeProvider(String name, String targetName) {
        this.name = name;
        this.targetName = targetName;
    }

    public GraphQLObjectType getType() {
        // @formatter:off
        return GraphQLObjectType.newObject()
                .name(this.name)
                .field(this.getNodeField())
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getNodeField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(NODE_FIELD)
                .type(new GraphQLNonNull(new GraphQLTypeReference(this.targetName)))
                .build();
        // @formatter:on
    }
}
