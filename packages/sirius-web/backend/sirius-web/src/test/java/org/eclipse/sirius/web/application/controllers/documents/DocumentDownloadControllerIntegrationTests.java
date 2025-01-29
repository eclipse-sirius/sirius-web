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
package org.eclipse.sirius.web.application.controllers.documents;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the document download controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"sirius.web.test.enabled=studio"})
public class DocumentDownloadControllerIntegrationTests extends AbstractIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the download of a domain document is requested, then the domain is retrieved")
    public void givenStudioWhenTheDownloadOfDomainDocumentIsRequestedThenTheDomainIsRetrieved() {
        this.givenCommittedTransaction.commit();

        var testRestTemplate = new TestRestTemplate();

        var uri = "http://localhost:" + this.port + "/api/editingcontexts/" + StudioIdentifiers.SAMPLE_STUDIO_PROJECT + "/documents/" + StudioIdentifiers.DOMAIN_DOCUMENT;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        var response = testRestTemplate.exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a download document request, when a resource search service is provided, then the good resource is retrieved")
    public void givenDownloadDocumentRequestWhenAResourceSearchServiceIsProvidedThenTheGoodResourceIsRetrieved() throws IOException {
        this.givenCommittedTransaction.commit();

        var testRestTemplate = new TestRestTemplate();

        var uri = "http://localhost:" + this.port + "/api/editingcontexts/" + PapayaIdentifiers.PAPAYA_PROJECT + "/documents/" + PapayaIdentifiers.PROJECT_OBJECT;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_XML));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        var response = testRestTemplate.exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContentAsString(StandardCharsets.UTF_8)).isEqualToIgnoringWhitespace("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<xmi:XMI xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\"/>\n"); //Empty resource load from PapayaDocumentDownloadResourceSearchService
    }
}
