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
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticDataDependency;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.services.api.ISemanticDataSearchService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Integration tests of the project upload controllers with a project using dependencies.
 *
 * @author jmallet
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectWithDependenciesUploadControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String ECORE_SAMPLE = "EcoreSample";

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;
    
    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @Autowired
    private ISemanticDataSearchService semanticDataSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when the upload of the project is requested, then the project with its representation and semantic data are available")
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
        assertTrue(this.projectSearchService.existsById(newProjectId));

        var optionalProject = this.projectSearchService.findById(newProjectId);
        assertThat(optionalProject).isPresent();
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo("Test"));

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(optionalProject.get().getId()));
        assertThat(optionalProjectSemanticData).isPresent();
        var semanticDataId = optionalProjectSemanticData.get().getSemanticData().getId();

        var optionalSemanticData = this.semanticDataSearchService.findById(semanticDataId);
        assertThat(optionalSemanticData).isPresent();
        var semanticData = optionalSemanticData.get();

        var dependencyIds = semanticData.getDependencies().stream()
                .map(SemanticDataDependency::dependencySemanticDataId)
                .map(AggregateReference::getId)
                .map(Object::toString)
                .toList();
        assertThat(dependencyIds).containsExactly("6f24a044-1605-484d-96c3-553ff6bc184d");
    }

    private byte[] getZipTestFile() {
        byte[] zipByte = null;
        String projectName = "Test";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            // Add Manifest
            ZipEntry zipEntry = new ZipEntry(projectName + "/manifest.json");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.manifest().getBytes(StandardCharsets.UTF_8));
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
                "siriusComponents://nature?kind=papaya"
              ],
              "documentIdsToName":{},
              "metamodels":[
                "https://www.eclipse.org/sirius-web/papaya"
              ],
              "representations":{
              },
              "dependencies":[
                "6f24a044-1605-484d-96c3-553ff6bc184d"
              ]
            }
            """;
    }
}
