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

import java.util.UUID;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestCommit;
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
 * Integration tests of the project-data-versioning commits REST controller.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommitRestControllerIntegrationTests extends AbstractIntegrationTests {

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
    @DisplayName("Given the Sirius Web REST API, when we ask for all commits of a project, then it should return all commits of a project")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForAllCommitsOfAProjectThenItShouldReturnAllCommitsOfAProject() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits", TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(RestCommit.class)
                .hasSize(1)
                .consumeWith(result -> {
                    var restCommits = result.getResponseBody();
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restCommits.get(0).id());
                    assertEquals("Commit", restCommits.get(0).type());
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restCommits.get(0).owningProject().id());
                });
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for all commits of an unknown project, then it should return an error")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForAllCommitsOfAnUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits", TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for the creation of a commit, then it should return an empty response")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForTheCreationOfACommitThenItShouldReturnAnEmptyResponse() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits", TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .post()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for the creation of a commit on an ivalid project, then it should return an empty response")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForTheCreationOfACommitOnAnInvalidProjectThenItShouldReturnAnEmptyResponse() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits", TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .post()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .isEmpty();
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for a specific commit of a project, then it should return this commit")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForASpecificCommitOfAProjectThenItShouldReturnThisCommit() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s", TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(RestCommit.class)
                .consumeWith(result -> {
                    var restCommit = result.getResponseBody();
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restCommit.id());
                    assertEquals("Commit", restCommit.type());
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restCommit.owningProject().id());
                });
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for an unknown commit of a project, then it should return an error")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForAnUnknownCommitOfAProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s", TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for commit changes of a project, then it should return these commit changes")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForCommitChangesOfAProjectThenItShouldReturnTheseCommitChanges() {
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
                .jsonPath("$[0].payload.@id").isEqualTo(TestIdentifiers.EPACKAGE_OBJECT.toString())
                .jsonPath("$[0].payload.@type").isEqualTo("EPackage")
                .jsonPath("$[0].payload.name").isEqualTo("Sample")
                .jsonPath("$[1].@type").isEqualTo("DataVersion")
                .jsonPath("$[1].identity.@id").isEqualTo(TestIdentifiers.ECLASS_OBJECT.toString())
                .jsonPath("$[0].identity.@type").isEqualTo("DataIdentity")
                .jsonPath("$[1].payload.@id").isEqualTo(TestIdentifiers.ECLASS_OBJECT.toString())
                .jsonPath("$[1].payload.@type").isEqualTo("EClass")
                .jsonPath("$[1].payload.name").isEqualTo("SampleEClass");
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for commit changes of a project with no data, then it should return an error")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForCommitChangesOfAProjectWithNoDataThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/changes", TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound(); // no data in this project, so RestDataVersion
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for a specific commit change, then it should return this commit change")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForASpecificCommitChangeThenItShouldReturnThisCommitChange() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var changeId = UUID.nameUUIDFromBytes((TestIdentifiers.ECORE_SAMPLE_PROJECT.toString() + TestIdentifiers.EPACKAGE_OBJECT.toString()).getBytes());
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
                .jsonPath("$.payload.@id").isEqualTo(TestIdentifiers.EPACKAGE_OBJECT.toString())
                .jsonPath("$.payload.@type").isEqualTo("EPackage")
                .jsonPath("$.payload.name").isEqualTo("Sample");
    }

    @Test
    @DisplayName("Given the Sirius Web REST API, when we ask for an invalid commit change, then it should return an error")
    @Sql(scripts = {"/scripts/initialize.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = {"/scripts/cleanup.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenSiriusWebRestAPIWhenWeAskForAnInvalidCommitChangeThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/changes/%s", TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.INVALID_PROJECT);
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
