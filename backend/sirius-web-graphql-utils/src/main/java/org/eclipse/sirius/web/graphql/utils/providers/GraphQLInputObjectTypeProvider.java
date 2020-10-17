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
package org.eclipse.sirius.web.graphql.utils.providers;

import java.util.List;
import java.util.stream.Collectors;

import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;

/**
 * Service class used to create a GraphQLInputObjectType from a Java class annotated with @GraphQLInputObjectType. Here
 * is an example of such class:
 *
 * <pre>
 * &#64;GraphQLInputObjectType
 * public class Item {
 *
 *     private String name;
 *
 *     &#64;GraphQLField
 *     &#64;GraphQLNonNull
 *     public String getName() {
 *         return this.name;
 *     }
 * }
 * </pre>
 *
 * Using this example, the following result would be produced for the GraphQL schema:
 *
 * <pre>
 * input Item {
 *     name: String!
 * }
 * </pre>
 *
 * This class does not register the GraphQL input object type created in a schema or produce any side-effect.
 *
 * @author sbegaudeau
 */
public class GraphQLInputObjectTypeProvider {

    private final GraphQLFieldProvider graphQLFieldProvider = new GraphQLFieldProvider();

    private final GraphQLInputObjectFieldProvider graphQLInputObjectFieldProvider = new GraphQLInputObjectFieldProvider();

    private final GraphQLNameProvider graphQLNameProvider = new GraphQLNameProvider();

    public GraphQLInputObjectType getType(Class<?> javaClass) {
        // @formatter:off
        List<GraphQLInputObjectField> fieldDefinitions = this.graphQLFieldProvider.getFields(javaClass).stream()
                .map(this.graphQLInputObjectFieldProvider::getField)
                .collect(Collectors.toList());
        // @formatter:on

        String name = this.graphQLNameProvider.getInputObjectTypeName(javaClass);

        // @formatter:off
        var graphQLInputObjectType = GraphQLInputObjectType.newInputObject()
                .name(name)
                .fields(fieldDefinitions)
                .build();
        // @formatter:on
        return graphQLInputObjectType;
    }
}
