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
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.data.MigrationIdentifiers;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of ReferenceWidgetDescriptionRemoveMigrationParticipant.
 *
 * @author tgiraudet
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReferenceWidgetDescriptionRemoveMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given old reference widget descriptions, when they are loaded, then empty remove operations are added")
    public void givenOldReferenceWidgetDescriptionsWhenTheyAreLoadedThenEmptyRemoveOperationsAreAdded() {
        var editingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_REFERENCE_WIDGET_DESCRIPTION_STUDIO.toString()).orElseThrow();
        assertThat(editingContext).isInstanceOf(EditingContext.class);
        var formDescription = ((EditingContext) editingContext).getViews().stream()
                .flatMap(view -> view.getDescriptions().stream())
                .filter(description -> description.getName().equals("WidgetRefMonoValue"))
                .map(FormDescription.class::cast)
                .findFirst().orElseThrow();

        assertThat(formDescription.getPages().get(0).getGroups().get(0).getChildren())
                .hasSize(2)
                .allSatisfy(widget -> assertThat(widget).isInstanceOfSatisfying(ReferenceWidgetDescription.class, referenceWidgetDescription -> {
                    assertThat(referenceWidgetDescription.getRemove()).isNotNull();
                    assertThat(referenceWidgetDescription.getRemove().getBody()).isEmpty();
                    assertThat(referenceWidgetDescription.getRemove().getPreconditionExpression()).isNull();
                }));
    }
}
