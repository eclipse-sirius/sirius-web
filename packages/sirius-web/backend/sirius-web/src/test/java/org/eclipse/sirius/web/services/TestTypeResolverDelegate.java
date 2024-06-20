/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.services;

import org.eclipse.sirius.components.graphql.api.ITypeResolverDelegate;
import org.springframework.stereotype.Service;

import graphql.TypeResolutionEnvironment;
import graphql.schema.GraphQLObjectType;

/**
 * Used during tests to return a GraphQL object type for unknown objects.
 *
 * @author sbegaudeau
 */
@Service
public class TestTypeResolverDelegate implements ITypeResolverDelegate {
    @Override
    public GraphQLObjectType getType(TypeResolutionEnvironment environment) {
        return environment.getSchema().getObjectType("Diagram");
    }
}
