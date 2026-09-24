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

import java.util.Map;

import org.eclipse.sirius.components.flow.starter.services.FlowStyleCustomizationDescriptionProvider;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.FlowIdentifier;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.ProjectStyleCustomizationsAssert;
import org.eclipse.sirius.web.tests.graphql.ProjectStyleCustomizationsQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import graphql.relay.Relay;

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
    @DisplayName("Given a set of style customizations of a project, When a valid first query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfProjectWhenValidFirstQueryIsPerformedThenStyleCustomizationsAreReturned() {
        Map<String, Object> variables = Map.of("projectId", FlowIdentifier.PROJECT_ID, "first", 1);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        new ProjectStyleCustomizationsAssert(result)
                .hasNoErrors()
                .hasPageInfo(pageInfo -> pageInfo
                        .hasNoPreviousPage()
                        .hasNextPage()
                        .hasNonBlankStartCursor()
                        .hasNonBlankEndCursor()
                        .hasCount(1))
                .hasStyleCustomizationIds(FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a project, When a valid last query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfProjectWhenValidLastQueryIsPerformedThenStyleCustomizationsAreReturned() {
        Map<String, Object> variables = Map.of("projectId", FlowIdentifier.PROJECT_ID, "last", 1);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        new ProjectStyleCustomizationsAssert(result)
                .hasNoErrors()
                .hasPageInfo(pageInfo -> pageInfo
                        .hasPreviousPage()
                        .hasNoNextPage()
                        .hasNonBlankStartCursor()
                        .hasNonBlankEndCursor()
                        .hasCount(1))
                .hasStyleCustomizationIds(FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_DA_BE_DI_DA_BE_DAI);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a project, When a 0 first query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfProjectWhenA0FirstQueryIsPerformedThenStyleCustomizationsAreReturned() {
        Map<String, Object> variables = Map.of("projectId", FlowIdentifier.PROJECT_ID, "first", 0);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        new ProjectStyleCustomizationsAssert(result)
                .hasNoErrors()
                .hasPageInfo(pageInfo -> pageInfo
                        .hasNoPreviousPage()
                        .hasNoNextPage()
                        .hasBlankStartCursor()
                        .hasBlankEndCursor()
                        .hasCount(0))
                .hasStyleCustomizationIds();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a project, When a 0 last query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfProjectWhenA0LastQueryIsPerformedThenStyleCustomizationsAreReturned() {
        Map<String, Object> variables = Map.of("projectId", FlowIdentifier.PROJECT_ID, "last", 0);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        new ProjectStyleCustomizationsAssert(result)
                .hasNoErrors()
                .hasPageInfo(pageInfo -> pageInfo
                        .hasNoPreviousPage()
                        .hasNoNextPage()
                        .hasBlankStartCursor()
                        .hasBlankEndCursor()
                        .hasCount(0))
                .hasStyleCustomizationIds();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a project, When a valid after query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfProjectWhenValidAfterQueryIsPerformedThenStyleCustomizationsAreReturned() {
        var cursorProjectId = new Relay().toGlobalId("StyleCustomization", FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE);
        Map<String, Object> variables = Map.of("projectId", FlowIdentifier.PROJECT_ID, "after", cursorProjectId);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        new ProjectStyleCustomizationsAssert(result)
                .hasNoErrors()
                .hasPageInfo(pageInfo -> pageInfo
                        .hasPreviousPage()
                        .hasNoNextPage()
                        .hasNonBlankStartCursor()
                        .hasNonBlankEndCursor()
                        .hasCount(1))
                .hasStyleCustomizationIds(FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_DA_BE_DI_DA_BE_DAI);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a project, When a valid before query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfProjectWhenValidBeforeQueryIsPerformedThenStyleCustomizationsAreReturned() {
        var cursorProjectId = new Relay().toGlobalId("StyleCustomization", FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_DA_BE_DI_DA_BE_DAI);
        Map<String, Object> variables = Map.of("projectId", FlowIdentifier.PROJECT_ID, "before", cursorProjectId);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        new ProjectStyleCustomizationsAssert(result)
                .hasNoErrors()
                .hasPageInfo(pageInfo -> pageInfo
                        .hasNoPreviousPage()
                        .hasNextPage()
                        .hasNonBlankStartCursor()
                        .hasNonBlankEndCursor()
                        .hasCount(1))
                .hasStyleCustomizationIds(FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a project, When an invalid before query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfProjectWhenInvalidBeforeQueryIsPerformedThenStyleCustomizationsAreReturned() {
        var cursorProjectId = new Relay().toGlobalId("StyleCustomization", "projectId");
        Map<String, Object> variables = Map.of("projectId", FlowIdentifier.PROJECT_ID, "before", cursorProjectId);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        new ProjectStyleCustomizationsAssert(result)
                .hasNoErrors()
                .hasPageInfo(pageInfo -> pageInfo
                        .hasNoPreviousPage()
                        .hasNoNextPage()
                        .hasNonBlankStartCursor()
                        .hasNonBlankEndCursor()
                        .hasCount(2))
                .hasStyleCustomizationIds(
                        FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE,
                        FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_DA_BE_DI_DA_BE_DAI);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a set of style customizations of a studio project, When a valid query is performed, then the style customizations are returned")
    public void givenSetOfStyleCustomizationsOfStudioProjectWhenValidQueryIsPerformedThenStyleCustomizationsAreReturned() {
        Map<String, Object> variables = Map.of("projectId", StudioIdentifiers.SAMPLE_STUDIO_PROJECT);
        var result = this.projectStyleCustomizationsQueryRunner.run(variables);

        new ProjectStyleCustomizationsAssert(result)
                .hasNoErrors()
                .hasPageInfo(pageInfo -> pageInfo
                        .hasNoPreviousPage()
                        .hasNoNextPage()
                        .hasBlankStartCursor()
                        .hasBlankEndCursor()
                        .hasCount(0))
                .hasStyleCustomizationIds();
    }

}
