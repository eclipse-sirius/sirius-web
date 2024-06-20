/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.graphql.api;

import java.util.List;
import java.util.Objects;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.TypeResolver;

/**
 * The reflective type resolver will look for the GraphQL object type with the name of the class of the given object.
 *
 * @author sbegaudeau
 */
public class ReflectiveTypeResolver implements TypeResolver {

    private final List<ITypeResolverDelegate> typeResolverDelegates;

    public ReflectiveTypeResolver(List<ITypeResolverDelegate> typeResolverDelegates) {
        this.typeResolverDelegates = Objects.requireNonNull(typeResolverDelegates);
    }

    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment environment) {
        GraphQLSchema graphQLSchema = environment.getSchema();
        Object object = environment.getObject();
        var graphQLObjectType = graphQLSchema.getObjectType(object.getClass().getSimpleName());

        if (graphQLObjectType == null) {
            graphQLObjectType = this.typeResolverDelegates.stream()
                    .map(typeResolverDelegate -> typeResolverDelegate.getType(environment))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }

        return graphQLObjectType;
    }

}
