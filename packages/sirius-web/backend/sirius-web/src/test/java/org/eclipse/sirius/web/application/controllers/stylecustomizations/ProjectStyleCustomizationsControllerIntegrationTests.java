/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.stylecustomizations;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.FlowIdentifier;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ProjectStyleCustomizationsQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the project style customizations controller.
 *
 * @author gcoutable
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.style.customization.enabled=true" })
public class ProjectStyleCustomizationsControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private ProjectStyleCustomizationsQueryRunner projectStyleCustomizationsQueryRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a flow project, When a valid query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfFlowProjectWhenValidQueryIsPerformedThenStyleCustomizationsAreReturned() {
        Map<String, Object> variables = Map.of("projectId", FlowIdentifier.PROJECT_ID);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        List<String> styleCustomizationIds = JsonPath.read(result.data(), "$.data.viewer.project.styleCustomizations[*].id");
        assertThat(styleCustomizationIds).hasSize(2);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a studio project, When a valid query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfStudioProjectWhenValidQueryIsPerformedThenStyleCustomizationsAreReturned() {
        Map<String, Object> variables = Map.of("projectId", StudioIdentifiers.SAMPLE_STUDIO_PROJECT);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        List<String> styleCustomizationIds = JsonPath.read(result.data(), "$.data.viewer.project.styleCustomizations[*].id");
        assertThat(styleCustomizationIds).hasSize(0);
    }

}
