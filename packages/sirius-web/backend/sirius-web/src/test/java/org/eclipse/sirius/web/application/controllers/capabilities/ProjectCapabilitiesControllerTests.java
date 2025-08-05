/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.controllers.capabilities;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateProjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.DeleteProjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.ProjectCapabilitiesQueryRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of Project capabilities.
 *
 * @author gcoutable
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "demo")
public class ProjectCapabilitiesControllerTests extends AbstractIntegrationTests {

    @Autowired
    private ProjectCapabilitiesQueryRunner projectCapabilitiesQueryRunner;

    @Autowired
    private CreateProjectMutationRunner createProjectMutationRunner;

    @Autowired
    private DeleteProjectMutationRunner deleteProjectMutationRunner;

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private IMessageService messageService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a query to retrieve capabilities on a project is executed, then it returns the expected capabilities values")
    public void givenTheDemoProfileWhenAQueryToRetrieveCapabilitiesOnAProjectIsExecutedThenItReturnsTheExpectedCapabilitiesValues() {
        var result = this.projectCapabilitiesQueryRunner.run(Map.of("projectId", TestIdentifiers.SYSML_SAMPLE_PROJECT, "tabIds", List.of()));
        boolean canDownload = JsonPath.read(result, "$.data.viewer.project.capabilities.canDownload");
        boolean canRename = JsonPath.read(result, "$.data.viewer.project.capabilities.canRename");
        boolean canDelete = JsonPath.read(result, "$.data.viewer.project.capabilities.canDelete");
        boolean canEdit = JsonPath.read(result, "$.data.viewer.project.capabilities.canEdit");
        boolean canViewSettings = JsonPath.read(result, "$.data.viewer.project.capabilities.settings.canView");
        assertThat(canDownload).isFalse();
        assertThat(canRename).isFalse();
        assertThat(canDelete).isFalse();
        assertThat(canEdit).isFalse();
        assertThat(canViewSettings).isFalse();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a query to retrieve capabilities the settings of a project is executed, then it returns the expected capabilities values")
    public void givenTheDemoProfileWhenAQueryToRetrieveTheSettingsOfAProjectIsExecutedThenItReturnsTheExpectedCapabilitiesValue() {
        var result = this.projectCapabilitiesQueryRunner.run(Map.of("projectId", TestIdentifiers.SYSML_SAMPLE_PROJECT, "tabIds", List.of(SiriusWebCapabilities.PROJECT_SETTINGS_IMAGE_TAB)));
        List<Boolean> canViewProjectTabSettings = JsonPath.read(result, "$.data.viewer.project.capabilities.settings.tabs[*].canView");
        List<String> tabIds = JsonPath.read(result, "$.data.viewer.project.capabilities.settings.tabs[*].tabId");
        assertThat(canViewProjectTabSettings).allMatch(Boolean.FALSE::equals);
        assertThat(tabIds).contains(SiriusWebCapabilities.PROJECT_SETTINGS_IMAGE_TAB);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a mutation to create a project is performed, then it returns an error payload")
    public void givenTheDemoProfileWhenAMutationToCreateAProjectIsPerformedThenItReturnsAnErrorPayload() {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", List.of());
        var result = this.createProjectMutationRunner.run(input);

        String typeName = JsonPath.read(result, "$.data.createProject.__typename");
        assertThat(typeName).isEqualTo(ErrorPayload.class.getSimpleName());

        String message = JsonPath.read(result, "$.data.createProject.message");
        assertThat(message).isEqualTo(this.messageService.unauthorized());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a mutation to delete a project is performed, then it returns an error payload")
    public void givenTheDemoProfileWhenAMutationToDeleteAProjectIsPerformedThenItReturnsAnErrorPayload() {
        var input = new DeleteProjectInput(UUID.randomUUID(), TestIdentifiers.SYSML_SAMPLE_PROJECT);
        var result = this.deleteProjectMutationRunner.run(input);

        String typeName = JsonPath.read(result, "$.data.deleteProject.__typename");
        assertThat(typeName).isEqualTo(ErrorPayload.class.getSimpleName());

        String message = JsonPath.read(result, "$.data.deleteProject.message");
        assertThat(message).isEqualTo(this.messageService.unauthorized());

        var project = this.projectRepository.findById(TestIdentifiers.SYSML_SAMPLE_PROJECT);
        assertThat(project).isPresent();
    }

}
