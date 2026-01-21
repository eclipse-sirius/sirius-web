/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.services.migration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.sirius.components.graphql.controllers.GraphQLPayload;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationContentSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.semanticdata.SemanticData;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Integration tests for migration on outdated uploaded representations.
 *
 * @author tgiraudet
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ExportedRepresentationMigrationDataTestsMigrationParticipant.class)
public class ExportedRepresentationMigrationDataTests extends AbstractIntegrationTests {

    private static final String FLOW_SAMPLE = "FlowSample";

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IRepresentationMetadataSearchService representationMetadataSearchService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @Autowired
    private IRepresentationContentSearchService representationContentSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with an outdated representation, when the upload of the project is requested and the representation manifest has migration data, then the representation of the project has been migrated")
    public void givenProjectWithOutdatedRepresentationWhenUploadRequestedWithMigrationDataThenRepresentationHasBeenMigrated() {
        this.checkBeforeImportProject();
        byte[] zipByte = this.getZipTestFile(this.manifestWithMigrationData());
        this.checkImportedProject(this.upload(zipByte));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with an outdated representation, when the upload of the project is requested and the representation manifest does not have migration data, then the representation of the project has been migrated")
    public void givenProjectWithOutdatedRepresentationWhenUploadRequestedWithoutMigrationDataThenRepresentationHasBeenMigrated() {
        this.checkBeforeImportProject();
        byte[] zipByte = this.getZipTestFile(this.manifestWithoutMigrationData());
        this.checkImportedProject(this.upload(zipByte));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with an outdated representation, when uploaded then downloaded, then the downloaded manifest contains the latest migration version")
    public void givenProjectWithOutdatedRepresentationWhenUploadedThenDownloadedThenManifestContainsMigrationData() {
        byte[] zipByte = this.getZipTestFile(this.manifestWithMigrationData());
        String uploadResponse = this.upload(zipByte);
        String projectId = JsonPath.read(uploadResponse, "$.data.uploadProject.project.id");

        var downloadResponse = this.download(projectId);

        try (var inputStream = new ZipInputStream(downloadResponse.getBody().getInputStream())) {
            var zipEntries = this.toZipEntries(inputStream);

            var manifestEntry = zipEntries.keySet().stream()
                    .filter(key -> key.endsWith("/manifest.json"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("manifest.json not found in downloaded project"));

            String manifestContent = zipEntries.get(manifestEntry).toString(StandardCharsets.UTF_8);

            Map<String, Map<String, Object>> representations = JsonPath.read(manifestContent, "$.representations");
            assertThat(representations).isNotNull();
            assertThat(representations).isNotEmpty();

            representations.forEach((representationId, metadata) -> {
                String representationType = (String) metadata.get("type");

                if ("siriusComponents://representation?type=Diagram".equals(representationType)) {
                    assertThat(metadata).containsKey("migrationVersion");
                    assertThat(metadata.get("migrationVersion")).isEqualTo("9999.12.99-300012310901");

                    assertThat(metadata).containsKey("latestMigrationPerformed");
                    assertThat(metadata.get("latestMigrationPerformed")).isEqualTo("ExportedRepresentationMigrationDataTestsMigrationParticipant");
                }
            });
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    private void checkBeforeImportProject() {
        var representation = representation();
        var parseContext = JsonPath.using(Configuration.defaultConfiguration().setOptions(Option.SUPPRESS_EXCEPTIONS));

        List<Double> textValues = parseContext.parse(representation).read("$..position.x");
        assertThat(textValues).size().isGreaterThan(1);
        assertThat(textValues.get(0)).isEqualTo(141.0);
    }

    private void checkImportedProject(String response) {
        String newProjectId = JsonPath.read(response, "$.data.uploadProject.project.id");
        assertTrue(this.projectSearchService.existsById(newProjectId));

        var optionalProject = this.projectSearchService.findById(newProjectId);
        assertThat(optionalProject).isPresent();
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(FLOW_SAMPLE));

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(optionalProject.get().getId()));
        assertThat(optionalProjectSemanticData).isPresent();
        var semanticDataId = optionalProjectSemanticData.get().getSemanticData().getId();

        var representations = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(semanticDataId));
        assertThat(representations).hasSize(1);

        var semanticDataReference = AggregateReference.<SemanticData, UUID>to(semanticDataId);
        var representationReference = AggregateReference.<RepresentationMetadata, UUID>to(representations.get(0).getRepresentationMetadataId());

        var optionalRepresentationContent = representationContentSearchService.findContentById(semanticDataReference, representationReference);
        assertThat(optionalRepresentationContent).isPresent();
        
        var representationContent = optionalRepresentationContent.get();

        assertThat(representationContent.getMigrationVersion()).isEqualTo("9999.12.99-300012310901");
        assertThat(representationContent.getLastMigrationPerformed()).isEqualTo("ExportedRepresentationMigrationDataTestsMigrationParticipant");

        var parseContext = JsonPath.using(Configuration.defaultConfiguration().setOptions(Option.SUPPRESS_EXCEPTIONS));
        List<Double> textValues = parseContext.parse(representationContent.getContent()).read("$..position.x");
        assertThat(textValues).size().isGreaterThan(1);
        assertThat(textValues.get(0)).isEqualTo(0.0);
    }

    private String upload(byte[] zipByte) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

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
                return FLOW_SAMPLE + ".zip";
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

    private byte[] getZipTestFile(String manifest) {
        byte[] zipByte = null;
        String projectName = FLOW_SAMPLE;
        String representationId = "36d8ac62-3b92-4f00-bdd0-04e4c1d76645";
        String documentId = "706d5f78-3118-4435-905d-5e99ef862fa2";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            // Add Manifest
            ZipEntry zipEntry = new ZipEntry(projectName + "/manifest.json");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(manifest.getBytes(StandardCharsets.UTF_8));
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

    private String manifestWithoutMigrationData() {
        return """
            {
              "natures": [
                "siriusWeb://nature?kind=flow"
              ],
              "documentIdsToName": {
                "706d5f78-3118-4435-905d-5e99ef862fa2": "Flow"
              },
              "metamodels": [
                "http://www.obeo.fr/dsl/designer/sample/flow"
              ],
              "representations": {
                "36d8ac62-3b92-4f00-bdd0-04e4c1d76645": {
                  "targetObjectURI": "sirius:///706d5f78-3118-4435-905d-5e99ef862fa2#f3014941-158e-49ae-b198-cafbf2c889fb",
                  "type": "siriusComponents://representation?type=Diagram",
                  "descriptionURI": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38"
                }
              }
            }
            """;
    }

    private String manifestWithMigrationData() {
        return """
            {
              "natures": [
                "siriusWeb://nature?kind=flow"
              ],
              "documentIdsToName": {
                "706d5f78-3118-4435-905d-5e99ef862fa2": "Flow"
              },
              "metamodels": [
                "http://www.obeo.fr/dsl/designer/sample/flow"
              ],
              "representations": {
                "36d8ac62-3b92-4f00-bdd0-04e4c1d76645": {
                  "targetObjectURI": "sirius:///706d5f78-3118-4435-905d-5e99ef862fa2#f3014941-158e-49ae-b198-cafbf2c889fb",
                  "type": "siriusComponents://representation?type=Diagram",
                  "descriptionURI": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
                  "migrationVersion": "0",
                  "latestMigrationPerformed": "none"
                }
              }
            }
            """;
    }

    private String representation() {
        return String.format("""
            {
              "id": "36d8ac62-3b92-4f00-bdd0-04e4c1d76645",
              "projectId": "adf1c2e7-6304-4968-8c72-609092633872",
              "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
              "targetObjectId": "f3014941-158e-49ae-b198-cafbf2c889fb",
              "label": "Topography",
              "kind": "siriusComponents://representation?type=Diagram",
              "representation": %s
            }
            """, this.getRepresentationContent());
    }

    private String getRepresentationContent() {
        return String.format("""
            {
              "id": "36d8ac62-3b92-4f00-bdd0-04e4c1d76645",
              "kind": "siriusComponents://representation?type=Diagram",
              "targetObjectId": "f3014941-158e-49ae-b198-cafbf2c889fb",
              "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
              "nodes": [%s],
              "edges": [],
              "layoutData": %s
            }
            """, this.getNodes(), this.getLayoutData());
    }

    private String getNodes() {
        return String.format("""
            {
              "id": "2e27e803-cb18-3243-a0d4-be9d1b909a1f",
              "type": "node:image",
              "targetObjectId": "13cfb446-2623-4fb4-bf09-1aa34553ce55",
              "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataSource",
              "targetObjectLabel": "DataSource",
              "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=278b2d48-7f82-39b5-81d4-d036b8e6af54",
              "borderNode": false,
              "initialBorderNodePosition": "NONE",
              "modifiers": [],
              "state": "Normal",
              "collapsingState": "EXPANDED",
              "insideLabel": null,
              "outsideLabels": [%s],
              "style": %s,
              "borderNodes": [],
              "childNodes": [],
              "defaultWidth": 66,
              "defaultHeight": 90,
              "labelEditable": true,
              "deletable": true,
              "pinned": false,
              "customizedStyleProperties": [],
              "size": {
                "height": -1.0,
                "width": -1.0
              }
            }
            """, this.getOutsideLabels(), this.getNodeStyle());
    }

    private String getOutsideLabels() {
        return """
            {
              "id": "5366beab-3652-33f6-9cd0-1ae27bcbbbdd",
              "text": "DataSource",
              "outsideLabelLocation": "BOTTOM_MIDDLE",
              "style": {
                "color": "#002B3C",
                "fontSize": 14,
                "bold": false,
                "italic": false,
                "underline": false,
                "strikeThrough": false,
                "iconURL": [],
                "background": "transparent",
                "borderColor": "black",
                "borderSize": 0,
                "borderRadius": 3,
                "borderStyle": "Solid",
                "maxWidth": null,
                "visibility": "visible"
              },
              "overflowStrategy": "NONE",
              "textAlign": "LEFT",
              "customizedStyleProperties": []
            }
            """;
    }

    private String getNodeStyle() {
        return """
            {
              "imageURL": "/flow-images/sensor.svg",
              "scalingFactor": 1,
              "borderColor": "black",
              "borderSize": 0,
              "borderRadius": 3,
              "borderStyle": "Solid",
              "positionDependentRotation": false,
              "childrenLayoutStrategy": {
                "kind": "FreeForm"
              }
            }
            """;
    }

    private String getLayoutData() {
        return """
            {
              "nodeLayoutData": {
                "2e27e803-cb18-3243-a0d4-be9d1b909a1f": {
                  "id": "2e27e803-cb18-3243-a0d4-be9d1b909a1f",
                  "position": {
                    "x": 141.0,
                    "y": 350.2762219384777
                  },
                  "resizedByUser": false,
                  "movedByUser": true,
                  "handleLayoutData": [],
                  "minComputedSize": {
                    "width": 66.0,
                    "height": 90.0
                  }
                }
              },
              "edgeLayoutData": {},
              "labelLayoutData": {
                "5366beab-3652-33f6-9cd0-1ae27bcbbbdd": {
                  "id": "5366beab-3652-33f6-9cd0-1ae27bcbbbdd",
                  "position": {
                    "x": 141.0,
                    "y": 350.2762219384777
                  },
                  "resizedByUser": false,
                  "movedByUser": false
                }
              }
            }
            """;
    }

    private String document() {
        return """
            {
              "json": {
                "version": "1.0",
                "encoding": "utf-8"
              },
              "ns": {
                "flow": "http://www.obeo.fr/dsl/designer/sample/flow"
              },
              "content": [
                {
                  "id": "f3014941-158e-49ae-b198-cafbf2c889fb",
                  "eClass": "flow:System",
                  "data": {
                    "name": "NewSystem",
                    "elements": [
                      {
                        "id": "13cfb446-2623-4fb4-bf09-1aa34553ce55",
                        "eClass": "flow:DataSource",
                        "data": {
                          "name": "DataSource",
                          "volume": 6
                        }
                      }
                    ]
                  }
                }
              ]
            }
            """;
    }

    private ResponseEntity<Resource> download(String projectId) {
        var uri = "http://localhost:" + this.port + "/api/projects/" + projectId;

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
