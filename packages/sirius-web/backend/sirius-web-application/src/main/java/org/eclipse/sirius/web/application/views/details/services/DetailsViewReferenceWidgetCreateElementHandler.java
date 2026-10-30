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

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetCreateElementHandler;
import org.eclipse.sirius.components.collaborative.widget.reference.api.IReferenceWidgetReflectiveCreateService;
import org.eclipse.sirius.components.core.api.ChildCreationDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.forms.NonContainmentReferenceIfDescriptionProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.widget.reference.ReferenceWidget;
import org.springframework.stereotype.Service;

/**
 * Creates objects from Details View reference widgets.
 *
 * @author tgiraudet
 */
@Service
public class DetailsViewReferenceWidgetCreateElementHandler implements IReferenceWidgetCreateElementHandler {

    private final IReferenceWidgetReflectiveCreateService reflectiveCreateService;

    public DetailsViewReferenceWidgetCreateElementHandler(IReferenceWidgetReflectiveCreateService reflectiveCreateService) {
        this.reflectiveCreateService = Objects.requireNonNull(reflectiveCreateService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, FormDescription formDescription, String referenceWidgetDescriptionId) {
        return referenceWidgetDescriptionId.equals(NonContainmentReferenceIfDescriptionProvider.REFERENCE_WIDGET_DESCRIPTION_ID);
    }

    @Override
    public List<ChildCreationDescription> getRootCreationDescriptions(IEditingContext editingContext, String domainId, String referenceKind, String descriptionId) {
        return this.reflectiveCreateService.getRootCreationDescriptions(editingContext, domainId, referenceKind);
    }

    @Override
    public List<ChildCreationDescription> getChildCreationDescriptions(IEditingContext editingContext, String kind, String referenceKind, String descriptionId) {
        return this.reflectiveCreateService.getChildCreationDescriptions(editingContext, kind, referenceKind);
    }

    @Override
    public IStatus createRootObject(IEditingContext editingContext, UUID documentId, String domainId, String rootObjectCreationDescriptionId, ReferenceWidget referenceWidget) {
        return this.reflectiveCreateService.createRootObject(editingContext, documentId, domainId, rootObjectCreationDescriptionId, referenceWidget);
    }

    @Override
    public IStatus createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId, ReferenceWidget referenceWidget) {
        return this.reflectiveCreateService.createChild(editingContext, object, childCreationDescriptionId, referenceWidget);
    }
}
