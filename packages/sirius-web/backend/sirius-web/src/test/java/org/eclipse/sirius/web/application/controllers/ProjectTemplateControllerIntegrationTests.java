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
package org.eclipse.sirius.web.application.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateSuccessPayload;
import org.eclipse.sirius.web.application.studio.services.StudioProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.IGraphQLRequestor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the project templates controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.enabled=*" })
public class ProjectTemplateControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String GET_PROJECT_TEMPLATES_QUERY = """
            query getProjectTemplates($page: Int!, $limit: Int!) {
              viewer {
                projectTemplates(page: $page, limit: $limit) {
                  edges {
                    node {
                      id
                      label
                      imageURL
                    }
                  }
                  pageInfo {
                    hasPreviousPage
                    hasNextPage
                    startCursor
                    endCursor
                    count
                  }
                }
              }
            }
            """;

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

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    @BeforeEach
    public void beforeEach() {
        this.editingContextEventProcessorRegistry.getEditingContextEventProcessors().stream()
                .map(IEditingContextEventProcessor::getEditingContextId)
                .forEach(this.editingContextEventProcessorRegistry::disposeEditingContextEventProcessor);
    }

    @Test
    @DisplayName("Given a set of project templates, when a query is performed, then the project templates are returned")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSetOfProjectTemplatesWhenQueryIsPerformedThenTheProjectTemplatesAreReturned() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 2);
        var result = this.graphQLRequestor.execute(GET_PROJECT_TEMPLATES_QUERY, variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasNextPage");
        assertThat(hasNextPage).isFalse();

        String startCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.count");
        assertThat(count).isGreaterThan(0);

        List<String> projectTemplateIds = JsonPath.read(result, "$.data.viewer.projectTemplates.edges[*].node.id");
        assertThat(projectTemplateIds).hasSizeGreaterThan(0);
    }

    @Test
    @DisplayName("Given a project to create from a template, when the mutation is performed, then the project is created")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectToCreateFromTemplateWhenMutationIsPerformedThenTheProjectIsCreated() {
        TestTransaction.flagForCommit();
        TestTransaction.end();

        var input = new CreateProjectFromTemplateInput(UUID.randomUUID(), StudioProjectTemplateProvider.STUDIO_TEMPLATE_ID);
        var result = this.graphQLRequestor.execute(CREATE_PROJECT_FROM_TEMPLATE_MUTATION, input);

        String typename = JsonPath.read(result, "$.data.createProjectFromTemplate.__typename");
        assertThat(typename).isEqualTo(CreateProjectFromTemplateSuccessPayload.class.getSimpleName());

        String projectId = JsonPath.read(result, "$.data.createProjectFromTemplate.project.id");
        assertThat(projectId).isNotBlank();

        var optionalEditingContext = this.editingContextSearchService.findById(projectId);
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        assertThat(editingContext).isInstanceOf(IEMFEditingContext.class);

        var emfEditingContext = (IEMFEditingContext) editingContext;
        assertThat(emfEditingContext.getDomain().getResourceSet().getResources()).hasSize(3);
    }
}
