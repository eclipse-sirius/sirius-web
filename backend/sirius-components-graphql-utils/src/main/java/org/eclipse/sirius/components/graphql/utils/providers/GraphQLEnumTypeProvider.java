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

import java.lang.reflect.Field;
import java.util.Arrays;

import graphql.schema.GraphQLEnumType;

/**
 * Service used to create a GraphQLEnumType from a Java enumeration annotated with @GraphQLEnumType. Here is an example
 * of such class:
 *
 * <pre>
 * &#64;GraphQLEnumType
 * public enum Status {
 *     ON, OFF
 * }
 * </pre>
 *
 * Using this example, the following result would be produced for the GraphQL schema:
 *
 * <pre>
 * enum Status {
 *     ON, OFF
 * }
 * </pre>
 *
 * This class does not register the GraphQL enum created in a schema or produce any side-effect.
 *
 * @author sbegaudeau
 */
public class GraphQLEnumTypeProvider {

    private final GraphQLNameProvider graphQLNameProvider = new GraphQLNameProvider();

    public GraphQLEnumType getType(Class<?> javaClass) {
        String name = this.graphQLNameProvider.getEnumTypeName(javaClass);

        GraphQLEnumType.Builder builder = GraphQLEnumType.newEnum().name(name);

        // @formatter:off
        Arrays.stream(javaClass.getDeclaredFields())
            .filter(Field::isEnumConstant)
            .forEach(field -> builder.value(field.getName(), field.getName()));
        // @formatter:on

        GraphQLEnumType graphQLEnumType = builder.build();
        return graphQLEnumType;
    }
}
