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

import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLNonNull.nonNull;
import static graphql.schema.GraphQLTypeReference.typeRef;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.collaborative.api.dto.PreDestroyPayload;
import org.eclipse.sirius.web.collaborative.api.dto.ProjectEventInput;
import org.eclipse.sirius.web.collaborative.api.dto.RepresentationRenamedEventPayload;
import org.eclipse.sirius.web.collaborative.api.dto.Subscriber;
import org.eclipse.sirius.web.collaborative.api.dto.SubscribersUpdatedEventPayload;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.DiagramEventInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.web.collaborative.forms.api.dto.FormEventInput;
import org.eclipse.sirius.web.collaborative.forms.api.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.web.collaborative.forms.api.dto.WidgetSubscription;
import org.eclipse.sirius.web.collaborative.forms.api.dto.WidgetSubscriptionsUpdatedEventPayload;
import org.eclipse.sirius.web.collaborative.trees.api.TreeEventInput;
import org.eclipse.sirius.web.collaborative.trees.api.TreeRefreshedEventPayload;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLInputObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ISubscriptionTypeProvider;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
import graphql.schema.GraphQLUnionType;

/**
 * This class is used to create the definition of the Subscription type.
 * <p>
 * The type created will match the following GraphQL textual definition:
 * </p>
 *
 * <pre>
 * type Subscription {
 *   diagramEvent(input: DiagramEventInput): DiagramEventPayload
 *   formEvent(input: FormEventInput): FormEventPayload
 *   treeEvent(input: TreeEventInput): TreeEventPayload
 *   projectEvent(input: ProjectEventInput): ProjectEventPayload
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@Service
public class SubscriptionTypeProvider implements ISubscriptionTypeProvider {

    public static final String TYPE = "Subscription"; //$NON-NLS-1$

    public static final String DIAGRAM_EVENT_FIELD = "diagramEvent"; //$NON-NLS-1$

    public static final String FORM_EVENT_FIELD = "formEvent"; //$NON-NLS-1$

    public static final String TREE_EVENT_FIELD = "treeEvent"; //$NON-NLS-1$

    public static final String PROJECT_EVENT_FIELD = "projectEvent"; //$NON-NLS-1$

    public static final String DIAGRAM_EVENT_PAYLOAD_UNION_TYPE = "DiagramEventPayload"; //$NON-NLS-1$

    public static final String FORM_EVENT_PAYLOAD_UNION_TYPE = "FormEventPayload"; //$NON-NLS-1$

    public static final String TREE_EVENT_PAYLOAD_UNION_TYPE = "TreeEventPayload"; //$NON-NLS-1$

    public static final String PROJECT_EVENT_PAYLOAD_UNION_TYPE = "ProjectEventPayload"; //$NON-NLS-1$

    public static final String INPUT_ARGUMENT = "input"; //$NON-NLS-1$

    private static final String INPUT_SUFFIX = "Input"; //$NON-NLS-1$

    private static final String PAYLOAD_SUFFIX = "Payload"; //$NON-NLS-1$

    private final GraphQLObjectTypeProvider graphQLObjectTypeProvider = new GraphQLObjectTypeProvider();

    private final GraphQLInputObjectTypeProvider graphQLInputObjectTypeProvider = new GraphQLInputObjectTypeProvider();

    @Override
    public GraphQLObjectType getType() {
        // @formatter:off
        List<GraphQLFieldDefinition> fields = List.of(
                DIAGRAM_EVENT_FIELD,
                FORM_EVENT_FIELD,
                TREE_EVENT_FIELD,
                PROJECT_EVENT_FIELD)
                .stream()
                .map(this::getSubscriptionField)
                .collect(Collectors.toUnmodifiableList());

        return GraphQLObjectType.newObject()
                .name(TYPE)
                .fields(fields)
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getSubscriptionField(String fieldName) {
        // @formatter:off
        return GraphQLFieldDefinition.newFieldDefinition()
                .name(fieldName)
                .argument(newArgument()
                            .name(INPUT_ARGUMENT)
                            .type(nonNull(typeRef(this.toUpperFirst(fieldName) + INPUT_SUFFIX))))
                .type(nonNull(typeRef(this.toUpperFirst(fieldName) + PAYLOAD_SUFFIX)))
                .build();
    }

    private String toUpperFirst(String name) {
        if (name.length() > 1) {
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return name.toUpperCase();
    }

    @Override
    public Set<GraphQLType> getAdditionalTypes() {
     // @formatter:off
        Set<Class<?>> objectClasses = Set.of(
                Subscriber.class,
                SubscribersUpdatedEventPayload.class,
                DiagramRefreshedEventPayload.class,
                FormRefreshedEventPayload.class,
                TreeRefreshedEventPayload.class,
                RepresentationRenamedEventPayload.class,
                WidgetSubscriptionsUpdatedEventPayload.class,
                WidgetSubscription.class,
                PreDestroyPayload.class
        );
        var graphQLObjectTypes = objectClasses.stream()
                .map(this.graphQLObjectTypeProvider::getType)
                .collect(Collectors.toUnmodifiableList());

        Set<Class<?>> inputObjectClasses = Set.of(
            DiagramEventInput.class,
            FormEventInput.class,
            TreeEventInput.class,
            ProjectEventInput.class
        );
        var graphQLInputObjectTypes = inputObjectClasses.stream()
                .map(this.graphQLInputObjectTypeProvider::getType)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on

        Set<GraphQLType> types = new LinkedHashSet<>();
        types.addAll(graphQLObjectTypes);
        types.addAll(graphQLInputObjectTypes);

        types.add(this.getDiagramEventPayloadUnion());
        types.add(this.getFormEventPayloadUnion());
        types.add(this.getTreeEventPayloadUnion());
        types.add(this.getProjectEventPayloadUnion());

        return types;
    }

    private GraphQLUnionType getDiagramEventPayloadUnion() {
        // @formatter:off
        return GraphQLUnionType.newUnionType().name(DIAGRAM_EVENT_PAYLOAD_UNION_TYPE).possibleTypes(
                new GraphQLTypeReference(DiagramRefreshedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(SubscribersUpdatedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(PreDestroyPayload.class.getSimpleName())
            ).build();
        // @formatter:on
    }

    private GraphQLUnionType getFormEventPayloadUnion() {
        // @formatter:off
        return GraphQLUnionType.newUnionType().name(FORM_EVENT_PAYLOAD_UNION_TYPE).possibleTypes(
                new GraphQLTypeReference(FormRefreshedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(SubscribersUpdatedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(WidgetSubscriptionsUpdatedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(PreDestroyPayload.class.getSimpleName())
            ).build();
        // @formatter:on
    }

    private GraphQLUnionType getTreeEventPayloadUnion() {
        // @formatter:off
        return GraphQLUnionType.newUnionType().name(TREE_EVENT_PAYLOAD_UNION_TYPE).possibleTypes(
                new GraphQLTypeReference(TreeRefreshedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(SubscribersUpdatedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(PreDestroyPayload.class.getSimpleName())
            ).build();
        // @formatter:on
    }

    private GraphQLUnionType getProjectEventPayloadUnion() {
        // @formatter:off
        return GraphQLUnionType.newUnionType().name(PROJECT_EVENT_PAYLOAD_UNION_TYPE).possibleTypes(
                new GraphQLTypeReference(RepresentationRenamedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(SubscribersUpdatedEventPayload.class.getSimpleName()),
                new GraphQLTypeReference(PreDestroyPayload.class.getSimpleName())
            ).build();
        // @formatter:on
    }
}
