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
import static org.assertj.core.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests of the project download controllers.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectDownloadControllerIntegrationTests extends AbstractIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @DisplayName("Given a studio, when the download of the project is requested, then the manifest is exported")
    @Sql(scripts = { "/scripts/studio.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenTheDownloadOfProjectIsRequestedThenTheManifestIsExported() {
        this.givenCommittedTransaction.commit();

        var response = this.download(StudioIdentifiers.SAMPLE_STUDIO_PROJECT);

        try (ZipInputStream inputStream = new ZipInputStream(response.getBody().getInputStream())) {
            HashMap<String, ByteArrayOutputStream> zipEntries = this.toZipEntries(inputStream);
            assertThat(zipEntries).isNotEmpty().containsKey("Studio/manifest.json");

            String manifestContentExpected = """
                {
                  "natures":[
                    "siriusComponents://nature?kind=studio"
                  ],
                  "documentIdsToName":{
                    "356e45e8-7d70-439e-b2dd-d0313cd65174":"Ellipse Diagram View",
                    "f0e490c1-79f1-49a0-b1f2-3637f2958148":"Domain",
                    "ed2a5355-991d-458f-87f1-ea3a18b1f104":"Form View",
                    "fc1d7b23-2818-4874-bb30-8831ea287a44":"Diagram View"
                  },
                  "metamodels":[
                    "domain://buck",
                    "http://www.eclipse.org/emf/2002/Ecore",
                    "http://www.eclipse.org/sirius-web/customnodes",
                    "http://www.eclipse.org/sirius-web/deck",
                    "http://www.eclipse.org/sirius-web/diagram",
                    "http://www.eclipse.org/sirius-web/domain",
                    "http://www.eclipse.org/sirius-web/form",
                    "http://www.eclipse.org/sirius-web/gantt",
                    "http://www.eclipse.org/sirius-web/view",
                    "https://www.eclipse.org/sirius/widgets/reference"
                  ],
                  "representations":{}
                }
                """;

            String manifestContent = new String(zipEntries.get("Studio/manifest.json").toByteArray(), StandardCharsets.UTF_8);
            var objectMapper = new ObjectMapper();
            assertThat(objectMapper.readTree(manifestContentExpected)).isEqualTo(objectMapper.readTree(manifestContent));
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    @DisplayName("Given a studio, when the download of the project is requested, then the semantic data are retrieved")
    @Sql(scripts = { "/scripts/studio.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenStudioWhenTheDownloadOfProjectIsRequestedThenTheSemanticDataAreRetrieved() {
        this.givenCommittedTransaction.commit();

        var response = this.download(StudioIdentifiers.SAMPLE_STUDIO_PROJECT);

        try (var inputStream = new ZipInputStream(response.getBody().getInputStream())) {
            HashMap<String, ByteArrayOutputStream> zipEntries = this.toZipEntries(inputStream);
            String domainDocumentPath = "Studio/documents/" + StudioIdentifiers.DOMAIN_DOCUMENT.toString() + ".json";
            String viewDocumentPath = "Studio/documents/" + StudioIdentifiers.VIEW_DOCUMENT.toString() + ".json";
            assertThat(zipEntries).isNotEmpty().containsKey(domainDocumentPath).containsKey(viewDocumentPath);

            var objectMapper = new ObjectMapper();

            String semanticDataContentExpected = this.getExpectedStudioDomainDocumentData();
            String semanticDataContent = new String(zipEntries.get(domainDocumentPath).toByteArray(), StandardCharsets.UTF_8);
            assertThat(objectMapper.readTree(semanticDataContentExpected)).isEqualTo(objectMapper.readTree(semanticDataContent));

            semanticDataContentExpected = this.getExpectedStudioViewDocumentData();
            semanticDataContent = new String(zipEntries.get(viewDocumentPath).toByteArray(), StandardCharsets.UTF_8);
            assertThat(objectMapper.readTree(semanticDataContentExpected)).isEqualTo(objectMapper.readTree(semanticDataContent));
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    @Test
    @DisplayName("Given a project, when the download of the project is requested, then the representation data are retrieved")
    @Sql(scripts = { "/scripts/initialize.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenProjectWhenTheDownloadOfProjectIsRequestedThenTheRepresentationDataAreRetrieved() {
        this.givenCommittedTransaction.commit();

        var response = this.download(TestIdentifiers.ECORE_SAMPLE_PROJECT);

        try (var inputStream = new ZipInputStream(response.getBody().getInputStream())) {
            var zipEntries = this.toZipEntries(inputStream);
            String representationPath = "Ecore Sample/representations/" + TestIdentifiers.EPACKAGE_PORTAL_REPRESENTATION.toString() + ".json";
            assertThat(zipEntries).isNotEmpty().containsKey(representationPath);

            String representationContentExpected = this.getExpectedRepresentation();
            String representationContent = new String(zipEntries.get(representationPath).toByteArray(), StandardCharsets.UTF_8);

            var objectMapper = new ObjectMapper();
            assertThat(objectMapper.readTree(representationContentExpected)).isEqualTo(objectMapper.readTree(representationContent));
        } catch (IOException exception) {
            fail(exception.getMessage());
        }
    }

    private ResponseEntity<Resource> download(UUID projectId) {
        var uri = "http://localhost:" + this.port + "/api/projects/" + projectId.toString();

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

    private String getExpectedStudioDomainDocumentData() {
        return """
                {
                  "json":{
                    "version":"1.0",
                    "encoding":"utf-8"
                  },
                  "ns":{
                    "domain":"http://www.eclipse.org/sirius-web/domain"
                  },
                  "content":[
                    $CONTENT$
                  ]
                }
            """.replace("$CONTENT$", this.getExpectedStudioDomainDocumentDataContent());
    }

    private String getExpectedStudioDomainDocumentDataContent() {
        return """
            {
              "id":"f8204cb6-3705-48a5-bee3-ad7e7d6cbdaf",
              "eClass":"domain:Domain",
              "data":{
                "name":"buck",
                "types":[
                  {
                    "id":"c341bf91-d315-4264-9787-c51b121a6375",
                    "eClass":"domain:Entity",
                    "data":{
                      "name":"Root",
                      "attributes":[
                        {
                          "id":"7ac92c9d-3cb6-4374-9774-11bb62962fe2",
                          "eClass":"domain:Attribute",
                          "data":{
                            "name":"label"
                          }
                        }
                      ],
                      "relations":[
                        {
                          "id":"f8fefc5d-4fee-4666-815e-94b24a95183f",
                          "eClass":"domain:Relation",
                          "data":{
                            "name":"humans",
                            "many":true,
                            "containment":true,
                            "targetType":"//@types.2"
                          }
                        }
                      ]
                    }
                  },
                  {
                    "id":"c6fdba07-dea5-4a53-99c7-7eefc1bfdfcc",
                    "eClass":"domain:Entity",
                    "data":{
                      "name":"NamedElement",
                      "attributes":[
                        {
                          "id":"520bb7c9-5f28-40f7-bda0-b35dd593876d",
                          "eClass":"domain:Attribute",
                          "data":{
                            "name":"name"
                          }
                        }
                      ],
                      "abstract":true
                    }
                  },
                  {
                    "id":"1731ffb5-bfb0-46f3-a23d-0c0650300005",
                    "eClass":"domain:Entity",
                    "data":{
                      "name":"Human",
                      "attributes":[
                        {
                          "id":"e86d3f45-d043-441e-b8ab-12e4b3f8915a",
                          "eClass":"domain:Attribute",
                          "data":{
                            "name":"description"
                          }
                        },
                        {
                          "id":"703e6db4-a193-4da7-a872-6efba2b3a2c5",
                          "eClass":"domain:Attribute",
                          "data":{
                            "name":"promoted",
                            "type":"BOOLEAN"
                          }
                        },
                        {
                          "id":"a480dbc0-14b7-4f06-a4f7-4c86139a803a",
                          "eClass":"domain:Attribute",
                          "data":{
                            "name":"birthDate"
                          }
                        }
                      ],
                      "superTypes":[
                        "//@types.1"
                      ]
                    }
                  }
                ]
              }
            }
            """;
    }

    private String getExpectedStudioViewDocumentData() {
        return """
                {
                  "json":{
                    "version":"1.0",
                    "encoding":"utf-8"
                  },
                  "ns":{
                    "form":"http://www.eclipse.org/sirius-web/form",
                    "view":"http://www.eclipse.org/sirius-web/view"
                  },
                  "content":[
                    $CONTENT$
                  ]
                }
            """.replace("$CONTENT$", this.getExpectedStudioViewDocumentDataContent());
    }

    private String getExpectedStudioViewDocumentDataContent() {
        return """
            {
              "id":"c4591605-8ea8-4e92-bb17-05c4538248f8",
              "eClass":"view:View",
              "data":{
                "descriptions":[
                  {
                    "id":"ed20cb85-a58a-47ad-bc0d-749ec8b2ea03",
                    "eClass":"form:FormDescription",
                    "data":{
                      "name":"Human Form",
                      "domainType":"buck::Human",
                      "pages":[
                        {
                          "id":"b0c73654-6f1b-4be5-832d-b97f053b5196",
                          "eClass":"form:PageDescription",
                          "data":{
                            "name":"Human",
                            "labelExpression":"aql:self.name",
                            "domainType":"buck::Human",
                            "groups": $GROUP$
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            }
            """.replace("$GROUP$", this.getGroups());
    }

    private String getGroups() {
        return """
            [
              {
                "id":"28d8d6de-7d6f-4434-9293-0ac4ef2461ac",
                "eClass":"form:GroupDescription",
                "data":{
                  "name":"Properties",
                  "labelExpression":"Properties",
                  "children":[
                    $WIDGETS$
                  ]
                }
              }
            ]
            """.replace("$WIDGETS$", this.getWidgets());
    }

    private String getWidgets() {
        return """
            {
              "id":"b02b89b7-6c06-40f8-9366-83d5f885ada1",
              "eClass":"form:TextfieldDescription",
              "data":{
                "name":"Name",
                "labelExpression":"Name",
                "helpExpression":"The name of the human",
                "valueExpression":"aql:self.name",
                "body":[
                  {
                    "id":"ecdc23ff-fd4b-47a4-939d-1bc03e656d3d",
                    "eClass":"view:ChangeContext",
                    "data":{
                      "children":[
                        {
                          "id":"a8b95d5b-833a-4b19-b783-3025225613de",
                          "eClass":"view:SetValue",
                          "data":{
                            "featureName":"name",
                            "valueExpression":"aql:newValue"
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            },
            {
              "id":"98e756a2-305f-4767-b75c-4130996ae6da",
              "eClass":"form:TextAreaDescription",
              "data":{
                "name":"Description",
                "labelExpression":"Description",
                "helpExpression":"The description of the human",
                "valueExpression":"aql:self.description",
                "body":[
                  {
                    "id":"59ea57d5-c365-4421-9648-f38a74644768",
                    "eClass":"view:ChangeContext",
                    "data":{
                      "children":[
                        {
                          "id":"811bb719-ab53-49ea-9281-6558f7022ecc",
                          "eClass":"view:SetValue",
                          "data":{
                            "featureName":"description",
                            "valueExpression":"aql:newValue"
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            },
            {
              "id":"ba20babb-0e75-4f66-a382-a2f02bce904a",
              "eClass":"form:CheckboxDescription",
              "data":{
                "name":"Promoted",
                "labelExpression":"Promoted",
                "helpExpression":"Has this human been promoted?",
                "valueExpression":"aql:self.promoted",
                "body":[
                  {
                    "id":"afac13bd-71ac-4287-baf6-3669f23ac806",
                    "eClass":"view:ChangeContext",
                    "data":{
                      "children":[
                        {
                          "id":"0eaeca64-ee2b-4f2c-9454-c528181d0d64",
                          "eClass":"view:SetValue",
                          "data":{
                            "featureName":"promoted",
                            "valueExpression":"aql:newValue"
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            },
            {
              "id":"91a4fcd9-a176-4df1-8f88-52a406fc3f73",
              "eClass":"form:DateTimeDescription",
              "data":{
                "name":"BirthDate",
                "labelExpression":"Birth Date",
                "helpExpression":"The birth date of the human",
                "stringValueExpression":"aql:self.birthDate",
                "type":"DATE"
              }
            }
            """;
    }

    private String getExpectedRepresentation() {
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
}
