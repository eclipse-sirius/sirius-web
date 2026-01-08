/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationMetadataSearchService;
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
 * Integration tests of the project upload controllers with a project export in the version 2024.9.0.
 * The purpose of this test is to check that it is still possible to import a project created in an older version.
 * You should therefore not modify the input data for this test
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectUploadControllerTests extends AbstractIntegrationTests {

    private static final String FLOW_SAMPLE = "FlowSample";

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

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project, when the upload of the project is requested, then the project the representation and semantic data are available")
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

    private void checkImportedProject(String response) {
        String newProjectId = JsonPath.read(response, "$.data.uploadProject.project.id");
        assertTrue(this.projectSearchService.existsById(newProjectId));

        var optionalProject = this.projectSearchService.findById(newProjectId);
        assertThat(optionalProject).isPresent();
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(FLOW_SAMPLE));

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(optionalProject.get().getId()));
        assertThat(optionalProjectSemanticData).isPresent();
        var semanticDataId = optionalProjectSemanticData.get().getSemanticData().getId();

        var representationMetaDatas = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(semanticDataId));
        assertThat(representationMetaDatas).hasSize(1);
        assertThat(representationMetaDatas.get(0)).extracting("label").isEqualTo("Topography");

        var editingContext = new IEditingContext() {
            @Override
            public String getId() {
                return semanticDataId.toString();
            }
        };
        var optionalDiagram = this.representationSearchService.findById(editingContext, representationMetaDatas.get(0).getRepresentationMetadataId().toString(), Diagram.class);
        assertThat(optionalDiagram).isPresent();

        var diagram = optionalDiagram.get();
        assertThat(diagram.getNodes().size()).isEqualTo(2);
        assertThat(diagram.getEdges().size()).isEqualTo(1);
    }

    private byte[] getZipTestFile() {
        byte[] zipByte = null;
        String projectName = FLOW_SAMPLE;
        String representationId = "a1865f97-c6b4-4214-91f2-5ed63311389d";
        String documentId = "d8e57d65-b8fd-45a4-a3a6-77b31c123be2";

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
                  "natures": [
                    "siriusWeb://nature?kind=flow"
                  ],
                  "documentIdsToName": {
                    "d8e57d65-b8fd-45a4-a3a6-77b31c123be2": "Flow"
                  },
                  "metamodels": [
                    "http://www.obeo.fr/dsl/designer/sample/flow"
                  ],
                  "representations": {
                    "a1865f97-c6b4-4214-91f2-5ed63311389d": {
                      "descriptionURI": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
                      "type": "siriusComponents://representation?type=Diagram",
                      "targetObjectURI": "sirius:///d8e57d65-b8fd-45a4-a3a6-77b31c123be2#/"
                    }
                  }
                }
                """;
    }

    private String representation() {
        return String.format("""
                        {
                            "id": "a1865f97-c6b4-4214-91f2-5ed63311389d",
                            "projectId": "35982c8e-107b-46e9-8ca9-4f9cd95b6775",
                            "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
                            "targetObjectId": "400faca9-fe5b-4f6e-9380-a0e3c887dca2",
                            "label": "Topography",
                            "kind": "siriusComponents://representation?type=Diagram",
                            "representation": {
                                "id": "a1865f97-c6b4-4214-91f2-5ed63311389d",
                                "kind": "siriusComponents://representation?type=Diagram",
                                "targetObjectId": "400faca9-fe5b-4f6e-9380-a0e3c887dca2",
                                "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
                                "label": "Topography",
                                "position": {"x": -1.0, "y": -1.0},
                                "size": {"width": -1.0, "height": -1.0},
                                "nodes": %s,
                                "edges": %s,
                                "layoutData": %s
                            }
                        }
                        """,
                this.getNodes(),
                this.getEdges(),
                this.getLayoutData()
        );
    }

    private String getNodes() {
        return String.format("""
                        [
                            %s,
                            %s
                        ]
                        """,
                this.getImageNode(),
                this.getRectangleNode()
        );
    }

    private String getImageNode() {
        return """
                {
                    "id": "ff57e3fd-66f5-30b2-96ca-cb4da2f672a6",
                    "type": "node:image",
                    "targetObjectId": "0fd894cf-ef87-4b61-9fc4-a5d7734674b2",
                    "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataSource",
                    "targetObjectLabel": "DataSource1",
                    "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=278b2d48-7f82-39b5-81d4-d036b8e6af54",
                    "borderNode": false,
                    "modifiers": [],
                    "state": "Normal",
                    "collapsingState": "EXPANDED",
                    "insideLabel": null,
                    "outsideLabels": [
                        {
                            "id": "7269af66-5772-362c-b4c8-d06c4c22244b",
                            "text": "DataSource1",
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
                                "maxWidth": null
                            },
                            "overflowStrategy": "NONE",
                            "textAlign": "LEFT"
                        }
                    ],
                    "style": {
                        "imageURL": "/flow-images/sensor.svg",
                        "scalingFactor": 1,
                        "borderColor": "black",
                        "borderSize": 0,
                        "borderRadius": 3,
                        "borderStyle": "Solid",
                        "positionDependentRotation": false
                    },
                    "childrenLayoutStrategy": {"kind": "FreeForm"},
                    "position": {"x": -1.0, "y": -1.0},
                    "size": {"width": 66.0, "height": 90.0},
                    "borderNodes": [],
                    "childNodes": [],
                    "customizedProperties": [],
                    "defaultWidth": 66,
                    "defaultHeight": 90,
                    "labelEditable": true,
                    "pinned": false
                }""";
    }

    private String getRectangleNode() {
        return String.format("""
                        {
                            "id": "46e1cdf7-b8f4-3b75-b87f-995abb74514e",
                            "type": "node:rectangle",
                            "targetObjectId": "8ad2aba5-a54e-4841-a510-759d6fdfec98",
                            "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
                            "targetObjectLabel": "CompositeProcessor1",
                            "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=4db7fccf-c76f-3c65-bbad-e1f7f82953f9",
                            "borderNode": false,
                            "modifiers": [],
                            "state": "Normal",
                            "collapsingState": "EXPANDED",
                            "insideLabel": {
                                "id": "50ec6541-fd20-3e9b-9813-aa07c653e153",
                                "text": "CompositeProcessor1",
                                "insideLabelLocation": "TOP_CENTER",
                                "style": {
                                    "color": "#002B3C",
                                    "fontSize": 14,
                                    "bold": true,
                                    "italic": false,
                                    "underline": false,
                                    "strikeThrough": false,
                                    "iconURL": [],
                                    "background": "transparent",
                                    "borderColor": "black",
                                    "borderSize": 0,
                                    "borderRadius": 3,
                                    "borderStyle": "Solid",
                                    "maxWidth": null
                                },
                                "isHeader": true,
                                "displayHeaderSeparator": false,
                                "overflowStrategy": "NONE",
                                "textAlign": "LEFT"
                            },
                            "outsideLabels": [],
                            "style": {
                                "background": "#F0F0F0",
                                "borderColor": "#B1BCBE",
                                "borderSize": 1,
                                "borderRadius": 0,
                                "borderStyle": "Solid"
                            },
                            "childrenLayoutStrategy": {"kind": "FreeForm"},
                            "position": {"x": -1.0, "y": -1.0},
                            "size": {"width": 150.0, "height": 70.0},
                            "borderNodes": [],
                            "childNodes": [%s],
                            "customizedProperties": [],
                            "defaultWidth": 150,
                            "defaultHeight": 70,
                            "labelEditable": true,
                            "pinned": false
                        }
                        """,
                this.getChildNodes()
        );
    }

    private String getChildNodes() {
        return """
                {
                    "id": "68bcf501-f31d-341b-af89-ae6d1bb00d2f",
                    "type": "node:image",
                    "targetObjectId": "2023d28e-8c19-4044-af94-4ea3cd7dc5da",
                    "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=Processor",
                    "targetObjectLabel": "Processor1",
                    "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=d25f3cf7-9ccf-3947-9e1b-03933f469fd2",
                    "borderNode": false,
                    "modifiers": [],
                    "state": "Normal",
                    "collapsingState": "EXPANDED",
                    "insideLabel": null,
                    "outsideLabels": [
                        {
                            "id": "07d26d1f-7e7f-331b-a89b-f3cea85f07fe",
                            "text": "Processor1",
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
                                "maxWidth": null
                            },
                            "overflowStrategy": "NONE",
                            "textAlign": "LEFT"
                        }
                    ],
                    "style": {
                        "imageURL": "/flow-images/cpu_unused.svg",
                        "scalingFactor": 1,
                        "borderColor": "black",
                        "borderSize": 0,
                        "borderRadius": 3,
                        "borderStyle": "Solid",
                        "positionDependentRotation": false
                    },
                    "childrenLayoutStrategy": {"kind": "FreeForm"},
                    "position": {"x": -1.0, "y": -1.0},
                    "size": {"width": 150.0, "height": 150.0},
                    "borderNodes": [],
                    "childNodes": [],
                    "customizedProperties": [],
                    "defaultWidth": 150,
                    "defaultHeight": 150,
                    "labelEditable": true,
                    "pinned": false
                }""";
    }

    private String getEdges() {
        return """
                [
                    {
                        "id": "d008e23d-e2d5-30ae-8e78-701b098c98b3",
                        "type": "edge:straight",
                        "targetObjectId": "73283f1d-654a-47bb-a263-81ad8fd45fe1",
                        "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataFlow",
                        "targetObjectLabel": "standard",
                        "descriptionId": "siriusComponents://edgeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=0575c2ae-0469-341f-ae36-9ace98e0a2c6",
                        "beginLabel": null,
                        "centerLabel": {
                            "id": "15559140-0fe0-3f23-a208-fd39019a2389",
                            "type": "label:edge-center",
                            "text": "6",
                            "position": {"x": -1.0, "y": -1.0},
                            "size": {"width": -1.0, "height": -1.0},
                            "alignment": {"x": -1.0, "y": -1.0},
                            "style": {
                                "color": "#B1BCBE",
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
                                "maxWidth": null
                            }
                        },
                        "endLabel": null,
                        "sourceId": "ff57e3fd-66f5-30b2-96ca-cb4da2f672a6",
                        "targetId": "68bcf501-f31d-341b-af89-ae6d1bb00d2f",
                        "modifiers": [],
                        "state": "Normal",
                        "style": {
                            "size": 1,
                            "lineStyle": "Dash",
                            "sourceArrow": "None",
                            "targetArrow": "InputClosedArrow",
                            "color": "#B1BCBE"
                        },
                        "routingPoints": [],
                        "sourceAnchorRelativePosition": {"x": -1.0, "y": -1.0},
                        "targetAnchorRelativePosition": {"x": -1.0, "y": -1.0},
                        "centerLabelEditable": true
                    }
                ]""";
    }

    private String getLayoutData() {
        return """
                {
                    "nodeLayoutData": {
                        "ff57e3fd-66f5-30b2-96ca-cb4da2f672a6": {
                            "id": "ff57e3fd-66f5-30b2-96ca-cb4da2f672a6",
                            "position": {"x": 0.0, "y": 0.0},
                            "size": {"width": 66.0, "height": 90.0},
                            "resizedByUser": false
                        },
                        "68bcf501-f31d-341b-af89-ae6d1bb00d2f": {
                            "id": "68bcf501-f31d-341b-af89-ae6d1bb00d2f",
                            "position": {"x": 8.0, "y": 34.366668701171875},
                            "size": {"width": 150.0, "height": 150.0},
                            "resizedByUser": false
                        },
                        "46e1cdf7-b8f4-3b75-b87f-995abb74514e": {
                            "id": "46e1cdf7-b8f4-3b75-b87f-995abb74514e",
                            "position": {"x": 86.0, "y": 0.0},
                            "size": {"width": 197.18333435058594, "height": 209.4666748046875},
                            "resizedByUser": false
                        }
                    },
                    "edgeLayoutData": {},
                    "labelLayoutData": {}
                }""";
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
                      "id": "400faca9-fe5b-4f6e-9380-a0e3c887dca2",
                      "eClass": "flow:System",
                      "data": {
                        "name": "NewSystem",
                        "elements": [
                          {
                            "id": "8ad2aba5-a54e-4841-a510-759d6fdfec98",
                            "eClass": "flow:CompositeProcessor",
                            "data": {
                              "name": "CompositeProcessor1",
                              "elements": [
                                {
                                  "id": "2023d28e-8c19-4044-af94-4ea3cd7dc5da",
                                  "eClass": "flow:Processor",
                                  "data": {
                                    "incomingFlows": [
                                      "//@elements.1/@outgoingFlows.0"
                                    ],
                                    "name": "Processor1"
                                  }
                                }
                              ]
                            }
                          },
                          {
                            "id": "0fd894cf-ef87-4b61-9fc4-a5d7734674b2",
                            "eClass": "flow:DataSource",
                            "data": {
                              "outgoingFlows": [
                                {
                                  "id": "73283f1d-654a-47bb-a263-81ad8fd45fe1",
                                  "eClass": "flow:DataFlow",
                                  "data": {
                                    "usage": "standard",
                                    "capacity": 6,
                                    "load": 6,
                                    "target": "//@elements.0/@elements.0"
                                  }
                                }
                              ],
                              "name": "DataSource1",
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
}
