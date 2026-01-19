/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
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
 * Integration tests of InsideLabelStyleHeaderSeparatorDisplayMigrationParticipant.
 *
 * @author frouene
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InsideLabelStyleHeaderSeparatorDisplayMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with an old model, InsideLabelStyleHeaderSeparatorDisplayMigrationParticipant migrates the model correctly")
    public void givenAnOldModelMigrationParticipantCanBeContributedToUpdateTheModel() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_INSIDE_LABEL_STYLE_HEADER_SEPARATOR_DISPLAY_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }


    private void testIsMigrationSuccessful(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalDiagramDescription = siriusWebEditingContext.getViews().stream().flatMap(view -> view.getDescriptions().stream())
                    .filter(representationDescription -> representationDescription.getId().equals(MigrationIdentifiers.MIGRATION_INSIDE_LABEL_STYLE_HEADER_SEPARATOR_DISPLAY_STUDIO_DIAGRAM))
                    .findFirst();
            assertThat(optionalDiagramDescription).isPresent();
            assertThat(optionalDiagramDescription.get()).isInstanceOf(DiagramDescription.class);
            optionalDiagramDescription.ifPresent(representationDescription -> {
                if (representationDescription instanceof DiagramDescription diagramDescription) {
                    assertThat(diagramDescription.getNodeDescriptions()).hasSize(2);
                    diagramDescription.getNodeDescriptions().forEach(nodeDescription -> {
                        if (nodeDescription.getId().equals(MigrationIdentifiers.MIGRATION_INSIDE_LABEL_STYLE_HEADER_SEPARATOR_DISPLAY_STUDIO_NODE_1)) {
                            assertThat(nodeDescription.getInsideLabel().getStyle().getHeaderSeparatorDisplayMode()).isEqualTo(HeaderSeparatorDisplayMode.IF_CHILDREN);
                        } else {
                            assertThat(nodeDescription.getInsideLabel().getStyle().getHeaderSeparatorDisplayMode()).isEqualTo(HeaderSeparatorDisplayMode.NEVER);
                        }
                    });
                }
            });
        }
    }


}
