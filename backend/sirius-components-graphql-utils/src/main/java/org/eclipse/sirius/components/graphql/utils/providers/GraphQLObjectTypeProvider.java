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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeReference;

/**
 * Service used to create a GraphQLObjectType from a Java class annotated with @GraphQLObjectType. Here is an example of
 * such class:
 *
 * <pre>
 * &#64;GraphQLObjectType
 * public final class Machine {
 *     private String id;
 *
 *     private boolean active;
 *
 *     private List<String> notes;
 *
 *     &#64;GraphQLID
 *     &#64;GraphQLField
 *     &#64;GraphQLNonNull
 *     public String getId() {
 *         return this.id;
 *     }
 *
 *     &#64;GraphQLField
 *     &#64;GraphQLNonNull
 *     public boolean isActive() {
 *         return this.active;
 *     }
 *
 *     &#64;GraphQLField
 *     &#64;GraphQLNonNull
 *     public List<&#64;GraphQLNonNull String> getNotes() {
 *         return this.notes;
 *     }
 * }
 * </pre>
 *
 * Using this example, the following result would be produced for the GraphQL schema:
 *
 * <pre>
 * type Machine {
 *     id: ID!
 *     active: Boolean!
 *     notes: [String!]!
 * }
 * </pre>
 *
 * This class does not register the GraphQL object type created in a schema or produce any side-effect.
 *
 * @author sbegaudeau
 */
public class GraphQLObjectTypeProvider {

    private final GraphQLFieldProvider graphQLFieldProvider = new GraphQLFieldProvider();

    private final GraphQLFieldDefinitionProvider graphQLFieldDefinitionProvider = new GraphQLFieldDefinitionProvider();

    private final GraphQLNameProvider graphQLNameProvider = new GraphQLNameProvider();

    public GraphQLObjectType getType(Class<?> javaClass) {
        // @formatter:off
        List<GraphQLFieldDefinition> fieldDefinitions = this.graphQLFieldProvider.getFields(javaClass).stream()
                .map(this.graphQLFieldDefinitionProvider::getField)
                .collect(Collectors.toList());
        // @formatter:on

        String name = this.graphQLNameProvider.getObjectTypeName(javaClass);

        List<GraphQLTypeReference> interfaces = new ArrayList<>();
        Class<?> superClass = javaClass.getSuperclass();
        if (superClass.isAnnotationPresent(org.eclipse.sirius.components.annotations.graphql.GraphQLInterfaceType.class)) {
            interfaces.add(new GraphQLTypeReference(this.graphQLNameProvider.getInterfaceTypeName(superClass)));
        }
        Arrays.stream(javaClass.getInterfaces()).forEach(anInterface -> {
            if (anInterface.isAnnotationPresent(org.eclipse.sirius.components.annotations.graphql.GraphQLInterfaceType.class)) {
                interfaces.add(new GraphQLTypeReference(this.graphQLNameProvider.getInterfaceTypeName(anInterface)));
            }
        });

        // @formatter:off
        var graphQLObjectType = GraphQLObjectType.newObject()
                .name(name)
                .fields(fieldDefinitions)
                .withInterfaces(interfaces.toArray(new GraphQLTypeReference[interfaces.size()]))
                .build();
        // @formatter:on

        return graphQLObjectType;
    }
}
