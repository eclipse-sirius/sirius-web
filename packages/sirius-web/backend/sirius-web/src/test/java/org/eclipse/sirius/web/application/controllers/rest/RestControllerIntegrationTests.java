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
package org.eclipse.sirius.web.application.controllers.rest;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of REST controller.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @LocalServerPort
    private String port;

    private String getHTTPBaseUrl() {
        return "http://localhost:" + this.port;
    }

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for several queries involving dates, then all queries should return correctly")
    public void givenSiriusWebRestAPIWhenWeAskForSeveralQueriesInvolvingDatesThenAllQueriesShouldReturnCorrectly() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects");
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk();

        uri = String.format("/api/rest/projects/%s/commits/%s/elements", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk();

        uri = String.format("/api/rest/projects");
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
