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
package org.eclipse.sirius.web.application.controllers.objects;

import org.eclipse.core.runtime.IPath;
import org.eclipse.sirius.web.AbstractIntegrationTests;
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
 * Integration tests of the object REST controller.
 *
 * @author arichard
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObjectRestControllerIntegrationTests extends AbstractIntegrationTests {


    private static final String API_REST_PROJECTS = "/api/rest/projects";

    private static final String API_REST_COMMITS = "/commits";

    private static final String API_REST_ELEMENTS = "/elements";

    private static final String API_REST_RELATIONSHIPS = "/relationships";

    private static final String API_REST_ROOTS = "/roots";

    private static final String ECORE_SAMPLE_PKG = """
            {
                "@id": "%s",
                "@type": "EPackage",
                "name": "Sample",
                "nsURI": null,
                "nsPrefix": null,
                "eAnnotations": [],
                "eClassifiers": [
                    {
                        "@id": "%s"
                    }
                ],
                "eSubpackages": [],
                "eSuperPackage": null
            }
            """.formatted(TestIdentifiers.EPACKAGE_OBJECT, TestIdentifiers.ECLASS_OBJECT);

    private static final String ECORE_SAMPLE_ECLASS = """
            {
                "@id": "%s",
                "@type": "EClass",
                "name": "SampleEClass",
                "instanceClassName": null,
                "instanceClass": null,
                "defaultValue": null,
                "instanceTypeName": null,
                "abstract": "false",
                "interface": "false",
                "eAnnotations": [],
                "ePackage": { "@id": "%s" },
                "eTypeParameters": [],
                "eSuperTypes": [],
                "eOperations": [],
                "eAllAttributes": [],
                "eAllReferences": [],
                "eReferences": [],
                "eAttributes": [],
                "eAllContainments": [],
                "eAllOperations": [],
                "eAllStructuralFeatures": [],
                "eAllSuperTypes": [],
                "eIDAttribute": null,
                "eStructuralFeatures": [],
                "eGenericSuperTypes": [],
                "eAllGenericSuperTypes": []
            }
            """.formatted(TestIdentifiers.ECLASS_OBJECT, TestIdentifiers.EPACKAGE_OBJECT);

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
    @DisplayName("Test the GET '/projects/{projectId}/commits/{commitId}/elements' REST API, should return all elements of the given project")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIgetElements() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        uri.append(API_REST_ELEMENTS);

        var response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isOk();
        response.expectBody().json("[%s, %s]".formatted(ECORE_SAMPLE_PKG, ECORE_SAMPLE_ECLASS));

        uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.UML_SAMPLE_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.UML_SAMPLE_PROJECT);
        uri.append(API_REST_ELEMENTS);

        response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isOk();
        response.expectBodyList(Object.class).hasSize(0);

        uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.INVALID_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.INVALID_PROJECT);
        uri.append(API_REST_ELEMENTS);

        response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Test the GET '/projects/{projectId}/commits/{commitId}/elements/{elementId}' REST API, should return the element of the given project with the given elementId")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIgetElementById() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        uri.append(API_REST_ELEMENTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.EPACKAGE_OBJECT);

        var response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isOk();
        response.expectBody().json(ECORE_SAMPLE_PKG);

        uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.UML_SAMPLE_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.UML_SAMPLE_PROJECT);
        uri.append(API_REST_ELEMENTS);

        response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isOk();
        response.expectBody(Object.class).equals(null);

        uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.INVALID_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.INVALID_PROJECT);
        uri.append(API_REST_ELEMENTS);

        response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Test the GET '/projects/{projectId}/commits/{commitId}/elements/{relatedElementId}/relationships' REST API, should return the relationships of the given elementId")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIgetRelationshipsByRelatedElement() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        uri.append(API_REST_ELEMENTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.EPACKAGE_OBJECT);
        uri.append(API_REST_RELATIONSHIPS);

        var response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isOk();
        response.expectBodyList(Object.class).hasSize(0);

        uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.UML_SAMPLE_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.UML_SAMPLE_PROJECT);
        uri.append(API_REST_ELEMENTS);

        response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isOk();
        response.expectBodyList(Object.class).hasSize(0);

        uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.INVALID_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.INVALID_PROJECT);
        uri.append(API_REST_ELEMENTS);

        response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Test the GET '/projects/{projectId}/commits/{commitId}/roots' REST API, should return root elements of the given project")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void restAPIgetRootElements() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        uri.append(API_REST_ROOTS);

        var response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isOk();
        response.expectBody().json("[%s]".formatted(ECORE_SAMPLE_PKG));

        uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.UML_SAMPLE_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.UML_SAMPLE_PROJECT);
        uri.append(API_REST_ELEMENTS);

        response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isOk();
        response.expectBodyList(Object.class).hasSize(0);

        uri = new StringBuilder();
        uri.append(API_REST_PROJECTS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.INVALID_PROJECT);
        uri.append(API_REST_COMMITS);
        uri.append(IPath.SEPARATOR);
        uri.append(TestIdentifiers.INVALID_PROJECT);
        uri.append(API_REST_ELEMENTS);

        response = webTestClient.get()
                .uri(uri.toString())
                .exchange();

        response.expectStatus().isNotFound();
    }
}
