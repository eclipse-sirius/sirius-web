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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests of ReferenceWidgetDescriptionCreateButtonMigrationParticipant.
 *
 * @author tgiraudet
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReferenceWidgetDescriptionCreateButtonMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an old reference widget model, when it is loaded, then a create button is added")
    public void givenOldReferenceWidgetModelWhenItIsLoadedThenACreateButtonIsAdded() {
        var editingContextId = MigrationIdentifiers.MIGRATION_REFERENCE_WIDGET_DESCRIPTION_STUDIO.toString();
        var optionalEditingContext = this.editingContextSearchService.findById(editingContextId);

        assertThat(optionalEditingContext).isPresent();
        assertThat(optionalEditingContext.orElseThrow()).isInstanceOf(EditingContext.class);
        var editingContext = (EditingContext) optionalEditingContext.orElseThrow();
        var optionalFormDescription = editingContext.getViews().stream()
                .flatMap(view -> view.getDescriptions().stream())
                .filter(description -> description.getName().equals("WidgetRefMonoValue"))
                .findFirst();
        assertThat(optionalFormDescription).isPresent();
        assertThat(optionalFormDescription.orElseThrow()).isInstanceOf(FormDescription.class);
        var formDescription = (FormDescription) optionalFormDescription.orElseThrow();
        var widget = formDescription.getPages().get(0).getGroups().get(0).getChildren().get(0);
        assertThat(widget).isInstanceOf(ReferenceWidgetDescription.class);
        var referenceWidgetDescription = (ReferenceWidgetDescription) widget;
        assertThat(referenceWidgetDescription.getLabelExpression()).isEqualTo("Test Widget Reference");
        assertThat(referenceWidgetDescription.getCreateButton()).isNotNull();
    }
}
