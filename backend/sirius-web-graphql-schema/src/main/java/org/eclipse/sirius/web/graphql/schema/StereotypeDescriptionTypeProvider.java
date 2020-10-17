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

import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.springframework.stereotype.Service;

import graphql.Scalars;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;

/**
 * This class is used to create the definition of the StereotypeDescription type.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type StereotypeDescription {
 *   id: ID!
 *   label: String!
 * }
 * </pre>
 *
 * @author hmarchadour
 */
@Service
public class StereotypeDescriptionTypeProvider implements ITypeProvider {
    public static final String TYPE = "StereotypeDescription"; //$NON-NLS-1$

    public static final String LABEL_FIELD = "label"; //$NON-NLS-1$

    @Override
    public Set<GraphQLType> getTypes() {
        LinkedHashSet<GraphQLType> types = new LinkedHashSet<>();
        // @formatter:off
        var stereotypeDescriptionType = GraphQLObjectType.newObject()
                .name(TYPE)
                .field(new IdFieldProvider().getField())
                .field(this.getLabelField())
                .build();
        // @formatter:on

        types.add(stereotypeDescriptionType);
        return types;
    }

    private GraphQLFieldDefinition getLabelField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(LABEL_FIELD)
                .type(new GraphQLNonNull(Scalars.GraphQLString))
                .build();
        // @formatter:on
    }

}
