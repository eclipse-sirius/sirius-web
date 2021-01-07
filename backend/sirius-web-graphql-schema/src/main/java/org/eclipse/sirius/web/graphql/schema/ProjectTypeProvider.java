/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLNonNull.nonNull;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.sirius.web.graphql.utils.providers.GraphQLEnumTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.eclipse.sirius.web.services.api.projects.AccessLevel;
import org.eclipse.sirius.web.services.api.projects.Visibility;
import org.springframework.stereotype.Service;

import graphql.Scalars;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;

/**
 * This class is used to create the definition of the Project type and its related types.
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Project {
 *   id: ID!
 *   name: String!
 *   owner: Account!
 *   visibility: Visibility!
 *   representation(representationId: ID!): Representation
 *   representations: [Representation!]!
 *   accessLevel: AccessLevel!
 *   modelers: [Modeler!]!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@Service
public class ProjectTypeProvider implements ITypeProvider {
    public static final String TYPE = "Project"; //$NON-NLS-1$

    public static final String REPRESENTATION_TYPE = "Representation"; //$NON-NLS-1$

    public static final String MODELER_TYPE = "Modeler"; //$NON-NLS-1$

    public static final String NAME_FIELD = "name"; //$NON-NLS-1$

    public static final String REPRESENTATIONS_FIELD = "representations"; //$NON-NLS-1$

    public static final String REPRESENTATION_FIELD = "representation"; //$NON-NLS-1$

    public static final String REPRESENTATION_ID_ARGUMENT = "representationId"; //$NON-NLS-1$

    public static final String OWNER_FIELD = "owner"; //$NON-NLS-1$

    public static final String VISIBILITY_FIELD = "visibility"; //$NON-NLS-1$

    public static final String ACCESS_LEVEL_FIELD = "accessLevel"; //$NON-NLS-1$

    public static final String MODELERS_FIELD = "modelers"; //$NON-NLS-1$

    private final GraphQLEnumTypeProvider graphQLEnumTypeProvider = new GraphQLEnumTypeProvider();

    @Override
    public Set<GraphQLType> getTypes() {
        // @formatter:off
        GraphQLObjectType projectType = GraphQLObjectType.newObject()
                .name(TYPE)
                .field(new IdFieldProvider().getField())
                .field(this.getNameField())
                .field(this.getOwnerField())
                .field(this.getVisibilityField())
                .field(this.getRepresentationField())
                .field(this.getRepresentationsField())
                .field(this.getAccessLevelField())
                .field(this.getModelersField())
                .build();
        // @formatter:on

        GraphQLEnumType accessLevelType = this.graphQLEnumTypeProvider.getType(AccessLevel.class);
        GraphQLEnumType visibilityLevelType = this.graphQLEnumTypeProvider.getType(Visibility.class);

        Set<GraphQLType> types = new LinkedHashSet<>();
        types.add(projectType);
        types.add(accessLevelType);
        types.add(visibilityLevelType);

        return types;
    }

    private GraphQLFieldDefinition getNameField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(NAME_FIELD)
                .type(new GraphQLNonNull(Scalars.GraphQLString))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getRepresentationsField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(REPRESENTATIONS_FIELD)
                .type(new GraphQLNonNull(new GraphQLList(new GraphQLNonNull(new GraphQLTypeReference(REPRESENTATION_TYPE)))))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getRepresentationField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(REPRESENTATION_FIELD)
                .argument(
                        newArgument()
                            .name(REPRESENTATION_ID_ARGUMENT)
                            .type(nonNull(Scalars.GraphQLID)))
                .type(new GraphQLTypeReference(REPRESENTATION_TYPE))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getOwnerField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(OWNER_FIELD)
                .type(new GraphQLNonNull(new GraphQLTypeReference(ProfileTypeProvider.TYPE)))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getVisibilityField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(VISIBILITY_FIELD)
                .type(new GraphQLNonNull(new GraphQLTypeReference(Visibility.class.getSimpleName())))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getAccessLevelField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(ACCESS_LEVEL_FIELD)
                .type(new GraphQLNonNull(new GraphQLTypeReference(AccessLevel.class.getSimpleName())))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getModelersField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(MODELERS_FIELD)
                .type(new GraphQLNonNull(new GraphQLList(new GraphQLNonNull(new GraphQLTypeReference(MODELER_TYPE)))))
                .build();
        // @formatter:on
    }
}
