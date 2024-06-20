/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.web.graphql.configuration;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.api.ITypeResolverDelegate;
import org.eclipse.sirius.components.graphql.api.ReflectiveTypeResolver;
import org.springframework.stereotype.Service;

import graphql.schema.TypeResolver;
import graphql.schema.idl.InterfaceWiringEnvironment;
import graphql.schema.idl.UnionWiringEnvironment;
import graphql.schema.idl.WiringFactory;

/**
 * Wire the type resolvers for the type definitions.
 *
 * @author sbegaudeau
 */
@Service
public class GraphQLWiringFactory implements WiringFactory {

    private final List<ITypeResolverDelegate> typeResolverDelegates;

    public GraphQLWiringFactory(List<ITypeResolverDelegate> typeResolverDelegates) {
        this.typeResolverDelegates = Objects.requireNonNull(typeResolverDelegates);
    }

    @Override
    public boolean providesTypeResolver(InterfaceWiringEnvironment environment) {
        return true;
    }

    @Override
    public TypeResolver getTypeResolver(InterfaceWiringEnvironment environment) {
        return new ReflectiveTypeResolver(this.typeResolverDelegates);
    }

    @Override
    public boolean providesTypeResolver(UnionWiringEnvironment environment) {
        return true;
    }

    @Override
    public TypeResolver getTypeResolver(UnionWiringEnvironment environment) {
        return new ReflectiveTypeResolver(this.typeResolverDelegates);
    }
}
