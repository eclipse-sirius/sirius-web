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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.annotations.graphql.GraphQLSubscriptionTypes;
import org.eclipse.sirius.web.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.web.collaborative.api.dto.Subscriber;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLInputObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLNameProvider;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ISubscriptionTypeProvider;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNamedType;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
import graphql.schema.GraphQLUnionType;

/**
 * This class is used to create the definition of the Subscription type.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class SubscriptionTypeProvider implements ISubscriptionTypeProvider {

    public static final String TYPE = "Subscription"; //$NON-NLS-1$

    public static final String INPUT_ARGUMENT = "input"; //$NON-NLS-1$

    private static final String INPUT_SUFFIX = "Input"; //$NON-NLS-1$

    private static final String PAYLOAD_SUFFIX = "Payload"; //$NON-NLS-1$

    private final GraphQLNameProvider graphQLNameProvider = new GraphQLNameProvider();

    private final GraphQLObjectTypeProvider graphQLObjectTypeProvider = new GraphQLObjectTypeProvider();

    private final GraphQLInputObjectTypeProvider graphQLInputObjectTypeProvider = new GraphQLInputObjectTypeProvider();

    private final List<Class<?>> subscriptionDataFetcherClasses;

    public SubscriptionTypeProvider(List<IDataFetcherWithFieldCoordinates<?>> dataFetchersWithCoordinates) {
        // @formatter:off
        this.subscriptionDataFetcherClasses = Objects.requireNonNull(dataFetchersWithCoordinates).stream()
                .map(Object::getClass)
                .filter(aClass -> aClass.isAnnotationPresent(SubscriptionDataFetcher.class))
                .collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public GraphQLObjectType getType() {
        // @formatter:off
        var fields = this.subscriptionDataFetcherClasses.stream()
                .map(dataFetcherClass -> this.graphQLNameProvider.getSubscriptionFieldName(dataFetcherClass))
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
        var graphQLInputObjectTypes = this.subscriptionDataFetcherClasses.stream()
                .map(dataFetcherClass -> dataFetcherClass.getAnnotation(GraphQLSubscriptionTypes.class))
                .map(GraphQLSubscriptionTypes::input)
                .map(this.graphQLInputObjectTypeProvider::getType)
                .collect(Collectors.toUnmodifiableList());

        List<GraphQLNamedType> graphQLTypes = new ArrayList<>();
        for (Class<?> subscriptionDataFetcherClass : this.subscriptionDataFetcherClasses) {
            List<GraphQLObjectType> graphQLPayloadTypes = this.getGraphQLPayloadTypes(subscriptionDataFetcherClass);
            for (GraphQLObjectType graphQLPayloadType : graphQLPayloadTypes) {
                var hasTypeWithSameName = graphQLTypes.stream()
                        .map(GraphQLNamedType::getName)
                        .anyMatch(graphQLPayloadType.getName()::equals);

                if (!hasTypeWithSameName) {
                    graphQLTypes.add(graphQLPayloadType);
                }
            }

            GraphQLUnionType unionType = this.getUnionType(subscriptionDataFetcherClass);
            graphQLTypes.add(unionType);
        }
        // @formatter:on

        Set<GraphQLType> types = new LinkedHashSet<>();
        types.addAll(graphQLTypes);
        types.addAll(graphQLInputObjectTypes);
        types.add(this.graphQLObjectTypeProvider.getType(Subscriber.class));

        return types;
    }

    private GraphQLUnionType getUnionType(Class<?> dataFetcherClass) {
        GraphQLSubscriptionTypes graphQLSubscriptionTypes = dataFetcherClass.getAnnotation(GraphQLSubscriptionTypes.class);

        // @formatter:off
        var payloadTypeReferences = Arrays.stream(graphQLSubscriptionTypes.payloads())
                .map(this.graphQLNameProvider::getObjectTypeName)
                .map(GraphQLTypeReference::new)
                .collect(Collectors.toList());

        String unionTypeName = this.graphQLNameProvider.getSubscriptionUnionTypeName(dataFetcherClass);
        return GraphQLUnionType.newUnionType()
                .name(unionTypeName)
                .possibleTypes(payloadTypeReferences.toArray(new GraphQLTypeReference[payloadTypeReferences.size()]))
                .possibleType(new GraphQLTypeReference(ErrorPayload.class.getSimpleName()))
                .build();
        // @formatter:on
    }

    private List<GraphQLObjectType> getGraphQLPayloadTypes(Class<?> dataFetcherClass) {
        GraphQLSubscriptionTypes graphQLSubscriptionTypes = dataFetcherClass.getAnnotation(GraphQLSubscriptionTypes.class);

        // @formatter:off
        return Arrays.stream(graphQLSubscriptionTypes.payloads())
                .map(this.graphQLObjectTypeProvider::getType)
                .collect(Collectors.toList());
        // @formatter:on
    }

}
