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
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.SetValue;
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
 * Integration tests of ReferenceWidgetDescriptionAddBodyMigrationParticipant.
 *
 * @author tgiraudet
 */
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReferenceWidgetDescriptionAddElementMigrationParticipantTests extends AbstractIntegrationTests {

    @Autowired
    private IEditingContextSearchService editingContextSearchService;

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given an old reference widget model with a body, when it is loaded, then an add body with the content of the old body is added")
    public void givenOldReferenceWidgetModelWithABodyWhenItIsLoadedThenAnAddBodyWithTheContentOfTheOldBodyIsAdded() {
        var editingContextId = MigrationIdentifiers.MIGRATION_REFERENCE_WIDGET_DESCRIPTION_CLEAR_BUTTON_STUDIO.toString();
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
        assertThat(referenceWidgetDescription.getAddBody()).isNotNull();

        // body should not be modified
        assertThat(referenceWidgetDescription.getBody()).hasSize(1);

        // Check content of the addBody
        assertThat(referenceWidgetDescription.getAddBody().getBody()).hasSize(1);
        var operation = referenceWidgetDescription.getAddBody().getBody().get(0);
        assertThat(operation).isInstanceOf(ChangeContext.class).isNotSameAs(referenceWidgetDescription.getBody().get(0));
        var changeContext = (ChangeContext) operation;
        assertThat(changeContext.getExpression()).isEqualTo("aql:self");
        assertThat(changeContext.getChildren()).hasSize(1);
        var child = changeContext.getChildren().get(0);
        assertThat(child).isInstanceOf(SetValue.class).isNotSameAs(referenceWidgetDescription.getBody().get(0).getChildren().get(0));
        var setValue = (SetValue) child;
        assertThat(setValue.getFeatureName()).isEqualTo("target");
        assertThat(setValue.getValueExpression()).isEqualTo("aql:newValue");
    }
}
