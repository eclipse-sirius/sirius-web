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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Integration tests of the project upload controllers with an unsynchronised diagram and some layout datas.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectWithDiagramUnsynchroniszedUploadControllerTests extends AbstractIntegrationTests {

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
    @DisplayName("Given a project, when the upload of the project is requested, then the project with its unsynchronised representation and semantic data are available")
    public void givenProjectWhenTheUploadOfProjectIsRequestedThenTheUnsynchronisedRepresentationAndSemanticDataAreAvailable() {
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
        assertThat(representationMetaDatas.get(0)).extracting("label").isEqualTo("Topography unsynchronized");

        var representation = this.representationSearchService.findById(null, representationMetaDatas.get(0).getId().toString(), Diagram.class);
        assertThat(representation).isPresent();
        assertThat(representation.get().getNodes().size()).isEqualTo(2);
        assertThat(representation.get().getNodes().get(1).getChildNodes().size()).isEqualTo(1);
        assertThat(representation.get().getLayoutData().nodeLayoutData().size()).isEqualTo(3);
        assertThat(representation.get().getLayoutData().nodeLayoutData().get(representation.get().getNodes().get(0).getId())).isNotNull();
        assertThat(representation.get().getLayoutData().nodeLayoutData().get(representation.get().getNodes().get(1).getId())).isNotNull();
        assertThat(representation.get().getLayoutData().nodeLayoutData().get(representation.get().getNodes().get(1).getChildNodes().get(0).getId())).isNotNull();

        assertThat(representation.get().getEdges().size()).isEqualTo(1);
        assertThat(representation.get().getLayoutData().edgeLayoutData().size()).isEqualTo(1);
        assertThat(representation.get().getLayoutData().edgeLayoutData().get(representation.get().getEdges().get(0).getId())).isNotNull();

    }

    private byte[] getZipTestFile() {
        byte[] zipByte = null;
        String projectName = FLOW_SAMPLE;
        String representationId = "9b88d00e-9de2-4ceb-b9d2-c7c3e95579d5";
        String documentId = "682ea436-e23e-4f47-b997-70d6ad72b23d";

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
              "natures": ["siriusWeb://nature?kind=flow"],
              "documentIdsToName": { "682ea436-e23e-4f47-b997-70d6ad72b23d": "Flow" },
              "metamodels": [
                "domain://wiles",
                "http://www.obeo.fr/dsl/designer/sample/flow"
              ],
              "representations": {
                "9b88d00e-9de2-4ceb-b9d2-c7c3e95579d5": {
                  "descriptionURI": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=fac1c3c2-cc89-30fc-ada3-2c51f24d5275",
                  "type": "siriusComponents://representation?type=Diagram",
                  "targetObjectURI": "sirius:///682ea436-e23e-4f47-b997-70d6ad72b23d#318e6b63-be3f-41d3-9467-89ed63dc2b41"
                }
              }
            }
            """;
    }

    private String representation() {
        return """
            {
              "id": "9b88d00e-9de2-4ceb-b9d2-c7c3e95579d5",
              "projectId": "98a5321e-2732-4cbd-8235-65425abc07ed",
              "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=fac1c3c2-cc89-30fc-ada3-2c51f24d5275",
              "targetObjectId": "318e6b63-be3f-41d3-9467-89ed63dc2b41",
              "label": "Topography unsynchronized",
              "kind": "siriusComponents://representation?type=Diagram",
              "representation": {
                "id": "9b88d00e-9de2-4ceb-b9d2-c7c3e95579d5",
                "kind": "siriusComponents://representation?type=Diagram",
                "targetObjectId": "318e6b63-be3f-41d3-9467-89ed63dc2b41",
                "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=fac1c3c2-cc89-30fc-ada3-2c51f24d5275",
                %s
                %s
                %s
              }
            }
            """.formatted(getNodes(), getEdges(), getLayout());
    }

    private String getNodes() {
        return """
            "nodes": [
              %s
              {
                "id": "6267c929-b7a9-3798-aa68-7521087b56ec",
                "type": "node:rectangle",
                "targetObjectId": "98aee5cc-9893-445e-95a8-36923b33f5e1",
                "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
                "targetObjectLabel": "CompositeProcessor1",
                "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=1207e7e1-4194-3cbb-a036-4748c52f629f",
                "borderNode": false,
                "modifiers": [],
                "state": "Normal",
                "collapsingState": "EXPANDED",
                "insideLabel": {
                  "id": "670c4a91-ea89-353f-8c54-913b6420dc49",
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
                  "headerSeparatorDisplayMode": "NEVER",
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
                "childrenLayoutStrategy": { "kind": "FreeForm" },
                "borderNodes": [],
                %s
                "defaultWidth": 150,
                "defaultHeight": 70,
                "labelEditable": true,
                "pinned": false
              }
            ],
            """.formatted(getImageNode(), getChildNodes());
    }

    private String getImageNode() {
        return """
                {
                "id": "8c413729-434f-33f0-b54f-1f299baffaf0",
                "type": "node:image",
                "targetObjectId": "b80c5955-ef04-423d-b58e-c6071746d64c",
                "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataSource",
                "targetObjectLabel": "DataSource1",
                "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=f20eb9c3-0181-3c64-ab68-8dca5ae6aa49",
                "borderNode": false,
                "modifiers": [],
                "state": "Normal",
                "collapsingState": "EXPANDED",
                "insideLabel": null,
                "outsideLabels": [
                  {
                    "id": "6aed4d32-c0f9-3cad-b781-3f0a161fc1f2",
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
                "childrenLayoutStrategy": { "kind": "FreeForm" },
                "borderNodes": [],
                "childNodes": [],
                "defaultWidth": 66,
                "defaultHeight": 90,
                "labelEditable": true,
                "pinned": false
              },
            """;
    }

    private String getChildNodes() {
        return """
            "childNodes": [
              {
                "id": "eab592b4-afe6-3fdd-af54-b03d03a0269e",
                "type": "node:image",
                "targetObjectId": "d8021293-3e03-4c3e-a337-17c74b9cbd6b",
                "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=Processor",
                "targetObjectLabel": "Processor1",
                "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bda79bdd-10da-3c47-9655-98705b0c49aa",
                "borderNode": false,
                "modifiers": [],
                "state": "Normal",
                "collapsingState": "EXPANDED",
                "insideLabel": null,
                "outsideLabels": [
                  {
                    "id": "f0e5bf38-7691-3ea8-8f6a-e138b39ea6ff",
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
                "childrenLayoutStrategy": { "kind": "FreeForm" },
                "borderNodes": [],
                "childNodes": [],
                "defaultWidth": 150,
                "defaultHeight": 150,
                "labelEditable": true,
                "pinned": false
              }
            ],
            """;
    }

    private String getLayout() {
        return """
            "layoutData": {
              "nodeLayoutData": {
                "6267c929-b7a9-3798-aa68-7521087b56ec": {
                  "id": "6267c929-b7a9-3798-aa68-7521087b56ec",
                  "position": { "x": 637.3746671921161, "y": 35.32298260722773 },
                  "size": { "width": 203.1746240068104, "height": 217.02468732410244 },
                  "resizedByUser": false,
                  "handleLayoutData": []
                },
                "8c413729-434f-33f0-b54f-1f299baffaf0": {
                  "id": "8c413729-434f-33f0-b54f-1f299baffaf0",
                  "position": { "x": 481.95505817253127, "y": 310.04716080713234 },
                  "size": { "width": 66.0, "height": 90.0 },
                  "resizedByUser": false,
                  "handleLayoutData": []
                },
                "eab592b4-afe6-3fdd-af54-b03d03a0269e": {
                  "id": "eab592b4-afe6-3fdd-af54-b03d03a0269e",
                  "position": { "x": 45.174624006810404, "y": 43.024687324102445 },
                  "size": { "width": 150.0, "height": 150.0 },
                  "resizedByUser": false,
                  "handleLayoutData": []
                }
              },
              "edgeLayoutData": {
                "4b12d66b-4865-379f-b809-188dcdf2fa86": {
                  "id": "4b12d66b-4865-379f-b809-188dcdf2fa86",
                  "bendingPoints": [
                    { "x": 576.333931600402, "y": 356.0435917862337 },
                    { "x": 576.333931600402, "y": 52.347669931330174 },
                    { "x": 749.9620269137911, "y": 52.347669931330174 }
                  ],
                  "edgeAnchorLayoutData": []
                }
              },
              "labelLayoutData": {}
            }
            """;
    }

    private String getEdges() {
        return """
            "edges": [
              {
                "id": "4b12d66b-4865-379f-b809-188dcdf2fa86",
                "type": "edge:straight",
                "targetObjectId": "1d1f9720-d4ce-4e20-b97c-8589254b0b53",
                "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataFlow",
                "targetObjectLabel": "standard",
                "descriptionId": "siriusComponents://edgeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=4cf4890d-9a01-36fc-8196-b7c8d25287e9",
                "beginLabel": null,
                "centerLabel": {
                  "id": "0a0d4e60-4f65-3881-aebd-92176d9fe762",
                  "type": "label:edge-center",
                  "text": "6",
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
                "sourceId": "8c413729-434f-33f0-b54f-1f299baffaf0",
                "targetId": "eab592b4-afe6-3fdd-af54-b03d03a0269e",
                "modifiers": [],
                "state": "Normal",
                "style": {
                  "size": 1,
                  "lineStyle": "Dash",
                  "sourceArrow": "None",
                  "targetArrow": "InputClosedArrow",
                  "color": "#B1BCBE"
                },
                "centerLabelEditable": true
              }
            ],
            """;
    }

    private String document() {
        return """
            {
              "json": { "version": "1.0", "encoding": "utf-8" },
              "ns": { "flow": "http://www.obeo.fr/dsl/designer/sample/flow" },
              "content": [
                {
                  "id": "318e6b63-be3f-41d3-9467-89ed63dc2b41",
                  "eClass": "flow:System",
                  "data": {
                    "name": "NewSystem",
                    "elements": [
                      {
                        "id": "98aee5cc-9893-445e-95a8-36923b33f5e1",
                        "eClass": "flow:CompositeProcessor",
                        "data": {
                          "name": "CompositeProcessor1",
                          "elements": [
                            {
                              "id": "d8021293-3e03-4c3e-a337-17c74b9cbd6b",
                              "eClass": "flow:Processor",
                              "data": {
                                "incomingFlows": ["1d1f9720-d4ce-4e20-b97c-8589254b0b53"],
                                "name": "Processor1"
                              }
                            }
                          ]
                        }
                      },
                      {
                        "id": "b80c5955-ef04-423d-b58e-c6071746d64c",
                        "eClass": "flow:DataSource",
                        "data": {
                          "outgoingFlows": [
                            {
                              "id": "1d1f9720-d4ce-4e20-b97c-8589254b0b53",
                              "eClass": "flow:DataFlow",
                              "data": {
                                "usage": "standard",
                                "capacity": 6,
                                "load": 6,
                                "target": "d8021293-3e03-4c3e-a337-17c74b9cbd6b"
                              }
                            }
                          ],
                          "name": "DataSource1",
                          "volume": 6
                        }
                      },
                      {
                        "id": "de692388-1f80-4bd1-a188-980ef9914f24",
                        "eClass": "flow:CompositeProcessor",
                        "data": { "name": "CompositeProcessor2" }
                      }
                    ]
                  }
                }
              ]
            }
            """;
    }
}
