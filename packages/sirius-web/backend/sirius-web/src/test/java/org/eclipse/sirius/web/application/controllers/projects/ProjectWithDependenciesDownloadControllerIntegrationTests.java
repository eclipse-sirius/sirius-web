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
import static org.assertj.core.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests of the project download controllers for a project with dependencies.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectWithDependenciesDownloadControllerIntegrationTests extends AbstractIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with dependencies, when the download of the project is requested, then the manifest is exported")
    public void givenProjectWithDependenciesWhenTheDownloadOfProjectIsRequestedThenTheManifestIsExported() {
        var response = this.download(PapayaIdentifiers.PAPAYA_SAMPLE_PROJECT, null);

        try (ZipInputStream inputStream = new ZipInputStream(response.getBody().getInputStream())) {
            HashMap<String, ByteArrayOutputStream> zipEntries = this.toZipEntries(inputStream);
            assertThat(zipEntries).isNotEmpty().containsKey("Papaya Sample/manifest.json");

            String manifestContentExpected = """
                    {
                      "natures":[
                        "siriusComponents://nature?kind=papaya"
                      ],
                      "documentIdsToName":{
                        "a4495c9c-d00c-4f0e-a591-1176d102a4a1":"Sirius Web Architecture",
                        "56a66774-cbff-499f-a9fa-6b6600c86064":"GraphQL",
                        "a0473b31-912f-4d99-8b41-3d44a8a1b238":"Sirius Web Project",
                        "8139fdb7-bb71-4bca-b50b-9170870bbc0d":"Sirius Web Architecture"
                      },
                      "metamodels":[
                        "domain://buck",
                        "http://www.eclipse.org/emf/2002/Ecore",
                        "https://www.eclipse.org/sirius-web/papaya"
                      ],
                      "representations":{
                        "dd0080f8-430d-441f-99a4-f46c7d9b28ef":{
                          "targetObjectURI":"sirius:///a4495c9c-d00c-4f0e-a591-1176d102a4a1#569d3f9b-2a43-4254-b609-511258251d96",
                          "type":"siriusComponents://representation?type=Table",
                          "descriptionURI":"papaya_package_table_description"
                        }
                      },
                      "dependencies":[
                        "6f24a044-1605-484d-96c3-553ff6bc184d"
                      ]
                    }
                    """;

            String manifestContent = zipEntries.get("Papaya Sample/manifest.json").toString(StandardCharsets.UTF_8);
            var objectMapper = new ObjectMapper();
            assertThat(objectMapper.readTree(manifestContent)).isEqualTo(objectMapper.readTree(manifestContentExpected));
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    private ResponseEntity<Resource> download(String projectId, String name) {
        var uri = "http://localhost:" + this.port + "/api/projects/" + projectId;
        if (name != null) {
            uri += "?name=" + name;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.parseMediaType("application/zip")));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        var response = new TestRestTemplate().exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        return response;
    }

    private HashMap<String, ByteArrayOutputStream> toZipEntries(ZipInputStream inputStream) {
        HashMap<String, ByteArrayOutputStream> zipEntries = new HashMap<>();

        try {
            var zipEntry = inputStream.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    ByteArrayOutputStream entryBaos = new ByteArrayOutputStream();
                    inputStream.transferTo(entryBaos);
                    zipEntries.put(name, entryBaos);
                }
                zipEntry = inputStream.getNextEntry();
            }
        } catch (IOException exception) {
            fail(exception.getMessage());
        }

        return zipEntries;
    }

}
