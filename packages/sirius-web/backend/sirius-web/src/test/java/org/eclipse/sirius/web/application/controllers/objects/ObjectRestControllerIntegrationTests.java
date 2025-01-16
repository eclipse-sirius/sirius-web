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
package org.eclipse.sirius.web.application.controllers.objects;

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
 * Integration tests of the object REST controller.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ObjectRestControllerIntegrationTests extends AbstractIntegrationTests {

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
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all elements, then all elements should be returned")
    public void givenSiriusWebRestAPIWhenWeAskForAllElementsThenAllElementsShouldBeReturned() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/elements", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("[%s, %s]".formatted(ECORE_SAMPLE_PKG, ECORE_SAMPLE_ECLASS));


        uri = String.format("/api/rest/projects/%s/commits/%s/elements", TestIdentifiers.UML_SAMPLE_PROJECT, TestIdentifiers.UML_SAMPLE_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Object.class)
                .hasSize(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for all elements in an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeAskForAllElementsInUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/elements", TestIdentifiers.INVALID_PROJECT, TestIdentifiers.INVALID_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for a specific element, then it should return the element")
    public void givenSiriusWebRestAPIWhenWeAskForSpecificElementThenItShouldReturnTheElement() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/elements/%s", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.EPACKAGE_OBJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(ECORE_SAMPLE_PKG);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for a specific element in an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeAskForSpecificElementInUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/elements/%s", TestIdentifiers.INVALID_PROJECT, TestIdentifiers.INVALID_PROJECT, TestIdentifiers.EPACKAGE_OBJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the relationships of an element, then it should return the relationships")
    public void givenSiriusWebRestAPIWhenWeAskForRelationshipsOfAnElementThenItShouldReturnTheRelationships() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/elements/%s/relationships", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.EPACKAGE_OBJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Object.class)
                .hasSize(0);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the relationships of an element in an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeAskForRelationshipsOfAnElementInAnUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/elements/%s/relationships", TestIdentifiers.INVALID_PROJECT, TestIdentifiers.INVALID_PROJECT, TestIdentifiers.EPACKAGE_OBJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the root elements, then it should return the root elements")
    public void givenSiriusWebRestAPIWhenWeAskForTheRootElementsThenItShouldReturnTheRootElements() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/roots", TestIdentifiers.ECORE_SAMPLE_PROJECT, TestIdentifiers.ECORE_SAMPLE_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("[%s]".formatted(ECORE_SAMPLE_PKG));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the Sirius Web REST API, when we ask for the root elements of an unknown project, then it should return an error")
    public void givenSiriusWebRestAPIWhenWeAskForTheRootElementsOfUnknownProjectThenItShouldReturnAnError() {
        var webTestClient = WebTestClient.bindToServer()
                .baseUrl(this.getHTTPBaseUrl())
                .build();

        var uri = String.format("/api/rest/projects/%s/commits/%s/roots", TestIdentifiers.INVALID_PROJECT, TestIdentifiers.INVALID_PROJECT);
        webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
