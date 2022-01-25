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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.annotations.graphql.GraphQLField;

/**
 * Service used to discover all the methods of a class which should be considered as GraphQL fields. For that this
 * service will look for all methods annotated with @GraphQLField on the given class and its superclasses.
 *
 * @author sbegaudeau
 */
public class GraphQLFieldProvider {
    public List<Method> getFields(Class<?> aClass) {
        List<Method> annotatedMethods = new ArrayList<>();

        Class<?> classToConsider = aClass;
        while (classToConsider != null) {
            // @formatter:off
            List<Method> methods = Arrays.asList(classToConsider.getDeclaredMethods()).stream()
                    .filter(method -> method.isAnnotationPresent(GraphQLField.class))
                    .collect(Collectors.toList());
            // @formatter:on
            annotatedMethods.addAll(methods);

            classToConsider = classToConsider.getSuperclass();
        }

        return annotatedMethods;
    }
}
