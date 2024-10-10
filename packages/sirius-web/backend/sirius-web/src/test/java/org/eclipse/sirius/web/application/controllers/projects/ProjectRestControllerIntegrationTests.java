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

import org.eclipse.core.runtime.IPath;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectRestControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String API_REST_PROJECTS = "/api/rest/projects";

    private static final String PARAM_NAME = "name";

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
    @DisplayName("Test the GET '/projects' REST API, should return all known projects")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIgetProjects() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var response = webTestClient
                .get()
                .uri(API_REST_PROJECTS)
                .exchange();

        response.expectStatus().isOk();
        response.expectBodyList(RestProject.class).hasSize(3);
    }

    @Test
    @DisplayName("Test the GET '/projects/{projectId}' REST API, should return the project corresponding to the given projectId")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIgetProjectFromProjectId() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var response = webTestClient
                .get()
                .uri(API_REST_PROJECTS + IPath.SEPARATOR + TestIdentifiers.UML_SAMPLE_PROJECT)
                .exchange();

        response.expectStatus().isOk();
        response.expectBody(RestProject.class).consumeWith(result -> {
            var restProject = result.getResponseBody();
            assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restProject.id());
            assertEquals("UML Sample", restProject.name());
        });

        response = webTestClient
                .get()
                .uri(API_REST_PROJECTS + IPath.SEPARATOR + TestIdentifiers.INVALID_PROJECT)
                .exchange();
        response.expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Test the POST '/projects' REST API, should create a project with the given name")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIcreateProjectFromName() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        String projectName = "NewProjectFromRestAPI";

        var response = webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(API_REST_PROJECTS)
                        .queryParam(PARAM_NAME, projectName)
                        .build())
                .exchange();

        response.expectStatus().isCreated();
        response.expectBody(RestProject.class).consumeWith(result -> {
            var restProject = result.getResponseBody();
            assertEquals(projectName, restProject.name());
        });
    }

    @Test
    @DisplayName("Test the PUT '/projects/{projectId}' REST API, should update the project with the given projectId")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIupdateProject() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        String updatedProjectName = "UML Sample Renamed";

        var response = webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path(API_REST_PROJECTS + IPath.SEPARATOR + TestIdentifiers.UML_SAMPLE_PROJECT)
                        .queryParam(PARAM_NAME, updatedProjectName)
                        .build())
                .exchange();

        response.expectStatus().isOk();
        response.expectBody(RestProject.class).consumeWith(result -> {
            var restProject = result.getResponseBody();
            assertEquals(updatedProjectName, restProject.name());
        });

        response = webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path(API_REST_PROJECTS + IPath.SEPARATOR + TestIdentifiers.INVALID_PROJECT)
                        .queryParam(PARAM_NAME, updatedProjectName)
                        .build())
                .exchange();

        response.expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Test the DELETE '/projects/{projectId}' REST API, should delete and return the project corresponding to the given projectId")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIdeleteProjectFromProjectId() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var response = webTestClient
                .delete()
                .uri(API_REST_PROJECTS + IPath.SEPARATOR + TestIdentifiers.UML_SAMPLE_PROJECT)
                .exchange();

        response.expectStatus().isOk();
        response.expectBody(RestProject.class).consumeWith(result -> {
            var restProject = result.getResponseBody();
            assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restProject.id());
            assertEquals("UML Sample", restProject.name());
        });

        response = webTestClient
                .delete()
                .uri(API_REST_PROJECTS + IPath.SEPARATOR + TestIdentifiers.INVALID_PROJECT)
                .exchange();
        response.expectStatus().isNoContent();
    }
}
