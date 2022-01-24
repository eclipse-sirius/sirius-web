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

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLOutputType;
import graphql.schema.GraphQLTypeReference;

/**
 * Service used to create a GraphQL field definition from a given method.
 *
 * @author sbegaudeau
 */
public class GraphQLFieldDefinitionProvider {

    private final GraphQLNameProvider graphQLNameProvider = new GraphQLNameProvider();

    private final GraphQLScalarConverter graphQLScalarConverter = new GraphQLScalarConverter();

    public GraphQLFieldDefinition getField(Method method) {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(this.graphQLNameProvider.getName(method))
                .type(this.getOutputType(method))
                .build();
        // @formatter:on
    }

    private GraphQLOutputType getOutputType(Method method) {
        GraphQLOutputType outputType = null;
        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            outputType = this.getCollectionOutputType(method).orElse(null);
        } else if (method.isAnnotationPresent(org.eclipse.sirius.web.annotations.graphql.GraphQLID.class)) {
            outputType = Scalars.GraphQLID;
        } else {
            outputType = this.getRawOutputType(method.getReturnType());
        }

        if (method.isAnnotationPresent(org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull.class)) {
            outputType = new GraphQLNonNull(outputType);
        }

        return outputType;
    }

    private Optional<GraphQLOutputType> getCollectionOutputType(Method method) {
        // @formatter:off
        return Optional.of(method.getAnnotatedReturnType())
                .filter(AnnotatedParameterizedType.class::isInstance)
                .map(AnnotatedParameterizedType.class::cast)
                .flatMap(this::getFirstAnnotatedType)
                .flatMap(this::getOutputType);
        // @formatter:on
    }

    /**
     * Returns the first potentially annotated type of a generic type.
     * <p>
     * Let's consider the following example:
     * </p>
     *
     * <pre>
     * List<@NonNull String> names;
     * </pre>
     * <p>
     * This method would be used to return <code>@NonNull String</code>. In the case of multiple annotated types in a
     * generic signature, it would only return the first one.
     * </p>
     *
     * @return
     */
    private Optional<AnnotatedType> getFirstAnnotatedType(AnnotatedParameterizedType type) {
        return Arrays.asList(type.getAnnotatedActualTypeArguments()).stream().findFirst();
    }

    /**
     * Uses the given annotated type to compute the GraphQL output type.
     * <p>
     * Let's consider the following examples:
     * </p>
     *
     * <pre>
     * Collection<@NonNull String> names;
     *
     * Collection<String> names;
     *
     * <pre>
     *
     * <p>
     * The first example will be used to create a GraphQL field with the type [String!] while the second one will be
     * used to create the type [String].
     * </p>
     *
     * @param annotatedType
     *            The Java annotated type from the generic part of the signature.
     * @return The GraphQL output type matching the given type
     */
    private Optional<GraphQLOutputType> getOutputType(AnnotatedType annotatedType) {
        Optional<GraphQLOutputType> optionalOutputType = Optional.empty();
        Type type = annotatedType.getType();
        if (type instanceof Class<?>) {
            Class<?> genericType = (Class<?>) type;
            GraphQLOutputType genericOutputType = this.getRawOutputType(genericType);

            if (annotatedType.isAnnotationPresent(org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull.class)) {
                optionalOutputType = Optional.of(new GraphQLList(new GraphQLNonNull(genericOutputType)));
            } else {
                optionalOutputType = Optional.of(new GraphQLList(genericOutputType));
            }

        }
        return optionalOutputType;
    }

    private GraphQLOutputType getRawOutputType(Class<?> type) {
        return this.graphQLScalarConverter.getScalar(type).map(GraphQLOutputType.class::cast).orElseGet(() -> {
            String name = type.getSimpleName();

            if (type.isAnnotationPresent(org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType.class)) {
                name = this.graphQLNameProvider.getObjectTypeName(type);
            } else if (type.isAnnotationPresent(org.eclipse.sirius.web.annotations.graphql.GraphQLInterfaceType.class)) {
                name = this.graphQLNameProvider.getInterfaceTypeName(type);
            }

            return new GraphQLTypeReference(name);
        });
    }

}
