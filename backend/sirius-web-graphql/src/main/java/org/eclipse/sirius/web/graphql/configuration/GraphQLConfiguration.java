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
package org.eclipse.sirius.web.graphql.configuration;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.graphql.datafetchers.GraphQLDataFetcherExceptionHandler;
import org.eclipse.sirius.web.graphql.utils.schema.IMutationTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.IQueryTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ISubscriptionTypeProvider;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeCustomizer;
import org.eclipse.sirius.web.graphql.utils.schema.ITypeProvider;
import org.eclipse.sirius.web.graphql.utils.typeresolvers.ReflectiveTypeResolver;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.GraphQL;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.ExecutionStrategy;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLInterfaceType;
import graphql.schema.GraphQLNamedType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLType;
import graphql.schema.GraphQLUnionType;

/**
 * Spring Configuration used to create everything necessary to run GraphQL queries.
 * <p>
 * This class is responsible of the configuration of both the GraphQL schema and the code used to execute GraphQL
 * queries. It will provide an instance of the {@link GraphQL} configuration object which can be injected as a bean in
 * order components.
 * </p>
 *
 * @author sbegaudeau
 */
@Configuration
public class GraphQLConfiguration {
    /**
     * Creates the GraphQL configuration used to execute GraphQL queries.
     *
     * @param codeRegistry
     *            The GraphQL code registry
     *
     * @return The GraphQL configuration
     */
    @Bean
    public GraphQL graphQL(GraphQLSchema graphQLSchema) {
        DataFetcherExceptionHandler exceptionHandler = new GraphQLDataFetcherExceptionHandler();
        ExecutionStrategy queryExecutionStrategy = new AsyncExecutionStrategy(exceptionHandler);
        // @see https://www.graphql-java.com/documentation/v11/execution/ The graphql specification says that mutations
        // MUST be executed serially and in the order in which the query fields occur.
        ExecutionStrategy mutationExecutionStrategy = new AsyncSerialExecutionStrategy(exceptionHandler);
        // @formatter:off
        return GraphQL.newGraphQL(graphQLSchema)
                .queryExecutionStrategy(queryExecutionStrategy)
                .mutationExecutionStrategy(mutationExecutionStrategy)
                .build();
        // @formatter:on
    }

    @Bean
    public GraphQLSchema graphQLSchema(List<IDataFetcherWithFieldCoordinates<?>> dataFetchersWithCoordinates, IQueryTypeProvider queryTypeProvider, IMutationTypeProvider mutationTypeProvider,
            ISubscriptionTypeProvider subscriptionTypeProvider, List<ITypeProvider> typeProviders, List<ITypeCustomizer> typeCustomizers) {
        Set<GraphQLType> types = new LinkedHashSet<>();

        // @formatter:off
        var additionalTypes = typeProviders.stream()
                .map(ITypeProvider::getTypes)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        // @formatter:on

        types.addAll(additionalTypes);
        types.addAll(queryTypeProvider.getAdditionalTypes());
        types.addAll(mutationTypeProvider.getAdditionalTypes());
        types.addAll(subscriptionTypeProvider.getAdditionalTypes());

        Set<GraphQLType> customizedTypes = new LinkedHashSet<>();
        for (GraphQLType graphQLType : types) {
            GraphQLType type = graphQLType;
            for (ITypeCustomizer typeCustomizer : typeCustomizers) {
                type = typeCustomizer.customize(type);
            }
            customizedTypes.add(type);
        }

        GraphQLCodeRegistry.Builder builder = GraphQLCodeRegistry.newCodeRegistry();
        // @formatter:off
        customizedTypes.stream()
            .filter(graphQLType -> GraphQLUnionType.class.isInstance(graphQLType) || GraphQLInterfaceType.class.isInstance(graphQLType))
            .filter(GraphQLNamedType.class::isInstance)
            .map(GraphQLNamedType.class::cast)
            .forEach(graphQLType -> builder.typeResolver(graphQLType.getName(), new ReflectiveTypeResolver()));
        // @formatter:on

        dataFetchersWithCoordinates.forEach(dataFetcherWithCoordinates -> {
            dataFetcherWithCoordinates.getFieldCoordinates().forEach(fieldCoordinates -> {
                builder.dataFetcher(fieldCoordinates, dataFetcherWithCoordinates);
            });
        });
        var graphQLCodeRegistry = builder.build();

        // @formatter:off
        return GraphQLSchema.newSchema()
                .query(queryTypeProvider.getType())
                .mutation(mutationTypeProvider.getType())
                .subscription(subscriptionTypeProvider.getType())
                .additionalTypes(customizedTypes)
                .codeRegistry(graphQLCodeRegistry)
                .build();
        // @formatter:on

    }
}
