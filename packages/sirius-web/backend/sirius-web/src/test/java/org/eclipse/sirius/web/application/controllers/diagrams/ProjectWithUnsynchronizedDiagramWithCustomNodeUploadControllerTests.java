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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.graphql.controllers.GraphQLPayload;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.diagram.EllipseNodeStyle;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
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
 * Integration tests of the project upload controllers with an unsynchronized diagram and a custom node.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class ProjectWithUnsynchronizedDiagramWithCustomNodeUploadControllerTests extends AbstractIntegrationTests {

    private static final String PAPAYA_SAMPLE = "PapayaSample";

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
                return PAPAYA_SAMPLE + ".zip";
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
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(PAPAYA_SAMPLE));

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(optionalProject.get().getId()));
        assertThat(optionalProjectSemanticData).isPresent();
        var semanticDataId = optionalProjectSemanticData.get().getSemanticData().getId();

        var representationMetaDatas = this.representationMetadataSearchService.findAllRepresentationMetadataBySemanticData(AggregateReference.to(semanticDataId));
        assertThat(representationMetaDatas).hasSize(1);
        assertThat(representationMetaDatas.get(0)).extracting("label").isEqualTo("Component component diagram");

        var editingContext = new IEditingContext() {
            @Override
            public String getId() {
                return semanticDataId.toString();
            }
        };
        var optionalDiagram = this.representationSearchService.findById(editingContext, representationMetaDatas.get(0).getId().toString(), Diagram.class);
        assertThat(optionalDiagram).isPresent();

        var diagram = optionalDiagram.get();
        assertThat(diagram.getNodes().size()).isEqualTo(1);

        var diagramNavigator = new DiagramNavigator(diagram);

        var componentNode = diagramNavigator.nodeWithLabel("Component").getNode();
        assertThat(componentNode).hasState(ViewModifier.Faded);
        assertThat(componentNode.getChildNodes()).hasSize(0);
        assertThat(componentNode.getCustomizedStyleProperties()).hasSize(4);
        assertThat(componentNode.getCustomizedStyleProperties()).containsExactlyInAnyOrderElementsOf(List.of("BACKGROUND", "BORDER_COLOR", "BORDER_SIZE", "BORDER_STYLE"));
        assertThat(componentNode.getStyle()).isInstanceOf(EllipseNodeStyle.class);

        EllipseNodeStyle componentStyle = (EllipseNodeStyle) componentNode.getStyle();
        assertThat(componentStyle.getBackground()).isEqualTo("#F0F030");
        assertThat(componentStyle.getBorderColor()).isEqualTo("red");
        assertThat(componentStyle.getBorderSize()).isEqualTo(3);
        assertThat(componentStyle.getBorderStyle()).isEqualTo(LineStyle.Dot);

        assertThat(componentNode.getInsideLabel()).isNotNull();
        assertThat(componentNode.getInsideLabel().getCustomizedStyleProperties()).hasSize(11);
        assertThat(componentNode.getInsideLabel().getCustomizedStyleProperties()).containsExactlyInAnyOrderElementsOf(List.of("BOLD", "ITALIC", "UNDERLINE", "STRIKE_THROUGH", "COLOR",
                "FONT_SIZE", "BACKGROUND", "BORDER_COLOR", "BORDER_SIZE", "BORDER_RADIUS", "BORDER_STYLE"));
        var insideLabelStyle = componentNode.getInsideLabel().getStyle();
        assertThat(insideLabelStyle.isBold()).isFalse();
        assertThat(insideLabelStyle.getColor()).isEqualTo("#002B40");
        assertThat(insideLabelStyle.getFontSize()).isEqualTo(15);
        assertThat(insideLabelStyle.isItalic()).isTrue();
        assertThat(insideLabelStyle.isUnderline()).isTrue();
        assertThat(insideLabelStyle.isStrikeThrough()).isTrue();
        assertThat(insideLabelStyle.getBackground()).isEqualTo("red");
        assertThat(insideLabelStyle.getBorderColor()).isEqualTo("green");
        assertThat(insideLabelStyle.getBorderSize()).isEqualTo(1);
        assertThat(insideLabelStyle.getBorderRadius()).isEqualTo(0);
        assertThat(insideLabelStyle.getBorderStyle()).isEqualTo(LineStyle.Dash);
    }

    private byte[] getZipTestFile() {
        byte[] zipByte = null;
        String projectName = PAPAYA_SAMPLE;
        String representationId = "0df91f30-98f0-437b-909e-00d06eec5cac";
        String documentId = "0ccca4a4-7869-45df-b4f7-6c71a0c8caf6";

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
                  "natures": ["siriusComponents://nature?kind=papaya"],
                  "documentIdsToName": { "0ccca4a4-7869-45df-b4f7-6c71a0c8caf6": "Papaya" },
                  "metamodels": [
                    "https://www.eclipse.org/sirius-web/papaya"
                  ],
                  "representations": {
                    "0df91f30-98f0-437b-909e-00d06eec5cac": {
                      "type": "siriusComponents://representation?type=Diagram",
                      "targetObjectURI": "sirius:///0ccca4a4-7869-45df-b4f7-6c71a0c8caf6#1c95b0aa-3c37-4f29-9815-b0b46c4588af",
                      "descriptionURI": "%s"
                    }
                  }
                }
                """.formatted(this.customNodesDiagramDescriptionProvider.getRepresentationDescriptionId());
    }

    private String representation() {
        return """
                {
                  "id": "0df91f30-98f0-437b-909e-00d06eec5cac",
                  "projectId": "23937d6b-f9e0-48e7-a29d-5c3010c22443",
                  "descriptionId": "%s",
                  "targetObjectId": "1c95b0aa-3c37-4f29-9815-b0b46c4588af",
                  "label": "Component component diagram",
                  "kind": "siriusComponents://representation?type=Diagram",
                  "representation": {
                    "id": "0df91f30-98f0-437b-909e-00d06eec5cac",
                    "kind": "siriusComponents://representation?type=Diagram",
                    "targetObjectId": "1c95b0aa-3c37-4f29-9815-b0b46c4588af",
                    "descriptionId": "%s",
                    "nodes": [
                      {
                        "id": "6267c929-b7a9-3798-aa68-7521087b56ec",
                        "type": "customnode:ellipse",
                        "targetObjectId": "157d71bd-5867-44e4-8899-56bbcc06ecef",
                        "targetObjectKind": "siriusComponents://semantic?domain=papaya&entity=Component",
                        "targetObjectLabel": "Component",
                        "descriptionId": "%s",
                        "borderNode": false,
                        "modifiers": ["Faded"],
                        "state": "Faded",
                        "collapsingState": "EXPANDED",
                        "insideLabel": {
                          "id": "670c4a91-ea89-353f-8c54-913b6420dc49",
                          "text": "Component",
                          "insideLabelLocation": "TOP_CENTER",
                          "style": {
                            "color": "#002B40",
                            "fontSize": 15,
                            "bold": false,
                            "italic": true,
                            "underline": true,
                            "strikeThrough": true,
                            "iconURL": [],
                            "background": "red",
                            "borderColor": "green",
                            "borderSize": 1,
                            "borderRadius": 0,
                            "borderStyle": "Dash",
                            "maxWidth": null
                          },
                          "isHeader": true,
                          "headerSeparatorDisplayMode": "NEVER",
                          "overflowStrategy": "NONE",
                          "textAlign": "LEFT",
                          "customizedStyleProperties": [
                            "BOLD",
                            "ITALIC",
                            "UNDERLINE",
                            "STRIKE_THROUGH",
                            "COLOR",
                            "FONT_SIZE",
                            "BACKGROUND",
                            "BORDER_COLOR",
                            "BORDER_SIZE",
                            "BORDER_RADIUS",
                            "BORDER_STYLE"
                          ]
                        },
                        "outsideLabels": [],
                        "style": {
                          "background": "#F0F030",
                          "borderColor": "red",
                          "borderSize": 3,
                          "borderStyle": "Dot"
                        },
                        "childrenLayoutStrategy": { "kind": "FreeForm" },
                        "borderNodes": [],
                        "childNodes": [],
                        "defaultWidth": 150,
                        "defaultHeight": 70,
                        "labelEditable": true,
                        "pinned": false,
                        "customizedStyleProperties": [
                          "BACKGROUND",
                          "BORDER_COLOR",
                          "BORDER_SIZE",
                          "BORDER_STYLE"
                        ]
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
                """.formatted(this.customNodesDiagramDescriptionProvider.getRepresentationDescriptionId(), this.customNodesDiagramDescriptionProvider.getRepresentationDescriptionId(), this.customNodesDiagramDescriptionProvider.getCustomNodeDescriptionId());
    }

    private String document() {
        return """
                {
                   "json": { "version": "1.0", "encoding": "utf-8" },
                   "ns": { "papaya": "https://www.eclipse.org/sirius-web/papaya" },
                   "content": [
                     {
                       "id": "1c95b0aa-3c37-4f29-9815-b0b46c4588af",
                       "eClass": "papaya:Project",
                       "data": {
                         "name": "Project",
                         "elements": [
                           {
                             "id": "157d71bd-5867-44e4-8899-56bbcc06ecef",
                             "eClass": "papaya:Component",
                             "data": { "name": "Component" }
                           }
                         ]
                       }
                     }
                   ]
                 }
                """;
    }
}
