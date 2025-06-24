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

import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ProjectCapabilitiesQueryRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of Project capabilities.
 *
 * @author gcoutable
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectCapabilitiesControllerTests extends AbstractIntegrationTests {

    @Autowired
    private ProjectCapabilitiesQueryRunner projectCapabilitiesQueryRunner;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a server, when a query to retrieve capabilities of a project is executed, then it return true by default")
    public void givenAServerWhenQueryToRetrieveCapabilitiesIsExecutedThenItReturnsTrueByDefault() {
        var result = this.projectCapabilitiesQueryRunner.run(Map.of("projectId", TestIdentifiers.ECORE_SAMPLE_PROJECT));
        boolean canDownload = JsonPath.read(result, "$.data.viewer.project.capabilities.canDownload");
        assertThat(canDownload).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a capability voter that deny the project download on a specific project, when a query to retrieve capabilities on that specific project is executed, then the canDownload is false")
    public void givenACapabilityVoterThatDenyTheProjectDownloadOnASpecificProjectWhenAQueryToRetrieveCapabilitiesOnThatSpecificProjectIsExecutedThenTheCanDownloadIsFalse() {
        var result = this.projectCapabilitiesQueryRunner.run(Map.of("projectId", TestIdentifiers.SYSML_SAMPLE_PROJECT));
        boolean canDownload = JsonPath.read(result, "$.data.viewer.project.capabilities.canDownload");
        assertThat(canDownload).isFalse();
    }

}
