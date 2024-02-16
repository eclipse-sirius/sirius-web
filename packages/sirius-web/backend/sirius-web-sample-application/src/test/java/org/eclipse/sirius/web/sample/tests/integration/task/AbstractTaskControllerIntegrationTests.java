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
package org.eclipse.sirius.web.sample.tests.integration.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.task.starter.configuration.TaskProjectTemplatesProvider;
import org.eclipse.sirius.web.sample.tests.integration.AbstractIntegrationTests;
import org.eclipse.sirius.web.services.api.document.CreateDocumentInput;
import org.eclipse.sirius.web.services.api.projects.CreateProjectFromTemplateInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;

/**
 * Abstract class for integration tests for gantt and deck controllers.
 *
 * @author lfasani
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractTaskControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String CREATE_PROJECT_FROM_TEMPLATE_MUTATION = """
            mutation createProjectFromTemplate($input: CreateProjectFromTemplateInput!) {
              createProjectFromTemplate(input: $input) {
                __typename
                ... on CreateProjectFromTemplateSuccessPayload {
                  project {
                    id
                  }
                  representationToOpen {
                    id
                  }
                }
              }
            }
            """;

    private static final String CREATE_REPRESENTATION_MUTATION = """
            mutation createRepresentation($input: CreateRepresentationInput!) {
              createRepresentation(input: $input) {
                __typename
                ... on CreateRepresentationSuccessPayload {
                  representation {
                    id
                    label
                    kind
                    __typename
                  }
                  __typename
                }
                ... on ErrorPayload {
                  message
                  __typename
                }
              }
            }
            """;

    private static final String CREATE_DOCUMENT_MUTATION = """
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

    protected String projectId;

    protected IEMFEditingContext editingDomain;

    @Autowired
    private GraphQL graphQL;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IObjectService objectService;

    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream().map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    protected void createTaskProject() {
        var input = new CreateProjectFromTemplateInput(UUID.randomUUID(), TaskProjectTemplatesProvider.TASK_EXAMPLE_TEMPLATE_ID);
        var executionInput = ExecutionInput.newExecutionInput()
                .query(CREATE_PROJECT_FROM_TEMPLATE_MUTATION)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        var executionResult = this.graphQL.execute(executionInput);

        String jsonResult = null;
        try {
            jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            this.projectId = JsonPath.read(jsonResult, "$.data.createProjectFromTemplate.project.id");

            var optionalEditingContext = this.editingContextSearchService.findById(this.projectId);
            assertThat(optionalEditingContext).isPresent();

            this.editingDomain = (IEMFEditingContext) optionalEditingContext.get();
            assertThat(this.editingDomain.getDomain().getResourceSet().getResources()).hasSize(1);

        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    protected void createStudio(UUID stereotypeDescriptionId) {
        var input = new CreateProjectFromTemplateInput(UUID.randomUUID(), TaskProjectTemplatesProvider.TASK_EXAMPLE_TEMPLATE_ID);
        var executionInput = ExecutionInput.newExecutionInput()
                .query(CREATE_PROJECT_FROM_TEMPLATE_MUTATION)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        var executionResult = this.graphQL.execute(executionInput);

        try {
            String jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());
            String studioProjectId = JsonPath.read(jsonResult, "$.data.createProjectFromTemplate.project.id");

            Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(studioProjectId);
            CreateDocumentInput createDocumentInput = new CreateDocumentInput(UUID.randomUUID(), optionalEditingContext.get().getId(), "views", stereotypeDescriptionId);
            executionInput = ExecutionInput.newExecutionInput()
                    .query(CREATE_DOCUMENT_MUTATION)
                    .variables(Map.of("input", this.objectMapper.convertValue(createDocumentInput, new TypeReference<Map<String, Object>>() {
                    })))
                    .build();
            this.graphQL.execute(executionInput);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }


    protected String getTaskProjectId(String taskName) {
        Iterable<EObject> allObjects = () -> this.editingDomain.getDomain().getResourceSet().getResources().get(0).getAllContents();
        Optional<EObject> taskProject = StreamSupport.stream(allObjects.spliterator(), false)
                .filter(eObject -> this.objectService.getLabel(eObject).equals(taskName))
                .findFirst();

        return this.objectService.getId(taskProject.get());
    }


    protected String createRepresentation(String representationDescriptionId, String taskProjectId, String reprensentationName) {
        String representationId = "";
        var input = new CreateRepresentationInput(UUID.randomUUID(), this.projectId, representationDescriptionId, taskProjectId, reprensentationName);
        var executionInput = ExecutionInput.newExecutionInput()
                .query(CREATE_REPRESENTATION_MUTATION)
                .variables(Map.of("input", this.objectMapper.convertValue(input, new TypeReference<Map<String, Object>>() {
                })))
                .build();
        ExecutionResult executionResult = this.graphQL.execute(executionInput);

        try {
            String jsonResult = this.objectMapper.writeValueAsString(executionResult.toSpecification());

            representationId = JsonPath.read(jsonResult, "$.data.createRepresentation.representation.id");
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
        return representationId;
    }
}
