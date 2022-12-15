/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.graphql.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import org.eclipse.sirius.components.graphql.api.UploadScalarType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.GraphQLTypeReference;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

/**
 * Unit tests of the GraphQL controller.
 *
 * @author sbegaudeau
 */
public class GraphQLControllerTests {
    private static final MultipartFile FILE = new MockMultipartFile("file", "<MOCK_CONTENT>".getBytes());

    private static final String QUERY = "{ \"query\": \"mutation uploadDocument($input: UploadDocumentInput!){ uploadDocument(input: $input) }\", \"variables\": { \"input\": { \"file\": null }}}";

    private static final String MAPPING = "{\"0\": \"variables.file\"}";

    private GraphQL getGraphQL() {
        // @formatter:off
        // The dummy field is needed to pass GraphQL validation, as Query must have at least one field
        GraphQLFieldDefinition dummyField = GraphQLFieldDefinition.newFieldDefinition()
                .name("dummy")
                .type(Scalars.GraphQLBoolean)
                .build();
        GraphQLObjectType queryObjectType = GraphQLObjectType.newObject()
                .name("Query")
                .field(dummyField)
                .build();

        GraphQLInputObjectField fileField = GraphQLInputObjectField.newInputObjectField()
                .name("file")
                .type(UploadScalarType.INSTANCE)
                .build();
        GraphQLInputObjectType inputObjectType = GraphQLInputObjectType.newInputObject()
                .name("UploadDocumentInput")
                .field(fileField)
                .build();

        GraphQLArgument inputArgument = GraphQLArgument.newArgument()
                .name("input")
                .type(new GraphQLNonNull(new GraphQLTypeReference("UploadDocumentInput")))
                .build();
        GraphQLFieldDefinition uploadDocumentField = GraphQLFieldDefinition.newFieldDefinition()
                .name("uploadDocument")
                .type(Scalars.GraphQLString)
                .argument(inputArgument)
                .build();

        GraphQLObjectType mutationObjectType = GraphQLObjectType.newObject()
                .name("Mutation")
                .field(uploadDocumentField)
                .build();

        var dataFetcher = new DataFetcher<String>() {
            @Override
            public String get(DataFetchingEnvironment environment) throws Exception {
                return "DOCUMENT_CREATED";
            }
        };
        GraphQLCodeRegistry codeRegistry = GraphQLCodeRegistry.newCodeRegistry()
                .dataFetcher(FieldCoordinates.coordinates("Mutation", "uploadDocument"), dataFetcher)
                .build();

        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema()
                .query(queryObjectType)
                .mutation(mutationObjectType)
                .additionalType(inputObjectType)
                .codeRegistry(codeRegistry)
                .build();

        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema)
                .build();
        // @formatter:on
        return graphQL;
    }

    @Test
    public void testInvalidOperation() {
        GraphQLController graphQLController = new GraphQLController(new ObjectMapper(), this.getGraphQL(), new SimpleMeterRegistry());
        ResponseEntity<Map<String, Object>> responseEntity = graphQLController.uploadDocument(null, MAPPING, FILE);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testInvalidMapping() {
        GraphQLController graphQLController = new GraphQLController(new ObjectMapper(), this.getGraphQL(), new SimpleMeterRegistry());
        ResponseEntity<Map<String, Object>> responseEntity = graphQLController.uploadDocument(QUERY, null, FILE);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testInvalidMultipartFile() {
        GraphQLController graphQLController = new GraphQLController(new ObjectMapper(), this.getGraphQL(), new SimpleMeterRegistry());
        ResponseEntity<Map<String, Object>> responseEntity = graphQLController.uploadDocument(QUERY, MAPPING, null);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testValidUpload() {
        GraphQLController graphQLController = new GraphQLController(new ObjectMapper(), this.getGraphQL(), new SimpleMeterRegistry());
        ResponseEntity<Map<String, Object>> responseEntity = graphQLController.uploadDocument(QUERY, MAPPING, FILE);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().toString()).isEqualTo("{data={uploadDocument=DOCUMENT_CREATED}}");
    }
}
