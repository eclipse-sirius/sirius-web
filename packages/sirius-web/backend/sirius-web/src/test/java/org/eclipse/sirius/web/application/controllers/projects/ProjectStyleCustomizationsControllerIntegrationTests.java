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

import java.util.UUID;

import org.eclipse.sirius.components.flow.starter.services.FlowStyleCustomizationDescriptionProvider;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.FlowIdentifier;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.UpdateProjectStyleCustomizationStateInput;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.events.ProjectStyleCustomizationCreatedEvent;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.events.ProjectStyleCustomizationDeletedEvent;
import org.eclipse.sirius.web.projects.stylecustomizations.domain.services.api.IProjectStyleCustomizationSearchService;
import org.eclipse.sirius.web.services.api.IDomainEventCollector;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.UpdateProjectStyleCustomizationStateExecutor;
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
    private UpdateProjectStyleCustomizationStateExecutor updateProjectStyleCustomizationStateExecutor;

    @Autowired
    private IDomainEventCollector domainEventCollector;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
        this.domainEventCollector.clear();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a disabled project style customization, when it is enabled, then it becomes active")
    public void givenDisabledProjectStyleCustomizationWhenEnabledThenItBecomesActive(CapturedOutput capturedOutput) {
        String arbitraryProjectStyleCustomizationDescriptionId = "does-not-really-exists";
        assertThat(this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(FlowIdentifier.PROJECT_ID), arbitraryProjectStyleCustomizationDescriptionId)).isFalse();

        var input = new UpdateProjectStyleCustomizationStateInput(UUID.randomUUID(), FlowIdentifier.PROJECT_ID, arbitraryProjectStyleCustomizationDescriptionId, true);
        this.updateProjectStyleCustomizationStateExecutor.execute(input, capturedOutput).isSuccess();

        assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(ProjectStyleCustomizationCreatedEvent.class::isInstance);
        assertThat(this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(FlowIdentifier.PROJECT_ID), arbitraryProjectStyleCustomizationDescriptionId)).isTrue();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a enabled project style customization, when it is disabled, then it becomes inactive")
    public void givenEnabledProjectStyleCustomizationWhenDisabledThenItBecomeInactive(CapturedOutput capturedOutput) {
        assertThat(this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(FlowIdentifier.PROJECT_ID), FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE)).isTrue();

        var input = new UpdateProjectStyleCustomizationStateInput(UUID.randomUUID(), FlowIdentifier.PROJECT_ID, FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE, false);
        this.updateProjectStyleCustomizationStateExecutor.execute(input, capturedOutput).isSuccess();

        assertThat(this.domainEventCollector.getDomainEvents()).anyMatch(ProjectStyleCustomizationDeletedEvent.class::isInstance);
        assertThat(this.projectStyleCustomizationSearchService.existsByProjectIdAndStyleCustomizationDescriptionId(AggregateReference.to(FlowIdentifier.PROJECT_ID), FlowStyleCustomizationDescriptionProvider.FLOW_STYLE_CUSTOMIZATION_I_M_BLUE)).isFalse();
    }
}
