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

import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.springframework.stereotype.Service;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;

/**
 * This class is used to create the definition of the Document type and its related types.
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Document {
 *   id: ID!
 *   name: String!
 * }
 *
 * </pre>
 *
 * @author sbegaudeau
 */
@Service
public class DocumentTypesProvider implements ITypeProvider {
    public static final String TYPE = "Document"; //$NON-NLS-1$

    public static final String NAME_FIELD = "name"; //$NON-NLS-1$

    @Override
    public Set<GraphQLType> getTypes() {
        // @formatter:off
        GraphQLObjectType documentType = GraphQLObjectType.newObject()
                .name(TYPE)
                .field(new IdFieldProvider().getField())
                .field(this.getNameField())
                .build();
        // @formatter:on
        return Set.of(documentType);
    }

    private GraphQLFieldDefinition getNameField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(NAME_FIELD)
                .type(new GraphQLNonNull(Scalars.GraphQLString))
                .build();
        // @formatter:on
    }
}
