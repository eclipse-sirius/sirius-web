/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of SelectionDialogDescriptionMigrateLabelsToExpressionMigrationParticipant.
 *
 * @author gcoutable
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SelectionDialogDescriptionMigrateLabelsToExpressionMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with an old selection dialog description, when the editing context is loaded, then SelectionDialogDescriptionMigrateLabelsToExpressionMigrationParticipant migrates the old selection dialog description correctly")
    public void givenProjectWithOldSelectionDialogDescriptionWhenEditingContextIsLoadedThenSelectionDialogDescriptionMigrateLabelsToExpressionMigrationParticipantMigratesOldSelectionDialogDescriptionCorrectly() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_SELECTION_DIALOG_DESCRIPTION_SELECTION_CANDIDATES_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }

    private void testIsMigrationSuccessful(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalDiagramDescription = siriusWebEditingContext.getViews().stream().flatMap(view -> view.getDescriptions().stream()).filter(
                            representationDescription -> representationDescription.getName().equals(MigrationIdentifiers.SELECTION_DIALOG_DESCRIPTION_MIGRATE_LABELS_TO_EXPRESSION_MIGRATION_PARTICIPANT_DOCUMENT_NAME))
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
                        assertThat(selectionDialogDescription.getDescriptionExpression()).isEqualTo("Selection message to migrate");
                        assertThat(selectionDialogDescription.getNoSelectionActionLabelExpression()).isEqualTo("no selection label to migrate");
                    }
                }
            });
        }
    }
}
