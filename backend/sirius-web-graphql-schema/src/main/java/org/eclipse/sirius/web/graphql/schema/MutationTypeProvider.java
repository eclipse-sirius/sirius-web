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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.sirius.web.annotations.graphql.GraphQLMutationTypes;
import org.eclipse.sirius.web.annotations.spring.graphql.MutationDataFetcher;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLInputObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLNameProvider;
import org.eclipse.sirius.web.graphql.utils.providers.GraphQLObjectTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.IMutationTypeProvider;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.springframework.stereotype.Service;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLTypeReference;
import graphql.schema.GraphQLUnionType;

/**
 * This class is used to create the definition of the Mutation type.
 *
 * @author sbegaudeau
 */
@Service
public class MutationTypeProvider implements IMutationTypeProvider {
    public static final String TYPE = "Mutation"; //$NON-NLS-1$

    public static final String INPUT_ARGUMENT = "input"; //$NON-NLS-1$

    private static final String INPUT_SUFFIX = "Input"; //$NON-NLS-1$

    private static final String PAYLOAD_SUFFIX = "Payload"; //$NON-NLS-1$

    private final GraphQLObjectTypeProvider graphQLObjectTypeProvider = new GraphQLObjectTypeProvider();

    private final GraphQLInputObjectTypeProvider graphQLInputObjectTypeProvider = new GraphQLInputObjectTypeProvider();

    private final GraphQLNameProvider graphQLNameProvider = new GraphQLNameProvider();

    private final List<Class<?>> mutationDataFetcherClass;

    public MutationTypeProvider(List<IDataFetcherWithFieldCoordinates<?>> dataFetchersWithCoordinates) {
        // @formatter:off
        this.mutationDataFetcherClass = Objects.requireNonNull(dataFetchersWithCoordinates).stream()
                .map(Object::getClass)
                .filter(aClass -> aClass.isAnnotationPresent(MutationDataFetcher.class))
                .collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public GraphQLObjectType getType() {
        // @formatter:off
        var fields = this.mutationDataFetcherClass.stream()
                .map(dataFetcherClass -> this.graphQLNameProvider.getMutationFieldName(dataFetcherClass))
                .map(this::getMutationField)
                .collect(Collectors.toUnmodifiableList());

        return GraphQLObjectType.newObject()
                .name(TYPE)
                .fields(fields)
                .build();
        // @formatter:on
    }

    private GraphQLFieldDefinition getMutationField(String fieldName) {
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
        var graphQLInputObjectTypes = this.mutationDataFetcherClass.stream()
                .map(dataFetcherClass -> dataFetcherClass.getAnnotation(GraphQLMutationTypes.class))
                .map(GraphQLMutationTypes::input)
                .map(this.graphQLInputObjectTypeProvider::getType)
                .collect(Collectors.toUnmodifiableList());

        var graphQLObjectTypes = this.mutationDataFetcherClass.stream()
                .flatMap(this::getGraphQLPayloadTypes)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on

        GraphQLObjectType errorPayloadObjectType = this.graphQLObjectTypeProvider.getType(ErrorPayload.class);

        Set<GraphQLType> types = new LinkedHashSet<>();
        types.addAll(graphQLObjectTypes);
        types.addAll(graphQLInputObjectTypes);
        types.add(errorPayloadObjectType);
        return types;
    }

    private Stream<GraphQLType> getGraphQLPayloadTypes(Class<?> dataFetcherClass) {
        GraphQLMutationTypes graphQLMutationTypes = dataFetcherClass.getAnnotation(GraphQLMutationTypes.class);
        Class<?>[] payloadClasses = graphQLMutationTypes.payloads();

        // @formatter:off
        List<GraphQLObjectType> graphQLObjectTypes = new ArrayList<>();
        for (Class<?> payloadClass : payloadClasses) {
            GraphQLObjectType graphQLObjectType = this.graphQLObjectTypeProvider.getType(payloadClass);
            graphQLObjectTypes.add(graphQLObjectType);
        }

        String unionTypeName = this.graphQLNameProvider.getMutationUnionTypeName(dataFetcherClass);
        var union = GraphQLUnionType.newUnionType()
                .name(unionTypeName)
                .possibleTypes(graphQLObjectTypes.toArray(new GraphQLObjectType[graphQLObjectTypes.size()]))
                .possibleType(new GraphQLTypeReference(ErrorPayload.class.getSimpleName()))
                .build();

        return Stream.concat(Stream.of(union), graphQLObjectTypes.stream());
        // @formatter:on
    }

}
