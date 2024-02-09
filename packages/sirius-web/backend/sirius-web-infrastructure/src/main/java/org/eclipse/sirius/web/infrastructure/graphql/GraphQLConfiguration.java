/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.infrastructure.graphql;

import java.io.IOException;
import java.util.List;

import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.InstantScalarType;
import org.eclipse.sirius.components.graphql.api.UploadScalarType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import graphql.GraphQL;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.AsyncSerialExecutionStrategy;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.ExecutionStrategy;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.SchemaPrinter;
import graphql.schema.idl.TypeDefinitionRegistry;

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

    private final Logger logger = LoggerFactory.getLogger(GraphQLConfiguration.class);

    @Bean
    public GraphQL graphQL(GraphQLSchema graphQLSchema) {
        var options = SchemaPrinter.Options.defaultOptions();
        String schema = new SchemaPrinter(options).print(graphQLSchema);

        this.logger.trace(schema);

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
    public GraphQLSchema graphQLSchema(ResourcePatternResolver resourcePatternResolver, GraphQLWiringFactory graphQLWiringFactory, List<IDataFetcherWithFieldCoordinates<?>> dataFetchersWithCoordinates) {
        GraphQLCodeRegistry.Builder builder = GraphQLCodeRegistry.newCodeRegistry();
        dataFetchersWithCoordinates.forEach(dataFetcherWithCoordinates -> {
            dataFetcherWithCoordinates.getFieldCoordinates().forEach(fieldCoordinates -> builder.dataFetcher(fieldCoordinates, dataFetcherWithCoordinates));
        });
        var graphQLCodeRegistry = builder.build();

        try {
            TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();

            SchemaParser schemaParser = new SchemaParser();
            SchemaGenerator schemaGenerator = new SchemaGenerator();

            Resource[] resources = resourcePatternResolver.getResources("classpath*:/schema/**/*.graphqls");
            this.logger.info("{} GraphQL schemas found", resources.length);
            for (Resource resource : resources) {
                if (this.logger.isInfoEnabled()) {
                    this.logger.info("Processing the GraphQL schema: {}", resource.getURL());
                }
                TypeDefinitionRegistry childTypeDefinitionRegistry = schemaParser.parse(resource.getInputStream());
                typeRegistry.merge(childTypeDefinitionRegistry);
            }

            var runtimeWiring = RuntimeWiring.newRuntimeWiring()
                    .codeRegistry(graphQLCodeRegistry)
                    .wiringFactory(graphQLWiringFactory)
                    .scalar(UploadScalarType.INSTANCE)
                    .scalar(InstantScalarType.INSTANCE)
                    .build();

            return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return null;
    }
}