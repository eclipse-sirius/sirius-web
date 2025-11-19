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

import java.time.Duration;
import java.util.List;

import org.eclipse.sirius.components.collaborative.forms.dto.FormCapabilitiesRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.validation.dto.ValidationRefreshedEventPayload;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.services.workbench.GivenWorkbench;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of the workbench.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class WorkbenchControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private GivenWorkbench givenWorkbench;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when we subscribe to the workbench, then subscriptions are sending the proper payloads")
    public void givenProjectWhenWeSubscribeToTheWorkbenchThenSubscriptionsAreSendingTheProperPayloads() {
        var workbench = this.givenWorkbench.onEditingContext(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString())
                .withSelection(List.of(PapayaIdentifiers.SIRIUS_WEB_DOMAIN_OBJECT.toString()))
                .build();

        var flux = workbench.asFlux();
        StepVerifier.create(flux)
                .expectNextMatches(TreeRefreshedEventPayload.class::isInstance)
                .expectNextMatches(ValidationRefreshedEventPayload.class::isInstance)
                .expectNextMatches(FormCapabilitiesRefreshedEventPayload.class::isInstance)
                .expectNextMatches(FormRefreshedEventPayload.class::isInstance)
                .expectNextMatches(FormCapabilitiesRefreshedEventPayload.class::isInstance)
                .expectNextMatches(FormRefreshedEventPayload.class::isInstance)
                .expectNextMatches(FormCapabilitiesRefreshedEventPayload.class::isInstance)
                .expectNextMatches(FormRefreshedEventPayload.class::isInstance)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
