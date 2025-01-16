/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.RestProject;
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

import graphql.relay.Relay;

/**
 * Integration tests of the project REST controller.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectRestControllerIntegrationTests extends AbstractIntegrationTests {

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
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects, then it should return all projects")
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsThenItShouldReturnAllProjects() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = "/api/rest/projects";
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .value(value -> assertThat(value).isNotEmpty());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects with a page size, then it should return a max number of projects corresponding to the size")
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsWithAPageSizeThenItShouldReturnAMaxNumberOfProjectsCorrespondingToTheSize() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = "/api/rest/projects?page[size]=1";
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .hasSize(1);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects with a page size > projects size, then it should return a max number of projects corresponding to the projects size")
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsWithAPageSizeSupToProjectsSizeThenItShouldReturnAMaxNumberOfProjectsCorrespondingToTheProjectsSize() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = "/api/rest/projects?page[size]=10";
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .value(value -> assertThat(value).isNotEmpty());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects with a page size = 0, then it should return an empty list")
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsWithAPageSizeEq0ThenItShouldReturnAnEmptyList() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = "/api/rest/projects?page[size]=0";
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .hasSize(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects after a specific one, then it should return all projects after the specific one")
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsAfterASpecificOneThenItShouldReturnAllProjectsAfterTheSpecificOne() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var link = new Relay().toGlobalId("Project", TestIdentifiers.UML_SAMPLE_PROJECT.toString());
        var uri = String.format("/api/rest/projects?page[after]=%s", link);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .consumeWith(result -> {
                    var restProjects = result.getResponseBody();
                    assertThat(restProjects)
                            .isNotEmpty()
                            .anySatisfy(restProject -> assertThat(restProject.id()).isEqualTo(TestIdentifiers.ECORE_SAMPLE_PROJECT))
                            .anySatisfy(restProject -> assertThat(restProject.id()).isEqualTo(TestIdentifiers.SYSML_SAMPLE_PROJECT));
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for N projects after a specific one, then it should return N projects after the specific one")
    public void givenSiriusWebRestAPIWhenWeAskForNProjectsAfterASpecificOneThenItShouldReturnNProjectsAfterTheSpecificOne() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var link = new Relay().toGlobalId("Project", TestIdentifiers.UML_SAMPLE_PROJECT.toString());
        var uri = String.format("/api/rest/projects?page[after]=%s&page[size]=1", link);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .hasSize(1)
                .consumeWith(result -> {
                    var restProjects = result.getResponseBody();
                    assertEquals(TestIdentifiers.ECORE_SAMPLE_PROJECT, restProjects.get(0).id());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects before a specific one, then it should return all projects before the specific one")
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsBeforeASpecificOneThenItShouldReturnAllProjectsBeforeTheSpecificOne() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var link = new Relay().toGlobalId("Project", TestIdentifiers.UML_SAMPLE_PROJECT.toString());
        var uri = String.format("/api/rest/projects?page[before]=%s", link);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .consumeWith(result -> {
                    var restProjects = result.getResponseBody();
                    assertThat(restProjects)
                            .isNotEmpty()
                            .anySatisfy(restProject -> assertThat(restProject.id()).isEqualTo(TestIdentifiers.ECORE_SAMPLE_PROJECT))
                            .anySatisfy(restProject -> assertThat(restProject.id()).isEqualTo(TestIdentifiers.SYSML_SAMPLE_PROJECT));
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for N projects before a specific one, then it should return N projects after the specific one")
    public void givenSiriusWebRestAPIWhenWeAskForNProjectsBeforeASpecificOneThenItShouldReturnNProjectsAfterTheSpecificOne() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var link = new Relay().toGlobalId("Project", TestIdentifiers.UML_SAMPLE_PROJECT.toString());
        var uri = String.format("/api/rest/projects?page[before]=%s&page[size]=1", link);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .hasSize(1)
                .consumeWith(result -> {
                    var restProjects = result.getResponseBody();
                    assertEquals(TestIdentifiers.ECORE_SAMPLE_PROJECT, restProjects.get(0).id());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects after an unkwnown, then it should return an empty list")
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsAfterAnUnknownThenItShouldReturnAnEmptyList() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var link = new Relay().toGlobalId("Project", TestIdentifiers.INVALID_PROJECT.toString());
        var uri = String.format("/api/rest/projects?page[after]=%s", link);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .hasSize(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects before an unkwnown, then it should return an empty list")
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsBeforeAnUnknownThenItShouldReturnAnEmptyList() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var link = new Relay().toGlobalId("Project", TestIdentifiers.INVALID_PROJECT.toString());
        var uri = String.format("/api/rest/projects?page[before]=%s", link);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestProject.class)
                .hasSize(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for a project, then it should return the project")
    public void givenSiriusWebRestAPIWhenWeAskForProjectThenItShouldReturnTheProject() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s", TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(RestProject.class)
                .consumeWith(result -> {
                    var restProject = result.getResponseBody();
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restProject.id());
                    assertEquals("Project", restProject.type());
                    assertEquals("UML Sample", restProject.name());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeAskForUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s", TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we create a new project, then it should be created successfully")
    public void givenSiriusWebRestAPIWhenWeCreateNewProjectThenItShouldBeCreatedSuccessfully() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        String projectName = "NewProjectFromRestAPI";

        var uri = String.format("/api/rest/projects?name=%s", projectName);
        webTestClient
                .post()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(RestProject.class)
                .consumeWith(result -> {
                    var restProject = result.getResponseBody();
                    assertEquals(projectName, restProject.name());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we update the name of a project, then it should be updated")
    public void givenSiriusWebRestAPIWhenWeUpdateTheNameOfProjectTheItShouldBeUpdated() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        String updatedProjectName = "UML Sample Renamed";

        var uri = String.format("/api/rest/projects/%s?name=%s", TestIdentifiers.UML_SAMPLE_PROJECT, updatedProjectName);
        webTestClient
                .put()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(RestProject.class)
                .consumeWith(result -> {
                    var restProject = result.getResponseBody();
                    assertEquals(updatedProjectName, restProject.name());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we update the name of an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeUpdateTheNameOfUnknownProjectTheItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        String updatedProjectName = "UML Sample Renamed";

        var uri = String.format("/api/rest/projects/%s?name=%s", TestIdentifiers.UML_SAMPLE_PROJECT, updatedProjectName);
        webTestClient
                .put()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(RestProject.class)
                .consumeWith(result -> {
                    var restProject = result.getResponseBody();
                    assertEquals(updatedProjectName, restProject.name());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we delete a project, then it should be deleted")
    public void givenSiriusWebRestAPIWhenWeDeleteProjectThenItShouldBeDeleted() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s", TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .delete()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(RestProject.class)
                .consumeWith(result -> {
                    var restProject = result.getResponseBody();
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restProject.id());
                    assertEquals("Project", restProject.type());
                    assertEquals("UML Sample", restProject.name());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we delete an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeDeleteAnUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s", TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .delete()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNoContent();
    }
}
