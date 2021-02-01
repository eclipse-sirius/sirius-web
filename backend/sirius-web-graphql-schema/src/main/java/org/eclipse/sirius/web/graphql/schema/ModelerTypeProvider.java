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
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;

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
 *   editingContext: EditingContext!
 * }
 * </pre>
 *
 * @author pcdavid
 */
@Service
public class ModelerTypeProvider implements ITypeProvider {
    public static final String TYPE = "Modeler"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_FIELD = "editingContext"; //$NON-NLS-1$

    private final GraphQLObjectTypeProvider graphQLObjectTypeProvider = new GraphQLObjectTypeProvider();

    private final GraphQLEnumTypeProvider graphQLEnumTypeProvider = new GraphQLEnumTypeProvider();

    @Override
    public Set<GraphQLType> getTypes() {
        // @formatter:off
        GraphQLObjectType modelerType = GraphQLObjectType.newObject(this.graphQLObjectTypeProvider.getType(Modeler.class))
                .field(this.getEditingContextField())
                .build();
        // @formatter:on

        GraphQLEnumType satusType = this.graphQLEnumTypeProvider.getType(PublicationStatus.class);

        return Set.of(modelerType, satusType);
    }

    private GraphQLFieldDefinition getEditingContextField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(EDITING_CONTEXT_FIELD)
                .type(new GraphQLNonNull(new GraphQLTypeReference(EditingContextTypeProvider.TYPE)))
                .build();
        // @formatter:on
    }
}
