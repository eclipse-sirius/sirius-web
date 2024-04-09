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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.sirius.components.graphql.controllers.GraphQLPayload;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Integration tests of the project upload controllers.
 *
 * @author jmallet
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectUploadControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String ECORE_SAMPLE = "EcoreSample";

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;


    @Autowired
    private IProjectSearchService projectSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a project, when the upload of the project is requested, then the project with its representation and semantic data are available")
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenTheUploadOfProjectIsRequestedThenTheRepresentationAndSemanticDataAreAvailable() {
        byte[] zipByte = this.getZipTestFile();
        this.checkImportedProject(this.upload(zipByte));
    }

    private String upload(byte[] zipByte) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Request arguments
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        var query = """
                mutation uploadProject($input: UploadProjectInput!) {
                        uploadProject(input: $input) {
                    __typename
                    ... on UploadProjectSuccessPayload {
                      project {
                        id
                      }
                    }
                    ... on ErrorPayload {
                      message
                    }
                  }
                }
                """;
        var payload = GraphQLPayload.newGraphQLPayload()
                .query(query)
                .variables(Map.of("input", new UploadProjectInput(UUID.randomUUID(), null)))
                .build();
        String operations = "";
        try {
            operations = new ObjectMapper().writeValueAsString(payload);
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }

        ByteArrayResource contentsAsResource = new ByteArrayResource(zipByte) {
            @Override
            public String getFilename() {
                return ECORE_SAMPLE + ".zip";
            }
        };

        body.add("operations", operations);
        body.add("map", "{\"0\":\"variables.file\"}");
        body.add("0", contentsAsResource);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = "http://localhost:" + this.port + "/api/graphql/upload";

        // Send http request
        var response = new TestRestTemplate().postForEntity(serverUrl, requestEntity, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        try {
            return new ObjectMapper().writeValueAsString(response.getBody());
        } catch (JsonProcessingException exception) {
            fail(exception.getMessage());
        }
        return "";
    }

    private void checkImportedProject(String response) {
        String newProjectId = JsonPath.read(response, "$.data.uploadProject.project.id");
        assertTrue(this.projectSearchService.existsById(UUID.fromString(newProjectId)));

        var optionalProject = this.projectSearchService.findById(UUID.fromString(newProjectId));
        assertThat(optionalProject).isPresent();
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(ECORE_SAMPLE));
    }

    private byte[] getZipTestFile() {
        byte[] zipByte = null;
        String projectName = ECORE_SAMPLE;
        String representationId = "e81eec5c-42d6-491c-8bcc-9beb951356f8";
        String documentId = "48dc942a-6b76-4133-bca5-5b29ebee133d";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            // Add Manifest
            ZipEntry zipEntry = new ZipEntry(projectName + "/manifest.json");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.manifest().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add Representation
            zipEntry = new ZipEntry(projectName + "/representations/" + representationId + "." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.representation().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add Document
            zipEntry = new ZipEntry(projectName + "/documents/" + documentId + "." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.document().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
        } catch (IOException exception) {
            fail(exception.getMessage());
        }

        if (outputStream.size() > 0) {
            zipByte = outputStream.toByteArray();
        }

        return zipByte;
    }

    private String manifest() {
        return """
            {
              "natures":[
                "ecore"
              ],
              "documentIdsToName":{
                "48dc942a-6b76-4133-bca5-5b29ebee133d":"Ecore"
              },
              "metamodels":[
                "https://www.eclipse.org/sirius/widgets/reference",
                "http://www.eclipse.org/emf/2002/Ecore"
              ],
              "representations":{
                "e81eec5c-42d6-491c-8bcc-9beb951356f8":{
                  "targetObjectURI":"sirius:///48dc942a-6b76-4133-bca5-5b29ebee133d#/",
                  "type":"siriusComponents://representation?type=Portal",
                  "descriptionURI":"69030a1b-0b5f-3c1d-8399-8ca260e4a672"
                }
              }
            }
            """;
    }

    private String representation() {
        return """
            {
              "id":"e81eec5c-42d6-491c-8bcc-9beb951356f8",
              "projectId":"99d336a2-3049-439a-8853-b104ffb22653",
              "descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672",
              "targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
              "label":"Portal",
              "kind":"siriusComponents://representation?type=Portal",
              "representation":{
                "id":"e81eec5c-42d6-491c-8bcc-9beb951356f8",
                "kind":"siriusComponents://representation?type=Portal",
                "descriptionId":"69030a1b-0b5f-3c1d-8399-8ca260e4a672",
                "label":"Portal",
                "targetObjectId":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
                "views":[
                  {
                    "id":"9e277e97-7f71-4bdd-99af-9eeb8bd7f2df",
                    "representationId":"05e44ccc-9363-443f-a816-25fc73e3e7f7"
                  }
                ],
                "layoutData":[
                  {
                    "portalViewId":"9e277e97-7f71-4bdd-99af-9eeb8bd7f2df",
                    "x":0,
                    "y":0,
                    "width":500,
                    "height":200
                  }
                ]
              }
            }
            """;
    }

    private String document() {
        return """
            {
              "json":{
                "version":"1.0",
                "encoding":"utf-8"
              },
              "ns":{
                "ecore":"http://www.eclipse.org/emf/2002/Ecore"
              },
              "content":[
                {
                  "id":"3237b215-ae23-48d7-861e-f542a4b9a4b8",
                  "eClass":"ecore:EPackage",
                  "data":{
                    "name":"Sample"
                  }
                }
              ]
            }
            """;
    }
}
