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

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
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
@ActiveProfiles("demo")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectCapabilitiesControllerTests extends AbstractIntegrationTests {

    @Autowired
    private ProjectCapabilitiesQueryRunner projectCapabilitiesQueryRunner;

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
}
