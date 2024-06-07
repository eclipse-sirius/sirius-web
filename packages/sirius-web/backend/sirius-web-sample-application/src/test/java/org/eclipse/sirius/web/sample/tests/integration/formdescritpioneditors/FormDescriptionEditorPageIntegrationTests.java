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
package org.eclipse.sirius.web.sample.tests.integration.formdescritpioneditors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.components.collaborative.editingcontext.EditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.api.FormDescriptionEditorConfiguration;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddPageInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.DeletePageInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorEventInput;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.FormDescriptionEditorRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.MovePageInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.forms.Page;
import org.eclipse.sirius.components.graphql.api.IEventProcessorSubscriptionProvider;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.sample.configuration.StereotypeRegistryConfigurer;
import org.eclipse.sirius.web.sample.configuration.StudioProjectTemplatesProvider;
import org.eclipse.sirius.web.sample.tests.integration.AbstractIntegrationTests;
import org.eclipse.sirius.web.services.api.document.CreateDocumentInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectFromTemplateInput;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
 * Integration tests of the page concept in form description editor.
 *
 * @author frouene
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormDescriptionEditorPageIntegrationTests extends AbstractIntegrationTests {

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

    private UUID formDescriptionObjectId;

    private UUID representationId;

    //To use spring service with scope request, we simulate it.
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
        this.createProject();
        this.createProjectContent();
        this.createFormDescriptor();
        this.createFormDescriptionEditor();
    }

    @AfterEach
    public void teardown() {
        this.editingContextEventProcessorRegistry.dispose();
        this.projectRepository.deleteAll();
    }


    private void createProject() {
        var query = """
                mutation createProjectFromTemplate($input: CreateProjectFromTemplateInput!) {
                  createProjectFromTemplate(input: $input) {
                    __typename
                    ... on CreateProjectFromTemplateSuccessPayload {
                      project {
                        id
                      }
                    }
                  }
                }
                """;

        var input = new CreateProjectFromTemplateInput(UUID.randomUUID(), StudioProjectTemplatesProvider.STUDIO_TEMPLATE_ID);

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

            String rawProjectId = JsonPath.read(jsonResult, "$.data.createProjectFromTemplate.project.id");
            this.projectId = UUID.fromString(rawProjectId);
        } catch (JsonProcessingException exception) {
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

        var createDocumentInput = new CreateDocumentInput(UUID.randomUUID(), this.projectId.toString(), StereotypeRegistryConfigurer.EMPTY_VIEW_LABEL, StereotypeRegistryConfigurer.EMPTY_VIEW_ID);

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
                    ... on ErrorPayload {
                        message
                    }
                  }
                }
                """;

        var createRootObjectInput = new CreateRootObjectInput(UUID.randomUUID(), this.projectId.toString(), documentId, "http://www.eclipse.org/sirius-web/view", "View");

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

    private void createFormDescriptor() {
        var createChildQuery = """
                mutation createChild($input: CreateChildInput!) {
                  createChild(input: $input) {
                    __typename
                    ... on CreateChildSuccessPayload {
                      object {
                        id
                      }
                    }
                    ... on ErrorPayload {      message      __typename    }
                  }
                }
                """;

        var createChildInput = new CreateChildInput(UUID.randomUUID(), this.projectId.toString(), this.rootObjectId.toString(), "Form Description");

        var createChildExecutionInput = ExecutionInput.newExecutionInput()
                .query(createChildQuery)
                .variables(Map.of("input", this.objectMapper.convertValue(createChildInput, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        var createChildExecutionResult = this.graphQL.execute(createChildExecutionInput);
        assertThat(createChildExecutionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(createChildExecutionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.createChild.__typename");
            assertThat(responseTypeName).isEqualTo("CreateChildSuccessPayload");

            String rawObjectId = JsonPath.read(jsonResult, "$.data.createChild.object.id");
            this.formDescriptionObjectId = UUID.fromString(rawObjectId);
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }
    }

    private void createFormDescriptionEditor() {
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
                .variables(Map.of("editingContextId", this.projectId.toString(), "objectId", this.formDescriptionObjectId.toString()))
                .build();
        var getRepresentationDescriptionsExecutionResult = this.graphQL.execute(getRepresentationDescriptionsExecutionInput);
        assertThat(getRepresentationDescriptionsExecutionResult.getErrors()).isEmpty();

        String representationDescriptionId = null;
        try {
            var jsonResult = this.objectMapper.writeValueAsString(getRepresentationDescriptionsExecutionResult.toSpecification());
            var matches = (JSONArray) JsonPath.read(jsonResult, "$.data.viewer.editingContext.representationDescriptions.edges[?(@.node.label=='FormDescriptionEditor')].node.id");
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

        var input = new CreateRepresentationInput(UUID.randomUUID(), this.projectId.toString(), representationDescriptionId, this.formDescriptionObjectId.toString(),
                "FormDescriptionEditor");
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
    public void testPageMutations() {
        var configuration = new FormDescriptionEditorConfiguration(this.representationId.toString());
        var input = new FormDescriptionEditorEventInput(UUID.randomUUID(), this.projectId.toString(), this.representationId.toString());
        var payloadFlux = this.eventProcessorSubscriptionProvider.getSubscription(this.projectId.toString(), configuration, input);
        AtomicReference<String> defaultPageId = new AtomicReference<>();
        AtomicReference<String> newPageId = new AtomicReference<>();

        Predicate<IPayload> isInitiatedWithOnePageFormDescriptionEditorRefreshedEventPayload = payload -> {
            if (payload instanceof FormDescriptionEditorRefreshedEventPayload formDescriptionEditorRefreshedEventPayload) {
                var formDescriptionEditor = formDescriptionEditorRefreshedEventPayload.formDescriptionEditor();
                defaultPageId.set(formDescriptionEditor.getPages().get(0).getId());
                return formDescriptionEditor.getPages().size() == 1;
            }
            return false;
        };

        Predicate<IPayload> afterAddPageFormDescriptionEditorRefreshedEventPayload = payload -> {
            if (payload instanceof FormDescriptionEditorRefreshedEventPayload formDescriptionEditorRefreshedEventPayload) {
                var formDescriptionEditor = formDescriptionEditorRefreshedEventPayload.formDescriptionEditor();
                newPageId.set(formDescriptionEditor.getPages().get(1).getId());
                return formDescriptionEditor.getPages().size() == 2 && formDescriptionEditor.getPages().stream().map(Page::getId).toList().indexOf(defaultPageId.get()) == 0;
            }
            return false;
        };

        Predicate<IPayload> afterMovePageFormDescriptionEditorRefreshedEventPayload = payload -> {
            if (payload instanceof FormDescriptionEditorRefreshedEventPayload formDescriptionEditorRefreshedEventPayload) {
                var formDescriptionEditor = formDescriptionEditorRefreshedEventPayload.formDescriptionEditor();
                return formDescriptionEditor.getPages().size() == 2 && formDescriptionEditor.getPages().stream().map(Page::getId).toList().indexOf(newPageId.get()) == 0;
            }
            return false;
        };

        Predicate<IPayload> afterDeletePageFormDescriptionEditorRefreshedEventPayload = payload -> {
            if (payload instanceof FormDescriptionEditorRefreshedEventPayload formDescriptionEditorRefreshedEventPayload) {
                var formDescriptionEditor = formDescriptionEditorRefreshedEventPayload.formDescriptionEditor();
                return formDescriptionEditor.getPages().size() == 1 && !formDescriptionEditor.getPages().stream().map(Page::getId).toList().contains(newPageId.get());
            }
            return false;
        };


        StepVerifier.create(payloadFlux)
                .expectNextMatches(isInitiatedWithOnePageFormDescriptionEditorRefreshedEventPayload)
                .then(this::addPageMutation)
                .expectNextMatches(afterAddPageFormDescriptionEditorRefreshedEventPayload)
                .then(() -> this.movePageMutation(newPageId.get()))
                .expectNextMatches(afterMovePageFormDescriptionEditorRefreshedEventPayload)
                .then(() -> this.deletePageMutation(newPageId.get()))
                .expectNextMatches(afterDeletePageFormDescriptionEditorRefreshedEventPayload)
                .thenCancel()
                .verify(Duration.ofSeconds(10));

    }

    private void addPageMutation() {
        var query = """
                mutation addPage($input: AddPageInput!) {
                  addPage(input: $input) {
                      __typename
                      ... on SuccessPayload {
                            __typename
                      }
                  }
                }
                """;

        var input = new AddPageInput(UUID.randomUUID(), this.projectId.toString(), this.representationId.toString(), 1);
        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.addPage.__typename");
            assertThat(responseTypeName).isEqualTo("SuccessPayload");
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }

    }

    private void movePageMutation(String pageId) {
        var query = """
                mutation movePage($input: MovePageInput!) {
                  movePage(input: $input) {
                      __typename
                      ... on SuccessPayload {
                            __typename
                      }
                  }
                }
                """;

        var input = new MovePageInput(UUID.randomUUID(), this.projectId.toString(), this.representationId.toString(), pageId, 0);
        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.movePage.__typename");
            assertThat(responseTypeName).isEqualTo("SuccessPayload");
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }
    }

    private void deletePageMutation(String pageId) {
        var query = """
                mutation deletePage($input: DeletePageInput!) {
                  deletePage(input: $input) {
                      __typename
                      ... on SuccessPayload {
                            __typename
                      }
                  }
                }
                """;

        var input = new DeletePageInput(UUID.randomUUID(), this.projectId.toString(), this.representationId.toString(), pageId);
        var executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        var executionResult = this.graphQL.execute(executionInput);
        assertThat(executionResult.getErrors()).isEmpty();

        try {
            var jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String responseTypeName = JsonPath.read(jsonResult, "$.data.deletePage.__typename");
            assertThat(responseTypeName).isEqualTo("SuccessPayload");
        } catch (JsonProcessingException | IllegalArgumentException exception) {
            fail(exception.getMessage());
        }
    }
}
