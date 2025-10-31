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
 * Verify that custom appearances and layout data are preserved after uploading a project with a diagram representation.
 *
 * @author frouene
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiagramRepresentationUploadControllerTests extends AbstractIntegrationTests {

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
    @DisplayName("Given a project with a diagram representation, when the upload of the project is requested, then all the custom appearances and layout data are preserved")
    public void givenProjectWithDiagramRepresentationWhenTheUploadOfProjectIsRequestedThenAppearancesAndLayoutDataArePreserved() {
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

        var optionalDiagram = this.representationSearchService.findById(new IEditingContext.NoOp(), representationMetaDatas.get(0).getId().toString(), Diagram.class);
        assertThat(optionalDiagram).isPresent();

        var diagram = optionalDiagram.get();
        assertThat(diagram.getNodes().size()).isEqualTo(2);
        assertThat(diagram.getEdges().size()).isEqualTo(1);
        assertThat(diagram.getLayoutData().edgeLayoutData().size()).isEqualTo(1);
        assertThat(diagram.getLayoutData().edgeLayoutData().values().stream().map(edgeLayoutData -> edgeLayoutData.bendingPoints().size())).allMatch(size -> size == 4);
        assertThat(diagram.getLayoutData().nodeLayoutData().size()).isEqualTo(3);
        assertThat(diagram.getLayoutData().labelLayoutData().size()).isEqualTo(3);
        var edge = diagram.getEdges().get(0);
        assertThat(edge.getCustomizedStyleProperties().size()).isEqualTo(4);
        var edgeStyle = edge.getStyle();
        assertThat(edgeStyle.getColor()).isEqualTo("red");
        var nodeImage = diagram.getNodes().get(0);
        assertThat(nodeImage.getCustomizedStyleProperties().size()).isEqualTo(1);
        var nodeRectangularChild = diagram.getNodes().get(1).getChildNodes().get(0);
        assertThat(nodeRectangularChild.getCustomizedStyleProperties().size()).isEqualTo(1);
    }

    private byte[] getZipTestFile() {
        byte[] zipByte = null;
        String projectName = FLOW_SAMPLE;
        String representationId = "ff02f72f-6be4-43bb-b581-aa9dd6a7b9d2";
        String documentId = "f03b8538-17f9-4df3-82b1-d378b6ce4e4e";

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
                     "f03b8538-17f9-4df3-82b1-d378b6ce4e4e": "Flow"
                   },
                   "metamodels": [
                     "http://www.obeo.fr/dsl/designer/sample/flow"
                   ],
                   "representations": {
                     "ff02f72f-6be4-43bb-b581-aa9dd6a7b9d2": {
                       "descriptionURI": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
                       "type": "siriusComponents://representation?type=Diagram",
                       "targetObjectURI": "sirius:///f03b8538-17f9-4df3-82b1-d378b6ce4e4e#9a543523-e43b-436b-a642-5ee5dcee0a95"
                     }
                   }
                 }
                """;
    }

    private String representation() {
        return String.format("""
                        {
                              "id": "ff02f72f-6be4-43bb-b581-aa9dd6a7b9d2",
                              "projectId": "3921036d-b0ba-4ea7-baf2-1d1fd32ce6b6",
                              "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
                              "targetObjectId": "9a543523-e43b-436b-a642-5ee5dcee0a95",
                              "label": "Topography",
                              "kind": "siriusComponents://representation?type=Diagram",
                              "representation": {
                                "id": "ff02f72f-6be4-43bb-b581-aa9dd6a7b9d2",
                                "kind": "siriusComponents://representation?type=Diagram",
                                "targetObjectId": "9a543523-e43b-436b-a642-5ee5dcee0a95",
                                "descriptionId": "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=bce2748b-a1e5-39e6-ad86-a29323589b38",
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
                   "id": "b4499e7b-4dca-367b-a62a-3cd80f3686d7",
                   "type": "node:image",
                   "targetObjectId": "0cd9cad0-61e4-4fdd-9542-7c136b379ca3",
                   "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataSource",
                   "targetObjectLabel": "DataSource1",
                   "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=278b2d48-7f82-39b5-81d4-d036b8e6af54",
                   "borderNode": false,
                   "initialBorderNodePosition": "NONE",
                   "modifiers": [],
                   "state": "Normal",
                   "collapsingState": "EXPANDED",
                   "insideLabel": null,
                   "outsideLabels": [
                     {
                       "id": "cc6c2cfd-fc24-35c3-8233-346317e8da55",
                       "text": "DataSource1",
                       "outsideLabelLocation": "BOTTOM_MIDDLE",
                       "style": {
                         "color": "#002B3C",
                         "fontSize": 14,
                         "bold": false,
                         "italic": false,
                         "underline": false,
                         "strikeThrough": true,
                         "iconURL": [],
                         "background": "red",
                         "borderColor": "black",
                         "borderSize": 2,
                         "borderRadius": 3,
                         "borderStyle": "Dash",
                         "maxWidth": null
                       },
                       "overflowStrategy": "NONE",
                       "textAlign": "LEFT",
                       "customizedStyleProperties": [
                         "STRIKE_THROUGH",
                         "BACKGROUND",
                         "BORDER_SIZE",
                         "BORDER_STYLE"
                       ]
                     }
                   ],
                   "style": {
                     "imageURL": "/flow-images/sensor.svg",
                     "scalingFactor": 1,
                     "borderColor": "black",
                     "borderSize": 2,
                     "borderRadius": 3,
                     "borderStyle": "Solid",
                     "positionDependentRotation": false,
                     "childrenLayoutStrategy": {
                       "kind": "FreeForm"
                     }
                   },
                   "borderNodes": [],
                   "childNodes": [],
                   "defaultWidth": 66,
                   "defaultHeight": 90,
                   "labelEditable": true,
                   "pinned": false,
                   "customizedStyleProperties": [
                     "BORDER_SIZE"
                   ]
                }""";
    }

    private String getRectangleNode() {
        return String.format("""
                        {
                            "id": "7959c437-6a52-389a-a3d1-9acf93126c63",
                            "type": "node:rectangle",
                            "targetObjectId": "1eeb518d-5234-4d30-b91e-d8633dd7e954",
                            "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=CompositeProcessor",
                            "targetObjectLabel": "CompositeProcessor1",
                            "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=4db7fccf-c76f-3c65-bbad-e1f7f82953f9",
                            "borderNode": false,
                            "initialBorderNodePosition": "NONE",
                            "modifiers": [],
                            "state": "Normal",
                            "collapsingState": "EXPANDED",
                            "insideLabel": {
                              "id": "5cdb9357-45c6-362a-9f41-fb203b87d6dc",
                              "text": "CompositeProcessor1",
                              "insideLabelLocation": "TOP_CENTER",
                              "style": {
                                "color": "#002B3C",
                                "fontSize": 23,
                                "bold": true,
                                "italic": true,
                                "underline": true,
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
                              "textAlign": "LEFT",
                              "customizedStyleProperties": [
                                "FONT_SIZE",
                                "ITALIC",
                                "UNDERLINE"
                              ]
                            },
                            "outsideLabels": [],
                            "style": {
                              "background": "blue",
                              "borderColor": "#B1BCBE",
                              "borderSize": 1,
                              "borderRadius": 0,
                              "borderStyle": "Solid",
                              "childrenLayoutStrategy": {
                                "kind": "FreeForm"
                              }
                            },
                            "borderNodes": [],
                            "childNodes": [%s],
                            "defaultWidth": 150,
                            "defaultHeight": 70,
                            "labelEditable": true,
                            "pinned": false,
                            "customizedStyleProperties": [
                              "BACKGROUND"
                            ]
                        }
                        """,
                this.getChildNodes()
        );
    }

    private String getChildNodes() {
        return """
                {
                   "id": "61716a74-ab57-3e47-985c-1a05493c5702",
                   "type": "node:image",
                   "targetObjectId": "6bd1de86-74ce-4538-a2dc-92aabb9d5029",
                   "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=Processor",
                   "targetObjectLabel": "Processor1",
                   "descriptionId": "siriusComponents://nodeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=d25f3cf7-9ccf-3947-9e1b-03933f469fd2",
                   "borderNode": false,
                   "initialBorderNodePosition": "NONE",
                   "modifiers": [],
                   "state": "Normal",
                   "collapsingState": "EXPANDED",
                   "insideLabel": null,
                   "outsideLabels": [
                     {
                       "id": "2c63df9f-3772-39f7-83bb-fe0b40b5aedb",
                       "text": "Processor1",
                       "outsideLabelLocation": "BOTTOM_MIDDLE",
                       "style": {
                         "color": "#002B3C",
                         "fontSize": 23,
                         "bold": false,
                         "italic": false,
                         "underline": true,
                         "strikeThrough": false,
                         "iconURL": [],
                         "background": "transparent",
                         "borderColor": "black",
                         "borderSize": 2,
                         "borderRadius": 3,
                         "borderStyle": "Solid",
                         "maxWidth": null
                       },
                       "overflowStrategy": "NONE",
                       "textAlign": "LEFT",
                       "customizedStyleProperties": [
                         "FONT_SIZE",
                         "UNDERLINE",
                         "BORDER_SIZE"
                       ]
                     }
                   ],
                   "style": {
                     "imageURL": "/flow-images/cpu_unused.svg",
                     "scalingFactor": 1,
                     "borderColor": "black",
                     "borderSize": 3,
                     "borderRadius": 3,
                     "borderStyle": "Solid",
                     "positionDependentRotation": false,
                     "childrenLayoutStrategy": {
                       "kind": "FreeForm"
                     }
                   },
                   "borderNodes": [],
                   "childNodes": [],
                   "defaultWidth": 150,
                   "defaultHeight": 150,
                   "labelEditable": true,
                   "pinned": false,
                   "customizedStyleProperties": [
                     "BORDER_SIZE"
                   ]
                }""";
    }

    private String getEdges() {
        return """
                [
                    {
                     "id": "d726d93b-c4fe-34de-aada-3c12f1cf905c",
                     "type": "edge:straight",
                     "targetObjectId": "f400e421-013f-4a7c-9fd3-28c844bafcc4",
                     "targetObjectKind": "siriusComponents://semantic?domain=flow&entity=DataFlow",
                     "targetObjectLabel": "standard",
                     "descriptionId": "siriusComponents://edgeDescription?sourceKind=view&sourceId=942b5891-9b51-3fba-90ab-f5e49ccf345e&sourceElementId=0575c2ae-0469-341f-ae36-9ace98e0a2c6",
                     "beginLabel": null,
                     "centerLabel": {
                       "id": "bb2dfd59-253c-3f4b-b0e3-822d8b5229b7",
                       "type": "label:edge-center",
                       "text": "6",
                       "style": {
                         "color": "#B1BCBE",
                         "fontSize": 34,
                         "bold": true,
                         "italic": true,
                         "underline": false,
                         "strikeThrough": false,
                         "iconURL": [],
                         "background": "transparent",
                         "borderColor": "black",
                         "borderSize": 1,
                         "borderRadius": 3,
                         "borderStyle": "Solid",
                         "maxWidth": null
                       },
                       "customizedStyleProperties": [
                         "FONT_SIZE",
                         "BOLD",
                         "ITALIC",
                         "BORDER_SIZE"
                       ]
                     },
                     "endLabel": null,
                     "sourceId": "b4499e7b-4dca-367b-a62a-3cd80f3686d7",
                     "targetId": "61716a74-ab57-3e47-985c-1a05493c5702",
                     "modifiers": [],
                     "state": "Normal",
                     "style": {
                       "size": 2,
                       "lineStyle": "Dash",
                       "sourceArrow": "InputClosedArrow",
                       "targetArrow": "OutputArrow",
                       "color": "red",
                       "edgeType": "Manhattan"
                     },
                     "centerLabelEditable": true,
                     "customizedStyleProperties": [
                       "COLOR",
                       "SIZE",
                       "SOURCE_ARROW",
                       "TARGET_ARROW"
                     ]
                   }
                ]""";
    }

    private String getLayoutData() {
        return String.format("""
                {
                   "nodeLayoutData": %s,
                   "edgeLayoutData": %s,
                   "labelLayoutData": %s
                }""", this.getNodeLayoutData(), this.getEdgeLayoutData(), this.getLabelLayoutData());
    }

    private String getNodeLayoutData() {
        return """
                {
                     "61716a74-ab57-3e47-985c-1a05493c5702": {
                       "id": "61716a74-ab57-3e47-985c-1a05493c5702",
                       "position": {
                         "x": 147.88440594412273,
                         "y": 151.3716742801385
                       },
                       "size": {
                         "width": 150.0,
                         "height": 150.0
                       },
                       "resizedByUser": false,
                       "handleLayoutData": [
                         {
                           "edgeId": "d726d93b-c4fe-34de-aada-3c12f1cf905c",
                           "position": {
                             "x": 72.0,
                             "y": -6.0
                           },
                           "handlePosition": "bottom",
                           "type": "target"
                         }
                       ]
                     },
                     "7959c437-6a52-389a-a3d1-9acf93126c63": {
                       "id": "7959c437-6a52-389a-a3d1-9acf93126c63",
                       "position": {
                         "x": 231.38226932382213,
                         "y": -108.1005411827095
                       },
                       "size": {
                         "width": 305.8844059441227,
                         "height": 333.3716742801385
                       },
                       "resizedByUser": false,
                       "handleLayoutData": []
                     },
                     "b4499e7b-4dca-367b-a62a-3cd80f3686d7": {
                       "id": "b4499e7b-4dca-367b-a62a-3cd80f3686d7",
                       "position": {
                         "x": -222.71969736179307,
                         "y": -263.54765211469396
                       },
                       "size": {
                         "width": 66.0,
                         "height": 90.0
                       },
                       "resizedByUser": false,
                       "handleLayoutData": [
                         {
                           "edgeId": "d726d93b-c4fe-34de-aada-3c12f1cf905c",
                           "position": {
                             "x": 30.0,
                             "y": 0.0
                           },
                           "handlePosition": "top",
                           "type": "source"
                         }
                       ]
                     }
                   }
                """;
    }

    private String getEdgeLayoutData() {
        return """
                {
                     "d726d93b-c4fe-34de-aada-3c12f1cf905c": {
                       "id": "d726d93b-c4fe-34de-aada-3c12f1cf905c",
                       "bendingPoints": [
                         {
                           "x": -189.7067469775894,
                           "y": -304.5654548606087
                         },
                         {
                           "x": -39.19579244657988,
                           "y": -304.5654548606087
                         },
                         {
                           "x": -39.19579244657988,
                           "y": 266.04787738625765
                         },
                         {
                           "x": 454.2758951569607,
                           "y": 266.04787738625765
                         }
                       ],
                       "edgeAnchorLayoutData": []
                     }
                   }
                """;
    }

    private String getLabelLayoutData() {
        return """
                {
                     "2c63df9f-3772-39f7-83bb-fe0b40b5aedb": {
                       "id": "2c63df9f-3772-39f7-83bb-fe0b40b5aedb",
                       "position": {
                         "x": -15.163349784584591,
                         "y": -186.14943811527223
                       }
                     },
                     "cc6c2cfd-fc24-35c3-8233-346317e8da55": {
                       "id": "cc6c2cfd-fc24-35c3-8233-346317e8da55",
                       "position": {
                         "x": -49.38807298262425,
                         "y": 160.93699644337897
                       }
                     },
                     "bb2dfd59-253c-3f4b-b0e3-822d8b5229b7": {
                       "id": "bb2dfd59-253c-3f4b-b0e3-822d8b5229b7",
                       "position": {
                         "x": -63.012368977830945,
                         "y": -173.70977393888518
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
                       "id": "9a543523-e43b-436b-a642-5ee5dcee0a95",
                       "eClass": "flow:System",
                       "data": {
                         "name": "NewSystem",
                         "elements": [
                           {
                             "id": "1eeb518d-5234-4d30-b91e-d8633dd7e954",
                             "eClass": "flow:CompositeProcessor",
                             "data": {
                               "name": "CompositeProcessor1",
                               "elements": [
                                 {
                                   "id": "6bd1de86-74ce-4538-a2dc-92aabb9d5029",
                                   "eClass": "flow:Processor",
                                   "data": {
                                     "incomingFlows": [
                                       "f400e421-013f-4a7c-9fd3-28c844bafcc4"
                                     ],
                                     "name": "Processor1"
                                   }
                                 }
                               ]
                             }
                           },
                           {
                             "id": "0cd9cad0-61e4-4fdd-9542-7c136b379ca3",
                             "eClass": "flow:DataSource",
                             "data": {
                               "outgoingFlows": [
                                 {
                                   "id": "f400e421-013f-4a7c-9fd3-28c844bafcc4",
                                   "eClass": "flow:DataFlow",
                                   "data": {
                                     "usage": "standard",
                                     "capacity": 6,
                                     "load": 6,
                                     "target": "6bd1de86-74ce-4538-a2dc-92aabb9d5029"
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
