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
import static org.assertj.core.api.InstanceOfAssertFactories.type;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.view.form.CheckboxDescription;
import org.eclipse.sirius.components.view.form.FormDescription;
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
 * Integration tests of WidgetDescriptionStyleLayoutPropertiesMigrationParticipant.
 *
 * @author lfasani
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WidgetDescriptionStyleLayoutPropertiesMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;


    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a project with an old model, InsideLabelStyleHeaderSeparatorDisplayMigrationParticipant migrates the model correctly")
    public void givenAnOldModelMigrationParticipantCanBeContributedToUpdateTheModel() {
        var optionalEditingContext = this.editingContextSearchService.findById(MigrationIdentifiers.MIGRATION_WIDGET_DESCRIPTION_STYLE_LAYOUT_PROPERTIES_MIGRATION_PARTICIPANT_STUDIO.toString());
        assertThat(optionalEditingContext).isPresent();
        this.testIsMigrationSuccessful(optionalEditingContext.get());
    }


    private void testIsMigrationSuccessful(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            var optionalFormDescription = siriusWebEditingContext.getViews().stream().flatMap(view -> view.getDescriptions().stream())
                    .filter(representationDescription -> representationDescription.getId().equals(MigrationIdentifiers.MIGRATION_WIDGET_DESCRIPTION_STYLE_LAYOUT_PROPERTIES_MIGRATION_PARTICIPANT_STUDIO_FORM))
                    .findFirst();
            assertThat(optionalFormDescription).isPresent();
            assertThat(optionalFormDescription.get()).isInstanceOf(FormDescription.class);
            optionalFormDescription.ifPresent(representationDescription -> {
                if (representationDescription instanceof FormDescription formDescription) {
                    assertThat(formDescription.getPages().get(0).getGroups().get(0).getChildren().get(2))
                            .asInstanceOf(type(CheckboxDescription.class))
                            .returns("min-content 1fr", textfield -> textfield.getStyle().getGridTemplateColumns())
                            .returns("2/3", textfield -> textfield.getStyle().getLabelGridColumn())
                            .returns("1/2", textfield -> textfield.getStyle().getWidgetGridRow());
                }
            });
        }
    }


}
