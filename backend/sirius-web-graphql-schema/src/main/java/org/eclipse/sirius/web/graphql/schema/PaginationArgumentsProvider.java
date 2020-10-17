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

import java.util.ArrayList;
import java.util.List;

import graphql.Scalars;
import graphql.schema.GraphQLArgument;

/**
 * This class is used to create the arguments used for the pagination of the multi-valued references in the GraphQL
 * schema.
 * <p>
 * It will be used to add the following arguments to a field:
 * </p>
 *
 * <pre>
 * first: Int, after: String, last: Int, before: Int
 * </pre>
 *
 * @author sbegaudeau
 */
public class PaginationArgumentsProvider {

    /**
     * The name of the parameter used to indicate the number of elements to retrieve using a forward pagination
     * strategy.
     */
    public static final String FIRST = "first"; //$NON-NLS-1$

    /**
     * The name of the parameter used to indicate the number of elements to retrieve using a backward pagination
     * strategy.
     */
    public static final String LAST = "last"; //$NON-NLS-1$

    /**
     * The name of the parameter used to indicate after which element should the elements be retrieved using a forward
     * pagination strategy.
     */
    public static final String AFTER = "after"; //$NON-NLS-1$

    /**
     * The name of the parameter used to indicate before which element should the elements be retrieved using a forward
     * pagination strategy.
     */
    public static final String BEFORE = "before"; //$NON-NLS-1$

    public List<GraphQLArgument> getArguments() {
        // @formatter:off
        GraphQLArgument firstArgument = GraphQLArgument.newArgument()
                .name(FIRST)
                .type(Scalars.GraphQLInt)
                .build();

        GraphQLArgument afterArgument = GraphQLArgument.newArgument()
                .name(AFTER)
                .type(Scalars.GraphQLString)
                .build();

        GraphQLArgument lastArgument = GraphQLArgument.newArgument()
                .name(LAST)
                .type(Scalars.GraphQLInt)
                .build();

        GraphQLArgument beforeArgument = GraphQLArgument.newArgument()
                .name(BEFORE)
                .type(Scalars.GraphQLString)
                .build();
        // @formatter:on

        List<GraphQLArgument> arguments = new ArrayList<>();

        arguments.add(firstArgument);
        arguments.add(afterArgument);
        arguments.add(lastArgument);
        arguments.add(beforeArgument);

        return arguments;
    }

}
