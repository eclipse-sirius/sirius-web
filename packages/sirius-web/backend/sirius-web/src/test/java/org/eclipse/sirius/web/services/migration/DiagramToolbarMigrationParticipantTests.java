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

import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
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
 * Integration tests of DiagramToolbarMigrationParticipant.
 *
 * @author tgiraudet
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DiagramToolbarMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an old diagram description, when the diagram description is loaded, then it has a toolbar")
    public void givenAnOldDiagramDescriptionWhenTheDiagramDescriptionIsLoadedThenItHasToolbar() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();

        var editingContext = optionalEditingContext.get();
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalDiagramDescription = siriusWebEditingContext.getViews().stream()
                    .flatMap(view -> view.getDescriptions().stream())
                    .filter(representationDescription -> representationDescription.getName().equals(MigrationIdentifiers.MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO_DIAGRAM))
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast)
                    .findFirst();
            assertThat(optionalDiagramDescription).isPresent();

            var diagramDescription = optionalDiagramDescription.get();
            assertThat(diagramDescription.getToolbar()).isNotNull();
            assertThat(diagramDescription.getToolbar().isExpandedByDefault()).isTrue();
        }
    }
}
