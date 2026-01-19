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
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.graphql.controllers.GraphQLPayload;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.emfjson.resource.JsonResourceFactoryImpl;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.project.dto.UploadProjectInput;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
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

import reactor.test.StepVerifier;

/**
 * Integration tests of the project upload controllers with documents that cross-reference each other.
 *
 * @author pcdavid
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectUploadWithCrossReferencingModelsControllerIntegrationTests extends AbstractIntegrationTests {

    private static final String PROJECT_NAME = "CrossDocumentsReferences";

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IProjectSearchService projectSearchService;

    @Autowired
    private IProjectSemanticDataSearchService projectSemanticDataSearchService;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project archive with documents referencing each other, when the project is uploaded, then all the documents are available and consistent")
    public void givenProjectArchiveWithDocumentsReferencingEachOtherWhenTheUploadingProjectThenAllDocumentsAreAvailableAndConsistent() {
        byte[] zipByte = this.getZipTestFileABC();
        String response = this.upload(zipByte);

        String newProjectId = JsonPath.read(response, "$.data.uploadProject.project.id");
        assertTrue(this.projectSearchService.existsById(newProjectId));

        var optionalProject = this.projectSearchService.findById(newProjectId);
        assertThat(optionalProject).isPresent();
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(PROJECT_NAME));

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(optionalProject.get().getId()));
        assertThat(optionalProjectSemanticData).isPresent();

        this.validateProjectContents(optionalProjectSemanticData.get().getSemanticData().getId().toString(), this::importedDocumentsAreValid);
    }

    private boolean importedDocumentsAreValid(IEMFEditingContext emfEditingContext) {
        var resourceSet = emfEditingContext.getDomain().getResourceSet();
        var optionalNodeA = this.findNodeDescription(resourceSet, "View A", "NodeA");
        var optionalNodeB = this.findNodeDescription(resourceSet, "View B", "NodeB");
        var optionalNodeC = this.findNodeDescription(resourceSet, "View C", "NodeC");
        if (optionalNodeA.isEmpty() || optionalNodeB.isEmpty() || optionalNodeC.isEmpty()) {
            return false;
        }
        var nodeA = optionalNodeA.get();
        var nodeB = optionalNodeB.get();
        var nodeC = optionalNodeC.get();
        // B reuses A & C, C reuses A & B
        return nodeB.getReusedChildNodeDescriptions().get(0) == nodeA
            && nodeB.getReusedChildNodeDescriptions().get(1) == nodeC
            && nodeC.getReusedChildNodeDescriptions().get(0) == nodeA
            && nodeC.getReusedChildNodeDescriptions().get(1) == nodeB;
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project archive with documents with invalid reference, when the project is uploaded, then only the subset of documents which are self-contained is imported")
    public void givenProjectArchiveWithDocumentsWithInvalidReferencesWhenTheUploadingProjectThenOnlySelfContainedSubsetImported() {
        byte[] zipByte = this.getZipTestFileADE();
        String response = this.upload(zipByte);

        String newProjectId = JsonPath.read(response, "$.data.uploadProject.project.id");
        assertTrue(this.projectSearchService.existsById(newProjectId));

        var optionalProject = this.projectSearchService.findById(newProjectId);
        assertThat(optionalProject).isPresent();
        optionalProject.ifPresent(project -> assertThat(project.getName()).isEqualTo(PROJECT_NAME));

        var optionalProjectSemanticData = this.projectSemanticDataSearchService.findByProjectId(AggregateReference.to(optionalProject.get().getId()));
        assertThat(optionalProjectSemanticData).isPresent();

        this.validateProjectContents(optionalProjectSemanticData.get().getSemanticData().getId().toString(), this::importedDocumentsIncludeOnlySelfContainedSubset);
    }

    /**
     * After uploading "ZipTestFileADE", only View A should have survived: D contains invalid proxies so is dropped; E
     * contained initially valid references to D, but with D gone it must go too.
     */
    private boolean importedDocumentsIncludeOnlySelfContainedSubset(IEMFEditingContext emfEditingContext) {
        var resourceSet = emfEditingContext.getDomain().getResourceSet();
        var optionalNodeA = this.findNodeDescription(resourceSet, "View A", "NodeA");
        var optionalNodeD = this.findNodeDescription(resourceSet, "View D", "NodeD");
        var optionalNodeE = this.findNodeDescription(resourceSet, "View E", "NodeE");
        return !optionalNodeA.isEmpty() &&
                optionalNodeD.isEmpty() &&
                optionalNodeE.isEmpty() &&
                resourceSet.getResources().size() == 2; // Only DocumentA & the studioColorsPalette
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


    private Optional<NodeDescription> findNodeDescription(ResourceSet resourceSet, String documentName, String nodeDescriptionName) {
        return resourceSet.getResources().stream()
                .filter(resource -> this.isDocumentNamed(resource, documentName))
                .flatMap(resource -> Stream.of((View) resource.getContents().get(0)))
                .flatMap(view -> this.findNodeDescription(view, nodeDescriptionName).stream())
                .findFirst();
    }

    private Optional<NodeDescription> findNodeDescription(View view, String name) {
        var iterator = view.eAllContents();
        var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
        return stream.filter(NodeDescription.class::isInstance)
                .map(NodeDescription.class::cast)
                .filter(nodeDescription -> Objects.equals(name, nodeDescription.getId()))
                .findFirst();
    }

    private void validateProjectContents(String editingContextId, Predicate<IEMFEditingContext> projectContentsPredicate) {
        BiFunction<IEditingContext, IInput, IPayload> function = (editingContext, executeEditingContextFunctionInput) -> {
            boolean resourceFound = Optional.of(editingContext)
                    .filter(IEMFEditingContext.class::isInstance)
                    .map(IEMFEditingContext.class::cast)
                    .map(emfEditingContext -> projectContentsPredicate.test(emfEditingContext))
                    .orElse(false);
            return new ExecuteEditingContextFunctionSuccessPayload(executeEditingContextFunctionInput.id(), resourceFound);
        };

        var mono = this.executeEditingContextFunctionRunner.execute(new ExecuteEditingContextFunctionInput(UUID.randomUUID(), editingContextId, function));

        Predicate<IPayload> predicate = payload -> Optional.of(payload)
                .filter(ExecuteEditingContextFunctionSuccessPayload.class::isInstance)
                .map(ExecuteEditingContextFunctionSuccessPayload.class::cast)
                .map(ExecuteEditingContextFunctionSuccessPayload::result)
                .filter(Boolean.class::isInstance)
                .map(Boolean.class::cast)
                .orElse(false);

        StepVerifier.create(mono)
                .expectNextMatches(predicate)
                .thenCancel()
                .verify();
    }

    private boolean isDocumentNamed(Resource resource, String name) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .map(ResourceMetadataAdapter::getName)
                .anyMatch(name::equals);
    }

    private byte[] getZipTestFileABC() {
        byte[] zipByte = null;
        String projectName = PROJECT_NAME;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            // Add Manifest
            ZipEntry zipEntry = new ZipEntry(projectName + "/manifest.json");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.manifestABC().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add DocumentA
            zipEntry = new ZipEntry(projectName + "/documents/255a9f00-be6d-4739-9c09-eb03f6bb6d85." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.documentA().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add DocumentB
            zipEntry = new ZipEntry(projectName + "/documents/58324e8e-98f7-4eb9-825f-a2d277313628." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.documentB().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add DocumentC
            zipEntry = new ZipEntry(projectName + "/documents/55525419-a155-4e68-9932-179e6c670575." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.documentC().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

        } catch (IOException exception) {
            fail(exception.getMessage());
        }

        if (outputStream.size() > 0) {
            zipByte = outputStream.toByteArray();
        }

        return zipByte;
    }

    private byte[] getZipTestFileADE() {
        byte[] zipByte = null;
        String projectName = PROJECT_NAME;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            // Add Manifest
            ZipEntry zipEntry = new ZipEntry(projectName + "/manifest.json");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.manifestADE().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add DocumentA
            zipEntry = new ZipEntry(projectName + "/documents/255a9f00-be6d-4739-9c09-eb03f6bb6d85." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.documentA().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add DocumentD
            zipEntry = new ZipEntry(projectName + "/documents/ac1baab8-593d-4e68-96bc-87eadb084032." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.documentD().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();

            // Add DocumentE
            zipEntry = new ZipEntry(projectName + "/documents/274b8d1e-9b08-4a40-9a1a-b9695f0a6a7c." + JsonResourceFactoryImpl.EXTENSION);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(this.documentE().getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();


        } catch (IOException exception) {
            fail(exception.getMessage());
        }

        if (outputStream.size() > 0) {
            zipByte = outputStream.toByteArray();
        }

        return zipByte;
    }

    // Documents A, B and C contain valid references between them
    private String manifestABC() {
        return """
            {
              "natures": [
                "siriusComponents://nature?kind=studio"
              ],
              "documentIdsToName": {
                "255a9f00-be6d-4739-9c09-eb03f6bb6d85": "View A",
                "58324e8e-98f7-4eb9-825f-a2d277313628": "View B",
                "55525419-a155-4e68-9932-179e6c670575": "View C"
              },
              "metamodels": [
                "http://www.eclipse.org/sirius-web/customcells",
                "http://www.eclipse.org/sirius-web/customnodes",
                "http://www.eclipse.org/sirius-web/deck",
                "http://www.eclipse.org/sirius-web/diagram",
                "http://www.eclipse.org/sirius-web/domain",
                "http://www.eclipse.org/sirius-web/form",
                "http://www.eclipse.org/sirius-web/gantt",
                "http://www.eclipse.org/sirius-web/table",
                "http://www.eclipse.org/sirius-web/tree",
                "http://www.eclipse.org/sirius-web/view",
                "https://www.eclipse.org/sirius/widgets/reference",
                "https://www.eclipse.org/sirius/widgets/tablewidget"
              ],
              "representations": {}
            }
            """;
    }

    /*
     * <ul>
     * <li>Document A is self-contained</li>
     * <li>Document D contains invalid references/unresolvable proxies</li>
     * <li>Document E contains a valid reference to D, but can not "survive" the import as D itself will be refused</li>
     * </ul>
     */
    private String manifestADE() {
        return """
            {
              "natures": [
                "siriusComponents://nature?kind=studio"
              ],
              "documentIdsToName": {
                "255a9f00-be6d-4739-9c09-eb03f6bb6d85": "View A",
                "ac1baab8-593d-4e68-96bc-87eadb084032": "View D",
                "274b8d1e-9b08-4a40-9a1a-b9695f0a6a7c": "View E"
              },
              "metamodels": [
                "http://www.eclipse.org/sirius-web/customcells",
                "http://www.eclipse.org/sirius-web/customnodes",
                "http://www.eclipse.org/sirius-web/deck",
                "http://www.eclipse.org/sirius-web/diagram",
                "http://www.eclipse.org/sirius-web/domain",
                "http://www.eclipse.org/sirius-web/form",
                "http://www.eclipse.org/sirius-web/gantt",
                "http://www.eclipse.org/sirius-web/table",
                "http://www.eclipse.org/sirius-web/tree",
                "http://www.eclipse.org/sirius-web/view",
                "https://www.eclipse.org/sirius/widgets/reference",
                "https://www.eclipse.org/sirius/widgets/tablewidget"
              ],
              "representations": {}
            }
            """;
    }


    // DiagramA define NodeA but is self-contained
    private String documentA() {
        return """
            {
              "json": {
                "version": "1.0",
                "encoding": "utf-8"
              },
              "ns": {
                "diagram": "http://www.eclipse.org/sirius-web/diagram",
                "view": "http://www.eclipse.org/sirius-web/view"
              },
              "content": [
                {
                  "id": "a8745fc8-0c23-4930-93e0-d89e52c466c3",
                  "eClass": "view:View",
                  "data": {
                    "descriptions": [
                      {
                        "id": "89506059-5724-4a49-8c7e-ea34d529ceba",
                        "eClass": "diagram:DiagramDescription",
                        "data": {
                          "name": "DiagramA",
                          "titleExpression": "DiagramA",
                          "nodeDescriptions": [
                            {
                              "id": "59b4b223-201a-4456-996c-27c181e6b6f9",
                              "eClass": "diagram:NodeDescription",
                              "data": {
                                "name": "NodeA"
                              }
                            }
                          ]
                        }
                      }
                    ]
                  }
                }
              ]
            }
            """;
    }

    // DiagramB defines NodeB which references (via reuse) NodeA from DiagramA and NodeC from DiagramC
    private String documentB() {
        return """
            {
              "json": {
                "version": "1.0",
                "encoding": "utf-8"
              },
              "ns": {
                "diagram": "http://www.eclipse.org/sirius-web/diagram",
                "view": "http://www.eclipse.org/sirius-web/view"
              },
              "content": [
                {
                  "id": "fcb0cf11-2ee8-44bb-b6e1-efde25beaea3",
                  "eClass": "view:View",
                  "data": {
                    "descriptions": [
                      {
                        "id": "2d9a44bc-b959-45c5-9beb-74e278c74ec1",
                        "eClass": "diagram:DiagramDescription",
                        "data": {
                          "name": "DiagramB",
                          "titleExpression": "DiagramB",
                          "nodeDescriptions": [
                            {
                              "id": "7088e3e4-4450-45a6-80aa-3723028e8772",
                              "eClass": "diagram:NodeDescription",
                              "data": {
                                "name": "NodeB",
                                "reusedChildNodeDescriptions": [
                                  "diagram:NodeDescription 255a9f00-be6d-4739-9c09-eb03f6bb6d85#59b4b223-201a-4456-996c-27c181e6b6f9",
                                  "diagram:NodeDescription 55525419-a155-4e68-9932-179e6c670575#7158ad28-be24-44b3-8a21-9fd2ee2412fd"
                                ]
                              }
                            }
                          ]
                        }
                      }
                    ]
                  }
                }
              ]
            }
            """;
    }

    // DiagramC defines NodeC which references (via reuse) NodeA from DiagramA and NodeB from DiagramB
    private String documentC() {
        return """
            {
              "json": {
                "version": "1.0",
                "encoding": "utf-8"
              },
              "ns": {
                "diagram": "http://www.eclipse.org/sirius-web/diagram",
                "view": "http://www.eclipse.org/sirius-web/view"
              },
              "content": [
                {
                  "id": "e11e5d93-2597-4044-8573-9aba492133ad",
                  "eClass": "view:View",
                  "data": {
                    "descriptions": [
                      {
                        "id": "c9fd85d8-e22f-46e4-89e7-30fd3cf08610",
                        "eClass": "diagram:DiagramDescription",
                        "data": {
                          "name": "DiagramC",
                          "titleExpression": "DiagramC",
                          "nodeDescriptions": [
                            {
                              "id": "7158ad28-be24-44b3-8a21-9fd2ee2412fd",
                              "eClass": "diagram:NodeDescription",
                              "data": {
                                "name": "NodeC",
                                "reusedChildNodeDescriptions": [
                                  "diagram:NodeDescription 255a9f00-be6d-4739-9c09-eb03f6bb6d85#59b4b223-201a-4456-996c-27c181e6b6f9",
                                  "diagram:NodeDescription 58324e8e-98f7-4eb9-825f-a2d277313628#7088e3e4-4450-45a6-80aa-3723028e8772"
                                ]
                              }
                            }
                          ]
                        }
                      }
                    ]
                  }
                }
              ]
            }
            """;
    }

    // DiagramD define NodeD which with two unresolvable proxy references:
    // - one towards an invalid element from a valid document (DocumentA)
    // - one towards an invalid element from a invalid document
    private String documentD() {
        return """
            {
              "json": {
                "version": "1.0",
                "encoding": "utf-8"
              },
              "ns": {
                "diagram": "http://www.eclipse.org/sirius-web/diagram",
                "view": "http://www.eclipse.org/sirius-web/view"
              },
              "content": [
                {
                  "id": "39655347-cb58-4fe0-bdd7-bf5a1f1475ff",
                  "eClass": "view:View",
                  "data": {
                    "descriptions": [
                      {
                        "id": "13ee7806-46d8-4069-819e-7296deb24fe3",
                        "eClass": "diagram:DiagramDescription",
                        "data": {
                          "name": "DiagramD",
                          "titleExpression": "DiagramD",
                          "nodeDescriptions": [
                            {
                              "id": "e8e1e2f2-b0c6-499f-be0c-ad2a4aa03865",
                              "eClass": "diagram:NodeDescription",
                              "data": {
                                "name": "NodeD",
                                "reusedChildNodeDescriptions": [
                                  "diagram:NodeDescription 255a9f00-be6d-4739-9c09-eb03f6bb6d85#23fc82dc-e409-4299-a0ab-1629a2a8e664",
                                  "diagram:NodeDescription c3ec4210-cb1f-4c1d-93c4-b009179371c0#78e9c27d-96c6-43d3-a4f7-dfa83acbf0a5"
                                ]
                              }
                            }
                          ]
                        }
                      }
                    ]
                  }
                }
              ]
            }
            """;
    }

    // DiagramE define NodeE which contains a valid reference to NodeD (which however, is itself invalid)
    private String documentE() {
        return """
            {
              "json": {
                "version": "1.0",
                "encoding": "utf-8"
              },
              "ns": {
                "diagram": "http://www.eclipse.org/sirius-web/diagram",
                "view": "http://www.eclipse.org/sirius-web/view"
              },
              "content": [
                {
                  "id": "db745268-d5c1-467b-b1f0-1c74d60288bc",
                  "eClass": "view:View",
                  "data": {
                    "descriptions": [
                      {
                        "id": "3b42216f-2ef7-4d41-8f5b-d26fc4cfd123",
                        "eClass": "diagram:DiagramDescription",
                        "data": {
                          "name": "DiagramE",
                          "titleExpression": "DiagramE",
                          "nodeDescriptions": [
                            {
                              "id": "cb006c2d-9ed6-439e-a049-180b048d58c6",
                              "eClass": "diagram:NodeDescription",
                              "data": {
                                "name": "NodeE",
                                "reusedChildNodeDescriptions": [
                                  "diagram:NodeDescription ac1baab8-593d-4e68-96bc-87eadb084032#e8e1e2f2-b0c6-499f-be0c-ad2a4aa03865"
                                ]
                              }
                            }
                          ]
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
