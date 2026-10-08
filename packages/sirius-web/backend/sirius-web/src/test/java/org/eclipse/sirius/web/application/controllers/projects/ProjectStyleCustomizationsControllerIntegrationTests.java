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
package org.eclipse.sirius.web.application.controllers.projects;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.flow.starter.services.FlowProjectTemplatesProvider;
import org.eclipse.sirius.components.flow.starter.services.FlowStyleCustomizationDescriptionProvider;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationSearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateProjectExecutor;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for project style customizations.
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
    private IProjectStyleCustomizationSearchService projectStyleCustomizationSearchService;

    @Autowired
    private CreateProjectExecutor createProjectExecutor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the initial server state, when a flow project is created, then flow related project style customization are enabled")
    public void givenTheInitialServerStateWhenFlowProjectIsCreatedThenFlowRelatedProjectStyleCustomizationsAreEnabled(CapturedOutput capturedOutput) {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Flow Project", FlowProjectTemplatesProvider.FLOW_TEMPLATE_ID, List.of());
        var createdProjectId = this.createProjectExecutor.execute(input, capturedOutput).isSuccess().getProjectId();

        assertThat(this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(createdProjectId), FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE)).isTrue();
        assertThat(this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(createdProjectId), FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_DA_BE_DI_DA_BE_DAI)).isTrue();
    }
}
