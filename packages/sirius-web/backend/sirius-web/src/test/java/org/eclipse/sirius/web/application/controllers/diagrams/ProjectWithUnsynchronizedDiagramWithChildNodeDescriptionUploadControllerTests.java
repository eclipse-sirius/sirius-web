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
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;
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

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.graphql.controllers.GraphQLPayload;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.application.representation.services.RepresentationDescriptionSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.web.services.diagrams.CustomNodesUnsynchonisedDiagramDescriptionProvider;
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
 * Integration tests of the project upload controllers with an unsynchronized diagram and child node description.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ProjectWithUnsynchronizedDiagramWithChildNodeDescriptionUploadControllerTests extends AbstractIntegrationTests {

    private static final String PROJECT_NAME = "Instance";

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @Autowired
    private IRepresentationMetadataSearchService representationMetadataSearchService;

    @Autowired
    private IRepresentationSearchService representationSearchService;

    @Autowired
    private CustomNodesUnsynchonisedDiagramDescriptionProvider customNodesDiagramDescriptionProvider;

    @Autowired
    private RepresentationDescriptionSearchService representationDescriptionSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with child node description, when the upload of the project is requested, then the unsynchronised representation contains the child node")
    public void givenProjectWithChildNodeDescriptionWhenUploadOfTheProjectIsRequestedThenTheUnsynchronisedRepresentationContainsTheChildNode() {
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
                return PROJECT_NAME + ".zip";
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
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(PROJECT_NAME));

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(optionalProject.get().getId()));
        assertThat(optionalProjectSemanticData).isPresent();
        var semanticDataId = optionalProjectSemanticData.get().getSemanticData().getId();

        var representationMetaDatas = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(semanticDataId));
        assertThat(representationMetaDatas).hasSize(1);
        assertThat(representationMetaDatas.get(0)).extracting("label").isEqualTo("Unsynchronized Root Description");

        var optionalDiagram = this.representationSearchService.findById(new IEditingContext.NoOp(), representationMetaDatas.get(0).getId().toString(), Diagram.class);
        assertThat(optionalDiagram).isPresent();

        var diagram = optionalDiagram.get();
        assertThat(diagram.getNodes().size()).isEqualTo(1);

        var rootNode = diagram.getNodes().get(0);
        assertThat(rootNode.getChildNodes()).hasSize(1);
    }

    private byte[] getZipTestFile() {
        byte[] zipByte = null;
        String projectName = PROJECT_NAME;
        String representationId = "e96cdc0e-7fe1-41bd-a185-9cecb4ba3e35";
        String documentId = "5b7291aa-0686-4d4f-aa7b-aab416dde82e";

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
                    "natures": [],
                    "documentIdsToName": {
                        "5b7291aa-0686-4d4f-aa7b-aab416dde82e": "Buck model"
                    },
                    "metamodels": [
                        "domain://buck"
                    ],
                    "representations": {
                        "e96cdc0e-7fe1-41bd-a185-9cecb4ba3e35": {
                            "descriptionURI": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=5a2ec092-0b05-410e-bdc2-0d56c0368165&sourceElementId=e42c06d3-3d01-40bf-871a-73495ea767c8",
                            "type": "siriusComponents://representation?type=Diagram",
                            "targetObjectURI": "sirius:///5b7291aa-0686-4d4f-aa7b-aab416dde82e#87fa4553-6889-4ce6-b017-d013987f9fae"
                        }
                    }
                }
                """;
    }

    private String representation() {
        return """
                {
                    "id": "e96cdc0e-7fe1-41bd-a185-9cecb4ba3e35",
                    "projectId": "bb66e0e9-4ab5-47ef-99f5-c6b26be995ea",
                    "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=5a2ec092-0b05-410e-bdc2-0d56c0368165&sourceElementId=e42c06d3-3d01-40bf-871a-73495ea767c8",
                    "targetObjectId": "87fa4553-6889-4ce6-b017-d013987f9fae",
                    "label": "Unsynchronized Root Description",
                    "kind": "siriusComponents://representation?type=Diagram",
                    "representation": {
                        "id": "e96cdc0e-7fe1-41bd-a185-9cecb4ba3e35",
                        "kind": "siriusComponents://representation?type=Diagram",
                        "targetObjectId": "87fa4553-6889-4ce6-b017-d013987f9fae",
                        "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=5a2ec092-0b05-410e-bdc2-0d56c0368165&sourceElementId=e42c06d3-3d01-40bf-871a-73495ea767c8",
                        "nodes": [
                            {
                                "id": "1c3d628d-3737-3986-8798-f9d709b71b2f",
                                "type": "node:rectangle",
                                "targetObjectId": "87fa4553-6889-4ce6-b017-d013987f9fae",
                                "targetObjectKind": "siriusComponents://semantic?domain=buck&entity=Root",
                                "targetObjectLabel": "Root",
                                "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=5a2ec092-0b05-410e-bdc2-0d56c0368165&sourceElementId=92cc1d59-487f-4e0a-8864-983915b562c8",
                                "borderNode": false,
                                "initialBorderNodePosition": "NONE",
                                "modifiers": [],
                                "state": "Normal",
                                "collapsingState": "EXPANDED",
                                "insideLabel": null,
                                "outsideLabels": [],
                                "style": {
                                    "background": "#ffffff",
                                    "borderColor": "#000000",
                                    "borderSize": 1,
                                    "borderRadius": 3,
                                    "borderStyle": "Solid",
                                    "childrenLayoutStrategy": {
                                        "kind": "FreeForm"
                                    }
                                },
                                "borderNodes": [],
                                "childNodes": [
                                    {
                                        "id": "30a3a8e4-811d-3305-9550-d7959969578b",
                                        "type": "node:rectangle",
                                        "targetObjectId": "2d72cfca-500c-4e02-8813-d5c54172fff2",
                                        "targetObjectKind": "siriusComponents://semantic?domain=buck&entity=Human",
                                        "targetObjectLabel": "John",
                                        "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=5a2ec092-0b05-410e-bdc2-0d56c0368165&sourceElementId=2c363d7d-b5ed-45b5-8f73-c7c3bb02493b",
                                        "borderNode": false,
                                        "initialBorderNodePosition": "NONE",
                                        "modifiers": [],
                                        "state": "Normal",
                                        "collapsingState": "EXPANDED",
                                        "insideLabel": null,
                                        "outsideLabels": [],
                                        "style": {
                                            "background": "pink[50]",
                                            "borderColor": "#000000",
                                            "borderSize": 1,
                                            "borderRadius": 3,
                                            "borderStyle": "Solid",
                                            "childrenLayoutStrategy": {
                                                "kind": "FreeForm"
                                            }
                                        },
                                        "borderNodes": [],
                                        "childNodes": [],
                                        "defaultWidth": null,
                                        "defaultHeight": null,
                                        "labelEditable": false,
                                        "pinned": false,
                                        "customizedStyleProperties": []
                                    }
                                ],
                                "defaultWidth": null,
                                "defaultHeight": null,
                                "labelEditable": false,
                                "pinned": false,
                                "customizedStyleProperties": []
                            }
                        ],
                        "edges": [],
                        "layoutData": {
                          "nodeLayoutData": {},
                          "edgeLayoutData": {},
                          "labelLayoutData": {}
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
                        "buck": "domain://buck"
                    },
                    "content": [
                        {
                            "id": "87fa4553-6889-4ce6-b017-d013987f9fae",
                            "eClass": "buck:Root",
                            "data": {
                                "label": "Root",
                                "humans": [
                                    {
                                        "id": "2d72cfca-500c-4e02-8813-d5c54172fff2",
                                        "eClass": "buck:Human",
                                        "data": {
                                            "name": "John"
                                        }
                                    },
                                    {
                                        "id": "5d504e23-d154-4ec0-a8bf-b8c773fbcf9d",
                                        "eClass": "buck:Human",
                                        "data": {
                                            "name": "Jane"
                                        }
                                    }
                                ]
                            }
                        }
                    ]
                }
                """;
    }
}
