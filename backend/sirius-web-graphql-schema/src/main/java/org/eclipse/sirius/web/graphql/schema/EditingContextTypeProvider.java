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

import static graphql.schema.GraphQLList.list;
import static graphql.schema.GraphQLNonNull.nonNull;
import static graphql.schema.GraphQLTypeReference.typeRef;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;

/**
 * This class is used to create the definition of the EditingContext type and its related types.
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   id: ID!
 *   stereotypeDescriptions: [StereotypeDescription!]!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextTypeProvider implements ITypeProvider {

    public static final String TYPE = "EditingContext"; //$NON-NLS-1$

    public static final String STEREOTYPE_DESCRIPTIONS_FIELD = "stereotypeDescriptions"; //$NON-NLS-1$

    @Override
    public Set<GraphQLType> getTypes() {
        // @formatter:off
        GraphQLObjectType editingContextType = GraphQLObjectType.newObject()
                .name(TYPE)
                .field(new IdFieldProvider().getField())
                .field(this.getStereotypeDescriptionsField())
                .build();
        // @formatter:on

        Set<GraphQLType> types = new LinkedHashSet<>();
        types.add(editingContextType);

        return types;
    }

    private GraphQLFieldDefinition getStereotypeDescriptionsField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(STEREOTYPE_DESCRIPTIONS_FIELD)
                .type(nonNull(list(nonNull(typeRef(StereotypeDescriptionTypeProvider.TYPE)))))
                .build();
        // @formatter:on
    }

}
