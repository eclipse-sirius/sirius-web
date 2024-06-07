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

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEventProcessorSubscriptionProvider;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.sample.configuration.StereotypeRegistryConfigurer;
import org.eclipse.sirius.web.sample.papaya.PapayaStudioTemplateProvider;
import org.eclipse.sirius.web.sample.tests.integration.AbstractIntegrationTests;
import org.eclipse.sirius.web.services.api.document.CreateDocumentInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectFromTemplateInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import graphql.ExecutionInput;
import graphql.GraphQL;
import net.minidev.json.JSONArray;
import reactor.test.StepVerifier;

/**
 * Integration tests of a diagram representation based on the view DSL.
 *
 * @author sbegaudeau
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewDiagramIntegrationTests extends AbstractIntegrationTests {

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

    //In order to use spring service with scope request, we simulate it.
    @BeforeEach
    public void simulateRequestContext() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);
    }

    @AfterEach
    public void resetRequestContext() {
        RequestContextHolder.resetRequestAttributes();
    }

    @BeforeEach
    public void setup() {
        this.createStudio();
        this.createProject();
        this.createProjectContent();
        this.createDiagram();
    }

    @AfterEach
    public void teardown() {
        this.editingContextEventProcessorRegistry.dispose();
        this.projectRepository.deleteAll();
    }

    private void createStudio() {
        var query = """
                mutation createProjectFromTemplate($input: CreateProjectFromTemplateInput!) {
                  createProjectFromTemplate(input: $input) {
                    __typename
                  }
                }
                """;

        var input = new CreateProjectFromTemplateInput(UUID.randomUUID(), PapayaStudioTemplateProvider.STUDIO_TEMPLATE_ID);

        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createProjectFromTemplate.__typename");
            assertThat(responseTypeName).isEqualTo("CreateProjectFromTemplateSuccessPayload");
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }
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

        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
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

        var createDocumentExecutionInput = ExecutionInput.newExecutionInput()
                .query(createDocumentQuery)
                .variables(Map.of("input", this.objectMapper.convertValue(createDocumentInput, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        var createDocumentExecutionResult = this.graphQL.execute(createDocumentExecutionInput);
        assertThat(createDocumentExecutionResult.getErrors()).isEmpty();

        UUID documentId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(createDocumentExecutionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createDocument.__typename");
            assertThat(responseTypeName).isEqualTo("CreateDocumentSuccessPayload");

            String rawDocumentId = JsonPath.read(jsonResult, "$.data.createDocument.document.id");
            documentId = UUID.fromString(rawDocumentId);
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

        var createRootObjectInput = new CreateRootObjectInput(UUID.randomUUID(), this.projectId.toString(), documentId, "domain://papaya_core", "Root");

        var createRootObjectExecutionInput = ExecutionInput.newExecutionInput()
                .query(createRootObjectQuery)
                .variables(Map.of("input", this.objectMapper.convertValue(createRootObjectInput, new TypeReference<Map<String, Object>>() {
                })))
                .build();
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

    private void createDiagram() {
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

        var getRepresentationDescriptionsExecutionInput = ExecutionInput.newExecutionInput()
                .query(getRepresentationDescriptionsQuery)
                .variables(Map.of("editingContextId", this.projectId.toString(), "objectId", this.rootObjectId.toString()))
                .build();
        var getRepresentationDescriptionsExecutionResult = this.graphQL.execute(getRepresentationDescriptionsExecutionInput);
        assertThat(getRepresentationDescriptionsExecutionResult.getErrors()).isEmpty();

        String representationDescriptionId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(getRepresentationDescriptionsExecutionResult.toSpecification());
            var matches = (JSONArray) JsonPath.read(jsonResult, "$.data.viewer.editingContext.representationDescriptions.edges[?(@.node.label=='Diagram')].node.id");
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

        var input = new CreateRepresentationInput(UUID.randomUUID(), this.projectId.toString(), representationDescriptionId, this.rootObjectId.toString(), "Diagram");
        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
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
    @DisplayName("Given a domain and a view, when a document and a representation are created, then we can subscribe to the representation")
    public void givenDomainAndViewWhenDocumentAndRepresentationAreCreatedThenWeCanSubscribeToTheRepresentation() {
        var configuration = new DiagramConfiguration(this.representationId.toString());
        var input = new DiagramEventInput(UUID.randomUUID(), this.projectId.toString(), this.representationId.toString());
        var payloadFlux = this.eventProcessorSubscriptionProvider.getSubscription(this.projectId.toString(), configuration, input);

        Predicate<IPayload> isEmptyDiagramRefreshedEventPayload = payload -> {
            if (payload instanceof DiagramRefreshedEventPayload diagramRefreshedEventPayload) {
                var diagram = diagramRefreshedEventPayload.diagram();
                return diagram.getNodes().isEmpty() && diagram.getEdges().isEmpty();
            }
            return false;
        };

        Predicate<IPayload> isInitializedPapayaDiagram = payload -> {
            if (payload instanceof DiagramRefreshedEventPayload diagramRefreshedEventPayload) {
                var diagram = diagramRefreshedEventPayload.diagram();
                return diagram.getNodes().size() == 15 && diagram.getEdges().size() == 59;
            }
            return false;
        };

        StepVerifier.create(payloadFlux)
                .expectNextMatches(isEmptyDiagramRefreshedEventPayload)
                .then(this::initializeDiagram)
                .expectNextMatches(isInitializedPapayaDiagram)
                .thenCancel()
                .verify(Duration.ofSeconds(5));
    }

    private void initializeDiagram() {
        var query = """
                query getToolSections($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
                  viewer {
                    editingContext(editingContextId: $editingContextId) {
                      representation(representationId: $diagramId) {
                        description {
                          ... on DiagramDescription {
                           palette(diagramElementId: $diagramElementId) {
                            id
                            tools {
                              id
                              label
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
                """;

        var variables = Map.<String, Object>of(
                "editingContextId", this.projectId.toString(),
                "diagramId", this.representationId.toString(),
                "diagramElementId", this.representationId.toString()
        );

        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(variables)
                .build();
        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        String toolId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            toolId = JsonPath.read(jsonResult, "$.data.viewer.editingContext.representation.description.palette.tools[0].id");
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }

        if (toolId != null) {
            var invokeToolQuery = """
                    mutation invokeSingleClickOnDiagramElementTool($input: InvokeSingleClickOnDiagramElementToolInput!) {
                      invokeSingleClickOnDiagramElementTool(input: $input) {
                        __typename
                      }
                    }
                    """;

            var invokeToolInput = new InvokeSingleClickOnDiagramElementToolInput(
                    UUID.randomUUID(),
                    this.projectId.toString(),
                    this.representationId.toString(),
                    this.representationId.toString(),
                    toolId,
                    0d,
                    0d,
                    null
            );

            var invokeToolExecutionInput = ExecutionInput.newExecutionInput()
                    .query(invokeToolQuery)
                    .variables(Map.of("input", this.objectMapper.convertValue(invokeToolInput, new TypeReference<Map<String, Object>>() {
                    })))
                    .build();
            var invokeToolExecutionResult = this.graphQL.execute(invokeToolExecutionInput);
            assertThat(invokeToolExecutionResult.getErrors()).isEmpty();

            try {
                var jsonResult = this.objectMapper.writeValueAsString(invokeToolExecutionResult.toSpecification());
                String responseTypeName = JsonPath.read(jsonResult, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
                assertThat(responseTypeName).isEqualTo("InvokeSingleClickOnDiagramElementToolSuccessPayload");
            } catch (JsonProcessingException exception) {
                fail(exception.getMessage());
            }
        }
    }
}
