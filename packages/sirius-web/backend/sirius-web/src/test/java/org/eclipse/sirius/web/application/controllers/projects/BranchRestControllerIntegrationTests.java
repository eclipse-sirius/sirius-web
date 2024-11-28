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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranch;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranchRequest;
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
 * Integration tests of the project-data-versioning branches REST controller.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BranchRestControllerIntegrationTests extends AbstractIntegrationTests {

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
    @DisplayName("Given the Sirius Web REST API, when we ask for all branches of a project, then it should return all branches of a project")
    public void givenSiriusWebRestAPIWhenWeAskForAllBranchesOfAProjectThenItShouldReturnAllBranchesOfAProject() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/branches", TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestBranch.class)
                .hasSize(1)
                .consumeWith(result -> {
                    var restBranches = result.getResponseBody();
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restBranches.get(0).id().toString());
                    assertEquals("Branch", restBranches.get(0).type());
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restBranches.get(0).owningProject().id().toString());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all branches of an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeAskForAllBranchesOfAnUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/branches", TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the creation of a branch, then it should return an empty response")
    public void givenSiriusWebRestAPIWhenWeAskForTheCreationOfABranchThenItShouldReturnAnEmptyResponse() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/branches", TestIdentifiers.UML_SAMPLE_PROJECT);
        var requestBody = new RestBranchRequest(null, "myNewBranch");
        webTestClient
                .post()
                .uri(uri)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the creation of a branch on an invalid project, then it should return an empty response")
    public void givenSiriusWebRestAPIWhenWeAskForTheCreationOfABranchOnAnInvalidProjectThenItShouldReturnAnEmptyResponse() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/branches", TestIdentifiers.INVALID_PROJECT);
        var requestBody = new RestBranchRequest(null, "myNewBranch");
        webTestClient
                .post()
                .uri(uri)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for a specific branch of a project, then it should return this branch")
    public void givenSiriusWebRestAPIWhenWeAskForASpecificBranchOfAProjectThenItShouldReturnThisBranch() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/branches/%s", TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(RestBranch.class)
                .consumeWith(result -> {
                    var restBranch = result.getResponseBody();
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restBranch.id().toString());
                    assertEquals("Branch", restBranch.type());
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restBranch.owningProject().id().toString());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for an unknown branch of a project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeAskForAnUnknownBranchAProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/branches/%s", TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the deletion of a branch, then it should return a no content response")
    public void givenSiriusWebRestAPIWhenWeAskForTheDeletionOfABranchThenItShouldReturnANoContentResponse() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/branches/%s", TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .delete()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody()
                .isEmpty();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the deletion of a branch of an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeAskForTheDeletionOfABranchAnUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/branches/%s", TestIdentifiers.INVALID_PROJECT, TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .delete()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
