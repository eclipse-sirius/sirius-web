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
package org.eclipse.sirius.web.application.views.details.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetReflectiveSetService;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetSetElementHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.forms.NonContainmentReferenceIfDescriptionProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Service to set Details View references.
 *
 * @author tgiraudet
 */
@Service
public class DetailsViewReferenceWidgetSetElementHandler implements IReferenceWidgetSetElementHandler {

    private final IReferenceWidgetReflectiveSetService reflectiveSetService;

    public DetailsViewReferenceWidgetSetElementHandler(IReferenceWidgetReflectiveSetService reflectiveSetService) {
        this.reflectiveSetService = Objects.requireNonNull(reflectiveSetService);
    }

    @Override
    public boolean canHandle(FormDescription formDescription, ReferenceWidget referenceWidget) {
        return referenceWidget.getDescriptionId().equals(NonContainmentReferenceIfDescriptionProvider.REFERENCE_WIDGET_DESCRIPTION_ID);
    }

    @Override
    public IStatus set(IEditingContext editingContext, FormDescription formDescription, ReferenceWidget referenceWidget, String newValueId) {
        return this.reflectiveSetService.set(editingContext, referenceWidget, newValueId);
    }
}
