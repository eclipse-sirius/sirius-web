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
package org.eclipse.sirius.web.services.migration;

import static org.assertj.core.api.Assertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.migration.api.MigrationData;
import org.eclipse.sirius.components.graphql.api.UploadFile;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentInput;
import org.eclipse.sirius.web.application.document.dto.UploadDocumentSuccessPayload;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.tests.graphql.UploadDocumentMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

/**
 * Integration tests of DiagramLabelStyleBorderSizeMigrationParticipant.
 *
 * @author frouene
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DiagramLabelStyleBorderSizeMigrationParticipantTests extends AbstractIntegrationTests {

    public static final String DOCUMENT_CONTENT = """
            {
              "json": { "version": "1.0", "encoding": "utf-8" },
              "ns": {
                "diagram": "http://www.eclipse.org/sirius-web/diagram",
                "view": "http://www.eclipse.org/sirius-web/view"
              },
              "content": [
                {
                  "id": "9674f8f7-ff1a-4061-bb32-a4a235a9c2ca",
                  "eClass": "view:View",
                  "data": {
                    "descriptions": [
                     {
                       "id": "c1a0ae1c-8420-4aa7-b443-1a05044076e2",
                       "eClass": "diagram:DiagramDescription",
                       "data": {
                         "name": "DiagramLabelStyle#borderSize migration",
                         "domainType": "flow::System",
                         "edgeDescriptions": [
                           {
                             "data": {
                               "centerLabelExpression": "",
                               "name": "LabelBorderSize Edge",
                               "semanticCandidatesExpression": "",
                               "sourceNodeDescriptions": [
                                 "//@descriptions.0/@nodeDescriptions.0"
                               ],
                               "sourceNodesExpression": "aql:self",
                               "style": {
                                 "data": {
                                   "color": "//@colorPalettes.0/@colors.0"
                                 },
                                 "eClass": "diagram:EdgeStyle",
                                 "id": "748824cb-cd5f-42fe-95a8-4f21172ba2ce"
                               },
                               "targetNodeDescriptions": [
                                 "//@descriptions.0/@nodeDescriptions.1"
                               ],
                               "targetNodesExpression": "aql:self.linkedTo"
                             },
                             "eClass": "diagram:EdgeDescription",
                             "id": "c1077629-a4ec-4232-8eb5-5209b58c7464"
                           }
                         ],
                         "nodeDescriptions": [
                           {
                             "id": "4d1ec4d1-dd2c-4cb2-bbed-df02f0babbd8",
                             "eClass": "diagram:NodeDescription",
                             "data": {
                               "name": "LabelBorderSize migration",
                               "domainType": "flow::CompositeProcessor",
                               "insideLabel": {
                                 "data": {
                                   "style": {
                                     "eClass": "diagram:InsideLabelStyle",
                                     "id": "d70178c4-d36f-40d7-a3ab-ef8568e4ee2b"
                                   }
                                 },
                                 "eClass": "diagram:InsideLabelDescription",
                                 "id": "046b48ee-4554-4d8a-a457-5391b690d2a9"
                               },
                               "outsideLabels": [
                                 {
                                   "data": {
                                     "style": {
                                       "eClass": "diagram:OutsideLabelStyle",
                                       "id": "c69b0853-26ab-40b0-979d-f0a4974ad865"
                                     }
                                   },
                                   "eClass": "diagram:OutsideLabelDescription",
                                   "id": "fd8638cc-8ebb-48e6-bf3f-2c09206f6a09"
                                 },
                                 {
                                   "data": {
                                     "style": {
                                       "eClass": "diagram:OutsideLabelStyle",
                                       "id": "601e5a1d-3d8a-49bc-96b9-f0c47ee594ee"
                                     }
                                   },
                                   "eClass": "diagram:OutsideLabelDescription",
                                   "id": "a8a2bf4c-82b7-4050-b4e7-09fd26a2f7c0"
                                 }
                               ],
                               "childrenLayoutStrategy": {
                                 "id": "558f8558-bee6-4ae9-8dc7-4344efe2b83e",
                                 "eClass": "diagram:FreeFormLayoutStrategyDescription"
                               },
                               "style": {
                                 "data": {
                                    "background": "//@colorPalettes.0/@colors.0",
                                    "borderColor": "//@colorPalettes.0/@colors.0"
                                 },
                                 "eClass": "diagram:RectangularNodeStyleDescription",
                                 "id": "dacddb12-7c2c-4463-bf71-a7c3e68e2132"
                               }
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
    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private ExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Autowired
    private UploadDocumentMutationRunner uploadDocumentMutationRunner;

    @Autowired
    private List<IMigrationParticipant> migrationParticipants;

    @Test
    @DisplayName("Given a project with an old model, DiagramLabelStyleBorderSizeMigrationParticipant migrates the model correctly")
    @Sql(scripts = { "/scripts/migration.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnOldModelMigrationParticipantCanBeContributedToUpdateTheModel() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_DIAGRAM_LABEL_STYLE_BORDER_SIZE_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }

    @Test
    @DisplayName("Given an uploaded project with an old model, DiagramLabelStyleBorderSizeMigrationParticipant migrates the model correctly")
    @Sql(scripts = { "/scripts/migration.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnOldViewDiagramMigrationServiceIsExecutedProperly() {
        this.uploadDocument(MigrationIdentifiers.MIGRATION_DIAGRAM_LABEL_STYLE_BORDER_SIZE_STUDIO.toString(), "test_upload", DOCUMENT_CONTENT);
    }

    private void uploadDocument(String editingContextId, String name, String content) {
        this.givenCommittedTransaction.commit();

        var file = new UploadFile(name, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
        var input = new UploadDocumentInput(UUID.randomUUID(), editingContextId, file);
        var result = this.uploadDocumentMutationRunner.run(input);

        TestTransaction.flagForCommit();
        TestTransaction.end();

        String typename = JsonPath.read(result, "$.data.uploadDocument.__typename");
        assertThat(typename).isEqualTo(UploadDocumentSuccessPayload.class.getSimpleName());

        Predicate<IPayload> predicate = payload -> Optional.of(payload)
                .filter(ExecuteEditingContextFunctionSuccessPayload.class::isInstance)
                .map(ExecuteEditingContextFunctionSuccessPayload.class::cast)
                .map(ExecuteEditingContextFunctionSuccessPayload::result)
                .filter(Boolean.class::isInstance)
                .map(Boolean.class::cast)
                .orElse(false);

        var optionalLastMigrationData = this.migrationParticipants.stream()
                .sorted(Comparator.comparing(IMigrationParticipant::getVersion).reversed())
                .map(migrationParticipant -> new MigrationData(migrationParticipant.getClass().getSimpleName(), migrationParticipant.getVersion()))
                .findFirst();
        assertThat(optionalLastMigrationData).isPresent();
        var lastMigrationData = optionalLastMigrationData.get();

        BiFunction<IEditingContext, IInput, IPayload> function = (editingContext, executeEditingContextFunctionInput) -> {
            var isMigrated = Optional.of(editingContext)
                    .filter(EditingContext.class::isInstance)
                    .map(EditingContext.class::cast)
                    .map(siriusWebEditingContext -> siriusWebEditingContext.getViews().stream()
                            .anyMatch(view -> view.eResource().eAdapters().stream()
                                    .filter(ResourceMetadataAdapter.class::isInstance)
                                    .map(ResourceMetadataAdapter.class::cast)
                                    .filter(resourceMetadataAdapter -> resourceMetadataAdapter.getMigrationData() != null)
                                    .anyMatch(resourceMetadataAdapter -> resourceMetadataAdapter.getMigrationData().migrationVersion().equals(lastMigrationData.migrationVersion())
                                            && resourceMetadataAdapter.getMigrationData().lastMigrationPerformed().equals(lastMigrationData.lastMigrationPerformed()))
                            ))
                    .orElse(false);
            return new ExecuteEditingContextFunctionSuccessPayload(executeEditingContextFunctionInput.id(), isMigrated);
        };

        var mono = this.executeEditingContextFunctionRunner.execute(new ExecuteEditingContextFunctionInput(UUID.randomUUID(), editingContextId, function));
        StepVerifier.create(mono)
                .expectNextMatches(predicate)
                .thenCancel()
                .verify();
    }

    private void testIsMigrationSuccessful(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalDiagramDescription = siriusWebEditingContext.getViews().stream().flatMap(view -> view.getDescriptions().stream())
                    .filter(representationDescription -> representationDescription.getName().equals(MigrationIdentifiers.MIGRATION_DIAGRAM_LABEL_STYLE_BORDER_SIZE_STUDIO_DIAGRAM)).findFirst();
            assertThat(optionalDiagramDescription).isPresent();
            assertThat(optionalDiagramDescription.get()).isInstanceOf(DiagramDescription.class);
            optionalDiagramDescription.ifPresent(representationDescription -> {
                if (representationDescription instanceof DiagramDescription diagramDescription) {
                    assertThat(diagramDescription.getNodeDescriptions()).hasSize(1);
                    diagramDescription.getNodeDescriptions().forEach(nodeDescription -> {
                        assertThat(nodeDescription.getInsideLabel().getStyle().getBorderSize()).isEqualTo(0);
                        assertThat(nodeDescription.getOutsideLabels()).hasSize(2);
                        assertThat(nodeDescription.getOutsideLabels()).allMatch(outsideLabelDescription -> outsideLabelDescription.getStyle().getBorderSize() == 0);
                    });
                    assertThat(diagramDescription.getEdgeDescriptions()).hasSize(1);
                    diagramDescription.getEdgeDescriptions().forEach(edgeDescription -> {
                        assertThat(edgeDescription.getStyle().getBorderSize()).isEqualTo(0);
                    });
                }
            });
        }
    }

}
