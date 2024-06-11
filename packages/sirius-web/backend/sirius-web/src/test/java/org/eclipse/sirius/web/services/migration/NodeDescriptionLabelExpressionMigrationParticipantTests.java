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
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
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
 * Integration tests of NodeDescriptionLabelExpressionMigrationParticipant.
 *
 * @author mcharfadi
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NodeDescriptionLabelExpressionMigrationParticipantTests extends AbstractIntegrationTests {

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
    @DisplayName("Given a project with an old model, NodeDescriptionLabelExpressionMigrationParticipant migrates the model correctly")
    @Sql(scripts = { "/scripts/migration.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnOldModelMigrationParticipantCanBeContributedToUpdateTheModel() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }

    @Test
    @DisplayName("Given an uploaded project with an old model, NodeDescriptionLabelExpressionMigrationParticipant migrates the model correctly")
    @Sql(scripts = { "/scripts/migration.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnOldViewDiagramMigrationServiceIsExecutedProperly() {
        var content = """
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
                            "id": "22fb1f4d-109d-4e73-bff0-f7cd96fb5fbb",
                            "eClass": "diagram:DiagramDescription",
                            "data": {
                              "name": "NodeDescription#labelExpression migration_upload",
                              "domainType": "flow::System",
                              "nodeDescriptions": [
                                {
                                  "id": "6949ddfe-f480-473b-bc8a-6f2bdde07e4d",
                                  "eClass": "diagram:NodeDescription",
                                  "data": {
                                    "name": "NodeWithoutImage migration",
                                    "domainType": "flow::CompositeProcessor",
                                    "labelExpression": "aql:''NodeWithoutImage''",
                                    "childrenLayoutStrategy": {
                                      "id": "20651d93-2ee5-41cb-b2bd-1e75958c73cf",
                                      "eClass": "diagram:FreeFormLayoutStrategyDescription"
                                    },
                                    "style": {
                                      "id": "92fe9d3f-2c5b-41ab-81ea-8482c8cd57b9",
                                      "eClass": "diagram:RectangularNodeStyleDescription"
                                    }
                                  }
                                },
                                {
                                  "id": "88f95390-ac15-4381-ae82-7b23a2017bd4",
                                  "eClass": "diagram:NodeDescription",
                                  "data": {
                                    "name": "NodeWithImage migration",
                                    "domainType": "flow::CompositeProcessor",
                                    "labelExpression": "aql:''NodeWithImage''",
                                    "childrenLayoutStrategy": {
                                      "id": "db92e810-5d51-4d0c-a6cf-6a1e00cea6d9",
                                      "eClass": "diagram:FreeFormLayoutStrategyDescription"
                                    },
                                    "style": {
                                      "id": "fa35806a-bdd0-4ba7-81bf-8cfba8f8fef5",
                                      "eClass": "diagram:ImageNodeStyleDescription"
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
        this.uploadDocument(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO.toString(), "test_upload", content);
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
                    .filter(representationDescription -> representationDescription.getName().equals(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO_DIAGRAM)).findFirst();
            assertThat(optionalDiagramDescription).isPresent();
            assertThat(optionalDiagramDescription.get()).isInstanceOf(DiagramDescription.class);
            optionalDiagramDescription.ifPresent(representationDescription -> {
                if (representationDescription instanceof DiagramDescription diagramDescription) {
                    assertThat(diagramDescription.getNodeDescriptions()).hasSize(2);
                    diagramDescription.getNodeDescriptions().forEach(nodeDescription -> {
                        if (nodeDescription.getStyle() instanceof RectangularNodeStyleDescription) {
                            assertThat(nodeDescription.getInsideLabel().getLabelExpression()).isEqualTo("aql:'NodeWithoutImage'");
                        }
                        if (nodeDescription.getStyle() instanceof ImageNodeStyleDescription) {
                            assertThat(nodeDescription.getOutsideLabels().get(0).getLabelExpression()).isEqualTo("aql:'NodeWithImage'");
                        }
                    });
                }
            });
        }
    }

}
