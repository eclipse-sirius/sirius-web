/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.providers.GraphQLEnumTypeProvider;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.eclipse.sirius.web.services.api.modelers.Modeler;
import org.eclipse.sirius.web.services.api.modelers.PublicationStatus;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;

/**
 * Defines the Modeler type and its related type for the GrahQL Schema definition.
 *
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Modeler {
 *   id: ID!
 *   name: String!
 *   status: PublicationStatus!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@Service
public class ModelerTypeProvider implements ITypeProvider {
    private final GraphQLObjectTypeProvider graphQLObjectTypeProvider = new GraphQLObjectTypeProvider();

    private final GraphQLEnumTypeProvider graphQLEnumTypeProvider = new GraphQLEnumTypeProvider();

    @Override
    public Set<GraphQLType> getTypes() {
        GraphQLObjectType modelerType = this.graphQLObjectTypeProvider.getType(Modeler.class);
        GraphQLEnumType satusType = this.graphQLEnumTypeProvider.getType(PublicationStatus.class);

        return Set.of(modelerType, satusType);
    }
}
