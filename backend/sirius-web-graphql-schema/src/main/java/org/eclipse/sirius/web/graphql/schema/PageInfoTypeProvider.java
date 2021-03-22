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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.springframework.stereotype.Service;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;

/**
 * This class is used to create the definition of the PageInfo type.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type PageInfo {
 *   hasPreviousPage: boolean!
 *   hasNextPage: boolean!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@Service
public class PageInfoTypeProvider implements ITypeProvider {
    public static final String TYPE = "PageInfo"; //$NON-NLS-1$

    public static final String HAS_PREVIOUS_PAGE_FIELD = "hasPreviousPage"; //$NON-NLS-1$

    public static final String HAS_NEXT_PAGE_FIELD = "hasNextPage"; //$NON-NLS-1$

    public static final String START_CURSOR_FIELD = "startCursor"; //$NON-NLS-1$

    public static final String END_CURSOR_FIELD = "endCursor"; //$NON-NLS-1$

    @Override
    public Set<GraphQLType> getTypes() {
        LinkedHashSet<GraphQLType> types = new LinkedHashSet<>();
        // @formatter:off
        var pageInfoType = GraphQLObjectType.newObject()
                .name(TYPE)
                .field(this.getHasPreviousPage())
                .field(this.getHasNextPage())
                .field(this.getStartCursor())
                .field(this.getEndCursor())
                .build();
        // @formatter:on

        types.add(pageInfoType);
        return types;
    }

    private GraphQLFieldDefinition getHasPreviousPage() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(HAS_NEXT_PAGE_FIELD)
                .type(new GraphQLNonNull(Scalars.GraphQLBoolean))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getHasNextPage() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(HAS_PREVIOUS_PAGE_FIELD)
                .type(new GraphQLNonNull(Scalars.GraphQLBoolean))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getStartCursor() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(START_CURSOR_FIELD)
                .type(Scalars.GraphQLString)
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getEndCursor() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(END_CURSOR_FIELD)
                .type(Scalars.GraphQLString)
                .build();
        // @formatter:on
    }
}
