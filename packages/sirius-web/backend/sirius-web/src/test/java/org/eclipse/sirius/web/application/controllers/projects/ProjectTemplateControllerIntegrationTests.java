/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.CreateProjectSuccessPayload;
import org.eclipse.sirius.web.application.project.dto.ProjectTemplateContext;
import org.eclipse.sirius.web.application.project.services.BlankProjectTemplateProvider;
import org.eclipse.sirius.web.application.studio.services.StudioProjectTemplateProvider;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.papaya.projecttemplates.PapayaProjectTemplateProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateProjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.ProjectTemplatesQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
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

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ProjectTemplatesQueryRunner projectTemplatesQueryRunner;

    @Autowired
    private CreateProjectMutationRunner createProjectMutationRunner;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of project templates, when a query is performed, then the project templates are returned")
    public void givenSetOfProjectTemplatesWhenQueryIsPerformedThenTheProjectTemplatesAreReturned() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 10, "context", ProjectTemplateContext.PROJECT_TEMPLATE_MODAL);
        var result = this.projectTemplatesQueryRunner.run(variables);

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
    @GivenSiriusWebServer
    @DisplayName("Given a set of project templates, when a query asking default templates is performed, then the result contains the default templates")
    public void givenSetOfProjectTemplatesWhenQueryAskingDefaultTemplatesIsPerformedThenTheResultContainsTheDefaultTemplates() {
        Map<String, Object> variables = Map.of("page", 0, "limit", 6, "context", ProjectTemplateContext.PROJECT_BROWSER);
        var result = this.projectTemplatesQueryRunner.run(variables);

        boolean hasPreviousPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasPreviousPage");
        assertThat(hasPreviousPage).isFalse();

        boolean hasNextPage = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.hasNextPage");
        assertThat(hasNextPage).isTrue();

        String startCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.startCursor");
        assertThat(startCursor).isNotBlank();

        String endCursor = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.endCursor");
        assertThat(endCursor).isNotBlank();

        int count = JsonPath.read(result, "$.data.viewer.projectTemplates.pageInfo.count");
        assertThat(count).isGreaterThan(6);

        List<String> projectTemplateIds = JsonPath.read(result, "$.data.viewer.projectTemplates.edges[-3:].node.id");
        assertThat(projectTemplateIds.get(0)).isEqualTo(BlankProjectTemplateProvider.BLANK_PROJECT_TEMPLATE_ID);
        assertThat(projectTemplateIds.get(1)).isEqualTo("upload-project");
        assertThat(projectTemplateIds.get(2)).isEqualTo("browse-all-project-templates");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project to create from a template, when the mutation is performed, then the project is created")
    public void givenProjectToCreateFromTemplateWhenMutationIsPerformedThenTheProjectIsCreated() {
        TestTransaction.flagForCommit();
        TestTransaction.end();

        var input = new CreateProjectInput(UUID.randomUUID(), "Studio", StudioProjectTemplateProvider.STUDIO_TEMPLATE_ID, List.of());
        var result = this.createProjectMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.createProject.__typename");
        assertThat(typename).isEqualTo(CreateProjectSuccessPayload.class.getSimpleName());

        String projectId = JsonPath.read(result, "$.data.createProject.project.id");
        assertThat(projectId).isNotBlank();

        var optionalEditingContext = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(projectId))
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .map(UUID::toString)
                .flatMap(this.editingContextSearchService::findById);

        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        assertThat(editingContext).isInstanceOf(IEMFEditingContext.class);

        var emfEditingContext = (IEMFEditingContext) editingContext;
        assertThat(emfEditingContext.getDomain().getResourceSet().getResources()).hasSize(3);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the papaya project template, when the mutation is performed, then the project is created")
    public void givenPapayaProjectTemplateWhenMutationIsPerformedThenTheProjectIsCreated() {
        TestTransaction.flagForCommit();
        TestTransaction.end();

        var input = new CreateProjectInput(UUID.randomUUID(), "Papaya - Performance", PapayaProjectTemplateProvider.BENCHMARK_PROJECT_TEMPLATE_ID, List.of());
        var result = this.createProjectMutationRunner.run(input);

        String typename = JsonPath.read(result, "$.data.createProject.__typename");
        assertThat(typename).isEqualTo(CreateProjectSuccessPayload.class.getSimpleName());

        String projectId = JsonPath.read(result, "$.data.createProject.project.id");
        assertThat(projectId).isNotBlank();

        var optionalEditingContext = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(projectId))
                .map(ProjectSemanticData::getSemanticData)
                .map(AggregateReference::getId)
                .map(UUID::toString)
                .flatMap(this.editingContextSearchService::findById);

        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        assertThat(editingContext).isInstanceOf(IEMFEditingContext.class);

        var emfEditingContext = (IEMFEditingContext) editingContext;
        assertThat(emfEditingContext.getDomain().getResourceSet().getResources()).hasSizeGreaterThan(10);
    }
}
