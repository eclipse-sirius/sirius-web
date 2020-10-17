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
package org.eclipse.sirius.web.graphql.schema;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.providers.GraphQLInterfaceTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLType;

/**
 * Creates the GraphQL type for {@link IRepresentationDescription}.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationDescriptionTypeProvider implements ITypeProvider {

    public static final String TYPE = "RepresentationDescription"; //$NON-NLS-1$

    private final GraphQLInterfaceTypeProvider graphQLInterfaceTypeProvider = new GraphQLInterfaceTypeProvider();

    @Override
    public Set<GraphQLType> getTypes() {
        LinkedHashSet<GraphQLType> types = new LinkedHashSet<>();
        types.add(this.graphQLInterfaceTypeProvider.getType(IRepresentationDescription.class));
        return types;
    }

}
