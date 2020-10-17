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
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLType;

/**
 * This class is used to create the definition of the Representation interface.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * interface Representation {
 *   id: ID!
 *   kind: String!
 *   label: String!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationTypeProvider implements ITypeProvider {
    public static final String TYPE = "Representation"; //$NON-NLS-1$

    private static final String LABEL = "label"; //$NON-NLS-1$

    @Override
    public Set<GraphQLType> getTypes() {
        LinkedHashSet<GraphQLType> types = new LinkedHashSet<>();
        // @formatter:off
        var representationType = GraphQLInterfaceType.newInterface()
                .name(TYPE)
                .field(new IdFieldProvider().getField())
                .field(new KindFieldProvider().getField())
                .field(this.getLabelField())
                .build();
        // @formatter:on

        types.add(representationType);
        return types;
    }

    private GraphQLFieldDefinition getLabelField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(LABEL)
                .type(new GraphQLNonNull(Scalars.GraphQLString))
                .build();
        // @formatter:on
    }
}
