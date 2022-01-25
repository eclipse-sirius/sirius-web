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

import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.sirius.components.graphql.utils.types.UploadScalarType;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputType;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLTypeReference;

/**
 * Service used to create a GraphQL input field definition from a given method.
 *
 * @author sbegaudeau
 */
public class GraphQLInputObjectFieldProvider {

    private final GraphQLNameProvider graphQLNameProvider = new GraphQLNameProvider();

    private final GraphQLScalarConverter graphQLScalarConverter = new GraphQLScalarConverter();

    public GraphQLInputObjectField getField(Method method) {
        // @formatter:off
        return GraphQLInputObjectField.newInputObjectField()
                .name(this.graphQLNameProvider.getName(method))
                .type(this.getInputType(method))
                .build();
        // @formatter:on
    }

    private GraphQLInputType getInputType(Method method) {
        GraphQLInputType inputType = null;

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            inputType = this.getCollectionInputType(method).orElse(null);
        } else if (method.isAnnotationPresent(org.eclipse.sirius.components.annotations.graphql.GraphQLID.class)) {
            inputType = Scalars.GraphQLID;
        } else if (method.isAnnotationPresent(org.eclipse.sirius.components.annotations.graphql.GraphQLUpload.class)) {
            inputType = UploadScalarType.INSTANCE;
        } else {
            inputType = this.getRawInputType(method.getReturnType());
        }

        if (method.isAnnotationPresent(org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull.class)) {
            inputType = new GraphQLNonNull(inputType);
        }

        return inputType;
    }

    private Optional<GraphQLInputType> getCollectionInputType(Method method) {
        // @formatter:off
        return Optional.of(method.getAnnotatedReturnType())
                .filter(AnnotatedParameterizedType.class::isInstance)
                .map(AnnotatedParameterizedType.class::cast)
                .flatMap(this::getFirstAnnotatedType)
                .flatMap(this::getInputType);
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
     * Uses the given annotated type to compute the GraphQL input type.
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
     * @return The GraphQL intput type matching the given type
     */
    private Optional<GraphQLInputType> getInputType(AnnotatedType annotatedType) {
        Optional<GraphQLInputType> optionalInputType = Optional.empty();
        Type type = annotatedType.getType();
        if (type instanceof Class<?>) {
            Class<?> genericType = (Class<?>) type;
            GraphQLInputType genericInputType = this.getRawInputType(genericType);

            if (annotatedType.isAnnotationPresent(org.eclipse.sirius.components.annotations.graphql.GraphQLID.class)) {
                genericInputType = Scalars.GraphQLID;
            }

            if (annotatedType.isAnnotationPresent(org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull.class)) {
                optionalInputType = Optional.of(new GraphQLList(new GraphQLNonNull(genericInputType)));
            } else {
                optionalInputType = Optional.of(new GraphQLList(genericInputType));
            }
        }
        return optionalInputType;
    }

    private GraphQLInputType getRawInputType(Class<?> type) {
        return this.graphQLScalarConverter.getScalar(type).map(GraphQLInputType.class::cast).orElseGet(() -> {
            String name = type.getSimpleName();

            org.eclipse.sirius.components.annotations.graphql.GraphQLInputObjectType inputObjectTypeAnnotation = type.getAnnotation(org.eclipse.sirius.components.annotations.graphql.GraphQLInputObjectType.class);
            if (inputObjectTypeAnnotation != null && !inputObjectTypeAnnotation.name().isBlank()) {
                name = inputObjectTypeAnnotation.name();
            }

            return new GraphQLTypeReference(name);
        });
    }
}
