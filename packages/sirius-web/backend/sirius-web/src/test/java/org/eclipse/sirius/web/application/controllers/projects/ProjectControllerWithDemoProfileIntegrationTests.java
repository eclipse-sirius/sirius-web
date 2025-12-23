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
package org.eclipse.sirius.web.application.controllers.projects;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.graphql.controllers.GraphQLPayload;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.application.project.services.BlankProjectTemplateProvider;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.domain.boundedcontexts.project.repositories.IProjectRepository;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateProjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.DeleteProjectMutationRunner;
import org.eclipse.sirius.web.tests.graphql.RenameProjectMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Integration tests of the project controllers with demo profile.
 *
 * @author gcoutable
 */
@Transactional
@ActiveProfiles("demo")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerWithDemoProfileIntegrationTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @LocalServerPort
    private int port;

    @Autowired
    private CreateProjectMutationRunner createProjectMutationRunner;

    @Autowired
    private DeleteProjectMutationRunner deleteProjectMutationRunner;

    @Autowired
    private IProjectRepository projectRepository;

    @Autowired
    private RenameProjectMutationRunner renameProjectMutationRunner;

    @Autowired
    private IMessageService messageService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a mutation to create a project is performed, then it returns an error payload")
    public void givenTheDemoProfileWhenAMutationToCreateAProjectIsPerformedThenItReturnsAnErrorPayload() {
        var input = new CreateProjectInput(UUID.randomUUID(), "New Project", BlankProjectTemplateProvider.BLANK_PROJECT_TEMPLATE_ID, List.of());
        var result = this.createProjectMutationRunner.run(input);

        String typeName = JsonPath.read(result.data(), "$.data.createProject.__typename");
        assertThat(typeName).isEqualTo(ErrorPayload.class.getSimpleName());

        String message = JsonPath.read(result.data(), "$.data.createProject.message");
        assertThat(message).isEqualTo(this.messageService.unauthorized());
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a mutation to delete a project is performed, then it returns an error payload")
    public void givenTheDemoProfileWhenAMutationToDeleteAProjectIsPerformedThenItReturnsAnErrorPayload() {
        var input = new DeleteProjectInput(UUID.randomUUID(), TestIdentifiers.SYSML_SAMPLE_PROJECT);
        var result = this.deleteProjectMutationRunner.run(input);

        String typeName = JsonPath.read(result.data(), "$.data.deleteProject.__typename");
        assertThat(typeName).isEqualTo(ErrorPayload.class.getSimpleName());

        String message = JsonPath.read(result.data(), "$.data.deleteProject.message");
        assertThat(message).isEqualTo(this.messageService.unauthorized());

        var project = this.projectRepository.findById(TestIdentifiers.SYSML_SAMPLE_PROJECT);
        assertThat(project).isPresent();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a project is uploaded, then it returns an error payload")
    public void givenTheDemoProfileWhenProjectIsUploadedThenItReturnsAnErrorPayload() {
        byte[] zipByte = this.getZipTestFile();

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
            Assertions.fail(exception.getMessage());
        }

        ByteArrayResource contentsAsResource = new ByteArrayResource(zipByte) {
            @Override
            public String getFilename() {
                return "Ecore Sample.zip";
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
            var result = new ObjectMapper().writeValueAsString(response.getBody());
            var typename = JsonPath.read(result, "$.data.uploadProject.__typename");
            assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());
            var message = JsonPath.read(result, "$.data.uploadProject.message");
            assertThat(message).isEqualTo(this.messageService.unauthorized());
        } catch (JsonProcessingException exception) {
            Assertions.fail(exception.getMessage());
        }
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a project is downloaded, then it returns not found")
    public void givenTheDemoProfileWhenProjectIsDownloadedThenItReturnsAnErrorPayload() {
        var uri = "http://localhost:" + this.port + "/api/projects/" + TestIdentifiers.ECORE_SAMPLE_PROJECT;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.parseMediaType("application/zip")));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        var response = new TestRestTemplate().exchange(uri, HttpMethod.GET, entity, Resource.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given the demo profile, when a project rename is requested, then it returns an error payload")
    public void givenTheDemoProfileWhenProjectRenameIsRequestedThenItReturnsAnErrorPayload() {
        assertThat(this.projectRepository.existsById(TestIdentifiers.ECORE_SAMPLE_PROJECT)).isTrue();

        var input = new RenameProjectInput(UUID.randomUUID(), TestIdentifiers.ECORE_SAMPLE_PROJECT, "New Name");
        var result = this.renameProjectMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        String typename = JsonPath.read(result.data(), "$.data.renameProject.__typename");
        assertThat(typename).isEqualTo(ErrorPayload.class.getSimpleName());

        var optionalProject = this.projectRepository.findById(TestIdentifiers.ECORE_SAMPLE_PROJECT);
        assertThat(optionalProject).isPresent();

        var project = optionalProject.get();
        assertThat(project.getName()).isEqualTo("Ecore Sample");
    }

    private byte[] getZipTestFile() {
        byte[] zipByte = null;
        String projectName = "Ecore Sample";
        String documentId = "48dc942a-6b76-4133-bca5-5b29ebee133d";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            // Add Manifest
            ZipEntry zipEntry = new ZipEntry(projectName + "/manifest.json");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.manifest().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add Document
            zipEntry = new ZipEntry(projectName + "/documents/" + documentId + "." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.document().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
        } catch (IOException exception) {
            Assertions.fail(exception.getMessage());
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
              ]
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
