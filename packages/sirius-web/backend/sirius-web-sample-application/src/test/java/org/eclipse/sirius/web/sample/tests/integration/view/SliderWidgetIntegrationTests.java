/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.tests.integration.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import fr.obeo.dsl.designer.sample.flow.FlowPackage;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.forms.api.FormConfiguration;
import org.eclipse.sirius.components.collaborative.forms.dto.FormEventInput;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.FlexboxContainer;
import org.eclipse.sirius.components.graphql.api.IEventProcessorSubscriptionProvider;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.sample.configuration.StereotypeRegistryConfigurer;
import org.eclipse.sirius.components.forms.Slider;
import org.eclipse.sirius.web.sample.tests.integration.AbstractIntegrationTests;
import org.eclipse.sirius.web.services.api.document.CreateDocumentInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import graphql.ExecutionInput;
import graphql.GraphQL;
import net.minidev.json.JSONArray;
import reactor.test.StepVerifier;

/**
 * Integration tests for the Slider custom widget. Depends on
 * {@link SliderViewRepresentationDescriptionProvider} to register the "Slider Widget Test" form description.
 *
 * @author pcdavid
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SliderWidgetIntegrationTests extends AbstractIntegrationTests {
    @Autowired
    private GraphQL graphQL;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private EditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private IEventProcessorSubscriptionProvider eventProcessorSubscriptionProvider;

    private UUID projectId;

    private UUID rootObjectId;

    private UUID representationId;

    private UUID documentId;

    @BeforeEach
    public void setup() {
        this.createProject();
        this.createProjectContent();
        this.createForm();
    }

    @AfterEach
    public void teardown() {
        this.editingContextEventProcessorRegistry.dispose();
        this.projectRepository.deleteAll();
    }

    private void createProject() {
        var query = """
                mutation createProject($input: CreateProjectInput!) {
                  createProject(input: $input) {
                    __typename
                    ... on CreateProjectSuccessPayload {
                      project {
                        id
                      }
                    }
                  }
                }
                """;

        var input = new CreateProjectInput(UUID.randomUUID(), "Instance");

        var executionInput = ExecutionInput.newExecutionInput().query(query).variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() { }))).build();
        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createProject.__typename");
            assertThat(responseTypeName).isEqualTo("CreateProjectSuccessPayload");

            String rawProjectId = JsonPath.read(jsonResult, "$.data.createProject.project.id");
            this.projectId = UUID.fromString(rawProjectId);
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }

        assertThat(this.projectRepository.existsById(this.projectId)).isTrue();
    }

    private void createProjectContent() {
        var createDocumentQuery = """
                mutation createDocument($input: CreateDocumentInput!) {
                  createDocument(input: $input) {
                    __typename
                    ... on CreateDocumentSuccessPayload {
                      document {
                        id
                      }
                    }
                  }
                }
                """;

        assertThat(this.projectRepository.existsById(this.projectId)).isTrue();

        var createDocumentInput = new CreateDocumentInput(UUID.randomUUID(), this.projectId.toString(), "New", StereotypeRegistryConfigurer.EMPTY_ID);

        var createDocumentExecutionInput = ExecutionInput.newExecutionInput().query(createDocumentQuery)
                .variables(Map.of("input", this.objectMapper.convertValue(createDocumentInput, new TypeReference<Map<String, Object>>() { }))).build();
        var createDocumentExecutionResult = this.graphQL.execute(createDocumentExecutionInput);
        assertThat(createDocumentExecutionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(createDocumentExecutionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createDocument.__typename");
            assertThat(responseTypeName).isEqualTo("CreateDocumentSuccessPayload");

            String rawDocumentId = JsonPath.read(jsonResult, "$.data.createDocument.document.id");
            this.documentId = UUID.fromString(rawDocumentId);
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }

        var createRootObjectQuery = """
                mutation createRootObject($input: CreateRootObjectInput!) {
                  createRootObject(input: $input) {
                    __typename
                    ... on CreateRootObjectSuccessPayload {
                      object {
                        id
                      }
                    }
                  }
                }
                """;

        var createRootObjectInput = new CreateRootObjectInput(UUID.randomUUID(), this.projectId.toString(), this.documentId, FlowPackage.eNS_URI, "System");

        var createRootObjectExecutionInput = ExecutionInput.newExecutionInput().query(createRootObjectQuery)
                .variables(Map.of("input", this.objectMapper.convertValue(createRootObjectInput, new TypeReference<Map<String, Object>>() { }))).build();
        var createRootObjectExecutionResult = this.graphQL.execute(createRootObjectExecutionInput);
        assertThat(createRootObjectExecutionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(createRootObjectExecutionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createRootObject.__typename");
            assertThat(responseTypeName).isEqualTo("CreateRootObjectSuccessPayload");

            String rawObjectId = JsonPath.read(jsonResult, "$.data.createRootObject.object.id");
            this.rootObjectId = UUID.fromString(rawObjectId);
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }
    }

    private void createForm() {
        var getRepresentationDescriptionsQuery = """
                query getRepresentationDescriptions($editingContextId: ID!, $objectId: ID!) {
                  viewer {
                    editingContext(editingContextId: $editingContextId) {
                      representationDescriptions(objectId: $objectId) {
                        edges {
                          node {
                            id
                            label
                          }
                        }
                      }
                    }
                  }
                }
                """;

        var getRepresentationDescriptionsExecutionInput = ExecutionInput.newExecutionInput().query(getRepresentationDescriptionsQuery)
                .variables(Map.of("editingContextId", this.projectId.toString(), "objectId", this.rootObjectId.toString())).build();
        var getRepresentationDescriptionsExecutionResult = this.graphQL.execute(getRepresentationDescriptionsExecutionInput);
        assertThat(getRepresentationDescriptionsExecutionResult.getErrors()).isEmpty();

        String representationDescriptionId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(getRepresentationDescriptionsExecutionResult.toSpecification());
            var matches = (JSONArray) JsonPath.read(jsonResult, "$.data.viewer.editingContext.representationDescriptions.edges[?(@.node.label=='Slider Widget Test')].node.id");
            representationDescriptionId = (String) matches.get(0);
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }

        var query = """
                mutation createRepresentation($input: CreateRepresentationInput!) {
                  createRepresentation(input: $input) {
                    __typename
                    ... on CreateRepresentationSuccessPayload {
                      representation {
                        id
                      }
                    }
                  }
                }
                """;

        var input = new CreateRepresentationInput(UUID.randomUUID(), this.projectId.toString(), representationDescriptionId, this.rootObjectId.toString(), "Form");
        var executionInput = ExecutionInput.newExecutionInput().query(query).variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() { }))).build();
        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String rawRepresentationId = JsonPath.read(jsonResult, "$.data.createRepresentation.representation.id");
            this.representationId = UUID.fromString(rawRepresentationId);
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    @DisplayName("Can instanciate a View-based FormDescription which uses the Slider custom widget")
    public void canCreateFormWithSliderWidget() {
        var configuration = new FormConfiguration(this.representationId.toString());
        var input = new FormEventInput(UUID.randomUUID(), this.projectId.toString(), this.representationId.toString());
        var payloadFlux = this.eventProcessorSubscriptionProvider.getSubscription(this.projectId.toString(), configuration, input);
        Predicate<IPayload> isFormWithSlidersRefreshedEventPayload = payload -> {
            if (payload instanceof FormRefreshedEventPayload formRefreshedEventPayload) {
                var form = formRefreshedEventPayload.form();
                var group = form.getPages().get(0).getGroups().get(0);
                var flexbox = (FlexboxContainer) group.getWidgets().get(2);
                return group.getWidgets().get(1) instanceof Slider && flexbox.getChildren().get(1) instanceof Slider;
            }
            return false;
        };

        StepVerifier.create(payloadFlux).expectNextMatches(isFormWithSlidersRefreshedEventPayload).thenCancel().verify(Duration.ofSeconds(5));
    }

}
