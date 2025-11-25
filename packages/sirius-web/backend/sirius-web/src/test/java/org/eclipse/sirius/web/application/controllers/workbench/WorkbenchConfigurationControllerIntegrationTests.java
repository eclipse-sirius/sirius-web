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
package org.eclipse.sirius.web.application.controllers.workbench;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.EditingContextWorkbenchConfigurationQueryRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the workbench configuration.
 *
 * @author gcoutable
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class WorkbenchConfigurationControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private EditingContextWorkbenchConfigurationQueryRunner editingContextWorkbenchConfigurationQueryRunner;

    @Autowired
    private IGraphQLRequestor graphQLRequestor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when workbench configuration is requested, then it returns the default configuration")
    public void givenProjectWhenWorkbenchConfigurationIsRequestedThenItReturnsTheDefaultConfiguration() {
        var result = this.editingContextWorkbenchConfigurationQueryRunner.run(Map.of("editingContextId", TestIdentifiers.UML_SAMPLE_EDITING_CONTEXT_ID));
        List<Object> representationEditors = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.mainPanel.representationEditors");
        List<Object> workbenchPanels = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels");
        assertThat(representationEditors).isEmpty();
        assertThat(workbenchPanels).hasSize(2);

        List<Object> leftPanelViews = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels[?(@['id']=='left')].views[*]");
        List<String> leftPanelViewsIDs = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels[?(@['id']=='left')].views[*].id");
        assertThat(leftPanelViews.size()).isPositive();
        assertThat(leftPanelViewsIDs).containsExactlyInAnyOrder("explorer", "validation", "search");

        List<Object> rightPanelViews = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels[?(@['id']=='right')].views[*]");
        List<String> rightPanelViewsIDs = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels[?(@['id']=='right')].views[*].id");
        assertThat(rightPanelViews.size()).isPositive();
        assertThat(rightPanelViewsIDs).containsExactlyInAnyOrder("details", "query", "representations", "related-elements");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a papaya project, when workbench configuration is requested, then it contains a papaya project specific view")
    public void givenPapayaProjectWhenWorkbenchConfigurationIsRequestedThenItContainsPapayaProjectSpecificView() {
        var result = this.editingContextWorkbenchConfigurationQueryRunner.run(Map.of("editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID));
        List<Object> representationEditors = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.mainPanel.representationEditors");
        List<Object> workbenchPanels = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels");
        assertThat(representationEditors).isEmpty();
        assertThat(workbenchPanels).hasSize(2);

        List<Object> leftPanelViews = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels[?(@['id']=='left')].views[*]");
        List<String> leftPanelViewsIDs = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels[?(@['id']=='left')].views[*].id");
        assertThat(leftPanelViews.size()).isPositive();
        assertThat(leftPanelViewsIDs).containsExactlyInAnyOrder("explorer", "validation", "search");

        List<Object> rightPanelViews = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels[?(@['id']=='right')].views[*]");
        List<String> rightPanelViewsIDs = JsonPath.read(result.data(), "$.data.viewer.editingContext.workbenchConfiguration.workbenchPanels[?(@['id']=='right')].views[*].id");
        assertThat(rightPanelViews.size()).isPositive();
        assertThat(rightPanelViewsIDs).containsExactlyInAnyOrder("details", "query", "representations", "related-elements", "papaya-view");
    }

}
