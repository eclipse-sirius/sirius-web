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
package org.eclipse.sirius.components.view.emf.widget.reference;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.view.emf.form.api.IViewFormDescriptionSearchService;
import org.eclipse.sirius.components.collaborative.widget.reference.messages.IReferenceMessageService;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceFactory;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the View reference widget clear handler.
 *
 * @author tgiraudet
 */
public class ViewReferenceWidgetClearHandlerTests {

    @Test
    @DisplayName("Given a reference widget clear button without a body, when it is cleared, then nothing is executed")
    public void givenAReferenceWidgetClearButtonWithoutABodyWhenItIsClearedThenNothingIsExecuted() {
        var referenceWidgetDescription = ReferenceFactory.eINSTANCE.createReferenceWidgetDescription();
        referenceWidgetDescription.setClearButton(ReferenceFactory.eINSTANCE.createReferenceWidgetClearButtonDescription());
        var handler = new ViewReferenceWidgetClearHandler(new IObjectSearchService.NoOp(), this.createSearchService(referenceWidgetDescription), (editingContext, view) -> null,
                new IOperationExecutor.NoOp(), new IFeedbackMessageService.NoOp(), new IReferenceMessageService.NoOp());

        IStatus status = handler.clear(new IEditingContext.NoOp(), this.createReferenceWidget());

        assertThat(status)
                .isInstanceOfSatisfying(Success.class, success -> assertThat(success.getChangeKind()).isEmpty());
    }

    private IViewFormDescriptionSearchService createSearchService(ReferenceWidgetDescription referenceWidgetDescription) {
        return new IViewFormDescriptionSearchService.NoOp() {
            @Override
            public Optional<FormElementDescription> findFormElementDescriptionById(IEditingContext editingContext, String formElementDescriptionId) {
                return Optional.of(referenceWidgetDescription);
            }
        };
    }

    private ReferenceWidget createReferenceWidget() {
        return ReferenceWidget.newReferenceWidget("referenceWidgetId")
                .descriptionId("descriptionId")
                .label("")
                .diagnostics(Collections.emptyList())
                .referenceValues(Collections.emptyList())
                .referenceOptionsProvider(Collections::emptyList)
                .ownerId("ownerId")
                .ownerKind("")
                .referenceKind("")
                .referenceName("references")
                .many(false)
                .containment(false)
                .readOnly(false)
                .build();
    }
}
