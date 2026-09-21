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
package org.eclipse.sirius.web.application.editingcontext.migration.participants;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonObject;

import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests the migration of reference widget clear button bodies.
 *
 * @author tgiraudet
 */
public class ReferenceWidgetDescriptionClearButtonBodyMigrationParticipantTests {

    @Test
    @DisplayName("Given an existing reference widget clear button without a body, when it is migrated, then it has the default clear operation")
    public void givenExistingReferenceWidgetClearButtonWithoutABodyWhenItIsMigratedThenItHasTheDefaultClearOperation() {
        var participant = new ReferenceWidgetDescriptionClearButtonBodyMigrationParticipant();
        var referenceWidgetDescription = ReferenceFactory.eINSTANCE.createReferenceWidgetDescription();
        referenceWidgetDescription.setClearButton(ReferenceFactory.eINSTANCE.createReferenceWidgetClearButtonDescription());

        participant.postObjectLoading(null, referenceWidgetDescription, new JsonObject());

        assertThat(referenceWidgetDescription.getClearButton().getBody())
                .singleElement()
                .isInstanceOfSatisfying(ChangeContext.class, changeContext -> assertThat(changeContext.getExpression()).isEqualTo("aql:referenceOwner.defaultClearReference(referenceName)"));
    }
}
