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
package org.eclipse.sirius.web.application.controllers.projects;

import java.util.UUID;

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
 * Integration tests of the project-data-versioning commits REST controller.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommitChangesNoPayloadRestControllerIntegrationTests extends AbstractIntegrationTests {

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
    @DisplayName("Given the Sirius Web REST API, when we ask for commit changes of a project, then it should return these commit changes with a null payload")
    public void givenSiriusWebRestAPIWhenWeAskForCommitChangesOfAProjectThenItShouldReturnTheseCommitChangesWithANullPayload() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/changes", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].@type").isEqualTo("DataVersion")
                .jsonPath("$[0].identity.@id").isEqualTo(TestIdentifiers.EPACKAGE_OBJECT.toString())
                .jsonPath("$[0].identity.@type").isEqualTo("DataIdentity")
                .jsonPath("$[0].payload").isEqualTo(null)
                .jsonPath("$[1].@type").isEqualTo("DataVersion")
                .jsonPath("$[1].identity.@id").isEqualTo(TestIdentifiers.ECLASS_OBJECT.toString())
                .jsonPath("$[0].identity.@type").isEqualTo("DataIdentity")
                .jsonPath("$[1].payload").isEqualTo(null);
    }


    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for a specific commit change, then it should return this commit change with a null payload")
    public void givenSiriusWebRestAPIWhenWeAskForASpecificCommitChangeThenItShouldReturnThisCommitChangeWithANullPayload() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var changeId = UUID.nameUUIDFromBytes((TestIdentifiers.ECORE_SAMPLE_PROJECT + TestIdentifiers.EPACKAGE_OBJECT).getBytes());
        var uri = String.format("/api/rest/projects/%s/commits/%s/changes/%s", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT, changeId);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.@type").isEqualTo("DataVersion")
                .jsonPath("$.identity.@id").isEqualTo(TestIdentifiers.EPACKAGE_OBJECT.toString())
                .jsonPath("$.identity.@type").isEqualTo("DataIdentity")
                .jsonPath("$.payload").isEqualTo(null);
    }
}