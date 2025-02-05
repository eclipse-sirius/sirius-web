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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestCommit;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestCommitRequest;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the project-data-versioning commits REST controller.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=rest-commit-payload" })
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
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all commits of a project, then it should return all commits of a project")
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
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restCommits.get(0).id().toString());
                    assertEquals("Commit", restCommits.get(0).type());
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restCommits.get(0).owningProject().id());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all commits of an unknown project, then it should return an error")
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
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the creation of a commit with an object deletion, then it should return this amended commit")
    public void givenSiriusWebRestAPIWhenWeAskForTheCreationOfACommitWithAnObjectDeletionThenItShouldReturnThisAmendedCommit() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits", TestIdentifiers.ECORE_SAMPLE_PROJECT);
        // Deletion of the eClass
        var requestBody = """
                {
                    "@type": "Commit",
                    "change": [
                        {
                            "@type": "DataVersion",
                            "identity": {
                                "@id": "%s",
                                "@type": "DataIdentity"
                            },
                            "payload": null
                        }
                    ]
                }
                """.formatted(TestIdentifiers.ECLASS_OBJECT.toString());

        webTestClient
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(RestCommit.class)
                .consumeWith(result -> {
                    var restCommit = result.getResponseBody();
                    assertEquals(TestIdentifiers.ECORE_SAMPLE_PROJECT, restCommit.id().toString());
                    assertEquals("Commit", restCommit.type());
                    assertEquals(TestIdentifiers.ECORE_SAMPLE_PROJECT, restCommit.owningProject().id());
                });

        uri = String.format("/api/rest/projects/%s/commits/%s/elements", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].@type").isEqualTo("EPackage")
                .jsonPath("$[0].@id").isEqualTo(TestIdentifiers.EPACKAGE_OBJECT.toString())
                .jsonPath("$[0].name").isEqualTo("Sample")
                .jsonPath("$[1]").doesNotExist();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the creation of a commit with a new object, then it should return this amended commit")
    public void givenSiriusWebRestAPIWhenWeAskForTheCreationOfACommitWithANewObjectThenItShouldReturnThisAmendedCommit() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits", TestIdentifiers.ECORE_SAMPLE_PROJECT);
        // Add of a new ePackage inside the existing ePackage
        var requestBody = """
                {
                    "@type": "Commit",
                    "change": [
                        {
                            "@type": "DataVersion",
                            "identity": null,
                            "payload": {
                                "@id": "3a4d2a45-5de0-4f56-8f23-4fb1474a9fea",
                                "@type": "EPackage",
                                "name": "newSubPackage"
                            }
                        },
                        {
                            "@type": "DataVersion",
                            "identity": {
                                "@id": "%s",
                                "@type": "DataIdentity"
                            },
                            "payload": {
                                "@id": "%s",
                                "@type": "EPackage",
                                "eSubpackages": [
                                    {
                                        "@id": "3a4d2a45-5de0-4f56-8f23-4fb1474a9fea"
                                    }
                                ]
                            }
                        }
                    ]
                }
                """.formatted(TestIdentifiers.EPACKAGE_OBJECT.toString(), TestIdentifiers.EPACKAGE_OBJECT.toString());

        webTestClient
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(RestCommit.class)
                .consumeWith(result -> {
                    var restCommit = result.getResponseBody();
                    assertEquals(TestIdentifiers.ECORE_SAMPLE_PROJECT, restCommit.id().toString());
                    assertEquals("Commit", restCommit.type());
                    assertEquals(TestIdentifiers.ECORE_SAMPLE_PROJECT, restCommit.owningProject().id());
                });

        uri = String.format("/api/rest/projects/%s/commits/%s/elements", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].@type").isEqualTo("EPackage")
                .jsonPath("$[0].@id").isEqualTo(TestIdentifiers.EPACKAGE_OBJECT.toString())
                .jsonPath("$[0].name").isEqualTo("Sample")
                .jsonPath("$[0].eClassifiers[0].@id").isEqualTo(TestIdentifiers.ECLASS_OBJECT.toString())
                .jsonPath("$[0].eSubpackages[0].@id").exists() // the Id is randomly generated and does not match the initial Id given in the request body of the create commit
                .jsonPath("$[1].@type").isEqualTo("EClass")
                .jsonPath("$[1].@id").isEqualTo(TestIdentifiers.ECLASS_OBJECT.toString())
                .jsonPath("$[2].@type").isEqualTo("EPackage")
                .jsonPath("$[2].@id").exists() // the Id is randomly generated and does not match the initial Id given in the request body of the create commit
                .jsonPath("$[2].name").isEqualTo("newSubPackage");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the creation of a commit with an object update, then it should return this amended commit")
    public void givenSiriusWebRestAPIWhenWeAskForTheCreationOfACommitWithAnObjectUpdateThenItShouldReturnThisAmendedCommit() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits", TestIdentifiers.ECORE_SAMPLE_PROJECT);
        // Update of the ePackage name
        var requestBody = """
                {
                    "@type": "Commit",
                    "change": [
                        {
                            "@type": "DataVersion",
                            "identity": {
                                "@id": "%s",
                                "@type": "DataIdentity"
                            },
                            "payload": {
                                "@id": "%s",
                                "@type": "EPackage",
                                "name": "updatedPackageName"
                            }
                        }
                    ]
                }
                """.formatted(TestIdentifiers.EPACKAGE_OBJECT.toString(), TestIdentifiers.EPACKAGE_OBJECT.toString());

        webTestClient
                .post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(RestCommit.class)
                .consumeWith(result -> {
                    var restCommit = result.getResponseBody();
                    assertEquals(TestIdentifiers.ECORE_SAMPLE_PROJECT, restCommit.id().toString());
                    assertEquals("Commit", restCommit.type());
                    assertEquals(TestIdentifiers.ECORE_SAMPLE_PROJECT, restCommit.owningProject().id());
                });

        uri = String.format("/api/rest/projects/%s/commits/%s/elements", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$[0].@type").isEqualTo("EPackage")
                .jsonPath("$[0].@id").isEqualTo(TestIdentifiers.EPACKAGE_OBJECT.toString())
                .jsonPath("$[0].name").isEqualTo("updatedPackageName")
                .jsonPath("$[1]").exists();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the creation of a commit on an invalid project, then it should return an empty response")
    public void givenSiriusWebRestAPIWhenWeAskForTheCreationOfACommitOnAnInvalidProjectThenItShouldReturnAnEmptyResponse() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits", TestIdentifiers.INVALID_PROJECT);
        var requestBody = new RestCommitRequest(List.of(), null);
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
    @DisplayName("Given the Sirius Web REST API, when we ask for a specific commit of a project, then it should return this commit")
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
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restCommit.id().toString());
                    assertEquals("Commit", restCommit.type());
                    assertEquals(TestIdentifiers.UML_SAMPLE_PROJECT, restCommit.owningProject().id());
                });
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for an unknown commit of a project, then it should return an error")
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
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for commit changes of a project, then it should return these commit changes")
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
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for commit changes of a project with no data, then it should return an error")
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
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for a specific commit change, then it should return this commit change")
    public void givenSiriusWebRestAPIWhenWeAskForASpecificCommitChangeThenItShouldReturnThisCommitChange() {
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
                .jsonPath("$.payload.@id").isEqualTo(TestIdentifiers.EPACKAGE_OBJECT.toString())
                .jsonPath("$.payload.@type").isEqualTo("EPackage")
                .jsonPath("$.payload.name").isEqualTo("Sample");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for an invalid commit change, then it should return an error")
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