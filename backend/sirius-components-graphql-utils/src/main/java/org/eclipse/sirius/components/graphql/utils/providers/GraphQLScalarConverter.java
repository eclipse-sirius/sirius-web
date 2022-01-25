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

import java.util.Map;
import java.util.Optional;

import graphql.Scalars;
import graphql.schema.GraphQLScalarType;

/**
 * This class is used to retrieve convert, if possible, a Java class to a GraphQL scalar.
 *
 * @author sbegaudeau
 */
public class GraphQLScalarConverter {
    // @formatter:off
    private static final Map<String, GraphQLScalarType> CLASS_NAME_TO_SCALAR_TYPE = Map.ofEntries(
        Map.entry(String.class.getName(), Scalars.GraphQLString),
        Map.entry(Boolean.class.getName(), Scalars.GraphQLBoolean),
        Map.entry(Boolean.TYPE.getName(), Scalars.GraphQLBoolean),
        Map.entry(Integer.class.getName(), Scalars.GraphQLInt),
        Map.entry(Integer.TYPE.getName(), Scalars.GraphQLInt),
        Map.entry(Float.class.getName(), Scalars.GraphQLFloat),
        Map.entry(Float.TYPE.getName(), Scalars.GraphQLFloat),
        Map.entry(Double.class.getName(), Scalars.GraphQLFloat),
        Map.entry(Double.TYPE.getName(), Scalars.GraphQLFloat)
    );
    // @formatter:on

    public Optional<GraphQLScalarType> getScalar(Class<?> aClass) {
        return Optional.ofNullable(CLASS_NAME_TO_SCALAR_TYPE.get(aClass.getName()));
    }
}
