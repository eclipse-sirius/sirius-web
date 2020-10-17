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
package org.eclipse.sirius.web.graphql.utils.typeresolvers;

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

    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment environment) {
        GraphQLSchema graphQLSchema = environment.getSchema();
        Object object = environment.getObject();
        return graphQLSchema.getObjectType(object.getClass().getSimpleName());
    }

}
