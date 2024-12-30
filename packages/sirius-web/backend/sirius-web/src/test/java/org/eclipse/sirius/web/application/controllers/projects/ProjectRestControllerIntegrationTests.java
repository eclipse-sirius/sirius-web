/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import org.eclipse.sirius.web.application.project.dto.RestProject;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

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
    @DisplayName("Given the Sirius Web REST API, when we ask for all projects, then it should return all projects")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForAllProjectsThenItShouldReturnAllProjects() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = "/api/rest/projects";
        var response = webTestClient
                .get()
                .uri(uri)
                .exchange();

        response.expectStatus().isOk();
        response.expectBodyList(RestProject.class).hasSize(4);
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for a project, then it should return the project")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
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
    @DisplayName("Given the Sirius Web REST API, when we ask for an unknown project, then it should return an error")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
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
    @DisplayName("Given the Sirius Web REST API, when we create a new project, then it should be created successfully")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
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
    @DisplayName("Given the Sirius Web REST API, when we update the name of a project, then it should be updated")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
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
    @DisplayName("Given the Sirius Web REST API, when we update the name of an unknown project, then it should return an error")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
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
    @DisplayName("Given the Sirius Web REST API, when we delete a project, then it should be deleted")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
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
    @DisplayName("Given the Sirius Web REST API, when we delete an unknown project, then it should return an error")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
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
