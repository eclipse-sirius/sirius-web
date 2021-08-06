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

import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLList.list;
import static graphql.schema.GraphQLNonNull.nonNull;
import static graphql.schema.GraphQLTypeReference.typeRef;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.sirius.web.core.api.ChildCreationDescription;
import org.eclipse.sirius.web.core.api.Domain;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.springframework.stereotype.Service;

import graphql.Scalars;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;

/**
 * This class is used to create the definition of the EditingContext type and its related types.
 * <p>
 * The types created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type EditingContext {
 *   id: ID!
 *   stereotypeDescriptions: EditingContextStereotypeDescriptionConnection!
 *   childCreationDescriptions(classId: ID!): [ChildCreationDescription!]!
 *   rootObjectCreationDescriptions(domainId: ID!, suggested: Boolean!): [ChildCreationDescription!]!
 *   domains: [Domain!]!
 *   representationDescriptions(classId: ID): EditingContextRepresentationDescriptionConnection!
 *   representation(representationId: ID!): Representation
 *   representations: EditingContextRepresentationConnection!
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextTypeProvider implements ITypeProvider {

    public static final String TYPE = "EditingContext"; //$NON-NLS-1$

    public static final String STEREOTYPE_DESCRIPTIONS_FIELD = "stereotypeDescriptions"; //$NON-NLS-1$

    public static final String CHILD_CREATION_DESCRIPTIONS_FIELD = "childCreationDescriptions"; //$NON-NLS-1$

    public static final String CLASS_ID_ARGUMENT = "classId"; //$NON-NLS-1$

    public static final String ROOT_OBJECT_CREATION_DESCRIPTIONS_FIELD = "rootObjectCreationDescriptions"; //$NON-NLS-1$

    public static final String DOMAIN_ID_ARGUMENT = "domainId"; //$NON-NLS-1$

    public static final String SUGGESTED_ARGUMENT = "suggested"; //$NON-NLS-1$

    public static final String DOMAINS_FIELD = "domains"; //$NON-NLS-1$

    public static final String REPRESENTATION_DESCRIPTIONS_FIELD = "representationDescriptions"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_REPRESENTATION_DESCRIPTIONS_CONNECTION = "EditingContextRepresentationDescriptionConnection"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_REPRESENTATION_DESCRIPTIONS_EDGE = "EditingContextRepresentationDescriptionEdge"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_STEREOTYPE_DESCRIPTIONS_CONNECTION = "EditingContextStereotypeDescriptionConnection"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_STEREOTYPE_DESCRIPTIONS_EDGE = "EditingContextStereotypeDescriptionEdge"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_REPRESENTATION_CONNECTION = "EditingContextRepresentationConnection"; //$NON-NLS-1$

    public static final String EDITING_CONTEXT_REPRESENTATION_EDGE = "EditingContextRepresentationEdge"; //$NON-NLS-1$

    public static final String REPRESENTATIONS_FIELD = "representations"; //$NON-NLS-1$

    public static final String REPRESENTATION_FIELD = "representation"; //$NON-NLS-1$

    public static final String REPRESENTATION_ID_ARGUMENT = "representationId"; //$NON-NLS-1$

    @Override
    public Set<GraphQLType> getTypes() {
        // @formatter:off
        GraphQLObjectType editingContextType = GraphQLObjectType.newObject()
                .name(TYPE)
                .field(new IdFieldProvider().getField())
                .field(this.getStereotypeDescriptionsField())
                .field(this.getChildCreationDescriptionsField())
                .field(this.getRootObjectCreationDescriptionsField())
                .field(this.getDomainsField())
                .field(this.getRepresentationDescriptionField())
                .field(this.getRepresentationField())
                .field(this.getRepresentationsField())
                .build();
        // @formatter:on

        GraphQLObjectType editingContextRepresentationDescriptionEdge = new PaginationEdgeTypeProvider(EDITING_CONTEXT_REPRESENTATION_DESCRIPTIONS_EDGE, RepresentationDescriptionTypeProvider.TYPE)
                .getType();
        GraphQLObjectType editingContextRepresentationDescriptionConnection = new PaginationConnectionTypeProvider(EDITING_CONTEXT_REPRESENTATION_DESCRIPTIONS_CONNECTION,
                EDITING_CONTEXT_REPRESENTATION_DESCRIPTIONS_EDGE).getType();
        GraphQLObjectType editingContextSterotypeDescriptionEdge = new PaginationEdgeTypeProvider(EDITING_CONTEXT_STEREOTYPE_DESCRIPTIONS_EDGE, StereotypeDescriptionTypeProvider.TYPE).getType();
        GraphQLObjectType editingContextStereotypeDescriptionConnection = new PaginationConnectionTypeProvider(EDITING_CONTEXT_STEREOTYPE_DESCRIPTIONS_CONNECTION,
                EDITING_CONTEXT_STEREOTYPE_DESCRIPTIONS_EDGE).getType();
        GraphQLObjectType editingContextRepresentationEdge = new PaginationEdgeTypeProvider(EDITING_CONTEXT_REPRESENTATION_EDGE, RepresentationTypeProvider.TYPE).getType();
        GraphQLObjectType editingContextRepresentationConnection = new PaginationConnectionTypeProvider(EDITING_CONTEXT_REPRESENTATION_CONNECTION, EDITING_CONTEXT_REPRESENTATION_EDGE).getType();

        Set<GraphQLType> types = new LinkedHashSet<>();
        types.add(editingContextType);
        types.add(editingContextRepresentationDescriptionEdge);
        types.add(editingContextRepresentationDescriptionConnection);
        types.add(editingContextSterotypeDescriptionEdge);
        types.add(editingContextStereotypeDescriptionConnection);
        types.add(editingContextRepresentationEdge);
        types.add(editingContextRepresentationConnection);
        return types;
    }

    private GraphQLFieldDefinition getStereotypeDescriptionsField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(STEREOTYPE_DESCRIPTIONS_FIELD)
                .type(nonNull(typeRef(EDITING_CONTEXT_STEREOTYPE_DESCRIPTIONS_CONNECTION)))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getChildCreationDescriptionsField() {
        // @formatter:off
        return newFieldDefinition()
                .name(CHILD_CREATION_DESCRIPTIONS_FIELD)
                .argument(this.getClassIdArgument())
                .type(nonNull(list(nonNull(typeRef(ChildCreationDescription.class.getSimpleName())))))
                .build();
        // @formatter:on
    }

    private GraphQLArgument getClassIdArgument() {
        // @formatter:off
        return newArgument()
                .name(CLASS_ID_ARGUMENT)
                .type(nonNull(Scalars.GraphQLID))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getRootObjectCreationDescriptionsField() {
        // @formatter:off
        return newFieldDefinition()
                .name(ROOT_OBJECT_CREATION_DESCRIPTIONS_FIELD)
                .type(nonNull(list(nonNull(typeRef(ChildCreationDescription.class.getSimpleName())))))
                .argument(this.getDomainIdArgument())
                .argument(this.getSuggestedArgument())
                .build();
        // @formatter:on
    }

    private GraphQLArgument getDomainIdArgument() {
        // @formatter:off
        return newArgument()
                .name(DOMAIN_ID_ARGUMENT)
                .type(nonNull(Scalars.GraphQLID))
                .build();
        // @formatter:on
    }

    private GraphQLArgument getSuggestedArgument() {
        // @formatter:off
        return newArgument()
                .name(SUGGESTED_ARGUMENT)
                .type(nonNull(Scalars.GraphQLBoolean))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getDomainsField() {
        // @formatter:off
        return newFieldDefinition()
                .name(DOMAINS_FIELD)
                .type(nonNull(list(nonNull(typeRef(Domain.class.getSimpleName())))))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getRepresentationDescriptionField() {
        // @formatter:off
        return newFieldDefinition()
                .name(REPRESENTATION_DESCRIPTIONS_FIELD)
                .argument(this.getClassIdArgument())
                .type(nonNull(typeRef(EDITING_CONTEXT_REPRESENTATION_DESCRIPTIONS_CONNECTION)))
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getRepresentationsField() {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(REPRESENTATIONS_FIELD)
                .type(nonNull(typeRef(EDITING_CONTEXT_REPRESENTATION_CONNECTION)))
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
                .type(new GraphQLTypeReference(RepresentationTypeProvider.TYPE))
                .build();
        // @formatter:on
    }
}
