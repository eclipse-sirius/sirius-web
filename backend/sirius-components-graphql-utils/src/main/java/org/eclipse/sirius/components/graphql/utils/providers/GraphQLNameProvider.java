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

import java.lang.reflect.Method;

/**
 * Service used to compute the name of a GraphQL object type, interface type, input type or field.
 *
 * @author sbegaudeau
 */
public class GraphQLNameProvider {

    private static final String GET = "get"; //$NON-NLS-1$

    private static final String IS = "is"; //$NON-NLS-1$

    private static final String MUTATION_PREFIX = "Mutation"; //$NON-NLS-1$

    private static final String SUBSCRIPTION_PREFIX = "Subscription"; //$NON-NLS-1$

    private static final String DATA_FETCHER_SUFFIX = "DataFetcher"; //$NON-NLS-1$

    private static final String PAYLOAD_SUFFIX = "Payload"; //$NON-NLS-1$

    public String getName(Method method) {
        String name = method.getName();
        if (name.startsWith(GET)) {
            name = name.substring(GET.length());
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        } else if (name.startsWith(IS)) {
            name = name.substring(IS.length());
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return name;
    }

    public String getEnumTypeName(Class<?> javaClass) {
        String name = javaClass.getSimpleName();

        var annotation = javaClass.getAnnotation(org.eclipse.sirius.components.annotations.graphql.GraphQLEnumType.class);
        if (annotation != null && !annotation.name().isBlank()) {
            name = annotation.name();
        }
        return name;
    }

    public String getObjectTypeName(Class<?> javaClass) {
        String name = javaClass.getSimpleName();

        var annotation = javaClass.getAnnotation(org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType.class);
        if (annotation != null && !annotation.name().isBlank()) {
            name = annotation.name();
        }
        return name;
    }

    public String getInterfaceTypeName(Class<?> javaClass) {
        String name = javaClass.getSimpleName();

        var annotation = javaClass.getAnnotation(org.eclipse.sirius.components.annotations.graphql.GraphQLInterfaceType.class);
        if (annotation != null && !annotation.name().isBlank()) {
            name = annotation.name();
        }
        return name;
    }

    public String getInputObjectTypeName(Class<?> javaClass) {
        String name = javaClass.getSimpleName();

        var annotation = javaClass.getAnnotation(org.eclipse.sirius.components.annotations.graphql.GraphQLInputObjectType.class);
        if (annotation != null && !annotation.name().isBlank()) {
            name = annotation.name();
        }
        return name;
    }

    public String getMutationUnionTypeName(Class<?> dataFetcherClass) {
        String fieldName = this.getMutationFieldName(dataFetcherClass);
        String unionTypeName = this.toUpperFirst(fieldName) + PAYLOAD_SUFFIX;
        return unionTypeName;
    }

    public String getSubscriptionUnionTypeName(Class<?> dataFetcherClass) {
        String fieldName = this.getSubscriptionFieldName(dataFetcherClass);
        String unionTypeName = this.toUpperFirst(fieldName) + PAYLOAD_SUFFIX;
        return unionTypeName;
    }

    public String getMutationFieldName(Class<?> dataFetcherClass) {
        String name = dataFetcherClass.getSimpleName();
        if (name.startsWith(MUTATION_PREFIX) && name.endsWith(DATA_FETCHER_SUFFIX) && name.length() > (MUTATION_PREFIX.length() + DATA_FETCHER_SUFFIX.length() + 1)) {
            name = name.substring(MUTATION_PREFIX.length(), name.length() - DATA_FETCHER_SUFFIX.length());
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return name;
    }

    public String getSubscriptionFieldName(Class<?> dataFetcherClass) {
        String name = dataFetcherClass.getSimpleName();
        if (name.startsWith(SUBSCRIPTION_PREFIX) && name.endsWith(DATA_FETCHER_SUFFIX) && name.length() > (SUBSCRIPTION_PREFIX.length() + DATA_FETCHER_SUFFIX.length() + 1)) {
            name = name.substring(SUBSCRIPTION_PREFIX.length(), name.length() - DATA_FETCHER_SUFFIX.length());
            name = name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        return name;
    }

    private String toUpperFirst(String name) {
        if (name.length() > 1) {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return name.toUpperCase();
    }
}
