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
package org.eclipse.sirius.components.graphql.utils.providers;

import java.util.List;
import java.util.stream.Collectors;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInterfaceType;

/**
 * Service used to create a GraphQLInterfaceType from a Java interface annotated with @GraphQLInterfaceType. Here is an
 * example of such class:
 *
 * <pre>
 * &#64;GraphQLInterfaceType(name = "Data")
 * public interface IData {
 *     &#64;GraphQLField
 *     &#64;GraphQLNonNull
 *     String getName();
 * }
 * </pre>
 *
 * Using this example, the following result would be produced for the GraphQL schema:
 *
 * <pre>
 * interface Data {
 *     name: String!
 * }
 * </pre>
 *
 * This class does not register the GraphQL interface type created in a schema or produce any side-effect.
 *
 * @author sbegaudeau
 */
public class GraphQLInterfaceTypeProvider {

    private final GraphQLFieldProvider graphQLFieldProvider = new GraphQLFieldProvider();

    private final GraphQLFieldDefinitionProvider graphQLFieldDefinitionProvider = new GraphQLFieldDefinitionProvider();

    private final GraphQLNameProvider graphQLNameProvider = new GraphQLNameProvider();

    public GraphQLInterfaceType getType(Class<?> javaClass) {
        // @formatter:off
        List<GraphQLFieldDefinition> fieldDefinitions = this.graphQLFieldProvider.getFields(javaClass).stream()
                .map(this.graphQLFieldDefinitionProvider::getField)
                .collect(Collectors.toList());
        // @formatter:on

        String name = this.graphQLNameProvider.getInterfaceTypeName(javaClass);

        // @formatter:off
        var graphQLInterfaceType = GraphQLInterfaceType.newInterface()
                .name(name)
                .fields(fieldDefinitions)
                .build();
        // @formatter:on

        return graphQLInterfaceType;
    }
}
