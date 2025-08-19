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
package org.eclipse.sirius.web.application.controllers.objects;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.eclipse.sirius.web.AbstractIntegrationTests;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of the object download controllers.
 *
 * @author gdaniel
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ObjectDownloadControllerIntegrationTests extends AbstractIntegrationTests {

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
    @DisplayName("Given a studio, when the download of a single domain element is requested with text/csv format, then the element is retrieved with the appropriate format")
    public void givenStudioWhenTheDownloadOfASingleDomainElementIsRequestedWithTextCSvThenTheElementIsRetrievedWithTheAppropriateFormat() throws IOException {
        this.givenCommittedTransaction.commit();

        var testRestTemplate = new TestRestTemplate();

        var uri = "http://localhost:" + this.port + "/api/editingcontexts/" + StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID + "/objects?contentType=text/csv&objectIds="
                + StudioIdentifiers.DOMAIN_OBJECT.toString();

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

        var response = testRestTemplate.exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String responseBody = response.getBody().getContentAsString(StandardCharsets.UTF_8);
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<List<String>> mappingIterator = csvMapper
                .readerForListOf(String.class)
                .with(CsvParser.Feature.WRAP_AS_ARRAY)
                .readValues(responseBody);
        List<List<String>> csvData = mappingIterator.readAll();
        assertThat(csvData).hasSize(2);
        assertThat(csvData.get(0)).containsExactly("id", "type", "label");
        assertThat(csvData.get(1)).containsExactly(StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain", "buck");
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a studio, when the download of multiple domain elements is requested with text/csv format, then the elements are retrieved with the appropriate format")
    public void givenStudioWhenTheDownloadOfMultipleDomainElementsIsRequestedWithTextCSvThenTheElementsAreRetrievedWithTheAppropriateFormat() throws IOException {
        this.givenCommittedTransaction.commit();

        var testRestTemplate = new TestRestTemplate();

        var uri = "http://localhost:" + this.port + "/api/editingcontexts/" + StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID + "/objects?contentType=text/csv&objectIds="
                + StudioIdentifiers.DOMAIN_OBJECT.toString() + "," + StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString();

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());

        var response = testRestTemplate.exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String responseBody = response.getBody().getContentAsString(StandardCharsets.UTF_8);
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<List<String>> mappingIterator = csvMapper
                .readerForListOf(String.class)
                .with(CsvParser.Feature.WRAP_AS_ARRAY)
                .readValues(responseBody);
        List<List<String>> csvData = mappingIterator.readAll();
        assertThat(csvData).hasSize(3);
        assertThat(csvData.get(0)).containsExactly("id", "type", "label");
        assertThat(csvData.get(1)).containsExactly(StudioIdentifiers.DOMAIN_OBJECT.toString(), "Domain", "buck");
        assertThat(csvData.get(2)).containsExactly(StudioIdentifiers.HUMAN_ENTITY_OBJECT.toString(), "Entity", "Human");
    }

}
