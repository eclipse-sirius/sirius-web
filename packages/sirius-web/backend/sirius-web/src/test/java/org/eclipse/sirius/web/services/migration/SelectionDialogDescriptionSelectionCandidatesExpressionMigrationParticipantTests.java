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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of SelectionDialogDescriptionSelectionCandidatesExpressionMigrationParticipant.
 *
 * @author fbarbin
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelectionDialogDescriptionSelectionCandidatesExpressionMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @DisplayName("Given a project with an old model, SelectionDialogDescriptionSelectionCandidatesExpressionMigrationParticipant migrates the model correctly")
    @Sql(scripts = { "/scripts/migration.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = { "/scripts/cleanup.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED))
    public void givenAnOldModelMigrationParticipantCanBeContributedToUpdateTheModel() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_SELECTION_DIALOG_DESCRIPTION_SELECTION_CANDIDATES_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }

    private void testIsMigrationSuccessful(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalDiagramDescription = siriusWebEditingContext.getViews().stream().flatMap(view -> view.getDescriptions().stream()).filter(
                    representationDescription -> representationDescription.getName().equals(MigrationIdentifiers.MIGRATION_SELECTION_DIALOG_DESCRIPTION_SELECTION_CANDIDATES_EXPRESSION_STUDIO_DIAGRAM))
                    .findFirst();
            assertThat(optionalDiagramDescription).isPresent();
            assertThat(optionalDiagramDescription.get()).isInstanceOf(DiagramDescription.class);
            optionalDiagramDescription.ifPresent(representationDescription -> {
                if (representationDescription instanceof DiagramDescription diagramDescription) {
                    assertThat(diagramDescription.getPalette()).isNotNull();
                    var palette = diagramDescription.getPalette();
                    assertThat(palette.getNodeTools()).hasSize(1);
                    var nodeTool = palette.getNodeTools().get(0);
                    var dialogDescription = nodeTool.getDialogDescription();
                    assertThat(dialogDescription).isNotNull();
                    assertThat(dialogDescription).isInstanceOf(SelectionDialogDescription.class);
                    if (dialogDescription instanceof SelectionDialogDescription selectionDialogDescription) {
                        var selectionDialogTreeDescription = selectionDialogDescription.getSelectionDialogTreeDescription();
                        assertThat(selectionDialogTreeDescription).isNotNull();
                        assertThat(selectionDialogTreeDescription.getElementsExpression()).isEqualTo("aql:self.eContents()");
                    }
                }
            });
        }
    }

}
